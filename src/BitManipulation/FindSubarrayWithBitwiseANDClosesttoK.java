package BitManipulation;
/**
 * @time 6/2/24-9:02 AM
 * @author sivaprakashnithyanandam
 */

import java.util.Arrays;

/**
 *
 * https://leetcode.com/contest/weekly-contest-400/problems/find-subarray-with-bitwise-and-closest-to-k/
 *
 * You are given an array nums and an integer k. You need to find a
 * subarray
 *  of nums such that the absolute difference between k and the bitwise AND of the subarray elements is as small as possible. In other words, select a subarray nums[l..r] such that |k - (nums[l] AND nums[l + 1] ... AND nums[r])| is minimum.
 *
 * Return the minimum possible value of the absolute difference.
 *
 * A subarray is a contiguous non-empty sequence of elements within an array.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,4,5], k = 3
 *
 * Output: 1
 *
 * Explanation:
 *
 * The subarray nums[2..3] has AND value 4, which gives the minimum absolute difference |3 - 4| = 1.
 *
 * Example 2:
 *
 * Input: nums = [1,2,1,2], k = 2
 *
 * Output: 0
 *
 * Explanation:
 *
 * The subarray nums[1..1] has AND value 2, which gives the minimum absolute difference |2 - 2| = 0.
 *
 * Example 3:
 *
 * Input: nums = [1], k = 10
 *
 * Output: 9
 *
 * Explanation:
 *
 * There is a single subarray with AND value 1, which gives the minimum absolute difference |10 - 1| = 9.
 *
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * 1 <= nums[i] <= 109
 * 1 <= k <= 109
 */
public class FindSubarrayWithBitwiseANDClosesttoK {

    public static void main(String[] args) {
        FindSubarrayWithBitwiseANDClosesttoK o = new FindSubarrayWithBitwiseANDClosesttoK();
        System.out.println(o.minimumDifference(new int[]{4,1,5,6,7}, 3));
        System.out.println(o.minimumDifference_v1(new int[]{4,5,6,7,8,9,10}, 3));
        System.out.println(o.minimumDifference(new int[]{1,2,4,5}, 3));
    }

    public int minimumDifference_v1(int[] A, int k) {
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < A.length; i++) {
            res = Math.min(res, Math.abs(A[i] - k));
            for (int j = i - 1; j >= 0 && (A[j] & A[i]) != A[j]; j--) {
                A[j] &= A[i];
                res = Math.min(res, Math.abs(A[j] - k));
            }
        }
        return res;
    }


    public int minimumDifference(int[] nums, int k) {
        int n = nums.length;
        int[] a = {};
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++) {
            int v = nums[i];
            int[] na = new int[a.length+1];
            int p = 0;
            na[p++] = v;

            min = Math.min(min, Math.abs(v-k));

            for(int j = 0; j < a.length; j++){
                int w = a[j] & v;
                if(na[p-1] != w){
                    na[p++] = w;
                    min = Math.min(min, Math.abs(w-k));
                }
            }
            a = Arrays.copyOf(na, p);
        }
        return min;
    }
}
