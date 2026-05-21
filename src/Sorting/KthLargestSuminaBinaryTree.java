package Sorting;

/**
 * Author - Sivaprakash Nithyanandam Oct 21,2024 - 9:31 PM
 */

/**
 * https://leetcode.com/problems/kth-largest-sum-in-a-binary-tree/description/
 *
 * You are given the root of a binary tree and a positive integer k.
 *
 * The level sum in the tree is the sum of the values of the nodes that are on the same level.
 *
 * Return the kth largest level sum in the tree (not necessarily distinct). If there are fewer than k levels in the tree, return -1.
 *
 * Note that two nodes are on the same level if they have the same distance from the root.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [5,8,9,2,1,3,7,4,6], k = 2
 * Output: 13
 * Explanation: The level sums are the following:
 * - Level 1: 5.
 * - Level 2: 8 + 9 = 17.
 * - Level 3: 2 + 1 + 3 + 7 = 13.
 * - Level 4: 4 + 6 = 10.
 * The 2nd largest level sum is 13.
 * Example 2:
 *
 *
 * Input: root = [1,2,null,3], k = 1
 * Output: 3
 * Explanation: The largest level sum is 3.
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is n.
 * 2 <= n <= 105
 * 1 <= Node.val <= 106
 * 1 <= k <= n
 */
public class KthLargestSuminaBinaryTree {

    public static void main(String[] args) {
        KthLargestSuminaBinaryTree o = new KthLargestSuminaBinaryTree();

        TreeNode head = new TreeNode(5);
        head.left = new TreeNode(8);
        head.right = new TreeNode(9);
        head.left.left = new TreeNode(2);
        head.left.right= new TreeNode(1);
        head.right.left = new TreeNode(3);
        head.right.right= new TreeNode(7);
        head.left.left.left= new TreeNode(4);
        head.left.left.right= new TreeNode(6);

        System.out.println(o.kthLargestLevelSum(head, 2));
    }

    private int exploreLevels(TreeNode root, int level, long[] level_to_sum) {
        level_to_sum[level] += root.val;
        int left_level = level;
        if (root.left != null) {
            left_level = exploreLevels(root.left, level + 1, level_to_sum);
        }
        int right_level = level;
        if (root.right != null) {
            right_level = exploreLevels(root.right, level + 1, level_to_sum);
        }
        return Math.max(left_level, right_level);
    }

    private void swap(long[] values, int left, int right) {
        long temp = values[left];
        values[left] = values[right];
        values[right] = temp;
    }

    private int partition(long[] values, int start, int end, int target) {
        int current = start;
        long pivot_value = values[start];
        for (int i = current + 1; i <= end; i++) {
            long value = values[i];
            if (value <= pivot_value) {
                swap(values, current + 1, i);
                current++;
            }
        }
        swap(values, start, current);
        return current;
    }

    private long largest(long[] values, int start, int end, int target) {
        int index = partition(values, start, end, target);
        if (index < target) {
            return largest(values, index + 1, end, target);
        } else if (index == target) {
            return values[index];
        } else {
            long pivot_value = values[index];
            while (index > target && values[index] == pivot_value) {
                index--;
            }
            return largest(values, start, index, target);
        }
    }

    public long kthLargestLevelSum(TreeNode root, int k) {
        long[] level_to_sum = new long[100001];
        int max_level = exploreLevels(root, 0, level_to_sum);
        if (k > (max_level + 1)) {
            return -1;
        }
        return largest(level_to_sum, 0, max_level, max_level + 1 - k);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
