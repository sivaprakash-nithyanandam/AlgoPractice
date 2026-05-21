package BinarySearch;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jun 21,2024 - 6:46 PM
 */

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/maximum-number-of-robots-within-budget/description/
 *
 * You have n robots. You are given two 0-indexed integer arrays, chargeTimes and runningCosts, both of length n. The ith robot costs chargeTimes[i] units to charge and costs runningCosts[i] units to run. You are also given an integer budget.
 *
 * The total cost of running k chosen robots is equal to max(chargeTimes) + k * sum(runningCosts), where max(chargeTimes) is the largest charge cost among the k robots and sum(runningCosts) is the sum of running costs among the k robots.
 *
 * Return the maximum number of consecutive robots you can run such that the total cost does not exceed budget.
 *
 *
 *
 * Example 1:
 *
 * Input: chargeTimes = [3,6,1,3,4], runningCosts = [2,1,3,4,5], budget = 25
 * Output: 3
 * Explanation:
 * It is possible to run all individual and consecutive pairs of robots within budget.
 * To obtain answer 3, consider the first 3 robots. The total cost will be max(3,6,1) + 3 * sum(2,1,3) = 6 + 3 * 6 = 24 which is less than 25.
 * It can be shown that it is not possible to run more than 3 consecutive robots within budget, so we return 3.
 * Example 2:
 *
 * Input: chargeTimes = [11,12,19], runningCosts = [10,8,7], budget = 19
 * Output: 0
 * Explanation: No robot can be run that does not exceed the budget, so we return 0.
 *
 *
 * Constraints:
 *
 * chargeTimes.length == runningCosts.length == n
 * 1 <= n <= 5 * 104
 * 1 <= chargeTimes[i], runningCosts[i] <= 105
 * 1 <= budget <= 1015
 */
public class MaximumNumberofRobotsWithinBudget {

    public static void main(String[] args) {
        MaximumNumberofRobotsWithinBudget o = new MaximumNumberofRobotsWithinBudget();
        System.out.println(o.maximumRobots(new int[]{3,6,1,3,4}, new int[]{2,1,3,4,5}, 25));
        System.out.println(o.maximumRobots(new int[]{8,17,5,2}, new int[]{1,14,4,1}, 17));
        System.out.println(o.maximumRobots(new int[]{19,63,21,8,5,46,56,45,54,30,92,63,31,71,87,94,67,8,19,89,79,25},
                new int[]{91,92,39,89,62,81,33,99,28,99,86,19,5,6,19,94,65,86,17,10,8,42}, 85));
    }

    public int maximumRobots(int[] ct, int[] rc, long b) {
        int left = 0;
        int right = ct.length;

        while (left < right) {
            int mid = left + (right - left + 1) / 2;

            if (check(ct, rc, b, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }


    public boolean check(int[] chargeTimes, int[] runningCosts, long budget, int num) {

        long sum = 0;
        //Use Deque to dynamically find the max element in sliding window
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < chargeTimes.length; i++) {
            while (!deque.isEmpty() && chargeTimes[i] >= chargeTimes[deque.peekLast()]) {
                deque.pollLast();
            }
            while (!deque.isEmpty() && i - deque.peekFirst() >= num) {
                deque.pollFirst();
            }
            if (i >= num) {
                sum -= runningCosts[i - num];
            }
            deque.addLast(i);

            sum += runningCosts[i];

            if (i >= num - 1 && chargeTimes[deque.peekFirst()] + num * sum <= budget) {
                return true;
            }
        }
        return false;
    }
}
