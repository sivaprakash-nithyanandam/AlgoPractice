package PrefixSum;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Aug 18,2024 - 8:50 PM
 */

import java.util.Arrays;

/**
 * https://leetcode.com/problems/count-substrings-that-satisfy-k-constraint-ii/description/
 *
 * You are given a binary string s and an integer k.
 *
 * You are also given a 2D integer array queries, where queries[i] = [li, ri].
 *
 * A binary string satisfies the k-constraint if either of the following conditions holds:
 *
 * The number of 0's in the string is at most k.
 * The number of 1's in the string is at most k.
 * Return an integer array answer, where answer[i] is the number of
 * substrings
 *  of s[li..ri] that satisfy the k-constraint.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "0001111", k = 2, queries = [[0,6]]
 *
 * Output: [26]
 *
 * Explanation:
 *
 * For the query [0, 6], all substrings of s[0..6] = "0001111" satisfy the k-constraint except for the substrings s[0..5] = "000111" and s[0..6] = "0001111".
 *
 * Example 2:
 *
 * Input: s = "010101", k = 1, queries = [[0,5],[1,4],[2,3]]
 *
 * Output: [15,9,3]
 *
 * Explanation:
 *
 * The substrings of s with a length greater than 3 do not satisfy the k-constraint.
 *
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 105
 * s[i] is either '0' or '1'.
 * 1 <= k <= s.length
 * 1 <= queries.length <= 105
 * queries[i] == [li, ri]
 * 0 <= li <= ri < s.length
 * All queries are distinct.
 */
public class CountSubstringsThatSatisfyKConstraintII {

    public static void main(String[] args) {
        CountSubstringsThatSatisfyKConstraintII o = new CountSubstringsThatSatisfyKConstraintII();
        System.out.println(Arrays.toString(o.countKConstraintSubstrings("001110001", 3, new int[][]{{0,8},{1,8}})));
        System.out.println(Arrays.toString(o.countKConstraintSubstrings("010101", 1, new int[][]{{0,5},{1,4},{2,3}})));
        System.out.println(Arrays.toString(o.countKConstraintSubstrings("0001111", 2, new int[][]{{0,6}})));
    }

    public long[] countKConstraintSubstrings(String s, int k, int[][] queries) {
        int lastValidInd[] = new int[s.length()]; // lastValidInd[i] = min valid index for which substring ending at i satisfy the constraints
        int zeros = 0, ones = 0, i = 0, j = 0;

        while (j < s.length()) {
            if(s.charAt(j) == '0') {
                zeros++;
            }
            else {
                ones++;
            }
            while(zeros > k && ones > k){
                if(s.charAt(i++) == '0')  {
                    zeros--;
                }
                else {
                    ones--;
                }
            }
            lastValidInd[j++] = i;
        }

        int prefixSum[] = new int[s.length()];
        prefixSum[0] = 1;
        for(i = 1; i < s.length(); i++) {
            prefixSum[i] += prefixSum[i - 1] + (i - lastValidInd[i] + 1);
        }

        long res[] = new long[queries.length];
        int ind = 0;
        for(int q[]: queries){
            int validInd = getMaxValidInd(lastValidInd, q[0], q[1], q[0]);
            long currRes = ((validInd - q[0] + 1l) * (validInd - q[0] + 2l) / 2l) + prefixSum[q[1]] - prefixSum[validInd];
            res[ind++] = currRes;
        }
        return res;
    }
    private int getMaxValidInd(int lastValidInd[], int l, int h, int key){
        while(l <= h){
            int m = (l + h) / 2;
            if(lastValidInd[m] <= key) {
                l = m + 1;
            }
            else {
                h = m - 1;
            }
        }
        return l - 1;
    }

