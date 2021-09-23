package cn.kuwo.base;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Iterator;


// by海平
// 工作于当前线程中带计数的timer，减少线程切换开销，一个timer可以多次start stop，尽量复用，不要new一个扔一个
public final class KwTimer {

    private static final String TAG = "KwTimer";
    public static final int ACCURACY = 50; // 精度， 也就是延时的

    // 使用者实现这个来响应timer,在哪个线程里new，Listener就在哪个线程响应
    public interface Listener {
        void onTimer(final KwTimer timer); // 可以通过这个timer查询tick次数等各种timer状态了
    }

    // Listener中途是允许修改的，下方有接口
    public KwTimer(final Listener l) {
        listener = l;
        runThreadID = Thread.currentThread().getId();
    }

    // 时间间隔单位毫秒，timer精度为 ACCURACY ms。
    public void start(final int intervalMilisecond) {
        start(intervalMilisecond, -1);
    }

    // 执行times次数之后自动停止
    public void start(final int intervalMilisecond, final int times) {
        if (running) {
            return;
        }
        interval = intervalMilisecond;
        startTime = System.currentTimeMillis();
        remainderTimes = times;
        tickTimes = 0;
        TimerHelper.setTimer(this);
    }

    // 手动停止
    public void stop() {
        if (!running) {
            return;
        }
        TimerHelper.stopTimer(this);
    }

    public boolean isRunnig() {
        return running;
    }

    // 修改响应
    public void setListener(final Listener l) {
        listener = l;
    }

    // timer目前响应了多少次
    public int getTickTimes() {
        return tickTimes;
    }

    // 目前还有多少次没有响应，非计次timer返回-1
    public int getRemainderTimes() {
        return remainderTimes;
    }

    // 从start到现在过了多久
    public long getRunningTimeMiliseconds() {
        return System.currentTimeMillis() - startTime;
    }

    // //pirvate
    private long runThreadID = -1;
    private boolean running;
    private Listener listener;
    private int interval;
    private long startTime;
    private int remainderTimes = -1;
    private int tickTimes;

    private void onTimer() {
        if (remainderTimes > 0) {
            --remainderTimes;
            if (remainderTimes == 0) {
                TimerHelper.stopTimer(this);
            }
        }
        ++tickTimes;
        if (listener != null) {
            listener.onTimer(this);
        }
    }

    // 存在线程本地存储里的辅助类，每个线程一个，里面有一个handler发消息实现的计时器和本线程所有timer
    private static class TimerHelper extends Handler {

        public static void setTimer(final KwTimer timer) {
            TimerHelper helper = getThreadTimerHelper();
            helper.add(timer);
        }

        public static void stopTimer(final KwTimer timer) {
            TimerHelper helper = getThreadTimerHelper();
            helper.remove(timer);
        }

        // 根据interval把响应timer放在不同的group(TimingGroup)里
        private void add(final KwTimer timer) {
            timer.running = true;
            TimingItem item = new TimingItem();
            item.timer = timer;
            item.interval = timer.interval;
            item.elapsed = timer.interval;
            item.last = System.currentTimeMillis();
            if (dispathing) {
                preStartTimers.add(item);
            } else {
                allTimers.add(item);
            }
            ++timingNum;
            timingIdle = 0;
            if (!timerMessageRunning) {
                timerMessageRunning = true;
                sendEmptyMessageDelayed(TIMER_ID, ACCURACY);
            }
        }

        private void remove(final KwTimer timer) {
            timer.running = false;
            for (TimingItem t : allTimers) {
                if (t.timer == timer) {
                    t.timer = null;
                    return;
                }
            }
            for (TimingItem t : preStartTimers) {
                if (t.timer == timer) {
                    preStartTimers.remove(t);
                    --timingNum;
                    return;
                }
            }
        }

        @Override
        public void handleMessage(final android.os.Message msg) {
            if (msg.what == TIMER_ID) { // 只接收这一类的消息
                dispatch(); // 进行操作的地方
                // 继续循环
                if (timingNum > 0) {
                    sendEmptyMessageDelayed(TIMER_ID, ACCURACY);
                } else if (timingIdle < 10 * 1000 / ACCURACY) {
                    sendEmptyMessageDelayed(TIMER_ID, ACCURACY);
                    ++timingIdle;
                } else {
                    // 一段时间没有timer需要响应就自动停了吧
                    timerMessageRunning = false;
                    allTimers.clear();
                    threadLocal.remove();
                }
            }
        }

        // 分发timer消息
        private void dispatch() {
            dispathing = true;
            for (Iterator<TimingItem> itemIter = allTimers.iterator(); itemIter.hasNext(); ) {
                TimingItem item = itemIter.next();

                long current = System.currentTimeMillis();
                item.elapsed -= (current - item.last);
                item.last = current;
                if (item.elapsed > ACCURACY / 2) {
                    continue;
                }
                item.elapsed = item.interval;
                if (item.timer != null) {
                    item.timer.onTimer();
                } else {
                    itemIter.remove();
                    --timingNum;
                }
            }
            if (preStartTimers.size() > 0) {
                allTimers.addAll(preStartTimers);
                preStartTimers.clear();
            }
            dispathing = false;
        }

        // 线程本地存储
        private static TimerHelper getThreadTimerHelper() {
            if (threadLocal == null) {
                threadLocal = new ThreadLocal<TimerHelper>(); // 获取每个线程的
            }
            TimerHelper helper = threadLocal.get();
            if (helper == null) {
                helper = new TimerHelper();
                threadLocal.set(helper);
            }
            return helper;
        }

        // 存放个数
        private static class TimingItem {
            public int interval;
            public int elapsed;
            public long last;
            public KwTimer timer; // 本身的对象
        }

        private int timingNum;
        private int timingIdle;
        private boolean timerMessageRunning;
        private boolean dispathing;
        private ArrayList<TimingItem> allTimers = new ArrayList<TimingItem>();
        private ArrayList<TimingItem> preStartTimers = new ArrayList<TimingItem>();
        private static final int TIMER_ID = 1001;
        private static ThreadLocal<TimerHelper> threadLocal;
    }
}
