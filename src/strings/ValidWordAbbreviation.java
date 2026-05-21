package strings;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 02,2024 - 6:07 PM
 */

/**
 *
 * A string can be abbreviated by replacing any number of non-adjacent, non-empty substrings with their lengths. The lengths should not have leading zeros.
 *
 * For example, a string such as "substitution" could be abbreviated as (but not limited to):
 *
 * "s10n" ("s ubstitutio n")
 * "sub4u4" ("sub stit u tion")
 * "12" ("substitution")
 * "su3i1u2on" ("su bst i t u ti on")
 * "substitution" (no substrings replaced)
 * The following are not valid abbreviations:
 *
 * "s55n" ("s ubsti tutio n", the replaced substrings are adjacent)
 * "s010n" (has leading zeros)
 * "s0ubstitution" (replaces an empty substring)
 * Given a string word and an abbreviation abbr, return whether the string matches the given abbreviation.
 *
 * A substring is a contiguous non-empty sequence of characters within a string.
 *
 *
 *
 * Example 1:
 *
 * Input: word = "internationalization", abbr = "i12iz4n"
 * Output: true
 * Explanation: The word "internationalization" can be abbreviated as "i12iz4n" ("i nternational iz atio n").
 * Example 2:
 *
 * Input: word = "apple", abbr = "a2e"
 * Output: false
 * Explanation: The word "apple" cannot be abbreviated as "a2e".
 *
 *
 * Constraints:
 *
 * 1 <= word.length <= 20
 * word consists of only lowercase English letters.
 * 1 <= abbr.length <= 10
 * abbr consists of lowercase English letters and digits.
 * All the integers in abbr will fit in a 32-bit integer.
 */
public class ValidWordAbbreviation {

    public static void main(String[] args) {
        ValidWordAbbreviation o = new ValidWordAbbreviation();
        System.out.println(o.validWordAbbreviation("internationalization", "i12iz4n"));
        System.out.println(o.validWordAbbreviation("apple", "a2e"));
        System.out.println(o.validWordAbbreviation("substitution", "sub4u4"));
        System.out.println(o.validWordAbbreviation("substitution", "su3i1u2on"));
    }

    public boolean validWordAbbreviation(String word, String abbreviation) {
        int ai = 0, wi = 0;
        int abbLen = abbreviation.length();
        int wordLen = word.length();

        while (ai < abbLen && wi < wordLen) {
            int num = 0;
            while (ai < abbLen && Character.isDigit(abbreviation.charAt(ai))) {
                num = num * 10 + (abbreviation.charAt(ai) - '0');
                ai++;
            }

            if (num == 0) {
                if (abbreviation.charAt(ai) == word.charAt(wi)) {
                    ai++;
                    wi++;
                } else {
                    return false;
                }
            } else {
                wi += num;
            }
        }
        return ai == abbLen && wi == wordLen;
    }

}
