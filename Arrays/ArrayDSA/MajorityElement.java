import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MajorityElement {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION
    // ==========================================
    public static int majorityElementBruteForce(int[] nums) {
        int n = nums.length;

        // Step 1: Use an outer loop to select each element one by one.
        for (int i = 0; i < n; i++) {
            // Step 2: Initialize a counter for the current element.
            int count = 0;

            // Step 3: Use an inner loop to count how many times this element
            // appears in the entire array.
            for (int j = 0; j < n; j++) {
                if (nums[i] == nums[j]) {
                    count++;
                }
            }

            // Step 4: If the count strictly exceeds n / 2, we found our majority element.
            if (count > n / 2) {
                return nums[i];
            }
        }

        return -1; // Should not be reached given problem constraints
    }

    // ==========================================
    // 2. BETTER SOLUTION (Using HashMap)
    // ==========================================
    public static int majorityElementBetter(int[] nums) {
        int n = nums.length;

        // Step 1: Initialize a HashMap to store the frequency of each element.
        // Key = the number from the array, Value = its frequency.
        Map<Integer, Integer> map = new HashMap<>();

        // Step 2: Iterate through the array.
        for (int num : nums) {

            // Step 3: Update the frequency of the current element in the map.
            // map.getOrDefault fetches the current count (or 0 if it doesn't exist), then adds 1.
            map.put(num, map.getOrDefault(num, 0) + 1);

            // Step 4: As soon as an element's frequency strictly exceeds n / 2,
            // we can return it immediately.
            if (map.get(num) > n / 2) {
                return num;
            }
        }

        return -1;
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION (Boyer-Moore Voting Algorithm)
    // ==========================================
    // Logic: Since the majority element appears more than n/2 times,
    // its occurrences will outnumber the occurrences of all other elements combined.
    public static int majorityElementOptimal(int[] nums) {
        // Step 1: Initialize a candidate variable and a counter.
        int count = 0;
        int candidate = 0;

        // Step 2: Traverse the array.
        for (int num : nums) {

            // Step 3a: If our counter drops to 0, we assume the current number
            // is the new potential majority candidate.
            if (count == 0) {
                candidate = num;
            }

            // Step 3b: If the current number matches our candidate, increment the count.
            if (num == candidate) {
                count++;
            }
            // Step 3c: If it is a different number, decrement the count.
            // This is the "voting" mechanism where different numbers cancel each other out.
            else {
                count--;
            }
        }

        // Step 4: Because the majority element exists more than n/2 times,
        // it will survive the cancellations and remain as the final candidate.
        return candidate;
    }

    // ==========================================
    // MAIN METHOD (For testing in IDE)
    // ==========================================
    public static void main(String[] args) {
        // Test case 1
        int[] nums1 = {3, 2, 3};
        System.out.println("--- Test Case 1 ---");
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("Brute Force Output: " + majorityElementBruteForce(nums1));
        System.out.println("Better Output:      " + majorityElementBetter(nums1));
        System.out.println("Optimal Output:     " + majorityElementOptimal(nums1));
        System.out.println("Expected Output:    3\n");

        // Test case 2
        int[] nums2 = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("--- Test Case 2 ---");
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("Brute Force Output: " + majorityElementBruteForce(nums2));
        System.out.println("Better Output:      " + majorityElementBetter(nums2));
        System.out.println("Optimal Output:     " + majorityElementOptimal(nums2));
        System.out.println("Expected Output:    2\n");
    }
}