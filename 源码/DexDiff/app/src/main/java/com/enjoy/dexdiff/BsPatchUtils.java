package com.enjoy.dexdiff;

public class BsPatchUtils {

    static{
        System.loadLibrary("bspatch_utlis");
    }

    public static native int patch(String oldApk,String newApk,String patchFile);


}
