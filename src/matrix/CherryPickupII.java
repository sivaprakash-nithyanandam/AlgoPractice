package matrix;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 11,2024 - 8:47 AM
 */

import java.util.Arrays;

/**
 * https://leetcode.com/problems/cherry-pickup-ii/description/
 *
 * You are given a rows x cols matrix grid representing a field of cherries where grid[i][j] represents the number of cherries that you can collect from the (i, j) cell.
 *
 * You have two robots that can collect cherries for you:
 *
 * Robot #1 is located at the top-left corner (0, 0), and
 * Robot #2 is located at the top-right corner (0, cols - 1).
 * Return the maximum number of cherries collection using both robots by following the rules below:
 *
 * From a cell (i, j), robots can move to cell (i + 1, j - 1), (i + 1, j), or (i + 1, j + 1).
 * When any robot passes through a cell, It picks up all cherries, and the cell becomes an empty cell.
 * When both robots stay in the same cell, only one takes the cherries.
 * Both robots cannot move outside of the grid at any moment.
 * Both robots should reach the bottom row in grid.
 *
 *
 * Example 1:
 *
 *
 * Input: grid = [[3,1,1],[2,5,1],[1,5,5],[2,1,1]]
 * Output: 24
 * Explanation: Path of robot #1 and #2 are described in color green and blue respectively.
 * Cherries taken by Robot #1, (3 + 2 + 5 + 2) = 12.
 * Cherries taken by Robot #2, (1 + 5 + 5 + 1) = 12.
 * Total of cherries: 12 + 12 = 24.
 * Example 2:
 *
 *
 * Input: grid = [[1,0,0,0,0,0,1],[2,0,0,0,0,3,0],[2,0,9,0,0,0,0],[0,3,0,5,4,0,0],[1,0,2,3,0,0,6]]
 * Output: 28
 * Explanation: Path of robot #1 and #2 are described in color green and blue respectively.
 * Cherries taken by Robot #1, (1 + 9 + 5 + 2) = 17.
 * Cherries taken by Robot #2, (1 + 3 + 4 + 3) = 11.
 * Total of cherries: 17 + 11 = 28.
 *
 *
 * Constraints:
 *
 * rows == grid.length
 * cols == grid[i].length
 * 2 <= rows, cols <= 70
 * 0 <= grid[i][j] <= 100
 */
public class CherryPickupII {

    public static void main(String[] args) {
        CherryPickupII o = new CherryPickupII();
        System.out.println(o.cherryPickup(new int[][]{{3,1,1},{2,5,1},{1,5,5},{2,1,1}}));
    }

    //https://www.youtube.com/watch?v=c1stwk2TbNk
    public int cherryPickup(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][][] mem = new int[rows][cols][cols];
        for (int[][] d2 : mem) {
            for (int[] d1: d2) {
                Arrays.fill(d1, -1);
            }
        }

        return helper(grid, mem, rows, cols, 0, 0, cols-1);
    }

    private int helper(int[][] grid, int[][][] mem, int rows, int cols, int r, int c1, int c2) {
        if (c1 < 0 || c2 == cols || c1 >= c2 || r == rows) {
            return 0;
        }

        if (mem[r][c1][c2] != -1) {
            return mem[r][c1][c2];
        }

        int[] dirs = new int[]{-1, 0, 1};

        int curr = grid[r][c1] + grid[r][c2];
        int max = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                max = Math.max(max, helper(grid, mem, rows, cols, r+1, c1+dirs[i], c2+dirs[j]));
            }
        }
        return mem[r][c1][c2] = max + curr;
    }

    public int cherryPickup_another_simplified(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] mem = new int[cols][cols];

        int[] dirs = new int[]{-1, 0, 1};

        for (int r = rows-1; r >= 0; r--) {

            int[][] currMem = new int[cols][cols];

            for (int c1 = 0; c1 < cols-1; c1++) {
                for (int c2 = c1+1; c2 < cols; c2++) {

                    int curr = grid[r][c1] + grid[r][c2];
                    int max = 0;

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            int nc1 = c1 + dirs[i];
                            int nc2 = c2 + dirs[j];

                            if (nc1 < 0 || nc2 == cols || nc1 >= nc2) {
                                continue;
                            }

                            max = Math.max(max, mem[nc1][nc2]);
                        }
                    }

                    currMem[c1][c2] = curr + max;
                }
            }
            mem = currMem;
        }
        return mem[0][cols-1];
    }
}
