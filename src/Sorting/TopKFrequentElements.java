package Sorting;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 02,2024 - 1:24 PM
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/top-k-frequent-elements/description/
 *
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * Example 2:
 *
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * k is in the range [1, the number of unique elements in the array].
 * It is guaranteed that the answer is unique.
 *
 *
 * Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class TopKFrequentElements {

    public static void main(String[] args) {
        TopKFrequentElements o = new TopKFrequentElements();
        System.out.println(Arrays.toString(o.topKFrequent(new int[]{3,2,3,1,2,4,5,5,6,7,7,8,2,3,1,1,1,10,11,5,6,2,4,7,8,5,6}, 10)));
        System.out.println(Arrays.toString(o.topKFrequent(new int[]{1}, 1)));
        System.out.println(Arrays.toString(o.topKFrequent(new int[]{1,2,5,5,7,6,4,8,10,11}, 10)));
    }

    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }

        int[] uniques = map.keySet().stream().mapToInt(Integer::intValue).toArray();
        return quickSelect(uniques, 0, uniques.length-1, uniques.length - k, map);
    }

    private int[] quickSelect(int[] uniques, int left, int right, int k, Map<Integer, Integer> map) {

        int pivot = right;
        int pivotVal = map.get(uniques[pivot]);
        int p = left;

        for (int i = left; i < right; i++) {
            if (map.get(uniques[i]) < pivotVal) {
                int t = uniques[i];
                uniques[i] = uniques[p];
                uniques[p] = t;
                p++;
            }
        }
        int t = uniques[p];
        uniques[p] = uniques[pivot];
        uniques[pivot] = t;

        if (p > k) {
            return quickSelect(uniques, left, p-1, k, map);
        } else if (p < k) {
            return quickSelect(uniques, p+1, right, k, map);
        } else {
            return Arrays.copyOfRange(uniques, p, uniques.length);
        }
    }
}
