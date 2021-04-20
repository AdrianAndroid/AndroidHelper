package com.flannery.dynamic_process;

public class A198_house_robber {

    public static void main(String[] args) {
        System.out.println(rob(new int[]{2, 7, 9, 3, 1}));
        System.out.println(rob(new int[]{1,2,3,1}));
    }
    public static int rob(int[] nums) {
        if(nums.length == 0) return 0;
        //记录每一个偷与不偷的情况
        int steal = nums[0], nosteal = 0;
        //遍历每一个房子
        for (int i = 1; i < nums.length; i++) {
            int temp = steal;
            // 今天必须偷
            steal = nums[i] + nosteal; //
            // 今天不偷
            nosteal = Math.max(temp, nosteal);
        }
        return Math.max(steal, nosteal);
    }
    public static int rob1(int[] nums) {
        if(nums.length == 0) return 0;
        //记录每一个偷与不偷的情况
        int[][] dp = new int[nums.length][2];
        dp[0][0] = nums[0]; //偷
        dp[0][1] = 0; //不偷
        //遍历每一个房子
        for (int i = 1; i < nums.length; i++) {
            // 今天必须偷
            dp[i][0] = nums[i] + dp[i-1][1]; //
            // 今天不偷
            dp[i][1] = Math.max(dp[i-1][0], dp[i-1][1]);
        }
        return Math.max(dp[nums.length-1][0], dp[nums.length-1][1]);
    }

}





