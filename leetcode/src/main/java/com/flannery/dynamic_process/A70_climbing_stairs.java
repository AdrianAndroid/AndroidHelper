package com.flannery.dynamic_process;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class A70_climbing_stairs {


    public static void main(String[] args) {
        System.out.println(climbStairs(10));
    }

    // 动态规划 -- 滑动窗口
    public static int climbStairs(int n) {
        if (n <= 2) return n;
        int first = 1, second = 2, result = 0;
        for (int i = 3; i <= n; i++) {
            result = first + second;
            //滑动
            first = second;
            second = result;
        }
        return result; //存到最后一个值里
    }

    // 动态规划 -- 初始值为0
//    public static int climbStairs(int n) {
//        if(n <= 2) return n;
//        int[] dp = new int[n+1];//存储所有可能情况
//        dp[0] = 1; dp[1] = 1; // dp[2] = dp[0] + dp[1];
//        for (int i = 2; i <= n; i++) { //遍历所有的值
//            dp[i] = dp[i-1] + dp[i-2];
//        }
//        return dp[n]; //存到最后一个值里
//    }

    // 动态规划
//    public static int climbStairs(int n) {
//        if(n <= 2) return n;
//        int[] dp = new int[n];//存储所有可能情况
//        dp[0] = 1; dp[1] = 2;
//        for (int i = 2; i < n; i++) { //遍历所有的值
//            dp[i] = dp[i-1] + dp[i-2];
//        }
//        return dp[n-1]; //存到最后一个值里
//    }

    // 记忆化
//    public static int climbStairs(int n) {
//        if(n <=2) return n;
//        HashMap<Integer, Integer> map = new HashMap<>(n);
//        map.put(0, 1);
//        map.put(1, 2);
//        for (int i = 0; i < n; i++) {
//            if(!map.containsKey(i)) {
//                map.put(i, map.get(i-1)+map.get(i-2));
//            }
//        }
//        return map.get(n-1);
//    }
    ////# 第一种
//    public static int climbStairs(int n) {
//        if (n <= 2) return n; //结束
//        return climbStairs(n - 1) + climbStairs(n - 2);
//    }


    class Solution {
        public int climbStairs_dp(int n) {
            return 0;
        }

        public int climbStairs_x(int n) {
            if (n < 2) return n;
            int before = 1;
            int after = 2;
            int result = 0;
            for (int i = 2; i < n; i++) {
                result = before + after;
                before = after; //前移动
                after = result;
            }
            return result;
        }

        public int climbStairs_0(int n) {
            if (n <= 2) return n;
            int one_step_before = 2;
            int two_step_before = 1;
            int all_ways = 0;
            for (int i = 2; i < n; i++) {
                all_ways = one_step_before + two_step_before;
                two_step_before = one_step_before;
                one_step_before = all_ways;
            }
            return 0;
        }

        public int climbStairs_1(int n) {
            if (n == 0 || n == 1 || n == 2) return n;
            int[] mem = new int[n];
            mem[0] = 1;
            mem[1] = 2;
            for (int i = 2; i < n; i++) {
                mem[i] = mem[i - 1] + mem[i - 2];
            }
            return mem[n - 1];
        }

        public int climbStairs_2(int n) {
            if (n == 0 || n == 1) return n;
            return climbStairs_1(n) + climbStairs_1(n - 1);
        }
    }
}
