package Graph.dfs;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Aug 27,2024 - 8:18 AM
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * https://leetcode.com/problems/path-with-maximum-probability/description/
 *
 * You are given an undirected weighted graph of n nodes (0-indexed), represented by an edge list where edges[i] = [a, b] is an undirected edge connecting the nodes a and b with a probability of success of traversing that edge succProb[i].
 *
 * Given two nodes start and end, find the path with the maximum probability of success to go from start to end and return its success probability.
 *
 * If there is no path from start to end, return 0. Your answer will be accepted if it differs from the correct answer by at most 1e-5.
 *
 *
 *
 * Example 1:
 *
 *
 *
 * Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0, end = 2
 * Output: 0.25000
 * Explanation: There are two paths from start to end, one having a probability of success = 0.2 and the other has 0.5 * 0.5 = 0.25.
 * Example 2:
 *
 *
 *
 * Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.3], start = 0, end = 2
 * Output: 0.30000
 * Example 3:
 *
 *
 *
 * Input: n = 3, edges = [[0,1]], succProb = [0.5], start = 0, end = 2
 * Output: 0.00000
 * Explanation: There is no path between 0 and 2.
 *
 *
 * Constraints:
 *
 * 2 <= n <= 10^4
 * 0 <= start, end < n
 * start != end
 * 0 <= a, b < n
 * a != b
 * 0 <= succProb.length == edges.length <= 2*10^4
 * 0 <= succProb[i] <= 1
 * There is at most one edge between every two nodes.
 */
public class PathwithMaximumProbability {

    public static void main(String[] args) {
        PathwithMaximumProbability o = new PathwithMaximumProbability();
        System.out.println(o.maxProbability(3, new int[][]{{0,1},{1,2},{0,2}}, new double[]{0.5,0.5,0.2}, 0, 2));
    }

    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {

        double[] dist = new double[n];
        dist[start_node] = 1;
        Set<Integer> seen = new HashSet<>();
        Map<Integer, Map<Integer, Double>> adj = new HashMap<>();

        for (int i = 0; i < edges.length; i++) {
            adj.computeIfAbsent(edges[i][0], none -> new HashMap<>()).put(edges[i][1], succProb[i]);
            adj.computeIfAbsent(edges[i][1], none -> new HashMap<>()).put(edges[i][0], succProb[i]);
        }


        dfs(adj, start_node, dist, seen);

        return dist[end_node];
    }

    private void dfs(Map<Integer, Map<Integer, Double>> adj, int node, double[] dist, Set<Integer> seen) {

        if (!adj.containsKey(node) || seen.contains(node)) {
            return;
        }

        seen.add(node);

        for (Map.Entry<Integer, Double> conn : adj.get(node).entrySet()) {
            if (dist[conn.getKey()] < dist[node] * conn.getValue()) {
                dist[conn.getKey()] = dist[node] * conn.getValue();
                dfs(adj, conn.getKey(), dist, seen);
            }
        }
    }

    public double maxProbability_bfs(int n, int[][] edges, double[] succProb, int start, int end) {

        Map<Integer, Map<Integer, Double>> graph = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            graph.computeIfAbsent(edges[i][0], x -> new HashMap<>()).put(edges[i][1], succProb[i]);
            graph.computeIfAbsent(edges[i][1], x -> new HashMap<>()).put(edges[i][0], succProb[i]);
        }
        if (graph.get(start) == null) {
            return 0;
        }
        double[] dist = new double[n];
        dist[start] = 1;
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);

        while (!q.isEmpty()) {
            int node = q.poll();
            for (Map.Entry<Integer, Double> adj : graph.get(node).entrySet()) {
                double d = dist[node] * adj.getValue();
                if (dist[adj.getKey()] < d) {
                    dist[adj.getKey()] = d;
                    q.offer(adj.getKey());
                }
            }
        }
        return dist[end];
    }
}
