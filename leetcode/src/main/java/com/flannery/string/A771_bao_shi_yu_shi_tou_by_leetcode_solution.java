package com.flannery.string;

import java.util.HashSet;

public class A771_bao_shi_yu_shi_tou_by_leetcode_solution {
    public static void main(String[] args) {
        new A771_bao_shi_yu_shi_tou_by_leetcode_solution().numJewelsInStones("","")
;    }

    public int numJewelsInStones(String jewels, String stones) {
        if (jewels.length() == 0 || stones.length() == 0) return 0;
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < jewels.length(); i++) {
            set.add(jewels.charAt(i));
        }
        int len = 0;
        for (int i = 0; i < stones.length(); i++) {
            if (set.contains(stones.charAt(i))) {
                len++;
            }
        }
        return 0;
    }
}
