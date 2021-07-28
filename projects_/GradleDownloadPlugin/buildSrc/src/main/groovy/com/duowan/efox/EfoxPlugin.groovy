package com.duowan.efox


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.json.JSONObject

class EfoxPlugin implements Plugin<Project> {

    static void log(String msg) {
        println("[EfoxPlugin] $msg")
    }

    // 实现apply方法，注入插件的逻辑
    void apply(Project project) {
        log("apply(Porject project)")

        EfoxExtension extension = project.extensions.create('efoxdownload', EfoxExtension)

        // 创建一个新的task
        project.task('zhaojian') {
            setGroup("efox")
            log('zhaojian')
            doLast {
                log("zhaojian ${extension.message}")
                log("zhaojian ${project.getProjectDir().absolutePath} ")
                EFox efox = new EFox(extension, project)
                downloadEfox(project)
            }
        }
    }


    void downloadEfox(Project project) {
        //setDependsOn([clean])
        //http://multi-lang.duowan.com/multiLangBig/Teachee/iOS_1_9_0___1_9_0/ko.json
        long start = System.currentTimeMillis()
        File projectDir = project.getProjectDir() // 工程目录


        def projectPath = "Teachee___2_3_0" // 项目
//    def projectPath = "iOS_1_9_0___1_9_0"
        def key_values = "values"
        def key_values_ko = "values-ko"

        def valuesDir = [
                "$key_values"   : "en",
                "$key_values_ko": "ko",
                //"values-zh" : "zh",
        ]
        // 每个文件夹存放所有的key
        def listJsonObject = [
                "$key_values"   : new JSONObject(),
                "$key_values_ko": new JSONObject()
        ] //把所有的字符串都下载下来
        print("\n $listJsonObject")


//        File buildTmpValue = new File(buildDir, "tmp${File.separator}values") // 先保存到临时目录
//        buildTmpValue.deleteOnExit()
//        buildTmpValue.mkdirs()


        valuesDir.forEach({ value, path ->
            def pathJson = "${path}.json"
            def url_path = "http://multi-lang.duowan.com/multiLangBig/Teachee/${projectPath}/${pathJson}?time=${System.currentTimeSeconds()}"

            // 读取字符串
            JSONObject jo = new JSONObject(readStringFromUrl(url_path))
            JSONObject jo_data = jo.optJSONObject("data")
            listJsonObject.put(value, jo_data) // 保存所有的jsonObject， 要有过滤功能
        })

        print("\n $listJsonObject")

        // 以第一个为准， 读取文件
        if (!listJsonObject.isEmpty()) {
            def fjo = listJsonObject.get("$key_values")
            assert fjo instanceof JSONObject

            int count = fjo.length()
            // 先判断是否等长
            listJsonObject.forEach({ key, jo ->
                assert count == jo.length()
            })
            // 直接查看是否所有的key都相同,因为已经判断了length
            Iterator iter = fjo.keys()
            while (iter.hasNext()) { // 遍历所有的key
                def key = iter.next()
                print("\n key = $key")
                listJsonObject.forEach({ _, JSONObject jo ->
                    assert jo.has(key) // 不包含，直接崩溃，gradle时间太长
                })
            }

            // res/value文件夹
            def res_value = new StringBuilder()
                    .append(projectDir.absolutePath)
                    .append(File.separator).append("src")
                    .append(File.separator).append("main")
                    .append(File.separator).append("res")
                    .append(File.separator).toString()
            print("\nres_value = $res_value")

            // 写入新文件
            // values    : ["key":"value]
            // values-ko : ["key":"value"]
            listJsonObject.forEach({ key, JSONObject jo ->
                // 检查目录是否存在
                File file = new File(res_value, key)
                if (!file.exists()) file.mkdirs()
                // 删除原先生成的commonstring.xml， 这个不进行版本控制
                File xmlFile = new File(file, "commonstring.xml") // 要存放的地址
                println("[xmlFile] = ${xmlFile.absolutePath}")

                if (xmlFile.exists() && xmlFile.size() > 0) { // 存在，才做增量更新
                    // 增量更新
                    HashMap<String, String> hashMap = readXmlToHashMap(xmlFile) // 读取原有数据
                    hashMap = incremental(hashMap, jo)
                    writeHashMapToFile(xmlFile, hashMap)
                } else {
                    // 找到File
                    writeXmlToFile(xmlFile, jo) // 统一文件
                }
            })
        }

//    def branchName = getBranchName()
//    println "\n分支名称 ： $branchName"
        println "\n分支名称 ： ${System.currentTimeSeconds()}"

        long end = System.currentTimeMillis()
        println "time=${end - start}"
        println "time=${end - start}"
        println "time=${end - start}"
        println "time=${end - start}"
        println "time=${end - start}"
        println "time=${end - start}"
        println "time=${end - start}"
        println "time=${end - start}"
        println "time=${end - start}"
    }


