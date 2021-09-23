//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package java.util.concurrent;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutor extends AbstractExecutorService {
    private final AtomicInteger ctl;
    private static final int COUNT_BITS = 29;
    private static final int COUNT_MASK = 536870911;
    private static final int RUNNING = -536870912;
    private static final int SHUTDOWN = 0;
    private static final int STOP = 536870912;
    private static final int TIDYING = 1073741824;
    private static final int TERMINATED = 1610612736;
    private final BlockingQueue<Runnable> workQueue;
    private final ReentrantLock mainLock;
    private final HashSet<ThreadPoolExecutor.Worker> workers;
    private final Condition termination;
    private int largestPoolSize;
    private long completedTaskCount;
    private volatile ThreadFactory threadFactory;
    private volatile RejectedExecutionHandler handler;
    private volatile long keepAliveTime;
    private volatile boolean allowCoreThreadTimeOut;
    private volatile int corePoolSize;
    private volatile int maximumPoolSize;
    private static final RejectedExecutionHandler defaultHandler = new ThreadPoolExecutor.AbortPolicy();
    private static final RuntimePermission shutdownPerm = new RuntimePermission("modifyThread");
    private static final boolean ONLY_ONE = true;

    private static int runStateOf(int c) {
        return c & -536870912;
    }

    private static int workerCountOf(int c) {
        return c & 536870911;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static boolean runStateLessThan(int c, int s) {
        return c < s;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private static boolean isRunning(int c) {
        return c < 0;
    }

    private boolean compareAndIncrementWorkerCount(int expect) {
        return this.ctl.compareAndSet(expect, expect + 1);
    }

    private boolean compareAndDecrementWorkerCount(int expect) {
        return this.ctl.compareAndSet(expect, expect - 1);
    }

    private void decrementWorkerCount() {
        this.ctl.addAndGet(-1);
    }

    private void advanceRunState(int targetState) {
        int c;
        do {
            c = this.ctl.get();
        } while(!runStateAtLeast(c, targetState) && !this.ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c))));

    }

    final void tryTerminate() {
        while(true) {
            int c = this.ctl.get();
            if (isRunning(c) || runStateAtLeast(c, 1073741824) || runStateLessThan(c, 536870912) && !this.workQueue.isEmpty()) {
                return;
            }

            if (workerCountOf(c) != 0) {
                this.interruptIdleWorkers(true);
                return;
            }

            ReentrantLock mainLock = this.mainLock;
            mainLock.lock();

            try {
                if (!this.ctl.compareAndSet(c, ctlOf(1073741824, 0))) {
                    continue;
                }

                try {
                    this.terminated();
                } finally {
                    this.ctl.set(ctlOf(1610612736, 0));
                    this.termination.signalAll();
                }
            } finally {
                mainLock.unlock();
            }

            return;
        }
    }

    private void checkShutdownAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(shutdownPerm);
            Iterator var2 = this.workers.iterator();

            while(var2.hasNext()) {
                ThreadPoolExecutor.Worker w = (ThreadPoolExecutor.Worker)var2.next();
                security.checkAccess(w.thread);
            }
        }

    }

    private void interruptWorkers() {
        Iterator var1 = this.workers.iterator();

        while(var1.hasNext()) {
            ThreadPoolExecutor.Worker w = (ThreadPoolExecutor.Worker)var1.next();
            w.interruptIfStarted();
        }

    }

    private void interruptIdleWorkers(boolean onlyOne) {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            Iterator var3 = this.workers.iterator();

            while(var3.hasNext()) {
                ThreadPoolExecutor.Worker w = (ThreadPoolExecutor.Worker)var3.next();
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException var15) {
                    } finally {
                        w.unlock();
                    }
                }

                if (onlyOne) {
                    break;
                }
            }
        } finally {
            mainLock.unlock();
        }

    }

    private void interruptIdleWorkers() {
        this.interruptIdleWorkers(false);
    }

    final void reject(Runnable command) {
        this.handler.rejectedExecution(command, this);
    }

    void onShutdown() {
    }

    private List<Runnable> drainQueue() {
        BlockingQueue<Runnable> q = this.workQueue;
        ArrayList<Runnable> taskList = new ArrayList();
        q.drainTo(taskList);
        if (!q.isEmpty()) {
            Runnable[] var3 = (Runnable[])q.toArray(new Runnable[0]);
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Runnable r = var3[var5];
                if (q.remove(r)) {
                    taskList.add(r);
                }
            }
        }

        return taskList;
    }

    private boolean addWorker2(Runnable firstTask, boolean core) {
        // 第一部分：自旋、CAS、重读ctl 等结合，直到确定是否可以创建worker，
        // 可以则跳出循环继续操作，否则返回false
        retry:
        for (;;) {
            int c = ctl.get();
            int rs = runStateOf(c);

            // Check if queue empty only if necessary.
            if (rs >= SHUTDOWN &&
                ! (rs == SHUTDOWN &&
                   firstTask == null &&
                   ! workQueue.isEmpty()))
                return false;

            for (;;) {
                int wc = workerCountOf(c);
                if (wc >= CAPACITY ||
                    wc >= (core ? corePoolSize : maximumPoolSize))
                    return false;
                if (compareAndIncrementWorkerCount(c)) // CAS增长workerCount，成功则跳出循环
                    break retry;
                c = ctl.get();  // Re-read ctl 重新获取ctl
                if (runStateOf(c) != rs) // 状态改变则继续外层循环，否则在内层循环
                    continue retry;
                // else CAS failed due to workerCount change; retry inner loop
            }
        }
        // 第二部分：创建worker，这部分使用ReentrantLock锁
        boolean workerStarted = false; // 线程启动标志位
        boolean workerAdded = false;  // 线程是否加入workers 标志位
        Worker w = null;
        try {
            w = new Worker(firstTask); //创建worker
            final Thread t = w.thread;
            if (t != null) {
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {
                    // 获取到锁以后仍需检查ctl，可能在上一个获取到锁处理的线程可能会改变runState
                    // 如 ThreadFactory 创建失败 或线程池被 shut down等
                    int rs = runStateOf(ctl.get());

                    if (rs < SHUTDOWN ||
                        (rs == SHUTDOWN && firstTask == null)) {
                        if (t.isAlive())
                            throw new IllegalThreadStateException();
                        workers.add(w);
                        int s = workers.size();
                        if (s > largestPoolSize)
                            largestPoolSize = s;
                        workerAdded = true;
                    }
                } finally {
                    mainLock.unlock();
                }
                if (workerAdded) {
                    t.start(); // 启动线程
                    workerStarted = true;
                }
            }
        } finally {
            if (! workerStarted)
                addWorkerFailed(w); // 失败操作
        }
        return workerStarted;
    }

    private boolean addWorker(Runnable firstTask, boolean core) {
        int c = this.ctl.get();

        label247:
        while(!runStateAtLeast(c, 0) || !runStateAtLeast(c, 536870912) && firstTask == null && !this.workQueue.isEmpty()) {
            while(workerCountOf(c) < ((core ? this.corePoolSize : this.maximumPoolSize) & 536870911)) {
                if (this.compareAndIncrementWorkerCount(c)) {
                    boolean workerStarted = false;
                    boolean workerAdded = false;
                    ThreadPoolExecutor.Worker w = null;

                    try {
                        w = new ThreadPoolExecutor.Worker(firstTask);
                        Thread t = w.thread;
                        if (t != null) {
                            ReentrantLock mainLock = this.mainLock;
                            mainLock.lock();

                            try {
                                int c = this.ctl.get();
                                if (isRunning(c) || runStateLessThan(c, 536870912) && firstTask == null) {
                                    if (t.getState() != State.NEW) {
                                        throw new IllegalThreadStateException();
                                    }

                                    this.workers.add(w);
                                    workerAdded = true;
                                    int s = this.workers.size();
                                    if (s > this.largestPoolSize) {
                                        this.largestPoolSize = s;
                                    }
                                }
                            } finally {
                                mainLock.unlock();
                            }

                            if (workerAdded) {
                                t.start();
                                workerStarted = true;
                            }
                        }
                    } finally {
                        if (!workerStarted) {
                            this.addWorkerFailed(w);
                        }

                    }

                    return workerStarted;
                }

                c = this.ctl.get();
                if (runStateAtLeast(c, 0)) {
                    continue label247;
                }
            }

            return false;
        }

        return false;
    }

    private void addWorkerFailed(ThreadPoolExecutor.Worker w) {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            if (w != null) {
                this.workers.remove(w);
            }

            this.decrementWorkerCount();
            this.tryTerminate();
        } finally {
            mainLock.unlock();
        }

    }

    private void processWorkerExit(ThreadPoolExecutor.Worker w, boolean completedAbruptly) {
        if (completedAbruptly) {
            this.decrementWorkerCount();
        }

        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            this.completedTaskCount += w.completedTasks;
            this.workers.remove(w);
        } finally {
            mainLock.unlock();
        }

        this.tryTerminate();
        int c = this.ctl.get();
        if (runStateLessThan(c, 536870912)) {
            if (!completedAbruptly) {
                int min = this.allowCoreThreadTimeOut ? 0 : this.corePoolSize;
                if (min == 0 && !this.workQueue.isEmpty()) {
                    min = 1;
                }

                if (workerCountOf(c) >= min) {
                    return;
                }
            }

            this.addWorker((Runnable)null, false);
        }

    }

    private Runnable getTask() {
        boolean timedOut = false;

        while(true) {
            int c = this.ctl.get();
            if (runStateAtLeast(c, 0) && (runStateAtLeast(c, 536870912) || this.workQueue.isEmpty())) {
                this.decrementWorkerCount();
                return null;
            }

            int wc = workerCountOf(c);
            boolean timed = this.allowCoreThreadTimeOut || wc > this.corePoolSize;
            if (wc <= this.maximumPoolSize && (!timed || !timedOut) || wc <= 1 && !this.workQueue.isEmpty()) {
                try {
                    Runnable r = timed ? (Runnable)this.workQueue.poll(this.keepAliveTime, TimeUnit.NANOSECONDS) : (Runnable)this.workQueue.take();
                    if (r != null) {
                        return r;
                    }

                    timedOut = true;
                } catch (InterruptedException var6) {
                    timedOut = false;
                }
            } else if (this.compareAndDecrementWorkerCount(c)) {
                return null;
            }
        }
    }

    final void runWorker(ThreadPoolExecutor.Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        w.unlock();
        boolean completedAbruptly = true;

        try {
            // loop 直至 task = null （线程池关闭、超时等）
            // 注意这里的getTask()方法，我们配置的阻塞队列会在这里起作用
            while(task != null || (task = this.getTask()) != null) {
                w.lock();
                / 如果线程池停止，确保线程中断; 如果没有，确保线程不中断。这需要在第二种情况下进行重新获取ctl，以便在清除中断时处理shutdownNow竞争
                if ((runStateAtLeast(this.ctl.get(), 536870912)
                    || Thread.interrupted() && runStateAtLeast(this.ctl.get(), 536870912)) && !wt.isInterrupted()) {
                    wt.interrupt();
                }

                try {
                    this.beforeExecute(wt, task);

                    try {
                        task.run();
                        this.afterExecute(task, (Throwable)null);
                    } catch (Throwable var14) {
                        this.afterExecute(task, var14);
                        throw var14;
                    }
                } finally {
                    task = null;
                    ++w.completedTasks;
                    w.unlock();
                }
            }

            completedAbruptly = false;
        } finally {
            this.processWorkerExit(w, completedAbruptly);
        }

    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), defaultHandler);
    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, defaultHandler);
    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), handler);
    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        this.ctl = new AtomicInteger(ctlOf(-536870912, 0));
        this.mainLock = new ReentrantLock();
        this.workers = new HashSet();
        this.termination = this.mainLock.newCondition();
        if (corePoolSize >= 0 && maximumPoolSize > 0 && maximumPoolSize >= corePoolSize && keepAliveTime >= 0L) {
            if (workQueue != null && threadFactory != null && handler != null) {
                this.corePoolSize = corePoolSize;
                this.maximumPoolSize = maximumPoolSize;
                this.workQueue = workQueue;
                this.keepAliveTime = unit.toNanos(keepAliveTime);
                this.threadFactory = threadFactory;
                this.handler = handler;
            } else {
                throw new NullPointerException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        } else {
            int c = this.ctl.get();
             // 1、工作线程 < 核心线程
            if (workerCountOf(c) < this.corePoolSize) {
                if (this.addWorker(command, true)) {
                    return;
                }

                c = this.ctl.get();
            }
            // 2、运行态，并尝试将任务加入队列
            if (isRunning(c) && this.workQueue.offer(command)) {
                int recheck = this.ctl.get();
                if (!isRunning(recheck) && this.remove(command)) {
                    this.reject(command);
                } else if (workerCountOf(recheck) == 0) {
                    this.addWorker((Runnable)null, false);
                }
            }
            // 3、使用尝试使用最大线程运行
            else if (!this.addWorker(command, false)) {
                this.reject(command);
            }

        }
    }

    public void shutdown() {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            this.checkShutdownAccess();
            this.advanceRunState(0);
            this.interruptIdleWorkers();
            this.onShutdown();
        } finally {
            mainLock.unlock();
        }

        this.tryTerminate();
    }

    public List<Runnable> shutdownNow() {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        List tasks;
        try {
            this.checkShutdownAccess();
            this.advanceRunState(536870912);
            this.interruptWorkers();
            tasks = this.drainQueue();
        } finally {
            mainLock.unlock();
        }

        this.tryTerminate();
        return tasks;
    }

    public boolean isShutdown() {
        return runStateAtLeast(this.ctl.get(), 0);
    }

    boolean isStopped() {
        return runStateAtLeast(this.ctl.get(), 536870912);
    }

    public boolean isTerminating() {
        int c = this.ctl.get();
        return runStateAtLeast(c, 0) && runStateLessThan(c, 1610612736);
    }

    public boolean isTerminated() {
        return runStateAtLeast(this.ctl.get(), 1610612736);
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        boolean var7;
        try {
            while(runStateLessThan(this.ctl.get(), 1610612736)) {
                if (nanos <= 0L) {
                    var7 = false;
                    return var7;
                }

                nanos = this.termination.awaitNanos(nanos);
            }

            var7 = true;
        } finally {
            mainLock.unlock();
        }

        return var7;
    }

    /** @deprecated */
    @Deprecated(
        since = "9"
    )
    protected void finalize() {
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        if (threadFactory == null) {
            throw new NullPointerException();
        } else {
            this.threadFactory = threadFactory;
        }
    }

    public ThreadFactory getThreadFactory() {
        return this.threadFactory;
    }

    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        if (handler == null) {
            throw new NullPointerException();
        } else {
            this.handler = handler;
        }
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return this.handler;
    }

    public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize >= 0 && this.maximumPoolSize >= corePoolSize) {
            int delta = corePoolSize - this.corePoolSize;
            this.corePoolSize = corePoolSize;
            if (workerCountOf(this.ctl.get()) > corePoolSize) {
                this.interruptIdleWorkers();
            } else if (delta > 0) {
                int var3 = Math.min(delta, this.workQueue.size());

                while(var3-- > 0 && this.addWorker((Runnable)null, true) && !this.workQueue.isEmpty()) {
                }
            }

        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public boolean prestartCoreThread() {
        return workerCountOf(this.ctl.get()) < this.corePoolSize && this.addWorker((Runnable)null, true);
    }

    void ensurePrestart() {
        int wc = workerCountOf(this.ctl.get());
        if (wc < this.corePoolSize) {
            this.addWorker((Runnable)null, true);
        } else if (wc == 0) {
            this.addWorker((Runnable)null, false);
        }

    }

    public int prestartAllCoreThreads() {
        int n;
        for(n = 0; this.addWorker((Runnable)null, true); ++n) {
        }

        return n;
    }

    public boolean allowsCoreThreadTimeOut() {
        return this.allowCoreThreadTimeOut;
    }

    public void allowCoreThreadTimeOut(boolean value) {
        if (value && this.keepAliveTime <= 0L) {
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        } else {
            if (value != this.allowCoreThreadTimeOut) {
                this.allowCoreThreadTimeOut = value;
                if (value) {
                    this.interruptIdleWorkers();
                }
            }

        }
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        if (maximumPoolSize > 0 && maximumPoolSize >= this.corePoolSize) {
            this.maximumPoolSize = maximumPoolSize;
            if (workerCountOf(this.ctl.get()) > maximumPoolSize) {
                this.interruptIdleWorkers();
            }

        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    public void setKeepAliveTime(long time, TimeUnit unit) {
        if (time < 0L) {
            throw new IllegalArgumentException();
        } else if (time == 0L && this.allowsCoreThreadTimeOut()) {
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        } else {
            long keepAliveTime = unit.toNanos(time);
            long delta = keepAliveTime - this.keepAliveTime;
            this.keepAliveTime = keepAliveTime;
            if (delta < 0L) {
                this.interruptIdleWorkers();
            }

        }
    }

    public long getKeepAliveTime(TimeUnit unit) {
        return unit.convert(this.keepAliveTime, TimeUnit.NANOSECONDS);
    }

    public BlockingQueue<Runnable> getQueue() {
        return this.workQueue;
    }

    public boolean remove(Runnable task) {
        boolean removed = this.workQueue.remove(task);
        this.tryTerminate();
        return removed;
    }

    public void purge() {
        BlockingQueue q = this.workQueue;

        try {
            Iterator it = q.iterator();

            while(it.hasNext()) {
                Runnable r = (Runnable)it.next();
                if (r instanceof Future && ((Future)r).isCancelled()) {
                    it.remove();
                }
            }
        } catch (ConcurrentModificationException var7) {
            Object[] var3 = q.toArray();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object r = var3[var5];
                if (r instanceof Future && ((Future)r).isCancelled()) {
                    q.remove(r);
                }
            }
        }

        this.tryTerminate();
    }

    public int getPoolSize() {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        int var2;
        try {
            var2 = runStateAtLeast(this.ctl.get(), 1073741824) ? 0 : this.workers.size();
        } finally {
            mainLock.unlock();
        }

        return var2;
    }

    public int getActiveCount() {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            int n = 0;
            Iterator var3 = this.workers.iterator();

            while(var3.hasNext()) {
                ThreadPoolExecutor.Worker w = (ThreadPoolExecutor.Worker)var3.next();
                if (w.isLocked()) {
                    ++n;
                }
            }

            int var8 = n;
            return var8;
        } finally {
            mainLock.unlock();
        }
    }

    public int getLargestPoolSize() {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        int var2;
        try {
            var2 = this.largestPoolSize;
        } finally {
            mainLock.unlock();
        }

        return var2;
    }

    public long getTaskCount() {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            long n = this.completedTaskCount;
            Iterator var4 = this.workers.iterator();

            while(var4.hasNext()) {
                ThreadPoolExecutor.Worker w = (ThreadPoolExecutor.Worker)var4.next();
                n += w.completedTasks;
                if (w.isLocked()) {
                    ++n;
                }
            }

            long var9 = n + (long)this.workQueue.size();
            return var9;
        } finally {
            mainLock.unlock();
        }
    }

    public long getCompletedTaskCount() {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        try {
            long n = this.completedTaskCount;

            ThreadPoolExecutor.Worker w;
            for(Iterator var4 = this.workers.iterator(); var4.hasNext(); n += w.completedTasks) {
                w = (ThreadPoolExecutor.Worker)var4.next();
            }

            long var9 = n;
            return var9;
        } finally {
            mainLock.unlock();
        }
    }

    public String toString() {
        ReentrantLock mainLock = this.mainLock;
        mainLock.lock();

        long ncompleted;
        int nworkers;
        int nactive;
        try {
            ncompleted = this.completedTaskCount;
            nactive = 0;
            nworkers = this.workers.size();
            Iterator var6 = this.workers.iterator();

            while(var6.hasNext()) {
                ThreadPoolExecutor.Worker w = (ThreadPoolExecutor.Worker)var6.next();
                ncompleted += w.completedTasks;
                if (w.isLocked()) {
                    ++nactive;
                }
            }
        } finally {
            mainLock.unlock();
        }

        int c = this.ctl.get();
        String runState = isRunning(c) ? "Running" : (runStateAtLeast(c, 1610612736) ? "Terminated" : "Shutting down");
        return super.toString() + "[" + runState + ", pool size = " + nworkers + ", active threads = " + nactive + ", queued tasks = " + this.workQueue.size() + ", completed tasks = " + ncompleted + "]";
    }

    protected void beforeExecute(Thread t, Runnable r) {
    }

    protected void afterExecute(Runnable r, Throwable t) {
    }

    protected void terminated() {
    }

    public static class DiscardOldestPolicy implements RejectedExecutionHandler {
        public DiscardOldestPolicy() {
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                e.getQueue().poll();
                e.execute(r);
            }

        }
    }

    public static class DiscardPolicy implements RejectedExecutionHandler {
        public DiscardPolicy() {
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        }
    }

    public static class AbortPolicy implements RejectedExecutionHandler {
        public AbortPolicy() {
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new RejectedExecutionException("Task " + r.toString() + " rejected from " + e.toString());
        }
    }

    public static class CallerRunsPolicy implements RejectedExecutionHandler {
        public CallerRunsPolicy() {
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                r.run();
            }

        }
    }

    private final class Worker extends AbstractQueuedSynchronizer implements Runnable {
        private static final long serialVersionUID = 6138294804551838833L;
        final Thread thread;
        Runnable firstTask;
        volatile long completedTasks;

        Worker(Runnable firstTask) {
            this.setState(-1);
            this.firstTask = firstTask;
            this.thread = ThreadPoolExecutor.this.getThreadFactory().newThread(this);
        }

        public void run() {
            ThreadPoolExecutor.this.runWorker(this);
        }

        protected boolean isHeldExclusively() {
            return this.getState() != 0;
        }

        protected boolean tryAcquire(int unused) {
            if (this.compareAndSetState(0, 1)) {
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            } else {
                return false;
            }
        }

        protected boolean tryRelease(int unused) {
            this.setExclusiveOwnerThread((Thread)null);
            this.setState(0);
            return true;
        }

        public void lock() {
            this.acquire(1);
        }

        public boolean tryLock() {
            return this.tryAcquire(1);
        }

        public void unlock() {
            this.release(1);
        }

        public boolean isLocked() {
            return this.isHeldExclusively();
        }

        void interruptIfStarted() {
            Thread t;
            if (this.getState() >= 0 && (t = this.thread) != null && !t.isInterrupted()) {
                try {
                    t.interrupt();
                } catch (SecurityException var3) {
                }
            }

        }
    }
}
