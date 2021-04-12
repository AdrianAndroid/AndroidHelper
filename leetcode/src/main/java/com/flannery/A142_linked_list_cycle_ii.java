package com.flannery;

public class A142_linked_list_cycle_ii {

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public class Solution {
        public ListNode detectCycle(ListNode head) {
            // 1. 使用set记录所有经过的点
            // 两个指针
            ListNode slow=head,fast=head;
            while (slow!=null && fast.next!=null){
                if(slow == fast) return slow;
                slow = slow.next;
                fast = fast.next.next;
            }
            return slow;
        }
    }
}
