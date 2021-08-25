package com.joyy.router.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Time:2021/7/5 20:14
 * Author:
 * Description: 当前可以修饰的元素，此处标识可以用于标记在类上面
 */
@Target({ElementType.TYPE}) // 元注解
@Retention(RetentionPolicy.CLASS)// 能被保留到什么时间点
public @interface Destination {
    String url(); //页面的URL,不能为空
    String description();//页面的描述
}
