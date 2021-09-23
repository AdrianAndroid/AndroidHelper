package com.flannery;

import java.util.ArrayList;
import java.util.List;

public class A22_generate_parentheses {
    public static void main(String[] args) {
//        final List<String> x = new Solution().generateParenthesis(3);
//        System.out.println(x.size());
//        System.out.println(x);

        List<String> list = generateParenthesis(3);
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static List<String> generateParenthesis(int n) {
        ArrayList<String> array = new ArrayList<>();
        generate(array, new StringBuilder(), n, 0, 0);
        return array;
    }

    private static void generate(ArrayList<String> list, StringBuilder sb, int n, int l, int r) {
        if (sb.length() == 2 * n) {
            list.add(sb.toString());
            return;
        }
        if (l < n) { // l 从0开始
            sb.append("(");
            generate(list, sb, n, l + 1, r);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (r < l) {
            sb.append(")");
            generate(list, sb, n, l, r + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }


    static class Solution {
        public List<String> generateParenthesis(int n) {
            List<String> list = new ArrayList<>();
            generate(list, new StringBuilder(), 0, 0, n);
            return list;
        }

        public void generate(List<String> result, StringBuilder sb, int left, int right, int n) {
            if (sb.length() == n * 2) {
                result.add(sb.toString());
                return;
            }
            if (left < n) {
                sb.append('(');
                generate(result, sb, left + 1, right, n);
                sb.deleteCharAt(sb.length() - 1);
            }
            if (right < left) {
                sb.append(')');
                generate(result, sb, left, right + 1, n);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
}
