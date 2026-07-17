import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle {

    public static void main(String[] args) {
        int testRows = 5;
        System.out.println("--- Running Pascal's Triangle Generator (Rows: " + testRows + ") ---\n");

        // 1. Test Brute Force Approach
        System.out.println("1. Brute Force Solution [O(N^3) Time, O(1) Auxiliary Space]:");
        List<List<Integer>> bruteResult = bruteForceGenerate(testRows);
        printTriangle(bruteResult);

        // 2. Test Better Approach
        System.out.println("\n2. Better Solution [O(N^2) Time, O(1) Auxiliary Space]:");
        List<List<Integer>> betterResult = betterGenerate(testRows);
        printTriangle(betterResult);

        // 3. Test Optimal Approach
        System.out.println("\n3. Optimal Solution [O(N^2) Time (No Multiplications), O(1) Auxiliary Space]:");
        List<List<Integer>> optimalResult = optimalGenerate(testRows);
        printTriangle(optimalResult);

        // Practical nCr Bonus Example
        System.out.println("\n--- Bonus: Practical nCr Practical Application ---");
        int totalEmployees = 10;
        int teamSize = 3;
        long combinations = nCr(totalEmployees, teamSize);
        System.out.println("Unique ways to pick a committee of " + teamSize + " from " + totalEmployees + " employees: " + combinations);
    }

    /**
     * APPROACH 1: BRUTE FORCE
     * Calculates every single cell independently using the raw math combination formula.
     * Time Complexity: O(N^3)
     */
    public static List<List<Integer>> bruteForceGenerate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();

        for (int r = 0; r < numRows; r++) {
            List<Integer> row = new ArrayList<>();
            for (int c = 0; c <= r; c++) {
                row.add((int) nCr(r, c));
            }
            result.add(row);
        }
        return result;
    }

    // Helper method to calculate nCr in O(r) time
    private static long nCr(int n, int r) {
        long res = 1;
        for (int i = 0; i < r; i++) {
            res = res * (n - i);
            res = res / (i + 1);
        }
        return res;
    }

    /**
     * APPROACH 2: BETTER
     * Generates each row linearly using a derivation pattern to skip redundant calculations.
     * Time Complexity: O(N^2)
     */
    public static List<List<Integer>> betterGenerate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();

        for (int r = 0; r < numRows; r++) {
            result.add(generateRow(r));
        }
        return result;
    }

    // Generates an entire row in O(rowNum) time
    private static List<Integer> generateRow(int rowNum) {
        List<Integer> row = new ArrayList<>();
        long val = 1;
        row.add((int) val);

        for (int col = 1; col <= rowNum; col++) {
            val = val * (rowNum - col + 1) / col;
            row.add((int) val);
        }
        return row;
    }

    /**
     * APPROACH 3: OPTIMAL (Dynamic Programming)
     * The classic LeetCode way. Builds the current row simply by adding the two elements
     * from the previous row directly above it.
     * Time Complexity: O(N^2) - Fast, lightweight addition only.
     */
    public static List<List<Integer>> optimalGenerate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        if (numRows <= 0) return result;

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                // Outer edges are always 1
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    // Left parent + right parent
                    List<Integer> prevRow = result.get(i - 1);
                    row.add(prevRow.get(j - 1) + prevRow.get(j));
                }
            }
            result.add(row);
        }
        return result;
    }

    // Helper method to format and visually print the triangle structure
    private static void printTriangle(List<List<Integer>> triangle) {
        for (List<Integer> row : triangle) {
            System.out.println(row);
        }
    }
}