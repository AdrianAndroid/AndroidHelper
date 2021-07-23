package com.imooc.router.processor;

/**
 * Time:2021/7/23 11:39
 * Author:
 * Description:
 */
public class RouterMappingFactory {

    private StringBuilder builder;

    public RouterMappingFactory() {
        builder = new StringBuilder();
        builder.append("package com.imooc.router.mapping;\n\n");
        builder.append("import java.util.HashMap;\n");
        builder.append("import java.util.Map;\n\n");
    }

    // 开始创建一个class
    public void beginClass(String className) {
        assert !(className == null || className.isBlank());
        builder.append("public class ").append(className).append(" {\n\n");
    }

    // 结束
    public void endClass() {
        builder.append("}\n");
    }

    // 开始Get方法
    public void methodBeginGet() {
        builder.append("    public static Map<String, String> get() {\n\n");
        builder.append("        Map<String, String> mapping = new HashMap<>();\n\n");
    }

    // 中间添加
    public void methodAppendGet(String url, String realPath) {
        builder.append("        ")
                .append("mapping.put(")
                .append("\"" + url + "\"")
                .append(", ")
                .append("\"" + realPath + "\"")
                .append(");\n");
    }

    public void methodEndGet() {
        builder.append("        return mapping;\n");
        builder.append("    }\n");
    }

}
