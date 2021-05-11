package com.flannery.whale;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


//https://blog.csdn.net/qq_20641565/article/details/78626845
@Aspect
public class AspectAOP {

    @Around("call(* com.flannery.whale.MyApp.**(..))")
    public void getTime(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String name = signature.toShortString();
        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("AspectAOP ======>" + name);
//        LogUtils.i(name + " getTime cost " + (System.currentTimeMillis() - time));
    }

    //com.squareup.leakcanary
    @Pointcut("execution(public * com.squareup.leakcanary.*.*(..))")
    void testlog() {
    }


    @Before("testlog()")
    public void doBefore(JoinPoint joinPoint) {
        String s = joinPoint.getSignature().toShortString();
        System.out.println("doBefore --> " + s);
    }

    @After("testlog()")
    public void afterTime() {

    }


    @Pointcut("execution(private * com.squareup.leakcanary.*.*(..))")
    void testlog2() {
    }

    @Before("testlog2()")
    public void doBefore2(JoinPoint joinPoint) {
        String s = joinPoint.getSignature().toShortString();
        System.out.println("doBefore[p] --> " + s);
    }

}
