package com.google.springboot.service.algorithm;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Author kris
 * @Create 2024-10-15 22:02
 * @Description
 */
public class NextGreaterElement {
    //leetcode.503的变种，即如果題目變化數組為普通數組
    //tc : o(n)
    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        // 初始化结果数组，默认值为 -1（表示没有下一个更大元素）
        Arrays.fill(result, -1);
        Stack<Integer> stack = new Stack<>(); // 栈存储元素的索引

        // 只需要遍历一次数组
        for (int i = 0; i < n; i++) {
            // 如果当前元素大于栈顶元素对应的值，说明找到了”栈顶“的下一个更大元素
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                //更新栈顶元素的下一个更大元素，同时将栈顶元素pop删除
                result[stack.pop()] = nums[i];
            }
            // 将当前元素索引压入栈
            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4,1,2,3};
        int[] res = nextGreaterElements(nums);
        System.out.println(Arrays.toString(res));
    }
}

