// LiveData.java
public abstract class LiveData<T> {
    private static final Object NOT_SET = new Object();
    // 实际数据,类型为 Object 而非 T
    private volatile Object mData = NOT_SET;
    // 存储所有的 Observer
    private SafeIterableMap<Observer<T>, ObserverWrapper> mObservers = new SafeIterableMap<>();

    // 添加跟生命周期相关的 observer
    // 目标数据
    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        // 若 LifecycleOwner 已处于已被销毁,则忽略该 observer
        if (owner.getLifecycle().getCurrentState() == DESTROYED) {
            return;
        }

        // 将 LifecycleOwner 和 Observer 功能进行绑定包装
        // 生成支持生命周期感知的 Observer: LifecycleBoundObserver
        LifecycleBoundObserver wrapper = new LifecycleBoundObserver(owner, observer);
        // 避免重复添加相同的observer
        ObserverWrapper existing = mObservers.putIfAbsent(observer, wrapper);
        if (existing != null && !existing.isAttachedTo(owner)) {
            throw new IllegalArgumentException("Cannot add the same observer"
                    + " with different lifecycles");
        }
        if (existing != null) {
            return;
        }

        // 实现对 LifecycleOwner 生命周期的感知
        // 关键还是 LifecycleBoundObserver 类,我们马上进去看一下
        owner.getLifecycle().addObserver(wrapper);
    }

    // 无视生命周期, 每次数据发生变化时,都会回调通知 Observer
    // 需要手动在不需要时移除 Observer
    @MainThread
    public void observeForever(@NonNull Observer<T> observer) {
        AlwaysActiveObserver wrapper = new AlwaysActiveObserver(observer);
        ObserverWrapper existing = mObservers.putIfAbsent(observer, wrapper);
        if (existing != null && existing instanceof LiveData.LifecycleBoundObserver) {
            throw new IllegalArgumentException("Cannot add the same observer"
                    + " with different lifecycles");
        }
        if (existing != null) {
            return;
        }
        wrapper.activeStateChanged(true);
    }
}