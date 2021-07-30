package com.duowan.efox

import org.gradle.api.Project
import org.json.JSONObject

import java.text.SimpleDateFormat

class EFox {
    private final String TAG = "[EFOX]"
    static final String url = "http://multi-lang.duowan.com/multiLangBig/Teachee/%s/%s" //默认的

    static void log(String msg) {
        println("[EFox] $msg")
    }


    private StringBuilder logSb // 记录本地日志

    void logWrite(String msg) {
        def log = "[EFox] $msg"
        println(log)
        if (useLog) logSb.append(log).append("\n")
    }

    private final EfoxExtension extension //从外面传过来的参数
    private final Project project //这个项目


    // 项目地址
    private String efoxPath
    private String efoxDefaultValues
    private Map valuesDir
    private String resName
    private String resPath
    private String urlFomat
    private Map<String, String> valueReplace
    private boolean clearBefore
    private boolean useLog

    EFox(EfoxExtension extension, Project project) {
        this.extension = extension
        this.project = project

        resName = extension.resName //这个一般不改变，默认的就是 "commonstring.xml"
        efoxPath = extension.efoxPath //"Teachee___2_3_0" //需要修改
        efoxDefaultValues = extension.default_values //"values"
        // 所有要下载下来的目录， 应该外部传入
        valuesDir = extension.valuesDir //["values": "en", "values-ko": "ko"]
        urlFomat = extension.urlFomat //"http://multi-lang.duowan.com/multiLangBig/Teachee/%s/%s" // 地址模版
        resPath = extension.resPath //src/main/res
        valueReplace = extension.valueReplace //["&": "&amp;", "%@": "%s"]
        clearBefore = extension.clearBefore
        useLog = extension.useLog
    }

    // 下载
    void downloadEFox() {
        if (useLog) logSb = new StringBuilder()
        long startTime = System.nanoTime()
        // 下载所有内容
        Map<String, JSONObject> listJsonObject = readJSON()

        // 写入本地文件
        doWriteMapToFile(listJsonObject)
        long endTime = System.nanoTime()
        log("[EFOX] 更新完毕！！用时 = ${endTime - startTime}")
        if (useLog) writeLogToFile(new File(project.getProjectDir(), "log.txt"), logSb.toString())
    }

    // 写入本地文件
    private void doWriteMapToFile(Map<String, JSONObject> listJsonObject) {
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
                if (clearBefore) xmlFile.delete() //一般未false，增量更新
                log("源文件已经存在， 做增量更新")
                log("正在读取本地xml xmlFile = ${xmlFile.absolutePath}")
                HashMap<String, String> map = readXmlToHashMap(xmlFile)
                log("正在获取增量数据 ...")
                map = incremental(map, jo) // 获取的增量数据
                log("正在写入本地文件")
                writeHashMapToFile(xmlFile, map)
            } else {
                log("源文件不存在， 创建新文件")
                writeXmlToFile(xmlFile, jo)
            }
        })
    }

    private HashMap<String, String> incremental(HashMap<String, String> hashMap, JSONObject jo) {
        // 1。 把JSONObject中的文件拿出来，
        //     有相同的，直接提示，手动删除
        //     增量更新的直接加入
        // 所有的内容
        def key = jo.keys()
        while (key.hasNext()) {
            def k = key.next()
            def v = jo.getString(k)
            if (check_validate(k)) {
                def oldV = hashMap.get(k, null)
                if (oldV != null && oldV != v) {
                    logWrite("[修改该值(替换词忽略)] $k  oldv=$oldV  newV=$v")
                    hashMap.put(k, v)// 要覆盖原来的
                } else if (oldV == null) {
                    logWrite(">>>>[新增] key=$k, value=$v")
                    hashMap.put(k, v)// 要覆盖原来的
                } else {
                    log(">>>>[保持不变] key=$k, value=$v")
                }
            } else {
                logWrite(">>>>[不能加入] key=$k, value=$v  (已有数据或者不合格Key数据)")
            }
        }
        return hashMap
    }

    // 把本地文件全部读出来
    private HashMap<String, String> readXmlToHashMap(File xmlFile) {
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
    private void writeHashMapToFile(File file, HashMap<String, String> map) {
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
    private void writeXmlToFile(File file, JSONObject jo) {
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


    // 将xml写入本地文件
    private void writeLogToFile(File file, String log) {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true))) //
        // 重新写文件
        bufferedWriter.writeLine("<-------------------------------->")
        bufferedWriter.writeLine("<-----此文件最好不要加入版本控制----->")
        bufferedWriter.writeLine("<------------此文件可以随便删掉----->")
        def time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
        bufferedWriter.writeLine("<-----$time----->")
        bufferedWriter.writeLine(log)
        bufferedWriter.writeLine("<------------->\n\n\n\n")
        bufferedWriter.flush()
        bufferedWriter.close()
    }

    private String replaceValue(String value) {
        if (value == null || value.size() == 0) return ""
        valueReplace.forEach({ k, v ->
            value = value.replace(k, v)//value.replaceAll(k, v)
        })
        return value
    }

    private Map readJSON() {
        Map<String, JSONObject> listJsonObject = new HashMap<>()
        valuesDir.forEach({ value, path ->
            // def url_path = "http://multi-lang.duowan.com/multiLangBig/Teachee/${projectPath}/${pathJson}?time=${System.currentTimeSeconds()}"
            def json = readStringFromUrl(getEfoxUrl(efoxPath, "${path}.json"))
            // 下载并读取字符串
            JSONObject jo = new JSONObject(json)
            JSONObject jo_data = jo.optJSONObject("data")
            listJsonObject.put(value, jo_data) // 保存所有的jsonObject， 要有过滤功能
        })
        return listJsonObject
    }


    private String getEfoxUrl(String efoxProjectPath, String pathJson) {
        //http://multi-lang.duowan.com/multiLangBig/Teachee/iOS_1_9_0___1_9_0/ko.json
//        return "http://multi-lang.duowan.com/multiLangBig/Teachee/${efoxProjectPath}/${pathJson}?time=${time}"
        def baseUrl = String.format(urlFomat, efoxProjectPath, pathJson);
        return "$baseUrl?time=${System.currentTimeSeconds()}" // 加time，为了防止缓存， ios这么加的
    }

    // 从网络读取内容
    private String readStringFromUrl(String url_path) {
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


    String generatXmlStringitem(String key, String value) {
        if (key == null || key.isEmpty()) {
            throw IllegalArgumentException("key 不能为空！！！")
        }
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("value 不能为空！！！")
        }
        String v = replaceValue(value)
        def str = "    <string name=\"${key}\">${v}</string>"
        print("\n $str")
        return str
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

}