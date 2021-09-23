package com.offer;

public class offer05_ti_huan_kong_ge_lcof {
    public static void main(String[] args) {
        System.out.println(new offer05_ti_huan_kong_ge_lcof().replaceSpace("We are happy."));
    }

    public String replaceSpace(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                sb.append("%20");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
