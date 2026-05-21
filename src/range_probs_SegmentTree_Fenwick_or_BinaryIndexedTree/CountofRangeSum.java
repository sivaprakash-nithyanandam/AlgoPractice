package range_probs_SegmentTree_Fenwick_or_BinaryIndexedTree;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/10/2021  12:41 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

/**
 * https://leetcode.com/problems/count-of-range-sum/
 * <p>
 * Given an integer array nums and two integers lower and upper, return the number of range sums
 * that lie in [lower, upper] inclusive.
 * <p>
 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j
 * inclusive, where i <= j.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [-2,5,-1], lower = -2, upper = 2 Output: 3 Explanation: The three ranges are:
 * [0,0], [2,2], and [0,2] and their respective sums are: -2, -1, 2. Example 2:
 * <p>
 * Input: nums = [0], lower = 0, upper = 0 Output: 1
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= nums.length <= 105 -231 <= nums[i] <= 231 - 1 -105 <= lower <= upper <= 105 The answer is
 * guaranteed to fit in a 32-bit integer.
 */
public class CountofRangeSum {

  private int lower;
  private int upper;
  private int count = 0;
  private long[] pfxSum;

  public int countRangeSum_faster(int[] nums, int lower, int upper) {
    int n = nums.length;
    long[] sum = new long[n + 1];
    sum[0] = 0;
    for(int i = 1; i <= n; i++) {
      sum[i] = sum[i - 1] + nums[i - 1];
    }
    count = 0;
    long[] sorted = new long[n + 1];
    mergeSort(sum, sorted, lower, upper, 0, n);
    return count;
  }
  private void mergeSort(long[] sum, long[] sorted, int lower, int upper, int start, int end) {
    if(start >= end) {
      return;
    }
    int mid = start + (end - start) / 2;
    mergeSort(sum, sorted, lower, upper, start, mid);
    mergeSort(sum, sorted, lower, upper, mid + 1, end);
    merge(sum, sorted, lower, upper, start, mid, end);
  }
  private void merge(long[] sum, long[] sorted, int lower, int upper, int start, int mid, int end) {
    int right = mid + 1, low = mid + 1, high = mid + 1;
    int idx = start;
    for(int left = start; left <= mid; left++) {
      while(low <= end && sum[low] - sum[left] < lower) {
        low++;
      }
      while(high <= end && sum[high] - sum[left] <= upper) {
        high++;
      }
      while(right <= end && sum[right] < sum[left]) {
        sorted[idx++] = sum[right++];
      }
      sorted[idx++] = sum[left];
      count += high - low;
    }
    while(right <= end) {
      sorted[idx++] = sum[right++];
    }
    System.arraycopy(sorted, start, sum, start, end - start + 1);
  }

  public int countRangeSum(int[] nums, int lower, int upper) {
    long[] prefixSums = new long[nums.length+1];

    for (int i = 0; i < nums.length; i++) {
      prefixSums[i+1] = prefixSums[i] + nums[i];
    }

    mergeSort(prefixSums, 0, prefixSums.length-1, lower, upper);
    return count;
  }

  private void mergeSort(long[] prefixSums, int low, int high, int lower, int upper) {
    if (low >= high) {
      return;
    }

    int mid = low + (high - low)/2;
    mergeSort(prefixSums, low, mid, lower, upper);
    mergeSort(prefixSums, mid+1, high, lower, upper);

    int j = mid+1, k = mid+1;

    for (int i = low; i <= mid; i++) {
      while(j <= high && prefixSums[j] - prefixSums[i] < lower) {
        j++;
      }
      while (k <= high && prefixSums[k] - prefixSums[i] <= upper) {
        k++;
      }
      count += (k - j);
    }
    mergeSort(prefixSums, low, mid, high);
  }

  private void mergeSort(long[] prefixSums, int low, int mid, int high) {
    long[] temp = new long[high-low+1];

    for (int i = low; i <= high; i++) {
      temp[i - low] = prefixSums[i];
    }

    int i = low, j = mid+1;
    int idx = low;
    while(i <= mid && j <= high) {
      if (temp[i-low] > temp[j-low]) {
        prefixSums[idx++] = temp[j-low];
        j++;
      } else {
        prefixSums[idx++] = temp[i-low];
        i++;
      }
    }
    while (i <= mid) {
      prefixSums[idx++] = temp[i-low];
      i++;
    }
  }

  public int countRangeSum_1(int[] nums, int lower, int upper) {
    int n = nums.length;
    this.lower = lower;
    this.upper = upper;

    this.pfxSum = new long[n + 1];
    for (int i = 0; i < n; i++) {
      pfxSum[i + 1] = pfxSum[i] + nums[i];
    }

    mergeSort_1(0, n);
    return count;
  }

  private void mergeSort_1(int low, int high) {
    if (low >= high) {
      return;
    }
    int mid = low + (high - low) / 2;

    mergeSort_1(low, mid);
    mergeSort_1(mid + 1, high);

    int i = mid + 1, j = mid + 1;
    for (int k = low; k <= mid; k++) {
      while (i <= high && pfxSum[i] - pfxSum[k] < lower) {
        i++;
      }
      while (j <= high && pfxSum[j] - pfxSum[k] <= upper) {
        j++;
      }

      count += j - i;
    }

    merge_1(low, mid, high);
  }

  private void merge_1(int low, int mid, int high) {
    long[] helper = new long[high - low + 1];
    for (int i = low; i <= high; i++) {
      helper[i - low] = pfxSum[i];
    }

    int i = low, j = mid + 1;
    int idx = low;

    while (i <= mid && j <= high) {
      if (helper[i - low] < helper[j - low]) {
        pfxSum[idx++] = helper[i++ - low];
      } else {
        pfxSum[idx++] = helper[j++ - low];
      }
    }

    while (i <= mid) {
      pfxSum[idx++] = helper[i++ - low];
    }
  }

  public static void main(String[] args) {
    CountofRangeSum o = new CountofRangeSum();
    System.out.println(o.countRangeSum(new int[]{-2,5,-1}, -2, 2));
    System.out.println(o.countRangeSum(new int[]{1}, -2, 2));
    System.out.println(o.countRangeSum(new int[]{0, -3, -3, 1, 1, 2}, 3, 4));
    System.out.println(o.countRangeSum(new int[]{-2147483647,0,-2147483647,2147483647}, -564, 3864));
  }
}
