package Greedy;

import java.util.Arrays;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 07,2024 - 11:21 PM
 */

/**
 * You are given an array of integers start and an integer d, representing n intervals [start[i], start[i] + d].
 *
 * You are asked to choose n integers where the ith integer must belong to the ith interval. The score of the chosen integers is defined as the minimum absolute difference between any two integers that have been chosen.
 *
 * Return the maximum possible score of the chosen integers.
 *
 *
 *
 * Example 1:
 *
 * Input: start = [6,0,3], d = 2
 *
 * Output: 4
 *
 * Explanation:
 *
 * The maximum possible score can be obtained by choosing integers: 8, 0, and 4. The score of these chosen integers is min(|8 - 0|, |8 - 4|, |0 - 4|) which equals 4.
 *
 * Example 2:
 *
 * Input: start = [2,6,13,13], d = 5
 *
 * Output: 5
 *
 * Explanation:
 *
 * The maximum possible score can be obtained by choosing integers: 2, 7, 13, and 18. The score of these chosen integers is min(|2 - 7|, |2 - 13|, |2 - 18|, |7 - 13|, |7 - 18|, |13 - 18|) which equals 5.
 *
 *
 *
 * Constraints:
 *
 * 2 <= start.length <= 105
 * 0 <= start[i] <= 109
 * 0 <= d <= 109
 */
public class MaximizeScoreofNumbersinRanges {

    public static void main(String[] args) {
        MaximizeScoreofNumbersinRanges o = new MaximizeScoreofNumbersinRanges();
        System.out.println(o.maxPossibleScore(new int[]{6,0,3}, 2));
    }

    public int maxPossibleScore(int[] start, int d) {
        int n = start.length;
        Arrays.sort(start);
        long low = 0, high = start[n-1] + d - start[0];
        int result = 0;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (canAchieveDifference(start, d, mid)) {
                result = (int)mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    private boolean canAchieveDifference(int[] start, int d, long minDiff) {
        long lastChosen = start[0];

        for (int i = 1; i < start.length; i++) {
            long nextChosen = Math.max(lastChosen + minDiff, start[i]);
            if (nextChosen > start[i] + d) {
                return false;
            }
            lastChosen = nextChosen;
        }
        return true;
    }

}
