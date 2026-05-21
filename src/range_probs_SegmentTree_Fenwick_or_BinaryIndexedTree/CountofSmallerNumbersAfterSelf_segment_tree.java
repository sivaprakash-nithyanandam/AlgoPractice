package range_probs_SegmentTree_Fenwick_or_BinaryIndexedTree;
/**
 * Author - Sivaprakash Nithyanandam Timestamp - 7/9/2021  8:29 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode.com/problems/count-of-smaller-numbers-after-self/
 *
 * You are given an integer array nums and you have to return a new counts array.
 * The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [5,2,6,1]
 * Output: [2,1,1,0]
 * Explanation:
 * To the right of 5 there are 2 smaller elements (2 and 1).
 * To the right of 2 there is only 1 smaller element (1).
 * To the right of 6 there is 1 smaller element (1).
 * To the right of 1 there is 0 smaller element.
 * Example 2:
 *
 * Input: nums = [-1]
 * Output: [0]
 * Example 3:
 *
 * Input: nums = [-1,-1]
 * Output: [0,0]
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 */
public class CountofSmallerNumbersAfterSelf_segment_tree {

  public static void main(String[] args) {
    CountofSmallerNumbersAfterSelf_segment_tree o = new CountofSmallerNumbersAfterSelf_segment_tree();
    System.out.println(Arrays.asList(o.countSmaller_merge_sort_understandable(new int[]{5,2,6,1})));
    System.out.println(Arrays.asList(o.countSmaller_segment_tree(new int[]{26,78,27,100,33,67,90,23,66,5,38,7,35,23,52,22,83,51,98,69,81,32,78,28,94,13,2,97,3,76,99,51,9,21,84,66,65,36,100,41})));
  }

  int[] count;
  public List<Integer> countSmaller_merge_sort_understandable(int[] nums) {
    List<Integer> ans = new ArrayList<>();
    int len = nums.length;
    count = new int[len];
    int[] indices = new int[len];
    for(int i = 0; i < len; i++)
      indices[i] = i;

    sort(nums, indices, 0, len - 1);

    for(int i : count)
      ans.add(i);

    return ans;
  }

  public void sort(int[] arr, int[] indices, int start, int end){
    if(start < end){
      int mid = start + (end - start) / 2;
      sort(arr, indices, start, mid);
      sort(arr, indices, mid + 1, end);
      merge(arr,indices,start,mid,end);
    }
  }

  public void merge(int[] arr, int[] indices, int start, int mid, int end){
    int i = start;                                              // pointer to  lArray
    int j = mid + 1;                                            // pointer to rArray

    int leftShift = 0;                                         // counting shift for sorting

    int newIndices[] = new int[end - start + 1];

    int k = 0;                                                  // sorted pointer

    while(i <= mid && j <= end ){
      if(arr[indices[i]] > arr[indices[j]]){
        newIndices[k] = indices[j];
        j++;
        leftShift++;
      }else{
        newIndices[k] = indices[i];
        count[indices[i]] += leftShift;
        i++;
      }
      k++;
    }

    while(i <= mid){
      newIndices[k] = indices[i];
      count[indices[i]] += leftShift;
      i++;
      k++;
    }
    while(j <= end){
      newIndices[k] = indices[j];
      j++;
      k++;
    }

    for(int x = start; x <= end; x++)
      indices[x] = newIndices[x - start];                     // returning merged array
  }

  public List<Integer> countSmaller_merge_sort(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    int[] indices = new int[n]; // record the index. we are going to sort this array
    for (int i = 0; i < n; i++) {
      indices[i] = i;
    }
    // sort indices with their corresponding values in nums, i.e., nums[indices[i]]
    mergeSort(indices, 0, n, result, nums);
    // change int[] to List to return
    List<Integer> resultToReturn = new ArrayList<Integer>(n);
    for (int i : result) {
      resultToReturn.add(i);
    }
    return resultToReturn;
  }

  private void mergeSort(int[] indices, int left, int right, int[] result, int[] nums) {
    if (right - left <= 1) {
      return;
    }
    int mid = (left + right) / 2;
    mergeSort(indices, left, mid, result, nums);
    mergeSort(indices, mid, right, result, nums);
    merge(indices, left, right, mid, result, nums);
  }

