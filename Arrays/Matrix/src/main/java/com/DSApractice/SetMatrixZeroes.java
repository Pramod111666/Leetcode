package com.DSApractice;
import java.util.Arrays;

public class SetMatrixZeroes {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION
    // Time Complexity: O((m * n) * (m + n))
    // Space Complexity: O(m * n)
    // ==========================================
    public void setZeroesBruteForce(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] isZero = new boolean[m][n];

        // Step 1: Find all the original zeros
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    isZero[i][j] = true;
                }
            }
        }

        // Step 2: Set rows and columns to zero based on the boolean matrix
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isZero[i][j]) {
                    // Set entire row to 0
                    for (int c = 0; c < n; c++) {
                        matrix[i][c] = 0;
                    }
                    // Set entire column to 0
                    for (int r = 0; r < m; r++) {
                        matrix[r][j] = 0;
                    }
                }
            }
        }
    }

    // ==========================================
    // 2. BETTER SOLUTION
    // Time Complexity: O(m * n)
    // Space Complexity: O(m + n)
    // ==========================================
    public void setZeroesBetter(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[] row = new int[m];
        int[] col = new int[n];

        // Step 1: Mark the rows and columns that need to be zeroed
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = 1;
                    col[j] = 1;
                }
            }
        }

        // Step 2: Update the matrix using the marker arrays
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] == 1 || col[j] == 1) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION
    // Time Complexity: O(m * n)
    // Space Complexity: O(1)
    // ==========================================
    public void setZeroesOptimal(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        // This will track if the first column needs to be zeroed
        int col0 = 1;

        // Step 1: Traverse the matrix and mark the 1st row and 1st col
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    // Mark the row
                    matrix[i][0] = 0;
                    // Mark the column
                    if (j == 0) {
                        col0 = 0;
                    } else {
                        matrix[0][j] = 0;
                    }
                }
            }
        }

        // Step 2: Use the markers to set inner elements to zero
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Step 3: Handle the first row
        if (matrix[0][0] == 0) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }

        // Step 4: Handle the first column using the col0 variable
        if (col0 == 0) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    // This method is just to make pasting code back to LeetCode super easy for you!
    // Simply uncomment the version you want to submit.
    public void setZeroes(int[][] matrix) {
        setZeroesOptimal(matrix); // Defaulting to the best solution
    }

    // ==========================================
    // MAIN METHOD TO RUN IN YOUR IDE
    // ==========================================
    public static void main(String[] args) {
        SetMatrixZeroes sol = new SetMatrixZeroes();

        // Let's create a test matrix
        int[][] originalMatrix = {
                {0, 1, 2, 0},
                {3, 4, 5, 2},
                {1, 3, 1, 5}
        };

        System.out.println("--- Original Matrix ---");
        printMatrix(originalMatrix);

        // 1. Test Brute Force
        int[][] matrix1 = cloneMatrix(originalMatrix);
        sol.setZeroesBruteForce(matrix1);
        System.out.println("\n--- Brute Force Solution Output ---");
        printMatrix(matrix1);

        // 2. Test Better Solution
        int[][] matrix2 = cloneMatrix(originalMatrix);
        sol.setZeroesBetter(matrix2);
        System.out.println("\n--- Better Solution Output ---");
        printMatrix(matrix2);

        // 3. Test Optimal Solution
        int[][] matrix3 = cloneMatrix(originalMatrix);
        sol.setZeroesOptimal(matrix3);
        System.out.println("\n--- Optimal Solution Output ---");
        printMatrix(matrix3);
    }

    // Helper method to print 2D arrays nicely
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    // Helper method to make deep copies of arrays so they don't overwrite each other
    private static int[][] cloneMatrix(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
}