package TreesAndGraphs;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/3/2021  10:18 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

/**
 * https://leetcode.com/explore/interview/card/google/61/trees-and-graphs/3070/
 * <p>
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You
 * are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take
 * course bi first if you want to take course ai.
 * <p>
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * Return the ordering of courses you should take to finish all courses. If there are many valid
 * answers, return any of them. If it is impossible to finish all courses, return an empty array.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: numCourses = 2, prerequisites = [[1,0]] Output: [0,1] Explanation: There are a total of 2
 * courses to take. To take course 1 you should have finished course 0. So the correct course order
 * is [0,1]. Example 2:
 * <p>
 * Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]] Output: [0,2,1,3] Explanation:
 * There are a total of 4 courses to take. To take course 3 you should have finished both courses 1
 * and 2. Both courses 1 and 2 should be taken after you finished course 0. So one correct course
 * order is [0,1,2,3]. Another correct ordering is [0,2,1,3]. Example 3:
 * <p>
 * Input: numCourses = 1, prerequisites = [] Output: [0]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= numCourses <= 2000 0 <= prerequisites.length <= numCourses * (numCourses - 1)
 * prerequisites[i].length == 2 0 <= ai, bi < numCourses ai != bi All the pairs [ai, bi] are
 * distinct.
 */
public class CourseScheduleII {

  public static void main(String[] args) {
    CourseScheduleII o = new CourseScheduleII();
    System.out.println(Arrays.toString(o.findOrder(2, new int[][]{{1,0},{1,2},{0,1}})));
    System.out.println(Arrays.toString(o.findOrder(2, new int[][]{})));
    System.out.println(Arrays.toString(o.findOrder(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})));
  }

  //Topological Sorting
  public int[] findOrder(int n, int[][] pres) {
    int idx = 0;
    int[] res = new int[n], degrees = new int[n];

    HashMap<Integer, List<Integer>> map = new HashMap<>();
    for (int i = 0; i < n; i++) {
      map.put(i, new ArrayList<>());
    }

    for (int[] p : pres) {
      degrees[p[0]]++;
      map.get(p[1]).add(p[0]);
    }

    Deque<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
      if (degrees[i] == 0) {
        queue.offerLast(i);
      }
    }

    while (!queue.isEmpty()) {
      int p = queue.pollFirst();
      res[idx++] = p;
      for (int c : map.get(p)) {
        degrees[c]--;
        if (degrees[c] == 0) {
          queue.offerLast(c);
        }
      }
    }

    return idx == n ? res : new int[0];
  }
}
