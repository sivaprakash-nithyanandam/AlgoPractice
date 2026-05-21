package strings;
/**
 * @time 5/11/24-1:08 PM
 * @author sivaprakashnithyanandam
 */

import java.util.Arrays;

/**
 * https://leetcode.com/problems/minimum-substring-partition-of-equal-character-frequency/description/
 * <p>
 * Given a string s, you need to partition it into one or more balanced
 * substrings
 * . For example, if s == "ababcc" then ("abab", "c", "c"), ("ab", "abc", "c"), and ("ababcc") are all valid partitions, but ("a", "bab", "cc"), ("aba", "bc", "c"), and ("ab", "abcc") are not. The unbalanced substrings are bolded.
 * <p>
 * Return the minimum number of substrings that you can partition s into.
 * <p>
 * Note: A balanced string is a string where each character in the string occurs the same number of times.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: s = "fabccddg"
 * <p>
 * Output: 3
 * <p>
 * Explanation:
 * <p>
 * We can partition the string s into 3 substrings in one of the following ways: ("fab, "ccdd", "g"), or ("fabc", "cd", "dg").
 * <p>
 * Example 2:
 * <p>
 * Input: s = "abababaccddb"
 * <p>
 * Output: 2
 * <p>
 * Explanation:
 * <p>
 * We can partition the string s into 2 substrings like so: ("abab", "abaccddb").
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= s.length <= 1000
 * s consists only of English lowercase letters.
 */
public class MinimumSubstringPartitionofEqualCharacterFrequency {

    public static void main(String[] args) {
        MinimumSubstringPartitionofEqualCharacterFrequency o = new MinimumSubstringPartitionofEqualCharacterFrequency();
        System.out.println(o.minimumSubstringsInPartition("fabccddg"));
    }

    /**
     * Different reference: https://www.youtube.com/watch?v=Wr7czdXQXN4
     *
     * Its a sliding window technique, where i to j of substrings of given string will be counted of english letters
     * and check if its balanced, if so mark balanced[i][j] true.
     * Again iterate given string of i to j in the balanced array and if its balanced, count the sum between i to j and
     * mark the result in j+1
     * So the final numbers of minumum substrings will be at n+1
     * @param s
     * @return
     */
    public int minimumSubstringsInPartition(String s) {
        char[] cs = s.toCharArray();
        boolean[][] balanced = new boolean[cs.length][cs.length];
        for (int l = 0; l < cs.length; l++) {
            int[] cnt = new int[26];
            for (int r = l; r < cs.length; r++) {
                cnt[cs[r] - 'a']++;
                balanced[l][r] = isBalanced(cnt);
            }
        }
        int[] min = new int[cs.length + 1];
        Arrays.fill(min, Integer.MAX_VALUE);
        min[0] = 0;
        for (int i = 0; i < cs.length; i++) {
            for (int next = i; next < cs.length; next++) {
                if (!balanced[i][next]) {
                    continue;
                }
                int amt = min[i] + 1;
                if (min[next + 1] > amt) {
                    min[next + 1] = amt;
                }
            }
        }
        return min[cs.length];
    }

    private boolean isBalanced(int[] ar) {
        int amt = 0;
        for (int elem : ar) {
            if (elem > amt){
                amt = elem;
            }
        }
        for (int elem : ar){
            if (elem != amt && elem != 0){
                return false;
            }
        }
        return true;
    }

    /**
     * This is more straight forward method
     * From the last, do the recursive execution for each element and if its
     * @param s
     * @return
     */
    public int minimumSubstringsInPartition_n2(String s) {
        int arr[] = new int[1002];
        Arrays.fill(arr, -1);
        return task(s.length()-1,s,arr);
    }
    private int task(int ind, String s, int arr[])
    {
        if(ind < 0)
        {
            return 0;
        }
        if(arr[ind] != -1)
        {
            return arr[ind];
        }
        int a[]=new int[26];
        int ans=10000;
        for(int j = ind; j >= 0; j--)
        {
            a[s.charAt(j)-'a']+=1;
            int maxi=-1,mini=10000;
            for(int k=0;k<26;k++)
            {
                if(a[k]>0)
                {
                    mini=Math.min(mini,a[k]);
                    maxi=Math.max(maxi,a[k]);
                }
            }
            if(mini==maxi)
            {
                ans=Math.min(ans,1+task(j-1,s,arr));
            }
        }
        return arr[ind]=ans;
    }
}
