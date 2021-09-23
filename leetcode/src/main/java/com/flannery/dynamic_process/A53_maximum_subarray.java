package com.flannery.dynamic_process;

import java.util.Arrays;

public class A53_maximum_subarray {


    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }


    // 替换自己这个数组
    public static int maxSubArray(int[] nums) {
        // 定义 dp[]
        if (nums.length == 0) return 0;
        int dp = nums[0]; //只复用这一个变量
        int max = dp;
        for (int i = 1; i < nums.length; i++) { //从第一个开始
            dp = Math.max(0, dp) + nums[i];
            max = Math.max(max, dp);
        }
        //System.out.println(Arrays.toString(dp));
        return max;
    }


    // 替换自己这个数组
    public static int maxSubArray2(int[] nums) {
        // 定义 dp[]
        if (nums.length == 0) return 0;
        int[] dp = nums;
        int max = dp[0];
        for (int i = 1; i < nums.length; i++) { //从第一个开始
            dp[i] = Math.max(0, dp[i - 1]) + nums[i];
            max = Math.max(max, dp[i]);
        }
        //System.out.println(Arrays.toString(dp));
        return max;
    }

    // 额外的空间
    public static int maxSubArray1(int[] nums) {
        // 定义 dp[]
        int len = nums.length;
        if (len == 0) return 0;
        int[] dp = new int[len];
        dp[0] = nums[0]; //把第一个保存起// 来
        int max = nums[0];
        for (int i = 1; i < len; i++) { //从第一个开始
            dp[i] = Math.max(0, dp[i - 1]) + nums[i];
            max = Math.max(max, dp[i]);
        }
        //System.out.println(Arrays.toString(dp));
        return max;
    }

}
