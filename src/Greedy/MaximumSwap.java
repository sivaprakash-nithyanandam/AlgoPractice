package Greedy;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 02,2024 - 1:29 PM
 */

/**
 * https://leetcode.com/problems/maximum-swap/description/
 *
 * You are given an integer num. You can swap two digits at most once to get the maximum valued number.
 *
 * Return the maximum valued number you can get.
 *
 *
 *
 * Example 1:
 *
 * Input: num = 2736 Output: 7236 Explanation: Swap the number 2 and the number 7. Example 2:
 *
 * Input: num = 9973 Output: 9973 Explanation: No swap.
 *
 *
 * Constraints:
 *
 * 0 <= num <= 108
 */
public class MaximumSwap {

    public static void main(String[] args) {
        MaximumSwap o = new MaximumSwap();
        System.out.println();
    }

    public int maximumSwap(int num) {

        String str = String.valueOf(num);
        int n = str.length();

        char[] chars = str.toCharArray();
        int[] suffixMax = new int[n];
        suffixMax[n - 1] = n - 1;

        for (int i = n - 2; i >= 0; i--) {
            if (chars[suffixMax[i + 1]] >= chars[i]) {
                suffixMax[i] = suffixMax[i + 1];
            } else {
                suffixMax[i] = i;
            }

        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (chars[i] < chars[suffixMax[j]]) {
                    char t = chars[i];
                    chars[i] = chars[suffixMax[j]];
                    chars[suffixMax[j]] = t;
                    return Integer.parseInt(String.valueOf(chars));
                }
            }
        }
        return Integer.parseInt(String.valueOf(chars));
    }

    public int maximumSwap_v1(int num) {
        char[] A = Integer.toString(num).toCharArray();
        int[] last = new int[10];
        for (int i = 0; i < A.length; i++) {
            last[A[i] - '0'] = i;
        }

        for (int i = 0; i < A.length; i++) {
            for (int d = 9; d > A[i] - '0'; d--) {
                if (last[d] > i) {
                    char tmp = A[i];
                    A[i] = A[last[d]];
                    A[last[d]] = tmp;
                    return Integer.valueOf(new String(A));
                }
            }
        }
        return num;
    }

}
