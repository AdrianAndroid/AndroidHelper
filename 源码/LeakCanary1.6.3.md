https://www.dazhuanlan.com/2020/03/19/5e72d531565b3/
https://blog.csdn.net/aptentity/article/details/71308257
LeakCanary.install
    refWatcher - new AndroidRefWatcherBuilder(conext)
    DisplayLeakService.class
    excludedRefs(DisplayLeakService.class)
    buildAndInstall	

​		build()

​		ActivityRefWatcher.install(context, refWatcher)(监听Activity	)

​		FragmentRefWatcher.Helper.install(context, refWatcher) (监听Fragment)





# RefWatcherBuilder#build()

```java
/** Creates a {@link RefWatcher}. */
  public final RefWatcher build() {
    if (isDisabled()) {
      return RefWatcher.DISABLED;
    }

    if (heapDumpBuilder.excludedRefs == null) {
      heapDumpBuilder.excludedRefs(defaultExcludedRefs());
    }

    HeapDump.Listener heapDumpListener = this.heapDumpListener;
    if (heapDumpListener == null) {
      heapDumpListener = defaultHeapDumpListener();
    }

    DebuggerControl debuggerControl = this.debuggerControl;
    if (debuggerControl == null) {
      debuggerControl = defaultDebuggerControl();
    }

    HeapDumper heapDumper = this.heapDumper;
    if (heapDumper == null) {
      heapDumper = defaultHeapDumper();
    }

    WatchExecutor watchExecutor = this.watchExecutor;
    if (watchExecutor == null) {
      watchExecutor = defaultWatchExecutor();
    }

    GcTrigger gcTrigger = this.gcTrigger;
    if (gcTrigger == null) {
      gcTrigger = defaultGcTrigger();
    }

    if (heapDumpBuilder.reachabilityInspectorClasses == null) {
      heapDumpBuilder.reachabilityInspectorClasses(defaultReachabilityInspectorClasses());
    }

    return new RefWatcher(watchExecutor, debuggerControl, gcTrigger, heapDumper, heapDumpListener,
        heapDumpBuilder);
  }
```





# 打印了所有的方法

调用方法	

I: doBefore --> LeakCanary.isInAnalyzerProcess(..)
I: doBefore --> LeakCanary.install(..)
I: doBefore --> LeakCanary.refWatcher(..)
I: doBefore --> AndroidRefWatcherBuilder.listenerServiceClass(..)
I: doBefore --> RefWatcherBuilder.heapDumpListener(..)
I: doBefore --> AndroidExcludedRefs.createAppDefaults()
I: doBefore --> AndroidExcludedRefs.values()
I: doBefore --> AndroidExcludedRefs.createBuilder(..)
I: doBefore --> ExcludedRefs.builder()
I: doBefore[p] --> ExcludedRefs.unmodifiableRefStringMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefStringMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore --> RefWatcherBuilder.excludedRefs(..)
I: doBefore --> AndroidRefWatcherBuilder.buildAndInstall()
I: doBefore --> RefWatcherBuilder.build()
I: doBefore --> LeakCanary.isInAnalyzerProcess(..)
I: doBefore --> AndroidReachabilityInspectors.defaultAndroidInspectors()
I: doBefore --> AndroidReachabilityInspectors.values()
I: doBefore --> RefWatcherBuilder.build()
I: doBefore --> ExcludedRefs.builder()
I: doBefore[p] --> ExcludedRefs.unmodifiableRefStringMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefStringMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore --> ActivityRefWatcher.install(..)



## 退了出来

I: doBefore --> RefWatcher.watch(..)
I: doBefore --> RefWatcher.watch(..)
I: doBefore[p] --> RefWatcher.ensureGoneAsync(..)
I: doBefore --> AndroidWatchExecutor.execute(..)
I: doBefore[p] --> AndroidWatchExecutor.waitForIdle(..)
I: doBefore --> RefWatcher.watch(..)
I: doBefore --> RefWatcher.watch(..)
I: doBefore[p] --> RefWatcher.ensureGoneAsync(..)
I: doBefore --> AndroidWatchExecutor.execute(..)
I: doBefore[p] --> AndroidWatchExecutor.waitForIdle(..)
I: doBefore[p] --> AndroidWatchExecutor.postToBackgroundWithDelay(..)
I: doBefore[p] --> AndroidWatchExecutor.postToBackgroundWithDelay(..)

