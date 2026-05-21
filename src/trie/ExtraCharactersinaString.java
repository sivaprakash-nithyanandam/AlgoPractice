package trie;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 23,2024 - 9:43 AM
 */

/**
 * https://leetcode.com/problems/extra-characters-in-a-string/description/
 *
 * You are given a 0-indexed string s and a dictionary of words dictionary. You have to break s into one or more non-overlapping substrings such that each substring is present in dictionary. There may be some extra characters in s which are not present in any of the substrings.
 *
 * Return the minimum number of extra characters left over if you break up s optimally.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "leetscode", dictionary = ["leet","code","leetcode"]
 * Output: 1
 * Explanation: We can break s in two substrings: "leet" from index 0 to 3 and "code" from index 5 to 8. There is only 1 unused character (at index 4), so we return 1.
 *
 * Example 2:
 *
 * Input: s = "sayhelloworld", dictionary = ["hello","world"]
 * Output: 3
 * Explanation: We can break s in two substrings: "hello" from index 3 to 7 and "world" from index 8 to 12. The characters at indices 0, 1, 2 are not used in any substring and thus are considered as extra characters. Hence, we return 3.
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 50
 * 1 <= dictionary.length <= 50
 * 1 <= dictionary[i].length <= 50
 * dictionary[i] and s consists of only lowercase English letters
 * dictionary contains distinct words
 */
public class ExtraCharactersinaString {

    public static void main(String[] args) {
        ExtraCharactersinaString o = new ExtraCharactersinaString();
        System.out.println(o.minExtraChar("leetscode", new String[]{"leet","code","leetcode"}));
    }

    public int minExtraChar(String s, String[] dictionary) {

        Trie trie = new Trie();
        trie.addWords(dictionary);

        return helper(s, 0, trie);
    }

    private int helper(String s, int i, Trie trie) {
        if (i >= s.length()) {
            return i;
        }

        int skip = helper(s, i+1, trie);
        int maxReach = trie.match(s, i);
        if (maxReach > i) {
            maxReach = helper(s, maxReach+1, trie);
        }
        return Math.min(s.length()-maxReach, 1+skip);
    }

    class Trie {
        private Trie[] childs;
        private boolean isWord;

        public Trie() {
            childs = new Trie[26];
        }

        private int match(String s, int pos) {

            Trie curr = this;
            int maxReach = pos;

            for (int j = pos; j < s.length(); j++) {
                if (curr.childs[s.charAt(j)-'a'] == null) {
                    return maxReach;
                }
                curr = curr.childs[s.charAt(j)-'a'];
                if (curr.isWord) {
                    maxReach = j;
                }
            }
            return maxReach;
        }

        private void addWords(String[] words) {

            for (String word : words) {

                Trie curr = this;

                for (char c : word.toCharArray()) {
                    if (curr.childs[c-'a'] == null) {
                        curr.childs[c-'a'] = new Trie();
                    }
                    curr = curr.childs[c-'a'];
                }
                curr.isWord = true;
            }
        }
    }
}
