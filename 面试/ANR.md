# ConententProvider超时

## ContentProviderClient

```java
@SystemApi
    @TestApi
    @RequiresPermission(android.Manifest.permission.REMOVE_TASKS)
    public void setDetectNotResponding(@DurationMillisLong long timeoutMillis) {
        synchronized (ContentProviderClient.class) {
            mAnrTimeout = timeoutMillis;

            if (timeoutMillis > 0) {
                if (mAnrRunnable == null) {
                    mAnrRunnable = new NotRespondingRunnable();
                }
                if (sAnrHandler == null) {
                    sAnrHandler = new Handler(Looper.getMainLooper(), null, true /* async */);
                }

                // If the remote process hangs, we're going to kill it, so we're
                // technically okay doing blocking calls.
                Binder.allowBlocking(mContentProvider.asBinder());
            } else {
                mAnrRunnable = null;

                // If we're no longer watching for hangs, revert back to default
                // blocking behavior.
                Binder.defaultBlocking(mContentProvider.asBinder());
            }
        }
    }
    
    private void beforeRemote() {
        if (mAnrRunnable != null) {
            sAnrHandler.postDelayed(mAnrRunnable, mAnrTimeout);
        }
    }
```



## NotRespondingRunnable

```java
    private class NotRespondingRunnable implements Runnable {
        @Override
        public void run() {
            Log.w(TAG, "Detected provider not responding: " + mContentProvider);
            mContentResolver.appNotRespondingViaProvider(mContentProvider);
        }
    }
```



## ActivityThread

```java
    final void appNotRespondingViaProvider(IBinder provider) {
        synchronized (mProviderMap) {
            ProviderRefCount prc = mProviderRefCountMap.get(provider);
            if (prc != null) {
                try {
                    ActivityManager.getService()
                            .appNotRespondingViaProvider(prc.holder.connection);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }
```



## ActivityManagerService

```java
    @Override
    public void appNotRespondingViaProvider(IBinder connection) {
        enforceCallingPermission(REMOVE_TASKS, "appNotRespondingViaProvider()");

        final ContentProviderConnection conn = (ContentProviderConnection) connection;
        if (conn == null) {
            Slog.w(TAG, "ContentProviderConnection is null");
            return;
        }

        final ProcessRecord host = conn.provider.proc;
        if (host == null) {
            Slog.w(TAG, "Failed to find hosting ProcessRecord");
            return;
        }

        mAnrHelper.appNotResponding(host, "ContentProvider not responding");
    }
```





## AnrHelper

```java
   void appNotResponding(ProcessRecord anrProcess, String activityShortComponentName,
            ApplicationInfo aInfo, String parentShortComponentName,
            WindowProcessController parentProcess, boolean aboveSystem, String annotation) {
        synchronized (mAnrRecords) {
            mAnrRecords.add(new AnrRecord(anrProcess, activityShortComponentName, aInfo,
                    parentShortComponentName, parentProcess, aboveSystem, annotation));
        }
        startAnrConsumerIfNeeded();
    }
    
    
    
```

