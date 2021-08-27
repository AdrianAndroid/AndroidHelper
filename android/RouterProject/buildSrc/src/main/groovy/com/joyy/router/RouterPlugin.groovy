package com.joyy.router


import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {

    // 实现apply方法，注入插件的逻辑
    @Override
    void apply(Project project) {
        println("I am from RouterPlugin, apply from ${project.name}")

        // 自动帮助用户传递路径参数到注解处理器
        def kapt = project.extensions.findByName("kapt")
        if(kapt != null) {
            kapt.arguments {
                arg("root_project_dir", project.rootProject.projectDir.absolutePath)
            }
        }

        project.getExtensions().create("router", RouterExtension) //使用Extension
        project.afterEvaluate {
            // 拿到配置的router
            RouterExtension extension = project["router"]
            println("用户设置的WIKI路径为：${extension.wikiDir}")
        }
    }
}