I: doBefore --> RefWatcher.1.run()
I: doBefore[p] --> RefWatcher.removeWeaklyReachableReferences()
I: doBefore --> AndroidDebuggerControl.isDebuggerAttached()
I: doBefore[p] --> RefWatcher.gone(..)
I: doBefore --> GcTrigger.1.runGc()
I: doBefore[p] --> RefWatcher.removeWeaklyReachableReferences()
I: doBefore[p] --> RefWatcher.gone(..)
I: doBefore --> AndroidHeapDumper.dumpHeap()
I: doBefore --> DefaultLeakDirectoryProvider.newHeapDumpFile()
I: doBefore --> DefaultLeakDirectoryProvider.listFiles(..)
I: doBefore[p] --> DefaultLeakDirectoryProvider.hasStoragePermission()
I: doBefore[p] --> DefaultLeakDirectoryProvider.requestWritePermissionNotification()
I: doBefore[p] --> DefaultLeakDirectoryProvider.externalStorageDirectory()
I: doBefore[p] --> DefaultLeakDirectoryProvider.appStorageDirectory()
I: doBefore --> CanaryLog.d(..)
I: doBefore[p] --> AndroidWatchExecutor.postWaitForIdle(..)
I: doBefore --> RefWatcher.1.run()
I: doBefore[p] --> RefWatcher.removeWeaklyReachableReferences()
I: doBefore --> AndroidDebuggerControl.isDebuggerAttached()
I: doBefore[p] --> RefWatcher.gone(..)
I: doBefore --> GcTrigger.1.runGc()
I: doBefore[p] --> AndroidWatchExecutor.waitForIdle(..)
I: doBefore[p] --> AndroidWatchExecutor.postToBackgroundWithDelay(..)
I: doBefore[p] --> RefWatcher.removeWeaklyReachableReferences()
I: doBefore[p] --> RefWatcher.gone(..)
I: doBefore --> AndroidHeapDumper.dumpHeap()
I: doBefore --> DefaultLeakDirectoryProvider.newHeapDumpFile()
I: doBefore --> DefaultLeakDirectoryProvider.listFiles(..)
I: doBefore[p] --> DefaultLeakDirectoryProvider.hasStoragePermission()
I: doBefore[p] --> DefaultLeakDirectoryProvider.requestWritePermissionNotification()
I: doBefore[p] --> DefaultLeakDirectoryProvider.externalStorageDirectory()
I: doBefore[p] --> DefaultLeakDirectoryProvider.appStorageDirectory()
I: doBefore --> CanaryLog.d(..)
I: doBefore[p] --> AndroidWatchExecutor.postWaitForIdle(..)
I: doBefore[p] --> AndroidWatchExecutor.waitForIdle(..)
I: doBefore[p] --> AndroidWatchExecutor.postToBackgroundWithDelay(..)





# [*]打印了所有的方法

调用方法	

I: doBefore --> LeakCanary.isInAnalyzerProcess(..)

​	

I: doBefore --> LeakCanary.install(..)
I: doBefore --> LeakCanary.refWatcher(..)

​	new AndroidRefWatcherBuilder(context)

