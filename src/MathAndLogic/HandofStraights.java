package MathAndLogic;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jun 05,2024 - 9:32 PM
 */

import java.util.*;

/**
 * https://leetcode.com/problems/hand-of-straights/description/
 *
 * Alice has some number of cards and she wants to rearrange the cards into groups so that each group is of size groupSize, and consists of groupSize consecutive cards.
 *
 * Given an integer array hand where hand[i] is the value written on the ith card and an integer groupSize, return true if she can rearrange the cards, or false otherwise.
 *
 *
 *
 * Example 1:
 *
 * Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
 * Output: true
 * Explanation: Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8]
 * Example 2:
 *
 * Input: hand = [1,2,3,4,5], groupSize = 4
 * Output: false
 * Explanation: Alice's hand can not be rearranged into groups of 4.
 *
 *
 *
 * Constraints:
 *
 * 1 <= hand.length <= 104
 * 0 <= hand[i] <= 109
 * 1 <= groupSize <= hand.length
 */
public class HandofStraights {

    public static void main(String[] args) {
        HandofStraights o = new HandofStraights();
        System.out.println(o.isNStraightHand_9ms(new int[]{1,2,3,6,2,3,4,7,8}, 3));
        System.out.println(o.isNStraightHand_huge_input(new int[]{9,13,15,23,22,25,4,4,29,15,8,23,12,19,24,17,18,11,22,24,17,17,10,23,21,18,14,18,7,6,3,6,19,11,16,11,12,13,8,26,17,20,13,19,22,21,27,9,20,15,20,27,8,13,25,23,22,15,9,14,20,10,6,5,14,12,7,16,21,18,21,24,23,10,21,16,18,16,18,5,20,19,20,10,14,26,2,9,19,12,28,17,5,7,25,22,16,17,21,11}, 10));
        System.out.println(o.isNStraightHand(new int[]{1,2,3,6,2,3,4,7,8}, 3));
    }

    public boolean findsucessors(int[] hand, int groupSize, int i, int n) {
        int f = hand[i] + 1;
        hand[i] = -1;
        int count = 1;
        i += 1;
        while (i < n && count < groupSize) {
            if (hand[i] == f) {
                f = hand[i] + 1;
                hand[i] = -1;
                count++;
            }
            i++;
        }
        if (count != groupSize)
            return false;
        else
            return true;
    }

    public boolean isNStraightHand_9ms(int[] hand, int groupSize) {
        int n = hand.length;
        if (n % groupSize != 0)
            return false;
        Arrays.sort(hand);
        int i = 0;
        for (; i < n; i++) {
            if (hand[i] >= 0) {
                if (!findsucessors(hand, groupSize, i, n))
                    return false;
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/hand-of-straights/solutions/135598/c-java-python-o-mlogm-complexity/
     *
     * Solution 2:
     * Count number of different cards to a map c
     * Cur represent current open straight groups.
     * In a deque start, we record the number of opened a straight group.
     * Loop from the smallest card number.
     * For example, hand = [1,2,3,2,3,4], W = 3
     * We meet one 1:
     * opened = 0, we open a new straight groups starting at 1, push (1,1) to start.
     * We meet two 2:
     * opened = 1, we need open another straight groups starting at 1, push (2,1) to start.
     * We meet two 3:
     * opened = 2, it match current opened groups.
     * We open one group at 1, now we close it. opened = opened - 1 = 1
     * We meet one 4:
     * opened = 1, it match current opened groups.
     * We open one group at 2, now we close it. opened = opened - 1 = 0
     *
     * return if no more open groups.
     * Complexity
     * O(MlogM + N), where M is the number of different cards.
     * Because I count and sort cards.
     * In Cpp and Java it's O(NlogM), which can also be improved.
     * @param hand
     * @param W
     * @return
     */
    public boolean isNStraightHand_huge_input(int[] hand, int W) {

        Map<Integer, Integer> c = new TreeMap<>();

        for (int i : hand) {
            c.put(i, c.getOrDefault(i, 0)+1);
        }

        Queue<Integer> start = new LinkedList<>();

        int last_checked = -1;
        int opened = 0;

        for (int i : c.keySet()) {

            if (opened > 0 && i > last_checked + 1 || opened > c.get(i)) {
                return false;
            }
            start.add(c.get(i) - opened);

            last_checked = i;

            opened = c.get(i);

            if (start.size() == W) {
                opened -= start.remove();
            }
        }
        return opened == 0;
    }

    /**
     * Solution 1
     * Count number of different cards to a map c
     * Loop from the smallest card number.
     * Everytime we meet a new card i, we cut off i - i + W - 1 from the counter.
     * Complexity:
     * Time O(MlogM + MW), where M is the number of different cards.
     * @param hand
     * @param W
     * @return
     */
    public boolean isNStraightHand(int[] hand, int W) {
        Map<Integer, Integer> c = new TreeMap<>();
        for (int i : hand) {
            c.put(i, c.getOrDefault(i, 0)+1);
        }
        for (int it : c.keySet())
            if (c.get(it) > 0) {
                for (int i = W - 1; i >= 0; --i) {
                    if (c.getOrDefault(it + i, 0) < c.get(it)) {
                        return false;
                    }
                    c.put(it + i, c.get(it + i) - c.get(it));
                }
            }
        return true;
    }
}
