package cn.kuwo.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tencent.mmkv.MMKV;

import java.util.Map;
import java.util.Set;

public class SP {

    private volatile static MMKV mmkv;

    public static MMKV getMMKV() {
        if (mmkv == null) {
            synchronized (SP.class) {
                if (mmkv == null) {
                    mmkv = MMKV.defaultMMKV();
                }
            }
        }
        return mmkv;
    }

    private static boolean isInitialize = false;

    public static void initialize(Context context) {
        if (isInitialize) return;
        String initialize = MMKV.initialize(context);
        isInitialize = true;
        Log.i("SP", initialize);
    }


    public static String cryptKey() {
        return SP.getMMKV().cryptKey();
    }


    public static boolean reKey(String s) {
        return SP.getMMKV().reKey(s);
    }


    public static void checkReSetCryptKey(String s) {
        SP.getMMKV().checkReSetCryptKey(s);
    }


    public static String mmapID() {
        return SP.getMMKV().mmapID();
    }


    public static void lock() {
        SP.getMMKV().lock();
    }


    public static void unlock() {
        SP.getMMKV().unlock();
    }


    public static boolean tryLock() {
        return SP.getMMKV().tryLock();
    }


    public static boolean encode(String key, boolean value) {
        return SP.getMMKV().encode(key, value);
    }


    public static boolean decodeBool(String key) {
        return SP.getMMKV().decodeBool(key);
    }


    public static boolean decodeBool(String key, boolean defaultValue) {
        return SP.getMMKV().decodeBool(key, defaultValue);
    }


    public static boolean encode(String key, int value) {
        return SP.getMMKV().encode(key, value);
    }


    public static int decodeInt(String key) {
        return SP.getMMKV().decodeInt(key);
    }


    public static int decodeInt(String key, int defaultValue) {
        return SP.getMMKV().decodeInt(key, defaultValue);
    }


    public static boolean encode(String key, long value) {
        return SP.getMMKV().encode(key, value);
    }


    public static long decodeLong(String key) {
        return SP.getMMKV().decodeLong(key);
    }


    public static long decodeLong(String key, long defaultValue) {
        return SP.getMMKV().decodeLong(key, defaultValue);
    }


    public static boolean encode(String key, float value) {
        return SP.getMMKV().encode(key, value);
    }


    public static float decodeFloat(String key) {
        return SP.getMMKV().decodeFloat(key);
    }


    public static float decodeFloat(String key, float defaultValue) {
        return SP.getMMKV().decodeFloat(key, defaultValue);
    }


    public static boolean encode(String key, double value) {
        return SP.getMMKV().encode(key, value);
    }


    public static double decodeDouble(String key) {
        return SP.getMMKV().decodeDouble(key);
    }


    public static double decodeDouble(String key, double defaultValue) {
        return SP.getMMKV().decodeDouble(key, defaultValue);
    }


    public static boolean encode(String key, String value) {
        return SP.getMMKV().encode(key, value);
    }


    public static String decodeString(String key) {
        return SP.getMMKV().decodeString(key);
    }


    public static String decodeString(String key, String defaultValue) {
        return SP.getMMKV().decodeString(key, defaultValue);
    }


    public static boolean encode(String key, Set<String> value) {
        return SP.getMMKV().encode(key, value);
    }


    public static Set<String> decodeStringSet(String key) {
        return SP.getMMKV().decodeStringSet(key);
    }


    public static Set<String> decodeStringSet(String key, Set<String> defaultValue) {
        return SP.getMMKV().decodeStringSet(key, defaultValue);
    }


    public static boolean encode(String key, byte[] value) {
        return SP.getMMKV().encode(key, value);
    }


    public static byte[] decodeBytes(String key) {
        return SP.getMMKV().decodeBytes(key);
    }


    public static boolean encode(String key, Parcelable value) {
        return SP.getMMKV().encode(key, value);
    }


    public static <T extends Parcelable> T decodeParcelable(String key, Class<T> tClass) {
        return SP.getMMKV().decodeParcelable(key, tClass);
    }


    public static <T extends Parcelable> T decodeParcelable(String key, Class<T> tClass, T defaultValue) {
        return SP.getMMKV().decodeParcelable(key, tClass, defaultValue);
    }


    public static int getValueSize(String key) {
        return SP.getMMKV().getValueSize(key);
    }


    public static boolean containsKey(String key) {
        return SP.getMMKV().containsKey(key);
    }


    public static String[] allKeys() {
        return SP.getMMKV().allKeys();
    }


    public static long count() {
        return SP.getMMKV().count();
    }


    public static long totalSize() {
        return SP.getMMKV().totalSize();
    }


    public static void removeValueForKey(String key) {
        SP.getMMKV().removeValueForKey(key);
    }


    public static void removeValuesForKeys(String[] strings) {
        SP.getMMKV().removeValuesForKeys(strings);
    }


    public static void clearAll() {
        SP.getMMKV().clearAll();
    }


    public static void trim() {
        SP.getMMKV().trim();
    }


    public static void close() {
        SP.getMMKV().close();
    }


    public static void clearMemoryCache() {
        SP.getMMKV().clearMemoryCache();
    }


    public static void sync() {
        SP.getMMKV().sync();
    }


    public static int importFromSharedPreferences(SharedPreferences preferences) {
        return SP.getMMKV().importFromSharedPreferences(preferences);
    }


    public static Map<String, ?> getAll() {
        return SP.getMMKV().getAll();
    }

    @Nullable
    public static String getString(String key, @Nullable String defValue) {
        return SP.getMMKV().getString(key, defValue);
    }


    public static SharedPreferences.Editor putString(String key, @Nullable String value) {
        return SP.getMMKV().putString(key, value);
    }

    @Nullable
    public static Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return SP.getMMKV().getStringSet(key, defValues);
    }


    public static SharedPreferences.Editor putStringSet(String key, @Nullable Set<String> values) {
        return SP.getMMKV().putStringSet(key, values);
    }


    public static int getInt(String key, int defValue) {
        return SP.getMMKV().getInt(key, defValue);
    }


    public static SharedPreferences.Editor putInt(String key, int value) {
        return SP.getMMKV().putInt(key, value);
    }


    public static long getLong(String key, long defValue) {
        return SP.getMMKV().getLong(key, defValue);
    }


    public static SharedPreferences.Editor putLong(String key, long value) {
        return SP.getMMKV().putLong(key, value);
    }


    public static float getFloat(String key, float defValue) {
        return SP.getMMKV().getFloat(key, defValue);
    }


    public static SharedPreferences.Editor putFloat(String key, float value) {
        return SP.getMMKV().putFloat(key, value);
    }


    public static boolean getBoolean(String key, boolean defValue) {
        return SP.getMMKV().getBoolean(key, defValue);
    }


    public static SharedPreferences.Editor putBoolean(String key, boolean value) {
        return SP.getMMKV().putBoolean(key, value);
    }


    public static SharedPreferences.Editor remove(String key) {
        return SP.getMMKV().remove(key);
    }


    public static SharedPreferences.Editor clear() {
        return SP.getMMKV().clear();
    }


    public static boolean commit() {
        return SP.getMMKV().commit();
    }


    public static void apply() {
        SP.getMMKV().apply();
    }


    public static boolean contains(String key) {
        return SP.getMMKV().contains(key);
    }


    public static SharedPreferences.Editor edit() {
        return SP.getMMKV().edit();
    }


    public static int ashmemFD() {
        return SP.getMMKV().ashmemFD();
    }


    public static int ashmemMetaFD() {
        return SP.getMMKV().ashmemMetaFD();
    }
}
