package test;

/**
 * Time:2021/7/23 11:06
 * Author:
 * Description:
 */
public class Test {
    //构造函数
    public Test() {
        System.out.println("----构造函数---");
    }

    //静态的参数初始化
    static {
        System.out.println("---静态的参数初始化---");
    }

    //非静态的参数初始化
    {
        System.out.println("----非静态的参数初始化---");
    }

    public void sayHello() {
        System.out.println("Say Hello!");
    }
}
