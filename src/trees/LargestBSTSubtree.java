package trees;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 01,2024 - 9:10 AM
 */

public class LargestBSTSubtree {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        LargestBSTSubtree o = new LargestBSTSubtree();
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(8);
        root.right = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(o.largestBSTSubtree(root));
    }

    int ans = 0;
    public int largestBSTSubtree(TreeNode node) {
        dfs(node);
        return ans;
    }

    private int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        }

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        if (left[1] < node.val && right[0] > node.val) {
            ans = Math.max(ans, left[2]+right[2]+1);
            return new int[]{Math.min(node.val, left[0]), Math.max(node.val, right[1]), left[2] + right[2] + 1};
        } else {
            return new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0};
        }
    }
}
