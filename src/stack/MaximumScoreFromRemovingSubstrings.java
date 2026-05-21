package stack;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 12,2024 - 7:49 AM
 */

import java.util.Stack;

/**
 * https://leetcode.com/problems/maximum-score-from-removing-substrings/
 *
 * You are given a string s and two integers x and y. You can perform two types of operations any number of times.
 *
 * Remove substring "ab" and gain x points.
 * For example, when removing "ab" from "cabxbae" it becomes "cxbae".
 * Remove substring "ba" and gain y points.
 * For example, when removing "ba" from "cabxbae" it becomes "cabxe".
 * Return the maximum points you can gain after applying the above operations on s.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "cdbcbbaaabab", x = 4, y = 5
 * Output: 19
 * Explanation:
 * - Remove the "ba" underlined in "cdbcbbaaabab". Now, s = "cdbcbbaaab" and 5 points are added to the score.
 * - Remove the "ab" underlined in "cdbcbbaaab". Now, s = "cdbcbbaa" and 4 points are added to the score.
 * - Remove the "ba" underlined in "cdbcbbaa". Now, s = "cdbcba" and 5 points are added to the score.
 * - Remove the "ba" underlined in "cdbcba". Now, s = "cdbc" and 5 points are added to the score.
 * Total score = 5 + 4 + 5 + 5 = 19.
 * Example 2:
 *
 * Input: s = "aabbaaxybbaabb", x = 5, y = 4
 * Output: 20
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 105
 * 1 <= x, y <= 104
 * s consists of lowercase English letters.
 */
public class MaximumScoreFromRemovingSubstrings {

    public static void main(String[] args) {
        MaximumScoreFromRemovingSubstrings o = new MaximumScoreFromRemovingSubstrings();
        System.out.println(o.maximumGain("cdbcbbaaabab", 4, 5));
    }

    public int maximumGain(String s, int x, int y) {
        Stack<Character> stack = new Stack<>(), stack2 = new Stack<>();
        int result = 0, max=Math.max(x,y), min =Math.min(x,y);
        char first= (x>y)?'a':'b', second=(x>y)?'b':'a';
        for(char c: s.toCharArray())
            if(!stack.isEmpty() && stack.peek() == first && c == second) {
                stack.pop();
                result +=max;
            } else stack.push(c);
        while(!stack.isEmpty()) {
            char c = stack.pop();
            if(!stack2.isEmpty() && stack2.peek() == first && c == second) {
                stack2.pop();
                result +=min;
            } else stack2.push(c);
        }
        return result;
    }

    public int maximumGain_faster(String s, int x, int y) {

        int aCount = 0;
        int bCount = 0;
        int lesser = Math.min(x, y);
        int result = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 'b') {
                result += Math.min(aCount, bCount) * lesser;
                aCount = 0;
                bCount = 0;
            } else if (c == 'a') {
                if (x < y && bCount > 0) {
                    bCount--;
                    result += y;
                } else {
                    aCount++;
                }
            } else {
                if (x > y && aCount > 0) {
                    aCount--;
                    result += x;
                } else {
                    bCount++;
                };
            }
        }

        result += Math.min(aCount, bCount) * lesser;

        return result;
    }

}
