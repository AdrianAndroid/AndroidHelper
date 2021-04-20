package com.flannery.dynamic_process;

import java.util.Arrays;

public class A322_coin_change {
    public static void main(String[] args) {
        //System.out.println(new Solution().coinChange2(new int[]{1, 2, 5}, 11));
        System.out.println(coinChange(new int[]{1, 2, 5}, 11));
        System.out.println(coinChange(new int[]{2}, 3));
        System.out.println(coinChange(new int[]{1}, 0));
        System.out.println(coinChange(new int[]{1}, 1));
        System.out.println(coinChange(new int[]{1}, 2));
    }


    //    public static int coinChange(int[] coins, int amount) {
//        // 动态规划
//        // i 0->amount
//        // f[n] = min(f[k] k->coins.size) + 1
//        int[] dp = new int[amount + 1];
//        dp[amount] = -1; //最后一个是-1
//        for (int i = 0; i <= amount; i++) {
//            for (int j = 0; j < coins.length; j++) { //每次找到可以花掉的最大的那个
//                if (amount - coins[i] > 0) { //说明还可以花掉
//                    dp[i] =
//                }
//            }
//        }
//        return dp[amount];
//    }
    public static int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[max];
        Arrays.fill(dp, max);
        dp[0] = 0; //
        for (int i = 1; i < max; i++) { // amount = max - 1
            // 遍历所有的coins
            for (int coin : coins) {// 遍历所有的coin
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount]; //存放最后一个
    }

    public static int[] memo = null;

    public static int coinChange2(int[] coins, int amount) {
        memo = new int[amount + 1];
        return helper(coins, amount);
    }


    // 记忆化，递归
    public static int helper(int[] coins, int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        if (memo[amount] != 0) return memo[amount];
        // 找到最小路径的那个
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int res = Math.min(min, helper(coins, amount - coins[i]));
            // 满足条件+1
            if (res > -1 && res < min) min = 1 + res; //
        }
        memo[amount] = (min == Integer.MAX_VALUE) ? -1 : min;
        return memo[amount];
    }


    static class Solution {
        public int coinChange2(int[] coins, int amount) {
            //定义数组
            int[] dp = new int[amount + 1]; //存放所有结果
            Arrays.fill(dp, amount + 1); // 放入最大值+1， 避免影响结果
            dp[0] = 0;
            for (int i = 1; i <= amount; i++) { //从真实的数值开始
                for (int j = 0; j < coins.length; j++) { //遍历所有的钱币
                    if (coins[j] <= i) { //还有剩余
                        dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);// +1说明可以多加一层可能
                        // i - coins[j]
                    }
                }
            }
            return dp[amount] > amount ? -1 : dp[amount];
        }


        public int coinChange(int[] coins, int amount) {
            int[] dp = new int[amount + 1]; //为什么要创建这个数组？到达这个数值能达到的个数,最前边就是为了保存值，所以多出一个
            Arrays.fill(dp, amount + 1); // 超过最大值， 因为要从最小值找
            dp[0] = 0;
            for (int i = 1; i <= amount; i++) { //所有的钱币
                for (int j = 0; j < coins.length; j++) {
                    if (coins[j] <= i) {
                        dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                    }
                }
            }
            return dp[amount] > amount ? -1 : dp[amount];
        }
    }
}