I: doBefore --> AndroidRefWatcherBuilder.listenerServiceClass(..)
I: doBefore --> RefWatcherBuilder.heapDumpListener(..)
I: doBefore --> AndroidExcludedRefs.createAppDefaults()
I: doBefore --> AndroidExcludedRefs.values()
I: doBefore --> AndroidExcludedRefs.createBuilder(..)
I: doBefore --> ExcludedRefs.builder()
I: doBefore[p] --> ExcludedRefs.unmodifiableRefStringMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefStringMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore --> RefWatcherBuilder.excludedRefs(..)
I: doBefore --> AndroidRefWatcherBuilder.buildAndInstall()
I: doBefore --> RefWatcherBuilder.build()
I: doBefore --> LeakCanary.isInAnalyzerProcess(..)
I: doBefore --> AndroidReachabilityInspectors.defaultAndroidInspectors()
I: doBefore --> AndroidReachabilityInspectors.values()
I: doBefore --> RefWatcherBuilder.build()
I: doBefore --> ExcludedRefs.builder()
I: doBefore[p] --> ExcludedRefs.unmodifiableRefStringMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefStringMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore[p] --> ExcludedRefs.unmodifiableRefMap(..)
I: doBefore --> ActivityRefWatcher.install(..)



## [*]退了出来

I: doBefore --> RefWatcher.watch(..)
I: doBefore --> RefWatcher.watch(..)
I: doBefore[p] --> RefWatcher.ensureGoneAsync(..)
I: doBefore --> AndroidWatchExecutor.execute(..)
I: doBefore[p] --> AndroidWatchExecutor.waitForIdle(..)
I: doBefore --> RefWatcher.watch(..)
I: doBefore --> RefWatcher.watch(..)
I: doBefore[p] --> RefWatcher.ensureGoneAsync(..)
I: doBefore --> AndroidWatchExecutor.execute(..)
I: doBefore[p] --> AndroidWatchExecutor.waitForIdle(..)
I: doBefore[p] --> AndroidWatchExecutor.postToBackgroundWithDelay(..)
I: doBefore[p] --> AndroidWatchExecutor.postToBackgroundWithDelay(..)

I: doBefore --> RefWatcher.1.run()
I: doBefore[p] --> RefWatcher.removeWeaklyReachableReferences()
I: doBefore --> AndroidDebuggerControl.isDebuggerAttached()
I: doBefore[p] --> RefWatcher.gone(..)
I: doBefore --> GcTrigger.1.runGc()
I: doBefore[p] --> RefWatcher.removeWeaklyReachableReferences()
I: doBefore[p] --> RefWatcher.gone(..)
I: doBefore --> AndroidHeapDumper.dumpHeap()
I: doBefore --> DefaultLeakDirectoryProvider.newHeapDumpFile()
I: doBefore --> DefaultLeakDirectoryProvider.listFiles(..)
I: doBefore[p] --> DefaultLeakDirectoryProvider.hasStoragePermission()
I: doBefore[p] --> DefaultLeakDirectoryProvider.requestWritePermissionNotification()
I: doBefore[p] --> DefaultLeakDirectoryProvider.externalStorageDirectory()
I: doBefore[p] --> DefaultLeakDirectoryProvider.appStorageDirectory()
I: doBefore --> CanaryLog.d(..)
I: doBefore[p] --> AndroidWatchExecutor.postWaitForIdle(..)
I: doBefore --> RefWatcher.1.run()
I: doBefore[p] --> RefWatcher.removeWeaklyReachableReferences()
I: doBefore --> AndroidDebuggerControl.isDebuggerAttached()
I: doBefore[p] --> RefWatcher.gone(..)
I: doBefore --> GcTrigger.1.runGc()
I: doBefore[p] --> AndroidWatchExecutor.waitForIdle(..)
I: doBefore[p] --> AndroidWatchExecutor.postToBackgroundWithDelay(..)
I: doBefore[p] --> RefWatcher.removeWeaklyReachableReferences()
I: doBefore[p] --> RefWatcher.gone(..)
I: doBefore --> AndroidHeapDumper.dumpHeap()
I: doBefore --> DefaultLeakDirectoryProvider.newHeapDumpFile()
I: doBefore --> DefaultLeakDirectoryProvider.listFiles(..)
I: doBefore[p] --> DefaultLeakDirectoryProvider.hasStoragePermission()
I: doBefore[p] --> DefaultLeakDirectoryProvider.requestWritePermissionNotification()
I: doBefore[p] --> DefaultLeakDirectoryProvider.externalStorageDirectory()
I: doBefore[p] --> DefaultLeakDirectoryProvider.appStorageDirectory()
I: doBefore --> CanaryLog.d(..)
I: doBefore[p] --> AndroidWatchExecutor.postWaitForIdle(..)
I: doBefore[p] --> AndroidWatchExecutor.waitForIdle(..)
I: doBefore[p] --> AndroidWatchExecutor.postToBackgroundWithDelay(..)





