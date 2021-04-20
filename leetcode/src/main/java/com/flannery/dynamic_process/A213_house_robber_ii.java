package com.flannery.dynamic_process;

public class A213_house_robber_ii {

    public static void main(String[] args) {
        System.out.println(rob(new int[]{2, 3, 2})); //3
        System.out.println(rob(new int[]{1, 2})); //2
        System.out.println(rob(new int[]{1, 2, 1, 1}));//3
    }


    //动态规划
    public static int rob(int[] nums) {
        int len = nums.length;
        if (len == 0) return 0;
        if (len == 1) return nums[0];
        return Math.max(
                rob_max(nums, 0, 0, nums.length),
                rob_max(nums, nums[0], 0, nums.length - 1)
        );
    }


    public static int rob_max(int[] nums, int steal, int nosteal, int len) {
        //第一间不选的情况
        for (int i = 1; i < len; i++) {
            int t = steal;
            steal = nums[i] + nosteal;
            nosteal = Math.max(t, nosteal);
        }
        return Math.max(steal, nosteal);
    }

    //

}
