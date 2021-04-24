package com.flannery;

import java.util.LinkedList;
import java.util.Queue;

public class A104_maximum_depth_of_binary_tree {


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
        public int minDepth(TreeNode root){
            if(root == null) return 0;
            final int left = minDepth(root.left);
            final int right = minDepth(root.right);
            return (left == 0 || right == 0)
                    ? left+right+1
                    : Math.min(left, right) + 1; //算上本层
        }

        public int maxDepth_bfs(TreeNode root) {
            if (root == null) return 0;
            int level = 0;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);//尾插
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    final TreeNode poll = queue.poll(); //头取
                    if (poll.left != null) queue.offer(poll.left);//尾插
                    if (poll.right != null) queue.offer(poll.right);
                }
                level++;
            }
            return level;

        }

        public int maxDepth_dfs(TreeNode root){
            //使用栈
            return -1;
        }

        public int maxDepth1(TreeNode root) {
            if (root == null) return 0;
            else {
                final int left = maxDepth1(root.left);
                final int right = maxDepth1(root.right);
                return Math.max(left, right) + 1;
            }
        }
    }


}
