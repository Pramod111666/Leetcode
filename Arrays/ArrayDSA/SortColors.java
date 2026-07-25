import java.util.Arrays;

public class SortColors {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION (Bubble Sort)
    // ==========================================
    public static void sortColorsBruteForce(int[] nums) {
        int n = nums.length;

        // Step 1: Use a simple sorting algorithm like Bubble Sort.
        // The outer loop keeps track of the number of passes.
        for (int i = 0; i < n - 1; i++) {

            // Step 2: The inner loop compares adjacent elements.
            for (int j = 0; j < n - i - 1; j++) {

                // Step 3: If the current element is greater than the next element,
                // they are in the wrong order, so we swap them.
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

    // ==========================================
    // 2. BETTER SOLUTION (Counting Sort / Two-Pass)
    // ==========================================
    public static void sortColorsBetter(int[] nums) {
        // Step 1: Initialize variables to keep track of the frequencies of 0, 1, and 2.
        int count0 = 0, count1 = 0, count2 = 0;

        // Step 2: First Pass - Traverse the array and count the occurrences of each color.
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) count0++;
            else if (nums[i] == 1) count1++;
            else if (nums[i] == 2) count2++;
        }

        // Step 3: Second Pass - Overwrite the original array based on the counts.
        // We start filling the array from index 0.
        int index = 0;

        // Step 4: Fill the array with the exact number of 0s we counted.
        for (int i = 0; i < count0; i++) {
            nums[index++] = 0;
        }

        // Step 5: Continue filling the array with the 1s.
        for (int i = 0; i < count1; i++) {
            nums[index++] = 1;
        }

        // Step 6: Finally, fill the remaining spaces with 2s.
        for (int i = 0; i < count2; i++) {
            nums[index++] = 2;
        }
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION (Dutch National Flag Algorithm / One-Pass)
    // ==========================================
    public static void sortColorsOptimal(int[] nums) {
        // Step 1: Initialize three pointers.
        // 'low' will keep track of where the next 0 should go (starts at 0).
        // 'high' will keep track of where the next 2 should go (starts at the end).
        // 'mid' is our current element explorer (starts at 0).
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        // Step 2: Loop until our explorer 'mid' crosses the 'high' boundary.
        // Anything past 'high' is already sorted as a 2.
        while (mid <= high) {

            // Step 3a: If we find a 0, it belongs on the left side.
            if (nums[mid] == 0) {
                // Swap the element at 'mid' with the element at 'low'.
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;

                // Move both 'low' and 'mid' forward because we successfully placed a 0.
                low++;
                mid++;
            }

            // Step 3b: If we find a 1, it is already in the correct middle section.
            else if (nums[mid] == 1) {
                // Just move our explorer 'mid' forward.
                mid++;
            }

            // Step 3c: If we find a 2, it belongs on the right side.
            else {
                // Swap the element at 'mid' with the element at 'high'.
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;

                // Move the 'high' pointer backward because we successfully placed a 2.
                // Note: We DO NOT increment 'mid' here. The element we just swapped from 'high'
                // to 'mid' hasn't been checked yet, so we must evaluate it on the next loop iteration.
                high--;
            }
        }
    }

    // ==========================================
    // MAIN METHOD (For testing in IDE)
    // ==========================================
    public static void main(String[] args) {
        // Test case 1
        int[] nums1 = {2, 0, 2, 1, 1, 0};
        System.out.println("Original Array 1: " + Arrays.toString(nums1));

        // Create copies to test all three methods independently
        int[] copy1Brute = nums1.clone();
        int[] copy1Better = nums1.clone();
        int[] copy1Optimal = nums1.clone();

        sortColorsBruteForce(copy1Brute);
        System.out.println("Brute Force Output: " + Arrays.toString(copy1Brute));

        sortColorsBetter(copy1Better);
        System.out.println("Better Output:      " + Arrays.toString(copy1Better));

        sortColorsOptimal(copy1Optimal);
        System.out.println("Optimal Output:     " + Arrays.toString(copy1Optimal));

        System.out.println("--------------------------------------------------");

        // Test case 2
        int[] nums2 = {2, 0, 1};
        System.out.println("Original Array 2: " + Arrays.toString(nums2));

        sortColorsOptimal(nums2); // Testing just optimal here for brevity
        System.out.println("Optimal Output:     " + Arrays.toString(nums2));
    }
}