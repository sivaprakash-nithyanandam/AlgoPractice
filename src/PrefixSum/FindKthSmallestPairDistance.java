package PrefixSum;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Aug 18,2024 - 9:08 PM
 */

import java.util.Arrays;

/**
 * https://leetcode.com/problems/find-k-th-smallest-pair-distance/description/
 *
 * The distance of a pair of integers a and b is defined as the absolute difference between a and b.
 *
 * Given an integer array nums and an integer k, return the kth smallest distance among all the pairs nums[i] and nums[j] where 0 <= i < j < nums.length.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,3,1], k = 1
 * Output: 0
 * Explanation: Here are all the pairs:
 * (1,3) -> 2
 * (1,1) -> 0
 * (3,1) -> 2
 * Then the 1st smallest distance pair is (1,1), and its distance is 0.
 * Example 2:
 *
 * Input: nums = [1,1,1], k = 2
 * Output: 0
 * Example 3:
 *
 * Input: nums = [1,6,1], k = 3
 * Output: 5
 *
 *
 * Constraints:
 *
 * n == nums.length
 * 2 <= n <= 104
 * 0 <= nums[i] <= 106
 * 1 <= k <= n * (n - 1) / 2
 */
public class FindKthSmallestPairDistance {

    public static void main(String[] args) {
        FindKthSmallestPairDistance o = new FindKthSmallestPairDistance();
        System.out.println(o.smallestDistancePair_v1(new int[]{1,6,1}, 3));
    }

    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int arraySize = nums.length;

        // Initialize binary search range
        int low = 0;
        int high = nums[arraySize - 1] - nums[0];

        while (low < high) {
            int mid = (low + high) / 2;

            // Count pairs with distance <= mid
            int count = countPairsWithMaxDistance(nums, mid);

            // Adjust binary search bounds based on count
            if (count < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    // Count number of pairs with distance <= maxDistance using a moving window
    private int countPairsWithMaxDistance(int[] nums, int maxDistance) {
        int count = 0;
        int arraySize = nums.length;
        int left = 0;

        for (int right = 0; right < arraySize; ++right) {
            // Adjust the left pointer to maintain the window with distances <=
            // maxDistance
            while (nums[right] - nums[left] > maxDistance) {
                ++left;
            }
            // Add the number of valid pairs ending at the current right index
            count += right - left;
        }
        return count;
    }

    public int smallestDistancePair_v1(int[] nums, int k) {
        Arrays.sort(nums);
        int arraySize = nums.length;
        int maxElement = nums[arraySize - 1];

        // Number of computed prefixes
        int prefixCountSize = maxElement * 2;

        // Initialize prefix counts and value counts
        int[] prefixCount = new int[prefixCountSize];
        int[] valueCount = new int[maxElement + 1];

        // Compute prefix counts and value counts
        int prefixIndex = 0;
        for (int value = 0; value < prefixCountSize; ++value) {
            while (prefixIndex < arraySize && nums[prefixIndex] <= value) {
                ++prefixIndex;
            }
            prefixCount[value] = prefixIndex;
        }
        for (int i = 0; i < arraySize; ++i) {
            ++valueCount[nums[i]];
        }

        // Binary search to find kth smallest distance
        int left = 0;
        int right = maxElement;
        while (left < right) {
            int middle = (left + right) / 2;

            // Compute number of pairs with distance <= middle
            int count = countPairs(nums, prefixCount, valueCount, middle);

            // Adjust bounds for binary search
            if (count < k) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

    private int countPairs(int[] nums, int[] prefixCount, int[] valueCount, int maxDistance) {
        int count = 0;
        int arraySize = nums.length;
        int index = 0;
        while (index < arraySize) {
            int currentValue = nums[index];
            int valueCountForCurrent = valueCount[currentValue];
            int pairsWithLargerValues =
                    prefixCount[Math.min(
                            currentValue + maxDistance,
                            prefixCount.length - 1
                    )] -
                            prefixCount[currentValue];
            int pairsWithSameValues =
                    (valueCountForCurrent * (valueCountForCurrent - 1)) / 2;
            count +=
                    pairsWithLargerValues * valueCountForCurrent + pairsWithSameValues;
            while (index + 1 < arraySize && nums[index] == nums[index + 1]) {
                ++index;
            }
            ++index;
        }
        return count;
    }

    public int smallestDistancePair_v0(int[] nums, int k) {

        int max = Arrays.stream(nums).max().getAsInt();

        int[] bucket = new int[max+1];

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i+1; j < nums.length; j++) {
                bucket[Math.abs(nums[i]-nums[j])]++;
            }
        }

        for (int dist = 0; dist <= max; dist++) {
            k -= bucket[dist];

            if (k <= 0) {
                return dist;
            }
        }

        return -1;
    }
}
