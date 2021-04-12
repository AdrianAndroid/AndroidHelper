package com.flannery.dynamic_process;

public class A70_climbing_stairs {

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
