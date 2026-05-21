package arrays;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 01,2024 - 6:35 AM
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Always prefer Java "Generics" for this type of problem
 * #autobox #java-generics
 */
public class ContainsDuplicateII {

    public static void main(String[] args) {
        ContainsDuplicateII o = new ContainsDuplicateII();

        int[] nums = {1,2,3,4};
        System.out.println(o.containsNearbyDuplicate(Arrays.stream(nums).boxed().toArray(), 3));
        System.out.println(o.containsNearbyDuplicate(new Integer[]{1,2,3,1}, 3));
        System.out.println(o.containsNearbyDuplicate(new Double[]{1.0,2.0,3.0,1.0}, 3));
        System.out.println(o.containsNearbyDuplicate(new Double[]{1.1,2.1,3.1,1.0}, 3));
        System.out.println(o.containsNearbyDuplicate(new String[]{"1","2","3","1"}, 2));
    }

    public <T> boolean containsNearbyDuplicate(T[] nums, int k) {

        Map<T, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }

}
