package com.flannery;

import java.util.Arrays;
import java.util.Stack;

public class A844_backspace_string_compare {

    class Solution {
        public boolean backspaceCompare(String S, String T) {
            // 生成两个字符串


            return buildString(S).equals(buildString(T));
        }

        public String buildString(String s){
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                final char ch = s.charAt(i);
                if(ch == '#'){
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            }
            return Arrays.toString(stack.toArray());
        }
    }
}
