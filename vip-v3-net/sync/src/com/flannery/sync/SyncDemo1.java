package com.flannery.sync;

import com.sun.xml.internal.ws.Closeable;
import sun.misc.ClassLoaderUtil;
import sun.misc.IOUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SyncDemo1 {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public  void AAA() {
        System.out.println("class:" + this + ", AAA()" + "  " + format.format(new Date()));
    }

    public  void BBB() {
        System.out.println("class:" + this + ", BBB()" + "  " + format.format(new Date()));
    }

    public void CCC() {
        //synchronized (this) {
            System.out.println("class:" + this + ", CCC()" + "  " + format.format(new Date()));
        //}
    }

    public static void main(String[] args) {
        final SyncDemo1 syncDemo1 = new SyncDemo1();
        new Thread(() -> {
            while (true) {
                syncDemo1.AAA();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                syncDemo1.BBB();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            while (true) {
                syncDemo1.CCC();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
