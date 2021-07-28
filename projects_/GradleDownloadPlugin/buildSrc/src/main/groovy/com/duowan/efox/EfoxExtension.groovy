package com.duowan.efox

class EfoxExtension {
    String message = "Hello this is my custom plugin ..."
    String default_values = "values"
    boolean check_K_V = false // 检查键值对
    String urlFomat = "http://multi-lang.duowan.com/multiLangBig/Teachee/%s/%s" // 地址模版
    String resName = "commonstring.xml" //通用的名称
    String resPath = "src/main/res"
    String efoxPath = "Teachee___2_3_0" //需要修改
    Map<String, String> valuesDir = ["values": "en", "values-ko": "ko"] //需要外部传入
}