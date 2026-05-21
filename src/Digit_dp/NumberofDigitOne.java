package Digit_dp;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 22,2024 - 6:58 PM
 */

/**
 * https://leetcode.com/problems/number-of-digit-one/description/
 *
 * Given an integer n, count the total number of digit 1 appearing in all non-negative integers less than or equal to n.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 13
 * Output: 6
 * Example 2:
 *
 * Input: n = 0
 * Output: 0
 *
 *
 * Constraints:
 *
 * 0 <= n <= 109
 */
public class NumberofDigitOne {

    public static void main(String[] args) {
        NumberofDigitOne o = new NumberofDigitOne();
        System.out.println();
    }

    //Formula: https://www.youtube.com/watch?v=Shz1Qo5PNso
    //Explanation: https://www.youtube.com/watch?v=43UYV3DgMT0
    public int countDigitOne(int n) {

        int base = 1;
        int res = 0;

        while (base <= n) {
            int curr = n / base % 10;
            int left = n / base / 10;
            int right = n % base;

            if (curr == 1) {
                res += (left * base) + (right + 1);
            } else if (curr > 1) {
                res += (left + 1) * base;
            } else {
                res += left * base;
            }

            base *= 10;
        }
        return res;
    }
}
