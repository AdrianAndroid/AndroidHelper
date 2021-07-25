package com.flannery;

public class A83_remove_duplicates_from_sorted_list {

    public static void main(String[] args) {

    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head.next == null) return head;
        // 两个指针
        ListNode pre = head, cur = head.next;
        while (cur != null) {
            if (pre.val == cur.val) { //移除掉
                pre.next = cur.next;
                cur = pre.next;
            }
            // 往下移动
            pre = cur;
            cur = cur.next;

        }
        return head;
    }


    public class ListNode {
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


}
