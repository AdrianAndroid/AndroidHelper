package com.joyy.router

import java.util.jar.JarEntry
import java.util.jar.JarFile

import static com.joyy.router.RouterPlugin.log

class RouterMappingController2 {
    private static final String PACKAGE_NAME = 'com/joyy/router/mapping'
    private static final String CLASS_NAME_PREFIX = 'RouterMapping_'
    private static final String CLASS_FILE_SUFFIX = '.class'

    //获取收集好的映射表类名
    final Set<String> mappingClassNames = new HashSet<>()

    // 是否是我们要找的名称 RouterMapping_xxxxx.class
    private boolean isValidateName(File classFile) {
        return classFile.absolutePath.contains(PACKAGE_NAME)
                && classFile.name.startsWith(CLASS_NAME_PREFIX)
                && classFile.name.endsWithIgnoreCase(CLASS_FILE_SUFFIX)
    }

    void collect(File classFile) {
        if (classFile == null || !classFile.exists()) return
        if (classFile.isFile()) { // 文件
            log("---->${classFile.name}")
            if (isValidateName(classFile)) {
                // RouterMapping_xxxxx.class --> RouterMapping_xxxxx
                String className = classFile.name.replace(CLASS_FILE_SUFFIX, "")
                mappingClassName.add(className)
            }
        } else { // 文件夹
            classFile.listFiles().each { collect(it) }
        }

    }


    // 是否是我们要找的名称 RouterMapping_xxxxx.class
    private boolean isValidateName(String file) {
        return file.contains(PACKAGE_NAME)
                && file.contains(CLASS_NAME_PREFIX)
                && file.contains(CLASS_FILE_SUFFIX)
    }

    void collectJarFile(File jarFile) {
        Enumeration enumeration = new JarFile(jarFile).entries()
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) enumeration.nextElement()
            String entryName = jarEntry.getName()
            log("------> $entryName")
            if (isValidateName(entryName)) {
                String className = entryName.replace(PACKAGE_NAME, "").replace("/", "").replace(CLASS_FILE_SUFFIX, "")
                mappingClassNames.add(className)
            }
        }
    }

}