    static HashMap<String, String> incremental(HashMap<String, String> hashMap, JSONObject jo) {
        // 1。 把JSONObject中的文件拿出来，
        //     有相同的，直接提示，手动删除
        //     增量更新的直接加入
        // 所有的内容
        def key = jo.keys()
        while (key.hasNext()) {
            def k = key.next()
            //TDDO assert hashMap.containsKey(k), "按照规定，efox和本地不能有重复，删除efox，保留本地【可手动修改本地】"
            def v = jo.getString(k)
            if (check_validate(k) && !hashMap.containsKey(k)) {
                println(">>>>[新增] key=$k, value=$v")
                hashMap.put(k, v) // 若没有此key，直接删除即可
            } else {
                println(">>>>[不能加入] key=$k, value=$v  (已有数据或者不合格Key数据)")
//            assert false, "不能有重复数据"
            }
//        if (check_validate(k)) {
//            bufferedWriter.writeLine(generatXmlStringitem(k, jo.getString(k)))
//        } else { //不符合要求
//            print("\n 不符合要求 ：$k ")
//        }
        }
        return hashMap
    }

    static String getBranchName() {
        def process = ("git branch").execute(null, getRootDir())
        def result = process.waitFor()
        if (result != 0) assert false, "没有找git分支!!!!"
        List<String> lines = process.text.readLines()
        String branchName = null
        if (lines.isEmpty()) {
            //branchName = 'master'
            assert false, "git branch 得到的结果为空!!!"
        } else {
            lines.each {
                if (it.startsWith("*")) {
                    branchName = it.substring(1)
                    // return
                }
            }
        }
        if (branchName == null) assert false, "错啦，没找到具体的分支！！！"
        return branchName
    }

// 从网络读取内容
    String readStringFromUrl(String url_path) {
        //https://blog.csdn.net/zhangmiao301/article/details/80839676
        URL url = new URL(url_path)
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection()
        httpURLConnection.setConnectTimeout(5000)
        httpURLConnection.setRequestMethod("GET")
        httpURLConnection.connect()
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))
//    String line = null
        StringBuffer stringBuffer = new StringBuffer()
        String json = "";
        while ((json = bufferedReader.readLine()) != null) {
            stringBuffer.append(json);
        }
        String rsp = new String(stringBuffer.toString().getBytes(), "UTF-8")
        bufferedReader.close()
        httpURLConnection.disconnect()
        print("\n---->$rsp")
        return rsp
    }

