import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {

    // ------------------------------------------------------------------
    // 1. BRUTE FORCE APPROACH
    // Logic: For every number x, check if (x+1) exists in the array using linear search.
    // ------------------------------------------------------------------
    public static int longestConsecutiveBruteForce(int[] nums) {
        if (nums.length == 0) return 0;
        int longest = 1;

        for (int i = 0; i < nums.length; i++) {
            int currentNum = nums[i];
            int currentStreak = 1;

            // Linearly search for currentNum + 1 sequentially
            while (linearSearch(nums, currentNum + 1)) {
                currentNum += 1;
                currentStreak += 1;
            }

            longest = Math.max(longest, currentStreak);
        }
        return longest;
    }

    private static boolean linearSearch(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) return true;
        }
        return false;
    }

    // ------------------------------------------------------------------
    // 2. BETTER APPROACH (Sorting)
    // Logic: Sort the array first. Consecutive elements will sit adjacent to each other.
    // ------------------------------------------------------------------
    public static int longestConsecutiveBetter(int[] nums) {
        if (nums.length == 0) return 0;

        // Step 1: Sort array in O(N log N)
        Arrays.sort(nums);

        int longest = 1;
        int currentStreak = 1;

        // Step 2: Iterate and count consecutive elements
        for (int i = 1; i < nums.length; i++) {
            // Ignore duplicate elements
            if (nums[i] != nums[i - 1]) {
                // If adjacent element is exactly 1 greater
                if (nums[i] == nums[i - 1] + 1) {
                    currentStreak++;
                } else {
                    // Sequence broke, reset streak
                    longest = Math.max(longest, currentStreak);
                    currentStreak = 1;
                }
            }
        }
        return Math.max(longest, currentStreak);
    }

    // ------------------------------------------------------------------
    // 3. OPTIMAL APPROACH (HashSet)
    // Logic: Store all elements in a HashSet. Identify "sequence starts"
    // (numbers without `num - 1` in the set) and build sequences forward.
    // ------------------------------------------------------------------
    public static int longestConsecutiveOptimal(int[] nums) {
        if (nums.length == 0) return 0;

        // Step 1: Insert all numbers into set for O(1) lookups
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        // Step 2: Iterate through distinct set elements
        for (int num : set) {
            // Check if 'num' is the START of a sequence (i.e., num - 1 doesn't exist)
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                // Count consecutive sequence length
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }

                longest = Math.max(longest, currentStreak);
            }
        }
        return longest;
    }

    // MAIN METHOD
    public static void main(String[] args) {
        int[] nums = {100, 4, 200, 1, 3, 2};

        System.out.println("--- Longest Consecutive Sequence ---");
        System.out.println("Brute Force Output: " + longestConsecutiveBruteForce(nums));
        System.out.println("Better (Sorting) Output: " + longestConsecutiveBetter(nums));
        System.out.println("Optimal (HashSet) Output: " + longestConsecutiveOptimal(nums));
    }
}