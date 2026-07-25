import java.util.Arrays;

public class BuySellStock {

    // ==========================================
    // 1. BRUTE FORCE SOLUTION
    // ==========================================
    public static int maxProfitBruteForce(int[] prices) {
        int maxProfit = 0;
        int n = prices.length;

        // Step 1: The outer loop picks a day to buy the stock.
        for (int i = 0; i < n - 1; i++) {

            // Step 2: The inner loop picks a future day to sell the stock.
            // It starts at i + 1 because we can only sell after we buy.
            for (int j = i + 1; j < n; j++) {

                // Step 3: Calculate the profit if we bought on day 'i' and sold on day 'j'.
                int currentProfit = prices[j] - prices[i];

                // Step 4: If this transaction yields a higher profit than our previous maximum,
                // update the maxProfit.
                if (currentProfit > maxProfit) {
                    maxProfit = currentProfit;
                }
            }
        }

        // Step 5: Return the highest profit found (will be 0 if no profit is possible).
        return maxProfit;
    }

    // ==========================================
    // 2. BETTER SOLUTION (Using Auxiliary Space)
    // ==========================================
    public static int maxProfitBetter(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;

        // Step 1: Create an array to store the maximum price we can get from day 'i' to the end.
        int[] maxFuturePrice = new int[n];

        // Step 2: Initialize the last element because there are no future days after it.
        maxFuturePrice[n - 1] = prices[n - 1];

        // Step 3: Iterate backwards to populate the maximum future prices.
        // For each day, the max future price is the maximum of the current day's price
        // and the max future price of the day after it.
        for (int i = n - 2; i >= 0; i--) {
            maxFuturePrice[i] = Math.max(prices[i], maxFuturePrice[i + 1]);
        }

        int maxProfit = 0;

        // Step 4: Iterate through the prices array one more time.
        // The maximum profit we can make by buying on day 'i' is the difference
        // between the max future price after day 'i' and the price on day 'i'.
        for (int i = 0; i < n; i++) {
            int currentProfit = maxFuturePrice[i] - prices[i];
            maxProfit = Math.max(maxProfit, currentProfit);
        }

        return maxProfit;
    }

    // ==========================================
    // 3. OPTIMAL SOLUTION (One Pass)
    // ==========================================
    public static int maxProfitOptimal(int[] prices) {
        // Step 1: Initialize minPrice to a very high value (infinity).
        // This keeps track of the lowest buying price we have seen so far.
        int minPrice = Integer.MAX_VALUE;

        // Step 2: Initialize maxProfit to 0.
        // This keeps track of the maximum profit we can achieve.
        int maxProfit = 0;

        // Step 3: Iterate through each price in the array exactly once.
        for (int i = 0; i < prices.length; i++) {

            // Step 4a: If the current price is lower than the lowest price seen so far,
            // update minPrice. We always want to buy at the lowest possible price.
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
            // Step 4b: Otherwise, calculate how much profit we would make if we sold today.
            // (Current price minus the lowest price we've seen).
            else {
                int currentProfit = prices[i] - minPrice;

                // Step 5: If selling today yields a better profit than our recorded maxProfit,
                // update the maxProfit.
                if (currentProfit > maxProfit) {
                    maxProfit = currentProfit;
                }
            }
        }

        // Step 6: Return the maximum profit recorded.
        return maxProfit;
    }

    // ==========================================
    // MAIN METHOD (For testing in IDE)
    // ==========================================
    public static void main(String[] args) {
        // Test case 1
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        System.out.println("Test Case 1: " + Arrays.toString(prices1));
        System.out.println("Brute Force Output: " + maxProfitBruteForce(prices1));
        System.out.println("Better Output:      " + maxProfitBetter(prices1));
        System.out.println("Optimal Output:     " + maxProfitOptimal(prices1));
        System.out.println("Expected Output:    5");

        System.out.println("--------------------------------------------------");

        // Test case 2
        int[] prices2 = {7, 6, 4, 3, 1};
        System.out.println("Test Case 2: " + Arrays.toString(prices2));
        System.out.println("Brute Force Output: " + maxProfitBruteForce(prices2));
        System.out.println("Better Output:      " + maxProfitBetter(prices2));
        System.out.println("Optimal Output:     " + maxProfitOptimal(prices2));
        System.out.println("Expected Output:    0");
    }
}