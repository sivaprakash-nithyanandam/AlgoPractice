package trie;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 24,2024 - 7:30 PM
 */

import java.util.HashSet;

/**
 * https://leetcode.com/problems/find-the-length-of-the-longest-common-prefix/description/
 *
 * You are given two arrays with positive integers arr1 and arr2.
 *
 * A prefix of a positive integer is an integer formed by one or more of its digits, starting from its leftmost digit. For example, 123 is a prefix of the integer 12345, while 234 is not.
 *
 * A common prefix of two integers a and b is an integer c, such that c is a prefix of both a and b. For example, 5655359 and 56554 have a common prefix 565 while 1223 and 43456 do not have a common prefix.
 *
 * You need to find the length of the longest common prefix between all pairs of integers (x, y) such that x belongs to arr1 and y belongs to arr2.
 *
 * Return the length of the longest common prefix among all pairs. If no common prefix exists among them, return 0.
 *
 *
 *
 * Example 1:
 *
 * Input: arr1 = [1,10,100], arr2 = [1000]
 * Output: 3
 * Explanation: There are 3 pairs (arr1[i], arr2[j]):
 * - The longest common prefix of (1, 1000) is 1.
 * - The longest common prefix of (10, 1000) is 10.
 * - The longest common prefix of (100, 1000) is 100.
 * The longest common prefix is 100 with a length of 3.
 * Example 2:
 *
 * Input: arr1 = [1,2,3], arr2 = [4,4,4]
 * Output: 0
 * Explanation: There exists no common prefix for any pair (arr1[i], arr2[j]), hence we return 0.
 * Note that common prefixes between elements of the same array do not count.
 *
 *
 * Constraints:
 *
 * 1 <= arr1.length, arr2.length <= 5 * 104
 * 1 <= arr1[i], arr2[i] <= 108
 */
public class FindtheLengthoftheLongestCommonPrefix {

    public static void main(String[] args) {
        FindtheLengthoftheLongestCommonPrefix o = new FindtheLengthoftheLongestCommonPrefix();
        System.out.println(o.longestCommonPrefix(new int[]{1,10,100}, new int[]{1000}));
    }

    private class Trie {
        Trie[] children = new Trie[10];
    }
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        Trie root = new Trie();
        for (int n : arr1) {
            int multi = multi(n);
            Trie parent = root;
            while(multi > 0) {
                int d = (n/multi) % 10;
                if (parent.children[d] == null) {
                    parent.children[d] = new Trie();
                }
                parent = parent.children[d];
                multi /= 10;
            }
        }

        int maxLen = 0;
        for (int n : arr2) {
            int multi = multi(n);
            int len = 0;
            Trie parent = root;
            while (multi > 0) {
                int d = (n/multi) % 10;
                parent = parent.children[d];
                if (parent == null) {
                    break;
                }
                len++;
                maxLen = Math.max(maxLen, len);
                multi /= 10;
            }
        }
        return maxLen;
    }

    private int multi(int n) {
        int mult = 1;
        while (mult * 10 <= n) {
            mult = mult * 10;
        }
        return mult;
    }

    public int longestCommonPrefix_My_Trie(int[] arr1, int[] arr2) {
        Trie_own trie = new Trie_own();

        for (int num : arr1) {
            trie.add(String.valueOf(num));
        }

        int res = 0;
        for (int num : arr2) {
            res = Math.max(res, trie.find(String.valueOf(num)));
        }
        return res;
    }

    class Trie_own {
        Trie_own[] childs;

        public Trie_own() {
            childs = new Trie_own[10];
        }

        private void add(String num) {
            Trie_own curr = this;
            for (char c : num.toCharArray()) {
                if (curr.childs[c - '0'] == null) {
                    curr.childs[c - '0'] = new Trie_own();
                }
                curr = curr.childs[c - '0'];
            }
        }

        private int find(String num) {
            Trie_own curr = this;
            int ret = 0;

            for (char c : num.toCharArray()) {
                if (curr.childs[c - '0'] == null) {
                    return ret;
                }
                ret++;
                curr = curr.childs[c - '0'];
            }
            return ret;
        }
    }

    public int longestCommonPrefix_hashset(int[] arr1, int[] arr2) {

        HashSet<Integer> set = new HashSet<>();

        for (int num : arr1) {
            while (num > 0) {
                set.add(num);
                num /= 10;
            }
        }

        int res = 0;
        for (int num : arr2) {
            while (num > 0 && !set.contains(num)) {
                num /= 10;
            }
            if (num > 0) {
                res = Math.max(res, (int)Math.log10(num)+1);
            }
        }
        return res;
    }

}