# RefWatcher#GC

```java
public final class RefWatcher {
	Retryable.Result ensureGone(final KeyedWeakReference reference, final long watchStartNanoTime) {
		long gcStartNanoTime = System.nanoTime();
		long watchDurationMs = NANOSECONDS.toMillis(gcStartNanoTime - watchStartNanoTime);

		// 将GC掉的对象从内存泄漏的怀疑名单中移除
		removeWeaklyReachableReference();
	
		if (debuggerControl.isDebuggerAttached()) {
			// The debugger can create false leaks.
			return RETRY;
		}
		// 如果名单没有内存泄漏的引用对象
		// 说明在某次GC已经回收对象，没有内存泄漏，不需要处理
		if (gone(reference)) {
			return DONE;
		}
		// 执行一次GC
		gcTrigger.runGc();

		// 再检查引用对象是否被回收
		removeWeaklyReachableReferences();
		// 引用对象没有被回收还在怀疑名单，说明已经内存泄漏，dump
		if (!gone(reference)) {
			long startDumpHeap = System.nanoTime();
			long gcDurationMs = NANOSECONDS.toMills(startDumpHeap - gcStartNanoTime);

			// 创建dump文件，创建通知提示dump
			File heapDumpFile = heapDumper.dumpHeap();
			// dump文件创建失败，重试
			if (heapDumpFile == RETRY_LATER) {
				// Could not dump the heap.
				return RETRY;
			}
			long heapDumpDurationMs = NANOSECONDS.toMills(System.nanoTime() - startDumpHeap);

      // 生成hprof吧
			HeapDump heapDump = heapDumpBuilder.heapDumpFile(heapDumpFile).referencekey(reference.key)
				.referenceName(reference.name)
				.watchDurationMs(watchDurationMs)
				.gcDurationMs(gcDurationMs)
				.heapDumpDurationMs(heapDumpDurationMs)
				.build();

			// 分析dump文件
			heapdumpListener.analyze(heapDump);
		}
		return DONE;
	}

	private boolean gone(KeyedWeakReference reference) {
		return !retainedKeys.contains(reference.key);
	}

	private void removeWeaklyReachableReference() {
		// WeakReferences are enqueued as soon as the object to which they point to be becomes weakly
		// reachable. This is before finalization or garbage collection has actually happened.
		KeyedWeakReference ref;
		while ((ref = (KeyedWeakReference) queue.poll()) != null) {
			retainedKeys.remove(ref.key);
		}
	}
}

```





# AndroidHeapDumper

```java
@Override @Nullable
  public File dumpHeap() {
    File heapDumpFile = leakDirectoryProvider.newHeapDumpFile();

    if (heapDumpFile == RETRY_LATER) {
      return RETRY_LATER;
    }

    FutureResult<Toast> waitingForToast = new FutureResult<>();
    showToast(waitingForToast);

    if (!waitingForToast.wait(5, SECONDS)) {
      CanaryLog.d("Did not dump heap, too much time waiting for Toast.");
      return RETRY_LATER;
    }

    Notification.Builder builder = new Notification.Builder(context)
        .setContentTitle(context.getString(R.string.leak_canary_notification_dumping));
    Notification notification = LeakCanaryInternals.buildNotification(context, builder);
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    int notificationId = (int) SystemClock.uptimeMillis();
    notificationManager.notify(notificationId, notification);

    Toast toast = waitingForToast.get();
    try {
      Debug.dumpHprofData(heapDumpFile.getAbsolutePath());
      cancelToast(toast);
      notificationManager.cancel(notificationId);
      return heapDumpFile;
    } catch (Exception e) {
      CanaryLog.d(e, "Could not dump heap");
      // Abort heap dump
      return RETRY_LATER;
    }
  }
```





