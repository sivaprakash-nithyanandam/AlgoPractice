package StacksAndQueues;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 *
 * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1,
 * return the area of the largest rectangle in the histogram.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: heights = [2,1,5,6,2,3]
 * Output: 10
 * Explanation: The above is a histogram where width of each bar is 1.
 * The largest rectangle is shown in the red area, which has an area = 10 units.
 * Example 2:
 *
 *
 * Input: heights = [2,4]
 * Output: 4
 *
 *
 * Constraints:
 *
 * 1 <= heights.length <= 105
 * 0 <= heights[i] <= 104
 */
public class LargestRectangleinHistogram {

  public static void main(String[] args) {
    LargestRectangleinHistogram o = new LargestRectangleinHistogram();
    System.out.println(o.largestRectangleArea(new int[]{0,3,2,5}));
    System.out.println(o.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    System.out.println(o.largestRectangleArea(new int[]{2, 2, 1, 1, 1}));
    System.out.println(o.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    System.out.println(o.maxHistogram(new int[]{1, 5, 6, 2, 3}));
  }

  public static int largestRectangleArea_faster(int[] height) {
    if (height == null || height.length == 0) {
      return 0;
    }
    int N = height.length;
    int[] stack = new int[N];
    int si = -1;
    int maxArea = 0;
    for (int i = 0; i < height.length; i++) {
      while (si != -1 && height[i] <= height[stack[si]]) {
        int j = stack[si--];
        int k = si == -1 ? -1 : stack[si];
        int curArea = (i - k - 1) * height[j];
        maxArea = Math.max(maxArea, curArea);
      }
      stack[++si] = i;
    }
    while (si != -1) {
      int j = stack[si--];
      int k = si == -1 ? -1 : stack[si];
      int curArea = (height.length - k - 1) * height[j];
      maxArea = Math.max(maxArea, curArea);
    }
    return maxArea;
  }

  public int largestRectangleArea(int[] heights) {
    int maxArea = 0;
    int area;
    Deque<Integer> stack = new LinkedList<Integer>();
    int i = 0;
    while (i < heights.length) {
      if (stack.isEmpty() || heights[stack.peek()] <= heights[i]) {
        stack.push(i);
        i++;
      } else {
        int currStack = stack.pop();
        if (stack.isEmpty()) {
          area = heights[currStack] * i;
        } else {
          area = heights[currStack] * (i-1-stack.peek());
        }
        maxArea = Math.max(maxArea, area);
      }
    }
    while (!stack.isEmpty()) {
      int currStack = stack.pop();
      if (stack.isEmpty()) {
        area = heights[currStack] * i;
      } else {
        area = heights[currStack] * (i-1-stack.peek());
      }
      maxArea = Math.max(maxArea, area);
    }
    return maxArea;
  }

  public int maxHistogram(int input[]){
    Deque<Integer> stack = new LinkedList<Integer>();
    int maxArea = 0;
    int area = 0;
    int i;
    for(i=0; i < input.length;){
      if(stack.isEmpty() || input[stack.peekFirst()] <= input[i]){
        stack.offerFirst(i++);
      }else{
        int top = stack.pollFirst();
        //if stack is empty means everything till i has to be
        //greater or equal to input[top] so get area by
        //input[top] * i;
        if(stack.isEmpty()){
          area = input[top] * i;
        }
        //if stack is not empty then everythin from i-1 to input.peek() + 1
        //has to be greater or equal to input[top]
        //so area = input[top]*(i - stack.peek() - 1);
        else{
          area = input[top] * (i - stack.peekFirst() - 1);
        }
        if(area > maxArea){
          maxArea = area;
        }
      }
    }
    while(!stack.isEmpty()){
      int top = stack.pollFirst();
      //if stack is empty means everything till i has to be
      //greater or equal to input[top] so get area by
      //input[top] * i;
      if(stack.isEmpty()){
        area = input[top] * i;
      }
      //if stack is not empty then everything from i-1 to input.peek() + 1
      //has to be greater or equal to input[top]
      //so area = input[top]*(i - stack.peek() - 1);
      else{
        area = input[top] * (i - stack.peekFirst() - 1);
      }
      if(area > maxArea){
        maxArea = area;
      }
    }
    return maxArea;
  }
}
