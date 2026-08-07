import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FourSum {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION (4 Nested Loops + Set)
    // ==========================================
    public static List<List<Integer>> fourSumBruteForce(int[] nums, int target) {
        int n = nums.length;
        // Step 1: Use a Set to store unique quadruplets and prevent duplicate entries.
        Set<List<Integer>> resultSet = new HashSet<>();

        // Step 2: Run 4 nested loops to generate all possible 4-element combinations.
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {

                        // Step 3: Use long to prevent integer overflow when adding 4 numbers.
                        long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];

                        // Step 4: If sum matches target, sort the quadruplet and add it to Set.
                        if (sum == target) {
                            List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                            Collections.sort(temp);
                            resultSet.add(temp);
                        }
                    }
                }
            }
        }

        // Step 5: Convert the Set into a List to match the required output format.
        return new ArrayList<>(resultSet);
    }

    // ==========================================
    // 2. BETTER SOLUTION (3 Loops + HashSet Lookups)
    // ==========================================
    public static List<List<Integer>> fourSumBetter(int[] nums, int target) {
        int n = nums.length;
        Set<List<Integer>> resultSet = new HashSet<>();

        // Step 1: Fix the first two elements using two nested loops.
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                // Step 2: Use a HashSet to track seen elements for the 4th element lookup.
                Set<Long> hashSet = new HashSet<>();

                // Step 3: Iterate with the 3rd pointer.
                for (int k = j + 1; k < n; k++) {

                    // Step 4: Calculate the required 4th element (nums[l]) needed to reach the target.
                    // Equation: nums[i] + nums[j] + nums[k] + nums[l] = target
                    // => nums[l] = target - (nums[i] + nums[j] + nums[k])
                    long sum = (long) nums[i] + nums[j] + nums[k];
                    long fourth = (long) target - sum;

                    // Step 5: If the required 4th element exists in our HashSet, we found a quadruplet.
                    if (hashSet.contains(fourth)) {
                        List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], (int) fourth);
                        Collections.sort(temp);
                        resultSet.add(temp);
                    }

                    // Step 6: Store the current element into the HashSet for future lookups.
                    hashSet.add((long) nums[k]);
                }
            }
        }

        return new ArrayList<>(resultSet);
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION (Sorting + Two-Pointers)
    // ==========================================
    public static List<List<Integer>> fourSumOptimal(int[] nums, int target) {
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();

        // Step 1: Sort the array to enable two-pointer traversal and easy duplicate skipping.
        Arrays.sort(nums);

        // Step 2: Fix the 1st element with pointer 'i'.
        for (int i = 0; i < n; i++) {
            // Skip duplicate values for 'i' to avoid duplicate quadruplets.
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // Step 3: Fix the 2nd element with pointer 'j'.
            for (int j = i + 1; j < n; j++) {
                // Skip duplicate values for 'j'.
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                // Step 4: Initialize two pointers for the remaining sub-array.
                int k = j + 1;
                int l = n - 1;

                // Step 5: Two-pointer traversal.
                while (k < l) {
                    long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];

                    if (sum == target) {
                        // Found a valid quadruplet.
                        result.add(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));

                        // Move pointers forward.
                        k++;
                        l--;

                        // Skip duplicate values for 'k' and 'l'.
                        while (k < l && nums[k] == nums[k - 1]) k++;
                        while (k < l && nums[l] == nums[l + 1]) l--;
                    } else if (sum < target) {
                        // Sum is too small -> increment left pointer to get a larger value.
                        k++;
                    } else {
                        // Sum is too large -> decrement right pointer to get a smaller value.
                        l--;
                    }
                }
            }
        }

        return result;
    }

    // ==========================================
    // MAIN METHOD (For testing in IDE)
    // ==========================================
    public static void main(String[] args) {
        // Test case 1
        int[] nums1 = {1, 0, -1, 0, -2, 2};
        int target1 = 0;

        System.out.println("--- Test Case 1 ---");
        System.out.println("Input: " + Arrays.toString(nums1) + ", Target: " + target1);
        System.out.println("Brute Force Output: " + fourSumBruteForce(nums1, target1));
        System.out.println("Better Output:      " + fourSumBetter(nums1, target1));
        System.out.println("Optimal Output:     " + fourSumOptimal(nums1, target1));
        System.out.println("Expected Output:    [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]\n");

        // Test case 2
        int[] nums2 = {2, 2, 2, 2, 2};
        int target2 = 8;

        System.out.println("--- Test Case 2 ---");
        System.out.println("Input: " + Arrays.toString(nums2) + ", Target: " + target2);
        System.out.println("Brute Force Output: " + fourSumBruteForce(nums2, target2));
        System.out.println("Better Output:      " + fourSumBetter(nums2, target2));
        System.out.println("Optimal Output:     " + fourSumOptimal(nums2, target2));
        System.out.println("Expected Output:    [[2, 2, 2, 2]]\n");
    }
}