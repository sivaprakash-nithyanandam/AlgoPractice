package matrix;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 07,2024 - 11:09 AM
 */

/**
 * https://leetcode.com/contest/weekly-contest-405/problems/count-submatrices-with-equal-frequency-of-x-and-y/description/
 *
 * Given a 2D character matrix grid, where grid[i][j] is either 'X', 'Y', or '.', return the number of
 * submatrices
 *  that contains:
 *
 * grid[0][0]
 * an equal frequency of 'X' and 'Y'.
 * at least one 'X'.
 *
 *
 * Example 1:
 *
 * Input: grid = [["X","Y","."],["Y",".","."]]
 *
 * Output: 3
 *
 * Explanation:
 *
 *
 *
 * Example 2:
 *
 * Input: grid = [["X","X"],["X","Y"]]
 *
 * Output: 0
 *
 * Explanation:
 *
 * No submatrix has an equal frequency of 'X' and 'Y'.
 *
 * Example 3:
 *
 * Input: grid = [[".","."],[".","."]]
 *
 * Output: 0
 *
 * Explanation:
 *
 * No submatrix has at least one 'X'.
 *
 *
 *
 * Constraints:
 *
 * 1 <= grid.length, grid[i].length <= 1000
 * grid[i][j] is either 'X', 'Y', or '.'.
 */
public class CountSubmatricesWithEqualFrequencyofXandY {

    public static void main(String[] args) {
        CountSubmatricesWithEqualFrequencyofXandY o = new CountSubmatricesWithEqualFrequencyofXandY();
        System.out.println(o.numberOfSubmatrices(new char[][]{{'X','Y','.'},{'Y','.','.'}}));
    }

    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] px = new int[m+1][n+1];
        int[][] py = new int[m+1][n+1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'X') {
                    px[i+1][j+1] = 1;
                } else if (grid[i][j] == 'Y') {
                    py[i+1][j+1] = 1;
                }
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                px[i][j] += px[i-1][j] + px[i][j-1] - px[i-1][j-1];
                py[i][j] += py[i-1][j] + py[i][j-1] - py[i-1][j-1];
            }
        }

        int res = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (px[i][j] > 0 && px[i][j] == py[i][j]) {
                    res++;
                }
            }
        }
        return res;
    }

}
