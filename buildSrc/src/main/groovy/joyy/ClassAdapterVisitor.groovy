import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import joyy.CustomMethodVisitor

public class ClassAdapterVisitor extends ClassVisitor {
    public ClassAdapterVisitor(ClassVisitor api) {
        super(Opcodes.ASM6, api);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.err.println("方法名称：" + name + "  signature:" + signature + "   descriptor=" + descriptor);
        //得到MethodVistor 然后传递给使用自定义的包装类来返回
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new CustomMethodVisitor(api, mv, access, name, descriptor);
    }
}