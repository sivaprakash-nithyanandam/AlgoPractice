package MathAndLogic;
/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

/**
 *
 */
public class NumberofIslands {

    public static void main(String[] args) {
    NumberofIslands o = new NumberofIslands();
    char[][] grid = {
  {'1','1','1','1','0'},
  {'1','1','0','1','0'},
  {'1','1','0','0','0'},
  {'0','0','0','0','0'}
};
    System.out.println(o.numIslands_iterative(grid));
  }

  public int numIslands_iterative(char[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }

    int nr = grid.length;
    int nc = grid[0].length;
    int num_islands = 0;

    for (int r = 0; r < nr; ++r) {
      for (int c = 0; c < nc; ++c) {
        if (grid[r][c] == '1') {
          ++num_islands;
          grid[r][c] = '0'; // mark as visited
          Queue<Integer> neighbors = new LinkedList<>();
          neighbors.add(r * nc + c);
          while (!neighbors.isEmpty()) {
            int id = neighbors.remove();
            int row = id / nc;
            int col = id % nc;
            if (row - 1 >= 0 && grid[row-1][col] == '1') {
              neighbors.add((row-1) * nc + col);
              grid[row-1][col] = '0';
            }
            if (row + 1 < nr && grid[row+1][col] == '1') {
              neighbors.add((row+1) * nc + col);
              grid[row+1][col] = '0';
            }
            if (col - 1 >= 0 && grid[row][col-1] == '1') {
              neighbors.add(row * nc + col-1);
              grid[row][col-1] = '0';
            }
            if (col + 1 < nc && grid[row][col+1] == '1') {
              neighbors.add(row * nc + col+1);
              grid[row][col+1] = '0';
            }
          }
        }
      }
    }

    return num_islands;
  }

  void dfs(char[][] grid, int r, int c) {
    int nr = grid.length;
    int nc = grid[0].length;

    if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
      return;
    }

    grid[r][c] = '0';
    dfs(grid, r - 1, c);
    dfs(grid, r + 1, c);
    dfs(grid, r, c - 1);
    dfs(grid, r, c + 1);
  }

  public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }

    int nr = grid.length;
    int nc = grid[0].length;
    int num_islands = 0;
    for (int r = 0; r < nr; ++r) {
      for (int c = 0; c < nc; ++c) {
        if (grid[r][c] == '1') {
          ++num_islands;
          dfs(grid, r, c);
        }
      }
    }

    return num_islands;
  }

  class UnionFind {
    int[] parent;

    UnionFind(int size) {
      parent = new int[size];
      IntStream.range(0, size).forEach(i -> parent[i] = i);
    }

    private int union(int node1, int node2) {
      int pn1 = find(node1);
      int pn2 = find(node2);

      if (pn1 != pn2) {
        if (pn1 < pn2) {
          parent[pn2] = pn1;
        } else {
          parent[pn1] = pn2;
        }
        return -1;
      }
      return 0;
    }

    private int find(int node) {
      if (parent[node] == node) {
        return node;
      }
      return find(parent[node]);
    }
  }

  public int numIslands_unionfind_v2(char[][] grid) {

    int m = grid.length;
    int n = grid[0].length;

    UnionFind uf = new UnionFind(m*n);

    int islands = m * n;

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == '1') {

          int idx = i * n + j;

          if (i > 0 && grid[i-1][j] == '1') {
            islands += uf.union(idx, (i-1) * n + j);
          }
          if (j > 0 && grid[i][j-1] == '1') {
            islands += uf.union(idx, i * n + (j-1));
          }
        } else {
          islands--;
        }
      }
    }
    return islands;
  }

  int[] par;

  public int numIslands_unionfind_v1(char[][] a) {
    if(a.length==0) return 0;

    int n = a.length, m=a[0].length;
    par = new int[m*n];
    Arrays.fill(par, -1);

    for(int i=0;i<n;i++){
      for(int j=0;j<m;j++){

        if(a[i][j]=='1'){
          par[i*m+j]=i*m+j; // note, that `par` was filled witn -1 values
          if(i>0 && a[i-1][j]=='1') union(i*m+j, (i-1)*m+j); // union current+top
          if(j>0 && a[i][j-1]=='1') union(i*m+j, i*m+(j-1)); // union current+left
        }

      }
    }

    Set<Integer> set = new HashSet<>();
    for(int k=0;k<par.length;k++){
      if(par[k]!=-1) set.add(find(k));
    }
    return set.size();
  }

  int find(int x){
    if(par[x]==x) return x;
    par[x]=find(par[x]);
    return par[x];
  }

  void union(int x, int y){
    int px = find(x);
    int py = find(y);
    par[px]=par[py];
  }
}
