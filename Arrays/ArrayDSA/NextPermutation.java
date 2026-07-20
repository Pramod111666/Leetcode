import java.util.*;

public class NextPermutation {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // --------------------------------------------------------
        // Test Case 1: Standard next permutation
        // --------------------------------------------------------
        int[] nums1Brute = {1, 2, 3};
        int[] nums1Better = {1, 2, 3};
        int[] nums1Optimal = {1, 2, 3};

        System.out.println("Test Case 1: [1, 2, 3]");

        // We clone or use separate arrays because the methods modify the array in-place
        solution.nextPermutationBruteForce(nums1Brute);
        System.out.println("Brute Force : " + Arrays.toString(nums1Brute));

        solution.nextPermutationBetter(nums1Better);
        System.out.println("Better      : " + Arrays.toString(nums1Better));

        solution.nextPermutationOptimal(nums1Optimal);
        System.out.println("Optimal     : " + Arrays.toString(nums1Optimal));
        System.out.println("--------------------------------------------------------");

        // --------------------------------------------------------
        // Test Case 2: Array is in descending order (wrap around)
        // --------------------------------------------------------
        int[] nums2 = {3, 2, 1};
        System.out.println("Test Case 2: [3, 2, 1]");
        solution.nextPermutationOptimal(nums2);
        System.out.println("Optimal     : " + Arrays.toString(nums2));
        System.out.println("--------------------------------------------------------");

        // --------------------------------------------------------
        // Test Case 3: Array with duplicate numbers
        // --------------------------------------------------------
        int[] nums3 = {1, 1, 5};
        System.out.println("Test Case 3: [1, 1, 5]");
        solution.nextPermutationOptimal(nums3);
        System.out.println("Optimal     : " + Arrays.toString(nums3));
        System.out.println("--------------------------------------------------------");
    }
}

class Solution {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION
    // Time: O(N! * N), Space: O(N!)
    // ==========================================
    public void nextPermutationBruteForce(int[] nums) {
        List<List<Integer>> allPermutations = new ArrayList<>();

        // We need a sorted copy to start generating from the lowest lexicographical order
        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);

        boolean[] used = new boolean[sortedNums.length];
        List<Integer> current = new ArrayList<>();
        generatePermutations(sortedNums, used, current, allPermutations);

        // Convert the current array to a List for easy comparison
        List<Integer> target = new ArrayList<>();
        for (int num : nums) {
            target.add(num);
        }

        // Find where our current permutation sits in the generated list
        int targetIndex = -1;
        for (int i = 0; i < allPermutations.size(); i++) {
            if (allPermutations.get(i).equals(target)) {
                targetIndex = i;
                break;
            }
        }

        // Get the next permutation (wrap around to index 0 if it's the last one)
        int nextIndex = (targetIndex + 1) % allPermutations.size();
        List<Integer> nextPerm = allPermutations.get(nextIndex);

        // Copy the result back to the original array
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nextPerm.get(i);
        }
    }

    private void generatePermutations(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // Skip duplicates to avoid redundant permutations
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                continue;
            }
            used[i] = true;
            current.add(nums[i]);
            generatePermutations(nums, used, current, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }

    // ==========================================
    // 2. BETTER SOLUTION
    // Time: O(N log N), Space: O(1)
    // ==========================================
    public void nextPermutationBetter(int[] nums) {
        int n = nums.length;
        int i = n - 2;

        // Step 1: Find the first decreasing element from the right
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // Step 2: If found, find the element just larger than nums[i] and swap
        if (i >= 0) {
            int j = n - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        // Step 3: Use built-in sort to reorder the suffix
        Arrays.sort(nums, i + 1, n);
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION
    // Time: O(N), Space: O(1)
    // ==========================================
    public void nextPermutationOptimal(int[] nums) {
        int n = nums.length;
        int i = n - 2;

        // Step 1: Find the break point
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // Step 2: Swap with the next strictly greater element
        if (i >= 0) {
            int j = n - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        // Step 3: Reverse the suffix to make it the smallest lexicographical order
        reverse(nums, i + 1, n - 1);
    }

    // --- Helper Methods ---
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
}