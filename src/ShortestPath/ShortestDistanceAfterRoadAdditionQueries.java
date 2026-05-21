package ShortestPath;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Aug 04,2024 - 12:39 AM
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * https://leetcode.com/problems/shortest-distance-after-road-addition-queries-ii/description/
 *
 * You are given an integer n and a 2D integer array queries.
 *
 * There are n cities numbered from 0 to n - 1. Initially, there is a unidirectional road from city i to city i + 1 for all 0 <= i < n - 1.
 *
 * queries[i] = [ui, vi] represents the addition of a new unidirectional road from city ui to city vi. After each query, you need to find the length of the shortest path from city 0 to city n - 1.
 *
 * There are no two queries such that queries[i][0] < queries[j][0] < queries[i][1] < queries[j][1].
 *
 * Return an array answer where for each i in the range [0, queries.length - 1], answer[i] is the length of the shortest path from city 0 to city n - 1 after processing the first i + 1 queries.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 5, queries = [[2,4],[0,2],[0,4]]
 *
 * Output: [3,2,1]
 *
 * Explanation:
 *
 *
 *
 * After the addition of the road from 2 to 4, the length of the shortest path from 0 to 4 is 3.
 *
 *
 *
 * After the addition of the road from 0 to 2, the length of the shortest path from 0 to 4 is 2.
 *
 *
 *
 * After the addition of the road from 0 to 4, the length of the shortest path from 0 to 4 is 1.
 *
 * Example 2:
 *
 * Input: n = 4, queries = [[0,3],[0,2]]
 *
 * Output: [1,1]
 *
 * Explanation:
 *
 *
 *
 * After the addition of the road from 0 to 3, the length of the shortest path from 0 to 3 is 1.
 *
 *
 *
 * After the addition of the road from 0 to 2, the length of the shortest path remains 1.
 *
 *
 *
 * Constraints:
 *
 * 3 <= n <= 105
 * 1 <= queries.length <= 105
 * queries[i].length == 2
 * 0 <= queries[i][0] < queries[i][1] < n
 * 1 < queries[i][1] - queries[i][0]
 * There are no repeated roads among the queries.
 * There are no two queries such that i != j and queries[i][0] < queries[j][0] < queries[i][1] < queries[j][1].
 */

public class ShortestDistanceAfterRoadAdditionQueries {

    public static void main(String[] args) {
        ShortestDistanceAfterRoadAdditionQueries o = new ShortestDistanceAfterRoadAdditionQueries();
        System.out.println(o.shortestDistanceAfterQueries_huge_input(5, new int[][]{{0,3},{2,4}}));
        System.out.println(o.shortestDistanceAfterQueries_II_v1(5, new int[][]{{2,4},{0,2},{0,4}}));
    }

    //ShortestDistanceAfterRoadAdditionQueries II
    public int[] shortestDistanceAfterQueries_huge_input(int n, int[][] queries) {
        int[] result = new int[queries.length];
        TreeMap<Integer, Integer> map = new TreeMap<>();

        for (int i = 0, k = n - 1; i < queries.length; i++) {

            Map.Entry<Integer, Integer> entry = map.floorEntry(queries[i][0]);

            if (entry == null || entry.getValue() < queries[i][1]) {
                for (; (entry = map.ceilingEntry(queries[i][0])) != null && entry.getValue() <= queries[i][1]; map.remove(entry.getKey())) {
                    k += entry.getValue() - entry.getKey() - 1;
                }
                map.put(queries[i][0], queries[i][1]);
                k -= queries[i][1] - queries[i][0] - 1;
            }
            result[i] = k;
        }
        return result;
    }

    public int[] shortestDistanceAfterQueries_II_v1(int n, int[][] queries) {
        TreeSet<Integer> onShortestPath = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            onShortestPath.add(i);
        }

        int q = queries.length;
        int[] out = new int[q];
        for (int i = 0; i < q; i++) {
            int from = queries[i][0];
            int to = queries[i][1];
            Integer higher = onShortestPath.higher(from);
            while (higher != null && higher < to) {
                onShortestPath.remove(higher);
                higher = onShortestPath.higher(from);
            }
            out[i] = onShortestPath.size() - 1;
        }
        return out;
    }

    //ShortestDistanceAfterRoadAdditionQueries I
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        ArrayList<Integer>[] list = new ArrayList[n - 1];
        int[] d = new int[n], result = new int[queries.length];
        for (int i = 1; i < n; i++) {
            list[i - 1] = new ArrayList<>(List.of(i));
            d[i] = i;
        }
        for (int i = 0, k; i < queries.length; i++) {
            list[queries[i][0]].add(queries[i][1]);
            ArrayDeque<Integer> queue = new ArrayDeque<>(List.of(queries[i][0]));
            while (!queue.isEmpty()) {
                if ((k = queue.poll()) == n - 1) {
                    break;
                }
                for (int j : list[k]) {
                    if (d[k] + 1 < d[j]) {
                        d[j] = d[k] + 1;
                        queue.offer(j);
                    }
                }
            }
            result[i] = d[n - 1];
        }
        return result;
    }

}
