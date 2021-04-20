package com.flannery.dynamic_process;

import sun.nio.cs.ext.MacThai;

public class A123_best_time_to_buy_and_sell_stock_iii {

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4})); //6
//        System.out.println(maxProfit(new int[]{1, 2, 3, 4, 5}));          //4
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}));          //0
//        System.out.println(maxProfit(new int[]{1}));                      //0
//        System.out.println(maxProfit(new int[]{2,1,4}));                      //0
    }


    //    // 动态规划
//    public static int maxProfit(int[] prices) {
//        if (prices.length == 0) return 0;
//        //状态记忆
//        int k_len = 3;// 0，1，2
//        int[][][] dp = new int[prices.length][k_len][2];
//        //初始化第一个组数据
//        for (int i = 0; i < k_len - 1; i++) {
//            dp[0][i][0] = 0; //持股（买入）
//            dp[0][i][1] = -prices[0]; //不持股
//        }
//        for (int i = 1; i < prices.length; i++) {
//            dp[i][0][0] = dp[i - 1][0][0];// k == 0时候啥也不动
//            for (int k = 1; k < k_len-1; k++) {
//                dp[i][k][0] = // 不动 + 买 + 卖
//            }
//            dp[i][k_len-1][1] =  dp[i-1][k_len-1][0] + prices[i];//只能买入，不能卖出，前边已经用完次数了
//        }
//
//        int max = 0;
//        for (int i = 0; i < k_len; i++) {
//            max = Math.max(max, Math.max(dp[prices.length - 1][k_len][0], dp[prices.length - 1][k_len][1]));
//        }
//        return max;
//    }



    // 动态规划 - 最多买两次
    public static int maxProfit2(int[] prices) {
        if (prices.length < 2) return 0;
        //状态记忆
        int[][][] dp = new int[prices.length][3][2]; // k:0->2
        //初始化
        dp[0][0][0] = -prices[0];//持股就得花钱
        dp[0][0][1] = 0;//不持股，就不用动

        dp[0][1][0] = -prices[0];//持股就得花钱
        dp[0][1][1] = -prices[0];//不持股，就不用动

        dp[0][2][0] = -prices[0];//持股就得花钱
        dp[0][2][1] = -prices[0];//不持股，就不用动


        //遍历所有
        for (int i = 1; i < prices.length; i++) {
            // 0 买入， 1 卖出
            dp[i][0][0] = Math.max(dp[i - 1][0][0], dp[i - 1][0][1] - prices[i]);
            dp[i][0][1] = dp[i - 1][0][1];

            dp[i][1][0] = Math.max(dp[i - 1][1][0], dp[i - 1][1][1] - prices[i]);//买入
            dp[i][1][1] = Math.max(dp[i - 1][1][1], dp[i - 1][0][0] + prices[i]);//卖出

            //dp[i][2][0] = Math.max(dp[i - 1][2][0], dp[i - 1][1][1] - prices[i]);//买入
            dp[i][2][1] = Math.max(dp[i - 1][2][1], dp[i - 1][1][0] + prices[i]);//卖出,用不到
        }

        for (int i = 0; i < dp.length; i++) {//每一行
            for (int j = 0; j < 3; j++) {//每一列
                System.out.print("(" + dp[i][j][0] + "," + dp[i][j][1] + ")");
            }
            System.out.println();
        }
        //初始化第一个组数据
        int a = Math.max(dp[prices.length - 1][1][1], dp[prices.length - 1][2][1]);
        int b = Math.max(dp[prices.length - 1][1][0], dp[prices.length - 1][2][0]);
        return Math.max(a, b);
    }

    public static int maxProfit(int[] prices) {
        if (prices.length < 2) return 0;
        int len = prices.length;
        int[][][] dp = new int[len][2][3];
        dp[0][0][0] = 0;
        dp[0][0][1] = 0;
        dp[0][0][2] = 0;
        dp[0][1][0] = -prices[0];
        dp[0][1][1] = -prices[0];
        dp[0][1][2] = -prices[0];
        for (int i = 1; i < len; ++i) {
            // 卖出股票
            dp[i][0][0] = 0; // k=0, 卖不了
            dp[i][0][1] = Math.max(dp[i - 1][0][1], dp[i - 1][1][0] + prices[i]); //k=2，max(上一次卖出， 上一次买入(k-1)+这次)
            dp[i][0][2] = Math.max(dp[i - 1][0][2], dp[i - 1][1][1] + prices[i]); //k=3, max(上一次卖出， 上一次买入(k-1)+这次)

            // 买入股票
            dp[i][1][0] = Math.max(dp[i - 1][1][0], dp[i - 1][0][0] - prices[i]); //k=0,max(上次买入, 上一次卖出(k-1)-这次)
            dp[i][1][1] = Math.max(dp[i - 1][1][1], dp[i - 1][0][1] - prices[i]); //k=1,mac(上次买入, 上一次卖出(k-1)-这次)
            dp[i][1][2] = 0;// k=2, 买不了
        }

        for (int i = 0; i < dp.length; i++) {
            System.out.print("(" + dp[i][0][0] + "," + dp[i][0][1] + "," + dp[i][0][2] + ")");
            System.out.print("(" + dp[i][1][0] + "," + dp[i][1][1] + "," + dp[i][1][2] + ")");
            System.out.println();
        }

        return Math.max(dp[len - 1][0][2], dp[len - 1][0][1]); // 交易两次卖的钱，， 交易一次买入的钱
    }




}
