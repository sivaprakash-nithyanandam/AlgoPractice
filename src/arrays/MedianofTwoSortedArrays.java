package arrays;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively. Find the median of the
 * two sorted arrays. The overall run time complexity should be O(log (m+n)). You may assume nums1
 * and nums2 cannot be both empty. Example 1: nums1 = [1, 3] nums2 = [2]
 * <p>
 * The median is 2.0 Example 2: nums1 = [1, 2] nums2 = [3, 4]
 * <p>
 * The median is (2 + 3)/2 = 2.5
 */
public class MedianofTwoSortedArrays {

  public static void main(String[] args) {
    MedianofTwoSortedArrays o = new MedianofTwoSortedArrays();

    System.out.println(o.findMedianSortedArrays_revisit(new int[]{2,3,4,5}, new int[]{1}));
    System.out.println(o.findMedianSortedArrays_revisit(new int[]{1, 2}, new int[]{3, 4}));
    System.out
            .println(o.findMedianSortedArrays(new int[]{1,3,8,9,15}, new int[]{7,11,18,19,21,25}));
    System.out
        .println(o.findMedianSortedArrays(new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10}, new int[]{1}));
    System.out.println(
        o.findMedianSortedArrays(new int[]{1, 2, 3, 11}, new int[]{4, 12, 13, 14, 15}));
    System.out.println(o.findMedianSortedArrays(new int[]{1}, new int[]{1}));
    System.out.println(o.findMedianSortedArrays(new int[]{1, 3, 7, 8}, new int[]{2, 4, 5, 6}));
    System.out.println(o.findMedianSortedArrays(new int[]{1, 2, 6, 7}, new int[]{3, 4, 5, 8}));
    System.out.println(o.findMedianSortedArrays(new int[]{1, 2, 3, 5}, new int[]{4, 6, 7, 8}));
    System.out.println(o.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
    System.out
        .println(o.findMedianSortedArrays(new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 10}));
    System.out.println(
        o.findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 6}, new int[]{6, 7, 8, 9, 10, 11}));
    System.out.println(
        o.findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 6}, new int[]{7, 8, 9, 10, 11, 12}));
  }

  public double findMedianSortedArrays_revisit(int[] nums1, int[] nums2) {

    if (nums2.length < nums1.length) {
      return findMedianSortedArrays_revisit(nums2, nums1);
    }

    int n = nums1.length + nums2.length;
    int l = 0, r = nums1.length;
    int i, j;

    while (true) {
      i = (l+r)/2;
      j = (n/2) - i;

      int r1 = i >= nums1.length ? Integer.MAX_VALUE : nums1[i];
      int l1 = i-1 < 0 ? Integer.MIN_VALUE : nums1[i-1];

      int r2 = j >= nums2.length ? Integer.MAX_VALUE : nums2[j];
      int l2 = j-1 < 0 ? Integer.MIN_VALUE : nums2[j-1];

      if (r1 >= l2 && r2 >= l1) {
        if (n % 2 == 0) {
          return (double)(Math.max(l1, l2) + Math.min(r1, r2))/2;
        } else {
          return (double)(Math.min(r1, r2));
        }
      } else if (l1 > r2) {
        r = i - 1;
      } else {
        l = i+1;
      }
    }
  }

  public double findMedianSortedArrays_try(int[] nums1, int[] nums2) {
    return 0;
  }

  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int m = nums1.length;
    int n = nums2.length;
    int k = (m + n) / 2;
    int i = 0, j = k;
    int lo = 0, hi = m;//Math.min(k, m);

    while (true) {
      i = lo + (hi - lo) / 2;
      j = k - i;
      if (get(nums1, i) >= get(nums2, j - 1)) {
        if (get(nums2, j) >= get(nums1, i - 1)) {
          break;
        } else {
          hi = i - 1;
        }
      } else {
        lo = i + 1;
      }
    }
    if ((m + n) % 2 == 1) {
      return Math.min(get(nums1, i), get(nums2, j)); //odd
    }
    return (double) (Math.min(get(nums1, i), get(nums2, j)) + Math
        .max(get(nums1, i - 1), get(nums2, j - 1))) / 2;//even
  }

  private int get(int[] nums, int i) {
    if (i < 0) {
      return Integer.MIN_VALUE;
    }
    if (i >= nums.length) {
      return Integer.MAX_VALUE;
    }
    return nums[i];
  }

  public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
    /*if (nums1.length == nums2.length) {
      return findMedianSortedArraysForSameSize(nums1, nums2, 0, nums1.length - 1, 0,
          nums2.length - 1);
    } else {
      return findMedianSortedArraysForDiffSize(nums1, nums2);
    }*/
    return findMedianSortedArraysForDiffSize(nums1, nums2);
  }

  private double findMedianSortedArraysForDiffSize(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) {
      return findMedianSortedArraysForDiffSize(nums2, nums1);
    }
    int low = 0;
    int high = nums1.length;
    int totalLen = nums1.length + nums2.length;
    while (low <= high) {
      int partitionX = (low + high) / 2;
      int partitionY = (totalLen + 1) / 2 - partitionX;

      int topLeft = partitionX == 0 ? Integer.MIN_VALUE : nums1[partitionX - 1];
      int topRight = partitionX == nums1.length ? Integer.MAX_VALUE : nums1[partitionX];

      int bottomLeft = partitionY == 0 ? Integer.MIN_VALUE : nums2[partitionY - 1];
      int bottomRight = partitionY == nums2.length ? Integer.MAX_VALUE : nums2[partitionY];

      if (topLeft <= bottomRight && bottomLeft <= topRight) {
        if ((totalLen & 1) == 1) {
          return Math.max(topLeft, bottomLeft);
        } else {
          return (double) (Math.max(topLeft, bottomLeft) + Math.min(topRight, bottomRight)) / 2;
        }
      } else if (topLeft > bottomRight) {
        high = partitionX - 1;
      } else {
        low = partitionX + 1;
      }
    }
    return -1;
  }

  private double findMedianSortedArraysForSameSize(int[] nums1, int[] nums2, int topStart,
      int topEnd,
      int bottomStart, int bottomEnd) {

    int diff = topEnd - topStart;
    if (diff == 0) {
      return (double) (nums1[topStart] + nums2[bottomStart]) / 2;
    } else if (diff == 1) {
      return (double) (Math.max(nums1[topStart], nums2[bottomStart]) + Math
          .min(nums1[topEnd], nums2[bottomEnd])) / 2;
    }

    double m1 = getMedian(nums1, topStart, topEnd);
    double m2 = getMedian(nums2, bottomStart, bottomEnd);

    if (m1 == m2) {
      return m1;
    } else {
      int carry = 0;
      if ((diff & 1) == 1) {
        carry = 1;
      }
      if (m1 < m2) {
        return findMedianSortedArraysForSameSize(nums1, nums2, (topStart + topEnd) / 2, topEnd,
            bottomStart, (bottomStart + bottomEnd) / 2 + carry);
      } else {
        return findMedianSortedArraysForSameSize(nums1, nums2, topStart,
            (topStart + topEnd) / 2 + carry, (bottomStart + bottomEnd) / 2, bottomEnd);
      }
    }
  }

  private double getMedian(int[] nums1, int start, int end) {
    int total = start + end;
    if ((total & 1) == 0) {
      return nums1[total / 2];
    } else {
      return (double) (nums1[total / 2] + nums1[(total + 1) / 2]) / 2;
    }
  }
}
