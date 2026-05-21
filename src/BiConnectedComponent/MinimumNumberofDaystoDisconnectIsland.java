package BiConnectedComponent;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Aug 13,2024 - 7:46 PM
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://leetcode.com/problems/minimum-number-of-days-to-disconnect-island/description/
 *
 * You are given an m x n binary grid grid where 1 represents land and 0 represents water. An island is a maximal 4-directionally (horizontal or vertical) connected group of 1's.
 *
 * The grid is said to be connected if we have exactly one island, otherwise is said disconnected.
 *
 * In one day, we are allowed to change any single land cell (1) into a water cell (0).
 *
 * Return the minimum number of days to disconnect the grid.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: grid = [[0,1,1,0],[0,1,1,0],[0,0,0,0]]
 *
 * Output: 2
 * Explanation: We need at least 2 days to get a disconnected grid.
 * Change land grid[1][1] and grid[0][2] to water and get 2 disconnected island.
 * Example 2:
 *
 *
 * Input: grid = [[1,1]]
 * Output: 2
 * Explanation: Grid of full water is also disconnected ([[1,1]] -> [[0,0]]), 0 islands.
 *
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 30
 * grid[i][j] is either 0 or 1.
 */
public class MinimumNumberofDaystoDisconnectIsland {

    public static void main(String[] args) {
        MinimumNumberofDaystoDisconnectIsland o = new MinimumNumberofDaystoDisconnectIsland();
        System.out.println(o.minDays(new int[][]{{1,1,0,1,1},{1,1,1,1,1},{1,1,0,1,1},{1,1,0,1,1}}));
        System.out.println(o.minDays(new int[][]{{1,0,1,0}}));
        System.out.println(o.minDays(new int[][]{{1,1}}));
        System.out.println(o.minDays(new int[][]{{0,1,1,0},{0,1,1,0},{0,0,0,0}}));
        int max = Arrays.stream(new int[]{1}).max().getAsInt();
    }

    public int minDays(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        Set<Integer> nodes = new HashSet<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    nodes.add(i * n + j);
                }
            }
        }
        if (nodes.size() < 2) {
            return nodes.size();
        }

        int timer = 0;
        int islandCount = 0;
        boolean[] vis = new boolean[m*n];
        int[] tin = new int[m*n];
        int[] lowTime = new int[m*n];
        List<Integer> aps = new ArrayList<>();

        for (int i : nodes) {
            if (!vis[i]) {
                islandCount++;
                tarjanHelper(i, -1, nodes, timer, vis, tin, lowTime, aps, m, n, new char[]{'R', 'D', 'L', 'U'});
            }
        }

        return islandCount > 1 ? 0 : aps.size() > 0 ? 1 : 2;
    }

    private int adj(int node, int m, int n, char dir) {
        int i = node / n;
        int j = node % n;

        if (dir == 'R') {
            return j < n - 1 ? node + 1 : -1;
        } else if (dir == 'D') {
            return i < m - 1 ? node + n : -1;
        } else if (dir == 'L') {
            return j > 0 ? node - 1 : -1;
        } else {
            return i > 0 ? node - n : -1;
        }
    }

    private void tarjanHelper(int node, int parent, Set<Integer> nodes, int timer, boolean[] vis, int[] tin,
            int[] lowTime, List<Integer> aps, int m, int n, char[] dirs) {

        vis[node] = true;
        tin[node] = lowTime[node] = timer++;
        int childs = 0;

        for (char dir : dirs) {
            int adj = adj(node, m, n, dir);
            if (adj == -1 || !nodes.contains(adj) || adj == parent) {
                continue;
            }
            childs++;

            if (!vis[adj]) {
                tarjanHelper(adj, node, nodes, timer, vis, tin, lowTime, aps, m, n, dirs);
                lowTime[node] = Math.min(lowTime[node], lowTime[adj]);

                if (parent != -1 && lowTime[adj] >= tin[node]) {
                    aps.add(node);
                } else if (parent == -1 && lowTime[adj] > tin[node] && childs > 1) {
                    aps.add(node);
                }
            } else {
                lowTime[node] = Math.min(lowTime[node], tin[adj]);
            }
        }
    }
}
