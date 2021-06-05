package cn.kuwo.common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatUtils {
    private volatile static SimpleDateFormat mFormat = null;

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getFormat() {
        if (mFormat == null) {
            synchronized (DateFormatUtils.class) {
                if (mFormat == null) {
                    mFormat = new SimpleDateFormat();
                    mFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                }
            }
        }
        return mFormat;
    }

    public static final String HHmmss = "HH:mm:ss";
    public static final String HHmm = "HH:mm";
    public static final String MM_dd = "MM月dd日";
    public static final String yyyy_MM_dd = "yyyy年MM月dd日";

    public static final int SECONDS = 1;
    public static final int MINUTES = 60 * SECONDS;
    public static final int HOURS = 60 * MINUTES;
    public static final int DAYS = 24 * HOURS;
    public static final int WEEKS = 7 * DAYS;
    public static final int MONTHS = 4 * WEEKS;
    public static final int YEARS = 12 * MONTHS;

    private static SimpleDateFormat ensureChangePattern(String pattern) {
        if (!TextUtils.isEmpty(pattern)
                && !pattern.equals(getFormat().toPattern())) {
            getFormat().applyPattern(pattern);
        }
        return getFormat();
    }

    /**
     * private val fHM = SimpleDateFormat("HH:mm")
     */
    public static String formatHHmmss(long millis) {
        return ensureChangePattern(HHmmss).format(new Date(millis));
    }

    /**
     * private val fHM = SimpleDateFormat("HH:mm")
     */
    public static String formatHHmm(long millis) {
        return ensureChangePattern(HHmm).format(new Date(millis));
    }

    /**
     * private val fMD = SimpleDateFormat("MM月dd日")
     */
    public static String formatMM_dd(long millis) {
        return ensureChangePattern(MM_dd).format(new Date(millis));
    }

    /**
     * private val fYMD = SimpleDateFormat("yyyy年MM月dd日")
     */
    public static String formatyyyy_MM_dd(long millis) {
        return ensureChangePattern(yyyy_MM_dd).format(new Date(millis));
    }

}
