/**
 * 耗时监视器对象，记录整个过程的耗时情况，可以用在很多需要统计的地方，比如Activity的启动耗时和Fragment的启动耗时。
 */
public class TimeMonitor {

    private final String TAG = TimeMonitor.class.getSimpleName();
    private int mMonitord = -1;

    // 保存一个耗时统计模块的各种耗时，tag对应某一个阶段的时间
    private HashMap<String, Long> mTimeTag = new HashMap<>();
    private long mStartTime = 0;

    public TimeMonitor(int mMonitorId) {
        Log.d(TAG, "init TimeMonitor id: " + mMonitorId);
        this.mMonitorId = mMonitorId;
    }

    public int getMonitorId() {
        return mMonitorId;
    }

    public void startMonitor() {
        // 每次重新启动都把前面的数据清除，避免统计错误的数据
        if (mTimeTag.size() > 0) {
            mTimeTag.clear();
        }
        mStartTime = System.currentTimeMillis();
    }

    /**
     * 每打一次点，记录某个tag的耗时
     */
    public void recordingTimeTag(String tag) {
        // 若保存过相同的tag，先清除
        if (mTimeTag.get(tag) != null) {
            mTimeTag.remove(tag);
        }
        long time = System.currentTimeMillis() - mStartTime;
        Log.d(TAG, tag + ": " + time);
        mTimeTag.put(tag, time);
    }

    public void end(String tag, boolean writeLog) {
        recordingTimeTag(tag);
        end(writeLog);
    }

    public void end(boolean writeLog) {
        if (writeLog) {
            //写入到本地文件
        }
    }

    public HashMap<String, Long> getTimeTags() {
        return mTimeTag;
    }
}

