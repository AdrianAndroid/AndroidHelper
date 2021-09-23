package com.flannery.string;

import java.util.ArrayList;
import java.util.List;

public class A14_longest_common_prefix {

    public static void main(String[] args) {

    }

    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();//公共字符串
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            Character c = null;
            for (int j = 0; j < strs.length; j++) {
                String str = strs[j];
                if (i >= str.length()) {
                    list.add(sb.toString());
                    return max(list);
                }
                if(c == null) {
                    c = str.charAt(i);
                } else if(c != str.charAt(i)) {
                    //不相同

                    break;
                }

            }

            i++;
        }
    }

    private String max(List<String> list) {
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.length() > str.length()) {
                str = s;
            }
        }
        return str;
    }


}
