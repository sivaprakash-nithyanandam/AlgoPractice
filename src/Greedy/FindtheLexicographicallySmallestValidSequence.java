package Greedy;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 28,2024 - 2:11 PM
 */

import java.util.Arrays;

/**
 * https://leetcode.com/problems/find-the-lexicographically-smallest-valid-sequence/description/
 *
 * You are given two strings word1 and word2.
 *
 * A string x is called almost equal to y if you can change at most one character in x to make it identical to y.
 *
 * A sequence of indices seq is called valid if:
 *
 * The indices are sorted in ascending order.
 * Concatenating the characters at these indices in word1 in the same order results in a string that is almost equal to word2.
 * Return an array of size word2.length representing the
 * lexicographically smallest
 *  valid sequence of indices. If no such sequence of indices exists, return an empty array.
 *
 * Note that the answer must represent the lexicographically smallest array, not the corresponding string formed by those indices.
 *
 *
 *
 * Example 1:
 *
 * Input: word1 = "vbcca", word2 = "abc"
 *
 * Output: [0,1,2]
 *
 * Explanation:
 *
 * The lexicographically smallest valid sequence of indices is [0, 1, 2]:
 *
 * Change word1[0] to 'a'.
 * word1[1] is already 'b'.
 * word1[2] is already 'c'.
 * Example 2:
 *
 * Input: word1 = "bacdc", word2 = "abc"
 *
 * Output: [1,2,4]
 *
 * Explanation:
 *
 * The lexicographically smallest valid sequence of indices is [1, 2, 4]:
 *
 * word1[1] is already 'a'.
 * Change word1[2] to 'b'.
 * word1[4] is already 'c'.
 * Example 3:
 *
 * Input: word1 = "aaaaaa", word2 = "aaabc"
 *
 * Output: []
 *
 * Explanation:
 *
 * There is no valid sequence of indices.
 *
 * Example 4:
 *
 * Input: word1 = "abc", word2 = "ab"
 *
 * Output: [0,1]
 *
 *
 *
 * Constraints:
 *
 * 1 <= word2.length < word1.length <= 3 * 105
 * word1 and word2 consist only of lowercase English letters.
 */
public class FindtheLexicographicallySmallestValidSequence {

    public static void main(String[] args) {
        FindtheLexicographicallySmallestValidSequence o = new FindtheLexicographicallySmallestValidSequence();
        System.out.println(Arrays.toString(o.validSequence_faster("ccbccccbcc", "b")));
        System.out.println(Arrays.toString(o.validSequence_Own("ccbccccbcc", "b")));
        System.out.println(Arrays.toString(o.validSequence_faster("bacdc", "abc")));
        System.out.println(Arrays.toString(o.validSequence_faster("vbcca", "abc")));
        System.out.println(Arrays.toString(o.validSequence("bacdc", "abc")));
    }

    public int[] validSequence(String word1, String word2) {
        int i = 0, j = 0;
        int[] res = new int[word2.length()];
        while (i < word1.length() && j < word2.length() && word1.charAt(i) == word2.charAt(j)) {
            res[j++] = i++;
        }
        while (i < word1.length() && j < word2.length()) {
            if (find(word1, word2, i + 1, j + 1, res)) {
                res[j] = i;
                return res;
            }
            while (i < word1.length() && j < word2.length()) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    while (i < word1.length() && j < word2.length() && word1.charAt(i) == word2.charAt(j)) {
                        res[j++] = i++;
                    }
                    break;
                } else {
                    i++;
                }
            }
        }
        if (j < word2.length()) {
            return new int[]{};
        }
        return res;
    }

    static boolean find(String w1, String w2, int i, int j, int[] res) {
        while (i < w1.length() && j < w2.length()) {
            if (w1.charAt(i) == w2.charAt(j)) {
                res[j++] = i++;
            } else {
                i++;
            }
        }
        return j == w2.length();
    }

    public int[] validSequence_TLE(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();
        int[] res = new int[n2];

        for (int i = 0; i < n1; i++) {
            if (helper(word1, word2, i, 0, n1, n2, 1, res)) {
                return res;
            }
        }
        return new int[0];
    }

    private boolean helper(String word1, String word2, int i, int j, int n1, int n2, int skips, int[] res) {

        if (j == n2) {
            return true;
        } else if (i == n1) {
            return false;
        }

        if (word1.charAt(i) == word2.charAt(j) && helper(word1, word2, i+1, j+1, n1, n2, skips, res)) {
            res[j] = i;
        } else if (skips > 0 && helper(word1, word2, i+1, j+1, n1, n2, skips-1, res)) {
            res[j] = i;
        } else if (helper(word1, word2, i+1, j, n1, n2, skips, res)) {

        }  else {
            return false;
        }
        return true;
    }

    public int[] validSequence_faster(String word1, String word2) {

        int [] minIndex = new int [word2.length() + 1];
        Arrays.fill(minIndex, -1);
        minIndex[minIndex.length - 1] = word2.length();

        int cIdx = word2.length() - 1;

        for (int i = word1.length() - 1; i >= 0; --i) {

            if (cIdx >= 0 && word2.charAt(cIdx) == word1.charAt(i)) {
                minIndex[cIdx] = i;
                cIdx--;
            }
        }

        int [] result = new int [word2.length()];

        int allowed = 1;
        int idx = 0;

        for (int i = 0; i < word1.length(); ++i) {

            if (idx == word2.length())
                break;

            if (idx < word2.length() && word2.charAt(idx) == word1.charAt(i)) {
                result[idx++] = i;
            }else if (allowed > 0 && minIndex[idx + 1] >= i + 1) {
                allowed--;
                result[idx++] = i;
            }
        }

        return idx == word2.length() ? result : new int [] {};
    }

    public int[] validSequence_Own(String word1, String word2) {

        int n1 = word1.length();
        int n2 = word2.length();

        int[] res = new int[n2];

        int[] suffixMatch = new int[n2+1];

        int j = n2-1;
        for (int i = n1-1; i >= 0 && j >= 0; i--) {
            if (word1.charAt(i) == word2.charAt(j)) {
                suffixMatch[j] = i;
                j--;
            }
        }

        j = 0;
        int allowed = 1;

        for (int i = 0; i < n1; i++) {
            if (j == n2) {
                return res;
            }

            if (word1.charAt(i) == word2.charAt(j)) {
                res[j] = i;
                j++;
            } else if (allowed > 0 && suffixMatch[j+1] >= i+1) {
                allowed--;
                res[j] = i;
                j++;
            }
        }

        return j == n2 ? res : new int[0];
    }
}
