package com.joyy.router

import com.android.build.api.transform.Transform
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {

    static void log(String msg) {
        println("[RouterPlugin]  $msg")
    }

    // 实现apply方法，注入插件的逻辑
    @Override
    void apply(Project project) {
        println("I am from RouterPlugin, apply from ${project.name}")

        log("打印所有的Transform（学习用）")
        project.plugins.each {
            log("$it")
        }

        log("注册Transform")
        if (project.plugins.hasPlugin(AppPlugin.class)) {
            log("包含AppPlugin")
            AppExtension appExtension = project.extensions.getByType(AppExtension)
            Transform transform = new RouterMappingTransform()
            appExtension.registerTransform(transform)
        } else {
            log("模块:${project.name}中 不包含AppPlugin")
        }

        log("自动帮助用户传递路径参数到注解处理器")
        def kapt = project.extensions.findByName("kapt")
        if (kapt != null) {
            kapt.arguments {
                arg("root_project_dir", project.rootProject.projectDir.absolutePath)
            }
        }

        log("构建旧的构建产物的自动清理 project.clean.doFirst")
        project.clean.doFirst { // task clean
            // 删除上一次构建生成的router_mapping目录
            File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
            if (routerMappingDir.exists()) routerMappingDir.deleteDir()
        }

        log("判断是否是主工程模块，如果不是返回 : ${project.plugins.hasPlugin(AppPlugin)}")
        if (!project.plugins.hasPlugin(AppPlugin)) {
            return
        }

        log("创建router扩展")
        project.getExtensions().create("router", RouterExtension) //使用Extension
        project.afterEvaluate {
            log("拿到配置的router")
            RouterExtension extension = project["router"]
            println("用户设置的WIKI路径为：${extension.wikiDir}")
            // 生成页面说明文档
        }
    }
}