package com.flannery.interview.innerclass;

/**
 * 静态内部类
 */
public class InnerClass2 {
    static String str = "一颗剽悍的种子";

    //静态内部类
    static class Inner {
        void fun() {
            System.out.println(str);
        }
    }
}
/*
static class Outer$Inner {
 void fun() { System.out.println(Outer.str); }Outer$Inner() {}
}
 */