package com.joyy.router

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils

import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

import static com.joyy.router.RouterPlugin.log

class RouterMappingTransform extends Transform {

    RouterMappingTransform() {

    }

    /**
     * 当前Transform的名称
     * @return
     */
    @Override
    String getName() {
        return "RouterMappingTransform"
    }


    /**
     * public static final Set<QualifiedContent.ScopeType> EMPTY_SCOPES = ImmutableSet.of();
     * public static final Set<QualifiedContent.ContentType> CONTENT_CLASS = ImmutableSet.of(CLASSES);
     * public static final Set<QualifiedContent.ContentType> CONTENT_JARS = ImmutableSet.of(CLASSES, RESOURCES);
     * public static final Set<QualifiedContent.ContentType> CONTENT_RESOURCES = ImmutableSet.of(RESOURCES);
     * public static final Set<QualifiedContent.ContentType> CONTENT_DEX = ImmutableSet.of(ExtendedContentType.DEX);
     * public static final Set<QualifiedContent.ContentType> CONTENT_DEX_WITH_RESOURCES = ImmutableSet.of(ExtendedContentType.DEX, RESOURCES);
     *
     * 返回告知编译器，当前Transform需要消费的输入类型
     * 在这里是CLASS类型
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * public static final Set<QualifiedContent.ScopeType> PROJECT_ONLY = ImmutableSet.of(QualifiedContent.Scope.PROJECT);
     * public static final Set<QualifiedContent.ScopeType> SCOPE_FULL_PROJECT = ImmutableSet.of(QualifiedContent.Scope.PROJECT, QualifiedContent.Scope.SUB_PROJECTS, QualifiedContent.Scope.EXTERNAL_LIBRARIES);
     * public static final Set<QualifiedContent.ScopeType> SCOPE_FULL_WITH_FEATURES = new ImmutableSet.Builder<QualifiedContent.ScopeType>().addAll(SCOPE_FULL_PROJECT).add(InternalScope.FEATURES).build();
     * public static final Set<QualifiedContent.ScopeType> SCOPE_FEATURES = ImmutableSet.of(InternalScope.FEATURES);
     * public static final Set<QualifiedContent.ScopeType> SCOPE_FULL_LIBRARY_WITH_LOCAL_JARS = ImmutableSet.of(QualifiedContent.Scope.PROJECT, InternalScope.LOCAL_DEPS);
     * public static final Set<QualifiedContent.ScopeType> SCOPE_FULL_PROJECT_WITH_LOCAL_JARS = new ImmutableSet.Builder<QualifiedContent.ScopeType>().addAll(SCOPE_FULL_PROJECT).add(InternalScope.LOCAL_DEPS).build();
     * 告知编译器，当前Transform需要手机的范围
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 是否支持增量
     * 通常返回false
     * @return
     */
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * 所有的class收集好以后，会被导报传入此方法
     * @param transformInvocation
     * @throws TransformException* @throws InterruptedException* @throws IOException
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        // 1。 遍历所有的Input
        // 2。 对Input进行二次处理
        // 3。 将Input拷贝到目标目录

        RouterMappingController2 controller2 = new RouterMappingController2()

        // 遍历所有的输入
        transformInvocation.inputs.each {
            log("把文件夹类型的输入，拷贝到目标目录")
            it.directoryInputs.each { directoryInput ->
                def destDir = transformInvocation.outputProvider
                        .getContentLocation(
                                directoryInput.name,
                                directoryInput.contentTypes,
                                directoryInput.scopes,
                                Format.DIRECTORY
                        )
                log(directoryInput.file.absolutePath)
                controller2.collect(directoryInput.file)
                FileUtils.copyDirectory(directoryInput.file, destDir)
            }

            log("把JAR类型的输入，拷贝到目标目录")
            it.jarInputs.each { jarInput ->
                def dest = transformInvocation.outputProvider
                        .getContentLocation(
                                jarInput.name,
                                jarInput.contentTypes,
                                jarInput.scopes,
                                Format.JAR
                        )
                log(jarInput.file.absolutePath)
                controller2.collectFromJarFile(jarInput.file)
                FileUtils.copyFile(jarInput.file, dest)
            }

            log("${getName()}  all mapping class name = ${controller2.mappingClassNames}")
            File mappingJarFile = transformInvocation.outputProvider
                    .getContentLocation(
                            "router_mapping",
                            getOutputTypes(),
                            getScopes(),
                            Format.JAR
                    )

            log("${getName()} mappingJarFile = $mappingJarFile")

            if (!mappingJarFile.getParentFile().exists()) {
                mappingJarFile.getParentFile().mkdirs()
            }
            if (mappingJarFile.exists()) {
                mappingJarFile.delete()
            }
            log("打印所有的MappingClassNames ${controller2.mappingClassNames.size()}")
            controller2.mappingClassNames.each {
                log("[mappingClassNames] in RouterMappingTransform中 $it")
            }

            log("将生成的字节码，写入本地文件!")
            FileOutputStream fos = new FileOutputStream(mappingJarFile)
            JarOutputStream jarOutputStream = new JarOutputStream(fos)
            ZipEntry zipEntry = new ZipEntry(RouterMappingByteCodeBuilder.CLASS_NAME + ".class")
            jarOutputStream.putNextEntry(zipEntry)
            jarOutputStream.write(RouterMappingByteCodeBuilder.get(controller2.mappingClassNames))
            jarOutputStream.closeEntry()
            jarOutputStream.close()
            fos.close()
        }

    }
}