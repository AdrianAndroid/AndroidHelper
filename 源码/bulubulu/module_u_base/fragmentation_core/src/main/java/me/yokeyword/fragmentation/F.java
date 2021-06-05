package me.yokeyword.fragmentation;

import android.util.Log;

/**
 * flannery
 */
public class F {
    public static void p(Object... o) {
        printLogString(null, o);
    }

    // 打印字符串
    public static void printLogString(StackTraceElement stackTraceElement, Object... o) {
        StringBuilder sb = new StringBuilder();
        if (stackTraceElement != null) {
            String fileName = stackTraceElement.getFileName();
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            int lineNumber = stackTraceElement.getLineNumber();

            sb.append(methodName).append("(").append(fileName).append(":").append(lineNumber).append(")");
        }
        for (Object value : o) {
            sb.append(value).append(",");
        }
        //Log.i(fileName, methodName+"("+fileName+":"+lineNumber+")");
        Log.i("Fragmentation", sb.toString());
    }


    /**
     * 自己提供类名
     */
    public static void m(Class<?> aClass, Object... o) {
        if (BuildConfig.DEBUG && aClass != null) {
            StackTraceElement ste = findClassStackTraceElement(aClass);
            if (ste == null) return;
            printLogString(ste, o);
        }
    }


    // 找到当前类的StackTraceElement
    public static StackTraceElement findClassStackTraceElement(Class<?> aClass) {
        if (BuildConfig.DEBUG)
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (stackTraceElement.getClassName().startsWith(aClass.getCanonicalName())) {
                    return stackTraceElement;
                }
            }
        return null;
    }

}
