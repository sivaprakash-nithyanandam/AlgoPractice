package Graph;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Dec 30,2022 - 3:56 PM
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/explore/learn/card/graph/621/algorithms-to-construct-minimum-spanning-tree/3857/
 *
 * You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] = [xi, yi].
 *
 * The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them: |xi - xj| + |yi - yj|, where |val| denotes the absolute value of val.
 *
 * Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path between any two points.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
 * Output: 20
 * Explanation:
 *
 * We can connect the points as shown above to get the minimum cost of 20.
 * Notice that there is a unique path between every pair of points.
 * Example 2:
 *
 * Input: points = [[3,12],[-2,5],[-4,1]]
 * Output: 18
 *
 *
 * Constraints:
 *
 * 1 <= points.length <= 1000
 * -106 <= xi, yi <= 106
 * All pairs (xi, yi) are distinct.
 */
public class MinCosttoConnectAllPoints {

    public static void main(String[] args) {
        MinCosttoConnectAllPoints o = new MinCosttoConnectAllPoints();
        System.out.println(o.minCostConnectPoints_prims(new int[][]{{0,0},{2,2},{3,10},{5,2},{7,0}}));
    }

    public int minCostConnectPoints_kruskal(int[][] points) {

        int n = points.length;
        UnionFind uf = new UnionFind(n);
        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                int dist = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{dist, i, j});
            }
        }

        Collections.sort(edges, (a, b) -> (a[0] - b[0]));
        int minCost = 0;

        for (int[] edge : edges) {
            int node1 = edge[1];
            int node2 = edge[2];

            if (uf.union(node1, node2)) {
                minCost += edge[0];
            }
        }
        return minCost;
    }

    public class UnionFind {
        int[] arr;
        int[] rank;

        UnionFind(int n) {
            arr = new int[n];
            rank = new int[n];

            IntStream.range(0, n).forEach(i -> arr[i] = i);
        }

        private boolean union(int node1, int node2) {
            int p1 = find(node1);
            int p2 = find(node2);

            if (p1 != p2) {
                if (rank[p2] > rank[p1]) {
                    arr[p1] = p2;
                    rank[p2]++;
                } else {
                    arr[p2] = p1;
                    rank[p1]++;
                }
                return true;
            } else {
                return false;
            }

        }

        private int find(int node) {
            if (arr[node] == node) {
                return node;
            }
            return arr[node] = find(arr[node]);
        }

    }

    public int minCostConnectPoints_prims(int[][] points) {
        int n = points.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((n1, n2) -> n1[2] - n2[2]);
        boolean[] vis = new boolean[points.length];
        vis[0] = true;
        for (int i = 1; i < n; i++) {
            int dist = Math.abs(points[0][0] - points[i][0]) + Math.abs(points[0][1] - points[i][1]);
            pq.add(new int[]{0, i, dist});
        }

        int resCost = 0;
        int remV = n-1;

        while(!pq.isEmpty() && remV > 0) {
            int[] v = pq.poll();

            resCost += v[2];
            vis[v[1]] = true;
            int i = v[1];

            for (int j = 1; j < n; j++) {
                if (vis[j]) {
                    continue;
                }
                int dist = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                pq.add(new int[]{i, j, dist});
            }
            remV--;
        }
        return resCost;
    }

}
