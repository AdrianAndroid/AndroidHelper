package com.flannery;

public class A206_reverse_linked_list {
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

    //迭代
    public ListNode reverseList(ListNode head){
        ListNode node = new ListNode();
        while (head != null) {
            ListNode next = head.next;
            head.next = node.next;
            node.next = head;
            head = next;
        }
        return node;
    }


    public ListNode reverseList2(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode newHead = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }













    public ListNode reverseList3(ListNode head) {
        ListNode node = new ListNode();
        ListNode temp = head;
        while(temp != null){
            ListNode t = temp.next; // 抽出剩余的链表
            temp.next = node;
            temp = t;
        }
        return node;
    }
}
