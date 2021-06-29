package com.temp;

/**
 * Time:2021/6/29 17:42
 * Author:
 * Description:
 */
public class TMPClass {

    public static void main(String[] args) {
        System.out.println(" ".startsWith("\\s"));
        String str = "I am   a   good     boy!    ";
        String newStr = str.replaceAll("\\s{2,}", " ").trim();
        System.out.println(newStr);
        System.out.println("90832aviriver jfiejo".startsWith("[0-9]"));
    }

}
