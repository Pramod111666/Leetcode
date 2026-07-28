import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION
    // ==========================================
    public static int[][] mergeBruteForce(int[][] intervals) {
        int n = intervals.length;
        if (n <= 1) return intervals;

        // Step 1: Sort the intervals based on their start times.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> result = new ArrayList<>();

        // Step 2: Iterate through all intervals using an outer loop.
        for (int i = 0; i < n; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];

            // Step 3: If the current interval is already completely covered by the
            // last merged interval in our result list, we can skip it.
            if (!result.isEmpty() && result.get(result.size() - 1)[1] >= end) {
                continue;
            }

            // Step 4: Use an inner loop to check all subsequent intervals.
            // If they overlap with our current 'end', extend the 'end'.
            for (int j = i + 1; j < n; j++) {
                if (intervals[j][0] <= end) {
                    end = Math.max(end, intervals[j][1]);
                } else {
                    // Since the array is sorted, if the next start is greater than our
                    // current end, no further intervals will overlap. Break early.
                    break;
                }
            }

            // Step 5: Add the fully expanded interval to the result list.
            result.add(new int[]{start, end});
        }

        // Step 6: Convert the ArrayList back to a 2D array.
        return result.toArray(new int[result.size()][]);
    }

    // ==========================================
    // 2. BETTER SOLUTION (Single Pass with ArrayList)
    // ==========================================
    public static int[][] mergeBetter(int[][] intervals) {
        int n = intervals.length;
        if (n <= 1) return intervals;

        // Step 1: Sort the intervals based on start times.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> result = new ArrayList<>();

        // Step 2: Iterate through the intervals exactly once.
        for (int i = 0; i < n; i++) {
            // Step 3: If the result list is empty, OR the current interval does NOT overlap
            // with the last interval in the result list, add it as a new interval.
            if (result.isEmpty() || result.get(result.size() - 1)[1] < intervals[i][0]) {
                result.add(new int[]{intervals[i][0], intervals[i][1]});
            }
            // Step 4: If there is an overlap, merge them by updating the end time
            // of the last interval in the result list.
            else {
                result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], intervals[i][1]);
            }
        }

        // Step 5: Convert and return as a 2D array.
        return result.toArray(new int[result.size()][]);
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION (In-Place Array Modification)
    // ==========================================
    public static int[][] mergeOptimal(int[][] intervals) {
        int n = intervals.length;
        if (n <= 1) return intervals;

        // Step 1: Sort the intervals based on start times.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Step 2: Use a pointer to keep track of the index of the last merged
        // interval directly within the original array to save space.
        int validIndex = 0;

        // Step 3: Iterate through the array starting from the second element.
        for (int i = 1; i < n; i++) {

            // Step 4a: If the current interval overlaps with the interval at validIndex,
            // update the end time of the interval at validIndex.
            if (intervals[i][0] <= intervals[validIndex][1]) {
                intervals[validIndex][1] = Math.max(intervals[validIndex][1], intervals[i][1]);
            }
            // Step 4b: If they do not overlap, move the validIndex forward and
            // overwrite that position with the current interval.
            else {
                validIndex++;
                intervals[validIndex] = intervals[i];
            }
        }

        // Step 5: Return a copy of the array containing only the valid merged intervals.
        return Arrays.copyOfRange(intervals, 0, validIndex + 1);
    }

    // ==========================================
    // HELPER METHOD (For Deep Copying Arrays)
    // ==========================================
    private static int[][] deepCopy(int[][] original) {
        int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    // ==========================================
    // MAIN METHOD (For testing in IDE)
    // ==========================================
    public static void main(String[] args) {
        // Test case 1
        int[][] intervals1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println("--- Test Case 1 ---");
        System.out.println("Original: " + Arrays.deepToString(intervals1));

        // We use deep copies so the in-place sorting and modifying don't mess up subsequent tests
        System.out.println("Brute:    " + Arrays.deepToString(mergeBruteForce(deepCopy(intervals1))));
        System.out.println("Better:   " + Arrays.deepToString(mergeBetter(deepCopy(intervals1))));
        System.out.println("Optimal:  " + Arrays.deepToString(mergeOptimal(deepCopy(intervals1))));

        System.out.println();

        // Test case 2
        int[][] intervals2 = {{1, 4}, {4, 5}};
        System.out.println("--- Test Case 2 ---");
        System.out.println("Original: " + Arrays.deepToString(intervals2));
        System.out.println("Optimal:  " + Arrays.deepToString(mergeOptimal(deepCopy(intervals2))));

        System.out.println();

        // Test case 3
        int[][] intervals3 = {{4, 7}, {1, 4}};
        System.out.println("--- Test Case 3 ---");
        System.out.println("Original: " + Arrays.deepToString(intervals3));
        System.out.println("Optimal:  " + Arrays.deepToString(mergeOptimal(deepCopy(intervals3))));
    }
}