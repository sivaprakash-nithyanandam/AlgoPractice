package dfs;

/**
 * Author - Sivaprakash Nithyanandam Oct 20,2024 - 5:43 PM
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 * https://leetcode.com/problems/check-if-dfs-strings-are-palindromes/description/
 *
 * You are given a tree rooted at node 0, consisting of n nodes numbered from 0 to n - 1. The tree is represented by an array parent of size n, where parent[i] is the parent of node i. Since node 0 is the root, parent[0] == -1.
 *
 * You are also given a string s of length n, where s[i] is the character assigned to node i.
 *
 * Consider an empty string dfsStr, and define a recursive function dfs(int x) that takes a node x as a parameter and performs the following steps in order:
 *
 * Iterate over each child y of x in increasing order of their numbers, and call dfs(y).
 * Add the character s[x] to the end of the string dfsStr.
 * Note that dfsStr is shared across all recursive calls of dfs.
 *
 * You need to find a boolean array answer of size n, where for each index i from 0 to n - 1, you do the following:
 *
 * Empty the string dfsStr and call dfs(i).
 * If the resulting string dfsStr is a palindrome, then set answer[i] to true. Otherwise, set answer[i] to false.
 * Return the array answer.
 *
 * A palindrome is a string that reads the same forward and backward.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: parent = [-1,0,0,1,1,2], s = "aababa"
 *
 * Output: [true,true,false,true,true,true]
 *
 * Explanation:
 *
 * Calling dfs(0) results in the string dfsStr = "abaaba", which is a palindrome.
 * Calling dfs(1) results in the string dfsStr = "aba", which is a palindrome.
 * Calling dfs(2) results in the string dfsStr = "ab", which is not a palindrome.
 * Calling dfs(3) results in the string dfsStr = "a", which is a palindrome.
 * Calling dfs(4) results in the string dfsStr = "b", which is a palindrome.
 * Calling dfs(5) results in the string dfsStr = "a", which is a palindrome.
 * Example 2:
 *
 *
 * Input: parent = [-1,0,0,0,0], s = "aabcb"
 *
 * Output: [true,true,true,true,true]
 *
 * Explanation:
 *
 * Every call on dfs(x) results in a palindrome string.
 *
 *
 *
 * Constraints:
 *
 * n == parent.length == s.length
 * 1 <= n <= 105
 * 0 <= parent[i] <= n - 1 for all i >= 1.
 * parent[0] == -1
 * parent represents a valid tree.
 * s consists only of lowercase English letters.
 */
public class CheckifDFSStringsArePalindromes {

    public static void main(String[] args) {
        CheckifDFSStringsArePalindromes o = new CheckifDFSStringsArePalindromes();
        System.out.println(o.findAnswer(new int[]{-1,0,0,1,1,2}, "aababa"));
    }

    public long base = 911;
    public long mod = 1000000007L;
    public boolean[] findAnswer(int[] parent, String s) {
        int n = parent.length;

        ArrayList<ArrayList<Integer>> children = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            children.add(new ArrayList<>());
        }
        for(int i = 0; i < n; i++) {
            if(parent[i] != -1){
                children.get(parent[i]).add(i);
            }
        }

        for(int i = 0; i < n; i++) {
            Collections.sort(children.get(i));
        }

        int maxLen = n + 1;
        long[] pow = new long[maxLen + 1];
        pow[0] = 1;
        for(int i = 1; i <= maxLen; i++) {
            pow[i] = (pow[i - 1] * base) % mod;
        }

        long[] forwardHash = new long[n];
        long[] reverseHash = new long[n];
        int[] len = new int[n];

        qwe(0, children, s, pow, forwardHash, reverseHash, len);

        boolean[] answer = new boolean[n];
        for(int i = 0; i < n; i++) {
            answer[i] = (forwardHash[i] == reverseHash[i]);
        }

        return answer;
    }

    public void qwe(int x, ArrayList<ArrayList<Integer>> children, String s, long[] pow, long[] forwardHash, long[] reverseHash, int[] len) {
        len[x] = 1;
        forwardHash[x] = 0;

        for(int y : children.get(x)) {
            qwe(y, children, s, pow, forwardHash, reverseHash, len);
            forwardHash[x] = (forwardHash[x] * pow[len[y]] + forwardHash[y]) % mod;
            len[x] += len[y];
        }

        forwardHash[x] = (forwardHash[x] * base + (s.charAt(x) - 'a' + 1)) % mod;

        reverseHash[x] = (s.charAt(x) - 'a' + 1);
        ArrayList<Integer> childs = children.get(x);
        for(int i = childs.size() - 1; i >= 0; i--){
            int y = childs.get(i);
            reverseHash[x] = (reverseHash[x] * pow[len[y]] + reverseHash[y]) % mod;
        }
    }
}
