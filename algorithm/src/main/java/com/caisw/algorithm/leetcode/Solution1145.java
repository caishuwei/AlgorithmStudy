package com.caisw.algorithm.leetcode;

import java.util.Stack;

/**
 * 二叉树着色游戏
 * https://leetcode-cn.com/problems/binary-tree-coloring-game/
 */
public class Solution1145 {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        //极客1选择之后，将二叉树分为三部分，若有一部分>=(n+1)/2，则可以获胜，否则失败
        //二叉树数字不是按照顺序自上而下，自左而右，而且左右分支都可能有缺失

        //遍历二叉树，找到极客1选择的地方
        //采用类似深度搜索的思路，先从左边走到底，若没找到，则一步一步回退寻找没有找过的岔路
        TreeNode currNode = findNode(root,x);
        int left = getNodeSum(currNode.left);
        if (left > n / 2) {
            return true;
        }
        int right = getNodeSum(currNode.right);
        if (right > n / 2) {
            return true;
        }
        if (left + right < n / 2) {
            return true;
        }
        return false;
    }

    private int getNodeSum(TreeNode node) {
        if (node != null) {
            return 1 + getNodeSum(node.left) + getNodeSum(node.right);
        }
        return 0;
    }

    private TreeNode findNode(TreeNode node, int x) {
        if (node == null) {
            return null;
        }
        if (node.val == x) {
            return node;
        } else {
            if (node.left != null) {
                TreeNode left = findNode(node.left, x);
                if (left != null) {
                    return left;
                }
            }
            if (node.right != null) {
                return findNode(node.right, x);
            }
            return null;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(8);

        root.left = new TreeNode(6);
        root.right = new TreeNode(7);

        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = null;
        root.right.right = new TreeNode(9);

        root.left.left.left = null;
        root.left.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(5);
        root.left.right.right = null;
//        root.right.left.left;
//        root.right.left.right;
        root.right.right.left = null;
        root.right.right.right = null;

        root.left.left.right.left = null;
        root.left.left.right.right = null;
        root.left.right.left.left = null;
        root.left.right.left.right = new TreeNode(1);

        System.out.println(new Solution1145().btreeGameWinningMove(root, 9, 4));
    }

}
