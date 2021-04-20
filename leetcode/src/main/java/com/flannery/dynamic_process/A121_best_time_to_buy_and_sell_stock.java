package com.flannery.dynamic_process;

import org.omg.CORBA.MARSHAL;

import java.util.Arrays;

public class A121_best_time_to_buy_and_sell_stock {

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));//5
//        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}));//0
//        System.out.println(maxProfit(new int[]{2, 4, 1})); //2
//        System.out.println(maxProfit(new int[]{1, 2}));//1
    }

    // 动态规划 -- 简化
    public static int maxProfit(int[] prices) {
        int steal = -prices[0]; //持股
        int nosteal = 0; //不持股

        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            int t = steal;
            steal = Math.max(steal, -prices[i]);//当前持股，前边买了+买
            nosteal = Math.max(nosteal, t + prices[i]); //当前不持股，前边卖了+卖
        }


        return Math.max(steal, nosteal);
    }

    // 动态规划
    public static int maxProfit3(int[] prices) {
        int[][] dp = new int[prices.length][2]; //持股 不持股
        dp[0][0] = -prices[0]; //持股
        dp[0][1] = 0; //不持股

        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            int[] pre = dp[i - 1];
            dp[i][0] = Math.max(pre[0], -prices[i]);//当前持股，前边买了+买
            dp[i][1] = Math.max(pre[1], pre[0] + prices[i]); //当前不持股，前边卖了+卖
        }


        return Math.max(dp[prices.length - 1][0], dp[prices.length - 1][1]);
    }

    // 最小值
    public static int maxProfit2(int[] prices) {
        int mp = prices[0]; //维护一个合理的价格
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < mp) {//比最小的价格还小
                mp = prices[i];
            } else if (prices[i] - mp > max) { //相差比最小的还有利润
                max = prices[i] - mp;
            }
        }
        return max;
    }

    //暴力法
    public static int maxProfit1(int[] prices) {
        if (prices.length == 0) return 0;
        //可以买入，可以卖出
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                max = Math.max(max, prices[j] - prices[i]);
            }
        }

        return max;
    }

    //A123_best_time_to_buy_and_sell_stock_iii/

}
