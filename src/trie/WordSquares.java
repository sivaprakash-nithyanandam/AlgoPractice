package trie;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/4/2021  11:36 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/word-squares/
 * <p>
 * Given an array of unique strings words, return all the word squares you can build from words. The
 * same word from words can be used multiple times. You can return the answer in any order.
 * <p>
 * A sequence of strings forms a valid word square if the kth row and column read the same string,
 * where 0 <= k < max(numRows, numColumns).
 * <p>
 * For example, the word sequence ["ball","area","lead","lady"] forms a word square because each
 * word reads the same both horizontally and vertically.
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: words = ["area","lead","wall","lady","ball"] Output: [["ball","area","lead","lady"],["wall","area","lead","lady"]]
 * Explanation: The output consists of two word squares. The order of output does not matter (just
 * the order of words in each word square matters). Example 2:
 * <p>
 * Input: words = ["abat","baba","atan","atal"] Output: [["baba","abat","baba","atal"],["baba","abat","baba","atan"]]
 * Explanation: The output consists of two word squares. The order of output does not matter (just
 * the order of words in each word square matters).
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= words.length <= 1000 1 <= words[i].length <= 5 All words[i] have the same length. words[i]
 * consists of only lowercase English letters. All words[i] are unique.
 */
public class WordSquares {

  public static void main(String[] args) {
    WordSquares o = new WordSquares();
    System.out.println(o.wordSquares_faster(new String[]{"ball", "area", "lead", "wall", "lady"}));
    System.out.println(o.wordSquares_trie(new String[]{"ball", "area", "lead", "wall", "lady"}));
    System.out.println(o.wordSquares(new String[]{"ball", "area", "lead", "wall", "lady"}));
  }

  private Trie root = new Trie();

  //TODO - Go through
  public List<List<String>> wordSquares_faster(String[] words) {
    List<List<String>> result = new ArrayList<>();
    for (String word : words) {
      addWord(word);
    }
    int len = words[0].length();
    Trie[] heads = new Trie[len];
    for (int i = 0; i < root.childrenSize; i++) {
      int cid = root.childrenIndexs[i];
      heads[0] = root.children[cid];
      wordSquares(result, heads, 0, new Trie[len], 0, root, len);
    }
    return result;
  }

  private void wordSquares(List<List<String>> result, Trie[] heads, int end, Trie[] newHeads,
      int newEnd, Trie progress, int len) {
    if (end == len - 1) {
      ArrayList<String> r = new ArrayList<>(len);
      for (Trie trie : heads) {
        r.add(trie.word);
      }
      result.add(r);
      return;
    }
    Trie current = newEnd > end ? progress : heads[newEnd];
    for (int i = 0; i < current.childrenSize; i++) {
      int cid = current.childrenIndexs[i];
      if (progress.children[cid] != null) {
        newHeads[newEnd] = current.children[cid];
        if (newEnd > end) {
          wordSquares(result, newHeads, newEnd, new Trie[len], 0, root, len);
        } else {
          wordSquares(result, heads, end, newHeads, newEnd + 1, progress.children[cid], len);
        }
      }
    }
  }

  class Trie {

    Trie[] children = new Trie[26];
    int[] childrenIndexs = new int[26];
    int childrenSize;
    String word;
  }

  private void addWord(String word) {
    Trie curNode = root;
    for (int i = 0; i < word.length(); i++) {
      if (curNode.children[word.charAt(i) - 'a'] == null) {
        curNode.children[word.charAt(i) - 'a'] = new Trie();
        curNode.childrenIndexs[curNode.childrenSize] = word.charAt(i) - 'a';
        curNode.childrenSize++;
      }
      curNode = curNode.children[word.charAt(i) - 'a'];
    }
    curNode.word = word;
  }

  class TrieOwn {

    Map<Character, TrieOwn> childs;
    List<Integer> words;

    TrieOwn() {
      childs = new HashMap<>();
      words = new ArrayList<>();
    }
  }

  public List<List<String>> wordSquares_trie(String[] words) {

    List<List<String>> res = new ArrayList<>();
    TrieOwn trieOwn = new TrieOwn();
    int n = words[0].length();

    for (int i = 0; i < words.length; i++) {
      buildTrie(words[i], i, trieOwn);
    }

    LinkedList<String> list;
    for (String word : words) {
      list = new LinkedList<>();
      list.add(word);
      findWordSquare(list, 1, trieOwn, n, res, words);
    }

    return res;
  }

  private void findWordSquare(LinkedList<String> list, int pos, TrieOwn trieOwn, int max,
      List<List<String>> res, String[] words) {
    if (pos == max) {
      res.add((List<String>) list.clone());
      return;
    }

    StringBuilder sb = new StringBuilder();
    for (String w : list) {
      sb.append(w.charAt(pos));
    }

    for (Integer i : getAssociatedWords(sb, trieOwn)) {
      list.add(words[i]);
      findWordSquare(list, pos + 1, trieOwn, max, res, words);
      list.remove(words[i]);
    }
  }

  private List<Integer> getAssociatedWords(StringBuilder sb, TrieOwn trieOwn) {

    TrieOwn node = trieOwn;
    for (int i = 0; i < sb.length(); i++) {
      node = node.childs.get(sb.charAt(i));
      if (node == null) {
        return new ArrayList<>();
      }
    }
    return node.words;
  }

  private void buildTrie(String word, int i, TrieOwn trieOwn) {

    TrieOwn node = trieOwn;
    for (char c : word.toCharArray()) {
      if (node.childs.containsKey(c)) {
        node = node.childs.get(c);
      } else {
        TrieOwn newTrieOwn = new TrieOwn();
        node.childs.put(c, newTrieOwn);
        node = newTrieOwn;
      }
      node.words.add(i);
    }
  }


  int N = 0;
  String[] words = null;
  HashMap<String, List<String>> prefixHashTable = null;

  public List<List<String>> wordSquares(String[] words) {
    this.words = words;
    this.N = words[0].length();

    List<List<String>> results = new ArrayList<List<String>>();
    this.buildPrefixHashTable(words);

    for (String word : words) {
      LinkedList<String> wordSquares = new LinkedList<String>();
      wordSquares.addLast(word);
      this.backtracking(1, wordSquares, results);
    }
    return results;
  }

  protected void backtracking(int step, LinkedList<String> wordSquares,
      List<List<String>> results) {
    if (step == N) {
      results.add((List<String>) wordSquares.clone());
      return;
    }

    StringBuilder prefix = new StringBuilder();

    for (String word : wordSquares) {
      prefix.append(word.charAt(step));
    }

    for (String candidate : this.getWordsWithPrefix(prefix.toString())) {
      wordSquares.addLast(candidate);
      this.backtracking(step + 1, wordSquares, results);
      wordSquares.removeLast();
    }
  }

  protected void buildPrefixHashTable(String[] words) {
    this.prefixHashTable = new HashMap<String, List<String>>();

    for (String word : words) {
      for (int i = 1; i < this.N; ++i) {
        String prefix = word.substring(0, i);
        List<String> wordList = this.prefixHashTable.get(prefix);
        if (wordList == null) {
          wordList = new ArrayList<String>();
          wordList.add(word);
          this.prefixHashTable.put(prefix, wordList);
        } else {
          wordList.add(word);
        }
      }
    }
  }

  protected List<String> getWordsWithPrefix(String prefix) {
    List<String> wordList = this.prefixHashTable.get(prefix);
    return (wordList != null ? wordList : new ArrayList<String>());
  }

}
