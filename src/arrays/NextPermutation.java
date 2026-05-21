package arrays;

/**
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
 *
 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
 *
 * The replacement must be in-place and use only constant extra memory.
 *
 * Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
 *
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 *
 */
public class NextPermutation {

    public static void main(String[] args) {
        NextPermutation o = new NextPermutation();


        o.nextPermutation(new int[]{7, 9, 8, 3, 2, 1}); //8 9 7 3 2 1
        o.nextPermutation(new int[]{4, 5, 6, 3, 2, 1});
        o.nextPermutation(new int[]{2, 3, 1});
        o.nextPermutation(new int[]{3, 2, 1});
        o.nextPermutation(new int[]{1});
        o.nextPermutation(new int[]{1, 2, 3});
    }

    public void nextPermutation(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        for (int i = nums.length-2; i >= 0; i--) {
            if (nums[i] < nums[i+1]) {
                int j = i+1, k=-1;
                while(j < nums.length) {
                    if (nums[j] > nums[i]) {
                        k = j;
                    }
                    j++;
                }
                int temp = nums[k];
                nums[k] = nums[i];
                nums[i] = temp;
                swap(nums, i+1, nums.length - 1);
                return;
            }
        }
        swap(nums, 0, nums.length - 1);
    }

    private void swap(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
