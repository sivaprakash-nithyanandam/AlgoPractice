package arrays;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/3/2021  1:42 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.Arrays;

/**
 * https://leetcode.com/problems/k-closest-points-to-origin/
 * <p>
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an
 * integer k, return the k closest points to the origin (0, 0).
 * <p>
 * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 +
 * (y1 - y2)2).
 * <p>
 * You may return the answer in any order. The answer is guaranteed to be unique (except for the
 * order that it is in).
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: points = [[1,3],[-2,2]], k = 1 Output: [[-2,2]] Explanation: The distance between (1, 3)
 * and the origin is sqrt(10). The distance between (-2, 2) and the origin is sqrt(8). Since sqrt(8)
 * < sqrt(10), (-2, 2) is closer to the origin. We only want the closest k = 1 points from the
 * origin, so the answer is just [[-2,2]]. Example 2:
 * <p>
 * Input: points = [[3,3],[5,-1],[-2,4]], k = 2 Output: [[3,3],[-2,4]] Explanation: The answer
 * [[-2,4],[3,3]] would also be accepted.
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= k <= points.length <= 104 -104 < xi, yi < 104
 */
public class KClosestPointstoOrigin {

  public static void main(String[] args) {
    KClosestPointstoOrigin o = new KClosestPointstoOrigin();
    System.out.println(o.kClosest(new int[][]{{1,3},{-2,2}}, 1));
    System.out.println(o.kClosest(new int[][]{{6,6}, {5,5}, {4,4}, {1,1}, {2,2}, {7,7}}, 2));
    System.out.println(o.kClosest(new int[][]{{4, 4}, {7, 7}, {3, 3}, {5, 5}, {6, 6}}, 2));
    System.out.println(o.kClosest(new int[][]{{6, 6}, {5, 5}, {4, 4}, {3, 3}, {2, 2}, {1, 1}}, 2));
    System.out.println(o.kClosest(new int[][]{{3, 3}, {5, -1}, {-2, 4}}, 2));
  }

  public int[][] kClosest(int[][] points, int k) {

    int pivot = points.length;
    int left = 0, right = points.length-1;

    while (pivot != k) {
      pivot = quickSort(left, right, points);

      if (pivot < k) {
        left = pivot;
      } else {
        right = pivot - 1;
      }
    }
    return Arrays.copyOf(points, k);
  }

  private int quickSort(int left, int right, int[][] points) {
    int pivot = left + (right - left)/2;
    int pivotDist = findDist(points[pivot]);

    while (left < right) {
      if (findDist(points[left]) >= pivotDist) {

        int[] t = points[left];
        points[left] = points[right];
        points[right] = t;
        right--;
      } else {
        left++;
      }
    }

    if (findDist(points[left]) < pivotDist) {
      left++;
    }

    return left;
  }

  private int findDist(int[] point) {
    return point[0] * point[0] + point[1] * point[1];
  }

  public int[][] kClosest1(int[][] points, int K) {
    int len = points.length, l = 0, r = len - 1;
    while (l <= r) {
      int mid = helper(points, l, r);
      if (mid == K) {
        break;
      }
      if (mid < K) {
        l = mid + 1;
      } else {
        r = mid - 1;
      }
    }
    return Arrays.copyOfRange(points, 0, K);
  }

  private int helper(int[][] A, int l, int r) {
    int[] pivot = A[l];
    while (l < r) {
      while (l < r && compare(A[r], pivot) >= 0) {
        r--;
      }
      A[l] = A[r];
      while (l < r && compare(A[l], pivot) <= 0) {
        l++;
      }
      A[r] = A[l];
    }
    A[l] = pivot;
    return l;
  }

  private int compare(int[] p1, int[] p2) {
    return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
  }
}
