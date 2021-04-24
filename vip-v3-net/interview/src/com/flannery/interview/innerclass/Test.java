package com.flannery.interview.innerclass;

public class Test {

    public static void main(String[] args) {
        //实例内部类：直接定义在类当中的一个类，在类前面没有任何一个修饰符。
        final InnerClass1 out = new InnerClass1();
        ////实例内部类
        out.new Inner().fun();
        //静态内部类：在内部类前面加上一个static。
        new InnerClass2.Inner().fun();

        //局部内部类：定义在方法当中的内部类。
        new InnerClass3().fun_1(); //类定义在方法中

        //匿名内部类：属于局部的一种特殊情况。
    }

}
