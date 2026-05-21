package dfs;

/**
 * @author - Sivaprakash Nithyanandam Oct 26,2024 - 8:52 PM
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/height-of-binary-tree-after-subtree-removal-queries/description/?envType=daily-question&envId=2024-10-26
 *
 * You are given the root of a binary tree with n nodes. Each node is assigned a unique value from 1 to n. You are also
 * given an array queries of size m.
 *
 * You have to perform m independent queries on the tree where in the ith query you do the following:
 *
 * Remove the subtree rooted at the node with the value queries[i] from the tree. It is guaranteed that queries[i] will
 * not be equal to the value of the root. Return an array answer of size m where answer[i] is the height of the tree
 * after performing the ith query.
 *
 * Note:
 *
 * The queries are independent, so the tree returns to its initial state after each query. The height of a tree is the
 * number of edges in the longest simple path from the root to some node in the tree.
 *
 *
 * Example 1:
 *
 *
 * Input: root = [1,3,4,2,null,6,5,null,null,null,null,null,7], queries = [4] Output: [2] Explanation: The diagram above
 * shows the tree after removing the subtree rooted at node with value 4. The height of the tree is 2 (The path 1 -> 3
 * -> 2). Example 2:
 *
 *
 * Input: root = [5,8,9,2,1,3,7,4,6], queries = [3,2,4,8] Output: [3,2,3,2] Explanation: We have the following queries:
 * - Removing the subtree rooted at node with value 3. The height of the tree becomes 3 (The path 5 -> 8 -> 2 -> 4). -
 * Removing the subtree rooted at node with value 2. The height of the tree becomes 2 (The path 5 -> 8 -> 1). - Removing
 * the subtree rooted at node with value 4. The height of the tree becomes 3 (The path 5 -> 8 -> 2 -> 6). - Removing the
 * subtree rooted at node with value 8. The height of the tree becomes 2 (The path 5 -> 9 -> 3).
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is n. 2 <= n <= 105 1 <= Node.val <= n All the values in the tree are unique. m ==
 * queries.length 1 <= m <= min(n, 104) 1 <= queries[i] <= n queries[i] != root.val
 */
public class HeightofBinaryTreeAfterSubtreeRemovalQueries {

    public static void main(String[] args) {
        HeightofBinaryTreeAfterSubtreeRemovalQueries o = new HeightofBinaryTreeAfterSubtreeRemovalQueries();
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(8);
        root.right = new TreeNode(9);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(1);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(4);
        root.left.left.right = new TreeNode(6);
        System.out.println(o.treeQueries(root, new int[] { 8, 3, 2, 4 }));
    }

    public static class TreeNode {
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

    public int[] treeQueries(TreeNode root, int[] queries) {
        Map<Integer, Integer> resultMap = new HashMap<>();
        Map<TreeNode, Integer> heightCache = new HashMap<>();

        // Run DFS to fill resultMap with maximum heights after each query
        dfs(root, 0, 0, resultMap, heightCache);

        int[] result = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            result[i] = resultMap.get(queries[i]);
        }
        return result;
    }

    // Function to calculate the height of the tree
    private int height(TreeNode node, Map<TreeNode, Integer> heightCache) {
        if (node == null) {
            return -1;
        }

        // Return cached height if already calculated
        if (heightCache.containsKey(node)) {
            return heightCache.get(node);
        }

        int h = 1 + Math.max(height(node.left, heightCache), height(node.right, heightCache));
        heightCache.put(node, h);
        return h;
    }

    // DFS to precompute the maximum values after removing the subtree
    private void dfs(TreeNode node, int depth, int maxVal, Map<Integer, Integer> resultMap,
            Map<TreeNode, Integer> heightCache) {
        if (node == null) {
            return;
        }

        resultMap.put(node.val, maxVal);

        // Traverse left and right subtrees while updating max values
        dfs(node.left, depth + 1, Math.max(maxVal, depth + 1 + height(node.right, heightCache)), resultMap,
                heightCache);
        dfs(node.right, depth + 1, Math.max(maxVal, depth + 1 + height(node.left, heightCache)), resultMap,
                heightCache);
    }

    //Eulerian Tour
    //https://leetcode.com/problems/height-of-binary-tree-after-subtree-removal-queries/editorial/?envType=daily-question&envId=2024-10-26
    public int[] treeQueries_v1(TreeNode root, int[] queries) {
        // Lists and maps to store tree information
        List<Integer> eulerTour = new ArrayList<>();
        Map<Integer, Integer> nodeHeights = new HashMap<>();
        Map<Integer, Integer> firstOccurrence = new HashMap<>();
        Map<Integer, Integer> lastOccurrence = new HashMap<>();

        // Perform DFS to build Euler tour and node information
        dfs(root, 0, eulerTour, nodeHeights, firstOccurrence, lastOccurrence);

        int tourSize = eulerTour.size();
        int[] maxDepthLeft = new int[tourSize];
        int[] maxDepthRight = new int[tourSize];

        // Initialize the first and last elements of maxHeight arrays
        maxDepthLeft[0] = maxDepthRight[tourSize - 1] = nodeHeights.get(
                root.val
        );

        // Build maxDepthLeft and maxDepthRight array
        for (int i = 1; i < tourSize; i++) {
            maxDepthLeft[i] = Math.max(
                    maxDepthLeft[i - 1],
                    nodeHeights.get(eulerTour.get(i))
            );
        }

        for (int i = tourSize - 2; i >= 0; i--) {
            maxDepthRight[i] = Math.max(
                    maxDepthRight[i + 1],
                    nodeHeights.get(eulerTour.get(i))
            );
        }

        // Process queries
        int[] results = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int queryNode = queries[i];
            int leftMax = (firstOccurrence.get(queryNode) > 0)
                    ? maxDepthLeft[firstOccurrence.get(queryNode) - 1]
                    : 0;
            int rightMax = (lastOccurrence.get(queryNode) < tourSize - 1)
                    ? maxDepthRight[lastOccurrence.get(queryNode) + 1]
                    : 0;
            results[i] = Math.max(leftMax, rightMax);
        }

        return results;
    }

    // Depth-first search to build the Euler tour and store node information
    private void dfs(
            TreeNode root,
            int height,
            List<Integer> eulerTour,
            Map<Integer, Integer> nodeHeights,
            Map<Integer, Integer> firstOccurrence,
            Map<Integer, Integer> lastOccurrence
    ) {
        if (root == null) return;

        nodeHeights.put(root.val, height);
        firstOccurrence.put(root.val, eulerTour.size());
        eulerTour.add(root.val);

        dfs(
                root.left,
                height + 1,
                eulerTour,
                nodeHeights,
                firstOccurrence,
                lastOccurrence
        );
        dfs(
                root.right,
                height + 1,
                eulerTour,
                nodeHeights,
                firstOccurrence,
                lastOccurrence
        );

        lastOccurrence.put(root.val, eulerTour.size());
        eulerTour.add(root.val);
    }


}
