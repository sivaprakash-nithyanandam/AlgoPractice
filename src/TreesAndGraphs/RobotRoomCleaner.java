package TreesAndGraphs;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/4/2021  7:55 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

/**
 * https://leetcode.com/problems/robot-room-cleaner/
 *
 * Given a robot cleaner in a room modeled as a grid.
 *
 * Each cell in the grid can be empty or blocked.
 *
 * The robot cleaner with 4 given APIs can move forward, turn left or turn right. Each turn it made is 90 degrees.
 *
 * When it tries to move into a blocked cell, its bumper sensor detects the obstacle and it stays on the current cell.
 *
 * Design an algorithm to clean the entire room using only the 4 given APIs shown below.
 *
 * interface Robot {
 *   // returns true if next cell is open and robot moves into the cell.
 *   // returns false if next cell is obstacle and robot stays on the current cell.
 *   boolean move();
 *
 *   // Robot will stay on the same cell after calling turnLeft/turnRight.
 *   // Each turn will be 90 degrees.
 *   void turnLeft();
 *   void turnRight();
 *
 *   // Clean the current cell.
 *   void clean();
 * }
 * Example:
 *
 * Input:
 * room = [
 *   [1,1,1,1,1,0,1,1],
 *   [1,1,1,1,1,0,1,1],
 *   [1,0,1,1,1,1,1,1],
 *   [0,0,0,1,0,0,0,0],
 *   [1,1,1,1,1,1,1,1]
 * ],
 * row = 1,
 * col = 3
 *
 * Explanation:
 * All grids in the room are marked by either 0 or 1.
 * 0 means the cell is blocked, while 1 means the cell is accessible.
 * The robot initially starts at the position of row=1, col=3.
 * From the top left corner, its position is one row below and three columns right.
 * Notes:
 *
 * The input is only given to initialize the room and the robot's position internally. You must solve this problem
 * "blindfolded". In other words, you must control the robot using only the mentioned 4 APIs,
 * without knowing the room layout and the initial robot's position.
 * The robot's initial position will always be in an accessible cell.
 * The initial direction of the robot will be facing up.
 * All accessible cells are connected, which means the all cells marked as 1 will be accessible by the robot.
 * Assume all four edges of the grid are all surrounded by wall.
 */
public class RobotRoomCleaner {

  public static void main(String[] args) {
    RobotRoomCleaner o = new RobotRoomCleaner();
    System.out.println();
  }

  /*private static final int[][] dirs = {{-1,0}, {0,1}, {1,0}, {0,-1}};

  public void cleanRoom_fastest(Robot robot) {
    boolean[][] visited = new boolean[robot.room.length][robot.room[0].length];
    backtrack(robot, robot.row, robot.col, visited, 0);
  }

  private void backtrack(Robot robot, int x, int y, boolean[][] visited, int di) {
    if(visited[x][y]) return;

    visited[x][y] = true;
    robot.clean();
    for(int i = 0; i < 4; i++) {
      if(robot.move()) {
        int[] dir = dirs[di];
        backtrack(robot, x+dir[0], y+dir[1], visited, di);

        robot.turnLeft();
        robot.turnLeft();
        robot.move();
        robot.turnRight();
        robot.turnRight();
      }
      robot.turnRight();
      di = (di+1)%4;
    }
  }

  //Up - Right - Down - Left
  int[][] dirs = {{-1,0}, {0,1}, {1,0}, {0,-1}};

  public void cleanRoom(Robot robot) {
    cleanRoom(robot, 0, 0, 0, new HashSet<String>());
  }

  private void cleanRoom(Robot robot, int r, int c, int d, Set<String> visited) {
    robot.clean();
    visited.add(r + "-" + c);

    for (int i = d; i < (d + 4); i++) {
      int nr = r + dirs[i%4][0];
      int nc = c + dirs[i%4][1];

      if (!visited.contains(nr+"-"+nc) && robot.move()) {
        cleanRoom(robot, nr, nc, i%4, visited);
      }
      robot.turnRight();
    }
    robot.turnRight();
    robot.turnRight();
    robot.move();
    robot.turnRight();
    robot.turnRight();
  }*/
}
