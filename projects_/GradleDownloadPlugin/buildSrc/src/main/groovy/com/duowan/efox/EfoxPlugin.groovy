package com.duowan.efox

import org.gradle.api.Plugin
import org.gradle.api.Project

class EfoxPlugin implements Plugin<Project> {

    static void log(String msg) {
        println("[EfoxPlugin] $msg")
    }

    // 实现apply方法，注入插件的逻辑
    void apply(Project project) {
        log("apply(Porject project)")

        EfoxExtension extension = project.extensions.create('efox', EfoxExtension)

        // 创建一个新的task
        project.task('efoxdownload') {
            setGroup("efox")
            log('efoxdownload')
            doLast {
                log(" ${extension.message}")
                log(" ${project.getProjectDir().absolutePath} ")
                EFox efox = new EFox(extension, project)
                efox.downloadEFox()
                //downloadEfox(project)
            }
        }
    }
}
