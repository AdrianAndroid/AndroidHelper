package com.joyy.dispatcher;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.UiThread;

import com.joyy.dispatcher.sort.TaskSortUtil;
import com.joyy.dispatcher.stat.TaskStat;
import com.joyy.dispatcher.utils.DLog;
import com.joyy.dispatcher.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Time:2021/9/7 13:47
 * Author: flannery
 * Description: 启动器调用类
 */
public class TaskDispatcher {
    private long mStartTime;
    private static final int WAITTIME = 10000;
    private static Context sContext;
    private static Application mApp;
    private static boolean sIsMainProcess;
    private List<Future> mFutures = new ArrayList<>();
    private static volatile boolean sHasInit;
    private List<Task> mAllTasks = new ArrayList<>();
    private List<Class<? extends Task>> mClsAllTasks = new ArrayList<>();
    private volatile List<Task> mMainThreadTasks = new ArrayList<>();
    private CountDownLatch mCountDownLatch;

    /**
     * 需要等待的任务数
     */
    private AtomicInteger mNeedWaitCount = new AtomicInteger();//

    /**
     * 调用了 await 还没结束且需要等待的任务列表
     */
    private List<Task> mNeedWaitTasks = new ArrayList<>();

    /**
     * 已经结束的Task
     */
    private volatile List<Class<? extends Task>> mFinishedTasks = new ArrayList<>(100);//

    private HashMap<Class<? extends Task>, ArrayList<Task>> mDependedHashMap = new HashMap<>();

    /**
     * 启动器分析的次数，统计下分析的耗时；
     */
    private AtomicInteger mAnalyseCount = new AtomicInteger();

    private TaskDispatcher() {
    }

    public static void init(Application context) {
        if (context != null) {
            sContext = context;
            mApp = context;
            sHasInit = true;
            sIsMainProcess = Utils.isMainProcess(sContext);
        }
    }

    /**
     * 注意：每次获取的都是新对象
     *
     * @return
     */
    public static TaskDispatcher createInstance() {
        if (!sHasInit) {
            throw new RuntimeException("must call TaskDispatcher.init first");
        }
        return new TaskDispatcher(); // 是不是不符合逃逸分析
    }

    public TaskDispatcher addTask(Task task) {
        if (task != null) {
            collectDepends(task);
            mAllTasks.add(task);
            mClsAllTasks.add(task.getClass());
            // 非主线程且需要wait的，主线程不需要CountDownLatch也是同步的
            if (ifNeedWait(task)) {
                mNeedWaitTasks.add(task);
                mNeedWaitCount.getAndIncrement();
            }
        }
        return this;
    }

    private void collectDepends(Task task) {
        if (task.dependsOn() != null && task.dependsOn().size() > 0) {
            for (Class<? extends Task> cls : task.dependsOn()) {
                if (mDependedHashMap.get(cls) == null) {
                    mDependedHashMap.put(cls, new ArrayList<Task>());
                }
                mDependedHashMap.get(cls).add(task);
                if (mFinishedTasks.contains(cls)) {
                    task.satisfy();
                }
            }
        }
    }

    private boolean ifNeedWait(Task task) {
        return !task.runOnMainThread() && task.needWait();
    }

    @UiThread
    public void start() {
        mStartTime = System.currentTimeMillis();
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("must be called from UiThread");
        }
        if (mAllTasks.size() > 0) {
            mAnalyseCount.getAndIncrement();
            printDependedMsg();
            mAllTasks = TaskSortUtil.getSortResult(mAllTasks, mClsAllTasks);
            mCountDownLatch = new CountDownLatch(mNeedWaitCount.get());

            sendAndExecuteAsyncTasks();

            DLog.i("task analyse cost " + (System.currentTimeMillis() - mStartTime) + "  begin main ");
            executeTaskMain();
        }
        DLog.i("task analyse cost startTime cost " + (System.currentTimeMillis() - mStartTime));
    }

    public void cancel() {
        for (Future future : mFutures) {
            future.cancel(true);
        }
    }

    private void executeTaskMain() {
        mStartTime = System.currentTimeMillis();
        for (Task task : mMainThreadTasks) {
            long time = System.currentTimeMillis();
            new DispatchRunnable(task, this).run();
            DLog.i("real main " + task.getClass().getSimpleName() + " cost   " +
                    (System.currentTimeMillis() - time));
        }
        DLog.i("maintask cost " + (System.currentTimeMillis() - mStartTime));
    }

    /**
     * 发送去并且执行异步任务
     */
    private void sendAndExecuteAsyncTasks() {
        for (Task task : mAllTasks) {
            if (task.onlyInMainProcess() && !sIsMainProcess) {
                markTaskDone(task);
            } else {
                sendTaskReal(task);
            }
            task.setSend(true);
        }
    }

    /**
     * 查看被依赖的信息
     */
    private void printDependedMsg() {
        DLog.i("needWait size : " + (mNeedWaitCount.get()));
        if (false) {
            for (Class<? extends Task> cls : mDependedHashMap.keySet()) {
                DLog.i("cls " + cls.getSimpleName() + "   " + mDependedHashMap.get(cls).size());
                for (Task task : mDependedHashMap.get(cls)) {
                    DLog.i("cls       " + task.getClass().getSimpleName());
                }
            }
        }
    }

    /**
     * 通知Children一个前置任务已完成
     */
    public void satisfyChildren(Task launchTask) {
        ArrayList<Task> arrayList = mDependedHashMap.get(launchTask.getClass());
        if (arrayList != null && arrayList.size() > 0) {
            for (Task task : arrayList) {
                task.satisfy();
            }
        }
    }

    public void markTaskDone(Task task) {
        if (ifNeedWait(task)) {
            mFinishedTasks.add(task.getClass());
            mNeedWaitTasks.remove(task);
            mCountDownLatch.countDown();
            mNeedWaitCount.getAndDecrement();
        }
    }

    /**
     * 发送任务
     */
    private void sendTaskReal(final Task task) {
        if (task.runOnMainThread()) {
            mMainThreadTasks.add(task);
            if (task.needCall()) {
                task.setTaskCallBack(new TaskCallBack() {
                    @Override
                    public void call() {
                        TaskStat.markTaskDone();
                        task.setFinished(true);
                        satisfyChildren(task);
                        markTaskDone(task);
                        DLog.i(task.getClass().getSimpleName() + " finish");
                        Log.i("testLog", "call");
                    }
                });
            }
        } else {
            // 直接发，是否执行取决于具体线程池
            Future future = task.runOn().submit(new DispatchRunnable(task, this));
            mFutures.add(future);
        }
    }

    public void executeTask(Task task) {
        if (ifNeedWait(task)) {
            mNeedWaitCount.getAndIncrement();
        }
        task.runOn().execute(new DispatchRunnable(task, this));
    }

    @UiThread
    public void await() {
        try {
            if (DLog.isDebug()) {
                DLog.i("still has " + mNeedWaitCount.get());
//                for (Task task : mNeedWaitTasks) {
//                    DispatcherLog.i("needWait: " + task.getClass().getSimpleName());
//                }
            }

            if (mNeedWaitCount.get() > 0) {
                if (mCountDownLatch == null) {
                    throw new RuntimeException("You have to call start() before call await()");
                }
                //使用CountDownLatch实现线程阻塞，保证必须执行的任务先执行完再进入主页
                mCountDownLatch.await(WAITTIME, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
        }
    }

    public static Context getContext() {
        return sContext;
    }

    public static Application getmApp() {
        return mApp;
    }

    public static boolean isMainProcess() {
        return sIsMainProcess;
    }
}
