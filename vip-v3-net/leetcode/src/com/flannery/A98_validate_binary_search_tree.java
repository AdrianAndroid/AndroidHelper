package com.flannery;

public class A98_validate_binary_search_tree {


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
        public boolean isValidBST(TreeNode root) {
            return isvalidate(root, null, null); //根节点
        }

        public boolean isvalidate(TreeNode root, Integer min, Integer max){
            if(root == null) return true;
            if(min!=null && root.val <= min) return false;
            if(max!=null && root.val >= max) return false;
            return isvalidate(root.left, min, root.val) //左子树
                    &&isvalidate(root.right, root.val, max); //右子树
        }

        public boolean helper(TreeNode root){
            if(root == null) return true;
            if (helper(root.left)) return false;
            if(helper(root.right)) return false;
            return false;
        }
    }

}
