package com.flannery;

import java.util.HashMap;
import java.util.Map;

public class A242_valid_anagram {


    class Solution {
        public boolean isAnagram(String s, String t) {
            if (s == null || t == null) return false;
            if (s == t) return true;
            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                final char c = s.charAt(i);
                map.put(c, map.getOrDefault(c, 0)+1);
            }
            for (int i = 0; i < t.length(); i++) {
                final char c = t.charAt(i);
                map.put(c, map.getOrDefault(c, 0) - 1);
                if (map.getOrDefault(c, -1) < 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
