package MathAndLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of words and a width maxWidth, format the text such that each line has exactly
 * maxWidth characters and is fully (left and right) justified.
 * You should pack your words in a greedy approach; that is, pack as many words as you can in each line.
 * Pad extra spaces ' ' when necessary
 * so that each line has exactly maxWidth characters.
 * Extra spaces between words should be distributed as evenly as possible.
 * If the number of spaces on a line do not divide evenly between words,
 * the empty slots on the left will be assigned more spaces than the slots on the right.
 * For the last line of text, it should be left justified and no extra space is inserted between words.
 * Note:
 * A word is defined as a character sequence consisting of non-space characters only.
 * Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
 * The input array words contains at least one word.
 * Example 1:
 * Input:
 * words = ["This", "is", "an", "example", "of", "text", "justification."]
 * maxWidth = 16
 * Output:
 * [
 *    "This    is    an",
 *    "example  of text",
 *    "justification.  "
 * ]
 * Example 2:
 * Input:
 * words = ["What","must","be","acknowledgment","shall","be"]
 * maxWidth = 16
 * Output:
 * [
 *   "What   must   be",
 *   "acknowledgment  ",
 *   "shall be        "
 * ]
 * Explanation: Note that the last line is "shall be    " instead of "shall     be",
 *              because the last line must be left-justified instead of fully-justified.
 *              Note that the second line is also left-justified becase it contains only one word.
 * Example 3:
 * Input:
 * words = ["Science","is","what","we","understand","well","enough","to","explain",
 *          "to","a","computer.","Art","is","everything","else","we","do"]
 * maxWidth = 20
 * Output:
 * [
 *   "Science  is  what we",
 *   "understand      well",
 *   "enough to explain to",
 *   "a  computer.  Art is",
 *   "everything  else  we",
 *   "do                  "
 * ]
 */
public class TextJustification {

  public static void main(String[] args) {
    String[] words;
    List<String> res;
    TextJustification tj = new TextJustification();

    words = new String[]{"This", "is", "an", "example", "of", "text", "justification."};
    res = tj.fullJustify_own(words, 16);
    for (String s : res) {
      System.out.println(s);
    }

    System.out.println();
    words = new String[]{"What", "must", "be", "acknowledgment", "shall", "be"};
    res = tj.fullJustify(words, 16);
    for (String s : res) {
      System.out.println(s);
    }

    System.out.println();
    words = new String[]{"Science", "is", "what", "we", "understand", "well", "enough", "to",
        "explain", "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"};
    res = tj.fullJustify(words, 16);
    for (String s : res) {
      System.out.println(s);
    }
  }

  public List<String> fullJustify(String[] words, int maxWidth) {
    List<String> res = new ArrayList<>();
    int idx = 0;
    while (idx < words.length) {
      int last = idx + 1;
      int count = words[idx].length();
      while (last < words.length && count + 1 + words[last].length() <= maxWidth) {
        count += words[last].length() + 1;
        last++;
      }
      int numOfWord = last - idx - 1;
      StringBuilder sb = new StringBuilder();
      if (last == words.length || numOfWord == 0) {
        for (int i = idx; i < last; i++) {
          sb.append(words[i]);
          sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        while (sb.length() < maxWidth) {
          sb.append(" ");
        }
      } else {
        int gap = (maxWidth - count) / numOfWord;
        int left = (maxWidth - count) % numOfWord;
        for (int i = idx; i < last; i++) {
          sb.append(words[i]);
          if (i == last - 1) {
            break;
          }
          for (int j = 0; j <= gap; j++) {
            sb.append(" ");
          }
          if (left > 0) {
            sb.append(" ");
            left--;
          }
        }
      }
      idx = last;
      res.add(sb.toString());
    }
    return res;
  }

  public List<String> fullJustify_own(String[] words, int maxWidth) {
    List<String> res = new ArrayList<>();
    StringBuilder sb;
    int length = 0;
    int start = 0;
    for (int i = 0; i < words.length; i++) {
      if (maxWidth < (length + words[i].length() + i - start)) {
        int end = i - 1;
        int spaceAval = maxWidth - length;
        int reqSpace;
        int excessSpace;
        if (start == end) {
          reqSpace = 1;
          excessSpace = 0;
          spaceAval = 0;
        } else {
          reqSpace = spaceAval / (end - start);
          excessSpace = spaceAval % (end - start);
        }

        sb = new StringBuilder();
        sb.append(words[start]);
        for (int j = start + 1; j <= end; j++) {
          sb.append(getSpaces(reqSpace));
          if (excessSpace > 0) {
            sb.append(" ");
            excessSpace--;
          }
          sb.append(words[j]);
        }
        sb.append(getSpaces(maxWidth - length - spaceAval));
        res.add(sb.toString());
        length = 0;
        start = i;
      }
      length += words[i].length();
    }

    sb = new StringBuilder();
    for (int j = start; j < words.length; j++) {
      sb.append(words[j]);
      sb.append(" ");
    }
    sb.deleteCharAt(sb.length() - 1);
    sb.append(getSpaces(maxWidth - length - (words.length - 1 - start)));
    res.add(sb.toString());
    return res;
  }

  private StringBuilder getSpaces(int count) {
    StringBuilder sb = new StringBuilder();
    while (count-- > 0) {
      sb.append(" ");
    }
    return sb;
  }

  public List<String> fullJustifyTusher(String[] words, int maxWidth) {
    int[][] matrix = new int[words.length][words.length];
    for (int i = 0; i < words.length; i++) {
      int length = 0;
      for (int j = i; j < words.length; j++) {
        int newLength = length + words[j].length() + j - i;
        if (newLength <= maxWidth) {
          matrix[i][j] = (int) Math.pow((maxWidth - newLength), 2);
        } else {
          matrix[i][j] = Integer.MAX_VALUE;
        }
        length += words[j].length();
      }
    }
    int[] minCost = new int[words.length];
    int[] resIndex = new int[words.length];

    for (int i = words.length - 1; i >= 0; i--) {
      minCost[i] = matrix[i][words.length - 1];
      resIndex[i] = words.length;
      for (int j = words.length - 1; j > i; j--) {
        if (matrix[i][j - 1] == Integer.MAX_VALUE) {
          continue;
        }
        if (minCost[i] > minCost[j] + matrix[i][j - 1]) {
          minCost[i] = minCost[j] + matrix[i][j - 1];
          resIndex[i] = j;
        }
      }
    }

    List<String> res = new ArrayList<>();
    StringBuilder sb;
    int i = 0;
    int j;
    do {
      j = resIndex[i];
      sb = new StringBuilder();
      while (i < j) {
        sb.append(words[i]).append(" ");
        i++;
      }
      res.add(sb.toString());
    } while (j < words.length);

    return res;
  }
}
