package com.flannery;

import java.util.Deque;
import java.util.LinkedList;

public class xA239_hua_dong_chuang_kou_zui_da_zhi {


    class Solution2 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length < 2) return nums;
            // 双向队列 保存当前窗口最大值的数组位置 保证队列中数组位置的数值按从大到小排序
            LinkedList<Integer> queue = new LinkedList();
            // 结果数组
            int[] result = new int[nums.length - k + 1];
            // 遍历nums数组
            for (int i = 0; i < nums.length; i++) {
                // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求
                while (!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]) {
                    queue.pollLast();
                }
                // 添加当前值对应的数组下标
                queue.addLast(i);
                // 判断当前队列中队首的值是否有效
                if (queue.peek() <= i - k) {
                    queue.poll();
                }
                // 当窗口长度为k时 保存当前窗口中最大值
                if (i + 1 >= k) {
                    result[i + 1 - k] = nums[queue.peek()];
                }
            }
            return result;
        }
    }

    class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length;
            Deque<Integer> deque = new LinkedList<Integer>();
            for (int i = 0; i < k; ++i) { //前k个值
                while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                    deque.pollLast();
                }
                deque.offerLast(i);
            }

            int[] ans = new int[n - k + 1];
            ans[0] = nums[deque.peekFirst()];
            for (int i = k; i < n; ++i) {
                while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                    deque.pollLast();
                }
                deque.offerLast(i);
                while (deque.peekFirst() <= i - k) {
                    deque.pollFirst();
                }
                ans[i - k + 1] = nums[deque.peekFirst()];
            }
            return ans;
        }
    }


}
