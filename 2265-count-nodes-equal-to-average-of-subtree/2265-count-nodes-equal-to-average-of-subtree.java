/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int matches = 0; // Global variable to keep track of the answer

    public int averageOfSubtree(TreeNode root) {
        dfs(root);
        return matches;
    }

    // Helper function returns an array: {sum of subtree, number of nodes}
    private int[] dfs(TreeNode node) {
        
        // BASE CASE: If null, sum is 0, count is 0
        if (node == null) {
            return new int[]{0, 0};
        }

        // RECURSIVE STEP: Get data from children
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        // CALCULATE CURRENT SUBTREE STATS
        int sum = node.val + left[0] + right[0];
        int count = 1 + left[1] + right[1];

        // CHECK CONDITION
        // Integer division in Java automatically rounds down (floor)
        int average = sum / count;
        
        if (average == node.val) {
            matches++;
        }

        // RETURN: Pass stats up to the parent
        return new int[]{sum, count};
    }
}