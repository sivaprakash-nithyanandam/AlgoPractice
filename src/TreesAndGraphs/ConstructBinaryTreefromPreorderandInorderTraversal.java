package TreesAndGraphs;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

/**
 * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 *
 * Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and
 * inorder is the inorder traversal of the same tree, construct and return the binary tree.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * Output: [3,9,20,null,null,15,7]
 * Example 2:
 *
 * Input: preorder = [-1], inorder = [-1]
 * Output: [-1]
 *
 *
 * Constraints:
 *
 * 1 <= preorder.length <= 3000
 * inorder.length == preorder.length
 * -3000 <= preorder[i], inorder[i] <= 3000
 * preorder and inorder consist of unique values.
 * Each value of inorder also appears in preorder.
 * preorder is guaranteed to be the preorder traversal of the tree.
 * inorder is guaranteed to be the inorder traversal of the tree.
 */
public class ConstructBinaryTreefromPreorderandInorderTraversal {

  public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }

  public static void main(String[] args) {
    ConstructBinaryTreefromPreorderandInorderTraversal o = new ConstructBinaryTreefromPreorderandInorderTraversal();
    TreeNode res = o.buildTree_faster(new int[]{3,9,8,7,10,1,2}, new int[]{8,9,7,1,10,2,3});
    res = o.buildTree_faster(new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7});
    res = o.buildTree_faster(new int[]{3,9,6,1,4,8,5,2,20,15,7}, new int[]{1,6,4,9,5,8,2,3,15,20,7});
    res = o.buildTree_faster(new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7});
    res = o.buildTree(new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7});
    System.out.println("Completed");
  }

  int pre = 0;
  int in = 0;
  public TreeNode buildTree_faster(int[] preorder, int[] inorder) {
    return helper(preorder, inorder, Integer.MIN_VALUE);
  }

  TreeNode helper(int[] preorder, int[] inorder, int stop) {
    if (pre >= preorder.length) {
      return null;
    }
    if (inorder[in] == stop) {
      in++;
      return null;
    }
    TreeNode node = new TreeNode(preorder[pre]);
    pre++;
    node.left = helper(preorder, inorder, node.val);
    node.right = helper(preorder, inorder, stop);
    return node;
  }

  int preOrderIdx = 0;
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    return buildTree(preorder, inorder, 0, inorder.length - 1);
  }

  private TreeNode buildTree(int[] preOrder, int[] inOrder, int start, int end) {
    if (start > end) {
      return null;
    }
    TreeNode node = new TreeNode(preOrder[preOrderIdx++]);
    if (start == end) {
      return node;
    }
    int splitPoint = search(inOrder, start, end, node.val);
    node.left = buildTree(preOrder, inOrder, start, splitPoint-1);
    node.right = buildTree(preOrder, inOrder, splitPoint+1, end);
    return node;
  }

  private int search(int[] inOrder, int start, int end, int val) {
    for (int i = start; i <= end; i++) {
      if (inOrder[i] == val) {
        return i;
      }
    }
    return -1;
  }
}
