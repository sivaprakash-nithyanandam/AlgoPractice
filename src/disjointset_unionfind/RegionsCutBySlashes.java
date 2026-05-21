package disjointset_unionfind;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Aug 09,2024 - 11:26 PM
 */

/**
 * https://leetcode.com/problems/regions-cut-by-slashes/description/
 *
 * An n x n grid is composed of 1 x 1 squares where each 1 x 1 square consists of a '/', '\', or blank space ' '. These characters divide the square into contiguous regions.
 *
 * Given the grid grid represented as a string array, return the number of regions.
 *
 * Note that backslash characters are escaped, so a '\' is represented as '\\'.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: grid = [" /","/ "]
 * Output: 2
 * Example 2:
 *
 *
 * Input: grid = [" /","  "]
 * Output: 1
 * Example 3:
 *
 *
 * Input: grid = ["/\\","\\/"]
 * Output: 5
 * Explanation: Recall that because \ characters are escaped, "\\/" refers to \/, and "/\\" refers to /\.
 *
 *
 * Constraints:
 *
 * n == grid.length == grid[i].length
 * 1 <= n <= 30
 * grid[i][j] is either '/', '\', or ' '.
 */
public class RegionsCutBySlashes {

    public static void main(String[] args) {
        RegionsCutBySlashes o = new RegionsCutBySlashes();
        System.out.println(o.regionsBySlashes_uf(new String[]{"\\/","/\\"}));
        System.out.println(o.regionsBySlashes_uf(new String[]{"/\\","\\/"}));
    }

    int[] parent;
    int[] rank;
    int count;
    public int regionsBySlashes_uf(String[] grid) {
        int rows = grid.length;
        int dots = rows+1;
        parent = new int[dots*dots];
        rank = new int[dots*dots];
        for (int i=0; i<parent.length; i++){
            parent[i] = i;
            rank[i] = 1;
        }

        for (int i=0; i<dots; i++){
            for (int j=0; j<dots; j++){
                if (i==0 || j==0 || i==rows || j==rows){
                    int cells = i * dots + j;
                    union(0, cells);
                }
            }
        }

        for (int i=0; i<rows; i++){
            char[] ch = grid[i].toCharArray();
            for (int j=0; j<ch.length; j++){
                if (ch[j] == '\\'){
                    int cell1 = i* dots+ j;
                    int cell2 = (i+1)*dots + (j+1);
                    union(cell1, cell2);
                } else if (ch[j] == '/'){
                    int cell1 = (i+1)*dots + j;
                    int cell2 = i*dots + (j+1);
                    union(cell1, cell2);
                }
            }
        }
        return count;
    }

    public void union(int a, int b){
        int parentA = find(a);
        int parentB = find(b);
        if (parentA == parentB){
            count++;
        } else {
            if (rank[parentA] > rank[parentB]){
                parent[parentB] = parentA;
            } else if (rank[parentA] < rank[parentB]){
                parent[parentA] = parentB;
            } else {
                parent[parentB] = parentA;
                rank[parentA]++;
            }
        }
    }

    public int find(int a){
        if(parent[a]==a)
            return a;
        int temp = find(parent[a]);
        parent[a] = temp;
        return temp;
    }

    public int regionsBySlashes(String[] grid) {

        int n = grid.length;
        int mapLen = n*3;
        int[][] map = new int[mapLen][mapLen];

        int i = 0;
        for (int k = 0; k < grid.length; k++) {

            int j = 0;

            for (char c : grid[k].toCharArray()) {
                if (c == '/') {
                    map[i+2][j] = 1;
                    map[i+1][j+1] = 1;
                    map[i][j+2] = 1;
                } else if (c == '\\') {
                    map[i][j] = 1;
                    map[i+1][j+1] = 1;
                    map[i+2][j+2] = 1;
                }
                j += 3;
            }
            i += 3;
        }

        int res = 0;
        for (int x = 0; x < mapLen; x++) {
            for (int j = 0; j < mapLen; j++) {
                if (map[x][j] == 0) {
                    res++;
                    fillFlood(x, j, map, mapLen);
                }
            }
        }
        return res;
    }

    private void fillFlood(int i, int j, int[][] map, int mapLen) {
        if (i < 0 || j < 0 || i == mapLen || j == mapLen || map[i][j] == 1) {
            return;
        }
        map[i][j] = 1;
        fillFlood(i, j+1, map, mapLen);
        fillFlood(i+1, j, map, mapLen);
        fillFlood(i, j-1, map, mapLen);
        fillFlood(i-1, j, map, mapLen);
    }
}
