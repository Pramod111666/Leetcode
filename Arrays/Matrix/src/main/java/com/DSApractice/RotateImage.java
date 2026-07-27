package com.DSApractice;

import java.util.Arrays;

public class RotateImage {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION (Using Extra Space)
    // ==========================================
    // Note: The problem asks for an in-place solution. This brute force
    // violates that constraint to demonstrate the raw mathematical pattern
    // of how coordinates change during a 90-degree rotation.
    public static void rotateBruteForce(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Create a new matrix of the same dimensions.
        int[][] rotated = new int[n][n];

        // Step 2: Traverse the original matrix row by row.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Step 3: Place the element in its new rotated position.
                // The 1st row becomes the last column, the 2nd row becomes the 2nd to last column, etc.
                // Formula: original (i, j) moves to new (j, n - 1 - i)
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }

        // Step 4: Copy the elements from the new matrix back into the original matrix.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rotated[i][j];
            }
        }
    }

    // ==========================================
    // 2. BETTER SOLUTION (4-Cell Cyclic Swap)
    // ==========================================
    // This achieves the O(1) space constraint by moving elements in groups of 4.
    public static void rotateBetter(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Loop through the matrix in concentric square layers.
        // We only need to go halfway deep into the matrix (n / 2).
        for (int i = 0; i < n / 2; i++) {

            // Step 2: Loop through the elements in the top row of the current layer.
            for (int j = i; j < n - i - 1; j++) {

                // Step 3: Save the top-left value in a temporary variable.
                int temp = matrix[i][j];

                // Step 4: Move the bottom-left value to the top-left position.
                matrix[i][j] = matrix[n - 1 - j][i];

                // Step 5: Move the bottom-right value to the bottom-left position.
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];

                // Step 6: Move the top-right value to the bottom-right position.
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];

                // Step 7: Place the saved top-left value into the top-right position.
                matrix[j][n - 1 - i] = temp;
            }
        }
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION (Transpose + Reverse)
    // ==========================================
    // This is the cleanest and most common interview solution.
    // Rotating a matrix 90 degrees clockwise is mathematically identical to
    // transposing the matrix, and then reversing every row.
    public static void rotateOptimal(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose the matrix.
        // Transposing means turning rows into columns (swapping matrix[i][j] with matrix[j][i]).
        for (int i = 0; i < n; i++) {
            // Notice j starts at i. If j started at 0, we would swap elements twice
            // and end up with the original matrix.
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row of the transposed matrix.
        for (int i = 0; i < n; i++) {
            // We only loop to n / 2 to swap the left half with the right half.
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                // Swap the current element with the element mirrored on the right side.
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

    // ==========================================
    // HELPER METHODS (For Deep Copying and Printing)
    // ==========================================
    private static int[][] deepCopy(int[][] original) {
        int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    // ==========================================
    // MAIN METHOD (For testing in IDE)
    // ==========================================
    public static void main(String[] args) {
        // Test case 1
        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("--- Test Case 1 ---");
        System.out.println("Original Matrix:");
        printMatrix(matrix1);

        // Create copies so each method modifies a fresh array
        int[][] copy1Brute = deepCopy(matrix1);
        int[][] copy1Better = deepCopy(matrix1);
        int[][] copy1Optimal = deepCopy(matrix1);

        rotateBruteForce(copy1Brute);
        System.out.println("Brute Force Output:");
        printMatrix(copy1Brute);

        rotateBetter(copy1Better);
        System.out.println("Better Output (Cyclic Swap):");
        printMatrix(copy1Better);

        rotateOptimal(copy1Optimal);
        System.out.println("Optimal Output (Transpose + Reverse):");
        printMatrix(copy1Optimal);

        // Test case 2
        int[][] matrix2 = {
                {5,  1,  9, 11},
                {2,  4,  8, 10},
                {13, 3,  6,  7},
                {15, 14, 12, 16}
        };

        System.out.println("--- Test Case 2 ---");
        System.out.println("Original Matrix:");
        printMatrix(matrix2);

        rotateOptimal(matrix2); // Testing just optimal here for brevity
        System.out.println("Optimal Output:");
        printMatrix(matrix2);
    }
}
