package com.example.lib;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


/**
 * Time:2021/6/29 18:40
 * Author:
 * Description:
 */
public class ClassAdapterVisitor extends ClassVisitor {

    public ClassAdapterVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("方法名称：" + name + "  signature:" + signature + "  descriptor=" + descriptor);
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new CustomMethodVisitor(api, mv, access, name, descriptor);
    }
}
