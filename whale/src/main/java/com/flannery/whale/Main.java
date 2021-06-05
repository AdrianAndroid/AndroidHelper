package com.flannery.whale;

import android.os.Parcel;
import android.util.Log;

import com.lody.whale.xposed.XC_MethodHook;
import com.lody.whale.xposed.XposedBridge;
import com.lody.whale.xposed.XposedHelpers;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

//https://blog.csdn.net/tianseyiwan008/article/details/97522266
//https://blog.csdn.net/tianseyiwan008/article/details/97522266
public class Main {

    //第一种采用反射机制hook
    private void hook_method(String className, ClassLoader classLoader, String methodName,
                             Object... parameterTypesAndCallback) {
        try {
            XposedHelpers.findAndHookMethod(className, classLoader, methodName, parameterTypesAndCallback);
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    //第二种方式，采用查找类方法方式hook
    private void hook_methods(String className, String methodName, XC_MethodHook xmh) {
        try {
            Class<?> clazz = Class.forName(className);
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(methodName)
                        && !Modifier.isAbstract(method.getModifiers())
                        && Modifier.isPublic(method.getModifiers())
                ) {
                    XposedBridge.hookMethod(method, xmh);
                }
            }
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    public void executeHook() {
        XposedBridge.log("start hook");
        hook_methods(Test.class.getName(), "get", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) {
                XposedBridge.log("hooking...");
                Object obj = param.getResult();
                XposedBridge.log("hooked args:" + obj);
                param.setResult("hook test by first way");
            }
        });
    }

    public void executeHook2(ClassLoader classLoader) {
        XposedBridge.log("start hook2");
        hook_method(Test.class.getName(), classLoader, "get", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) {
                XposedBridge.log("hooking...");
                Object obj = param.getResult();
                XposedBridge.log("hooked args:" + obj);
                param.setResult("hook test by second way");
            }
        });
    }

    public static void hook_BinderProxy() {
        try {
            //hook_method(Class.forName("android.os.BinderProxy"), "transact", );
            XposedHelpers.findAndHookMethod(Class.forName("android.os.BinderProxy"), "transact",
                    int.class, Parcel.class, Parcel.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            //super.beforeHookedMethod(param);
                            Log.i("Main --> ", "BinderProxy beforeHookedMethod " + param.thisObject.getClass().getSimpleName() + "\n" + Log.getStackTraceString(new Throwable()));
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void hook_Thread() {
        XposedBridge.hookAllConstructors(Thread.class, new XC_MethodHook(){
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Thread thread = (Thread) param.thisObject;
                Log.i("hook_Thread", thread.getName() + " stack " + Log.getStackTraceString(new Throwable()));
            }
        });
    }
}
