public class UniquePaths {

    // =========================================================================
    // 1. BRUTE FORCE APPROACH (Recursion)
    // =========================================================================
    /*
     * STEP 1: Define base case. If m == 1 or n == 1, there is only 1 straight
     *         path remaining (either all Right moves or all Down moves).
     * STEP 2: Explore choices. At any position (m, n), you can either go Up (m-1)
     *         or Left (n-1) to work backward toward the start.
     * STEP 3: Sum the ways. Add paths from both recursive directions.
     */
    public static int uniquePathsBruteForce(int m, int n) {
        // Step 1: Base Case
        if (m == 1 || n == 1) {
            return 1;
        }

        // Step 2 & 3: Sum choices from moving Down (reducing m) and Right (reducing n)
        return uniquePathsBruteForce(m - 1, n) + uniquePathsBruteForce(m, n - 1);
    }


    // =========================================================================
    // 2. BETTER APPROACH (Dynamic Programming - Tabulation)
    // =========================================================================
    /*
     * STEP 1: Create a 2D table 'dp' of size m x n to cache path counts.
     * STEP 2: Initialize the first row and first column with 1 (only 1 way to reach them).
     * STEP 3: Loop through remaining cells from top-left (1, 1) to bottom-right.
     * STEP 4: Populate dp[i][j] = dp[i - 1][j] (from top) + dp[i][j - 1] (from left).
     * STEP 5: Return bottom-right target cell dp[m - 1][n - 1].
     */
    public static int uniquePathsBetter(int m, int n) {
        // Step 1: Create 2D DP table
        int[][] dp = new int[m][n];

        // Step 2: Set first column cells to 1 (only 1 way: moving Down)
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        // Step 2: Set first row cells to 1 (only 1 way: moving Right)
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        // Step 3 & 4: Fill table using overlapping subproblems
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        // Step 5: Result is stored in the target grid cell
        return dp[m - 1][n - 1];
    }


    // =========================================================================
    // 3. OPTIMAL APPROACH (Combinatorics / Mathematics)
    // =========================================================================
    /*
     * STEP 1: Calculate total moves needed to reach (m-1, n-1):
     *         Down moves = m - 1, Right moves = n - 1.
     *         Total steps (N) = (m - 1) + (n - 1) = m + n - 2.
     * STEP 2: Calculate combinations: Choose (m - 1) Down steps out of N total steps.
     *         Formula: C(N, r) where N = m + n - 2 and r = min(m - 1, n - 1).
     * STEP 3: Iteratively multiply and divide to prevent integer overflow.
     */
    public static int uniquePathsOptimal(int m, int n) {
        // Step 1: Total steps required
        int N = m + n - 2;

        // Step 2: Pick smaller count (r) to optimize loop iterations
        int r = Math.min(m - 1, n - 1);

        long result = 1; // Use long to prevent temporary calculation overflow

        // Step 3: Compute N C r = (N * (N-1) * ... * (N-r+1)) / (1 * 2 * ... * r)
        for (int i = 1; i <= r; i++) {
            result = result * (N - r + i) / i;
        }

        return (int) result;
    }


    // =========================================================================
    // MAIN METHOD
    // =========================================================================
    public static void main(String[] args) {
        int m = 3;
        int n = 7;

        System.out.println("Grid Size: " + m + " x " + n);
        System.out.println("-------------------------------------------");

        // 1. Brute Force Call
        System.out.println("1. Brute Force (Recursion) Output : " + uniquePathsBruteForce(m, n));

        // 2. Better Call
        System.out.println("2. Better (Dynamic Programming) Output : " + uniquePathsBetter(m, n));

        // 3. Optimal Call
        System.out.println("3. Optimal (Combinatorics) Output     : " + uniquePathsOptimal(m, n));
    }
}