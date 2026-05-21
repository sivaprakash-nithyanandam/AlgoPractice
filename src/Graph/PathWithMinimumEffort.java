package Graph;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Dec 23,2022 - 2:19 PM
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode.com/explore/featured/card/graph/622/single-source-shortest-path-algorithm/3952/
 *
 * You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, where heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0), and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). You can move up, down, left, or right, and you wish to find a route that requires the minimum effort.
 *
 * A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.
 *
 * Return the minimum effort required to travel from the top-left cell to the bottom-right cell.
 *
 *
 *
 * Example 1:
 *
 *
 *
 * Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
 * Output: 2
 * Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
 * This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.
 * Example 2:
 *
 *
 *
 * Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
 * Output: 1
 * Explanation: The route of [1,2,3,4,5] has a maximum absolute difference of 1 in consecutive cells, which is better than route [1,3,5,3,5].
 * Example 3:
 *
 *
 * Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
 * Output: 0
 * Explanation: This route does not require any effort.
 *
 *
 * Constraints:
 *
 * rows == heights.length
 * columns == heights[i].length
 * 1 <= rows, columns <= 100
 * 1 <= heights[i][j] <= 106
 */
public class PathWithMinimumEffort {

    public static void main(String[] args) {
        PathWithMinimumEffort o = new PathWithMinimumEffort();
        System.out.println(o.minimumEffortPath(new int[][]{{1,2,2},{3,8,2},{5,3,5}}));
    }

    int[][] dirs = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};

    public int minimumEffortPath(int[][] heights) {
        int row = heights.length;
        int col = heights[0].length;
        if (row == 1 && col == 1) return 0;
        UnionFind unionFind = new UnionFind(heights);
        List<UnionFind.Edge> edgeList = unionFind.edgeList;
        Collections.sort(edgeList, (e1, e2) -> e1.difference - e2.difference);

        for (int i = 0; i < edgeList.size(); i++) {
            int x = edgeList.get(i).x;
            int y = edgeList.get(i).y;
            unionFind.union(x, y);
            if (unionFind.find(0) == unionFind.find(row * col - 1)) return edgeList.get(i).difference;
        }
        return -1;
    }
}

class UnionFind {
    int[] parent;
    int[] rank;
    List<Edge> edgeList;

    public UnionFind(int[][] heights) {
        int row = heights.length;
        int col = heights[0].length;
        parent = new int[row * col];
        edgeList = new ArrayList<>();
        rank = new int[row * col];
        for (int currentRow = 0; currentRow < row; currentRow++) {
            for (int currentCol = 0; currentCol < col; currentCol++) {
                if (currentRow > 0) {
                    edgeList.add(new Edge(currentRow * col + currentCol,
                            (currentRow - 1) * col + currentCol,
                            Math.abs(heights[currentRow][currentCol] - heights[currentRow - 1][currentCol]))
                    );
                }
                if (currentCol > 0) {
                    edgeList.add(new Edge(currentRow * col + currentCol,
                            currentRow * col + currentCol - 1,
                            Math.abs(heights[currentRow][currentCol] - heights[currentRow][currentCol - 1]))
                    );
                }
                parent[currentRow * col + currentCol] = currentRow * col + currentCol;
            }
        }
    }

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    void union(int x, int y) {
        int parentX = find(x);
        int parentY = find(y);
        if (parentX != parentY) {
            if (rank[parentX] > rank[parentY]) parent[parentY] = parentX;
            else if (rank[parentX] < rank[parentY]) parent[parentX] = parentY;
            else {
                parent[parentY] = parentX;
                rank[parentX] += 1;
            }
        }
    }


    class Edge {
        int x;
        int y;
        int difference;

        Edge(int x, int y, int difference) {
            this.x = x;
            this.y = y;
            this.difference = difference;
        }
    }

}
