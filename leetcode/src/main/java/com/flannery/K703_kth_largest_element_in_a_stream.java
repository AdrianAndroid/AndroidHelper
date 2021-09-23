package com.flannery;

import java.util.PriorityQueue;

public class K703_kth_largest_element_in_a_stream {

    class KthLargest {

        final PriorityQueue<Integer> queue;
        final int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            queue = new PriorityQueue<>(); //默认小顶堆
            for (int i = 0; i < nums.length; i++) {
                add(nums[i]);
            }
        }

        public int add(int val) {
            queue.offer(val);
            if(queue.size() > k){
                queue.poll();
            }
            return queue.peek();  //小顶堆
        }
    }
}
