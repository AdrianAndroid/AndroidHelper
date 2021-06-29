package com.example.lib;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// https://blog.csdn.net/xx326664162/article/details/95993522
// 字节码插桩
// https://blog.csdn.net/lingjianglin/article/details/105614948
public class MyClass {

/*
javac Sample.java   // 生成Sample.class，也就是Java字节码
javap -v Sample     // 查看Sample类的Java字节码

//通过Java字节码，生成Dalvik字节码
dx --dex --output=Sample.dex Sample.class

dexdump -d Sample.dex   // 查看Sample.dex的Dalvik的字节码
*/

    public static void main(String[] args) {
        System.out.println("Hello world");
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() throws Exception {
        String nameFrom = "/Users/flannery/Desktop/AndroidHelper/gradledemo/MyClass.class";
        String nameTo = "/Users/flannery/Desktop/AndroidHelper/gradledemo/MyClass2.class";
        File file = new File(nameTo);
        if (file.exists()) {
            file.delete();
        }
        // 1. 准备分析的class
        FileInputStream fis = new FileInputStream(nameFrom);
        ClassReader cr = new ClassReader(fis);

        // 2. 执行分析与插桩
        // class 字节码的读取与分析引擎
        // 写出器COMPUTE_FRAMES自动计算所有的内容，后续操作更简单
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // 分析，处理结果写入cw EXPAND_FRAMES:栈图以扩展格式进行访问
        cr.accept(new ClassAdapterVisitor(cw), ClassReader.EXPAND_FRAMES);

        // 3. 得到结果并输出
        byte[] newClassBytes = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream(nameTo);
        fos.write(newClassBytes);
        fos.close();
    }

}