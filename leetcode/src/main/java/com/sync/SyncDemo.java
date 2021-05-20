package com.sync;

import java.lang.annotation.RetentionPolicy;

//-XX:+UnlockDiagnosticVMOptions
//-XX:+PrintAssembly
//-Xcomp
//-XX:CompileCommand=dontinline,*Bar.sum
//-XX:CompileCommand=compileonly,*Bar.sum
//
//-server
//-Xcomp
//-XX:+UnlockDiagnosticVMOptions
//-XX:+PrintAssembly
//-XX:CompileCommand=compileonly,*VolatileInstance.main*
//https://www.cnblogs.com/zhuyeshen/p/12106113.html
public  class  SyncDemo {

    public volatile String AAABBBB = "hhlo";

    public static void main(String[] args) {
        System.out.println("hello");
        add();
        new SyncDemo().test();
    }

    public static synchronized void add(){
        System.out.println("add");
    }

    public synchronized void test() {
        System.out.println("test");
    }
    public void test2() {
        synchronized (this){
            System.out.println("test2");
        }
    }



//偏向锁的获取：
//  判断是否为可偏向状态
//  如果为可偏向状态，则判断线程ID是否是当前线程，如果是进入同步块；
//  如果线程ID并未指向当前线程，利用CAS操作竞争锁，如果竞争成功，将Mark Word中线程ID更新为当前线程ID，进入同步块
//  如果竞争失败，等待全局安全点，准备撤销偏向锁，根据线程是否处于活动状态，决定是转换为无锁状态还是升级为轻量级锁。\
//https://blog.csdn.net/weixin_38481963/article/details/88384493
    //https://zhidao.baidu.com/question/142992203452606005.html
}
