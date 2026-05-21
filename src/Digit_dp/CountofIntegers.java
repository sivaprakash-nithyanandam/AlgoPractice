package Digit_dp;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 22,2024 - 10:42 AM
 */

/**
 * https://leetcode.com/problems/count-of-integers/description/
 *
 * You are given two numeric strings num1 and num2 and two integers max_sum and min_sum. We denote an integer x to be good if:
 *
 * num1 <= x <= num2
 * min_sum <= digit_sum(x) <= max_sum.
 * Return the number of good integers. Since the answer may be large, return it modulo 109 + 7.
 *
 * Note that digit_sum(x) denotes the sum of the digits of x.
 *
 *
 *
 * Example 1:
 *
 * Input: num1 = "1", num2 = "12", min_sum = 1, max_sum = 8
 * Output: 11
 * Explanation: There are 11 integers whose sum of digits lies between 1 and 8 are 1,2,3,4,5,6,7,8,10,11, and 12. Thus, we return 11.
 * Example 2:
 *
 * Input: num1 = "1", num2 = "5", min_sum = 1, max_sum = 5
 * Output: 5
 * Explanation: The 5 integers whose sum of digits lies between 1 and 5 are 1,2,3,4, and 5. Thus, we return 5.
 *
 *
 * Constraints:
 *
 * 1 <= num1 <= num2 <= 1022
 * 1 <= min_sum <= max_sum <= 400
 */
public class CountofIntegers {

    public static void main(String[] args) {
        CountofIntegers o = new CountofIntegers();
        System.out.println(o.count("1", "12", 1, 8));
    }

    int MOD = (int)1e9+7;

    public int count(String num1, String num2, int min, int max) {

        Integer[][][] mem = new Integer[23][max+1][2];
        int max1 = findCount(num1, 0, min, max, 0, true, mem);

        mem = new Integer[23][max+1][2];
        int max2 = findCount(num2, 0, min, max, 0, true, mem);

        int minSum = 0;
        for (char c : num1.toCharArray()) {
            minSum += c - '0';
        }

        if (minSum >= min && minSum <= max) {
            return (max2 - max1 + 1 + MOD) % MOD;
        } else {
            return (max2 - max1 + MOD) % MOD;
        }
    }

    private int findCount(String num, int i, int min, int max, int sumSoFar, boolean bound, Integer[][][] mem) {
        if (sumSoFar > max) {
            return 0;
        }

        if (i == num.length()) {
            if (sumSoFar >= min && sumSoFar <= max) {
                return 1;
            } else {
                return 0;
            }
        }

        if (mem[i][sumSoFar][bound?1:0] != null) {
            return mem[i][sumSoFar][bound?1:0];
        }

        int maxRange;
        int ans = 0;

        if (bound) {
            maxRange = num.charAt(i) - '0';
        } else {
            maxRange = 9;
        }

        for (int j = 0; j <= maxRange; j++) {
            int digitsSum = (sumSoFar + j) % MOD;
            ans = (ans + findCount(num, i+1, min, max, digitsSum, bound && num.charAt(i)-'0' == j, mem)) % MOD;
        }

        return mem[i][sumSoFar][bound?1:0] = ans;
    }
}
