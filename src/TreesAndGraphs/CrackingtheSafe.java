package TreesAndGraphs;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/4/2021  6:14 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/explore/interview/card/google/61/trees-and-graphs/3075/
 *
 * There is a box protected by a password. The password is a sequence of n digits where each digit can be in the range [0, k - 1].
 *
 * While entering a password, the last n digits entered will automatically be matched against the correct password.
 *
 * For example, assuming the correct password is "345", if you type "012345", the box will open because the correct password matches the suffix of the entered password.
 * Return any password of minimum length that is guaranteed to open the box at some point of entering it.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 1, k = 2
 * Output: "10"
 * Explanation: "01" will be accepted too.
 * Example 2:
 *
 * Input: n = 2, k = 2
 * Output: "01100"
 * Explanation: "01100", "10011", "11001" will be accepted too.
 *
 *
 * Constraints:
 *
 * 1 <= n <= 4
 * 1 <= k <= 10
 * 1 <= kn <= 4096
 */
public class CrackingtheSafe {

  public static void main(String[] args) {
    CrackingtheSafe o = new CrackingtheSafe();
    System.out.println(o.crackSafe(2, 3));
    System.out.println(o.crackSafe(3, 4));
  }

  public String crackSafe(int n, int k) {
    StringBuilder sb = new StringBuilder();
    int total = (int) (Math.pow(k, n));
    for (int i = 0; i < n; i++) {
      sb.append('0');
    }

    Set<String> visited = new HashSet<>();
    visited.add(sb.toString());

    dfs(sb, total, visited, n, k);

    return sb.toString();
  }

  private boolean dfs(StringBuilder sb, int goal, Set<String> visited, int n, int k) {
    if (visited.size() == goal) {
      return true;
    }
    String prev = sb.substring(sb.length() - n + 1, sb.length());
    for (int i = 0; i < k; i++) {
      String next = prev + i;
      if (!visited.contains(next)) {
        visited.add(next);
        sb.append(i);
        if (dfs(sb, goal, visited, n, k)) {
          return true;
        } else {
          visited.remove(next);
          sb.delete(sb.length() - 1, sb.length());
        }
      }
    }
    return false;
  }
}
