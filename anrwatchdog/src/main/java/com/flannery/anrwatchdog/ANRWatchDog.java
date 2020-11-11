package com.flannery.anrwatchdog;

import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class ANRWatchDog extends Thread {
    public interface ANRListener {
        /**
         * Callled when an ANR is detected.
         */
        void onAppNotResponding(ANRError error);
    }

    public interface ANRInterceptor {
        /**
         * Called when main thread has froze more time than defined by the timeout
         *
         * @param duration The minimum time (in ms) the main thread has been frozen (may be more).
         * @return 0 or negative if the ANR should be reported immediately. A positve number of  ms to postpone the reporting.
         */
        long intercept(long duration);
    }

    public interface InterruptionListener {
        void onInterrupted(InterruptedException exception);
    }

    private static final int DEFAULT_ANR_TIMEOUT = 5000;

    private static final ANRListener DEFAULT_ANR_LISTENER = new ANRListener() {
        @Override
        public void onAppNotResponding(ANRError error) {
            throw error;
        }
    };

    private static final ANRInterceptor DEFAULT_ANR_INTERCEPTOR = new ANRInterceptor() {
        @Override
        public long intercept(long duration) {
            return 0;
        }
    };

    private static final InterruptionListener DEFAULT_INTERRUPTION_LISTENER =
            new InterruptionListener() {
                @Override
                public void onInterrupted(InterruptedException exception) {
                    Log.w("ANRWatchDog", "Interrupted: " + exception.getMessage());
                }
            };

    private ANRListener _anrListener = DEFAULT_ANR_LISTENER;
    private ANRInterceptor _anrInterceptor = DEFAULT_ANR_INTERCEPTOR;
    private InterruptionListener _interruptionListener = DEFAULT_INTERRUPTION_LISTENER;

    private final Handler _uiHandler = new Handler(Looper.getMainLooper());
    private final int _timeoutInterval;

    private String _namePrefix = "";
    private boolean _logThreadsWithoutStackTrace = false;
    private boolean _ignoreDebugger = false;

    private volatile long _tick = 0;
    private volatile boolean _reported = false;

    private final Runnable _ticker = new Runnable() {
        @Override
        public void run() {
            _tick = 0;
            _reported = false;
        }
    };

    /**
     * Constucts a watchdog that checks the ui thread every {@value #DEFAULT_ANR_TIMEOUT} millseconds
     */
    public ANRWatchDog() {
        this(DEFAULT_ANR_TIMEOUT);
    }

    /**
     * Constructs a watchdog that checks the ui thread every given interval
     *
     * @param timeoutInterval The interval the WatchDog
     */
    public ANRWatchDog(int timeoutInterval) {
        super();
        _timeoutInterval = timeoutInterval;
    }

    /**
     * @return
     */
    public int getTimeoutInterval() {
        return _timeoutInterval;
    }

    public ANRWatchDog setANRListener(ANRListener listener) {
        if (listener == null) {
            _anrListener = DEFAULT_ANR_LISTENER;
        } else {
            _anrListener = listener;
        }
        return this;
    }

    public ANRWatchDog setANRInterceptor(ANRInterceptor interceptor) {
        if (interceptor == null) {
            _anrInterceptor = DEFAULT_ANR_INTERCEPTOR;
        } else {
            _anrInterceptor = interceptor;
        }
        return this;
    }

    public ANRWatchDog setInterruptionListener(InterruptionListener listener) {
        if (listener == null) {
            _interruptionListener = DEFAULT_INTERRUPTION_LISTENER;
        } else {
            _interruptionListener = listener;
        }
        return this;
    }

    public ANRWatchDog setReportThreadNamePrefix(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        _namePrefix = prefix;
        return this;
    }

    public ANRWatchDog setReportMainThreadOnly() {
        _namePrefix = null;
        return this;
    }

    /**
     * Set that all running threads will be reoported,
     * even those from which no stack tracke could be extracted.
     * Default false;
     *
     * @param logThreadWithoutStackTrace
     * @return
     */
    public ANRWatchDog setLogThreadsWithoutStackTrace(boolean logThreadWithoutStackTrace) {
        _logThreadsWithoutStackTrace = logThreadWithoutStackTrace;
        return this;
    }

    public ANRWatchDog setIgnoreDebugger(boolean ignoreDebugger) {
        _ignoreDebugger = ignoreDebugger;
        return this;
    }


    @Override
    public void run() {
        setName("|ANR-WatchDog|");
        long interval = _timeoutInterval;

        while (!isInterrupted()) {
            boolean needPost = _tick == 0;
            _tick = _tick + interval;
            if (needPost) {
                _uiHandler.post(_ticker);
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                _interruptionListener.onInterrupted(e);
            }

            // If the main thread has not hadnled _ticker, it is blocked. ANR.
            if (_tick != 0 && !_reported) {
                if (!_ignoreDebugger && (Debug.isDebuggerConnected() || Debug.waitingForDebugger())) {
                    Log.w("ANRWatchdog", "An ANR was detected but ignored because the debugger is connected (you can prevent this with setIgnoreDebugger(true))");
                    _reported = true;
                    continue;
                }
            }
            interval = _anrInterceptor.intercept(_tick);
            if (interval > 0) {
                continue;
            }
            final ANRError error;
            if (_namePrefix != null) {
                error = ANRError.New(_tick, _namePrefix, _logThreadsWithoutStackTrace);
            } else {
                error = ANRError.NewMainOnly(_tick);
            }
            _anrListener.onAppNotResponding(error);
            interval = _timeoutInterval;
            _reported = true;
        }


    }
}
