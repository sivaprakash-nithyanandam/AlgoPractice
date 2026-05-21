package MathAndLogic;

/**
 * Given a string s, return the longest palindromic substring in s.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: s = "babad" Output: "bab" Note: "aba" is also a valid answer. Example 2:
 * <p>
 * Input: s = "cbbd" Output: "bb" Example 3:
 * <p>
 * Input: s = "a" Output: "a" Example 4:
 * <p>
 * Input: s = "ac" Output: "a"
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= s.length <= 1000 s consist of only digits and English letters (lower-case and/or
 * upper-case)
 */
public class LongestPalindromicSubstring {

  public static void main(String[] args) {
    LongestPalindromicSubstring o = new LongestPalindromicSubstring();
    System.out.println(o.longestPalindrome1("cbbd"));
    System.out.println(o.longestPalindrome1("babad"));
    System.out.println(o.longestPalindrome_faster("malayalam"));
    //System.out.println(o.longestPalindrome_faster("paapa"));
  }

  //manacher's algorithm
  //https://www.youtube.com/watch?v=IvakBbsAhUU
  public String longestPalindrome(String s) {
    int n = s.length();
    int nn = 2 * n + 1;
    char[] newStr = new char[nn];

    int k = 0;
    newStr[k++] = '#';
    for (char c : s.toCharArray()) {
      newStr[k++] = c;
      newStr[k++] = '#';
    }

    int[] p = new int[nn];
    int right = 0;
    int center = 0;
    int longestLen = 0;
    int longestCenter = 0;

    for (int i = 0; i < nn; i++) {
      int mirror = 2 * center - i;

      if (right > i) {
        p[i] = Math.min(p[mirror], right-i);
      }

      int f = i + (p[i] + 1);
      int b = i - (p[i] + 1);

      while (b >= 0 && f < nn && newStr[f] == newStr[b]) {
        f++;
        b--;
        p[i]++;
      }

      if (i + p[i] > right) {
        center = i;
        right = i + p[i];
      }

      if (p[i] > longestLen) {
        longestLen = p[i];
        longestCenter = i;
      }
    }

    String res = new String(newStr);
    return res.substring(longestCenter - longestLen, longestCenter + longestLen).replace("#", "");
  }

  public String longestPalindrome1(String s) {

    int i, j = 0, k;
    int start = 0, len = 1;

    char[] chars = s.toCharArray();
    while (j < chars.length) {
      k = j;
      i = j;
      while (j+1 < chars.length && chars[j] == chars[j+1]) {
        j++;
      }

      while (i > 0 && j < chars.length-1 && chars[i-1] == chars[j+1]) {
        i--;
        j++;
      }
      if (j-i+1 > len) {
        start = i;
        len = j - i + 1;
      }
      if (k == j) {
        j++;
      }
    }
    return s.substring(start, start+len);
  }



  int start = 0, end = 0;

  public String longestPalindrome_faster(String s) {
    if (s.length() < 2) {
      return s;
    }
    char[] c = s.toCharArray();
    longestPalindromeAt(c, 0);
    return s.substring(start, end + 1);
  }

  private void longestPalindromeAt(char[] c, int p) {
    int a = p;
    int b = p;
    int n = c.length;
    if (p == n - 1 || (n - p) < (end - start + 1)/2) {
      return;
    }
    while (b < n - 1 && c[b] == c[b + 1]) {
      b++;
    }
    p = b;
    while (a > 0 && b < n - 1 && c[a - 1] == c[b + 1]) {
      a--;
      b++;
    }
    if (b - a > end - start) {
      end = b;
      start = a;
    }
    longestPalindromeAt(c, p + 1);
  }

  public String longestPalindrome_v2(String s) {
    boolean[][] b = new boolean[s.length()][s.length()];

    for (int i = 0; i < s.length(); i++) {
      b[i][i] = true;
    }
    int start = 0, length = 0;
    for (int i = 1; i < s.length(); i++) {
      if (s.charAt(i - 1) == s.charAt(i)) {
        b[i - 1][i] = true;
        start = i - 1;
        length = 1;
      }
    }

    for (int ss = 2; ss < s.length(); ss++) {
      for (int i = 0; i < s.length() - ss; i++) {
        if (s.charAt(i) == s.charAt(i + ss) && b[i + 1][i + ss - 1]) {
          b[i][i + ss] = true;
          start = i;
          length = ss;
        }
      }
    }

    return s.substring(start, start + length + 1);
  }
}
