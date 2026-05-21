package Sliding;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 12,2023 - 9:23 AM
 */

/**
 * https://leetcode.com/problems/max-consecutive-ones-iii/description/
 *
 * Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip
 * at most k 0's.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2 Output: 6 Explanation: [1,1,1,0,0,1,1,1,1,1,1] Bolded numbers were
 * flipped from 0 to 1. The longest subarray is underlined. Example 2:
 *
 * Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3 Output: 10 Explanation:
 * [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1] Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105 nums[i] is either 0 or 1. 0 <= k <= nums.length
 */
public class MaxConsecutiveOnesIII {

    public static void main(String[] args) {
        MaxConsecutiveOnesIII o = new MaxConsecutiveOnesIII();
        System.out.println(o.longestOnes_2ms(new int[] { 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 }, 2));
        System.out.println(o.longestOnes_2ms(new int[] {1, 1, 1, 0, 0, 0, 0, 0 }, 2));
        System.out.println(o.longestOnes(new int[] { 1, 1, 0, 1, 0, 0, 1, 1, 0 }, 1));
    }

    public int longestOnes(int[] A, int K) {
        int left = 0;
        int right = 0;
        int max = 0;

        int numZeroes = 0;
        for (right = 0; right < A.length; right++) {

            if (A[right] == 0) {
                numZeroes++;
            }

            if (numZeroes > K) {
                if (A[left] == 0) {
                    numZeroes--;
                }
                left++;
            }
            if (numZeroes <= K) {
                // this is probably what I could come up during interview...
                max = Math.max(max, right - left + 1);
            }
        }
        return max;
    }

    public int longestOnes_2ms(int[] nums, int k) {

        int s = 0;
        int i;
        for (i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                k--;
            }

            if (k < 0) {
                if (nums[s] == 0) {
                    k++;
                }
                s++;
            }
        }
        return i - s;
    }
}
