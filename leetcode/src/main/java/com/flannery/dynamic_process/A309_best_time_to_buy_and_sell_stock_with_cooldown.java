//package com.flannery.dynamic_process;
//
//public class A309_best_time_to_buy_and_sell_stock_with_cooldown {
//
//    public static int maxProfit(int[] prices) {
//        if (prices.length < 2) return 0;
//        int[][][] dp = new int[prices.length][2][2]; // 0买入 1卖出
//        // 初始化第0个
//
//        for (int i = 1; i < prices.length; i++) {
//            dp[i][0][0] = dp[i-1][0][0];
//            dp[i][0][1] = Math.max(dp[i-1][0][1], dp[i-1][0][0] - prices[i]);
//            dp[i][1][0] = ;
//            dp[i][1][1] = 0;
//        }
//
//        return Math.max(dp[prices.length - 1][1][0], dp[prices.length - 1][1][1]);
//    }
//
//
//}
