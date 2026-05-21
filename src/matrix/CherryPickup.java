package matrix;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 10,2024 - 12:03 AM
 */

//import javafx.util.Pair;

/**
 * https://leetcode.com/problems/cherry-pickup/description/
 *
 * You are given an n x n grid representing a field of cherries, each cell is one of three possible integers.
 *
 * 0 means the cell is empty, so you can pass through,
 * 1 means the cell contains a cherry that you can pick up and pass through, or
 * -1 means the cell contains a thorn that blocks your way.
 * Return the maximum number of cherries you can collect by following the rules below:
 *
 * Starting at the position (0, 0) and reaching (n - 1, n - 1) by moving right or down through valid path cells (cells with value 0 or 1).
 * After reaching (n - 1, n - 1), returning to (0, 0) by moving left or up through valid path cells.
 * When passing through a path cell containing a cherry, you pick it up, and the cell becomes an empty cell 0.
 * If there is no valid path between (0, 0) and (n - 1, n - 1), then no cherries can be collected.
 *
 *
 * Example 1:
 *
 *
 * Input: grid = [[0,1,-1],[1,0,-1],[1,1,1]]
 * Output: 5
 * Explanation: The player started at (0, 0) and went down, down, right right to reach (2, 2).
 * 4 cherries were picked up during this single trip, and the matrix becomes [[0,1,-1],[0,0,-1],[0,0,0]].
 * Then, the player went left, up, up, left to return home, picking up one more cherry.
 * The total number of cherries picked up is 5, and this is the maximum possible.
 * Example 2:
 *
 * Input: grid = [[1,1,-1],[1,-1,1],[-1,1,1]]
 * Output: 0
 *
 *
 * Constraints:
 *
 * n == grid.length
 * n == grid[i].length
 * 1 <= n <= 50
 * grid[i][j] is -1, 0, or 1.
 * grid[0][0] != -1
 * grid[n - 1][n - 1] != -1
 */
public class CherryPickup {

    public static void main(String[] args) {
        CherryPickup o = new CherryPickup();
        System.out.println(o.cherryPickup(new int[][]{{1,1,-1},{1,-1,1},{-1,1,1}}));
        System.out.println(o.cherryPickup(new int[][]{{0,1,-1},{1,0,-1},{1,1,1}}));
    }

    //https://www.youtube.com/watch?v=q3-6jaNvj6c
    public int cherryPickup(int[][] grid) {

        int n = grid.length;
        int[][][] dp = new int[n][n][n];
        int val = dp(grid, 0, 0, 0, n, dp);
        return val == Integer.MIN_VALUE ? 0 : val;
    }

    private int dp(int[][] grid, int r1, int c1, int c2, int n, int[][][] dp) {
        int r2 = r1 + c1 - c2;
        if (r1 >= n || c1 >= n || r2 >= n || c2 >= n || grid[r1][c1] == -1 || grid[r2][c2] == -1) {
            return Integer.MIN_VALUE;
        }
        if (dp[r1][c1][c2] != 0) {
            return dp[r1][c1][c2];
        } else if (r1 == n-1 && c1 == n-1) {
            return grid[r1][c1];
        }

        int val = grid[r1][c1];
        if (c1 != c2) {
            val += grid[r2][c2];
        }

        //right, right
        int p1 = dp(grid, r1, c1+1, c2+1, n, dp);

        //right,down
        int p2 = dp(grid, r1, c1+1, c2, n, dp);

        //down, down
        int p3 = dp(grid, r1+1, c1, c2, n, dp);

        //down, right
        int p4 = dp(grid, r1+1, c1, c2+1, n, dp);

        int max = Math.max(Math.max(p1,p2), Math.max(p3,p4));
        if (max == Integer.MIN_VALUE) {
            val = Integer.MIN_VALUE;
        } else {
            val += max;
        }

        return dp[r1][c1][c2] = val;
    }
}
