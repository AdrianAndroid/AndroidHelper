/*
**        DroidPlugin Project
**
** Copyright(c) 2015 Andy Zhang <zhangyong232@gmail.com>
**
** This file is part of DroidPlugin.
**
** DroidPlugin is free software: you can redistribute it and/or
** modify it under the terms of the GNU Lesser General Public
** License as published by the Free Software Foundation, either
** version 3 of the License, or (at your option) any later version.
**
** DroidPlugin is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
** Lesser General Public License for more details.
**
** You should have received a copy of the GNU Lesser General Public
** License along with DroidPlugin.  If not, see <http://www.gnu.org/licenses/lgpl.txt>
**
**/

package com.morgoo.droidplugin.hook.proxy;

import android.content.Context;
import android.util.AndroidRuntimeException;

import com.morgoo.droidplugin.hook.BaseHookHandle;
import com.morgoo.droidplugin.hook.handle.IActivityManagerHookHandle;
import com.morgoo.droidplugin.reflect.FieldUtils;
import com.morgoo.droidplugin.reflect.Utils;
import com.morgoo.helper.Log;
import com.morgoo.helper.MyProxy;
import com.morgoo.helper.compat.ActivityManagerNativeCompat;
import com.morgoo.helper.compat.IActivityManagerCompat;
import com.morgoo.helper.compat.SingletonCompat;

import java.util.List;

/**
 * Hook some function on IActivityManager
 * <p/>
 * Code by Andy Zhang (zhangyong232@gmail.com) on 15/2/7.
 */
public class IActivityManagerHook extends ProxyHook {

    private static final String TAG = IActivityManagerHook.class.getSimpleName();

    public IActivityManagerHook(Context hostContext) {
        super(hostContext);
    }

    @Override
    public BaseHookHandle createHookHandle() {
        return new IActivityManagerHookHandle(mHostContext);
    }

    // 生成代理对象，赋值给系统的 mInstance 对象
    @Override
    public void onInstall(ClassLoader classLoader) throws Throwable {
        // 获取 ActivityManagerNative 的Class 对象
        Class cls = ActivityManagerNativeCompat.Class();
        // 获取 Singleton 对象
        Object obj = FieldUtils.readStaticField(cls, "gDefault");
        if (obj == null) {
            ActivityManagerNativeCompat.getDefault();
            obj = FieldUtils.readStaticField(cls, "gDefault");
        }

        // 判断 obj 是不是 IActivityManager 类或其子类
        if (IActivityManagerCompat.isIActivityManager(obj)) {
            setOldObj(obj);
            Class<?> objClass = mOldObj.getClass();
            List<Class<?>> interfaces = Utils.getAllInterfaces(objClass);
            Class[] ifs = interfaces != null && interfaces.size() > 0 ? interfaces.toArray(new Class[interfaces.size()]) : new Class[0];
            Object proxiedActivityManager = MyProxy.newProxyInstance(objClass.getClassLoader(), ifs, this);
            FieldUtils.writeStaticField(cls, "gDefault", proxiedActivityManager);
            Log.i(TAG, "Install ActivityManager Hook 1 old=%s,new=%s", mOldObj, proxiedActivityManager);
        } else if (SingletonCompat.isSingleton(obj)) { // 判断 obj 是不是 Singleton 类或其子类
            // 获取 mInstance 对象
            Object obj1 = FieldUtils.readField(obj, "mInstance");
            if (obj1 == null) {
                SingletonCompat.get(obj);
                obj1 = FieldUtils.readField(obj, "mInstance");
            }
            // 获取动态代理的第二个参数
            setOldObj(obj1);// this.mOldObj = oldObj;
            List<Class<?>> interfaces = Utils.getAllInterfaces(mOldObj.getClass());
            Class[] ifs = interfaces != null && interfaces.size() > 0 ? interfaces.toArray(new Class[interfaces.size()]) : new Class[0];

            // 创建 mInstance 的代理对象
            final Object object = MyProxy.newProxyInstance(mOldObj.getClass().getClassLoader(),ifs, IActivityManagerHook.this);
            Object iam1 = ActivityManagerNativeCompat.getDefault();

            //这里先写一次，防止后面找不到Singleton类导致的挂钩子失败的问题。
            // 将代理对象赋值给系统的 mInstance 对象
            FieldUtils.writeField(obj, "mInstance", object);

            //这里使用方式1，如果成功的话，会导致上面的写操作被覆盖。
            FieldUtils.writeStaticField(cls, "gDefault", new android.util.Singleton<Object>() {
                @Override
                protected Object create() {
                    Log.e(TAG, "Install ActivityManager 3 Hook  old=%s,new=%s", mOldObj, object);
                    return object;
                }
            });

            Log.i(TAG, "Install ActivityManager Hook 2 old=%s,new=%s", mOldObj.toString(), object);
            Object iam2 = ActivityManagerNativeCompat.getDefault();
            // 方式2
            if (iam1 == iam2) {
                //这段代码是废的，没啥用，写这里只是不想改而已。
                FieldUtils.writeField(obj, "mInstance", object);
            }
        } else {
            throw new AndroidRuntimeException("Can not install IActivityManagerNative hook");
        }
    }
}
