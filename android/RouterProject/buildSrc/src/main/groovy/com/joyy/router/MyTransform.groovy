package com.joyy.router

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import groovy.io.FileType
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode

class MyTransform extends Transform {

    Project project;

    MyTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "com.joyy.router.MyTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true //通常返回false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        //super.transform(transformInvocation)
        println "com.joyy.router.MyTransform transform() start"
        def start = System.currentTimeMillis()

        transformInvocation.getInputs().forEach({ input ->
            input.directoryInputs.forEach({ dir ->
                handleDirectoryInputs(dir, transformInvocation)
            })
        })

//        transformInvocation.getInputs().forEach(input -> {
//            input.getDirectoryInputs().forEach(dir -> {
//                handleDirectoryInputs(dir, transformInvocation);
//            })
//        });


        def end = System.currentTimeMillis();
        println "com.joyy.router.MyTransform transform() end  total = ${end - start}"
    }

    private boolean checkFileName(String name) {
        return name != null && name.endsWith("Fragment.class")
    }

    /**
     * handle Directory
     */
    private void handleDirectoryInputs(DirectoryInput dir, TransformInvocation transformInvocation) {
        try {
            if (dir.getFile().isDirectory()) {
//            transformDir(dir.getFile());
                System.out.println("fragment_build #### handleDirectoryInputs " + dir.getFile().getName());
                File dest = transformInvocation.getOutputProvider().getContentLocation(dir.getName(),
                        dir.getContentTypes(), dir.getScopes(),
                        Format.DIRECTORY);

                if (dir.file) {
                    def modifiedRecord = [:]
                    dir.file.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File classFile ->
                        def className = classFile.absolutePath
                        if (checkFileName(className)) {
                            println "className = $className" // 得到Class

                            FileInputStream fis = new FileInputStream(className);
                            ClassReader cr = new ClassReader(fis)
                            /*** 2、执行分析与插桩*/
                            //class字节码的读取与分析引擎
                            // 写出器 COMPUTE_FRAMES 自动计算所有的内容，后续操作更简单
                            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

                            // 分析，处理结果写入cw EXPAND_FRAMES：栈图以扩展格式进行访问
                            cr.accept(new ClassAdapterVisitor(cw), ClassReader.EXPAND_FRAMES)
                            /** 3、获得结果并输出*/
                            byte[] newClassBytes = cw.toByteArray()
                            FileOutputStream fos = FileOutputStream("/Users/flannery/Desktop/AndroidHelper/buildSrc/a.class");
                            fos.write(newClassBytes)
                            fis.close()
                            fos.close()
                        }
//                  if (!ASMHelper.filter(className)) {
//                      def transformedClass = ASMHelper.transformClass(classFile, dir.file, transformInvocation.context.temporaryDir)
//                      modifiedRecord[(className)] = transformedClass
//                  }
                    }
//                FileUtils.copyDirectory(dir.file, destDir)
//                modifiedRecord.each { name, file ->
//                    def targetFile = new File(destDir.absolutePath, name)
//                    if (targetFile.exists()) {
//                        targetFile.delete()
//                    }
//                    FileUtils.copyFile(file, targetFile)
//                }
//                modifiedRecord.clear()
                }
//            try {
//                FileUtils.copyDirectory(dir.getFile(), dest);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            }
        } catch(Exception e) {
            e.printStackTrace()
        }
    }

    byte[] modifyClass(InputStream inputStream) {
        ClassNode classNode = new ClassNode(Opcodes.ASM5)
        ClassReader classReader = new ClassReader(inputStream)
        // 1. 将读入的字节转换为ClassNode
        classReader.accept(classNode, 0)
        // 2. 对classNode的处理逻辑
        Iterator<MethodNode> iterator = classNode.methods.iterator()
        while(iterator.hasNext()) {
            MethodNode node = iterator.next()
            if(node.name.startsWith("test")) {
                iterator.remove()
            }
        }
        ClassWriter classWriter = new ClassWriter(0)
        // 3 将classNode转为字节数组
        classNode.accept(classWriter)
        return classWriter.toByteArray()
    }

    // 是否是Class文件
//    private boolean checkClassFile(String name) {
//        return (name.endsWith(".class")
//                && !name.startsWith("R\\$")
//                && !"R.class".equals(name)
//                && !"BuildConfig.class".equals(name)
//                && checkScanFile(name));
//    }


    //android.telephony.TelephonyManager,getLine1Number
    //android.telephony.TelephonyManager,getDeviceId
    //android.telephony.TelephonyManager,getSubscriberId

    //com.zipow.videobox.sip.server.CmmSIPCallManager.class
    //com/zipow/videobox/fragment/c.class
    //com/huawei/hms/update/f/a.class
//    private boolean checkScanFile(String name) {
//        int size1 = classesList.size();
//        for (int i = 0; i < size1; i++) {
//            if (classesList.get(i).equals(name)) {
//                return true;
//            }
//        }
//        return false
//    }


}