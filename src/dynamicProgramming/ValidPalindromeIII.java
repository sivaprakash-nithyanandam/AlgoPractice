package dynamicProgramming;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 09,2024 - 8:43 AM
 */

/**
 * Description
 * Given a string s and an integer k, return true if s is a k-palindrome.
 *
 * A string is k-palindrome if it can be transformed into a palindrome by removing at most k characters from it.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "abcdeca", k = 2
 * Output: true
 * Explanation: Remove 'b' and 'e' characters.
 * Example 2:
 *
 * Input: s = "abbababa", k = 1
 * Output: true
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s consists of only lowercase English letters.
 * 1 <= k <= s.length
 */
public class ValidPalindromeIII {

    public static void main(String[] args) {
        ValidPalindromeIII o = new ValidPalindromeIII();
        System.out.println(o.isValidPalindrome("abcdeca", 2));
    }

    /**
     * Solution 1: Dynamic Programming
     *
     * The problem requires us to remove at most
     *  characters to make the remaining string a palindrome. This can be transformed into finding the longest palindromic subsequence.
     *
     * We define
     *  as the length of the longest palindromic subsequence in the substring
     * . Initially, we have
     *  for all
     * , since each single character is a palindrome.
     *
     * If
     * , then we have
     * , since we can add both
     *  and
     *  to the longest palindromic subsequence of
     * .
     *
     * If
     * , then we have
     * , since we need to remove either
     *  or
     *  to make the remaining substring a palindrome.
     *
     * Finally, we check whether there exists
     * , where
     *  is the length of the string
     * . If so, it means that we can remove at most
     *  characters to make the remaining string a palindrome.
     * @param s
     * @param k
     * @return
     */
    public boolean isValidPalindrome(String s, int k) {
        int n = s.length();
        int[][] f = new int[n][n];
        for (int i = 0; i < n; ++i) {
            f[i][i] = 1;
        }
        for (int i = n - 2; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                if (s.charAt(i) == s.charAt(j)) {
                    f[i][j] = f[i + 1][j - 1] + 2;
                } else {
                    f[i][j] = Math.max(f[i + 1][j], f[i][j - 1]);
                }
                if (f[i][j] + k >= n) {
                    return true;
                }
            }
        }
        return false;
    }
}
