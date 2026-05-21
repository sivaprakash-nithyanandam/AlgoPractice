package arrays;/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

/**
 *https://leetcode.com/problems/find-peak-element/
 *
 * A peak element is an element that is strictly greater than its neighbors.
 *
 * Given an integer array nums, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.
 *
 * You may imagine that nums[-1] = nums[n] = -∞.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,1]
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 * Example 2:
 *
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 5
 * Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * -231 <= nums[i] <= 231 - 1
 * nums[i] != nums[i + 1] for all valid i.
 *
 *
 * Follow up: Could you implement a solution with logarithmic complexity?
 */
public class FindPeakElement {

  public static void main(String[] args) {
    FindPeakElement o = new FindPeakElement();
    System.out.println(o.findPeakElement(new int[]{1, 2}));
    System.out.println(o.findPeakElement(new int[]{1, 2, 3, 1}));
  }

  public int findPeakElement(int[] nums) {
    return findPeakElement(nums, 0, nums.length-1);
  }

  public int findPeakElement(int[] nums, int start, int end) {

    if (start > end) {
      return -1;
    }
    int mid = start + (end - start)/2;
    if (mid == 0 || nums[mid-1] < nums[mid]) {
      if (mid == nums.length-1 || nums[mid] > nums[mid+1]) {
        return mid;
      }
    }
    int res = findPeakElement(nums, start, mid-1);
    if (res == -1) {
      res = findPeakElement(nums, mid+1, end);
    }
    return res;
  }

  public int findPeakElement_also_works(int[] nums) {

    int n = nums.length;
    if (n == 1) {
      return 0;
    }

    if (nums[1] < nums[0]) {
      return 0;
    } else if (nums[n-2] < nums[n-1]) {
      return n-1;
    } else {
      for (int i = 1; i < n-1; i++) {
        if (nums[i] > nums[i-1] && nums[i] > nums[i+1]) {
          return i;
        }
      }
    }
    return -1;
  }
}
