package com.flannery.interview.innerclass;

/**
 * 实例内部类
 */
public class InnerClass1 {
    String str = "一颗彪悍的种子";

    //内部类
    class Inner{
        void fun(){
            System.out.println(str);
        }
    }
}


