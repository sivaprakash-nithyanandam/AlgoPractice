package Sorting;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 * Sep 17,2024 - 9:01 PM
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/largest-number/description/
 *
 * Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.
 *
 * Since the result may be very large, so you need to return a string instead of an integer.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [10,2]
 * Output: "210"
 * Example 2:
 *
 * Input: nums = [3,30,34,5,9]
 * Output: "9534330"
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 109
 */
public class LargestNumber {

    public static void main(String[] args) {
        LargestNumber o = new LargestNumber();
        System.out.println(o.largestNumber_heapsort(new int[]{10, 2}));
        System.out.println(o.largestNumber_mergesort(new int[]{10, 2}));
    }

    private static final int RUN = 32;

    //TODO Go through - https://leetcode.com/problems/largest-number/
    public String largestNumber_timSort(int[] nums) {
        // Convert int array to Integer array for custom sorting
        Integer[] numsArray = Arrays.stream(nums)
                .boxed()
                .toArray(Integer[]::new);
        // Sort the numbers using custom Tim Sort
        timSort(numsArray);
        // Concatenate sorted numbers to form the largest number
        StringBuilder largestNum = new StringBuilder();
        for (int num : numsArray) {
            largestNum.append(num);
        }
        // Handle the case where the largest number is zero
        return largestNum.charAt(0) == '0' ? "0" : largestNum.toString();
    }

    private void insertionSort(Integer[] nums, int left, int right) {
        for (int i = left + 1; i <= right; ++i) {
            int temp = nums[i];
            int j = i - 1;
            while (j >= left && compare(temp, nums[j])) {
                nums[j + 1] = nums[j];
                --j;
            }
            nums[j + 1] = temp;
        }
    }

    private void merge(Integer[] nums, int left, int mid, int right) {
        Integer[] leftArr = Arrays.copyOfRange(nums, left, mid + 1);
        Integer[] rightArr = Arrays.copyOfRange(nums, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            if (compare(leftArr[i], rightArr[j])) {
                nums[k++] = leftArr[i++];
            } else {
                nums[k++] = rightArr[j++];
            }
        }
        while (i < leftArr.length) nums[k++] = leftArr[i++];
        while (j < rightArr.length) nums[k++] = rightArr[j++];
    }

    private void timSort(Integer[] nums) {
        int n = nums.length;
        // Sort small runs with insertion sort
        for (int i = 0; i < n; i += RUN) {
            insertionSort(nums, i, Math.min(i + RUN - 1, n - 1));
        }
        // Merge sorted runs
        for (int size = RUN; size < n; size *= 2) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                if (mid < right) {
                    merge(nums, left, mid, right);
                }
            }
        }
    }

    private boolean compare(int firstNum, int secondNum) {
        String firstStr = Integer.toString(firstNum);
        String secondStr = Integer.toString(secondNum);
        return (firstStr + secondStr).compareTo(secondStr + firstStr) > 0;
    }

    public String largestNumber_heapsort(int[] nums) {
        // Priority queue to order numbers using the custom comparison function
        PriorityQueue<String> maxHeap = new PriorityQueue<>((first, second) -> (second + first).compareTo(first + second));

        int totalLength = 0;

        // Convert integers to strings and push them into the priority queue
        for (int num : nums) {
            String strNum = Integer.toString(num);
            totalLength += strNum.length();
            maxHeap.offer(strNum);
        }

        // Build the result string from the priority queue
        StringBuilder result = new StringBuilder(totalLength);
        while (!maxHeap.isEmpty()) {
            result.append(maxHeap.poll());
        }

        // Handle edge case where the result might be "000...0"
        return result.charAt(0) == '0' ? "0" : result.toString();
    }
    public String largestNumber_mergesort(int[] nums) {
        int n = nums.length;
        String[] strings = new String[n];

        for (int i = 0; i < n; i++) {
            strings[i] = String.valueOf(nums[i]);
        }
        List<String> sorted = mergeSort(strings, 0, n-1);

        if (sorted.get(0).equals("0")) {
            return "0";
        }

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            res.append(sorted.get(i));
        }
        return res.toString();
    }

    private List<String> mergeSort(String[] strings, int left, int right) {
        if (left >= right) {
            return List.of(strings[left]);
        }

        int mid = left + (right - left)/2;
        List<String> l = mergeSort(strings, left, mid);
        List<String> r = mergeSort(strings, mid+1, right);
        return merge(l, r);
    }

    private List<String> merge(List<String> l, List<String> r) {

        int i = 0;
        int j = 0;
        List<String> res = new LinkedList<>();

        while (i < l.size() && j < r.size()) {
            if (compare(l.get(i), r.get(j))) {
                res.add(l.get(i));
                i++;
            } else {
                res.add(r.get(j));
                j++;
            }
        }

        while (i < l.size()) {
            res.add(l.get(i));
            i++;
        }

        while (j < r.size()) {
            res.add(r.get(j));
            j++;
        }

        return res;
    }

    public String largestNumber_quicksort(int[] nums) {

        int n = nums.length;
        String[] strings = new String[n];

        for (int i = 0; i < n; i++) {
            strings[i] = String.valueOf(nums[i]);
        }
        quickSort(strings, 0, n-1);

        if (strings[0].equals("0")) {
            return "0";
        }

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            res.append(strings[i]);
        }
        return res.toString();
    }

    private void quickSort(String[] strings, int left, int right) {

        if (left < right) {
            int pivot = partition(strings, left, right);
            quickSort(strings, left, pivot-1);
            quickSort(strings, pivot+1, right);
        }
    }

    private int partition(String[] strings, int left, int right) {

        String pivot = strings[right];
        int i = left-1;

        for (int j = left; j < right; j++) {
            if (compare(strings[j], pivot)) {
                i++;
                String t = strings[i];
                strings[i] = strings[j];
                strings[j] = t;
            }
        }
        i++;
        String t = strings[i];
        strings[i] = strings[right];
        strings[right] = t;

        return i;
    }

    private boolean compare(String str1, String str2) {
        String s1 = str1.concat(str2);
        String s2 = str2.concat(str1);
        return s1.compareTo(s2) > 0;
    }

    public String largestNumber_inbuild_sorting(int[] nums) {

        int n = nums.length;
        String[] strs = new String[n];

        for (int i = 0; i < n; i++) {
            strs[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(strs, (a,b) -> (b+a).compareTo(a+b));

        if (strs[0].equals("0")) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(strs[i]);
        }

        return sb.toString();
    }
}
