package bfs;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Apr 22,2024 - 7:58 PM
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/open-the-lock/description/
 *
 * You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'. Each move consists of turning one wheel one slot.
 *
 * The lock initially starts at '0000', a string representing the state of the 4 wheels.
 *
 * You are given a list of deadends dead ends, meaning if the lock displays any of these codes, the wheels of the lock will stop turning and you will be unable to open it.
 *
 * Given a target representing the value of the wheels that will unlock the lock, return the minimum total number of turns required to open the lock, or -1 if it is impossible.
 *
 *
 *
 * Example 1:
 *
 * Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
 * Output: 6
 * Explanation:
 * A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
 * Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
 * because the wheels of the lock become stuck after the display becomes the dead end "0102".
 * Example 2:
 *
 * Input: deadends = ["8888"], target = "0009"
 * Output: 1
 * Explanation: We can turn the last wheel in reverse to move from "0000" -> "0009".
 * Example 3:
 *
 * Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
 * Output: -1
 * Explanation: We cannot reach the target without getting stuck.
 *
 *
 * Constraints:
 *
 * 1 <= deadends.length <= 500
 * deadends[i].length == 4
 * target.length == 4
 * target will not be in the list deadends.
 * target and deadends[i] consist of digits only.
 */
public class OpentheLock {

    public static void main(String[] args) {
        OpentheLock o = new OpentheLock();
        System.out.println(o.openLock_7ms(new String[]{"0201","0101","0102","1212","2002"}, "0202"));
        System.out.println(o.openLock_7ms(new String[]{"8888"}, "0009"));
        System.out.println(o.openLock_7ms(new String[]{"8887","8889","8878","8898","8788","8988","7888","9888"}, "8888"));
    }

    /**
     * Tricks:
     * Iterate in both the directions and prefer low size direction
     * Use Numerics compare to other string manipulation of digits - Consuming time
     *
     * @param deadends
     * @param target
     * @return
     */
    public int openLock_7ms(String[] deadends, String target) {
        int[] pow10 = {1, 10, 100, 1000};
        int[] visit = new int[10000]; // 0: not visited, 1: visited through forward direction, -1: visited through backward direction, 2: deadends
        for(String dead: deadends) {
            visit[Integer.parseInt(dead)] = 2;
        }
        int src = 0;
        int dest = Integer.parseInt(target);
        int steps = 0;
        int dir = 1;
        if(visit[src] == 2 || visit[dest] == 2) {
            return -1;
        }
        if(src == dest) {
            return 0;
        }
        Queue<Integer> forward = new LinkedList<>();
        forward.add(src);
        visit[src] = 1;

        Queue<Integer> backward = new LinkedList<>();
        backward.add(dest);
        visit[dest] = -1;

        while(!forward.isEmpty() && !backward.isEmpty()) {
            if(forward.size() > backward.size()) {
                Queue<Integer> tmp = forward;
                forward = backward;
                backward = tmp;
                dir = -dir;
            }
            steps++;
            int size = forward.size();

            while(size-- > 0) {
                int cur = forward.poll();
                for(int p: pow10) {
                    int d = (cur / p) % 10;

                    for(int i = -1; i <= 1; i += 2) {
                        int z = d + i;
                        z = z == -1 ? 9 : (z == 10 ? 0 : z);
                        int next = cur + (z - d) * p;

                        if(visit[next] == -dir) {
                            return steps;
                        }

                        if(visit[next] == 0) {
                            forward.add(next);
                            visit[next] = dir;
                        }
                    }
                }
            }
        }
        return -1;
    }

    /*public int openLock_95ms(String[] deadends, String target) {

        Set<String> deadendSet = new HashSet<>(Arrays.asList(deadends));

        if (deadendSet.contains("0000")) {
            return -1;
        }

        Queue<Pair<String, Integer>> q = new LinkedList<>();
        q.offer(new Pair<>("0000", 0));

        Set<String> vis = new HashSet<>();
        vis.add("0000");

        while(!q.isEmpty()) {
            Pair<String, Integer> curr = q.poll();

            if (curr.getKey().equals(target)) {
                return curr.getValue();
            }

            StringBuilder sb = new StringBuilder(curr.getKey());
            for (int i = 0; i < 4; i++) {
                for (int delta : new int[]{-1, 1}) {
                    char c = sb.charAt(i);
                    sb.setCharAt(i, (char)(((sb.charAt(i) - '0' + 10 + delta) % 10) + '0'));
                    String newComb = sb.toString();
                    if (!vis.contains(newComb) && !deadendSet.contains(newComb)) {
                        vis.add(newComb);
                        q.offer(new Pair<>(newComb, curr.getValue() + 1));
                    }
                    sb.setCharAt(i, c);
                }
            }
        }
        return -1;
    }*/

}