    public long[] countKConstraintSubstrings_non_tle_prefix_sum(String s, int k, int[][] queries) {
        int lastValidInd[] = new int[s.length()]; // lastValidInd[i] = min valid index for which substring ending at i satisfy the constraints
        int zeros = 0, ones = 0, i = 0, j = 0;

        while(j < s.length()) {
            if(s.charAt(j) == '0')  {
                zeros++;
            }
            else {
                ones++;
            }
            while(zeros > k && ones > k) {
                if(s.charAt(i++) == '0') {
                    zeros--;
                }
                else {
                    ones--;
                }
            }
            lastValidInd[j++] = i;
        }
        //Non TLE code
        int prefixSum[] = new int[s.length()];
        prefixSum[0] = 1;
        for(i = 1; i < s.length(); i++) {
            prefixSum[i] += prefixSum[i - 1] + (i - lastValidInd[i] + 1);
        }

        long res[] = new long[queries.length];
        int ind = 0;
        for(int q[]: queries){
            long currRes = 0l;
            for(j = q[0]; j <= q[1]; j++) {
                if(lastValidInd[j] <= q[0]) {
                    currRes += (j - q[0] + 1);  // if newRange start lies in between index j (i.e., substring ending at j) and its min valid index then all the subarrays between will satisfy the constraints
                }
                else {//Non TLE code
                    currRes += (prefixSum[q[1]] - prefixSum[j - 1]);
                    break;
                }
            }
            res[ind++] = currRes;
        }
        return res;
    }

    public long[] countKConstraintSubstrings_TLE(String s, int k, int[][] queries) {
        int lastValidInd[] = new int[s.length()]; // lastValidInd[i] = min valid index for which substring ending at i satisfy the constraints
        int zeros = 0, ones = 0, i = 0, j = 0;
        while(j < s.length()){
            if(s.charAt(j) == '0')  {
                zeros++;
            }
            else {
                ones++;
            }
            while(zeros > k && ones > k){
                if(s.charAt(i++) == '0')  {
                    zeros--;
                }
                else {
                    ones--;
                }
            }
            lastValidInd[j++] = i;
        }

        long res[] = new long[queries.length];
        int ind = 0;
        for(int q[]: queries){
            long currRes = 0l;
            for(j = q[0]; j <= q[1]; j++){
                if(lastValidInd[j] <= q[0])    {
                    currRes += (j - q[0] + 1);  // if newRange start lies in between index j (i.e., substring ending at j) and its min valid index then all the subarrays between will satisfy the constraints
                }
                else {
                    currRes += (j - lastValidInd[j] + 1); // if newRange start is prior to index j's min vaild index then we know we can only stastify constranits till min valid index for index j
                }
            }
            res[ind++] = currRes;
        }
        return res;
    }

    public long[] countKConstraintSubstrings_v1(String s, int k, int[][] queries) {
        int n = s.length();
        int i = 0;
        int j = 0;
        int ones = 0;
        int zeros = 0;
        int[] window = new int[n];
        int[] prefixes = new int[n];

        while (j < n) {
            if (s.charAt(j) == '1') {
                ones++;
            } else {
                zeros++;
            }
            while (ones > k && zeros > k) {
                if (s.charAt(i) == '1') {
                    ones--;
                } else {
                    zeros--;
                }
                i++;
            }
            window[j] = i;
            prefixes[j] = j - i + 1;
            j++;
        }

        for (int m = 1; m < n; m++) {
            prefixes[m] += prefixes[m-1];
        }

        long[] res = new long[queries.length];
        for (int x = 0; x < queries.length; x++) {
            int l = queries[x][0];
            int r = queries[x][1];
            int temp = r + 1;

            while (l <= r) {
                int mid = (l+r)/2;

                if (window[mid] > queries[x][0]) {
                    l = mid+1;
                } else {
                    r = mid - 1;
                    temp = mid;
                }
            }

            long sum = prefixes[queries[x][1]];
            if (temp - 1 >= 0) {
                sum -= prefixes[temp-1];
            }

            temp -= queries[x][0] ;

            sum += (long)(temp * (temp+1) / 2);
            res[x] = sum;
        }
        return res;
    }
}
