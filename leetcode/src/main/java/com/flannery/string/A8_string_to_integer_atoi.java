package com.flannery.string;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class A8_string_to_integer_atoi {

    public static void main(String[] args) {
        new A8_string_to_integer_atoi().print("  0000000000012345678", 12345678);
        new A8_string_to_integer_atoi().print("00000-42a1234", 0);
        new A8_string_to_integer_atoi().print("+-12", 0);
        new A8_string_to_integer_atoi().print("words and 987", 0);
        new A8_string_to_integer_atoi().print("42", 42);
        new A8_string_to_integer_atoi().print(" -42", -42);
        new A8_string_to_integer_atoi().print(" -412  ddd", -412);
    }

    public void print(String str, int result) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(str);
        sb.append(")");
        sb.append("   ");
        sb.append(myAtoi(str));
        sb.append("  ==  ");
        sb.append(result);
        System.out.println(sb.toString());
    }

    public int myAtoi(String str) {
        Automaton automaton = new Automaton();
        int length = str.length();
        for (int i = 0; i < length; ++i) {
            automaton.get(str.charAt(i));
        }
        return (int) (automaton.sign * automaton.ans);
    }


    class Automaton {
        public int sign = 1;
        public long ans = 0;
        private String state = "start";
        private Map<String, String[]> table = new HashMap<String, String[]>() {{
            put("start", new String[]{"start", "signed", "in_number", "end"});
            put("signed", new String[]{"end", "end", "in_number", "end"});
            put("in_number", new String[]{"end", "end", "in_number", "end"});
            put("end", new String[]{"end", "end", "end", "end"});
        }};

        public void get(char c) {
            state = table.get(state)[get_col(c)];
            if ("in_number".equals(state)) {
                ans = ans * 10 + c - '0';
                ans = sign == 1 ? Math.min(ans, (long) Integer.MAX_VALUE) : Math.min(ans, -(long) Integer.MIN_VALUE);
            } else if ("signed".equals(state)) {
                sign = c == '+' ? 1 : -1;
            }
        }

        private int get_col(char c) {
            if (c == ' ') {
                return 0;
            }
            if (c == '+' || c == '-') {
                return 1;
            }
            if (Character.isDigit(c)) {
                return 2;
            }
            return 3;
        }
    }


    public int myAtoi4(String str) {
        boolean canFlag = true; //区别出第一次符号
        boolean flag = true; // default +
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                if (list.isEmpty() && c == '0' && !canFlag) {
                    //if (!canFlag)
                        break;
                } else {
                    list.add(c - '0');
                }
            } else if (canFlag) {
                if ('+' == c) {
                    canFlag = false;
                    flag = true;
                } else if ('-' == c) {
                    canFlag = false;
                    flag = false;
                } else if (' ' == c) {

                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // list全是数字了
        double num = 0;
        for (int i = 0; i < list.size(); i++) {
            num = num * 10 + list.get(i);
        }
        if (num > Integer.MAX_VALUE) {
            if (flag) {
                num = Integer.MAX_VALUE;
            } else {
                num = Integer.MIN_VALUE;
            }
        } else if (!flag) {
            num *= -1;
        }
        return (int) num;
    }

    public int myAtoi3(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] chars = s.toCharArray();
        //找到第一个+
        double len = 0;
        Integer flag = null;
        for (int i = 0; i < chars.length; i++) {
            if (flag == null && '+' == chars[i] && '-' == chars[i]) {
                if ('-' == chars[i]) flag = -1;
                else flag = 1;
            } else if (Character.isDigit(chars[i])) {
                if (flag == null) flag = 1;
                len = len * 10 + chars[i] - '0';
            } else if (flag == null) {
                return 0;
            }
        }
        double num = len * flag;
        if (len > Integer.MAX_VALUE) {
            num = Integer.MAX_VALUE;
        } else if (len < Integer.MIN_VALUE) {
            num = Integer.MIN_VALUE;
        }
        return (int) num;
    }

    public int myAtoi2(String s) {
        if (s.length() == 0) return 0;
        double len = 0;
        s = s.trim();

        int i = 0;
        int flag = 1;
        char t = s.charAt(0);
        if ('-' == t) {
            flag = -1;
            i = 1;
        } else if ('+' == t) {
            i = 1;
        }
        for (; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                len = len * 10 + (c - '0');
            } else {
                break;
            }
        }

        double num = len * flag;
        if (len > Integer.MAX_VALUE) {
            num = Integer.MAX_VALUE;
        } else if (len < Integer.MIN_VALUE) {
            num = Integer.MIN_VALUE;
        }
        return (int) num;
    }
}
