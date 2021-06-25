package com.flannery;

public class A9_palindrome_number {

    public static void main(String[] args) {

    }

    public boolean isPalindrome(int x) {
        // 不合格的
        if(x < 0 || (x != 0 && x % 10 == 0)) return false;
        // 获取反转数字
        int ori = x;
        int rnum = 0;
        while (x > 0) {
            rnum = rnum * 10 + x % 10;
            x /= 10; //取消第一位
        }
        //判断是否相等
        return rnum == ori;
    }


}
