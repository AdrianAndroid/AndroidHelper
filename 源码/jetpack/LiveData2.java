//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap.IteratorWithAdditions;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import java.util.Iterator;
import java.util.Map.Entry;

public abstract class LiveData<T> {
    final Object mDataLock = new Object();
    static final int START_VERSION = -1;
    static final Object NOT_SET = new Object();
    private SafeIterableMap<Observer<? super T>, LiveData<T>.ObserverWrapper> mObservers = new SafeIterableMap();
    int mActiveCount = 0;
    private volatile Object mData;
    volatile Object mPendingData;
    private int mVersion;
    private boolean mDispatchingValue;
    private boolean mDispatchInvalidated;
    private final Runnable mPostValueRunnable;

    public LiveData(T value) {
        this.mPendingData = NOT_SET;
        this.mPostValueRunnable = new NamelessClass_1();
        this.mData = value;
        this.mVersion = 0;
    }

    public LiveData() {
        this.mPendingData = NOT_SET;

        class NamelessClass_1 implements Runnable {
            NamelessClass_1() {
            }

            public void run() {
                Object newValue;
                synchronized(LiveData.this.mDataLock) {
                    newValue = LiveData.this.mPendingData;
                    LiveData.this.mPendingData = LiveData.NOT_SET;
                }

                LiveData.this.setValue(newValue);
            }
        }

        this.mPostValueRunnable = new NamelessClass_1();
        this.mData = NOT_SET;
        this.mVersion = -1;
    }

