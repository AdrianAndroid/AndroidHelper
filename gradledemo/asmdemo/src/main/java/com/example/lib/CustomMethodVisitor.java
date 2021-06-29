package com.example.lib;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

/**
 * Time:2021/6/29 18:42
 * Author:
 * Description:
 */
public class CustomMethodVisitor extends AdviceAdapter {

    protected CustomMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
    }


    private int start;

    //方法进入的时候进行


    //  // access flags 0x1
    //  // signature ()Ljava/lang/Class<*>;
    //  // declaration: java.lang.Class<?> getMyClass()

    //  public getMyClass()Ljava/lang/Class;
    //   L0
    //    LINENUMBER 12 L0
    //    ALOAD 0
    //    INVOKEVIRTUAL java/lang/Object.getClass ()Ljava/lang/Class;
    //    ARETURN
    //   L1
    //    LOCALVARIABLE this Lcom/example/lib/Test3; L0 L1 0
    //    MAXSTACK = 1
    //    MAXLOCALS = 1


    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        // invokeStatic指令，调用静态方法
        invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
        //创建本地LONG类型变量
        start = newLocal(Type.LONG_TYPE);
        //store指令 将方法执行结果从操作数栈存储到局部变量
        storeLocal(start);
    }

    // 方法返回的时候执行
    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        super.onMethodExit(opcode);
        invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
        int end = newLocal(Type.LONG_TYPE);
        //store指令 将方法执行结果从操作数栈存储到局部变量
        storeLocal(end);

        getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType( "Ljava/io/PrintStream;"));
        newInstance(Type.getType("Ljava/lang/StringBuilder;"));
        dup();
        invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), new Method("<init>", "()V"));
        visitLdcInsn("execute :");
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
        loadLocal(end);
        loadLocal(start);
        math(SUB,Type.LONG_TYPE);
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("append", "(J)Ljava/lang/StringBuilder;"));
        visitLdcInsn("ms.");
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("toString", "()Ljava/lang/String;"));
        invokeVirtual(Type.getType("Ljava/io/PrintStream;"),new Method("println", "(Ljava/lang/String;)V"));
    }
}

/*
V  void
Z  boolean
B  byte
C  char
S  short
I  int
J  long
F  float
D  double
 */
