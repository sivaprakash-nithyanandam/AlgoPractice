package ShortestPath;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 8/1/2021  1:42 AM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 */
public class DijkstraAlgorithm {

  public static void main(String[] args) {
    DijkstraAlgorithm o = new DijkstraAlgorithm();
    //System.out.println(o.dijkstra(3, new int[][]{{0,1,5},{0,2,4},{1,2,2}}));
    System.out.println(o.dijkstra(4, new int[][]{{0,1,5},{0,2,1},{2,4,4},{2,3,1},{3,4,-1}}));
  }

  public int dijkstra(int n, int[][] edges) {

    Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
    for (int[] edge : edges) {
      graph.computeIfAbsent(edge[0], x -> new HashMap<>()).put(edge[1], edge[2]);
      //graph.computeIfAbsent(edge[1], x -> new HashMap<>()).put(edge[0], edge[2]);
    }
    int[] dist = new int[n+1];
    Arrays.fill(dist, (int)1E9);
    dist[0] = 0;
    Queue<int[]> q = new PriorityQueue<>((a,b) -> a[1] - b[1]);
    q.add(new int[]{0, 0});

    while (!q.isEmpty()) {
      int[] node = q.poll();
      if (graph.get(node[0]) == null) {
        continue;
      }
      for (Map.Entry<Integer, Integer> adj : graph.get(node[0]).entrySet()) {
        int d = dist[node[0]] + adj.getValue();
        if (dist[adj.getKey()] > d) {
          dist[adj.getKey()] = d;
          q.offer(new int[]{adj.getKey(), d});
        }
      }
    }
    return dist[4];
  }
}
