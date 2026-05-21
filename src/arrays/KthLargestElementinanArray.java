package arrays;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 6/14/2021  12:25 AM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 *
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 *
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 * Example 2:
 *
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4
 *
 *
 * Constraints:
 *
 * 1 <= k <= nums.length <= 104
 * -104 <= nums[i] <= 104
 */
public class KthLargestElementinanArray {

  public static void main(String[] args) {
    KthLargestElementinanArray o = new KthLargestElementinanArray(3, new int[]{4, 5, 8, 2});
    System.out.println(o.add(3));
    System.out.println(o.add(5));
    System.out.println(o.add(10));
    System.out.println(o.add(9));
    System.out.println(o.add(4));


//    System.out.println(o.KthLargestElementinanArray(new int[]{4, 5, 8, 2}, 3));
    System.out.println(o.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
    System.out.println(o.findKthLargest_faster(new int[]{3,2,1,5,6,4}, 2));
  }

  private PriorityQueue<Integer> minHeap;
  private final int s;

  public KthLargestElementinanArray(int k, int[] nums) {
    this.minHeap = new PriorityQueue<>((n1, n2) -> n1 - n2);
    this.s = k;
    Arrays.stream(nums).forEach(num -> this.add(num));
  }

  public int add(int val) {
    this.minHeap.offer(val);
    if (this.minHeap.size() > this.s) {
      this.minHeap.poll();
    }
    return this.minHeap.peek();
  }

  public int findKthLargest_faster(int[] nums, int k) {
    return quickSelect(nums, 0, nums.length - 1, nums.length - k);
  }

  // swap helper functiom
  private void swap(int[] nums, int a, int b) {
    int temp = nums[a];
    nums[a] = nums[b];
    nums[b] = temp;
  }

  // partition helper function
  // return the pivot index
  private int partition(int[] nums, int left, int right, int pivotIndex) {
    int actualPivotValue = nums[pivotIndex];
    int movingIndex = left;
    // swap the pivot to the right
    swap(nums, pivotIndex, right);

    // move all small element to the left
    for(int i = left; i <= right; i++) {
      if(nums[i] < actualPivotValue) {
        swap(nums, i, movingIndex);
        movingIndex++;
      }
    }

    // swap the pivot with the right
    swap(nums, movingIndex, right);
    return movingIndex;
  }

  // quick select main function
  private int quickSelect(int[] nums, int left, int right, int targetIndex) {
    // special case of one element array
    if(left == right) return nums[left];
    // calculate the random integer
    Random random = new Random();
    int pivotIndex = left + random.nextInt(right - left);

    // call partition once to figure out where the first pivot is
    pivotIndex = partition(nums, left, right, pivotIndex);

    if(pivotIndex == targetIndex) {
      return nums[targetIndex];
    } else if(targetIndex < pivotIndex) {
      return quickSelect(nums, left, pivotIndex - 1, targetIndex);
    } else {
      return quickSelect(nums, pivotIndex + 1, right, targetIndex);
    }
  }

  public int findKthLargest(int[] nums, int k) {
    List.of(nums).stream().forEach(num -> System.out.println(num));
    // init heap 'the smallest element first'
    PriorityQueue<Integer> heap =
        new PriorityQueue<Integer>((n1, n2) -> n1 - n2);

    // keep k largest elements in the heap
    for (int n: nums) {
      heap.add(n);
      if (heap.size() > k)
        heap.poll();
    }

    // output
    return heap.poll();
  }
}