  private void merge(int[] indices, int left, int right, int mid, int[] result, int[] nums) {
    // merge [left, mid) and [mid, right)
    int i = left; // current index for the left array
    int j = mid; // current index for the right array
    // use temp to temporarily store sorted array
    List<Integer> temp = new ArrayList<Integer>(right - left);
    while (i < mid && j < right) {
      if (nums[indices[i]] <= nums[indices[j]]) {
        // j - mid numbers jump to the left side of indices[i]
        result[indices[i]] += j - mid;
        temp.add(indices[i]);
        i++;
      } else {
        temp.add(indices[j]);
        j++;
      }
    }
    // when one of the subarrays is empty
    while (i < mid) {
      // j - mid numbers jump to the left side of indices[i]
      result[indices[i]] += j - mid;
      temp.add(indices[i]);
      i++;
    }
    while (j < right) {
      temp.add(indices[j]);
      j++;
    }
    // restore from temp
    for (int k = left; k < right; k++) {
      indices[k] = temp.get(k - left);
    }
  }

  public List<Integer> countSmaller_fenwick_faster(int[] nums) {
    int[] fenwik = new int[20002];
    Integer result[] = new Integer[nums.length];
    for(int i=nums.length-1 ; i>=0; i--){
      int cnt = 0, val = nums[i]-1+10001; // to make all positive
      while(val>0){
        // System.out.println("1. "+val+" "+fenwik[val]);
        cnt += fenwik[val];
        val -= (val & -val);
      }
      result[i]=cnt;
      val=nums[i]+10001;
      while(val<=20001){
        // System.out.println("2. "+val);
        fenwik[val]++;
        val += (val & -val);
      }
    }
    return Arrays.asList(result);
  }

  private int nextPowerOfTwo(int n) {
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

  public List<Integer> countSmaller_segment_tree(int[] nums) {
    int offset = 10000;
    int n = 2 * offset + 1;
    int size = nextPowerOfTwo(n);
    int[] tree = new int[size * 2 - 1];
    List<Integer> res = new ArrayList<>();

    for (int i = nums.length - 1; i >= 0; i--) {
      int count = query(0, nums[i] + offset - 1, tree, 0, n-1, 0);
      res.add(count);
      update(nums[i] + offset, tree, 0, n-1, 0);
    }
    Collections.reverse(res);
    return res;
  }

  private void update(int idx, int[] tree, int start, int end, int pos) {
    if (idx < start || idx > end) {
      return;
    }
    if (start == end) {
      tree[pos] += 1;
      return;
    }

    int mid = start + (end - start) / 2;

    if (idx <= mid) {
      update(idx, tree, start, mid, 2 * pos + 1);
    } else {
      update(idx, tree, mid+1, end, 2 * pos + 2);
    }
    tree[pos] = tree[2 * pos + 1] + tree[2 * pos + 2];
  }

  private int query(int ql, int qr, int[] tree, int start, int end, int pos) {

    //No overlap
    //Full overlap
    //Partial overlap

    if (qr < start || ql > end) {
      return 0;
    } else if (start >= ql && end <= qr) {
      return tree[pos];
    } else {
      int mid = start + (end - start) / 2;
      return query(ql, qr, tree, start, mid, 2 * pos + 1) + query(ql, qr, tree, mid+1, end, 2 * pos + 2);
    }
  }

  public List<Integer> countSmaller(int[] nums) {
    int offset = 10000; // offset negative to non-negative
    int size = 2 * 10000 + 1; // total possible values in nums
    int[] tree = new int[size * 2];
    List<Integer> result = new ArrayList<Integer>();

    for (int i = nums.length - 1; i >= 0; i--) {
      int smaller_count = query(0, nums[i] + offset, tree, size);
      result.add(smaller_count);
      update(nums[i] + offset, 1, tree, size);
    }
    Collections.reverse(result);
    return result;
  }

  // implement segment tree
  private void update(int index, int value, int[] tree, int size) {
    index += size; // shift the index to the leaf
    // update from leaf to root
    tree[index] += value;
    while (index > 1) {
      index /= 2;
      tree[index] = tree[index * 2] + tree[index * 2 + 1];
    }
  }

  private int query(int left, int right, int[] tree, int size) {
    // return sum of [left, right)
    int result = 0;
    left += size; // shift the index to the leaf
    right += size;
    while (left < right) {
      // if left is a right node
      // bring the value and move to parent's right node
      if (left % 2 == 1) {
        result += tree[left];
        left++;
      }
      // else directly move to parent
      left /= 2;
      // if right is a right node
      // bring the value of the left node and move to parent
      if (right % 2 == 1) {
        right--;
        result += tree[right];
      }
      // else directly move to parent
      right /= 2;
    }
    return result;
  }
}
