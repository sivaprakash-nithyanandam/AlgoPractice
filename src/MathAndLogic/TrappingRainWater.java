package MathAndLogic;

/**
 * https://leetcode.com/problems/trapping-rain-water/
 *
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it is able to trap after raining.
 *
 * The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
 * In this case, 6 units of rain water (blue section) are being trapped. Thanks Marcos for contributing this image!
 *
 * Example:
 *
 * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 */

/**
 * Idea is to cascade the max left and max right columns, and find the min of those and subtract by itself gives rain
 * water it can store. Summation of all columns gives final answer.
 */
public class TrappingRainWater {

    public static void main(String[] args) {
        TrappingRainWater o = new TrappingRainWater();
        System.out.println(o.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        System.out.println(o.trap_faster(new int[]{2,0,1,0,2}));
        System.out.println(o.trap_faster(new int[]{1,1,1,1}));
    }

    public int trap(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }
        int[] maxHeightRight = new int[n];
        maxHeightRight[n - 1] = height[n-1];
        for (int i = n - 2; i >= 0; i--) {
            maxHeightRight[i] = Math.max(maxHeightRight[i+1], height[i]);
        }
        int maxHeightLeft = height[0];
        int rainWater = 0;
        for (int i = 1; i < n; i++) {
            rainWater += Math.max(Math.min(maxHeightLeft, maxHeightRight[i]) - height[i], 0);
            maxHeightLeft = Math.max(maxHeightLeft, height[i]);
        }
        return rainWater;
    }

    public int trap_faster(int[] height) {
        int left = 0;
        int right = height.length - 1;

        int res = 0;
        int maxLeft = 0, maxRight = 0;
        while (left <= right){
            if (height[left] <= height[right]){
                if (height[left] >= maxLeft){
                    maxLeft = height[left];
                } else {
                    res+= maxLeft - height[left];
                }
                left++;
            } else {
                if (height[right] >= maxRight){
                    maxRight = height[right];
                } else {
                    res+= maxRight - height[right];
                }
                right--;
            }
        }
        return res;
    }
}
