package com.joyy.router.processor;

import com.google.auto.service.AutoService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.joyy.router.annotations.Destination;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Time:2021/7/5 20:42
 * Author:
 * Description:
 */
@AutoService(Processor.class)
public class DestinationProcessor extends AbstractProcessor {


    private static final String TAG = "DestinationProcessor";

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    /**
     * 告诉编译器，当前处理器支持的注解类型
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Destination.class.getCanonicalName());
    }


    /**
     * 编译器找到我们关心的注解后，会回调这个方法
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // 避免多次调用process
        if (roundEnv.processingOver()) {
            return false;
        }


        System.out.println(TAG + " >>> process start ... ");

        // 传递过来的参数
        String rootDir = processingEnv.getOptions().get("root_project_dir");
        if (rootDir == null) {
            throw new RuntimeException("rootDir = " + rootDir); // 编译过程比较漫长，这么比较快的退出
        }

        // 获取所有标记了@Destination注解的类的信息
        Set<? extends Element> allDestinationElements = roundEnv.getElementsAnnotatedWith(Destination.class);
        System.out.println(TAG + " >>> all Destination Element count = " + allDestinationElements.size());
        // 当未收集到@Destination注解的时候，跳过后续流程
        if (allDestinationElements.size() < 1) {
            return false;
        }
        final String packName = "com.joyy.router.mapping";
        String className = "RouterMapping_" + System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packName).append(";\n\n");
        builder.append("import java.util.HashMap;").append("\n");
        builder.append("import java.util.Map;").append("\n");
        builder.append("public class ").append(className).append(" {").append("\n");
        builder.append("    public static Map<String, String> get() {").append("\n");
        builder.append("        Map<String, String> mapping = new HashMap<>();").append("\n");

        final JsonArray destinationJsonArray = new JsonArray();

        // 遍历所有注解信息, 挨个获取详细信息
        for (Element element : allDestinationElements) {
            final TypeElement typeElement = (TypeElement) element;
            // 尝试在当前类上获取@Destination的信息
            final Destination destination = typeElement.getAnnotation(Destination.class);
            if (destination == null) {
                continue;
            }
            final String url = destination.url();
            final String description = destination.description();
            final String realPath = typeElement.getQualifiedName().toString();
            System.out.println(TAG + ">>> url = " + url);
            System.out.println(TAG + ">>> description = " + description);
            System.out.println(TAG + ">>> realPath = " + realPath);

            builder.append("        ").append("mapping.put(")
                    .append("\"").append(url).append("\"").append(",")
                    .append("\"").append(realPath).append("\"")
                    .append(");").append("\n");


            JsonObject item = new JsonObject();
            item.addProperty("url", url);
            item.addProperty("description", description);
            item.addProperty("realPath", realPath);
            destinationJsonArray.add(item);
        }
        builder.append("        return mapping;").append("\n");
        builder.append("    }").append("\n"); // get()
        builder.append("}").append("\n"); // class

        String mappingFullClassName = packName + "." + className;
        System.out.println(mappingFullClassName);
        System.out.println(builder.toString());

        //写入自动生成的类到文件中
        try {
            JavaFileObject source = processingEnv.getFiler()
                    .createSourceFile(mappingFullClassName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException("Error while create file", e);
        }

        // 想创建一个资源文件
        try {
            System.out.println("[[[创建一个资源文件]]]");
            System.out.println(processingEnv.getLocale().getCountry());
            JavaFileObject source = processingEnv.getFiler().createSourceFile("kkkkk.string");
//            Writer writer = source.openWriter();
//            writer.write("xxxxxxxxxxxx111111");
//            writer.flush();
//            writer.close();
//            processingEnv.getFiler().getResource(StandardLocation.SOURCE_PATH, "", "config.")
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 写入JSON到本地文件中
        File rootDirFile = new File(rootDir);
        if (!rootDirFile.exists()) throw new RuntimeException("root_project_dir not exits");
        // 创建router_mapping子目录
        File routerFileDir = new File(rootDirFile, "router_mapping");
        if (!routerFileDir.exists()) routerFileDir.mkdirs();
        File mappingFile = new File(routerFileDir, "mapping_" + System.currentTimeMillis() + ".json");

        // 写入json的内容
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(mappingFile));
            String jsonStr = destinationJsonArray.toString();
            out.write(jsonStr);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new RuntimeException("Error while writing json ", e);
        }

        return false;
    }
}
