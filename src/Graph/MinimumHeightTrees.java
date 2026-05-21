package Graph;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Dec 29,2022 - 3:21 PM
 */

import java.util.*;

/**
 * https://leetcode.com/explore/learn/card/graph/623/kahns-algorithm-for-topological-sorting/3953/
 *
 * A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.
 *
 * Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges where edges[i] = [ai, bi] indicates that there is an undirected edge between the two nodes ai and bi in the tree, you can choose any node of the tree as the root. When you select a node x as the root, the result tree has height h. Among all possible rooted trees, those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).
 *
 * Return a list of all MHTs' root labels. You can return the answer in any order.
 *
 * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: n = 4, edges = [[1,0],[1,2],[1,3]]
 * Output: [1]
 * Explanation: As shown, the height of the tree is 1 when the root is the node with label 1 which is the only MHT.
 * Example 2:
 *
 *
 * Input: n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
 * Output: [3,4]
 *
 *
 * Constraints:
 *
 * 1 <= n <= 2 * 104
 * edges.length == n - 1
 * 0 <= ai, bi < n
 * ai != bi
 * All the pairs (ai, bi) are distinct.
 * The given input is guaranteed to be a tree and there will be no repeated edges.
 */

/**
 * https://www.youtube.com/channel/UCuxmikkhqbmBOUVxf-61hxw
 */
public class MinimumHeightTrees {

    public static void main(String[] args) {
        MinimumHeightTrees o = new MinimumHeightTrees();
        System.out.println(o.findMinHeightTrees(6, new int[][]{{3,0},{3,1},{3,2},{3,4},{5,4}}));
        System.out.println(o.findMinHeightTrees(4, new int[][]{{1,0},{1,2},{1,3}}));
    }

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        int[] counts = new int[n];
        int[] links = new int[n];
        for (int[] edge : edges) {
            links[edge[0]] ^= edge[1];
            counts[edge[0]]++;
            links[edge[1]] ^= edge[0];
            counts[edge[1]]++;
        }
        Queue<Integer> Qu = new LinkedList<>();
        int[] dists = new int[n];
        for (int i = 0; i < n; i++) {
            if (counts[i] == 1)
                Qu.add(i);
        }
        int stp = 1;
        while (!Qu.isEmpty()) {
            int size = Qu.size();
            for (int j = 0; j < size; j++) {
                int tmp = Qu.poll();
                links[links[tmp]] ^= tmp;
                counts[links[tmp]]--;
                if (counts[links[tmp]] == 1) {
                    dists[links[tmp]] = Math.max(stp, dists[links[tmp]]);
                    Qu.add(links[tmp]);
                }
            }
            stp++;
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(dists[i], max);
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (dists[i] == max)
                res.add(i);
        }
        return res;
    }

    public List<Integer> findMinHeightTrees_v2(int n, int[][] edges) {

        if (n==1) {
            return Collections.singletonList(0);
        }

        int[] ind = new int[n];
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int[] edge : edges) {
            ind[edge[0]]++;
            ind[edge[1]]++;
            map.computeIfAbsent(edge[0], x -> new ArrayList<>()).add(edge[1]);
            map.computeIfAbsent(edge[1], x -> new ArrayList<>()).add(edge[0]);
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < ind.length; i++) {
            if (ind[i] == 1) {
                q.add(i);
            }
        }

        int currLeaves = 0;
        while (n - currLeaves > 2) {
            int qs = q.size();
            currLeaves += qs;
            while (qs-- > 0) {
                int leaf = q.poll();
                for (int parent : map.get(leaf)) {
                    ind[parent]--;
                    if (ind[parent] == 1) {
                        q.add(parent);
                    }
                }
            }
        }
        List<Integer> list = new ArrayList<>();
        list.addAll(q);
        return list;
    }
    public List<Integer> findMinHeightTrees_v1(int n, int[][] edges) {

        // edge cases
        if (n < 2) {
            ArrayList<Integer> centroids = new ArrayList<>();
            for (int i = 0; i < n; i++)
                centroids.add(i);
            return centroids;
        }

        // Build the graph with the adjacency list
        ArrayList<Set<Integer>> neighbors = new ArrayList<>();
        for (int i = 0; i < n; i++)
            neighbors.add(new HashSet<Integer>());

        for (int[] edge : edges) {
            Integer start = edge[0], end = edge[1];
            neighbors.get(start).add(end);
            neighbors.get(end).add(start);
        }

        // Initialize the first layer of leaves
        ArrayList<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (neighbors.get(i).size() == 1)
                leaves.add(i);

        // Trim the leaves until reaching the centroids
        int remainingNodes = n;
        //At most, the center can have 2 nodes. Ex: 1 or 1 <-> 2, 1 <-> 2 <-> 3 <-> 4 => [2, 3]
        while (remainingNodes > 2) {
            remainingNodes -= leaves.size();
            ArrayList<Integer> newLeaves = new ArrayList<>();

            // remove the current leaves along with the edges
            for (Integer leaf : leaves) {
                // the only neighbor left for the leaf node
                Integer neighbor = neighbors.get(leaf).iterator().next();
                // remove the edge along with the leaf node
                neighbors.get(neighbor).remove(leaf);
                if (neighbors.get(neighbor).size() == 1)
                    newLeaves.add(neighbor);
            }

            // prepare for the next round
            leaves = newLeaves;
        }

        // The remaining nodes are the centroids of the graph
        return leaves;
    }
}
