package TreesAndGraphs;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 6/18/2021  6:33 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * https://leetcode.com/problems/reconstruct-itinerary/
 * <p>
 * You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival
 * airports of one flight. Reconstruct the itinerary in order and return it.
 * <p>
 * All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there are
 * multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single
 * string.
 * <p>
 * For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"]. You may assume all tickets
 * form at least one valid itinerary. You must use all the tickets once and only once.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]] Output: ["JFK","MUC","LHR","SFO","SJC"]
 * Example 2:
 * <p>
 * <p>
 * Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]] Output:
 * ["JFK","ATL","JFK","SFO","ATL","SFO"] Explanation: Another possible reconstruction is
 * ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= tickets.length <= 300 tickets[i].length == 2 fromi.length == 3 toi.length == 3 fromi and toi consist of
 * uppercase English letters. fromi != toi
 */
public class ReconstructItinerary {

    public static void main(String[] args) {
        ReconstructItinerary o = new ReconstructItinerary();
        System.out.println(o.findItinerary_9ms(
                Arrays.asList(Arrays.asList("JFK", "AAA"), Arrays.asList("JFK", "BBB"), Arrays.asList("BBB", "CCC"),
                        Arrays.asList("CCC", "JFK"))));
    }

    List<String> order = new ArrayList<>();
    Map<String, PriorityQueue<String>> map = new HashMap<>();

    public List<String> findItinerary_9ms(List<List<String>> tickets) {

        for(List<String> ticket: tickets){
            String depart = ticket.get(0) ;
            String arrival = ticket.get(1);
            if(!map.containsKey(depart)){
                map.put(depart, new PriorityQueue<>());
            }
            map.get(depart).offer(arrival);
        }
        DFS("JFK");
        return order;

    }
    public void DFS(String origin){
        if(map.containsKey(origin)){
            PriorityQueue<String> q = map.get(origin);
            while(!q.isEmpty()){
                DFS(q.poll());
            }
        }
        order.add(0, origin);
    }

    public List<String> findItinerary_new(List<List<String>> tickets) {
        LinkedList<String> itinerary = new LinkedList<>();
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        Stack<String> stack = new Stack<>();

        for (List<String> ticket : tickets) {
            graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).add(ticket.get(1));
        }

        stack.push("JFK");
        while (!stack.isEmpty()) {
            String nextDestination = stack.peek();

            if (!graph.getOrDefault(nextDestination, new PriorityQueue<>()).isEmpty()) {
                stack.push(graph.get(nextDestination).poll());
            } else {
                itinerary.addFirst(stack.pop());
            }
        }
        return itinerary;
    }

    HashMap<String, List<String>> flightMap = new HashMap<>();
    HashMap<String, boolean[]> visitBitmap = new HashMap<>();
    int flights = 0;
    List<String> result = null;

    public List<String> findItinerary(List<List<String>> tickets) {
        // Step 1). build the graph first
        for (List<String> ticket : tickets) {
            String origin = ticket.get(0);
            String dest = ticket.get(1);
            if (this.flightMap.containsKey(origin)) {
                List<String> destList = this.flightMap.get(origin);
                destList.add(dest);
            } else {
                List<String> destList = new LinkedList<String>();
                destList.add(dest);
                this.flightMap.put(origin, destList);
            }
        }

        // Step 2). order the destinations and init the visit bitmap
        for (Map.Entry<String, List<String>> entry : this.flightMap.entrySet()) {
            Collections.sort(entry.getValue());
            this.visitBitmap.put(entry.getKey(), new boolean[entry.getValue().size()]);
        }

        this.flights = tickets.size();
        LinkedList<String> route = new LinkedList<String>();
        route.add("JFK");

        // Step 3). backtracking
        this.backtracking("JFK", route);
        return this.result;
    }

    protected boolean backtracking(String origin, LinkedList<String> route) {
        if (route.size() == this.flights + 1) {
            this.result = (List<String>) route.clone();
            return true;
        }

        if (!this.flightMap.containsKey(origin)) {
            return false;
        }

        int i = 0;
        boolean[] bitmap = this.visitBitmap.get(origin);

        for (String dest : this.flightMap.get(origin)) {
            if (!bitmap[i]) {
                bitmap[i] = true;
                route.add(dest);
                boolean ret = this.backtracking(dest, route);
                route.pollLast();
                bitmap[i] = false;

                if (ret) {
                    return true;
                }
            }
            ++i;
        }

        return false;
    }
}
