package com.imooc.router.processor;

import com.google.auto.service.AutoService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.imooc.router.annotations.Destination;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class DestinationProcessor extends AbstractProcessor {

    private static final String TAG = "DestinationProcessor";

    /**
     * 编译器找到我们关心的注解后，会回调这个方法
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set,
                           RoundEnvironment roundEnvironment) {

        // 避免多次调用 process
        if (roundEnvironment.processingOver()) {
            return false;
        }

        System.out.println(TAG + " >>> process start ...");

        String rootDir = processingEnv.getOptions().get("root_project_dir");

        // 获取所有标记了 @Destination 注解的 类的信息
        Set<Element> allDestinationElements =
                (Set<Element>) roundEnvironment.getElementsAnnotatedWith(Destination.class);

        System.out.println(TAG + " >>> all Destination elements count = "
                + allDestinationElements.size());

        // 当未收集到 @Destination 注解的时候，跳过后续流程
        if (allDestinationElements.size() < 1) {
            return false;
        }

        // 将要自动生成的类的类名]
        final JsonArray destinationJsonArray = new JsonArray();

        RouterMappingFactory factory =
                new RouterMappingFactory("com.imooc.router.mapping", "RouterMapping_" + System.currentTimeMillis());
        factory.beginClass();
        factory.methodBeginGet(); //开始记录get方法

        RouterPathFactory pathFactory = new RouterPathFactory(factory.getPackageName(), "RouterPath");
        pathFactory.beginClass();

        // 需要校验
        HashSet<String> urls = new HashSet<>(); //用于验证

        // 遍历所有 @Destination 注解信息，挨个获取详细信息
        for (Element element : allDestinationElements) {

            final TypeElement typeElement = (TypeElement) element;
            System.out.println("typeEelemtn ==> " + typeElement);


            // 尝试在当前类上，获取 @Destination 的信息
            final Destination destination = typeElement.getAnnotation(Destination.class);

            if (destination == null) {
                continue;
            }

            final String url = destination.url();
            final String description = destination.description();
            final String realPath = typeElement.getQualifiedName().toString();
            if (!urls.add(url)) {
                String message = "url=" + url + " , " + "realPath=" + realPath + "  路径重复";
                for (int i = 0; i < 50; i++) {
                    System.out.println("[ERROR]  异常抛不出文字，多打几遍！！！！！[ERROR]!!!![ERROR]");
                    System.out.println(message);
                    System.out.println();
                }
                throw new AssertionError(message);
            }

            System.out.println(TAG + " >>> url = " + url);
            System.out.println(TAG + " >>> description = " + description);
            System.out.println(TAG + " >>> realPath = " + realPath);

            pathFactory.fieldAdd(url);
            factory.methodAppendGet(url, realPath);

            JsonObject item = new JsonObject();
            item.addProperty("url", url);
            item.addProperty("description", description);
            item.addProperty("realPath", realPath);

            destinationJsonArray.add(item);
        }

        factory.methodEndGet(); //结束get
        factory.endClass();

        pathFactory.endClass();

        urls.clear();

        System.out.println(TAG + " >>> mappingFullClassName = " + factory.getFullPath());
        System.out.println(TAG + " >>> class content = \n" + factory.toString());

        // 写入自动生成的类到本地文件中
        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(factory.getFullPath());
            Writer writer = source.openWriter();
            writer.write(factory.toString());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            throw new RuntimeException("Error while create file", ex);
        }

        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(pathFactory.getFullPath());
            Writer writer = source.openWriter();
            writer.write(pathFactory.toString());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            throw new RuntimeException("Error while create file", ex);
        }

        // 写入JSON到本地文件中

        // 检测父目录是否存在
        File rootDirFile = new File(rootDir);
        if (!rootDirFile.exists()) {
            throw new RuntimeException("root_project_dir not exist!");
        }

        // 创建 router_mapping 子目录
        File routerFileDir = new File(rootDirFile, "router_mapping");
        if (!routerFileDir.exists()) {
            routerFileDir.mkdirs();
        }

        File mappingFile = new File(routerFileDir, "mapping_" + System.currentTimeMillis() + ".json");

        // 写入json内容
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(mappingFile));
            String jsonStr = destinationJsonArray.toString();
            out.write(jsonStr);
            out.flush();
            out.close();
        } catch (Throwable throwable) {
            throw new RuntimeException("Error while writing json", throwable);
        }

        System.out.println(TAG + " >>> process finish.");

        return false;
    }

    /**
     * 告诉编译器，当前处理器支持的注解类型
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(
                Destination.class.getCanonicalName()
        );
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
}
