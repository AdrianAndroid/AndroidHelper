package com.flannery.startmodeup;

import android.app.Activity;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
@Aspect
public class AOPTEST {

    // 打印出所有方法的调用
//    @Before("execution(* com.flannery.startmodeup.*Activity.on**(..))")
//    public void getMName(ProceedingJoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
//        Log.i("AOP", name);
//    }

}
