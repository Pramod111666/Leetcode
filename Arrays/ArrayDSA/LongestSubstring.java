import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstring {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // ==========================================
    // Logic:
    // - Generate all possible substrings using two nested loops.
    // - For each substring, check if it contains all unique characters.
    // - Keep track of the maximum length found so far.
    //
    // Time Complexity: O(N^3) - Two loops to generate substrings O(N^2), and a helper function taking O(N) to check uniqueness.
    // Space Complexity: O(N) or O(min(N, M)) - Set space needed to store characters during the uniqueness check.
    public static int lengthOfLongestSubstringBruteForce(String s) {
        int maxLength = 0;
        int n = s.length();

        // Step 1: Loop for the starting index of the substring
        for (int i = 0; i < n; i++) {
            // Step 2: Loop for the ending index of the substring
            for (int j = i; j < n; j++) {
                // Step 3: Check if the current substring s[i..j] has all unique characters
                if (hasAllUniqueChars(s, i, j)) {
                    // Step 4: Update the max length if this valid substring is longer
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
    }

    // Helper method to check if characters in s[start...end] are unique
    private static boolean hasAllUniqueChars(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i <= end; i++) {
            char ch = s.charAt(i);
            // If character is already in the set, duplicate found
            if (set.contains(ch)) {
                return false;
            }
            set.add(ch);
        }
        return true;
    }

    // ==========================================
    // 2. BETTER APPROACH (Sliding Window using Set)
    // ==========================================
    // Logic:
    // - Use a two-pointer sliding window approach (`left` and `right`).
    // - Expand the window by moving `right` and adding characters to a HashSet.
    // - If a duplicate character is encountered, shrink the window from the `left` by removing characters one by one until the duplicate is gone.
    //
    // Time Complexity: O(2 * N) = O(N) - In the worst case, each character is visited twice (once by `right` and once by `left`).
    // Space Complexity: O(N) or O(min(N, M)) - HashSet stores up to N unique characters.
    public static int lengthOfLongestSubstringBetter(String s) {
        int maxLength = 0;
        Set<Character> set = new HashSet<>();
        int left = 0;

        // Step 1: Expand the window using the right pointer
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // Step 2: If duplicate found, shrink the window from left until duplicate is removed
            while (set.contains(currentChar)) {
                set.remove(s.charAt(left));
                left++;
            }

            // Step 3: Add current character to set and update max length
            set.add(currentChar);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (Sliding Window using Map / Index Array)
    // ==========================================
    // Logic:
    // - Optimize the sliding window so `left` jumps directly past the duplicate character instead of shrinking step-by-step.
    // - Store the latest index of each character in a HashMap (or direct array).
    // - If `currentChar` was seen previously at `lastIndex`, update `left = max(left, lastIndex + 1)`.
    //
    // Time Complexity: O(N) - Single pass where the `right` pointer iterates through the string once.
    // Space Complexity: O(N) or O(min(N, M)) - HashMap stores character to last seen index mapping (M is size of character set).
    public static int lengthOfLongestSubstringOptimal(String s) {
        int maxLength = 0;
        // Map to store character -> its most recent index + 1
        Map<Character, Integer> charMap = new HashMap<>();

        int left = 0;
        // Step 1: Iterate through string with right pointer
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // Step 2: If character seen before, move left pointer directly to right of previous occurrence
            if (charMap.containsKey(currentChar)) {
                // Ensure left only moves forward, never backward
                left = Math.max(left, charMap.get(currentChar));
            }

            // Step 3: Calculate length of current window and update max
            maxLength = Math.max(maxLength, right - left + 1);

            // Step 4: Store next valid start position for current character
            charMap.put(currentChar, right + 1);
        }

        return maxLength;
    }

    // ==========================================
    // MAIN METHOD
    // ==========================================
    public static void main(String[] args) {
        String[] testCases = {"abcabcbb", "bbbbb", "pwwkew", ""};

        System.out.println("--- Longest Substring Without Repeating Characters ---\n");

        for (String test : testCases) {
            System.out.println("Input String: \"" + test + "\"");
            System.out.println("Brute Force Result : " + lengthOfLongestSubstringBruteForce(test));
            System.out.println("Better Result      : " + lengthOfLongestSubstringBetter(test));
            System.out.println("Optimal Result     : " + lengthOfLongestSubstringOptimal(test));
            System.out.println("----------------------------------------------");
        }
    }
}