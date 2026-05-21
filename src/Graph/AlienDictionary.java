package Graph;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Dec 28,2022 - 6:06 PM
 */

import java.util.*;

/**
 * https://leetcode.com/explore/learn/card/graph/623/kahns-algorithm-for-topological-sorting/3909/
 *
 * There is a new alien language that uses the English alphabet. However, the order among the letters is unknown to you.
 *
 * You are given a list of strings words from the alien language's dictionary, where the strings in words are sorted
 * lexicographically by the rules of this new language.
 *
 * Return a string of the unique letters in the new alien language sorted in lexicographically increasing order
 * by the new language's rules. If there is no solution, return "". If there are multiple solutions,
 * return any of them.
 *
 *
 *
 * Example 1:
 *
 * Input: words = ["wrt","wrf","er","ett","rftt"]
 * Output: "wertf"
 * Example 2:
 *
 * Input: words = ["z","x"]
 * Output: "zx"
 * Example 3:
 *
 * Input: words = ["z","x","z"]
 * Output: ""
 * Explanation: The order is invalid, so return "".
 *
 *
 * Constraints:
 *
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 100
 * words[i] consists of only lowercase English letters.
 */

public class AlienDictionary {

    public static void main(String[] args) {
        AlienDictionary o = new AlienDictionary();
        System.out.println(o.alienOrder(new String[]{"wrf","wte","b","r"}));
        System.out.println(o.alienOrder(new String[]{"wrt","wrf","er","ett","rftt"}));
        System.out.println(o.alienOrder(new String[]{"app","apple"}));
        System.out.println(o.alienOrder(new String[]{"apple","app"}));
        System.out.println(o.alienOrder(new String[]{"z","x", "z"}));

        System.out.println(o.alienOrder_own(new String[]{"wrf","wte","b","r"}));
        System.out.println(o.alienOrder_own(new String[]{"wrt","wrf","er","ett","rftt"}));
        System.out.println(o.alienOrder_own(new String[]{"app","apple"}));
        System.out.println(o.alienOrder_own(new String[]{"apple","app"}));
        System.out.println(o.alienOrder_own(new String[]{"z","x", "z"}));
    }

    private String alienOrder_own(String[] words) {

        Map<Character, Integer> counts = new HashMap<>();
        Set<Character> set = new HashSet<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                set.add(c);
                counts.put(c, 0);
            }
        }

        Map<Character, List<Character>> depMap = new HashMap<>();

        for (int i = 1; i < words.length; i++) {
            if (words[i-1].startsWith(words[i])) {
                return "";
            }

            for (int j = 0; j < Math.min(words[i-1].length(), words[i].length()); j++) {
                if (words[i-1].charAt(j) != words[i].charAt(j)) {
                    depMap.computeIfAbsent(words[i-1].charAt(j), none -> new ArrayList<>()).add(words[i].charAt(j));
                    counts.put(words[i].charAt(j), counts.get(words[i].charAt(j)) + 1);
                }
            }
        }

        Queue<Character> q = new LinkedList<>();
        for (char c : set) {
            if (counts.get(c) == 0) {
                q.add(c);
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!q.isEmpty()) {
            char c = q.poll();
            sb.append(c);
            if (depMap.containsKey(c)) {
                for (char dep : depMap.get(c)) {
                    counts.put(dep, counts.get(dep) - 1);
                    if (counts.get(dep) == 0) {
                        q.add(dep);
                    }
                }
            }
        }
        return sb.length() == set.size() ? sb.toString() : "";
    }




    public String alienOrder(String[] words) {
        // Step 0: Create data structures and find all unique letters.
        Map<Character, List<Character>> adjList = new HashMap<>();
        Map<Character, Integer> counts = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                counts.put(c, 0);
                adjList.put(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Check that word2 is not a prefix of word1.
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            // Find the first non match and insert the corresponding relation.
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    adjList.get(word1.charAt(j)).add(word2.charAt(j));
                    counts.put(word2.charAt(j), counts.get(word2.charAt(j)) + 1);
                    break;
                }
            }
        }

        // Step 2: Breadth-first search.
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (Character c : counts.keySet()) {
            if (counts.get(c).equals(0)) {
                queue.add(c);
            }
        }
        while (!queue.isEmpty()) {
            Character c = queue.remove();
            sb.append(c);
            for (Character next : adjList.get(c)) {
                counts.put(next, counts.get(next) - 1);
                if (counts.get(next).equals(0)) {
                    queue.offer(next);
                }
            }
        }

        if (sb.length() < counts.size()) {
            return "";
        }
        return sb.toString();
    }
}
