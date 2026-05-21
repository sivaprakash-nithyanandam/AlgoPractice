package disjointset_unionfind;

import java.util.HashSet;
import java.util.Set;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Aug 27,2024 - 10:53 PM
 */

public class MakingALargeIsland {

    public static void main(String[] args) {
        MakingALargeIsland o = new MakingALargeIsland();
        System.out.println(o.largestIsland(new int[][]{{1,1},{1,0}}));
    }

    class UnionFind {

        int[] parent;
        int[] rank;

        UnionFind(int size) {
            parent = new int[size+1];
            rank = new int[size+1];

            for (int i = 0; i <= size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        private int find(int node) {
            if (parent[node] == node) {
                return node;
            }
            return find(parent[node]);
        }

        private void union(int n1, int n2) {
            int pn1 = find(n1);
            int pn2 = find(n2);

            if (pn1 != pn2) {
                if (rank[pn1] > rank[pn2]) {
                    parent[pn2] = pn1;
                    rank[pn1]++;
                } else if (rank[pn2] > rank[pn1]) {
                    parent[pn1] = pn2;
                    rank[pn2]++;
                } else {
                    parent[pn2] = pn1;
                    rank[pn1]++;
                }
            }
        }

    }

    public int largestIsland(int[][] grid) {

        int size = grid.length * grid.length;
        int n = grid.length;
        Set<Integer> seen = new HashSet<>(size);
        UnionFind uf = new UnionFind(size);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int idx = i * n + j;
                if (grid[i][j] == 1 && !seen.contains(idx)) {
                    doGrouping(i, j, grid, seen, n, uf, idx);
                }
            }
        }

        int res = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {

                    Set<Integer> parents = new HashSet<>(4);
                    int total = 1;
                    int idx;
                    if (j < n-1 && grid[i][j+1] == 1) {

                        idx = (i * n) + (j+1);
                        parents.add(uf.find(idx));
                    }

                    if (i < n-1 && grid[i+1][j] == 1) {

                        idx = ((i+1) * n) + j;
                        parents.add(uf.find(idx));
                    }

                    if (j > 0 && grid[i][j-1] == 1) {

                        idx = (i * n) + (j-1);
                        parents.add(uf.find(idx));
                    }

                    if (i > 0 && grid[i-1][j] == 1) {

                        idx = ((i-1) * n) + j;
                        parents.add(uf.find(idx));
                    }
                    for (int parent : parents) {
                        total += uf.rank[parent];
                    }
                    res = Math.max(res, total);
                }
            }
        }

        for (int i = 0; i < size; i++) {
            res = Math.max(res, uf.rank[i]);
        }
        return res;
    }

    private void doGrouping(int i, int j, int[][] grid, Set<Integer> seen, int n, UnionFind uf, int pIdx) {

        seen.add(pIdx);
        int idx;

        if (j < n-1 && grid[i][j+1] == 1) {

            idx = (i * n) + (j+1);
            if (!seen.contains(idx)) {
                uf.union(pIdx, idx);
                doGrouping(i, j+1, grid, seen, n, uf, idx);
            }
        }

        if (i < n-1 && grid[i+1][j] == 1) {

            idx = ((i+1) * n) + j;
            if (!seen.contains(idx)) {
                uf.union(pIdx, idx);
                doGrouping(i+1, j, grid, seen, n, uf, idx);
            }
        }

        if (j > 0 && grid[i][j-1] == 1) {

            idx = (i * n) + (j-1);
            if (!seen.contains(idx)) {
                uf.union(pIdx, idx);
                doGrouping(i, j-1, grid, seen, n, uf, idx);
            }
        }

        if (i > 0 && grid[i-1][j] == 1) {

            idx = ((i-1) * n) + j;
            if (!seen.contains(idx)) {
                uf.union(pIdx, idx);
                doGrouping(i-1, j, grid, seen, n, uf, idx);
            }
        }

    }

}
