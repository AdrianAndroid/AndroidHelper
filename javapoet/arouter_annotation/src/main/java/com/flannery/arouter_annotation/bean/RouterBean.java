package com.flannery.arouter_annotation.bean;

import javax.lang.model.element.Element;

public class RouterBean {

    public enum TypeEnum {
        ACTIVITY
    }

    private TypeEnum typeEnum;
    private Element element;
    private Class<?> myClass;
    private String path;
    private String group;

    // TODO 以下是一组 Get 方法
    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public Element getElement() {
        return element;
    }

    public Class<?> getMyClass() {
        return myClass;
    }

    public String getPath() {
        return path;
    }

    public String getGroup() {
        return group;
    }

    // TODO 以下是一组 Set 方法
    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void setMyClass(Class<?> myClass) {
        this.myClass = myClass;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private RouterBean(TypeEnum typeEnum, /*Element element,*/ Class<?> myClass, String path, String group) {
        this.typeEnum = typeEnum;
        // this.element = element;
        this.myClass = myClass;
        this.path = path;
        this.group = group;
    }

    // 对外暴露
    // 对外提供简易版构造方法，主要是为了方便APT生成代码
    public static RouterBean create(TypeEnum type, Class<?> clazz, String path, String group) {
        return new RouterBean(type, clazz, path, group);
    }

    // 构建者模式相关
    private RouterBean(Builder builder) {
        this.typeEnum = builder.type;
        this.element = builder.element;
        this.myClass = builder.clazz;
        this.path = builder.path;
        this.group = builder.group;
    }

    /**
     * 构建者模式
     */
    public static class Builder {

        // 枚举类型：Activity
        private TypeEnum type;
        // 类节点
        private Element element;
        // 注解使用的类对象
        private Class<?> clazz;
        // 路由地址
        private String path;
        // 路由组
        private String group;

        public Builder addType(TypeEnum type) {
            this.type = type;
            return this;
        }

        public Builder addElement(Element element) {
            this.element = element;
            return this;
        }

        public Builder addClazz(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder addPath(String path) {
            this.path = path;
            return this;
        }

        public Builder addGroup(String group) {
            this.group = group;
            return this;
        }

        // 最后的build或者create，往往是做参数的校验或者初始化赋值工作
        public RouterBean build() {
            if (path == null || path.length() == 0) {
                throw new IllegalArgumentException("path必填项为空，如：/app/MainActivity");
            }
            return new RouterBean(this);
        }
    }

    @Override
    public String toString() {
        return "RouterBean{" +
                "path='" + path + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
