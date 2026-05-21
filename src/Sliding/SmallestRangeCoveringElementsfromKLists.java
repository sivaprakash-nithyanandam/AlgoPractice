package Sliding;

/**
 * Author - Sivaprakash Nithyanandam Oct 13,2024 - 1:52 PM
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/description/
 *
 * You have k lists of sorted integers in non-decreasing order. Find the smallest range that includes at least one number from each of the k lists.
 *
 * We define the range [a, b] is smaller than range [c, d] if b - a < d - c or a < c if b - a == d - c.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [[4,10,15,24,26],[0,9,12,20],[5,18,22,30]]
 * Output: [20,24]
 * Explanation:
 * List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
 * List 2: [0, 9, 12, 20], 20 is in range [20,24].
 * List 3: [5, 18, 22, 30], 22 is in range [20,24].
 * Example 2:
 *
 * Input: nums = [[1,2,3],[1,2,3],[1,2,3]]
 * Output: [1,1]
 *
 *
 * Constraints:
 *
 * nums.length == k
 * 1 <= k <= 3500
 * 1 <= nums[i].length <= 50
 * -105 <= nums[i][j] <= 105
 * nums[i] is sorted in non-decreasing order.
 */
public class SmallestRangeCoveringElementsfromKLists {

    public static void main(String[] args) {
        SmallestRangeCoveringElementsfromKLists o = new SmallestRangeCoveringElementsfromKLists();

        List<List<Integer>> nums = new ArrayList<>();
        Integer[] l1 = {4,10,15,24,26};
        Integer[] l2 = {0,9,12,20};
        Integer[] l3 = {5,18,22,30};
        nums.add(Arrays.asList(l1));
        nums.add(List.of(l2));
        nums.add(List.of(l3));
        System.out.println(o.smallestRange(nums));
    }

    public int[] smallestRange(List<List<Integer>> nums) {

        int[] res = new int[2];

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        int right = Integer.MIN_VALUE;

        for (int i = 0; i < nums.size(); i++) {
            pq.add(new int[]{nums.get(i).get(0), i, 0});
            right = Math.max(right, nums.get(i).get(0));
        }

        res[0] = pq.peek()[0];
        res[1] = right;

        while (true) {
            int[] min = pq.poll();
            List<Integer> minList = nums.get(min[1]);

            if (minList.size()-1 == min[2]) {
                return res;
            }

            pq.add(new int[]{minList.get(min[2]+1), min[1], min[2]+1});
            right = Math.max(right, minList.get(min[2]+1));

            if (right - pq.peek()[0] < res[1] - res[0]) {
                res[1] = right;
                res[0] = pq.peek()[0];
            }
        }

    }

    public int[] smallestRange_v2(List<List<Integer>> nums) {
        List<int[]> merged = new ArrayList<>();

        // Merge all lists with their list index
        for (int i = 0; i < nums.size(); i++) {
            for (int num : nums.get(i)) {
                merged.add(new int[] { num, i });
            }
        }

        // Sort the merged list
        merged.sort(Comparator.comparingInt(a -> a[0]));

        // Two pointers to track the smallest range
        Map<Integer, Integer> freq = new HashMap<>();
        int left = 0, count = 0;
        int rangeStart = 0, rangeEnd = Integer.MAX_VALUE;

        for (int right = 0; right < merged.size(); right++) {
            freq.put(
                    merged.get(right)[1],
                    freq.getOrDefault(merged.get(right)[1], 0) + 1
            );
            if (freq.get(merged.get(right)[1]) == 1) count++;

            // When all lists are represented, try to shrink the window
            while (count == nums.size()) {
                int curRange = merged.get(right)[0] - merged.get(left)[0];
                if (curRange < rangeEnd - rangeStart) {
                    rangeStart = merged.get(left)[0];
                    rangeEnd = merged.get(right)[0];
                }

                freq.put(
                        merged.get(left)[1],
                        freq.get(merged.get(left)[1]) - 1
                );
                if (freq.get(merged.get(left)[1]) == 0) count--;
                left++;
            }
        }

        return new int[] { rangeStart, rangeEnd };
    }

    public int[] smallestRange_v1(List<List<Integer>> nums) {
        if (nums.size() == 1) {
            return new int[] { nums.get(0).get(0), nums.get(0).get(0) };
        }
        int k = nums.size();
        int minK = nums.get(0).get(0), minArr = 0;
        int maxK = nums.get(0).get(0), maxArr = 0;
        for (int i = 1; i < k; ++i) {
            List<Integer> list = nums.get(i);
            int val = list.get(0);
            if (val > maxK) {
                maxK = val;
                maxArr = i;
            }
            if (val < minK) {
                minK = val;
                minArr = i;
            }
        }
        int[] ret = new int[] { minK, maxK };
        int[] pos = new int[k];
        boolean done = false;
        int curListLen;
        while (!done) {
            List<Integer> curMinList = nums.get(minArr);
            pos[minArr]++;
            if (curMinList.size() == pos[minArr]) {
                done = true;
                continue;
            }
            int next = curMinList.get(pos[minArr]);
            minK = next;
            for (int i = 0; i < k; ++i) {
                curMinList = nums.get(i);
                curListLen = curMinList.size();
                for (int curIdx = pos[i]; curIdx < curListLen && curMinList.get(curIdx) <= next; curIdx++) {
                    pos[i] = curIdx;
                }
                int curVal = curMinList.get(pos[i]);
                if (curVal < minK) {
                    minK = curVal;
                    minArr = i;
                }
                if (curVal > maxK) {
                    maxK = curVal;
                    maxArr = i;
                }
            }
            if (maxK - minK < ret[1] - ret[0]) {
                ret[1] = maxK;
                ret[0] = minK;
            }
        }
        return ret;
    }
}
