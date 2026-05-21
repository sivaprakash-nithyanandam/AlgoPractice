package range_probs_SegmentTree_Fenwick_or_BinaryIndexedTree;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jun 17,2024 - 9:46 AM
 */

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/peaks-in-array/description/
 *
 * A peak in an array arr is an element that is greater than its previous and next element in arr.
 *
 * You are given an integer array nums and a 2D integer array queries.
 *
 * You have to process queries of two types:
 *
 * queries[i] = [1, li, ri], determine the count of peak elements in the
 * subarray
 *  nums[li..ri].
 * queries[i] = [2, indexi, vali], change nums[indexi] to vali.
 * Return an array answer containing the results of the queries of the first type in order.
 *
 * Notes:
 *
 * The first and the last element of an array or a subarray cannot be a peak.
 *
 *
 * Example 1:
 *
 * Input: nums = [3,1,4,2,5], queries = [[2,3,4],[1,0,4]]
 *
 * Output: [0]
 *
 * Explanation:
 *
 * First query: We change nums[3] to 4 and nums becomes [3,1,4,4,5].
 *
 * Second query: The number of peaks in the [3,1,4,4,5] is 0.
 *
 * Example 2:
 *
 * Input: nums = [4,1,4,2,1,5], queries = [[2,2,4],[1,0,2],[1,0,4]]
 *
 * Output: [0,1]
 *
 * Explanation:
 *
 * First query: nums[2] should become 4, but it is already set to 4.
 *
 * Second query: The number of peaks in the [4,1,4] is 0.
 *
 * Third query: The second 4 is a peak in the [4,1,4,2,1].
 *
 *
 *
 * Constraints:
 *
 * 3 <= nums.length <= 105
 * 1 <= nums[i] <= 105
 * 1 <= queries.length <= 105
 * queries[i][0] == 1 or queries[i][0] == 2
 * For all i that:
 * queries[i][0] == 1: 0 <= queries[i][1] <= queries[i][2] <= nums.length - 1
 * queries[i][0] == 2: 0 <= queries[i][1] <= nums.length - 1, 1 <= queries[i][2] <= 105
 */

/**
 * Some reference: https://github.com/mission-peace/interview/blob/master/src/com/interview/tree/SegmentTreeMinimumRangeQuery.java
 */
public class PeaksinArray {

    public static void main(String[] args) {
        PeaksinArray o = new PeaksinArray();
        System.out.println(o.countOfPeaks(new int[]{9,7,5,8,9}, new int[][]{{2,0,2},{1,0,3},{1,3,3},{2,3,5}}));
        System.out.println(o.countOfPeaks(new int[]{8,5,9,3,5}, new int[][]{{1,2,4},{1,0,1},{2,2,4}}));
        System.out.println(o.countOfPeaks(new int[]{4,10,8,6}, new int[][]{{1,0,3},{1,2,3},{1,2,3}}));
        System.out.println(o.countOfPeaks(new int[]{6,10,8}, new int[][]{{1,0,2},{2,2,8},{2,1,4},{2,0,1}}));
        System.out.println(o.countOfPeaks(new int[]{3,6,9}, new int[][]{{1,1,1},{1,2,2},{2,2,3}}));
        System.out.println(o.countOfPeaks(new int[]{4,1,4,2,1,5}, new int[][]{{2,2,4},{1,0,2},{1,0,4}}));
        System.out.println(o.countOfPeaks(new int[]{3,1,4,2,5}, new int[][]{{2,3,4},{1,0,4}}));
    }

    public List<Integer> countOfPeaks(int[] nums, int[][] queries) {

        int n = nums.length;
        int[] bits = new int[n+1];
        boolean[] isPeak = new boolean[n];

        for (int i = 1; i < n-1; i++) {
            if (isPeak(i, nums)) {
                updateBit(i+1, 1, bits);
                isPeak[i] = true;
            }
        }

        List<Integer> res = new ArrayList<>();

        for (int[] query : queries) {

            if (query[0] == 1) {
                res.add(queryBit(query[1]+1, query[2]-1, bits));
            } else {

                int idx = query[1];
                nums[idx] = query[2];

                for (int i = idx-1; i <= idx+1; i++) {
                    if (i < 0 || i == nums.length) {
                        continue;
                    }
                    boolean isPeakNow = isPeak(i, nums);
                    boolean valChange = isPeak[i] != isPeakNow;

                    if (valChange) {
                        updateBit(i+1, isPeakNow ? 1 : -1, bits);
                    }
                    isPeak[i] = isPeakNow;
                }
            }
        }
        return res;
    }

    private int queryBit(int i, int j, int[] bits) {
        if (i > j) {
            return 0;
        } else {
            int a = queryBit(j+1, bits);
            int b = queryBit(i, bits);
            return a - b;
        }
    }

    private int queryBit(int idx, int[] bits) {

        int sum = 0;
        for (int i = idx; i > 0; i -= lsb(i)) {
            sum += bits[i];
        }
        return sum;
    }

    private void updateBit(int idx, int val, int[] bits) {
        for (int i = idx; i < bits.length; i += lsb(i)) {
            bits[i] += val;
        }
    }

    private int lsb(int i) {
        return i & (-i);
    }

    private boolean isPeak(int i, int[] nums) {
        return i > 0 && i < nums.length - 1 && nums[i-1] < nums[i] && nums[i+1] < nums[i];
    }
}
