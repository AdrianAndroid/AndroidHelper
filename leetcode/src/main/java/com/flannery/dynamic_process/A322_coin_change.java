package com.flannery.dynamic_process;

import java.util.Arrays;

public class A322_coin_change {
    public static void main(String[] args) {
        System.out.println(new Solution().coinChange2(new int[]{1, 2, 5}, 11));
    }

    static class Solution {
        public int coinChange2(int[] coins, int amount) {
            //定义数组
            int[] dp = new int[amount+1]; //存放所有结果
            Arrays.fill(dp, amount+1); // 放入最大值+1， 避免影响结果
            dp[0]=0;
            for (int i = 1; i <= amount; i++) { //从真实的数值开始
                for (int j = 0; j < coins.length; j++) { //遍历所有的钱币
                    if(coins[j] <= i) { //还有剩余
                        dp[i] = Math.min(dp[i], dp[i-coins[j]]+1);// +1说明可以多加一层可能
                        // i - coins[j]
                    }
                }
            }
            return dp[amount] > amount ? -1 : dp[amount];
        }






























        public int coinChange(int[] coins, int amount) {
            int[] dp = new int[amount + 1]; //为什么要创建这个数组？到达这个数值能达到的个数,最前边就是为了保存值，所以多出一个
            Arrays.fill(dp, amount+1); // 超过最大值， 因为要从最小值找
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
