package trie;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 07,2024 - 11:06 AM
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/construct-string-with-minimum-cost/description/
 *
 * You are given a string target, an array of strings words, and an integer array costs, both arrays of the same length.
 *
 * Imagine an empty string s.
 *
 * You can perform the following operation any number of times (including zero):
 *
 * Choose an index i in the range [0, words.length - 1].
 * Append words[i] to s.
 * The cost of operation is costs[i].
 * Return the minimum cost to make s equal to target. If it's not possible, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: target = "abcdef", words = ["abdef","abc","d","def","ef"], costs = [100,1,1,10,5]
 *
 * Output: 7
 *
 * Explanation:
 *
 * The minimum cost can be achieved by performing the following operations:
 *
 * Select index 1 and append "abc" to s at a cost of 1, resulting in s = "abc".
 * Select index 2 and append "d" to s at a cost of 1, resulting in s = "abcd".
 * Select index 4 and append "ef" to s at a cost of 5, resulting in s = "abcdef".
 * Example 2:
 *
 * Input: target = "aaaa", words = ["z","zz","zzz"], costs = [1,10,100]
 *
 * Output: -1
 *
 * Explanation:
 *
 * It is impossible to make s equal to target, so we return -1.
 *
 *
 *
 * Constraints:
 *
 * 1 <= target.length <= 5 * 104
 * 1 <= words.length == costs.length <= 5 * 104
 * 1 <= words[i].length <= target.length
 * The total sum of words[i].length is less than or equal to 5 * 104.
 * target and words[i] consist only of lowercase English letters.
 * 1 <= costs[i] <= 104
 */
public class ConstructStringwithMinimumCost {

    public static void main(String[] args) {
        ConstructStringwithMinimumCost o = new ConstructStringwithMinimumCost();
        System.out.println(o.minimumCost("abcdef", new String[]{"abdef","abc","d","def","ef"},
                new int[]{100,1,1,10,5}));
    }

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        List<Integer> wordIndices = new ArrayList<>();
    }

    public int minimumCost(String target, String[] words, int[] costs) {
        int n = target.length();
        TrieNode root = buildTrie(words);
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            TrieNode node = root;
            for (int j = i - 1; j >= 0 && node != null; j--) {
                char c = target.charAt(j);
                node = node.children[c - 'a'];
                if (node != null && !node.wordIndices.isEmpty()) {
                    for (int wordIndex : node.wordIndices) {
                        dp[i] = Math.min(dp[i], dp[j] + costs[wordIndex]);
                    }
                }
            }
        }

        return dp[n] == Integer.MAX_VALUE / 2 ? -1 : dp[n];
    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            TrieNode node = root;
            for (int j = words[i].length() - 1; j >= 0; j--) {
                char c = words[i].charAt(j);
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            node.wordIndices.add(i);
        }
        return root;
    }
}
