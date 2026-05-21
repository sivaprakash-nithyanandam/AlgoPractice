package dfs;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Aug 28,2024 - 9:35 AM
 */

/**
 * https://leetcode.com/problems/count-sub-islands/
 *
 *You are given two m x n binary matrices grid1 and grid2 containing only 0's (representing water) and 1's (representing land). An island is a group of 1's connected 4-directionally (horizontal or vertical). Any cells outside of the grid are considered water cells.
 *
 * An island in grid2 is considered a sub-island if there is an island in grid1 that contains all the cells that make up this island in grid2.
 *
 * Return the number of islands in grid2 that are considered sub-islands.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: grid1 = [[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]], grid2 = [[1,1,1,0,0],[0,0,1,1,1],[0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]
 * Output: 3
 * Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
 * The 1s colored red in grid2 are those considered to be part of a sub-island. There are three sub-islands.
 * Example 2:
 *
 *
 * Input: grid1 = [[1,0,1,0,1],[1,1,1,1,1],[0,0,0,0,0],[1,1,1,1,1],[1,0,1,0,1]], grid2 = [[0,0,0,0,0],[1,1,1,1,1],[0,1,0,1,0],[0,1,0,1,0],[1,0,0,0,1]]
 * Output: 2
 * Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
 * The 1s colored red in grid2 are those considered to be part of a sub-island. There are two sub-islands.
 *
 *
 * Constraints:
 *
 * m == grid1.length == grid2.length
 * n == grid1[i].length == grid2[i].length
 * 1 <= m, n <= 500
 * grid1[i][j] and grid2[i][j] are either 0 or 1.
 */

/**
 * https://leetcode.com/problems/count-sub-islands/
 */
public class CountSubIslands {

    public static void main(String[] args) {
        CountSubIslands o = new CountSubIslands();

        System.out.println(o.countSubIslands(new int[][]{{1,1,1,0,0},{0,1,1,1,1},{0,0,0,0,0},{1,0,0,0,0},{1,1,0,1,1}}
        , new int[][]{{1,1,1,0,0},{0,0,1,1,1},{0,1,0,0,0},{1,0,1,1,0},{0,1,0,1,0}}));

        System.out.println(o.countSubIslands(new int[][]{{1,1,1,1,0,0},{1,1,0,1,0,0},{1,0,0,1,1,1},{1,1,1,0,0,1},{1,1,1,1,1,0},{1,0,1,0,1,0},{0,1,1,1,0,1},{1,0,0,0,1,1},{1,0,0,0,1,0},{1,1,1,1,1,0}},
                new int[][]{{1,1,1,1,0,1},{0,0,1,0,1,0},{1,1,1,1,1,1},{0,1,1,1,1,1},{1,1,1,0,1,0},{0,1,1,1,1,1},{1,1,0,1,1,1},{1,0,0,1,0,1},{1,1,1,1,1,1},{1,0,0,1,0,0}}));
    }

    int[][] dirs = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};

    public int countSubIslands(int[][] grid1, int[][] grid2) {

        int m = grid2.length;
        int n = grid2[0].length;

        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    boolean isIsland = true;
                    grid2[i][j] = 0;
                    if (grid1[i][j] == 0) {
                        isIsland = false;
                    }
                    isIsland = isSubIsland(grid2, grid1, i, j, m, n) && isIsland;
                    res += isIsland ? 1 : 0;
                }
            }
        }
        return res;
    }

    private boolean isSubIsland(int[][] grid2, int[][] grid1, int i, int j, int m, int n) {

        boolean isIsland = true;

        for (int[] dir : dirs) {
            int ni = i + dir[0];
            int nj = j + dir[1];

            if (ni < 0 || nj < 0 || ni == m || nj == n || grid2[ni][nj] == 0) {
                continue;
            }
            if (grid2[ni][nj] == 1) {

                grid2[ni][nj] = 0;
                if (grid1[ni][nj] == 0) {
                    isIsland = false;
                }
                isIsland = isSubIsland(grid2, grid1, ni, nj, m, n) && isIsland;
            }
        }
        return isIsland;
    }

    // Directions in which we can traverse inside the grids.
    private final int[][] directions = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 },
    };

    // Helper method to check if the cell at the position (x, y) in the 'grid'
    // is a land cell.
    private boolean isCellLand(int x, int y, int[][] grid) {
        return grid[x][y] == 1;
    }

    // Union-Find class.
    class UnionFind {

        private final int[] parent;
        private final int[] rank;

        // Initialize union-find object with 'n' elements.
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; ++i) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        // Find the root of element 'u', using the path-compression technique.
        int find(int u) {
            if (parent[u] != u) {
                parent[u] = find(parent[u]);
            }
            return parent[u];
        }

        // Union two components of elements 'u' and 'v' respectively based on
        // their ranks.
        void unionSets(int u, int v) {
            int rootU = find(u);
            int rootV = find(v);
            if (rootU != rootV) {
                if (rank[rootU] > rank[rootV]) {
                    parent[rootV] = rootU;
                } else if (rank[rootU] < rank[rootV]) {
                    parent[rootU] = rootV;
                } else {
                    parent[rootV] = rootU;
                    rank[rootU]++;
                }
            }
        }
    }

    // Helper method to convert (x, y) position to a 1-dimensional index.
    private int convertToIndex(int x, int y, int totalCols) {
        return x * totalCols + y;
    }

    public int countSubIslands_union_find(int[][] grid1, int[][] grid2) {
        int totalRows = grid2.length;
        int totalCols = grid2[0].length;
        UnionFind uf = new UnionFind(totalRows * totalCols);

        // Traverse each land cell of 'grid2'.
        for (int x = 0; x < totalRows; ++x) {
            for (int y = 0; y < totalCols; ++y) {
                if (isCellLand(x, y, grid2)) {
                    // Union adjacent land cells with the current land cell.
                    for (int[] direction : directions) {
                        int nextX = x + direction[0];
                        int nextY = y + direction[1];
                        if (
                                nextX >= 0 &&
                                        nextY >= 0 &&
                                        nextX < totalRows &&
                                        nextY < totalCols &&
                                        isCellLand(nextX, nextY, grid2)
                        ) {
                            uf.unionSets(
                                    convertToIndex(x, y, totalCols),
                                    convertToIndex(nextX, nextY, totalCols)
                            );
                        }
                    }
                }
            }
        }

        // Traverse 'grid2' land cells and mark that cell's root as not a sub-island
        // if the land cell is not present at the respective position in 'grid1'.
        boolean[] isSubIsland = new boolean[totalRows * totalCols];
        for (int i = 0; i < isSubIsland.length; i++) {
            isSubIsland[i] = true;
        }
        for (int x = 0; x < totalRows; ++x) {
            for (int y = 0; y < totalCols; ++y) {
                if (isCellLand(x, y, grid2) && !isCellLand(x, y, grid1)) {
                    int root = uf.find(convertToIndex(x, y, totalCols));
                    isSubIsland[root] = false;
                }
            }
        }

        // Count all the sub-islands.
        int subIslandCounts = 0;
        for (int x = 0; x < totalRows; ++x) {
            for (int y = 0; y < totalCols; ++y) {
                if (isCellLand(x, y, grid2)) {
                    int root = uf.find(convertToIndex(x, y, totalCols));
                    if (isSubIsland[root]) {
                        subIslandCounts++;
                        // One cell can be the root of multiple land cells, so to
                        // avoid counting the same island multiple times, mark it as false.
                        isSubIsland[root] = false;
                    }
                }
            }
        }

        return subIslandCounts;
    }
}
