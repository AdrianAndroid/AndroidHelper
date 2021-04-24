package com.flannery.interview.innerclass;

/**
 * 局部内部类
 */
public class InnerClass3 {
    void fun_1() {
        String name = "一颗剽悍的种子";
        class Inner {
            void fun_2() {
                System.out.print(name);
            }
        }
        Inner in = new Inner();
        in.fun_2();
    }

    //局部内部类有什么局限性呢？
    //1.局部内部类只能在定义方法中使用。 可以看到代码示例，在方法中创建内部类并且调用内部类里的方法，后续实例化
    //只要调用外部类里的 fun_1 方法就可以。
    //2.不能使用 public、protected、private 修饰 。 这一条是对应第一条的，你想想，你已经在方法中创建了，只在方法
    //中使用，你还用这些权限干嘛 ？？？
    //3.局部类当中不能使用static变量。 上面静态内部类时说过，static是属于外部类的，所以在方法里的内部类定义static
    //变量会报错。
    //除了局部内部类的局限性，还可以看到在代码实例中，内部类是可以直接使用方法中的变量的。
    //（在jdk1.7之前内部类想访问方法中的变量是需要加上final的，我们知道final是用来声明常量的，fianl需要设置好值而
    //且不能修改。）
    //jdk1.8版本之后不需要，正好我的jdk1.8 所以就不用加final了，但是真实情况下，通过反编译工具看class文件会发现编
    //译时已经帮我们自动加上了。
    //（你会发现随着语言的发展，让你更易于开发的同时，你离本质也更加的遥远）
}
//class Outer$1Inner {
//    final Outer this$0;
//    private final String val$name;
//
//    void fun_2() {
//        System.out.print(val$name);
//    }
//
//    Outer$1Inner() {
//        this$0 = final_outer;
//        val$name = String.this;
//        super();
//    }
//}