package com.google.springboot.service.dataAndAlgorithm;
import java.util.*;
/**
 * @Author kris
 * @Create 2024-06-20 17:40
 * @Description //LevelOrder and Mid Order traversal
 * 层序遍历，并且每一次中序遍历
 */


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> levelList = new ArrayList<>();
            Stack<Integer> stack = new Stack<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                stack.push(node.val);  // Store values in reverse order to simulate inorder traversal

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            // Pop from stack to get values in correct inorder traversal order
            while (!stack.isEmpty()) {
                levelList.add(stack.pop());
            }

            result.add(levelList);
        }

        return result;
    }
}

