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
public class PeaksinArray_segment_tree {

    public static void main(String[] args) {
        PeaksinArray_segment_tree o = new PeaksinArray_segment_tree();
        System.out.println(o.countOfPeaks(new int[]{4,1,4,2,1,5}, new int[][]{{2,2,4},{1,0,2},{1,0,4}}));
        System.out.println(o.countOfPeaks(new int[]{6,10,8}, new int[][]{{1,0,2},{2,2,8},{2,1,4},{2,0,1}}));
        System.out.println(o.countOfPeaks(new int[]{3,1,4,2,5}, new int[][]{{2,3,4},{1,0,4}}));
    }

    public List<Integer> countOfPeaks(int[] nums, int[][] queries) {

        int n = nums.length;
        int segmentTreeSize = nextPowerOf2(n);
        int[] segments = new int[segmentTreeSize * 2 - 1];

        for (int i = 1; i < n-1; i++) {
            if (isPeak(i, nums)) {
                updateSegment(i, 1, segments, 0, n-1, 0);
            }
        }

        List<Integer> ans = new ArrayList<>();

        for (int[] q : queries) {
            if (q[0] == 2) {

                int i = q[1];
                nums[i] = q[2];

                for (int j = i-1; j <= i+1; j++) {
                    if (isPeak(j, nums)) {
                        updateSegment(j, 1,segments, 0, n-1, 0);
                    } else {
                        updateSegment(j, 0, segments, 0, n-1, 0);
                    }
                }
            } else {
                ans.add(querySegments(q[1]+1, q[2]-1, 0, n-1, segments, 0));
            }
        }
        return ans;
    }

    private int querySegments(int qLeft, int qRight, int left, int right, int[] segments, int pos) {

        if (qRight < left || qLeft > right) {
            return 0;
        } else if (left >= qLeft && right <= qRight) {
            return segments[pos];
        }

        int mid = left + (right - left)/2;
        int l = querySegments(qLeft, qRight, left, mid, segments, (2 * pos) + 1);
        int r = querySegments(qLeft, qRight, mid+1, right, segments, (2 * pos) + 2);
        return l + r;
    }

    private void updateSegment(int i, int val, int[] segments, int left, int right, int pos) {
        if (i < left || i > right || pos > segments.length) {
            return;
        }
        if (left == right) {
            segments[pos] = val;
            return;
        }

        int mid = left + (right - left)/2;
        updateSegment(i, val, segments, left, mid, (pos * 2) + 1);
        updateSegment(i, val, segments, mid+1, right, (pos * 2) + 2);
        segments[pos] = segments[pos * 2 + 1] + segments[pos * 2 + 2];
    }

    private boolean isPeak(int i, int[] nums) {
        return i > 0 && i < nums.length - 1 && nums[i-1] < nums[i] && nums[i+1] < nums[i];
    }

    private int nextPowerOf2(int n) {
         if (n == 0) {
             return 0;
         } else {
             while ((n & (n-1)) != 0) {
                 n = n & (n-1);
             }
             return n << 1;
         }
//        return n << 4;
    }
}
