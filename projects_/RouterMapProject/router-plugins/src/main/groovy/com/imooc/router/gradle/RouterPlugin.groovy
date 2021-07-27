package com.imooc.router.gradle

import com.android.build.api.transform.Transform
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.google.gson.Gson
import org.gradle.api.Plugin
import org.gradle.api.Project

//import groovy.json.JsonSlurper

class RouterPlugin implements Plugin<Project> {

    static void log(String msg) {
        println("[RouterPlugin] $msg")
    }

    // 实现apply方法，注入插件的逻辑
    void apply(Project project) {
        log("apply(Project project) --> ${project}")

        // 注册 Transform
        if (project.plugins.hasPlugin(AppPlugin)) {
            AppExtension appExtension = project.extensions.getByType(AppExtension)
            Transform transform = new RouterMappingTransform()
            appExtension.registerTransform(transform)
        } else {
            log("nnonononononono  project.plugins.hasPlugin(AppPlugin)")
        }

        // 1. 自动帮助用户传递路径参数到注解处理器中
        if (project.extensions.findByName("kapt") != null) {
            log("project.extensions.findByName(kapt) 不等于空")
            project.extensions.findByName("kapt").arguments {
                arg("root_project_dir", project.rootProject.projectDir.absolutePath)
                arg("module_name", project.name)
            }
        } else {
            log("project.extensions.findByName(kapt) 等于空")
        }


        new File(project.rootProject.projectDir, "router_mapping").deleteDir()

        // 2. 实现旧的构建产物的自动清理
//        project.clean.doFirst {
//            log("project.clean.doFirst")
//            // 删除 上一次构建生成的 router_mapping目录
//            File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
//
//            if (routerMappingDir.exists()) {
//                routerMappingDir.deleteOnExit()
//            }
//
//        }

        if (!project.plugins.hasPlugin(AppPlugin)) {
            return
        }

        println("I am from RouterPlugin, apply from ${project.name}")

        project.getExtensions().create("router", RouterExtension)

        project.afterEvaluate {
            RouterExtension extension = project["router"] //DSL 从gradle声明
            println("用户设置的WIKI路径为 : ${extension.wikiDir}")

            // 3. 在javac任务 (compileDebugJavaWithJavac) 后，汇总生成文档
            project.tasks.findAll { task ->
                task.name.startsWith('compile') && task.name.endsWith('JavaWithJavac')
            }.each { task ->
                task.doLast {
                    File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
                    if (!routerMappingDir.exists()) {
                        return
                    }
                    File[] allChildFiles = routerMappingDir.listFiles()
                    if (allChildFiles.length < 1) {
                        return
                    }
                    StringBuilder markdownBuilder = new StringBuilder()
                    markdownBuilder.append("# 页面文档\n\n")
                    allChildFiles.each { child ->
                        log(child.name)
                        // 读取到了json文件, 可能有好几个
                        if (child.name.endsWith(".json")) {

                            String str = new String(com.android.dex.util.FileUtils.readFile(child), "UTF-8")
                            log(str)
                            List<JsonRootBean> bean = new Gson().fromJson(str, JsonRootBean.getType())
                            log(bean.toString())
                            bean.each {
                                markdownBuilder.append("## ${it.description} \n")
                                markdownBuilder.append("- url: ${it.url} \n")
                                markdownBuilder.append("- realPath: ${it.realPath} \n\n")
                            }
                        }

                    }

                    File wikiFileDir = new File(extension.wikiDir)
                    if (!wikiFileDir.exists()) {
                        wikiFileDir.mkdir()
                    }

                    File wikiFile = new File(wikiFileDir, "页面文档.md")
                    if (wikiFile.exists()) {
                        wikiFile.delete()
                    }

                    wikiFile.write(markdownBuilder.toString())

                }

            }
        }
    }

}
