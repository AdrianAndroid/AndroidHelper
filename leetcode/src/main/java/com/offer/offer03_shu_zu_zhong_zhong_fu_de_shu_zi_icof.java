package com.offer;

import java.util.HashSet;
import java.util.Set;

public class offer03_shu_zu_zhong_zhong_fu_de_shu_zi_icof {

    public static void main(String[] args) {
        int repeatNumber = new offer03_shu_zu_zhong_zhong_fu_de_shu_zi_icof().findRepeatNumber(new int[]{2, 3, 1, 0, 2, 5, 3});
        System.out.println(repeatNumber);
    }

    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            if (!set.add(num)) return num;
        }
        return -1;
    }

}
