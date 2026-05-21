package Sliding;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Jul 20,2024 - 2:52 PM
 */

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/minimum-array-changes-to-make-differences-equal/description/
 *
 * You are given an integer array nums of size n where n is even, and an integer k.
 *
 * You can perform some changes on the array, where in one change you can replace any element in the array with any integer in the range from 0 to k.
 *
 * You need to perform some changes (possibly none) such that the final array satisfies the following condition:
 *
 * There exists an integer X such that abs(a[i] - a[n - i - 1]) = X for all (0 <= i < n).
 * Return the minimum number of changes required to satisfy the above condition.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,0,1,2,4,3], k = 4
 *
 * Output: 2
 *
 * Explanation:
 * We can perform the following changes:
 *
 * Replace nums[1] by 2. The resulting array is nums = [1,2,1,2,4,3].
 * Replace nums[3] by 3. The resulting array is nums = [1,2,1,3,4,3].
 * The integer X will be 2.
 *
 * Example 2:
 *
 * Input: nums = [0,1,2,3,3,6,5,4], k = 6
 *
 * Output: 2
 *
 * Explanation:
 * We can perform the following operations:
 *
 * Replace nums[3] by 0. The resulting array is nums = [0,1,2,0,3,6,5,4].
 * Replace nums[4] by 4. The resulting array is nums = [0,1,2,0,4,6,5,4].
 * The integer X will be 4.
 *
 *
 *
 * Constraints:
 *
 * 2 <= n == nums.length <= 105
 * n is even.
 * 0 <= nums[i] <= k <= 105
 */
public class MinimumArrayChangestoMakeDifferencesEqual {

    public static void main(String[] args) {
        MinimumArrayChangestoMakeDifferencesEqual o = new MinimumArrayChangestoMakeDifferencesEqual();
        System.out.println(o.minChanges(new int[]{1,0,1,2,4,3}, 4));
    }

    public int minChanges(int[] nums, int k) {
        Map<Integer,Integer> s = new HashMap<>();
        int n=nums.length;
        int[] f = new int[k+1];
        for(int i=0;i<n/2;i++){
            s.put((int)Math.abs(nums[i]-nums[n-1-i]),s.getOrDefault(Math.abs(nums[i]-nums[n-1-i]),0)+1);
            int min = Math.min(nums[i],nums[n-i-1]);
            int max = Math.max(nums[i],nums[n-i-1]);
            f[Math.max(k-min,max)]++;
        }
        for(int i=k-1;i>=0;i--){
            f[i]+=f[i+1];
        }
        int ans=n;

        for(int x:s.keySet()){
            int one = f[x]-s.get(x);
            int two = n/2 - f[x];
            ans=Math.min(ans,one+two*2);
        }
        return ans;
    }
}
