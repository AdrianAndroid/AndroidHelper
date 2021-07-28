package com.duowan.efox

import org.gradle.api.Project
import org.json.JSONObject

class EFox {
    final String TAG = "[EFOX]"
    static final String url = "http://multi-lang.duowan.com/multiLangBig/Teachee/%s/%s" //默认的

    static void log(String msg) {
        println("[EFox] $msg")
    }

    final EfoxExtension extension //从外面传过来的参数
    final Project project //这个项目

    // 项目地址
    String efoxPath
    String efoxDefaultValues
    Map valuesDir
    String resName
    String resPath
    String urlFomat

    EFox(EfoxExtension extension, Project project) {
        this.extension = extension
        this.project = project

        resName = extension.resName //这个一般不改变，默认的就是
        efoxPath = extension.efoxPath
        efoxDefaultValues = extension.default_values
        // 所有要下载下来的目录， 应该外部传入
        valuesDir = extension.valuesDir
        urlFomat = extension.urlFomat
        resPath = extension.resPath //src/main/res
    }

    // 下载
    void downloadEFox() {
        long startTime = System.currentTimeMillis()
        // 下载所有内容
        Map<String, JSONObject> listJsonObject = readJSON()

        // 写入本地文件
        writeMapToFile(listJsonObject)
        long endTime = System.currentTimeSeconds()
        log("[EFOX] 更新完毕！！用时 = ${endTime - startTime}")
    }

    // 写入本地文件
    void writeMapToFile(Map<String, JSONObject> listJsonObject) {
        // 拿到第一个基准
        def fjo = listJsonObject.get(efoxDefaultValues)
        assert fjo instanceof JSONObject // 判断是否是JSONObject
        // 是否等长
//        int count = fjo.length()
//        listJsonObject.forEach({ key, jo ->
//            assert count == jo.length()
//        })

        // 创建目录
        File resFile = new File(project.getProjectDir(), resPath)
        if (resFile.mkdirs()) {// 就创建
            log("创建了文件夹 resPath = ${resFile.absolutePath}")
        } else {
            log("不用创建文件夹 resPath = ${resFile.absolutePath}")
        }
        // 写入新文件
        listJsonObject.forEach({ key, JSONObject jo ->
            // 检查目录是否存在
            File file = new File(resFile, key)
            if (file.mkdirs()) {
                log("创建文件夹 ${file.absolutePath}")
            } else {
                log("文件夹已存在 ${file.absolutePath}")
            }
            File xmlFile = new File(file, resName)
            if (xmlFile.exists() && xmlFile.size() > 0) { // 存在才做增量更新
                log("源文件已经存在， 做增量更新")
                log("正在读取本地xml xmlFile = ${xmlFile.absolutePath}")
                HashMap<String, String> map = readXmlToHashMap(xmlFile)
                log("正在获取增量数据 ...")
                map = incremental(map, jo) // 获取的增量数据
                log("正在写入本地文件")
                writeMapToFile(map)
            } else {
                log("源文件不存在， 创建新文件")
                writeXmlToFile(xmlFile, jo)
            }
        })
    }

    HashMap<String, String> incremental(HashMap<String, String> hashMap, JSONObject jo) {
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

    // 把本地文件全部读出来
    HashMap<String, String> readXmlToHashMap(File xmlFile) {
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

    // 将xml写入本地文件
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

    Map readJSON() {
        Map<String, JSONObject> listJsonObject = new HashMap<>()
        valuesDir.forEach({
            // def url_path = "http://multi-lang.duowan.com/multiLangBig/Teachee/${projectPath}/${pathJson}?time=${System.currentTimeSeconds()}"
            def json = readStringFromUrl(getEfoxUrl(projectPath, "${path}.json"))
            // 下载并读取字符串
            JSONObject jo = new JSONObject(json)
            JSONObject jo_data = jo.optJSONObject("data")
            listJsonObject.put(value, jo_data) // 保存所有的jsonObject， 要有过滤功能
        })
        return listJsonObject
    }


    String getEfoxUrl(String efoxProjectPath, String pathJson) {
        //http://multi-lang.duowan.com/multiLangBig/Teachee/iOS_1_9_0___1_9_0/ko.json
//        return "http://multi-lang.duowan.com/multiLangBig/Teachee/${efoxProjectPath}/${pathJson}?time=${time}"
        def baseUrl = String.format(Efox.url, efoxProjectPath, pathJson);
        return "$baseUrl?time=${System.currentTimeSeconds()}" // 加time，为了防止缓存， ios这么加的
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
        StringBuffer stringBuffer = new StringBuffer()
        String json = "";
        while ((json = bufferedReader.readLine()) != null) {
            stringBuffer.append(json);
        }
        String rsp = new String(stringBuffer.toString().getBytes(), "UTF-8")
        bufferedReader.close()
        httpURLConnection.disconnect()
        log("---->$rsp")
        return rsp
    }
}