package TreesAndGraphs;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 6/19/2021  11:42 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-n-ary-tree/
 *
 *
 */


public class SerializeandDeserializeNaryTree {

  class Node {
    public int val;
    public List<Node> children;
    public Node() {}
    public Node(int _val, List<Node> _children) {
      val = _val;
      children = _children;
    }
  }

  public static void main(String[] args) {
    new SerializeandDeserializeNaryTree().call();
  }

  public void call() {
    SerializeandDeserializeNaryTree o = new SerializeandDeserializeNaryTree();
    Node node1 = new Node(1000000, new ArrayList<>());
    Node node2 = new Node(11, new ArrayList<>());
    Node node = new Node(2, Arrays.asList(node1, node2));
    String data = o.serialize(node);
    System.out.println(data);
    Node root1 = o.deserialize(data);

    Node root = o.deserialize("2,4,3,0,5,2,4,0,3,0,3,1,1,0,7,0");
    System.out.println(o.serialize(root));
  }

    private static final String SPLITER = ",";
    private static final String NULL_NODE = "#";

    // Encodes a tree to a single string.
    public String serialize(Node root) {
      StringBuilder sb = new StringBuilder();
      buildString(root, sb);
      return sb.toString();
    }

    // pre-order traversal
    private void buildString(Node node, StringBuilder sb) {
      if (node == null) {
        sb.append(NULL_NODE).append(SPLITER).append(0).append(SPLITER); // size=0
      } else {
        sb.append(node.val).append(SPLITER).append(node.children.size()).append(SPLITER);
        for (Node child: node.children) {
          buildString(child, sb);
        }
      }
    }

    // Decodes your encoded data to tree.
    public Node deserialize(String data) {
      if (data.length() == 0) {
        return null;
      }

      LinkedList<String> nodesList = new LinkedList<>();
      nodesList.addAll(Arrays.asList(data.split(SPLITER)));
      return buildTree(nodesList);
    }

    private Node buildTree(LinkedList<String> nodes) {
      // @note: key is here, just keep popping the 1st as root of current sub-tree
      String val = nodes.removeFirst();

      if (val.equals(NULL_NODE)) {
        return null;
      } else {
        Node node = new Node();
        node.val = Integer.valueOf(val);
        int size = Integer.valueOf(nodes.removeFirst()); // to get children size

        node.children = new ArrayList<>(size);
        for(int i = 0; i < size; ++i) {
          node.children.add(buildTree(nodes));
        }

        return node;
      }
    }
}
