import java.util.Arrays;

public class MergeSortedArray {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION
    // ==========================================
    public static void mergeBruteForce(int[] nums1, int m, int[] nums2, int n) {
        // Step 1: Copy all elements from nums2 into the empty spaces (zeros) at the end of nums1.
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }

        // Step 2: Sort the entirety of nums1.
        // Since we just appended the elements, the array is no longer guaranteed to be sorted.
        // We use Java's built-in sort to re-order everything.
        Arrays.sort(nums1);
    }

    // ==========================================
    // 2. BETTER SOLUTION (Using Extra Space)
    // ==========================================
    public static void mergeBetter(int[] nums1, int m, int[] nums2, int n) {
        // Step 1: Create a temporary array to hold the merged result.
        int[] temp = new int[m + n];

        // Step 2: Initialize three pointers.
        int p1 = 0; // Pointer for nums1 valid elements
        int p2 = 0; // Pointer for nums2
        int index = 0; // Pointer for the temp array

        // Step 3: Compare elements from both arrays and put the smaller one into temp.
        while (p1 < m && p2 < n) {
            if (nums1[p1] <= nums2[p2]) {
                temp[index] = nums1[p1];
                p1++;
            } else {
                temp[index] = nums2[p2];
                p2++;
            }
            index++;
        }

        // Step 4: If there are remaining elements in nums1, copy them over.
        while (p1 < m) {
            temp[index] = nums1[p1];
            p1++;
            index++;
        }

        // Step 5: If there are remaining elements in nums2, copy them over.
        while (p2 < n) {
            temp[index] = nums2[p2];
            p2++;
            index++;
        }

        // Step 6: Copy the fully sorted temp array back into the original nums1 array.
        for (int i = 0; i < m + n; i++) {
            nums1[i] = temp[i];
        }
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION (Three Pointers from the Back)
    // ==========================================
    public static void mergeOptimal(int[] nums1, int m, int[] nums2, int n) {
        // Step 1: Initialize three pointers.
        // p1 points to the last valid element in nums1.
        int p1 = m - 1;
        // p2 points to the last element in nums2.
        int p2 = n - 1;
        // p points to the very end of nums1 (where the largest elements should go).
        int p = m + n - 1;

        // Step 2: Traverse from the back to the front.
        // By filling nums1 from the back, we avoid overwriting elements in nums1
        // that we haven't evaluated yet.
        while (p2 >= 0) {

            // Step 3a: If p1 is still valid and the element in nums1 is larger,
            // place it at the end and decrement p1.
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            }
            // Step 3b: Otherwise, the element in nums2 is larger (or equal, or p1 is exhausted).
            // Place it at the end and decrement p2.
            else {
                nums1[p] = nums2[p2];
                p2--;
            }

            // Step 4: Decrement the placement pointer regardless of which array we took from.
            p--;
        }
    }

    // ==========================================
    // MAIN METHOD (For testing in IDE)
    // ==========================================
    public static void main(String[] args) {
        // Test Case 1
        System.out.println("--- Test Case 1 ---");
        int m1 = 3, n1 = 3;
        int[] nums1_brute = {1, 2, 3, 0, 0, 0};
        int[] nums2_brute = {2, 5, 6};
        mergeBruteForce(nums1_brute, m1, nums2_brute, n1);
        System.out.println("Brute Force Output: " + Arrays.toString(nums1_brute));

        int[] nums1_better = {1, 2, 3, 0, 0, 0};
        int[] nums2_better = {2, 5, 6};
        mergeBetter(nums1_better, m1, nums2_better, n1);
        System.out.println("Better Output:      " + Arrays.toString(nums1_better));

        int[] nums1_optimal = {1, 2, 3, 0, 0, 0};
        int[] nums2_optimal = {2, 5, 6};
        mergeOptimal(nums1_optimal, m1, nums2_optimal, n1);
        System.out.println("Optimal Output:     " + Arrays.toString(nums1_optimal));
        System.out.println();

        // Test Case 2
        System.out.println("--- Test Case 2 ---");
        int[] nums1_2 = {1};
        int[] nums2_2 = {};
        mergeOptimal(nums1_2, 1, nums2_2, 0);
        System.out.println("Optimal Output:     " + Arrays.toString(nums1_2));
        System.out.println();

        // Test Case 3
        System.out.println("--- Test Case 3 ---");
        int[] nums1_3 = {0};
        int[] nums2_3 = {1};
        mergeOptimal(nums1_3, 0, nums2_3, 1);
        System.out.println("Optimal Output:     " + Arrays.toString(nums1_3));
    }
}