package com.flannery.sort;

import java.util.PriorityQueue;

public class SortDemo {

    public static void main(String[] args) {
        int[] array = {3, 38, 44, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        //buboSort(array); //2,3,4,5,15,19,26,27,36,38,44,46,47,48,50,
        //quickSort();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            System.out.print(",");
        }
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i] ^ array[j];
        array[i] = temp ^ array[i];
        array[j] = temp ^ array[j];
    }

    public static void buboSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) swap(array, i, j);
            }
        }
    }

    //快速排序
    public static void quickSort(int[] array, int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(array, begin, end); //选出参考点
        quickSort(array, begin, pivot - 1);
        quickSort(array, pivot + 1, end);
    }

    private static int partition(int[] a, int begin, int end) {
        // pivot: 标杆位置，counter: ⼩于pivot的元素的个数
        int pivot = end, couter = begin;
        for (int i = begin; i < end; i++) {
            if (a[i] < a[pivot]) {
                swap(a, couter, i);
                couter++;
            }
        }
        swap(a, pivot, couter);
        return couter;
    }

    // 归并排序代码
    public static void mergeSort(int[] array, int left, int right) {
        if (right <= left) return;
        int mid = (left + right) >> 1;
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];//中间数组
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }
        while (i <= mid) temp[k++] = arr[j++];
        while (j <= right) temp[k++] = arr[j++];
        for (int p = 0; p < temp.length; p++) {
            arr[left + p] = temp[p];
        }
    }

    // 堆排序
    private static void heap_sort(int[] a, int len) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i = 0; i < len; i++) {
            q.offer(a[i]);
        }
        for (int i = 0; i < len; i++) {
            a[i] = q.poll();
        }
    }

//    static void heapify(int[] array, int length, int i) {
//        int left = 2 * i + 1, right = 2 * i + 2；
//        int largest = i;
//        if (left < length && array[left] > array[largest]) {
//            largest = leftChild;
//        }
//        if (right < length && array[right] > array[largest]) {
//            largest = right;
//        }
//        if (largest != i) {
//            int temp = array[i]; array[i] = array[largest]; array[largest] = temp;
//            heapify(array, length, largest);
//        }
//    }
//    public static void heapSort(int[] array) {
//        if (array.length == 0) return;
//        int length = array.length;
//        for (int i = length / 2-1; i >= 0; i-)
//            heapify(array, length, i);
//        for (int i = length - 1; i >= 0; i--) {
//            int temp = array[0]; array[0] = array[i]; array[i] = temp;
//            heapify(array, i, 0);
//        }
//    }
}