    private void considerNotify(LiveData<T>.ObserverWrapper observer) {
        if (observer.mActive) {
            if (!observer.shouldBeActive()) {
                observer.activeStateChanged(false);
            } else if (observer.mLastVersion < this.mVersion) {
                observer.mLastVersion = this.mVersion;
                observer.mObserver.onChanged(this.mData);
            }
        }
    }
    // 若参数 initiator 非空,则表示只通知特定的 ObserverWrapper, 否则是回调通知所有 ObserverWrapper
    // 由于只在主线程中调用,因此不用做多线程处理
    void dispatchingValue(@Nullable LiveData<T>.ObserverWrapper initiator) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
        } else {
            this.mDispatchingValue = true;

            do {
                this.mDispatchInvalidated = false;
                if (initiator != null) {
                    this.considerNotify(initiator);
                    initiator = null;
                } else {
                    IteratorWithAdditions iterator = this.mObservers.iteratorWithAdditions();

                    while(iterator.hasNext()) {
                        this.considerNotify((LiveData.ObserverWrapper)((Entry)iterator.next()).getValue());
                        if (this.mDispatchInvalidated) {
                            break;
                        }
                    }
                }
            } while(this.mDispatchInvalidated);

            this.mDispatchingValue = false;
        }
    }

    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        assertMainThread("observe");
        if (owner.getLifecycle().getCurrentState() != State.DESTROYED) {
            // 将LifecycleOwner和Observer功能进行绑定包装
            // 生成支持声明周期感知的Observer：LifecycleBoundObserver
            LiveData<T>.LifecycleBoundObserver wrapper = new LiveData.LifecycleBoundObserver(owner, observer);
            // 避免重复添加相同的observer
            LiveData<T>.ObserverWrapper existing = (LiveData.ObserverWrapper)this.mObservers.putIfAbsent(observer, wrapper);
            if (existing != null && !existing.isAttachedTo(owner)) {
                throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
            } else if (existing == null) {
                owner.getLifecycle().addObserver(wrapper);
            }
        }
    }

    @MainThread
    public void observeForever(@NonNull Observer<? super T> observer) {
        assertMainThread("observeForever");
        LiveData<T>.AlwaysActiveObserver wrapper = new LiveData.AlwaysActiveObserver(observer);
        LiveData<T>.ObserverWrapper existing = (LiveData.ObserverWrapper)this.mObservers.putIfAbsent(observer, wrapper);
        if (existing instanceof LiveData.LifecycleBoundObserver) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        } else if (existing == null) {
            wrapper.activeStateChanged(true);
        }
    }

    @MainThread
    public void removeObserver(@NonNull Observer<? super T> observer) {
        assertMainThread("removeObserver");
        LiveData<T>.ObserverWrapper removed = (LiveData.ObserverWrapper)this.mObservers.remove(observer);
        if (removed != null) {
            removed.detachObserver();
            removed.activeStateChanged(false);
        }
    }

    @MainThread
    public void removeObservers(@NonNull LifecycleOwner owner) {
        assertMainThread("removeObservers");
        Iterator var2 = this.mObservers.iterator();

        while(var2.hasNext()) {
            Entry<Observer<? super T>, LiveData<T>.ObserverWrapper> entry = (Entry)var2.next();
            if (((LiveData.ObserverWrapper)entry.getValue()).isAttachedTo(owner)) {
                this.removeObserver((Observer)entry.getKey());
            }
        }

    }

    protected void postValue(T value) {
        boolean postTask;
        synchronized(this.mDataLock) {
            postTask = this.mPendingData == NOT_SET;
            this.mPendingData = value;
        }

        if (postTask) {
            ArchTaskExecutor.getInstance().postToMainThread(this.mPostValueRunnable);
        }
    }

    @MainThread
    protected void setValue(T value) {
        // 只能在UI线程中调用，否则抛出异常，崩溃
        assertMainThread("setValue");
        ++this.mVersion;
        this.mData = value;
        this.dispatchingValue((LiveData.ObserverWrapper)null);
    }

    @Nullable
    public T getValue() {
        Object data = this.mData;
        return data != NOT_SET ? data : null;
    }

    int getVersion() {
        return this.mVersion;
    }

    protected void onActive() {
    }

    protected void onInactive() {
    }

    public boolean hasObservers() {
        return this.mObservers.size() > 0;
    }

    public boolean hasActiveObservers() {
        return this.mActiveCount > 0;
    }

    static void assertMainThread(String methodName) {
        if (!ArchTaskExecutor.getInstance().isMainThread()) {
            throw new IllegalStateException("Cannot invoke " + methodName + " on a background thread");
        }
    }

    private class AlwaysActiveObserver extends LiveData<T>.ObserverWrapper {
        AlwaysActiveObserver(Observer<? super T> observer) {
            super(observer);
        }

        boolean shouldBeActive() {
            return true;
        }
    }

    private abstract class ObserverWrapper {
        final Observer<? super T> mObserver;
        boolean mActive;
        int mLastVersion = -1;

        ObserverWrapper(Observer<? super T> observer) {
            this.mObserver = observer;
        }

        abstract boolean shouldBeActive();

        boolean isAttachedTo(LifecycleOwner owner) {
            return false;
        }

        void detachObserver() {
        }

        void activeStateChanged(boolean newActive) {
            if (newActive != this.mActive) {
                this.mActive = newActive;
                boolean wasInactive = LiveData.this.mActiveCount == 0;
                LiveData var10000 = LiveData.this;
                var10000.mActiveCount += this.mActive ? 1 : -1;
                if (wasInactive && this.mActive) {
                    LiveData.this.onActive();
                }

                if (LiveData.this.mActiveCount == 0 && !this.mActive) {
                    LiveData.this.onInactive();
                }

                if (this.mActive) {
                    LiveData.this.dispatchingValue(this);
                }

            }
        }
    }

    class LifecycleBoundObserver extends LiveData<T>.ObserverWrapper implements LifecycleEventObserver {
        @NonNull
        final LifecycleOwner mOwner;

        LifecycleBoundObserver(@NonNull LifecycleOwner owner, Observer<? super T> observer) {
            super(observer);
            this.mOwner = owner;
        }

        boolean shouldBeActive() {
            return this.mOwner.getLifecycle().getCurrentState().isAtLeast(State.STARTED);
        }

        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Event event) {
            if (this.mOwner.getLifecycle().getCurrentState() == State.DESTROYED) {
                LiveData.this.removeObserver(this.mObserver);
            } else {
                this.activeStateChanged(this.shouldBeActive());
            }
        }

        boolean isAttachedTo(LifecycleOwner owner) {
            return this.mOwner == owner;
        }

        void detachObserver() {
            this.mOwner.getLifecycle().removeObserver(this);
        }
    }
}
