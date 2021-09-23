package com.offer;

public class offer21 {

    public static void main(String[] args) {
        int[] exchange = new offer21().exchange(new int[]{1, 2, 3, 4});
        for (int i = 0; i < exchange.length; i++) {
            System.out.print(exchange[i]);
        }
    }

    public int[] exchange(int[] nums) {
        if (nums.length == 0) return nums;
        int low = 0, fast = 0; // 记录找到偶数的位置
        while (fast < nums.length) {
            if(nums[fast] % 2 == 1) {
                swap(nums, low, fast);
                low++;
            }
            fast++;
        }
        return nums;
    }

    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

}




