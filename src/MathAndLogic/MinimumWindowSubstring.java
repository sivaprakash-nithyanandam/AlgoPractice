package MathAndLogic;


/**
 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
 * Example:
 * Input: S = "ADOBECODEBANC", T = "ABC"
 * Output: "BANC"
 * Note:
 * If there is no such window in S that covers all characters in T, return the empty string "".
 * If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
 */

/**
 * Idea is to use sliding window technique. Move left and right cursor according to matches found.
 * Optimized solution moves time to 1ms from 4ms.
 */
public class MinimumWindowSubstring {

  public static void main(String[] args) {
    MinimumWindowSubstring mws = new MinimumWindowSubstring();
    System.out.println(mws.minWindow_own("ab", "b"));
    System.out.println(mws.minWindow_own("OOADOBECODEBANC", "ABC"));
    System.out.println(mws.minWindow_own("ab", "b"));
    System.out.println(mws.minWindow("ADOBECODEBANC", "ABC"));
    System.out.println(mws.minWindow("ADOBECODEBANC", "ABC"));
    System.out.println(mws.minWindow_optimized("ab", "b"));
  }

  public String minWindow_own(String s, String t) {
    int[] tArr = new int[128];
    for (int i = 0; i < t.length(); i++) {
      tArr[t.charAt(i)]++;
    }

    char[] sArr = s.toCharArray();
    int i = 0, len = s.length()+1, j = 0, start = -1;
    int count = t.length();

    while (i < s.length()) {
      if (tArr[sArr[i++]]-- > 0) {
        count--;
      }
      if (count == 0) {
        if (i - j < len) {
          len = i - j;
          start = j;
        }
      }
      while(count == 0 || (j < i && tArr[sArr[j]] < 0)) {
        if (++tArr[sArr[j]] > 0) {
          count++;
        }
        j++;
      }

    }
    return start == -1 ? "" : s.substring(start, start+len);
  }

  public String minWindow_optimized(String s, String t) {
    int[] counter = new int[256];
    for (char c : t.toCharArray()) {
      counter[c]++;
    }
    char[] sArr = s.toCharArray();
    int target = t.length(), len = s.length()+1, start = -1, i = 0, j = 0;
    while (j < sArr.length) {
      if (counter[sArr[j++]]-- > 0){
        target--;
      }
      if (target == 0
              && j - i < len) {
        len = j - i;
        start = i;
      }
      while(i < j && (target == 0 || counter[sArr[i]] < 0)) {
        if (counter[sArr[i++]]++ == 0) {
          target++;
        }
      }
    }
    return start == -1 ? "" : s.substring(start, start + len);
  }

  public String minWindow(String s, String t) {
    int[] strArr = new int[256];
    int[] patArr = new int[256];
    for (int i = 0; i < t.length(); i++) {
      patArr[t.charAt(i)]++;
    }
    int count = t.length();
    int start = 0;
    int startIndex = -1;
    int minLength = Integer.MAX_VALUE;
    for (int i = start; i < s.length(); i++) {
      char c = s.charAt(i);
      strArr[c]++;
      if (patArr[c] == 0) {
        continue;
      }
      if (patArr[c] * strArr[c] >= strArr[c] * strArr[c]) {
        count--;
      }
      if (count == 0) {
        while(strArr[s.charAt(start)] > patArr[s.charAt(start)]) {
          strArr[s.charAt(start)]--;
          start++;
        }
        if (minLength > i - start + 1) {
          minLength = i - start + 1;
          startIndex = start;
        }
      }
    }
    if (minLength == Integer.MAX_VALUE) {
      return "";
    } else {
      return s.substring(startIndex, startIndex+minLength);
    }
  }

  // Java program to find smallest window containing
// all characters of a pattern.

    static final int no_of_chars = 256;

    // Function to find smallest window containing
    // all characters of 'pat'
    static String findSubString(String str, String pat)
    {
      int len1 = str.length();
      int len2 = pat.length();

      // check if string's length is less than pattern's
      // length. If yes then no such window can exist
      if (len1 < len2)
      {
        System.out.println("No such window exists");
        return "";
      }

      int hash_pat[] = new int[no_of_chars];
      int hash_str[] = new int[no_of_chars];

      // store occurrence ofs characters of pattern
      for (int i = 0; i < len2; i++)
        hash_pat[pat.charAt(i)]++;

      int start = 0, start_index = -1, min_len = Integer.MAX_VALUE;

      // start traversing the string
      int count = 0; // count of characters
      for (int j = 0; j < len1 ; j++)
      {
        // count occurrence of characters of string
        hash_str[str.charAt(j)]++;

        // If string's char matches with pattern's char
        // then increment count
        if (hash_pat[str.charAt(j)] != 0 &&
                hash_str[str.charAt(j)] <= hash_pat[str.charAt(j)] )
          count++;

        // if all the characters are matched
        if (count == len2)
        {
          // Try to minimize the window i.e., check if
          // any character is occurring more no. of times
          // than its occurrence in pattern, if yes
          // then remove it from starting and also remove
          // the useless characters.
          while ( hash_str[str.charAt(start)] > hash_pat[str.charAt(start)]
                  || hash_pat[str.charAt(start)] == 0)
          {

            if (hash_str[str.charAt(start)] > hash_pat[str.charAt(start)])
              hash_str[str.charAt(start)]--;
            start++;
          }

          // update window size
          int len_window = j - start + 1;
          if (min_len > len_window)
          {
            min_len = len_window;
            start_index = start;
          }
        }
      }

      // If no window found
      if (start_index == -1)
      {
        System.out.println("No such window exists");
        return "";
      }

      // Return substring starting from start_index
      // and length min_len
      return str.substring(start_index, start_index + min_len);
    }

}
