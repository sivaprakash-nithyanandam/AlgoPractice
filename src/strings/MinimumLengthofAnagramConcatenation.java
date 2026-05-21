package strings;
/**
 * @time 5/5/24-9:24 AM
 * @author sivaprakashnithyanandam
 */

/**
 * https://leetcode.com/problems/minimum-length-of-anagram-concatenation/description/
 *
 * You are given a string s, which is known to be a concatenation of anagrams of some string t.
 *
 * Return the minimum possible length of the string t.
 *
 * An anagram is formed by rearranging the letters of a string. For example, "aab", "aba", and, "baa" are anagrams of "aab".
 *
 *
 *
 * Example 1:
 *
 * Input: s = "abba"
 *
 * Output: 2
 *
 * Explanation:
 *
 * One possible string t could be "ba".
 *
 * Example 2:
 *
 * Input: s = "cdef"
 *
 * Output: 4
 *
 * Explanation:
 *
 * One possible string t could be "cdef", notice that t can be equal to s.
 *
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 105
 * s consist only of lowercase English letters.
 */
public class MinimumLengthofAnagramConcatenation {

    public static void main(String[] args) {
        MinimumLengthofAnagramConcatenation o = new MinimumLengthofAnagramConcatenation();
        System.out.println(o.minAnagramLength("abcccba"));
        System.out.println(o.minAnagramLength("abba"));
    }

    public int gcd(int x,int y){
        if(y==0) {
            return x;
        }
        else {
            return gcd(y,x%y);
        }
    }
    public int minAnagramLength(String s) {
        int[] cnt=new int[26];
        for(int i = 0;i < s.length();i++) {
            cnt[s.charAt(i)-'a']++;
        }
        int g=0;
        for(int i=0;i<26;i++) {
            g = gcd(g,cnt[i]);
        }
        return s.length()/g;
    }
}