// 要做增量更新
    void writeHashMapToFile(File file, HashMap<String, String> map) {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)))
        bufferedWriter.writeLine("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
        bufferedWriter.writeLine("<resources>")
        map.entrySet().forEach({ entry ->
            if (check_validate(entry.getKey())) {
                bufferedWriter.writeLine(generatXmlStringitem(entry.getKey(), entry.getValue()))
            } else { //不符合要求
                print("\n 不符合要求 ：$entry ")
            }
        })
        bufferedWriter.writeLine("</resources>")
        bufferedWriter.flush()
        bufferedWriter.close()
    }


    void writeXmlToFile(File file, JSONObject jo) {
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false))) //
        // 重新写文件
        bufferedWriter.writeLine("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
        bufferedWriter.writeLine("<resources>")
        // 所有的内容
        def key = jo.keys()
        while (key.hasNext()) {
            def k = key.next()
            if (check_validate(k)) {
                bufferedWriter.writeLine(generatXmlStringitem(k, jo.getString(k)))
            } else { //不符合要求
                print("\n 不符合要求 ：$k ")
            }
        }
        bufferedWriter.writeLine("</resources>")
        bufferedWriter.flush()
        bufferedWriter.close()
    }

    static Boolean check_validate(String str) {
        for (i in 0..<str.length()) {
            int c = str.charAt(i)
            if (i == 0 && !Character.isLowerCase(c)) { //只能为小写字母
                return false
            } else if (!(Character.isLowerCase(c) || Character.isDigit(c) || '_' == c)) {
                return false
            }
        }
        return true
    }

    String generatXmlStringitem(String key, String value) {
        if (key == null || key.isEmpty()) {
            throw IllegalArgumentException("key 不能为空！！！")
        }
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("value 不能为空！！！")
        }
        def str = "        <string name=\"${key}\">${value}</string>"
        print("\n $str")
        return str
    }

// 把本地文件全部读出来
    static HashMap<String, String> readXmlToHashMap(File xmlFile) {
        assert xmlFile.exists() && xmlFile.size() > 0 //这里不能为空，按理说已经判断了
        println ">>>>>>>>>>>>>>>>>>>>"
        def xml = new XmlParser().parse(xmlFile) // 读出来
        println xml.children().size()
        println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        def hashMap = new LinkedHashMap() // 现在可以为空
        xml.children().forEach({
            if (it instanceof Node) {
                def key = it.attributes()['name']
                def value = ((List) it.value()).get(0)
                assert null == hashMap.put(key, value), "不能有重复的值，直接报错，请删除原先文件中的${key}"
            }
        })
        println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        println ">>>>>>>>>>>打印HashMap>>>>>>>>>>>>>>>>>"
        hashMap.forEach({ key, value ->
            println "$key =*******= $value"
        })
        println ">>>>>>>>>>>结束HashMap>>>>>>>>>>>>>>>>>"
        //println xml
        return hashMap
    }

    static void readXmlMethod(String url) {
        println ">>>>>>>>>>>>>>>>>>>>"
        println ">>>>>>>>>>>>>>>>>>>>"
        println ">>>>>>>>>>>>>>>>>>>>"
        println ">>>>>>>>>>>>>>>>>>>>"
        println ">>>>>>>>>>>>>>>>>>>>"
        println ">>>>>>>>>>>>>>>>>>>>"
        println ">>>>>>>>>>>>>>>>>>>>"
        println ">>>>>>>>>>>>>>>>>>>>"
        File f = new File(url);
        assert f.exists()


        def xml = new XmlParser().parse(f) // 读出来
        //println xml.attributes()
//    println xml.children()
        println xml.children().size()
        println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // 第一版
//    xml.children().forEach({
//        if (it instanceof NodeList) {
//            println "it instanceof NodeList"
//        } else if (it instanceof Node) {
//            println "it instanceof Node"
//        }
//    })
        // 第二版
//    xml.children().forEach({
//        if (it instanceof Node) {
//            if (it.value() instanceof List) {
//                List ll = (List) it.value()
//                println ll.get(0)
//            }
//        }
//    })
        // 第三版
//    xml.children().forEach({
//        if (it instanceof Node) {
//            def key = it.attributes()['name']
//            def value = ((List) it.value()).get(0)
//            println "$key \t\t====\t\t $value"
//            println()
//        }
//    })
//    println("xml.children() instanceof List => ${xml.children() instanceof List}")
        def hashMap = new LinkedHashMap()
        xml.children().forEach({
            if (it instanceof Node) {
                def key = it.attributes()['name']
                def value = ((List) it.value()).get(0)
                assert null == hashMap.put(key, value), "不能有重复的值，直接报错，请删除原先文件中的${key}"
            }
        })
        println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        println ">>>>>>>>>>>打印HashMap>>>>>>>>>>>>>>>>>"
        hashMap.forEach({ key, value ->
            println "$key =*******= $value"
        })
        println ">>>>>>>>>>>结束HashMap>>>>>>>>>>>>>>>>>"
        //println xml
    }
}
