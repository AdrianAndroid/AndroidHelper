package com.flannery.dynamic_process;

import java.util.Arrays;

public class A152_maximum_product_subarray {


    public static void main(String[] args) {
//        System.out.println(maxProduct(new int[]{2, 3, -2, 4}));
        System.out.println(maxProduct(new int[]{-4, -3, -2}));
    }

    // 只需要一个最大值，一个最小值就可以了
    public static int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;
        // 使用动态规划
        // 最大乘积，最小乘积，（可能会反转）
        int maxValue = nums[0], minValue = nums[0];
        int max = maxValue;
        for (int i = 1; i < nums.length; i++) {
            // 计算最大值-max(dp[i-1][0] || dp[i-1][0]*nums[i] || dp[i-1][1]*nums[i])
            // 计算最小值-min(dp[i-1][1] || dp[i-1][0]*nums[i] || dp[i-1][0]*nums[i])
            int tMax = maxValue;
            int tMin = minValue;
            maxValue = _max(nums[i], tMax * nums[i], tMin * nums[i]);
            minValue = _min(nums[i], tMin * nums[i], tMax * nums[i]);
            max = Math.max(maxValue, max);
//            System.out.println("maxValue:" + maxValue +
//                    ":minValue:" + minValue+
//                    ":max:" + max);
        }
        return max;
    }


    public static int maxProduct1(int[] nums) {
        if (nums.length == 0) return 0;
        // 使用动态规划
        // 最大乘积，最小乘积，（可能会反转）
        int[][] dp = new int[nums.length][2];
        dp[0][0] = nums[0];
        dp[0][1] = nums[0];
        int max = dp[0][0];
        for (int i = 1; i < nums.length; i++) {
            // 计算最大值-max(dp[i-1][0] || dp[i-1][0]*nums[i] || dp[i-1][1]*nums[i])
            // 计算最小值-min(dp[i-1][1] || dp[i-1][0]*nums[i] || dp[i-1][0]*nums[i])
            int preMax = dp[i - 1][0];
            int preMin = dp[i - 1][1];
            dp[i][0] = _max(nums[i], preMax * nums[i], preMin * nums[i]);
            dp[i][1] = _min(nums[i], preMin * nums[i], preMax * nums[i]);
            max = Math.max(dp[i][0], max);
        }
        for (int i = 0; i < dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        return max;
    }

    public static int _max(int b, int c, int d) {
        return Math.max(b, Math.max(c, d));
    }

    public static int _min(int b, int c, int d) {
        return Math.min(b, Math.min(c, d));
    }


}
