package BitMasking;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 19,2024 - 4:00 PM
 */

/**
 * https://leetcode.com/problems/maximize-score-after-n-operations/description/
 *
 * You are given nums, an array of positive integers of size 2 * n. You must perform n operations on this array.
 *
 * In the ith operation (1-indexed), you will:
 *
 * Choose two elements, x and y.
 * Receive a score of i * gcd(x, y).
 * Remove x and y from nums.
 * Return the maximum score you can receive after performing n operations.
 *
 * The function gcd(x, y) is the greatest common divisor of x and y.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2]
 * Output: 1
 * Explanation: The optimal choice of operations is:
 * (1 * gcd(1, 2)) = 1
 * Example 2:
 *
 * Input: nums = [3,4,6,8]
 * Output: 11
 * Explanation: The optimal choice of operations is:
 * (1 * gcd(3, 6)) + (2 * gcd(4, 8)) = 3 + 8 = 11
 * Example 3:
 *
 * Input: nums = [1,2,3,4,5,6]
 * Output: 14
 * Explanation: The optimal choice of operations is:
 * (1 * gcd(1, 5)) + (2 * gcd(2, 4)) + (3 * gcd(3, 6)) = 1 + 4 + 9 = 14
 *
 *
 * Constraints:
 *
 * 1 <= n <= 7
 * nums.length == 2 * n
 * 1 <= nums[i] <= 106
 *
 *
 */
public class MaximizeScoreAfterNOperations {

    public static void main(String[] args) {
        MaximizeScoreAfterNOperations o = new MaximizeScoreAfterNOperations();
        System.out.println(o.maxScore(new int[]{1,2,3,4,5,6}));
    }

    public int maxScore(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n/2 + 1][1 << n];

        return helper(nums, 1, n, dp, 0);
    }

    private int helper(int[] nums, int cnt, int n, int[][] dp, int mask) {

        if (cnt > n/2) {
            return 0;
        }

        if (dp[cnt][mask] != 0) {
            return dp[cnt][mask];
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int newMask = (1 << i) | (1 << j);
                if ((mask & newMask) == 0) {
                    int gcd = cnt * findGcd(nums[i], nums[j]);
                    dp[cnt][mask] = Math.max(dp[cnt][mask], gcd + helper(nums, cnt+1, n, dp, mask | newMask));
                }
            }
        }
        return dp[cnt][mask];
    }

    private int findGcd(int num1, int num2) {
        if (num2 == 0) {
            return num1;
        }
        return findGcd(num2, num1 % num2);
    }
}
