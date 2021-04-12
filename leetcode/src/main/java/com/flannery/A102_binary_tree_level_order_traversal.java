package com.flannery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class A102_binary_tree_level_order_traversal {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Solution {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if(root == null) return  res;

            //需要一个栈来记录节点
            Queue<TreeNode> nodes = new LinkedList<>();
            nodes.offer(root);//放入第一个元素
            while (!nodes.isEmpty()){

                List<Integer> list = new ArrayList<>();
                //遍历这个层级的所有节点
                for (int i = 0; i < nodes.size(); i++) {
                    TreeNode n = nodes.poll(); //从头开始取
                    // process
                    list.add(n.val);
                    if(n.left!=null) nodes.offer(n.left);//队尾存
                    if(n.right!=null) nodes.offer(n.right);
                }
                res.add(list);
            }

            return res;
        }
    }

}
