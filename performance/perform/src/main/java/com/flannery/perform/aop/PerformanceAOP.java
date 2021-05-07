package com.flannery.perform.aop;

import com.flannery.perform.utils.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

//aop，通过注解联系起来
@Aspect
public class PerformanceAOP {

    @Around("call(* com.flannery.perform.PerformApp.**(..))")
    public void getTime(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String name = signature.toShortString();
        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        LogUtils.i(name + " cost " + (System.currentTimeMillis() - time));
    }


}
