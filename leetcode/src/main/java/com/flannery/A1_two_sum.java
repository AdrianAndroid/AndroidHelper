package com.flannery;

import java.util.HashMap;
import java.util.Map;

public class A1_two_sum {
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            if (nums.length == 2) return nums;
            Map<Integer, Integer> map = new HashMap<>(nums.length);
            for (int i = 0; i < nums.length; i++) {
                final int num = nums[i];
                final Integer integer = map.get(num);
                if (integer != null && integer == target - num) {
                    return new int[]{num, integer};
                } else {
                    map.put(num, target - num);
                }
            }
            return new int[2];
        }
    }
}
