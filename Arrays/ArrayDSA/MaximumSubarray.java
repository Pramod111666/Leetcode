
import java.util.*;


public class MaximumSubarray {


    public static void main(String[] args) {
        // Test cases from the problem description
        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] nums2 = {1};
        int[] nums3 = {5, 4, -1, 7, 8};

        System.out.println("--- Testing Optimal Solution (Kadane's Algorithm) ---");
        System.out.println("Example 1: " + maxSubArrayOptimal(nums1)); // Expected: 6
        System.out.println("Example 2: " + maxSubArrayOptimal(nums2)); // Expected: 1
        System.out.println("Example 3: " + maxSubArrayOptimal(nums3)); // Expected: 23

        System.out.println("\n--- Testing Better Solution ---");
        System.out.println("Example 1: " + maxSubArrayBetter(nums1));

        System.out.println("\n--- Testing Brute Force Solution ---");
        System.out.println("Example 1: " + maxSubArrayBruteForce(nums1));
    }

    /**
     * APPROACH 1: BRUTE FORCE
     * Time Complexity: O(N^3)
     * Space Complexity: O(1)
     *
     * Logic:
     * - We generate every possible subarray.
     * - We use three nested loops:
     *   1. 'i' sets the starting point of the subarray.
     *   2. 'j' sets the ending point of the subarray.
     *   3. 'k' iterates from 'i' to 'j' to calculate the sum of that specific subarray.
     * - We update our maxSum if the calculated sum is strictly greater.
     * Note: This will result in a Time Limit Exceeded (TLE) on LeetCode for large arrays.
     */
    public static int maxSubArrayBruteForce(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int currentSum = 0;
                // Calculate sum of subarray from index i to j
                for (int k = i; k <= j; k++) {
                    currentSum += nums[k];
                }
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        return maxSum;
    }

    /**
     * APPROACH 2: BETTER
     * Time Complexity: O(N^2)
     * Space Complexity: O(1)
     *
     * Logic:
     * - We still generate every possible subarray, but we optimize the sum calculation.
     * - Instead of using a third loop to recalculate the sum from scratch,
     *   we maintain a running sum.
     * - When we expand our subarray end point 'j' by one element, we just add
     *   nums[j] to the existing sum of the subarray from 'i' to 'j-1'.
     */
    public static int maxSubArrayBetter(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            for (int j = i; j < n; j++) {
                // Add the current element to the running sum of the current starting point 'i'
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        return maxSum;
    }

    /**
     * APPROACH 3: OPTIMAL (Kadane's Algorithm)
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     *
     * Logic:
     * - We iterate through the array exactly once.
     * - We maintain a `currentSum` that adds up the elements as we go.
     * - If `currentSum` ever becomes negative, it means the subarray up to this
     *   point will only drag down the sum of any future subarrays we attach it to.
     * - Therefore, if `currentSum` < 0, we reset it to 0 (effectively abandoning
     *   the previous elements and starting a new subarray at the next element).
     * - We continuously update `maxSum` with the highest `currentSum` we've seen.
     * - This elegant approach guarantees the maximum subarray sum in a single pass,
     *   and correctly handles arrays with all negative numbers because we record the
     *   max sum before resetting `currentSum` to 0.
     */
    public static int maxSubArrayOptimal(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;

        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];

            // Update maxSum if we found a new maximum
            maxSum = Math.max(maxSum, currentSum);

            // If the running sum drops below zero, it's not worth keeping.
            // Reset to zero to start a fresh subarray evaluation on the next iteration.
            if (currentSum < 0) {
                currentSum = 0;
            }
        }

        return maxSum;
    }
}