package BinarySearch;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 14,2024 - 12:04 PM
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * You are given a 2D array of integers coordinates of length n and an integer k, where 0 <= k < n.
 *
 * coordinates[i] = [xi, yi] indicates the point (xi, yi) in a 2D plane.
 *
 * An increasing path of length m is defined as a list of points (x1, y1), (x2, y2), (x3, y3), ..., (xm, ym) such that:
 *
 * xi < xi + 1 and yi < yi + 1 for all i where 1 <= i < m.
 * (xi, yi) is in the given coordinates for all i where 1 <= i <= m.
 * Return the maximum length of an increasing path that contains coordinates[k].
 *
 *
 *
 * Example 1:
 *
 * Input: coordinates = [[3,1],[2,2],[4,1],[0,0],[5,3]], k = 1
 *
 * Output: 3
 *
 * Explanation:
 *
 * (0, 0), (2, 2), (5, 3) is the longest increasing path that contains (2, 2).
 *
 * Example 2:
 *
 * Input: coordinates = [[2,1],[7,0],[5,6]], k = 2
 *
 * Output: 2
 *
 * Explanation:
 *
 * (2, 1), (5, 6) is the longest increasing path that contains (5, 6).
 *
 *
 *
 * Constraints:
 *
 * 1 <= n == coordinates.length <= 105
 * coordinates[i].length == 2
 * 0 <= coordinates[i][0], coordinates[i][1] <= 109
 * All elements in coordinates are distinct.
 * 0 <= k <= n - 1
 *
 */
public class LengthoftheLongestIncreasingPath {

    public static void main(String[] args) {
        LengthoftheLongestIncreasingPath o = new LengthoftheLongestIncreasingPath();
        System.out.println(o.maxPathLength_unstandable(new int[][]{{3,1},{2,2},{4,1},{0,0},{5,3}}, 1));
        System.out.println(o.maxPathLength_unstandable(new int[][]{{3,1},{2,2},{4,1},{0,0},{5,3}}, 1));
    }

    public int maxPathLength_unstandable(int[][] coordinates, int k) {
        int ncx = coordinates[k][0], ncy = coordinates[k][1];
        int n = coordinates.length;

        // Sort coordinates by x and if equal, by decreasing y
        Arrays.sort(coordinates, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });

        List<Integer> coordinates1 = new ArrayList<>();
        List<Integer> coordinates2 = new ArrayList<>();

        // Classify points to the left & below and right & above (x_k, y_k)
        for (int i = 0; i < n; i++) {
            int x = coordinates[i][0];
            int y = coordinates[i][1];

            if (x < ncx && y < ncy) {
                coordinates1.add(y);
            }
            if (x > ncx && y > ncy) {
                coordinates2.add(y);
            }
        }

        // Array to store intermediate LIS lengths
        int[] dp = new int[n];

        // Calculate LIS for both parts
        int size1 = calculateLIS(coordinates1, dp);
        int size2 = calculateLIS(coordinates2, dp);

        // Combine results
        return size1 + size2 + 1;
    }

    // Helper function to calculate LIS using binary search
    private int calculateLIS(List<Integer> coordinates, int[] dp) {
        int size = 0;

        for (int i = 0; i < coordinates.size(); i++) {
            int y = coordinates.get(i);

            // Binary search for the position in dp array
            int pos = Arrays.binarySearch(dp, 0, size, y);
            if (pos < 0) pos = -(pos + 1);

            dp[pos] = y;

            if (pos == size) size++;
        }

        return size;
    }

    public int maxPathLength_do_not_understood_yet(int[][] coordinates, int k) {
        Integer[] sort = new Integer[coordinates.length];
        for (int i = 0; i < coordinates.length; ++i) {
            sort[i] = i;
        }
        Arrays.sort(sort, (a, b) -> (coordinates[a][0] == coordinates[b][0]
                ? Integer.compare(coordinates[b][1], coordinates[a][1]) : Integer.compare(coordinates[a][0], coordinates[b][0])));
        int[] dp = new int[coordinates.length];
        int initial = -1, p = 0;
        for (int i : sort) {
            if (i == k) {
                initial = binarySearchPos(dp, p, coordinates[i][1]);
                dp = new int[coordinates.length];
                dp[0] = coordinates[i][1];
                p = 1;
            } else {
                int tmp = binarySearchPos(dp, p, coordinates[i][1]);
                if (initial != -1) {
                    if (tmp != 0) {
                        dp[tmp] = coordinates[i][1];
                    }
                } else {
                    dp[tmp] = coordinates[i][1];
                }
                if (tmp == p) {
                    ++p;
                }
            }
        }
        return initial + p;
    }

    private int binarySearchPos(int[] dp, int pos, int num) {
        if (pos == 0 || dp[pos - 1] < num) {
            return pos;
        }
        int st = 0, ed = pos;
        while (st < ed) {
            int mid = (st + ed) / 2;
            if (dp[mid] < num) {
                st = mid + 1;
            } else {
                ed = mid;
            }
        }
        return st;
    }
}