# HeapAnalyzerService#runAnalysis

```java
  public static void runAnalysis(Context context, HeapDump heapDump,
      Class<? extends AbstractAnalysisResultService> listenerServiceClass) {
    setEnabledBlocking(context, HeapAnalyzerService.class, true);
    setEnabledBlocking(context, listenerServiceClass, true);
    Intent intent = new Intent(context, HeapAnalyzerService.class);
    intent.putExtra(LISTENER_CLASS_EXTRA, listenerServiceClass.getName());
    intent.putExtra(HEAPDUMP_EXTRA, heapDump);
    ContextCompat.startForegroundService(context, intent);
  }
```



# HeapAnalyzerService

```java
/**
 * This service runs in a separate process to avoid slowing down the app process or making it run
 * out of memory.
 */
public final class HeapAnalyzerService extends ForegroundService
    implements AnalyzerProgressListener {

  private static final String LISTENER_CLASS_EXTRA = "listener_class_extra";
  private static final String HEAPDUMP_EXTRA = "heapdump_extra";

  public static void runAnalysis(Context context, HeapDump heapDump,
      Class<? extends AbstractAnalysisResultService> listenerServiceClass) {
    setEnabledBlocking(context, HeapAnalyzerService.class, true);
    setEnabledBlocking(context, listenerServiceClass, true);
    Intent intent = new Intent(context, HeapAnalyzerService.class);
    intent.putExtra(LISTENER_CLASS_EXTRA, listenerServiceClass.getName());
    intent.putExtra(HEAPDUMP_EXTRA, heapDump);
    ContextCompat.startForegroundService(context, intent);
  }

  public HeapAnalyzerService() {
    super(HeapAnalyzerService.class.getSimpleName(), R.string.leak_canary_notification_analysing);
  }

  @Override protected void onHandleIntentInForeground(@Nullable Intent intent) {
    if (intent == null) {
      CanaryLog.d("HeapAnalyzerService received a null intent, ignoring.");
      return;
    }
    String listenerClassName = intent.getStringExtra(LISTENER_CLASS_EXTRA);
    // 获取到HeapDump
    HeapDump heapDump = (HeapDump) intent.getSerializableExtra(HEAPDUMP_EXTRA);

    HeapAnalyzer heapAnalyzer =
        new HeapAnalyzer(heapDump.excludedRefs, this, heapDump.reachabilityInspectorClasses);

    AnalysisResult result = heapAnalyzer.checkForLeak(heapDump.heapDumpFile, heapDump.referenceKey,
        heapDump.computeRetainedHeapSize);
    AbstractAnalysisResultService.sendResultToListener(this, listenerClassName, heapDump, result);
  }

  @Override public void onProgressUpdate(Step step) {
    int percent = (int) ((100f * step.ordinal()) / Step.values().length);
    CanaryLog.d("Analysis in progress, working on: %s", step.name());
    String lowercase = step.name().replace("_", " ").toLowerCase();
    String message = lowercase.substring(0, 1).toUpperCase() + lowercase.substring(1);
    showForegroundNotification(100, percent, false, message);
  }
}
```





# 享学例子

## KeyWeakReference

```java
/**
 * 继承自WeakReference，并且加入一个key，用来通过可以key可以查找到对应的KeyWeakReference
 * @param <T>
 */
public class KeyWeakReference<T> extends WeakReference<T> {

    private String key;
    private String name;

    public KeyWeakReference(T referent, String key, String name) {
        super(referent);
        this.key = key;
        this.name = name;
    }

    public KeyWeakReference(T referent, ReferenceQueue<? super T> q, String key, String name) {
        super(referent, q);
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("KeyWeakReference{");
        sb.append("key='").append(key).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

```



## LeakCanaryTest

```java
public class leakcanaryTest {

    public static void main(String[] args) {

        Watcher watcher = new Watcher();

        Object obj = new Object();
        System.out.println("obj: " + obj);
        watcher.watch(obj,"");
        Utils.sleep(500);
        //释放对象
        obj = null;
        Utils.gc();
        //TODO: 思考如何判断被观察的对象可能存在泄漏嫌疑

        Utils.sleep(5000);
        System.out.println("查看是否在怀疑列表：" + watcher.getRetainedReferences().size());
    }

}
```



