import java.util.HashMap;
import java.util.Map;

public class LongestSubarrayWithSumK {

    // ------------------------------------------------------------------
    // 1. BRUTE FORCE APPROACH
    // Logic: Generate all possible subarrays using nested loops and calculate sums.
    // ------------------------------------------------------------------
    public static int maxLenBruteForce(int[] nums, int k) {
        int maxLen = 0;
        int n = nums.length;

        // Step 1: Pick starting index of subarray
        for (int i = 0; i < n; i++) {
            int currentSum = 0;

            // Step 2: Pick ending index of subarray and calculate running sum
            for (int j = i; j < n; j++) {
                currentSum += nums[j];

                // Step 3: If current sum equals K, update max length
                if (currentSum == k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }

    // ------------------------------------------------------------------
    // 2. BETTER APPROACH (Prefix Sum + HashMap)
    // Logic: Store prefix sums in a HashMap. If (prefixSum - k) exists in the map,
    // a subarray with sum k exists between that stored index and current index.
    // (Note: This is the absolute optimal approach when negatives are present)
    // ------------------------------------------------------------------
    public static int maxLenPrefixSum(int[] nums, int k) {
        int maxLen = 0;
        long currentPrefixSum = 0;

        // Map stores <PrefixSum, FirstOccurredIndex>
        Map<Long, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            currentPrefixSum += nums[i];

            // Case 1: If prefix sum itself is equal to K
            if (currentPrefixSum == k) {
                maxLen = i + 1;
            }

            // Case 2: If (prefixSum - K) exists in map, we found a valid subarray
            long target = currentPrefixSum - k;
            if (map.containsKey(target)) {
                int previousIndex = map.get(target);
                maxLen = Math.max(maxLen, i - previousIndex);
            }

            // Case 3: Put prefixSum in map only if it doesn't already exist
            // (Preserves the earliest index to maximize subarray length)
            if (!map.containsKey(currentPrefixSum)) {
                map.put(currentPrefixSum, i);
            }
        }
        return maxLen;
    }

    // ------------------------------------------------------------------
    // 3. OPTIMAL APPROACH (Two Pointers / Sliding Window)
    // Logic: Dynamic window expansion (right pointer) and contraction (left pointer).
    // NOTE: Works strictly for non-negative numbers (positive numbers and zeros).
    // ------------------------------------------------------------------
    public static int maxLenTwoPointers(int[] nums, int k) {
        int left = 0, right = 0;
        long currentSum = 0;
        int maxLen = 0;
        int n = nums.length;

        while (right < n) {
            // Step 1: Expand window by adding current element
            currentSum += nums[right];

            // Step 2: Shrink window from left if sum exceeds K
            while (left <= right && currentSum > k) {
                currentSum -= nums[left];
                left++;
            }

            // Step 3: Check if window sum matches K
            if (currentSum == k) {
                maxLen = Math.max(maxLen, right - left + 1);
            }

            // Step 4: Move right pointer ahead
            right++;
        }
        return maxLen;
    }

    // MAIN METHOD
    public static void main(String[] args) {
        int[] nums = {-1, 1, 1};
        int k = 1;

        System.out.println("--- Longest Subarray with Sum K ---");
        System.out.println("Brute Force Output: " + maxLenBruteForce(nums, k));
        System.out.println("Prefix Sum Output: "  + maxLenPrefixSum(nums, k));

        // Demo with positive array for two-pointer approach
        int[] posNums = {10, 5, 2, 7, 1, 9};
        int posK = 15;
        System.out.println("Two Pointers (Positives array) Output: " + maxLenTwoPointers(posPos(posNums), posK));
    }

    private static int[] posPos(int[] arr) { return arr; }
}