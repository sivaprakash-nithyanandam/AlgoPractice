package disjointset_unionfind;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 04,2024 - 9:24 AM
 */

/**
 * https://leetcode.com/problems/possible-bipartition/description/
 *
 * We want to split a group of n people (labeled from 1 to n) into two groups of any size. Each person may dislike some other people, and they should not go into the same group.
 *
 * Given the integer n and the array dislikes where dislikes[i] = [ai, bi] indicates that the person labeled ai does not like the person labeled bi, return true if it is possible to split everyone into two groups in this way.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 4, dislikes = [[1,2],[1,3],[2,4]]
 * Output: true
 * Explanation: The first group has [1,4], and the second group has [2,3].
 * Example 2:
 *
 * Input: n = 3, dislikes = [[1,2],[1,3],[2,3]]
 * Output: false
 * Explanation: We need at least 3 groups to divide them. We cannot put them in two groups.
 *
 *
 * Constraints:
 *
 * 1 <= n <= 2000
 * 0 <= dislikes.length <= 104
 * dislikes[i].length == 2
 * 1 <= ai < bi <= n
 * All the pairs of dislikes are unique.
 */
public class PossibleBipartition {

    public static void main(String[] args) {
        PossibleBipartition o = new PossibleBipartition();
        System.out.println();
    }

    public boolean possibleBipartition(int n, int[][] dislikes) {
        int len = dislikes.length;
        boolean[] check = new boolean[len];
        int[] arr = new int[n + 1];
        boolean foundNew;
        for(int i = 0; i < len; i++){
            if(check[i]) continue;
            arr[dislikes[i][0]] = 1;
            arr[dislikes[i][1]] = 2;
            foundNew = true;
            while(foundNew){
                foundNew = false;
                for(int j = i + 1; j < len; j++){
                    if(check[j]) continue;
                    int x = arr[dislikes[j][0]];
                    int y = arr[dislikes[j][1]];
                    if(x == 0 && y == 0) continue;
                    if(x > 0 && y > 0){
                        if((x - y) % 2 == 0) return false;
                    }
                    else if(x > 0) arr[dislikes[j][1]] = x + 1;
                    else arr[dislikes[j][0]] = y + 1;
                    foundNew = true;
                    check[j] = true;
                }
            }
        }
        return true;
    }
}
