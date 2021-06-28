package com.imooc.router.gradle

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class RouterMappingTransform extends Transform {

    /**
     * 当前 Transform 的名称
     * @return
     */
    @Override
    String getName() {
        return "RouterMappingTransform"
    }

    /**
     * 返回告知编译器，当前Transform需要消费的输入类型
     * 在这里是CLASS类型
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 告知编译器，当前Transform需要收集的范围
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 是否支持增量
     * 通常返回False
     * @return
     */
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * 所有的class收集好以后，会被打包传入此方法
     * @param transformInvocation
     * @throws TransformException
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {
        // 1. 遍历所有的Input
        // 2. 对Input进行二次处理
        // 3. 将Input拷贝到目标目录

        RouterMappingCollector collector = new RouterMappingCollector()

        // 遍历所有的输入
        transformInvocation.inputs.each {

            // 把 文件夹 类型的输入，拷贝到目标目录
            it.directoryInputs.each { directoryInput ->
                def destDir = transformInvocation.outputProvider
                                .getContentLocation(
                                        directoryInput.name,
                                        directoryInput.contentTypes,
                                        directoryInput.scopes,
                                        Format.DIRECTORY)
                collector.collect(directoryInput.file)
                FileUtils.copyDirectory(directoryInput.file, destDir)
            }

            // 把 JAR 类型的输入，拷贝到目标目录
            it.jarInputs.each { jarInput ->
                def dest = transformInvocation.outputProvider
                              .getContentLocation(
                                      jarInput.name,
                                      jarInput.contentTypes,
                                      jarInput.scopes, Format.JAR)
                collector.collectFromJarFile(jarInput.file)
                FileUtils.copyFile(jarInput.file, dest)
            }
        }

        println("${getName()} all mapping class name = "
                + collector.mappingClassName)

        File mappingJarFile = transformInvocation.outputProvider.
                                getContentLocation(
                                         "router_mapping",
                                          getOutputTypes(),
                                          getScopes(),
                                          Format.JAR)

        println("${getName()}  mappingJarFile = $mappingJarFile")

        if (mappingJarFile.getParentFile().exists()) {
            mappingJarFile.getParentFile().mkdirs()
        }

        if (mappingJarFile.exists()) {
            mappingJarFile.delete()
        }

        // 将生成的字节码，写入本地文件
        FileOutputStream fos = new FileOutputStream(mappingJarFile)
        JarOutputStream jarOutputStream = new JarOutputStream(fos)
        ZipEntry zipEntry =
                new ZipEntry(RouterMappingByteCodeBuilder.CLASS_NAME + ".class")
        jarOutputStream.putNextEntry(zipEntry)
        jarOutputStream.write(
                RouterMappingByteCodeBuilder.get(collector.mappingClassName))
        jarOutputStream.closeEntry()
        jarOutputStream.close()
        fos.close()
    }
}
