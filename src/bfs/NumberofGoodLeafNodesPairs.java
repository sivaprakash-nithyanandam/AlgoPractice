package bfs;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 18,2024 - 8:18 PM
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/description/
 *
 * You are given the root of a binary tree and an integer distance. A pair of two different leaf nodes of a binary tree is said to be good if the length of the shortest path between them is less than or equal to distance.
 *
 * Return the number of good leaf node pairs in the tree.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [1,2,3,null,4], distance = 3
 * Output: 1
 * Explanation: The leaf nodes of the tree are 3 and 4 and the length of the shortest path between them is 3. This is the only good pair.
 * Example 2:
 *
 *
 * Input: root = [1,2,3,4,5,6,7], distance = 3
 * Output: 2
 * Explanation: The good pairs are [4,5] and [6,7] with shortest path = 2. The pair [4,6] is not good because the length of ther shortest path between them is 4.
 * Example 3:
 *
 * Input: root = [7,1,4,6,null,5,3,null,null,null,null,null,2], distance = 3
 * Output: 1
 * Explanation: The only good pair is [2,5].
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 210].
 * 1 <= Node.val <= 100
 * 1 <= distance <= 10
 */

/*
https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/editorial/
 */
public class NumberofGoodLeafNodesPairs {

    public static void main(String[] args) {
        NumberofGoodLeafNodesPairs o = new NumberofGoodLeafNodesPairs();
        TreeNode tn = new TreeNode(1);
        tn.left = new TreeNode(2);
        tn.left.left = new TreeNode(4);
        tn.left.right = new TreeNode(5);

        tn.right = new TreeNode(3);
        tn.right.left = new TreeNode(6);
        tn.right.right = new TreeNode(7);

        System.out.println(o.countPairs_3(tn, 3));
    }

    int ans = 0;

    public int countPairs_3(TreeNode root, int distance) {

        postOrderDfs(root, distance);
        return ans;
    }

    private int[] postOrderDfs(TreeNode node, int dist) {

        if (node == null) {
            return new int[dist+1];
        }

        if (node.left == null && node.right == null) {
            int[] res = new int[dist+1];
            res[1]++;
            return res;
        }

        int[] l = postOrderDfs(node.left, dist);
        int[] r = postOrderDfs(node.right, dist);

        for (int i = 1; i <= dist; i++) {
            for (int j = 1; j <= dist; j++) {
                if (i+j <= dist) {
                    ans += l[i] * r[j];
                }
            }
        }
        int[] res = new int[dist+1];

        for (int i = 0; i < dist; i++) {
            res[i+1] = l[i] + r[i];
        }

        return res;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }


    public int countPairs(TreeNode root, int distance) {
        Map<TreeNode, List<TreeNode>> graph = new HashMap<>();
        Set<TreeNode> leafNodes = new HashSet<>();

        traverseTree(root, null, graph, leafNodes);

        int ans = 0;

        for (TreeNode leaf : leafNodes) {
            Queue<TreeNode> bfsQueue = new LinkedList<>();
            Set<TreeNode> seen = new HashSet<>();
            bfsQueue.add(leaf);
            seen.add(leaf);
            // Go through all nodes that are within the given distance of
            // the current leaf node
            for (int i = 0; i <= distance; i++) {
                // Clear all nodes in the queue (distance i away from leaf node)
                // Add the nodes' neighbors (distance i+1 away from leaf node)
                int size = bfsQueue.size();
                for (int j = 0; j < size; j++) {
                    // If current node is a new leaf node, add the found pair to our count
                    TreeNode currNode = bfsQueue.remove();
                    if (leafNodes.contains(currNode) && currNode != leaf) {
                        ans++;
                    }
                    if (graph.containsKey(currNode)) {
                        for (TreeNode neighbor : graph.get(currNode)) {
                            if (!seen.contains(neighbor)) {
                                bfsQueue.add(neighbor);
                                seen.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        return ans / 2;
    }

    private void traverseTree(TreeNode currNode, TreeNode prevNode, Map<TreeNode, List<TreeNode>> graph,
            Set<TreeNode> leafNodes) {
        if (currNode == null) {
            return;
        }
        if (currNode.left == null && currNode.right == null) {
            leafNodes.add(currNode);
        }
        if (prevNode != null) {
            graph.computeIfAbsent(prevNode, k -> new ArrayList<TreeNode>());
            graph.get(prevNode).add(currNode);
            graph.computeIfAbsent(currNode, k -> new ArrayList<TreeNode>());
            graph.get(currNode).add(prevNode);
        }
        traverseTree(currNode.left, currNode, graph, leafNodes);
        traverseTree(currNode.right, currNode, graph, leafNodes);
    }


    public int countPairs_2(TreeNode root, int distance) {
        return postOrder(root, distance)[11];
    }

    private int[] postOrder(TreeNode currentNode, int distance) {
        if (currentNode == null) {
            return new int[12];
        } else if (currentNode.left == null && currentNode.right == null) {
            int[] current = new int[12];
            // Leaf node's distance from itself is 0
            current[0] = 1;
            return current;
        }

        // Leaf node count for a given distance i
        int[] left = postOrder(currentNode.left, distance);
        int[] right = postOrder(currentNode.right, distance);

        int[] current = new int[12];

        // Combine the counts from the left and right subtree and shift by
        // +1 distance
        for (int i = 0; i < 10; i++) {
            current[i + 1] += left[i] + right[i];
        }

        // Initialize to total number of good leaf nodes pairs from left and right subtrees.
        current[11] += left[11] + right[11];

        // Iterate through possible leaf node distance pairs
        for (int d1 = 0; d1 <= distance; d1++) {
            for (int d2 = 0; d2 <= distance; d2++) {
                if (2 + d1 + d2 <= distance) {
                    // If the total path distance is less than the given distance limit,
                    // then add to the total number of good pairs
                    current[11] += left[d1] * right[d2];
                }
            }
        }

        return current;
    }
}
