package com.imooc.router.processor;

/**
 * Time:2021/7/23 11:39
 * Author:
 * Description:
 */
public class RouterPathFactory {

    final private StringBuilder builder;
    final private String packageName;
    final private String className;

    public RouterPathFactory(String packageName, String className) {
        assert !(className == null || className.isBlank());
        this.packageName = packageName;
        this.className = className;
        builder = new StringBuilder();
        builder.append("package com.imooc.router.mapping;\n\n");
    }

    public void fieldAdd(String url) {
        // public static final String ROUTER_FORACTIVITY = "router://FourActivity";
        builder.append("    public static final String ").append(getFiledName(url)).append(" = \"").append(url).append(
            "\";\n");
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

    public String getFullPath() {
        return packageName + "." + className;
    }

    // 开始创建一个class
    public void beginClass() {
        builder.append("public class ").append(className).append(" {\n\n");
    }

    // 结束
    public void endClass() {
        builder.append("}\n");
    }

    @Override
    public String toString() {
        if (builder != null) {
            return builder.toString();
        } else {
            return "[null]";
        }
    }
}
