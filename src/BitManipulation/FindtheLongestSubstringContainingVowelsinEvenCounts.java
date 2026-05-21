package BitManipulation;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/26/2021  5:09 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/
 *
 * Given the string s, return the size of the longest substring containing each vowel an even number of times. That is, 'a', 'e', 'i', 'o', and 'u' must appear an even number of times.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "eleetminicoworoep"
 * Output: 13
 * Explanation: The longest substring is "leetminicowor" which contains two each of the vowels: e, i and o and zero of the vowels: a and u.
 * Example 2:
 *
 * Input: s = "leetcodeisgreat"
 * Output: 5
 * Explanation: The longest substring is "leetc" which contains two e's.
 * Example 3:
 *
 * Input: s = "bcbcbc"
 * Output: 6
 * Explanation: In this case, the given string "bcbcbc" is the longest because all vowels: a, e, i, o and u appear zero times.
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 5 x 10^5
 * s contains only lowercase English letters.
 */
public class FindtheLongestSubstringContainingVowelsinEvenCounts {

  public static void main(String[] args) {
    FindtheLongestSubstringContainingVowelsinEvenCounts o = new FindtheLongestSubstringContainingVowelsinEvenCounts();
    System.out.println(o.findTheLongestSubstring_try_this("eleetminicoworoep"));
    System.out.println(o.findTheLongestSubstring_better("eleetminicoworoep"));
    System.out.println(o.findTheLongestSubstring_4ms("eleetminicoworoep"));
    System.out.println(o.findTheLongestSubstring("aeiou"));
    System.out.println(o.findTheLongestSubstring("leetcodeisgreat"));
    System.out.println(o.findTheLongestSubstring("bcbcbc"));
  }

  public int findTheLongestSubstring_4ms(String s) {
    final int[] num = new int[32];
    num[0] = 1;
    num[4] = 2;
    num['i' - 'a'] = 4;
    num['o' - 'a'] = 8;
    num['u' - 'a'] = 16;
    final int[] first = new int[32];
    Arrays.fill(first, -1);
    int state = first[0] = 0, res = 0, n = s.length();
    for (int i = 0; i < n; i++) {
      state ^= num[s.charAt(i) - 'a'];
      if (first[state] == -1) {
        first[state] = i + 1;
      } else {
        res = Math.max(res, i + 1 - first[state]);
      }
    }
    return res;
  }

  HashMap<Character, Integer> vowelMask = new HashMap<Character, Integer>() {
    {
      put('a',1);
      put('e',2);
      put('i',4);
      put('o',8);
      put('u',16);
    }
  };
  public int findTheLongestSubstring_better(String s) {
    HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();
    hmap.put(0,-1);
    int maxLength = 0;
    int x = 0;
    for(int i = 0; i < s.length(); i++) {
      if(vowelMask.containsKey(s.charAt(i))) {
        x = x ^ vowelMask.get(s.charAt(i));
      }
      if(hmap.containsKey(x)) {
        maxLength = Math.max(maxLength,i - hmap.get(x));
      }
      else {
        hmap.put(x, i);
      }

    }
    return maxLength;
  }

  public int findTheLongestSubstring(String s) {
    int res = 0, cur = 0, n = s.length();
    HashMap<Integer, Integer> seen = new HashMap<>();
    seen.put(0, -1);
    for (int i = 0; i < n; ++i) {
      int pCurr = 1 << ("aeiou".indexOf(s.charAt(i)) + 1) >> 1;
      cur ^= pCurr;
      seen.putIfAbsent(cur, i);
      res = Math.max(res, i - seen.get(cur));
    }
    return res;
  }

  public int findTheLongestSubstring_try_this(String s) {

    int[] chars = new int[26];
    chars['a' - 'a'] = 1 << 0;
    chars['e' - 'a'] = 1 << 1;
    chars['i' - 'a'] = 1 << 2;
    chars['o' - 'a'] = 1 << 3;
    chars['u' - 'a'] = 1 << 4;

    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);

    int mask = 0;
    int res = 0;
    for (int i = 0; i < s.length(); i++) {
      mask ^= chars[s.charAt(i) - 'a'];
      if (map.containsKey(mask)) {
        res = Math.max(res, i - map.get(mask));
      } else {
        map.put(mask, i);
      }
    }
    return res;
  }


}
