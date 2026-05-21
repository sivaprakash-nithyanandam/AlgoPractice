package disjointset_unionfind;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/4/2021  9:15 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/explore/interview/card/google/61/trees-and-graphs/3076/
 *
 * On a 2D plane, we place n stones at some integer coordinate points. Each coordinate point may have at most one stone.
 *
 * A stone can be removed if it shares either the same row or the same column as another stone that has not been removed.
 *
 * Given an array stones of length n where stones[i] = [xi, yi] represents the location of the ith stone,
 * return the largest possible number of stones that can be removed.
 *
 *
 *
 * Example 1:
 *
 * Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
 * Output: 5
 * Explanation: One way to remove 5 stones is as follows:
 * 1. Remove stone [2,2] because it shares the same row as [2,1].
 * 2. Remove stone [2,1] because it shares the same column as [0,1].
 * 3. Remove stone [1,2] because it shares the same row as [1,0].
 * 4. Remove stone [1,0] because it shares the same column as [0,0].
 * 5. Remove stone [0,1] because it shares the same row as [0,0].
 * Stone [0,0] cannot be removed since it does not share a row/column with another stone still on the plane.
 * Example 2:
 *
 * Input: stones = [[0,0],[0,2],[1,1],[2,0],[2,2]]
 * Output: 3
 * Explanation: One way to make 3 moves is as follows:
 * 1. Remove stone [2,2] because it shares the same row as [2,0].
 * 2. Remove stone [2,0] because it shares the same column as [0,0].
 * 3. Remove stone [0,2] because it shares the same row as [0,0].
 * Stones [0,0] and [1,1] cannot be removed since they do not share a row/column with another stone still on the plane.
 * Example 3:
 *
 * Input: stones = [[0,0]]
 * Output: 0
 * Explanation: [0,0] is the only stone on the plane, so you cannot remove it.
 *
 *
 * Constraints:
 *
 * 1 <= stones.length <= 1000
 * 0 <= xi, yi <= 104
 * No two stones are at the same coordinate point.
 */
public class MostStonesRemovedwithSameRoworColumn {

  public static void main(String[] args) {
    MostStonesRemovedwithSameRoworColumn o = new MostStonesRemovedwithSameRoworColumn();
    System.out.println(o.removeStones_own(new int[][]{{0,0},{0,1},{1,0},{1,2},{1,1}}));
    System.out.println(o.removeStones_faster(new int[][]{{0,3},{2,3}}));
    System.out.println(o.removeStones(new int[][]{{0,1},{0,2},{4,3},{2,4},{0,3},{1,1}}));
    System.out.println(o.removeStones(new int[][]{{0,1},{1,1}}));
  }

  class UnionFind {
    int[] parent;
    int[] rank;

    UnionFind(int size) {
      this.parent = new int[size];
      this.rank = new int[size];

      IntStream.range(0, size).forEach(i -> parent[i] = i);
    }

    private int find(int node) {
      if (parent[node] == node) {
        return parent[node];
      }
      return find(parent[node]);
    }

    private int union(int node1, int node2) {
      int pn1 = find(node1);
      int pn2 = find(node2);

      if (pn1 == pn2) {
        return 0;
      } else {
        if (rank[pn2] > rank[pn1]) {
          parent[pn1] = pn2;
          rank[pn2]++;
        } else {
          parent[pn2] = pn1;
          rank[pn1]++;
        }
        return 1;
      }
    }
  }

  public int removeStones_own(int[][] stones) {

    int n = stones.length;
    UnionFind uf = new UnionFind(n+1);

    int[] rows = new int[10001];
    int[] cols = new int[10001];
    int islands = n;

    for (int i = 1; i <= n; i++) {
      int row = stones[i-1][0];
      int col = stones[i-1][1];

      if (rows[row] == 0) {
        rows[row] = i;
      } else {
        islands -= uf.union(rows[row], i);
      }

      if (cols[col] == 0) {
        cols[col] = i;
      } else {
        islands -= uf.union(cols[col], i);
      }
    }
    return n - islands;
  }

  public int removeStones_faster(int[][] stones) {
    int len = stones.length;
    int[] dsu = new int[len+1];
    Arrays.fill(dsu, -1);

    int[] rows = new int[10001];
    int[] cols = new int[10001];

    int islands = len;
    for (int i = 1; i <= len; i++) {
      int row = stones[i-1][0];
      int col = stones[i-1][1];

      // if row has not been assigned
      if (rows[row] == 0) {
        rows[row] = i;
      } else {
        if (union(dsu, rows[row], i)) {
          islands--;
        }
      }

      if (cols[col] == 0) {
        cols[col] = i;
      } else {
        if (union(dsu, cols[col], i)) {
          islands--;
        }
      }
    }

    return len-islands;
  }

  public boolean union(int[] dsu, int a, int b) {
    int r1 = getRoot(dsu, a);
    int r2 = getRoot(dsu, b);

    if (r1 == r2) {
      return false;
    }

    if (r1 < r2) {
      dsu[r1] += dsu[r2];
      dsu[r2] = r1;
    } else {
      dsu[r2] += dsu[r1];
      dsu[r1] = r2;
    }

    return true;
  }

  public int getRoot(int[] dsu, int a) {
    int n = a;
    while(dsu[n] >= 0) {
      n = dsu[n];
    }

    if (n != a) {
      dsu[a] = n;
    }

    return n;
  }

  int unvisited = 0;
  public int removeStones(int[][] stones) {
    unvisited = 0;
    Map<Integer, Integer> grid = new HashMap<Integer, Integer>();

    for (int[] stone : stones) {
      union(grid, stone[0], ~stone[1]);
    }
    return stones.length - unvisited;
  }

  private void union(Map<Integer, Integer> grid, int x, int y) {
    int nx = find(grid, x);
    int ny = find(grid, y);

    if (nx != ny) {
      unvisited--;
      grid.put(nx, ny);
    } else {
      System.out.println("Note here");
    }
  }

  private int find(Map<Integer, Integer> grid, int z) {
    if (!grid.containsKey(z)) {
      grid.put(z, z);
      unvisited++;
    }
    if (grid.get(z) != z) {
      grid.put(z, find(grid, grid.get(z)));
    }
    return grid.get(z);
  }
}
