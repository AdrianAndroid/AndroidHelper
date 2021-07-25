package com.immoc.router.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import groovy.json.JsonSlurper

class RouterPlugin implements Plugin<Project> {

    // 实现apply方法，注入插件的逻辑
    @Override
    void apply(Project project) {
        // 1. 自动的帮助用户传递路径参数到注解处理器中
        // 2. 实现旧的构建产物的自动清理
        // 3。在javac任务后汇总生成文档

        //kapt {
        //    arguments {
        //        arg("root_project_dir", rootProject.projectDir.absolutePath)
        //    }
        //}
        // 1. 自动的帮助用户传递路径参数到注解处理器中
        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg("root_project_dir", project.rootProject.projectDir.absolutePath)
            }
        }
        // 2. 实现旧的构建产物的自动清理
        project.clean.doFirst {
            // 删除上一次生成的router_mapping目录
            File routerMappingDir = new File(project.rootProject.projectDir.absolutePath, "router_mapping")
            if (routerMappingDir.exists()) {
                routerMappingDir.deleteDir()
            }

        }

        println("I am from RouterPlugin, apply from ${project.name}")

        project.getExtensions().create("router", RouterExtension) //使用Extension
        project.afterEvaluate {
            // 拿到配置的router
            RouterExtension extension = project["router"]
            println("用户设置的WIKI路径为：${extension.wikiDir}")

            // 3。在javac任务后汇总生成文档
            // compileDebugJavaWithJavac
            project.tasks.findAll { task ->
                task.name.startsWith("compile") && task.name.endsWith("JavaWithJavac")
            }.each { task ->
                task.doLast {
                    File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
                    if (!routerMappingDir.exists()) {
                        return
                    }
                    // 所有子文件
                    File[] allChildFiles = routerMappingDir.listFiles()
                    if (allChildFiles.length < 1) {
                        return
                    }
                    StringBuilder markdownBuilder = new StringBuilder()
                    markdownBuilder.append("# 页面文档\n\n")
                    allChildFiles.each { child ->
                        if (child.name.endsWith(".json")) {
                            JsonSlurper jsonSlurper = new JsonSlurper()
                            def content = jsonSlurper.parse(child)
                            content.each { innerContent ->
                                def url = innerContent['url']
                                def description = innerContent['description']
                                def realPath = innerContent['realPath']
                                markdownBuilder.append("## $description \n")
                                markdownBuilder.append("- url: $url \n")
                                markdownBuilder.append("- realPath: $realPath \n")
                            }

                        }
                    }
                    File wikiFileDir = new File(extension.wikiDir)
                    if (!wikiFileDir.exists()) {
                        wikiFileDir.mkdir()
                    }
                    File wikiFile = new File(wikiFileDir, "页面文档")
                    if (wikiFile.exists()) {
                        wikiFile.delete()
                    }
                    wikiFile.write(markdownBuilder.toString())
                }
            }
        }
    }
}