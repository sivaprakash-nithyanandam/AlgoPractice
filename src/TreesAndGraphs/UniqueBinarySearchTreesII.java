package TreesAndGraphs;

import java.util.ArrayList;
import java.util.List;

// Definition for a binary tree node.
  class TreeNode1 {
      int val;
      TreeNode1 left;
      TreeNode1 right;
      TreeNode1() {}
      TreeNode1(int val) { this.val = val; }
      TreeNode1(int val, TreeNode1 left, TreeNode1 right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
public class UniqueBinarySearchTreesII {
    public static void main(String[] args) {
        new UniqueBinarySearchTreesII().generateTrees(3);
    }

    public List<TreeNode1> generateTrees(int n) {
        return helper(1, n);
    }
    
    private List<TreeNode1> helper(int start, int end) {
        
        List<TreeNode1> res = new ArrayList<>();
        
        if (start > end) {
            res.add(null);
            return res;
        } else if (start == end) {
            res.add(new TreeNode1(start));
            return res;
        } else {
            for (int i = start; i <= end; i++) {
                List<TreeNode1> leftNodes = helper(start, i - 1);
                List<TreeNode1> rightNodes = helper(i+1, end);
                
                for (TreeNode1 left : leftNodes) {
                    for (TreeNode1 right : rightNodes) {
                        TreeNode1 node = new TreeNode1(i, left, right);
                        res.add(node);
                    }
                }
            }   
        }
        return res;
    }
}