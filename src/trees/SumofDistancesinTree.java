package trees;
/**
 * @time 4/28/24-7:53 PM
 * @author sivaprakashnithyanandam
 */

import java.util.*;

/**
 * https://leetcode.com/problems/sum-of-distances-in-tree/description/?envType=daily-question&envId=2024-04-28
 *
 * There is an undirected connected tree with n nodes labeled from 0 to n - 1 and n - 1 edges.
 *
 * You are given the integer n and the array edges where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the tree.
 *
 * Return an array answer of length n where answer[i] is the sum of the distances between the ith node in the tree and all other nodes.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: n = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
 * Output: [8,12,6,10,10,10]
 * Explanation: The tree is shown above.
 * We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
 * equals 1 + 1 + 2 + 2 + 2 = 8.
 * Hence, answer[0] = 8, and so on.
 * Example 2:
 *
 *
 * Input: n = 1, edges = []
 * Output: [0]
 * Example 3:
 *
 *
 * Input: n = 2, edges = [[1,0]]
 * Output: [1,1]
 *
 *
 * Constraints:
 *
 * 1 <= n <= 3 * 104
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= ai, bi < n
 * ai != bi
 * The given input represents a valid tree.
 */
public class SumofDistancesinTree {

    public static void main(String[] args) {
        SumofDistancesinTree o = new SumofDistancesinTree();
        System.out.println(o.sumOfDistancesInTree_v1(6, new int[][]{{0,1},{0,2},{2,3},{2,4},{2,5}}));
        System.out.println(o.sumOfDistancesInTree(6, new int[][]{{0,1},{0,2},{2,3},{2,4},{2,5}}));
    }

    int[][] graph;
    int[] count;
    int[] res;
    int N;

    /** Same as like below approach, but time efficient
     * Graph is in integer array format
     * @param N
     * @param edges
     * @return
     */
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        this.N = N;
        this.res = new int[N];
        this.graph = new int[N][];
        this.count = new int[N];

        for (int[] e : edges) {
            ++count[e[0]];
            ++count[e[1]];
        }
        for (int i = 0; i < N; i++) {
            graph[i] = new int[count[i]];
        }
        for (int[] e : edges) {
            graph[e[0]][--count[e[0]]] = e[1];
            graph[e[1]][--count[e[1]]] = e[0];
        }
        dfs1(0, -1);
        dfs2(0, -1);
        return res;
    }
    public void dfs1(int cur, int parent) {
        count[cur] = 1;
        for (int child : graph[cur]) {
            if (child != parent) {
                dfs1(child, cur);
                count[cur] += count[child];
                res[cur] += res[child] + count[child];
            }
        }
    }
    public void dfs2(int cur, int parent) {
        for (int child : graph[cur]) {
            if (child != parent) {
                res[child] = res[cur] + N - 2 * count[child];
                dfs2(child, cur);
            }
        }
    }

    public int[] sumOfDistancesInTree_v1(int N, int[][] edges) {
        int[] res = new int[N];
        if (N == 1) {
            return res;
        }

        int[] count = new int[N];

        Map<Integer, List<Integer>> conn = new HashMap<>();

        for (int[] edge : edges) {
            conn.computeIfAbsent(edge[0], x -> new ArrayList<>()).add(edge[1]);
            conn.computeIfAbsent(edge[1], x -> new ArrayList<>()).add(edge[0]);
        }

        dfs(0, -1, conn, count, res);
        dfs2(0, -1, conn, count, res, N);

        return res;
    }

    private void dfs(int curr, int parent, Map<Integer, List<Integer>> conn, int[] count, int[] res) {

        for (int child : conn.get(curr)) {
            if (child == parent) {
                continue;
            }
            dfs(child, curr, conn, count, res);
            count[curr] += count[child];
            res[curr] += res[child] + count[child];
        }
        count[curr]++;
    }

    private void dfs2(int curr, int parent, Map<Integer, List<Integer>> conn, int[] count, int[] res, int N) {

        for (int child : conn.get(curr)) {
            if (child == parent) {
                continue;
            }
            res[child] = res[curr] - count[child] + (N - count[child]);
            dfs2(child, curr, conn, count, res, N);
        }
    }
}
