package range_probs_SegmentTree_Fenwick_or_BinaryIndexedTree;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/10/2021  9:55 AM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

/**
 * https://leetcode.com/problems/range-sum-query-mutable/
 *
 * Given an integer array nums, handle multiple queries of the following types:
 *
 * Update the value of an element in nums.
 * Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
 * Implement the NumArray class:
 *
 * NumArray(int[] nums) Initializes the object with the integer array nums.
 * void update(int index, int val) Updates the value of nums[index] to be val.
 * int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 *
 *
 * Example 1:
 *
 * Input
 * ["NumArray", "sumRange", "update", "sumRange"]
 * [[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
 * Output
 * [null, 9, null, 8]
 *
 * Explanation
 * NumArray numArray = new NumArray([1, 3, 5]);
 * numArray.sumRange(0, 2); // return 1 + 3 + 5 = 9
 * numArray.update(1, 2);   // nums = [1, 2, 5]
 * numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 3 * 104
 * -100 <= nums[i] <= 100
 * 0 <= index < nums.length
 * -100 <= val <= 100
 * 0 <= left <= right < nums.length
 * At most 3 * 104 calls will be made to update and sumRange.
 */
public class RangeSumQueryMutable {

  public static void main(String[] args) {
    RangeSumQueryMutable o = new RangeSumQueryMutable(new int[]{1,3,5});
    System.out.println(o.sumRange_own(0,2));
    o.update_own(1,2);
    System.out.println(o.sumRange_own(0,2));
  }

  int arr[];
  int sum;
  public RangeSumQueryMutable(int[] nums, String faster) {
    for(int i = 0;i<nums.length;i++)
      sum += nums[i];

    arr = nums;
  }

  public void update_faster(int index, int val) {
    if (val == arr[index]) return;
    sum += (val - arr[index]);
    arr[index] = val;
    // sum = sum - arr[index];
    // arr[index] = val;
    // sum += val;
  }

  public int sumRange_faster(int left, int right) {
    if(left == 0 && right + 1 == arr.length){
      return sum;
    }

    int s = 0;
    if(right - left < (arr.length >> 1)){
      for(int i = left; i<=right; i++){
        s += arr[i];
      }
      return s;
    } else{
      s = sum;
      for(int i = 0;i < left;i++){
        s -= arr[i];
      }
      for(int i = arr.length-1 ;i>right;i--){
        s -= arr[i];
      }
      return s;
    }

  }

  //--------------

  int[] tree;
  int n;

  private int nextPowerOfTwo (int n) {
    if (n == 0) {
      return 1;
    } else if (n > 0 && (n & n-1) == 0) {
      return n;
    } else {
      while ((n & (n-1)) != 0) {
        n = n & (n-1);
      }
      return n << 1;
    }
  }

  private void buildSegmentTree_own(int[] nums, int[] tree, int start, int end, int pos) {

    if (start > end) {
      return;
    }

    if (start == end) {
      tree[pos] = nums[start];
      return;
    }

    int mid = start + (end - start)/2;
    buildSegmentTree_own(nums, tree, start, mid, (2 * pos + 1));
    buildSegmentTree_own(nums, tree, mid+1, end, (2 * pos + 2));
    tree[pos] = tree[2 * pos + 1] + tree[2 * pos + 2];
  }

  public RangeSumQueryMutable(int[] nums) {
    n = nums.length;
    int size = nextPowerOfTwo(n);
    tree = new int[size * 2 - 1];
    buildSegmentTree_own(nums, tree, 0, nums.length-1, 0);
  }

  private void updateTree_own(int start, int end, int pos, int val, int idx) {
    if (idx < start || idx > end) {
      return;
    }

    if (start == end) {
      tree[pos] = val;
      return;
    }

    int mid = start + (end - start)/2;

    if (idx <= mid) {
      updateTree_own(start, mid, 2 * pos + 1, val, idx);
    } else {
      updateTree_own(mid+1, end, 2 * pos + 2, val, idx);
    }
    tree[pos] = tree[2 * pos + 1] + tree[2 * pos + 2];
  }

  public void update_own(int index, int val) {
    updateTree_own(0, n-1, 0, val, index);
  }

  public int sumRange_own(int left, int right) {
    return sumRangeTree_own(left, right, 0, n-1, 0);
  }

  private int sumRangeTree_own(int ql, int qr, int start, int end, int pos) {
    if (ql > end || qr < start) {
      return 0;
    } else if (start >= ql && end <= qr) {
      return tree[pos];
    } else {
      int mid = start + (end - start)/2;
      return sumRangeTree_own(ql, qr, start, mid, 2 * pos + 1) + sumRangeTree_own(ql, qr, mid+1, end, 2 * pos + 2);
    }
  }

  ///-------------

  class SegmentTreeNode {
    int start;
    int end;
    int val;
    SegmentTreeNode left;
    SegmentTreeNode right;

    public SegmentTreeNode(int start, int end) {
      val = 0;
      this.start = start;
      this.end = end;
      left = null;
      right = null;
    }
  }

  SegmentTreeNode root = null;
  public RangeSumQueryMutable(int[] nums, int test) {
    root = buildTree(nums, 0, nums.length - 1);
  }
  private SegmentTreeNode buildTree(int[] nums, int l, int r) {
    if (l > r) return null;

    SegmentTreeNode cur = new SegmentTreeNode(l, r);
    if (l == r) cur.val = nums[l];
    else {
      int mid = l + (r - l) / 2;
      cur.left = buildTree(nums, l, mid);
      cur.right = buildTree(nums, mid + 1, r);
      cur.val = cur.left.val + cur.right.val;
    }
    return cur;
  }

  public void update(int index, int val) {
    update(root, index, val);
  }
  private void update(SegmentTreeNode root, int pos, int val) {
    if (root.start == root.end) {
      root.val = val;
      return;
    }
    int mid = root.start + (root.end - root.start) / 2;
    if (pos <= mid) update(root.left, pos, val);
    else update(root.right, pos, val);
    root.val = root.left.val + root.right.val;
  }

  public int sumRange(int left, int right) {
    return sumRange(root, left, right);
  }
  private int sumRange(SegmentTreeNode root, int start, int end) {
    if (root.start == start && root.end == end)
      return root.val;
    int mid = root.start + (root.end - root.start) / 2;
    if (start > mid)
      return sumRange(root.right, start, end);
    else if (end <= mid)
      return sumRange(root.left, start, end);
    else return sumRange(root.left, start, mid) + sumRange(root.right, mid + 1, end);
  }
}
