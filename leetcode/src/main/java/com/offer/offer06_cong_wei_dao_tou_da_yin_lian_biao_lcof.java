package com.offer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class offer06_cong_wei_dao_tou_da_yin_lian_biao_lcof {
    public static void main(String[] args) {

    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public int[] reversePrint(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        while (head != null) {
            stack.push(head.val);
            head = head.next;
        }
        int[] res = new int[stack.size()];
        int index = 0;
        while (!stack.isEmpty()) res[index++] = stack.pop();
        return res;
    }



}
