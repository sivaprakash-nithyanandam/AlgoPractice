package range_probs_SegmentTree_Fenwick_or_BinaryIndexedTree;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 29,2024 - 8:18 PM
 */

import java.util.Arrays;

/**
 * https://leetcode.com/problems/count-number-of-teams/description/
 *
 * There are n soldiers standing in a line. Each soldier is assigned a unique rating value.
 *
 * You have to form a team of 3 soldiers amongst them under the following rules:
 *
 * Choose 3 soldiers with index (i, j, k) with rating (rating[i], rating[j], rating[k]).
 * A team is valid if: (rating[i] < rating[j] < rating[k]) or (rating[i] > rating[j] > rating[k]) where (0 <= i < j < k < n).
 * Return the number of teams you can form given the conditions. (soldiers can be part of multiple teams).
 *
 *
 *
 * Example 1:
 *
 * Input: rating = [2,5,3,4,1]
 * Output: 3
 * Explanation: We can form three teams given the conditions. (2,3,4), (5,4,1), (5,3,1).
 * Example 2:
 *
 * Input: rating = [2,1,3]
 * Output: 0
 * Explanation: We can't form any team given the conditions.
 * Example 3:
 *
 * Input: rating = [1,2,3,4]
 * Output: 4
 *
 *
 * Constraints:
 *
 * n == rating.length
 * 3 <= n <= 1000
 * 1 <= rating[i] <= 105
 * All the integers in rating are unique.
 */
public class CountNumberofTeams {

    public static void main(String[] args) {
        CountNumberofTeams o = new CountNumberofTeams();
        System.out.println(o.numTeams_segment_tree(new int[]{2,5,3,4,1}));
        System.out.println(o.numTeams_fenwick_or_binary_indexed_tree(new int[]{2,5,3,4,1}));
        System.out.println(o.numTeams_segment_tree(new int[]{2,1,3}));
        System.out.println(o.numTeams_segment_tree(new int[]{1,2,3,4}));
    }

    private int nextPowerOf2(int n) {

        if (n == 0) {
            return 0;
        }

        while ((n & (n-1)) != 0) {
            n = (n & (n-1));
        }
        return n << 1;
    }

    private void updateSegment(int[] segments, int i, int val, int left, int right, int pos) {

        if (i < left || i > right || pos >= segments.length) {
            return;
        }

        if (left == right) {
            segments[pos] = val;
            return;
        }

        int mid = left + (right - left)/2;
        updateSegment(segments, i, val, left, mid, pos * 2 + 1);
        updateSegment(segments, i, val, mid+1, right, pos * 2 + 2);
        segments[pos] = segments[pos * 2 + 1] + segments[pos * 2 + 2];
    }

    private int querySegment(int[] segments, int qLeft, int qRight, int left, int right, int pos) {
        if (qLeft > right || qRight < left) {
            return 0;
        }
        if (left >= qLeft && right <= qRight) {
            return segments[pos];
        }

        int mid = left + (right - left) / 2;
        return querySegment(segments, qLeft, qRight, left, mid, pos * 2 + 1) +
                querySegment(segments, qLeft, qRight, mid+1, right, pos * 2 + 2);
    }

    public int numTeams_segment_tree(int[] rating) {

        int n = rating.length;
        int max = Arrays.stream(rating).max().getAsInt() + 1;

        int nextPowerOf2 = nextPowerOf2(max);
        int segmentSize = nextPowerOf2 * 2 - 1;
        int[] lSegments = new int[segmentSize];
        int[] rSegments = new int[segmentSize];

        updateSegment(lSegments, rating[0], 1, 0, max, 0);

        for (int i = 1; i < n; i++) {
            updateSegment(rSegments, rating[i], 1, 0, max, 0);
        }

        int res = 0;

        for (int i = 1; i < n; i++) {
            int x = rating[i];
            updateSegment(rSegments, x, 0, 0, max, 0);

            //For i < j < k
            int ls = querySegment(lSegments, 0, x-1, 0, max, 0);
            int rs = querySegment(rSegments, 0, x, 0, max, 0);
            res += ls * (n - 1 - i - rs);

            //For i > j > k
            ls = querySegment(lSegments, 0, x, 0, max, 0);
            rs = querySegment(rSegments, 0, x-1, 0, max, 0);
            res += (i - ls) * rs;

            updateSegment(lSegments, x, 1, 0, max, 0);
        }
        return res;
    }

    public int numTeams_fenwick_or_binary_indexed_tree(int[] rating) {
        int n = rating.length;
        int maxRating = Arrays.stream(rating).max().getAsInt();

        BIT lbtree = new BIT(maxRating);
        BIT rbtree = new BIT(maxRating);

        lbtree.update(rating[0], 1);
        for (int i = 1; i < n; i++) {
            rbtree.update(rating[i], 1);
        }

        int res = 0;
        for (int i = 1; i < n - 1; i++) {
            int x = rating[i];
            rbtree.update(x, -1);

            //For i > j > k
            res += rbtree.query(x - 1) * (i - lbtree.query(x));

            //For i < j < k
            res += lbtree.query(x - 1) * (n - i - 1 - rbtree.query(x));

            lbtree.update(rating[i], 1);
        }

        return res;
    }

    class BIT {
        private int[] tree;
        private int n;

        public BIT(int n) {
            this.n = n;
            this.tree = new int[n + 1];
        }

        public void update(int x, int v) {
            while (x <= n) {
                tree[x] += v;
                x += x & -x;
            }
        }

        public int query(int x) {
            int res = 0;
            while (x > 0) {
                res += tree[x];
                x -= x & -x;
            }
            return res;
        }
    }
}
