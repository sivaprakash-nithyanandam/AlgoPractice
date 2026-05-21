package arrays;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Aug 02,2024 - 8:40 PM
 */

/**
 * https://leetcode.com/problems/time-needed-to-rearrange-a-binary-string/
 * You are given a binary string s. In one second, all occurrences of "01" are simultaneously replaced with "10". This process repeats until no occurrences of "01" exist.
 *
 * Return the number of seconds needed to complete this process.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "0110101"
 * Output: 4
 * Explanation:
 * After one second, s becomes "1011010".
 * After another second, s becomes "1101100".
 * After the third second, s becomes "1110100".
 * After the fourth second, s becomes "1111000".
 * No occurrence of "01" exists any longer, and the process needed 4 seconds to complete,
 * so we return 4.
 * Example 2:
 *
 * Input: s = "11100"
 * Output: 0
 * Explanation:
 * No occurrence of "01" exists in s, and the processes needed 0 seconds to complete,
 * so we return 0.
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s[i] is either '0' or '1'.
 *
 *
 * Follow up:
 *
 * Can you solve this problem in O(n) time complexity?
 */

/**
 *
 * https://leetcode.com/problems/time-needed-to-rearrange-a-binary-string/solutions/2454262/dp-vs-brute-force/
 *
 * DP is O(n) but could be tricky to figure out; Brute-Force is good enough for the contest since n <= 1,000.
 *
 * DP
 * We count "zeros" so far. If we have a string 00001, we need 4 seconds (as we have 4 zeros) to make it 10000.
 *
 * We also track seconds we need to rearrange first i letters.
 *
 * If i + 1 letter is 1, we need at least seconds + 1 seconds, but not less than zero seconds.
 *
 * Here is calculation for the "100111110001000001" test case:
 *
 * For i == 11 (yellow), the result is 7 as we need 6 seconds for i - 1.
 * For i == 17 (green), the result is 10 as we have 10 zeros, even though we need 7 seconds for i - 1.
 */
public class TimeNeededtoRearrangeaBinaryString {

    public static void main(String[] args) {
        TimeNeededtoRearrangeaBinaryString o = new TimeNeededtoRearrangeaBinaryString();
        System.out.println(o.secondsToRemoveOccurrences_o0fn("0110101"));
    }

    public int secondsToRemoveOccurrences_o0fn(String s) {

        int zeros = 0, time = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                zeros++;
            } else if (zeros > 0) {
                time = Math.max(time+1, zeros);
            }
        }
        return time;
    }

    public int secondsToRemoveOccurrences_bruteforce(String s) {
        int res = 0;
        char[] chars = s.toCharArray();

        boolean changed = true;
        while (changed) {

            changed = false;

            for (int i = 0; i < chars.length-1; i++) {
                if (chars[i] == '0' && chars[i+1] == '1') {
                    chars[i] = '1';
                    chars[i+1] = '0';
                    changed = true;
                    i++;
                }
            }
            if (changed) {
                res++;
            }
        }
        return res;
    }
}
