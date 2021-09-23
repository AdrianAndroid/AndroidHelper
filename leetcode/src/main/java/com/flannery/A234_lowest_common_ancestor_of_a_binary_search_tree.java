package com.flannery;

public class A234_lowest_common_ancestor_of_a_binary_search_tree {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    class Solution {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || root == p || root == q) return root;
            final TreeNode left = lowestCommonAncestor(root.left, p, q);
            final TreeNode right = lowestCommonAncestor(root.right, p, q);
            //return left == null ? right : right == null ? left : right;
            if (left == null) return right;
            if (right == null) return left;
            return root;
        }
    }


}
