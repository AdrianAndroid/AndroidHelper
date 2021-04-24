package com.flannery;

public class A50_powx_n {

    public static void main(String[] args) {
//        System.out.println(new Solution().myPow(10, 3));
//        System.out.println(new Solution().myPow2(10, 3));
        System.out.println(new Solution().myPow2(2.00000, -2147483648));
    }

    static class Solution {

        public double myPow2(double x, int n) {
            if (n < 0) { //按正数计算
                x = 1 / x;
                n = -n;
            }
            double pow = 1;
            while (n > 0) {
                if ((n & 1) == 1) pow *= x; //说明是奇数个x
                x *= x;  //正常的相乘
                n >>= 1;
            }
            return pow;
        }

        public double myPow(double x, int n) {
            if (n == 0) return 1; //说明不用再乘了
            if (n < 0) return 1 / myPow(x, -n); //负数的情况
            if (n % 2 == 1) return x * myPow(x, n - 1);
            return myPow(x * x, n / 2);
        }
    }
}