## Utils

```java
public class Utils {
    public static void sleep(long millis){
        System.out.println("sleep: " + millis);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void gc(){
        System.out.println("执行gc...");
        //主要这里不是使用System.gc,因为它仅仅是通知系统在合适的时间进行一次垃圾回收操作
        //实际上并不保证一定执行
        Runtime.getRuntime().gc();
        sleep(100);
        System.runFinalization();
    }
}
```



## Watcher

```java
public class Watcher {


    //观察列表
    private HashMap<String, KeyWeakReference> watchedReferences = new HashMap<>();
    //怀疑列表
    private HashMap<String, KeyWeakReference> retainedReferences = new HashMap<>();

    //引用队列，相当于一个监视器设备，所有需要监视的对象，盛放监视对象的容器 都与之关联
    //当被监视的对象被gc回收后，对应的容器就会被加入到queue
    private ReferenceQueue queue = new ReferenceQueue();

    public Watcher() {
    }

    /**
     * 清理观察列表和怀疑列表的引用容器
     */
    private void removeWeaklyReachableReferences() {
        System.out.println("清理列表...");
        KeyWeakReference findRef = null;

        do {
            findRef = (KeyWeakReference) queue.poll();
            //不为空说明对应的对象被gc回收了，那么可以把对应的容器从观察列表，怀疑列表移除
            System.out.println("findRef = " + findRef);
            if (findRef != null) {
                System.out.println("打印对应的对象的key: " + findRef.getKey());
                //根据key把观察列表中对应的容器移除
                Reference removedRef = watchedReferences.remove(findRef.getKey());
                //如果removedRef为空，那么有可能被放入到怀疑列表了
                //TODO: 思考什么情况下会出现这么现象？
                //那么尝试从怀疑列表中移除
                if (removedRef == null) {
                    retainedReferences.remove(findRef.getKey());
                }
            }
        } while (findRef != null);//把所有放到referenceQueue的引用容器找出来
    }

    /**
     * 根据key把对应的容器加入到怀疑列表
     *
     * @param key
     */
    private synchronized void moveToRetained(String key) {
        System.out.println("加入到怀疑列表...");
        //在加入怀疑列表之前，做一次清理工作
        removeWeaklyReachableReferences();
        //根据key从观察列表中去找盛放对象的容器，如果被找到，说明到目前为止key对应的对象还没被是否
        KeyWeakReference retainedRef = watchedReferences.remove(key);
        if (retainedRef != null) {
            //把从观察列表中移除出来的对象加入到怀疑列表
            retainedReferences.put(key, retainedRef);
        }
    }


    public void watch(Object watchedReference, String referenceName) {
        System.out.println("开始watch对象...");
        //1. 在没有被监视之前，先清理下观察列表和怀疑列表
        removeWeaklyReachableReferences();

        //2. 为要监视的对象生成一个唯一的uuid
        //相当于把要监视的对象 和容器 与 引用队列建立联系
        final String key = UUID.randomUUID().toString();
        System.out.println("待监视对象的key: " + key);
        //3. 让watchedReference 与一个KeyWeakReference建立一对一映射关系，并与引用队列queue关联
        KeyWeakReference reference = new KeyWeakReference(watchedReference, queue, key, "");

        //4. 加入到观察列表
        watchedReferences.put(key, reference);

        //5.过5秒后去看是否还在观察列表，如果还在，则加入到怀疑列表
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Utils.sleep(5000);
            moveToRetained(key);
        });

    }

    public HashMap<String, KeyWeakReference> getRetainedReferences() {
        retainedReferences.forEach((key, keyWeakReference) -> {
                    System.out.println("key: " + key + " , obj: " + keyWeakReference.get() + " , keyWeakReference: " + keyWeakReference);
                }
        );
        return retainedReferences;
    }
}

```

