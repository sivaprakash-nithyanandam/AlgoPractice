package ShortestPath;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 8/1/2021  12:40 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.*;

/**
 * https://leetcode.com/problems/network-delay-time/
 *
 * You are given a network of n nodes, labeled from 1 to n. You are also given times,
 * a list of travel times as directed edges times[i] = (ui, vi, wi), where ui is the source node,
 * vi is the target node, and wi is the time it takes for a signal to travel from source to target.
 *
 * We will send a signal from a given node k. Return the time it takes for all the n nodes to receive the signal. If it is impossible for all the n nodes to receive the signal, return -1.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
 * Output: 2
 * Example 2:
 *
 * Input: times = [[1,2,1]], n = 2, k = 1
 * Output: 1
 * Example 3:
 *
 * Input: times = [[1,2,1]], n = 2, k = 2
 * Output: -1
 *
 *
 * Constraints:
 *
 * 1 <= k <= n <= 100
 * 1 <= times.length <= 6000
 * times[i].length == 3
 * 1 <= ui, vi <= n
 * ui != vi
 * 0 <= wi <= 100
 * All the pairs (ui, vi) are unique. (i.e., no multiple edges.)
 */
public class NetworkDelayTime {

  public static void main(String[] args) {
    NetworkDelayTime o = new NetworkDelayTime();
    System.out.println(o.networkDelayTime_dijikstra(new int[][]{{2,1,1}, {2,3,1}, {3,4,1}}, 4, 2));
    System.out.println(o.networkDelayTime_bellman_ford(new int[][]{{2,1,1}, {2,3,1}, {3,4,1}}, 4, 2));
    System.out.println(o.networkDelayTime_floyd_warshall_algo(new int[][]{{2,1,1}, {2,3,1}, {3,4,1}}, 4, 2));
  }

  public int networkDelayTime_floyd_warshall_algo(int[][] times, int n, int k) {

    int[][] graph = new int[n+1][n+1];

    for (int i = 1; i <= n; i++) {
      Arrays.fill(graph[i], (int)1e6);
    }
    for (int i = 1; i <= n; i++) {
      graph[i][i] = 0;
    }
    for (int[] time : times) {
      graph[time[0]][time[1]] = time[2];
    }

    for (int s = 1; s <= n; s++) {
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
          int d = graph[i][s] + graph[s][j];
          if (graph[i][j] > d) {
            graph[i][j] = d;
          }
        }
      }
    }

    int res = 0;
    for (int i = 1; i <= n; i++) {
      if (graph[k][i] == (int)1e6) {
        return -1;
      }
      if (graph[k][i] > res) {
        res = graph[k][i];
      }
    }
    return res;
  }

  public int networkDelayTime_bellman_ford(int[][] times, int n, int k) {
    int[] dist = new int[n+1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    boolean isChanged = true;
    int i = 1;

    while (isChanged && i < n) {
      isChanged = false;
      for (int[] time : times) {
        if (dist[time[0]] != Integer.MAX_VALUE && dist[time[0]] + time[2] < dist[time[1]]) {
          dist[time[1]] = dist[time[0]] + time[2];
          isChanged = true;
        }
      }
      i++;
    }

    int res = 0;
    for (int j = 1; j <= n; j++) {
      if (j == k) {
        continue;
      }
      if (dist[j] == Integer.MAX_VALUE) {
        return -1;
      }
      if (dist[j] > res) {
        res = dist[j];
      }
    }
    return res;
  }

  public int networkDelayTime_dijikstra(int[][] times, int n, int k) {
    Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();

    for (int[] time : times) {
      graph.computeIfAbsent(time[0], x -> new HashMap<>()).put(time[1], time[2]);
    }

    Queue<int[]> q = new PriorityQueue<int[]>((a,b) -> a[1] - b[1]);
    q.offer(new int[]{k, 0});
    int[] dist = new int[n+1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;

    while (!q.isEmpty()) {
      int[] curr = q.poll();
      if (graph.get(curr[0]) == null) {
        continue;
      }

      for (Map.Entry<Integer, Integer> adj : graph.get(curr[0]).entrySet()) {
        int d = dist[curr[0]] + adj.getValue();
        if (dist[adj.getKey()] > d) {
          dist[adj.getKey()] = d;
          q.offer(new int[]{adj.getKey(), d});
        }
      }
    }

    int res = 0;
    for (int i = 1; i <= n; i++) {
      if (i == k) {
        continue;
      }
      if (dist[i] == Integer.MAX_VALUE) {
        return -1;
      }
      if (dist[i] > res) {
        res = dist[i];
      }
    }
    return res;
  }

  public int networkDelayTime_DFS(int[][] times, int n, int k) {

    Map<Integer, Map<Integer, Integer>> adjList = new HashMap<>();

    for (int[] time : times) {
      Map<Integer, Integer> adjNode = adjList.get(time[0]);
      if (adjNode == null) {
        adjNode = new HashMap<>();
      }
      adjNode.put(time[1], time[2]);
      adjList.put(time[0], adjNode);
    }

    int[] signalReceived = new int[n+1];
    Arrays.fill(signalReceived, Integer.MAX_VALUE);

    DFS(adjList, signalReceived, k, 0);

    int res = 0;
    for (int i = 1; i <= n; i++) {
      if (signalReceived[i] == Integer.MAX_VALUE) {
        return -1;
      }
      if (signalReceived[i] > res) {
        res = signalReceived[i];
      }
    }
    return res;
  }

  private void DFS(Map<Integer, Map<Integer, Integer>> adjList, int[] signalRec, int p, int currTime) {

    if (currTime >= signalRec[p]) {
      return;
    }
    signalRec[p] = currTime;

    if (!adjList.containsKey(p)) {
      return;
    }

    for (Map.Entry<Integer, Integer> entry : adjList.get(p).entrySet()) {
      DFS(adjList, signalRec, entry.getKey(), currTime + entry.getValue());
    }
  }

  public int networkDelayTime_BFS(int[][] times, int n, int k) {

    Map<Integer, Map<Integer, Integer>> adjList = new HashMap<>();

    for (int[] time : times) {
      adjList.computeIfAbsent(time[0], x -> new HashMap<>()).put(time[1], time[2]);
    }

    int[] signalReceived = new int[n+1];
    Arrays.fill(signalReceived, Integer.MAX_VALUE);

    BFS(adjList, signalReceived, k);

    int res = 0;
    for (int i = 1; i <= n; i++) {
      if (signalReceived[i] == Integer.MAX_VALUE) {
        return -1;
      }
      if (signalReceived[i] > res) {
        res = signalReceived[i];
      }
    }
    return res;
  }

  private void BFS(Map<Integer, Map<Integer, Integer>> adjList, int[] signalRec, int p) {

    Queue<int[]> q = new LinkedList<>();
    q.add(new int[]{p, 0});

    while (!q.isEmpty()) {
      int[] nodes = q.poll();
      if (nodes[1] < signalRec[nodes[0]]){
        signalRec[nodes[0]] = nodes[1];

        if (!adjList.containsKey(nodes[0])) {
          continue;
        }

        for (Map.Entry<Integer, Integer> entry : adjList.get(nodes[0]).entrySet()) {
          q.add(new int[]{entry.getKey(), nodes[1] + entry.getValue()});
        }
      }
    }
  }


}