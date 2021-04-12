package com.flannery;

public class A24_swap_nodes_in_pairs {


    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Solution {
        public ListNode swLapPairs(ListNode head) {
            ListNode s1 = head;
            ListNode s2 = head.next;
            while (s2!=null&&s2.next!=null){
                //交换
                 ListNode temp = s2.next;
                 s2.next = s1;
                 s1.next= temp;
                s1 = s2.next;
                if(s1 != null) s2 = s1.next;
            }
            return null;
        }
    }


}
