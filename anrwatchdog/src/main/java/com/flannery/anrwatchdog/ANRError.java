package com.flannery.anrwatchdog;

import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ANRError extends Error {

    private static class $ implements Serializable {
        private final String _name;
        private final StackTraceElement[] _stackTrace;

        public $(String _name, StackTraceElement[] _stackTrace) {
            this._name = _name;
            this._stackTrace = _stackTrace;
        }

        private class _Thread extends Throwable {
            private _Thread(_Thread other) {
                super(_name, other);
            }

            @NonNull
            @Override
            public synchronized Throwable fillInStackTrace() {
                setStackTrace(_stackTrace);
                return this;
            }
        }
    }

    private static final long serailVersionUID = 1L;

    /**
     * The minimum duration, in ms, for which the main thread has been blocked. May be more.
     */
    public final long duration;

    private ANRError($._Thread st, long duration) {
        super("Application No Responding for at least " + duration + " ms.", st);
        this.duration = duration;
    }

    @NonNull
    @Override
    public synchronized Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[]{});
        return this;
    }

    static ANRError New(long duration, String prefix, boolean logThreadWithoutStackTrace) {
        final Thread mainThread = Looper.getMainLooper().getThread();

        final Map<Thread, StackTraceElement[]> stackTraces
                = new TreeMap<>(new Comparator<Thread>() {
            @Override
            public int compare(Thread lhs, Thread rhs) {
                if (lhs == rhs) return 0;
                if (lhs == mainThread) return 1;
                if (rhs == mainThread) return -1;
                return rhs.getName().compareTo(lhs.getName());
            }
        });

        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
            if (
                    entry.getKey().getName().startsWith(prefix)
                            && (logThreadWithoutStackTrace || entry.getValue().length > 0)
            ) {
                stackTraces.put(entry.getKey(), entry.getValue());
            }
        }

        // Somethimes main not returned in getAllStackTraces() - ensure that we list it
        if (!stackTraces.containsKey(mainThread)) {
            stackTraces.put(mainThread, mainThread.getStackTrace());
        }

        $._Thread tst = null;
        for (Map.Entry<Thread, StackTraceElement[]> entry : stackTraces.entrySet()) {
            tst = new $(getThreadTitle(entry.getKey()), entry.getValue()).new _Thread(tst);
        }

        return new ANRError(tst, duration);
    }

    static ANRError NewMainOnly(long duration) {
        final Thread mainThread = Looper.getMainLooper().getThread();
        final StackTraceElement[] mainStackTrace = mainThread.getStackTrace();

        return new ANRError(new $(getThreadTitle(mainThread), mainStackTrace).new _Thread(null), duration);
    }

    private static String getThreadTitle(Thread thread) {
        return thread.getName() + " (state = " + thread.getState() + ")";
    }

}
