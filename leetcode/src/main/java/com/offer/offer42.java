package com.offer;

public class offer42 {

    public static void main(String[] args) {
        System.out.println(new offer42().maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = 0;
        for (int i = 1; i < nums.length; i++) {
            nums[i] += Math.max(nums[i - 1], 0);
            max = Math.max(max, nums[i]);
        }
        return max;
    }

    public int maxSubArray2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = 0;
        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] + nums[i] > nums[i]) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];//重新开始
            }
            max = Math.max(max, dp[i]);
        }
        for (int i : dp) {
            System.out.print(i + " ");
        }
        System.out.println();
        return max;
    }
}
