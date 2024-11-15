package com.google.springboot.service.algorithm;

import java.util.Arrays;

public class MaxProfitBooking {
    /**
     * 请给出Java solution，注意最大利润是求的天数，注意给的2个test case的输出正确结果 分别5，100：“请给出Java solution："Datavisor. Tel interview :You are listing your house on aribnb. There are a bunch of booking requests. Each request is represented as an integer array with length of 2. The first element is start date, the second element is end date. Select booking requests wisely so that you can obtain maximum profit without conflict of schedule. You can assume everyday's price is same.
     * Example 1
     * Input [[1,2], [4,5], [7,7]]
     * Output 5
     *
     *  Example 2
     * Input [[4,5], [7,9], [1,100]]
     * Output 100"”
     * */
    /**
     * 解释： 这道题目是一个经典的动态规划问题，类似于"无重叠区间选择最大利润"。
     * 排序：首先我们根据结束时间对预约进行排序，这样确保我们可以依次选择无冲突的预约。
     * 动态规划：使用一个 dp 数组来存储到每一个预约为止可以获得的最大利润。对于每个预约，我们可以选择：
     * 不选择当前预约，那么最大利润就是前一个预约的最大利润，即 dp[i - 1]。
     * 选择当前预约，那么我们需要找到最后一个不与当前预约冲突的预约，并加上它的最大利润。
     * 找不冲突的预约：使用 findLastNonConflict 方法来找到最后一个不与当前预约冲突的预约，通过从当前预约向前遍历寻找结束时间小于当前预约的开始时间的预约。
     * 复杂度：
     * 时间复杂度：O(n^2)，因为对于每个预约，我们都需要找之前的所有预约来判断是否冲突。
     * 空间复杂度：O(n)，我们需要一个 dp 数组来存储中间结果。
     * */

    // Function to find maximum profit (days) from non-overlapping intervals
    public static int maxProfit(int[][] bookings) {
        // Sort the bookings by their end dates
        Arrays.sort(bookings, (a, b) -> Integer.compare(a[1], b[1]));

        // Array to store the maximum profit till each booking
        int[] dp = new int[bookings.length];

        // Initialize the first booking's profit as its own duration (end - start + 1)
        dp[0] = bookings[0][1] - bookings[0][0] + 1;

        for (int i = 1; i < bookings.length; i++) {
            // Profit by including the current booking
            int currentProfit = bookings[i][1] - bookings[i][0] + 1;

            // Find the latest non-conflicting booking
            int lastNonConflict = findLastNonConflict(bookings, i);

            if (lastNonConflict != -1) {
                currentProfit += dp[lastNonConflict];
            }

            // Max profit by either taking the current booking or skipping it
            dp[i] = Math.max(dp[i - 1], currentProfit);
        }

        // The maximum profit is stored in the last position of dp array
        return dp[dp.length - 1];
    }

    // Function to find the index of the last non-conflicting booking
    private static int findLastNonConflict(int[][] bookings, int index) {
        for (int i = index - 1; i >= 0; i--) {
            if (bookings[i][1] < bookings[index][0]) {
                return i;
            }
        }
        //not find
        return -1;
    }

    public static void main(String[] args) {
        // Test case 1
        int[][] bookings1 = {{1, 2}, {4, 5}, {7, 7}};
        System.out.println("Max profit for bookings1: " + maxProfit(bookings1)); // Expected output: 5

        // Test case 2
        int[][] bookings2 = {{4, 5}, {7, 9}, {1, 100}};
        System.out.println("Max profit for bookings2: " + maxProfit(bookings2)); // Expected output: 100
    }
}
