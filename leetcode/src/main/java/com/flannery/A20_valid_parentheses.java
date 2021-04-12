package com.flannery;

import java.util.*;

public class A20_valid_parentheses {

    class Solution {
        public boolean isValid1(String s) {
            if(s==null || s.length() % 2 == 1) return false; //单数肯定不行
            Map<Character, Character> pairs = new HashMap<>();
            pairs.put(')', '(' );
            pairs.put(']', '[' );
            pairs.put('}', '{' );
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                final char ch = s.charAt(i);
                if(pairs.containsKey(ch)){
                    if(stack.isEmpty() || stack.peek() != pairs.get(ch))
                        return false;
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            }

            return false;
        }

        public boolean isValid(String s) {
            int n = s.length();
            if (n % 2 == 1) {
                return false;
            }

            Map<Character, Character> pairs = new HashMap<Character, Character>() {{
                put(')', '(');
                put(']', '[');
                put('}', '{');
            }};
            Deque<Character> stack = new LinkedList<Character>();
            for (int i = 0; i < n; i++) {
                char ch = s.charAt(i);
                if (pairs.containsKey(ch)) {
                    if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
                        return false;
                    }
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            }
            return stack.isEmpty();
        }
    }

    private static final Map<Character,Character> map = new HashMap<Character,Character>(){{
        put('{','}'); put('[',']'); put('(',')'); put('?','?');
    }};
    public boolean isValid3(String s) {
        if(s.length() > 0 && !map.containsKey(s.charAt(0))) return false;
        LinkedList<Character> stack = new LinkedList<Character>() {{ add('?'); }};
        for(Character c : s.toCharArray()){
            if(map.containsKey(c)) stack.addLast(c);
            else if(map.get(stack.removeLast()) != c) return false;
        }
        return stack.size() == 1;
    }


}
