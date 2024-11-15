package com.google.springboot.utils.sort;
import java.util.Arrays;

/**
 * @Author kris
 * @Create 2024-10-28 12:01
 * @Description
 * refer->
 * https://www.youtube.com/watch?v=j-DqQcNPGbE
 */
public class HeapSort {

    /**
     * BottomUpHeapSort
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7}; // 待排序数组
        heapSort(arr); // 调用堆排序
        System.out.println("Sorted array is: " + Arrays.toString(arr)); // 输出排序后的数组
    }

    public static void heapSort(int arr[]) {
        int n = arr.length;

        // 自下而上构建最大堆（此时没有顺序，只是堆化，满足parent的值 大于children节点的值）
        for (int i = n / 2 - 1; i >= 0; i--) {
            //对index为i的元素 做 heapify
            siftDown(arr, n, i);
        }

        // 依次提取堆顶元素并重新调整堆
        for (int i = n - 1; i > 0; i--) {
            // 将堆顶元素（最大值）与当前最后一个元素交换，
            // 依次迭代后，较大的元素按顺序排到数组后面
            swap(arr, 0, i);
            // 调整新的堆, //对index为0的元素，(此时该元素为交换后的新元素)，做 heapify
            siftDown(arr, i, 0);
        }
    }

    // 自下而上调整堆
    private static void siftDown(int arr[], int n, int i) {
        int largest = i; // 初始化最大值为根节点
        int leftChild = 2 * i + 1; // 左子节点索引
        int rightChild = 2 * i + 2; // 右子节点索引

        // 如果左子节点大于根节点，更新最大值
        if (leftChild < n && arr[leftChild] > arr[largest]) {
            largest = leftChild;
        }

        // 如果右子节点大于当前最大值，更新最大值
        if (rightChild < n && arr[rightChild] > arr[largest]) {
            largest = rightChild;
        }

        // 如果最大值不是根节点，交换并递归调整
        if (largest != i) {
            swap(arr, i, largest);
            siftDown(arr, n, largest); // 递归调整受影响的子树
        }
    }

    // 交换两个元素
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
