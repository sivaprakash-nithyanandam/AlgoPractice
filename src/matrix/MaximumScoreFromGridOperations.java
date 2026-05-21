package matrix;

import java.util.Arrays;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 20,2024 - 6:32 PM
 */

//TODO: Go through
public class MaximumScoreFromGridOperations {

    public static void main(String[] args) {
        MaximumScoreFromGridOperations o = new MaximumScoreFromGridOperations();
        System.out.println(o.maximumScore(new int[][]{{10,9,0,0,15},{7,1,0,8,0},{5,20,0,11,0},{0,0,0,1,2},{8,12,1,10,3}}));
        System.out.println(o.maximumScore(new int[][]{{0,0,0,0,0},{0,0,3,0,0},{0,1,0,0,0},{5,0,0,3,0},{0,0,0,0,2}}));
    }

    public long maximumScore(int[][] grid) {
        int n = grid.length;

        if (n == 1) {
            return 0;
        }

        // DP arrays
        long[] prevColW = new long[n + 1]; // Previous column with current column's score
        long[] prevColWo = new long[n + 1]; // Previous column without current column's score

        for (int j = 1; j < n; j++) { // For each column
            long[] curColW = new long[n + 1]; // Current column with current column's score
            long[] curColWo = new long[n + 1]; // Current column without current column's score
            Arrays.fill(curColW, Long.MIN_VALUE); // Initialize with minimum values
            Arrays.fill(curColWo, Long.MIN_VALUE); // Initialize with minimum values

            for (int i = 0; i <= n; i++) { // For each row in the previous column
                long prevColVal = 0; // Previous column score when previous black is i and current black is k
                long curColVal = 0; // Current column score when previous black is i and current black is k

                // Calculate initial current column value
                for (int p = 0; p < i; p++) {
                    curColVal += grid[p][j];
                }

                for (int k = 0; k <= n; k++) { // For each row in the current column
                    if (k > 0 && k <= i) {
                        curColVal -= grid[k - 1][j];
                    }
                    if (k > i) {
                        prevColVal += grid[k - 1][j - 1];
                    }

                    curColWo[k] = Math.max(curColWo[k], prevColVal + prevColWo[i]);
                    curColWo[k] = Math.max(curColWo[k], prevColW[i]);
                    curColW[k] = Math.max(curColW[k], curColVal + prevColW[i]);
                    curColW[k] = Math.max(curColW[k], curColVal + prevColVal + prevColWo[i]);
                }
            }

            prevColW = curColW;
            prevColWo = curColWo;
        }

        long maxScore = Long.MIN_VALUE;
        for (long score : prevColW) {
            maxScore = Math.max(maxScore, score);
        }

        return maxScore;
    }

    public long maximumScore_1(int[][] grid) {
        int n = grid.length;
        long[][] colSum = new long[n + 1][n];

        // Precompute column sums
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                colSum[i + 1][j] = colSum[i][j] + grid[i][j];
            }
        }

        // Initialize DP array
        long[][][] dp = new long[n][n + 1][n + 1];
        for (long[][] ddp : dp) {
            for (long[] dddp : ddp) {
                Arrays.fill(dddp, -1);
            }
        }

        // Initial condition
        for (int color = 0; color <= n; color++) {
            dp[0][color][0] = 0;
        }

        // Fill DP table
        for (int col = 0; col < n - 1; col++) {
            for (int color = 0; color <= n; color++) {
                for (int taken = 0; taken <= n; taken++) {
                    if (dp[col][color][taken] == -1) continue;

                    for (int newCol = 0; newCol <= n; newCol++) {
                        long val = dp[col][color][taken];
                        if (newCol > color) {
                            if (color + taken < newCol) {
                                val += colSum[newCol][col] - colSum[color + taken][col];
                            }
                            dp[col + 1][newCol][0] = Math.max(dp[col + 1][newCol][0], val);
                        } else if (newCol < color) {
                            val += colSum[color][col + 1] - colSum[newCol][col + 1];
                            dp[col + 1][newCol][color - newCol] = Math.max(dp[col + 1][newCol][color - newCol], val);
                        } else {
                            dp[col + 1][newCol][0] = Math.max(dp[col + 1][newCol][0], val);
                        }
                    }
                }
            }
        }

        // Calculate the result
        long res = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                res = Math.max(res, dp[n - 1][i][j]);
            }
        }

        return res;
    }

}
