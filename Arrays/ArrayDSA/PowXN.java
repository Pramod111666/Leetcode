public class PowXN {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION
    // ==========================================
    // Note: This solution is straightforward but will result in a Time Limit Exceeded (TLE)
    // error on LeetCode for extremely large powers (e.g., n = 2147483647).
    public static double myPowBruteForce(double x, int n) {
        // Step 1: Copy 'n' into a 'long' variable.
        // Why? The minimum value of a 32-bit integer is -2,147,483,648.
        // If we try to make it positive using -n, it overflows the 32-bit limit.
        // Using a 64-bit 'long' prevents this overflow.
        long power = n;

        // Step 2: If the power is negative, mathematically x^-n is equal to (1/x)^n.
        // We flip 'x' to its reciprocal and make 'power' positive.
        if (power < 0) {
            x = 1 / x;
            power = -power;
        }

        double result = 1.0;

        // Step 3: Simply multiply 'x' by itself 'power' times.
        for (long i = 0; i < power; i++) {
            result *= x;
        }

        return result;
    }

    // ==========================================
    // 2. BETTER SOLUTION (Binary Exponentiation - Recursive)
    // ==========================================git
    public static double myPowBetter(double x, int n) {
        long power = n;

        // Step 1: Handle the negative power just like the brute force approach.
        if (power < 0) {
            x = 1 / x;
            power = -power;
        }

        // Step 2: Call our recursive helper function.
        return solveRecursive(x, power);
    }

    private static double solveRecursive(double x, long n) {
        // Step 3: Base Case - Any number to the power of 0 is 1.0.
        if (n == 0) return 1.0;

        // Step 4: Recursively calculate the power for half of 'n'.
        // e.g., if calculating x^10, we first find x^5.
        double halfPower = solveRecursive(x, n / 2);

        // Step 5a: If 'n' is even (e.g., x^10), the result is (x^5) * (x^5).
        if (n % 2 == 0) {
            return halfPower * halfPower;
        }
        // Step 5b: If 'n' is odd (e.g., x^11), the result is (x^5) * (x^5) * x.
        else {
            return halfPower * halfPower * x;
        }
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION (Binary Exponentiation - Iterative)
    // ==========================================
    public static double myPowOptimal(double x, int n) {
        long power = n;

        // Step 1: Handle the negative power conversion.
        if (power < 0) {
            x = 1 / x;
            power = -power;
        }

        // Step 2: Initialize our answer variable.
        double ans = 1.0;

        // Step 3: Loop as long as the power is greater than 0.
        while (power > 0) {

            // Step 4a: If the current power is odd, we multiply our answer by 'x'
            // and reduce the power by 1 to make it even.
            if (power % 2 == 1) {
                ans = ans * x;
                power = power - 1;
            }

            // Step 4b: If the current power is even, we square 'x' (x = x * x)
            // and cut the power in half. This is the core of binary exponentiation.
            // By squaring the base, we drastically reduce the steps needed.
            else {
                x = x * x;
                power = power / 2;
            }
        }

        // Step 5: Return the final accumulated answer.
        return ans;
    }

    // ==========================================
    // MAIN METHOD (For testing in IDE)
    // ==========================================
    public static void main(String[] args) {
        // Test case 1
        double x1 = 2.00000;
        int n1 = 10;
        System.out.println("--- Test Case 1 ---");
        System.out.println("Input: x = " + x1 + ", n = " + n1);
        System.out.println("Brute Force: " + myPowBruteForce(x1, n1));
        System.out.println("Better:      " + myPowBetter(x1, n1));
        System.out.println("Optimal:     " + myPowOptimal(x1, n1));
        System.out.println("Expected:    1024.0\n");

        // Test case 2
        double x2 = 2.10000;
        int n2 = 3;
        System.out.println("--- Test Case 2 ---");
        System.out.println("Input: x = " + x2 + ", n = " + n2);
        // Note: Output might slightly differ at extreme decimal places due to floating-point precision math
        System.out.println("Optimal:     " + myPowOptimal(x2, n2));
        System.out.println("Expected:    9.26100\n");

        // Test case 3
        double x3 = 2.00000;
        int n3 = -2;
        System.out.println("--- Test Case 3 ---");
        System.out.println("Input: x = " + x3 + ", n = " + n3);
        System.out.println("Optimal:     " + myPowOptimal(x3, n3));
        System.out.println("Expected:    0.25000\n");
    }
}