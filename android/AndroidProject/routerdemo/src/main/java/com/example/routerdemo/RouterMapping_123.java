package com.example.routerdemo;

import java.util.HashMap;
import java.util.Map;

/**
 * Time:2021/7/8 19:21
 * Author:
 * Description:
 */
public class RouterMapping_123 {

    public static Map<String, String> get() {
        Map<String, String> mapping = new HashMap<>();

        mapping.put("router://xxxx", "com.xxx.Activity");
        mapping.put("router://xxxx2", "com.xxx.Activity2");
        mapping.put("router://xxxx3", "com.xxx.Activity3");

        return mapping;
    }

}
