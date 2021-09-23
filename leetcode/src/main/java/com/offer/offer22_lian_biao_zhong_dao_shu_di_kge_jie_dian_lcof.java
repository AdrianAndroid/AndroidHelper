package com.offer;

public class offer22_lian_biao_zhong_dao_shu_di_kge_jie_dian_lcof {


    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode h1 = head, h2 = head;
        // h1先移动k个
        for (int i = 0; i < k; i++) h1 = h1.next;
        // 再同时移动
        while (h1 != null) {
            h1 = h1.next;
            h2 = h2.next;
        }
        return h2;
    }

    //ti_huan_kong_ge_lcof/
}
