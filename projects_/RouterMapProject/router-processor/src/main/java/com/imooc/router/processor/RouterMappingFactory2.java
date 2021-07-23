package com.imooc.router.processor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

/**
 * Time:2021/7/23 11:39
 * Author:
 * Description:
 */
public class RouterMappingFactory2 {

    // final private String packageName;
    // final private String className;
    private final Map<String, DestinationImpl> map;

    private void log(String msg) {
        System.out.println("[RouterMappingFactory2] " + msg);
    }

    public RouterMappingFactory2(Map<String, DestinationImpl> map) {
        this(map, null, null);
    }

    public RouterMappingFactory2(Map<String, DestinationImpl> map, String packageName, String className) {
        this.map = map; //存储所有的类

        // 下面可以为空
        // this.packageName = packageName;
        // this.className = className;
    }
    //
    // public String getFullPath() {
    //     return packageName + "." + className;
    // }
    //
    // public String getPackageName() {
    //     return packageName;
    // }
    //
    // public String getClassName() {
    //     return className;
    // }

    // 包括 field 和 get
    public void generateClass_field_get(Filer filer, Boolean write, String packageName, String className) {
        log("generateClass_field_get");
        StringBuilder builder = new StringBuilder();
        classHeader(builder); //头部
        beginClass(builder, className); // 类
        // filed
        for (String key : map.keySet()) {
            fieldAdd(builder, key);
        }
        // get
        methodBeginGet(builder);
        for (DestinationImpl destImpl : map.values()) {
            methodAppendGet(builder, destImpl.url, destImpl.realPath);
        }
        methodEndGet(builder);

        endClass(builder); // end 类

        writeToFile(filer, packageName, className, builder.toString());
    }

    private String getValidateClassNameFromModuleName(String module_name) {
        StringBuilder sb = new StringBuilder();
        sb.append("RouterPath_");
        int length = module_name.length();
        for (int i = 0; i < length; i++) {
            char c = module_name.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // 包括 field 和 get
    public void generateClass_field_only(Filer filer, String packageName, String module_name) {
        log("generateClass_field_only");
        String className = getValidateClassNameFromModuleName(module_name);
        StringBuilder builder = new StringBuilder();
        classHeader(builder); //头部
        annotationClass(builder, "此类主要方便自己模块的使用，最好不要其他模块使用！！");
        beginClass(builder, className); // 类
        // filed
        for (String key : map.keySet()) {
            fieldAdd(builder, key);
        }
        // get
        // methodBeginGet(builder);
        // for (DestinationImpl destImpl : map.values()) {
        //     methodAppendGet(builder, destImpl.url, destImpl.realPath);
        // }
        // methodEndGet(builder);

        endClass(builder); // end 类

        writeToFile(filer, packageName, className, builder.toString());
    }

    public void generateClass_get(Filer filer, String packageName, String className) {
        log("generateClass_get");
        StringBuilder builder = new StringBuilder();
        classHeader(builder); //头部
        beginClass(builder, className); // 类
        // get
        methodBeginGet(builder);
        for (DestinationImpl destImpl : map.values()) {
            methodAppendGet(builder, destImpl.url, destImpl.realPath);
        }
        methodEndGet(builder);

        endClass(builder); // end 类

        writeToFile(filer, packageName, className, builder.toString());
    }

    public void generateJsonAndWrite(String root_project_dir) {
        log("generateJsonAndWrite");
        // 将要自动生成的类的类名
        final JsonArray destinationJsonArray = new JsonArray();
        for (DestinationImpl value : map.values()) {
            JsonObject item = new JsonObject();
            item.addProperty("url", value.url);
            item.addProperty("description", value.description);
            item.addProperty("realPath", value.realPath);

            destinationJsonArray.add(item);
        }


        // 检测父目录是否存在
        File rootDirFile = new File(root_project_dir);
        if (!rootDirFile.exists()) {
            throw new RuntimeException("root_project_dir not exist!");
        }

        // 创建 router_mapping 子目录
        File routerFileDir = new File(rootDirFile, "router_mapping");
        if (!routerFileDir.mkdirs()) {
            log("[Error] router_mapping 没有创建成功");
            //throw new RuntimeException("router_mapping not exist!");
        } else {
            File mappingFile = new File(routerFileDir, "mapping_" + System.currentTimeMillis() + ".json");
            writeToFile(mappingFile, destinationJsonArray.toString());
        }
    }

    private void writeToFile(File file, String jsonStr) {
        log("writeToFile(File file, String jsonStr)");
        // 写入json内容
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(jsonStr);
            out.flush();
            out.close();
        } catch (Throwable throwable) {
            throw new RuntimeException("Error while writing json", throwable);
        }
    }

    private void writeToFile(Filer filer, String packageName, String className, String builder) {
        log("writeToFile(Filer filer, String packageName, String className, String builder)");
        if (filer != null) {
            try {
                JavaFileObject source = filer.createSourceFile(getFullPath(packageName, className));
                Writer writer = source.openWriter();
                writer.write(builder);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                log("[Error] packageName=" + packageName + " ,className=" + className + "  " + ex.toString());
                throw new RuntimeException("Error while create file", ex);
            }
        }
    }

    private String getFullPath(String packageName, String className) {
        return packageName + "." + className;
    }

    public void classHeader(StringBuilder builder) {
        builder.append("package com.imooc.router.mapping;\n\n");
        builder.append("import java.util.HashMap;\n");
        builder.append("import java.util.Map;\n\n");
    }

    public void fieldAdd(StringBuilder builder, String url) {
        // public static final String ROUTER_FORACTIVITY = "router://FourActivity";
        builder.append("    public static final String ").append(getFiledName(url)).append(" = \"").append(url)
                .append("\";\n");
    }

    private String getFiledName(String url) {
        int len = url.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = url.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }

    public void annotationClass(StringBuilder builder, String annotation) {
        builder.append("/**");
        builder.append(" * ").append(annotation);
        builder.append(" */");
    }

    // 开始创建一个class
    // annotation 给类上边添加注释
    public void beginClass(StringBuilder builder, String className) {
        builder.append("public class ").append(className).append(" {\n\n");
    }

    // 结束
    public void endClass(StringBuilder builder) {
        builder.append("}\n");
    }

    // 开始Get方法
    public void methodBeginGet(StringBuilder builder) {
        builder.append("\n\n");
        builder.append("    public static Map<String, String> get() {\n\n");
        builder.append("        Map<String, String> mapping = new HashMap<>();\n\n");
    }

    // 中间添加
    public void methodAppendGet(StringBuilder builder, String url, String realPath) {
        builder.append("        ")
                .append("mapping.put(")
                .append("\"" + url + "\"")
                .append(", ")
                .append("\"" + realPath + "\"")
                .append(");\n");
    }

    public void methodEndGet(StringBuilder builder) {
        builder.append("        return mapping;\n");
        builder.append("    }\n");
    }
}
