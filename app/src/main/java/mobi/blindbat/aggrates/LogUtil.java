package mobi.blindbat.aggrates;

import android.util.Log;

/**
 * Created by Kuan on 06.04.2016.
 */
public final class LogUtil {
    private static final String TAG = "AggRates";

    public static void v(String msg) {
        android.util.Log.v(TAG, getLocation() + msg);
    }

    private static String getLocation() {
        final String className = Log.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        boolean found = false;

        for (int i = 0; i < traces.length; i++) {
            StackTraceElement trace = traces[i];

            try {
                if (found) {
                    if (!trace.getClassName().startsWith(className)) {
                        Class<?> clazz = Class.forName(trace.getClassName());
                        return "[" + getClassName(clazz) + ":" + trace.getMethodName() + ":" + trace.getLineNumber() + "]: ";
                    }
                }
                else if (trace.getClassName().startsWith(className)) {
                    found = true;
                    continue;
                }
            }
            catch (ClassNotFoundException e) {
            }
        }

        return "[]: ";
    }

    private static String getClassName(Class<?> clazz) {
        if (clazz != null) {
            if (clazz.getSimpleName().isEmpty()) {
                return clazz.getSimpleName();
            }
            return getClassName(clazz.getEnclosingClass());
        }
        return "";
    }
}
