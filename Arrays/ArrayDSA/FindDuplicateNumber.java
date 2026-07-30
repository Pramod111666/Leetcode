import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FindDuplicateNumber {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION
    // ==========================================
    // Note: This approach strictly meets the O(1) space constraint and
    // doesn't modify the array, but it is too slow (Time Limit Exceeded) for large inputs.
    public static int findDuplicateBruteForce(int[] nums) {
        int n = nums.length;

        // Step 1: Use an outer loop to pick one number at a time.
        for (int i = 0; i < n; i++) {

            // Step 2: Use an inner loop to compare the chosen number with all subsequent numbers.
            for (int j = i + 1; j < n; j++) {

                // Step 3: If a match is found, we have found our duplicate.
                if (nums[i] == nums[j]) {
                    return nums[i];
                }
            }
        }
        return -1; // Should not reach here given problem constraints
    }

    // ==========================================
    // 2. BETTER SOLUTION (Using a HashSet / Boolean Array)
    // ==========================================
    // Note: This approach violates the problem's "constant extra space" constraint,
    // but it is the most intuitive O(N) time solution.
    public static int findDuplicateBetter(int[] nums) {
        // Step 1: Create a boolean array to keep track of numbers we have seen.
        // We use size nums.length because the numbers range from 1 to n.
        boolean[] seen = new boolean[nums.length];

        // Step 2: Iterate through the array exactly once.
        for (int num : nums) {

            // Step 3: Check if we have already marked this number as seen.
            // If yes, it means it is the duplicate.
            if (seen[num]) {
                return num;
            }

            // Step 4: If not seen yet, mark it as true for future iterations.
            seen[num] = true;
        }

        return -1;
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION (Floyd's Tortoise and Hare / Cycle Detection)
    // ==========================================
    // This perfectly meets all constraints: O(1) space, no array modification, O(N) time.
    // Logic: Since numbers are in the range [1, n] and there are n+1 numbers,
    // we can treat the array as a Linked List where index `i` points to index `nums[i]`.
    // The duplicate number creates a cycle in this list.
    public static int findDuplicateOptimal(int[] nums) {
        // Step 1: Initialize two pointers, slow (tortoise) and fast (hare).
        // Both start at the first element (index 0).
        int slow = nums[0];
        int fast = nums[0];

        // --- PHASE 1: Find the intersection point in the cycle ---
        do {
            // Step 2a: Move 'slow' pointer one step at a time.
            slow = nums[slow];

            // Step 2b: Move 'fast' pointer two steps at a time.
            fast = nums[nums[fast]];

            // Step 3: Loop continues until they meet inside the cycle.
        } while (slow != fast);

        // --- PHASE 2: Find the entrance to the cycle (the duplicate number) ---
        // Step 4: Move the 'slow' pointer back to the very beginning.
        // Keep the 'fast' pointer where it is (at the intersection point).
        slow = nums[0];

        // Step 5: Move BOTH pointers one step at a time until they meet again.
        // The mathematical property of Floyd's algorithm guarantees that the point
        // where they meet is the start of the cycle (which represents the duplicate).
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        // Step 6: Return the duplicate number.
        return slow;
    }

    // ==========================================
    // MAIN METHOD (For testing in IDE)
    // ==========================================
    public static void main(String[] args) {
        // Test case 1
        int[] nums1 = {1, 3, 4, 2, 2};
        System.out.println("--- Test Case 1 ---");
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("Brute Force Output: " + findDuplicateBruteForce(nums1));
        System.out.println("Better Output:      " + findDuplicateBetter(nums1));
        System.out.println("Optimal Output:     " + findDuplicateOptimal(nums1));
        System.out.println("Expected Output:    2\n");

        // Test case 2
        int[] nums2 = {3, 1, 3, 4, 2};
        System.out.println("--- Test Case 2 ---");
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("Optimal Output:     " + findDuplicateOptimal(nums2));
        System.out.println("Expected Output:    3\n");

        // Test case 3
        int[] nums3 = {3, 3, 3, 3, 3};
        System.out.println("--- Test Case 3 ---");
        System.out.println("Input: " + Arrays.toString(nums3));
        System.out.println("Optimal Output:     " + findDuplicateOptimal(nums3));
        System.out.println("Expected Output:    3\n");
    }
}