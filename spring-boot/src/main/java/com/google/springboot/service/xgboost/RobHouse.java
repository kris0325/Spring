package com.google.springboot.service.xgboost;

/**
 * @Author kris
 * @Create 2024-07-12 12:32
 * @Description 打家劫舍ii
 */
public class RobHouse {

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        //start to rob from the first house nums[0], so not consider to rob the last house nums[length-1]
        int robFromFirst = robLinear(nums, 0, nums.length - 2);
        // start to rob from the second house num[1], so consider to rob the last house nums[length-1]
        int robFromSecond = robLinear(nums, 1, nums.length - 1);
        return Math.max(robFromFirst, robFromSecond);
    }

    public static int robLinear(int[] nums, int start, int end) {
        if (start == end) {
            return nums[start];
        }
        int[] dp = new int[nums.length];
        dp[start] = nums[start];
        dp[start+1] = Math.max(nums[start], nums[start + 1]);
        for (int i = start + 2; i <= end; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[end];
    }

//        int result = 0;
//        //[1,2,8,1,10,9,3]
//        for (int i = 0; i < stores.length - 1; i = i + 2) {
//            //find every step max num
//            int currentStore = stores[i];
//            int j = i+1;
//            for (; j < stores.length - 2; j++) {
//                if (currentStore < stores[j] && stores[j] > stores[j + 1]) {
//                    currentStore = stores[j];
//                    break;
//                }
//            }
//            i = j+1;
//
//            System.out.println("currentStore:" + currentStore);
//            System.out.println("index:" + j);
//            result += currentStore;
////            int currentStore = Math.max(stores[i], stores[i + 1]);
//
//            result += currentStore;
//        }
//        return result;


    public static void main(String[] args) {
        int[] stores = {1, 2, 8, 1, 10, 9, 3};
        System.out.println(rob(stores));
    }

}
