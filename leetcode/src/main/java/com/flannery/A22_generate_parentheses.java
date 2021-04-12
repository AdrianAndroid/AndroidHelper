package com.flannery;

import java.util.ArrayList;
import java.util.List;

public class A22_generate_parentheses {
    public static void main(String[] args) {
        final List<String> x = new Solution().generateParenthesis(3);
        System.out.println(x.size());
        System.out.println(x);
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
