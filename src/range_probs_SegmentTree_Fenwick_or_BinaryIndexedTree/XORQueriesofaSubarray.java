package range_probs_SegmentTree_Fenwick_or_BinaryIndexedTree;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 13,2024 - 8:06 AM
 */

import java.util.Arrays;

/**
 * https://leetcode.com/problems/xor-queries-of-a-subarray/description/
 *
 * You are given an array arr of positive integers. You are also given the array queries where queries[i] = [lefti, righti].
 *
 * For each query i compute the XOR of elements from lefti to righti (that is, arr[lefti] XOR arr[lefti + 1] XOR ... XOR arr[righti] ).
 *
 * Return an array answer where answer[i] is the answer to the ith query.
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [1,3,4,8], queries = [[0,1],[1,2],[0,3],[3,3]]
 * Output: [2,7,14,8]
 * Explanation:
 * The binary representation of the elements in the array are:
 * 1 = 0001
 * 3 = 0011
 * 4 = 0100
 * 8 = 1000
 * The XOR values for queries are:
 * [0,1] = 1 xor 3 = 2
 * [1,2] = 3 xor 4 = 7
 * [0,3] = 1 xor 3 xor 4 xor 8 = 14
 * [3,3] = 8
 * Example 2:
 *
 * Input: arr = [4,8,2,10], queries = [[2,3],[1,3],[0,0],[0,3]]
 * Output: [8,0,4,4]
 *
 *
 * Constraints:
 *
 * 1 <= arr.length, queries.length <= 3 * 104
 * 1 <= arr[i] <= 109
 * queries[i].length == 2
 * 0 <= lefti <= righti < arr.length
 */
public class XORQueriesofaSubarray {

    public static void main(String[] args) {
        XORQueriesofaSubarray o = new XORQueriesofaSubarray();
        System.out.println(Arrays.toString(o.xorQueries(new int[]{1,3,4,8}, new int[][]{{0,1},{1,2},{0,3},{3,3}})));
        System.out.println(Arrays.toString(o.xorQueries_fenwick_tree(new int[]{1,15,8,4}, new int[][]{{2,2},{0,0},{0,3},{1,2}})));
    }

    private int powerOfTwo(int n) {
        if (n == 0 && ((n & (n-1)) == 0)) {
            return 0;
        }

        do {
            n &= n-1;
        } while ((n & (n-1)) != 0);

        return n << 1;
    }

    private void updateSegment(int[] segmentTree, int val, int i, int start, int end, int pos) {
        if (start > i || end < i || pos >= segmentTree.length) {
            return;
        }

        if (start == end) {
            segmentTree[pos] ^= val;
            return;
        }

        int mid = start + (end - start)/2;
        updateSegment(segmentTree, val, i, start, mid, pos * 2 + 1);
        updateSegment(segmentTree, val, i, mid+1, end, pos * 2 + 2);
        segmentTree[pos] = segmentTree[pos * 2 + 1] ^ segmentTree[pos * 2 + 2];

    }

    public int[] xorQueries(int[] arr, int[][] queries) {

        int n = arr.length;
        int size = powerOfTwo(n);
        int ssize = size * 2 - 1;
        int[] segmentTree = new int[ssize];

        for (int i = 0; i < n; i++) {
            updateSegment(segmentTree, arr[i], i, 0, n-1, 0);
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            res[i] = querySegment(queries[i][0], queries[i][1], segmentTree, 0, n-1, 0);
        }

        return res;
    }

    private int querySegment(int ql, int qr, int[] segmentTree, int start, int end, int pos) {

        if (qr < start || ql > end) {
            return 0;
        } else if (start >= ql && end <= qr) {
            return segmentTree[pos];
        } else {
            int mid = start + (end - start)/2;
            int l = querySegment(ql, qr, segmentTree, start, mid, pos * 2 + 1);
            int r = querySegment(ql, qr, segmentTree, mid+1, end, pos * 2 + 2);
            return l ^ r;
        }
    }

    public int[] xorQueries_fenwick_tree(int[] arr, int[][] queries) {

        int n = arr.length;
        int[] bits = new int[n+1];

        for (int i = 0; i < n; i++) {
            updateBit(i+1, arr[i], bits);
        }

        int[] res = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            res[i] = queryBit(queries[i][0], queries[i][1], bits);
        }

        return res;
    }

    private int queryBit(int i, int j, int[] bits) {
        return queryBit(j+1, bits) ^ queryBit(i, bits);
    }

    private int queryBit(int i, int[] bits) {
        int res = 0;
        while (i > 0) {
            res ^= bits[i];
            i -= (i & (-i));
        }
        return res;
    }

    private void updateBit(int i, int val, int[] bits) {
        while (i < bits.length) {
            bits[i] ^= val;
            i += (i & -i);
        }
    }

}
