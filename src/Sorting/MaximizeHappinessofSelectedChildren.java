package Sorting;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * May 09,2024 - 9:58 AM
 */

import java.util.PriorityQueue;

/**
 *
 * https://leetcode.com/problems/maximize-happiness-of-selected-children/description/?envType=daily-question&envId=2024-05-09
 * You are given an array happiness of length n, and a positive integer k.
 *
 * There are n children standing in a queue, where the ith child has happiness value happiness[i]. You want to select k children from these n children in k turns.
 *
 * In each turn, when you select a child, the happiness value of all the children that have not been selected till now decreases by 1. Note that the happiness value cannot become negative and gets decremented only if it is positive.
 *
 * Return the maximum sum of the happiness values of the selected children you can achieve by selecting k children.
 *
 *
 *
 * Example 1:
 *
 * Input: happiness = [1,2,3], k = 2
 * Output: 4
 * Explanation: We can pick 2 children in the following way:
 * - Pick the child with the happiness value == 3. The happiness value of the remaining children becomes [0,1].
 * - Pick the child with the happiness value == 1. The happiness value of the remaining child becomes [0]. Note that the happiness value cannot become less than 0.
 * The sum of the happiness values of the selected children is 3 + 1 = 4.
 * Example 2:
 *
 * Input: happiness = [1,1,1,1], k = 2
 * Output: 1
 * Explanation: We can pick 2 children in the following way:
 * - Pick any child with the happiness value == 1. The happiness value of the remaining children becomes [0,0,0].
 * - Pick the child with the happiness value == 0. The happiness value of the remaining child becomes [0,0].
 * The sum of the happiness values of the selected children is 1 + 0 = 1.
 * Example 3:
 *
 * Input: happiness = [2,3,4,5], k = 1
 * Output: 5
 * Explanation: We can pick 1 child in the following way:
 * - Pick the child with the happiness value == 5. The happiness value of the remaining children becomes [1,2,3].
 * The sum of the happiness values of the selected children is 5.
 *
 *
 * Constraints:
 *
 * 1 <= n == happiness.length <= 2 * 105
 * 1 <= happiness[i] <= 108
 * 1 <= k <= n
 */
public class MaximizeHappinessofSelectedChildren {

    public static void main(String[] args) {
        MaximizeHappinessofSelectedChildren o = new MaximizeHappinessofSelectedChildren();
        System.out.println(o.maximumHappinessSum(new int[]{5,2,4,3}, 2));
    }

    public long maximumHappinessSum(int[] happiness, int k) {

        int left = 0, right = k;
        while(left < right){
            int mid = left + right >> 1;
            if(check(happiness, mid))
                left = mid + 1;
            else
                right = mid;
        }

        k = left;
        int n = happiness.length;
        quickSort(happiness, 0, n - 1, n - k);
        long sum = -(long)k * (k - 1) >> 1;
        for(int i = n -1; k-- > 0; --i)
            sum += happiness[i];

        return sum;
    }

    public boolean check(int[] happiness, int mid) {
        int count = 0;
        for(int x:happiness){
            if(x < mid) {
                continue;
            }
            if(++count > mid) {
                return true;
            }
        }

        return false;
    }

    private void quickSort(int nums[], int low, int high, int k) {
        if (low == high)
            return;

        int left = low - 1, right = high + 1, mid = low + high >> 1;
        int x = nums[mid];
        while (left < right) {
            while (nums[++left] < x) {
                continue;
            }
            while (nums[--right] > x) {
                continue;
            }
            if (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
        }

        if (right < k)
            quickSort(nums, right + 1, high, k);
        else
            quickSort(nums, low, right, k);
    }

    public long maximumHappinessSum_own(int[] happiness, int k) {

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((n1, n2) -> n2 - n1);

        for (int h : happiness) {
            pq.add(h);
        }

        long sum = 0;
        int count = 0;
        while (k-- > 0) {
            if (pq.isEmpty()) {
                break;
            }
            int v = pq.poll();
            if (v - count > 0) {
                sum += v - count;
            }
            count++;
        }
        return sum;
    }

}
