package com.offer;

public class offer39 {

    public static void main(String[] args) {

    }

    public int majorityElement(int[] nums) {
        int x = 0; //记录数字
        int vote = 0;// 记录投票数
        for (int num : nums) {
            if (vote == 0) x = num;
            vote += num == x ? 1 : -1;
        }
        return x;
    }
}
