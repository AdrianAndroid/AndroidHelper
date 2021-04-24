package com.flannery;

import java.util.Arrays;

public class SearchDemo {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 2, 1, 4, 5, 6, 3};
        //bubbleSort(nums);
        //quickSort(nums, 0, nums.length - 1);
        //selectSort(nums);
        insertSort(nums);
        System.out.println(Arrays.toString(nums));
    }


    // 冒泡排序
    public static void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {//外层循环
            for (int j = i + 1; j < nums.length; j++) { //下一个循环
                if (nums[i] > nums[j]) {
                    //交换
                    int c = nums[i] ^ nums[j];
                    nums[i] = c ^ nums[i];
                    nums[j] = c ^ nums[j];
                }
            }
        }
    }

    // 快速排序， 把小的放左边，大的放右边，来回放值
    public static void quickSort(int a[], int l, int r) {
        if (l >= r) return;
        int i = l, j = r;
        int key = a[i];
        // l为起始点
        while (i < j) {
            while (i < j && a[j] >= key) j--; //有移动才换下一个
            if (i < j) {
                a[i] = a[j]; // 将a[j]空出来
                i++;
            }
            while (i < j && a[i] < key) i++; //有移动才换下一个
            if (i < j) {
                a[j] = a[i];
                j--;
            }
        }
        a[i] = key;
        quickSort(a, l, i - 1);
        quickSort(a, i + 1, r);
    }

    //选择排序
    public static void selectSort(int a[]) {
        final int length = a.length;
        if (length == 0) return;
        int min = 0;
        for (int i = 0; i < length - 1; i++) {
            min = i;
            for (int j = i + 1; j < length; j++) {
                if (a[min] > a[j]) min = j;
            }
            if (min != i) { //交换
                int c = a[min] ^ a[i];
                a[min] = a[min] ^ c;
                a[i] = a[i] ^ c;
            }
        }
    }

    public static void insertSort(int arr[]) {
        final int len = arr.length;
        for (int i = 1; i < len; i++) {
            int j = i;
            while (j > 0) {
                if (arr[j - 1] > arr[j]) { //跟它前面的交换
                    int c = arr[j - 1] ^ arr[j];
                    arr[j - 1] = arr[j - 1] ^ c;
                    arr[j] = arr[j] ^ c;
                }
                j--;
            }
        }
    }


    public static void mergeSort(int a[]) {
        // 分成两部分
    }

    public static void merge2(int a[], int l, int m, int r) {
//        int lIndex = l;
//        int rIndex = m+1;
//        while (lIndex <= m && rIndex <= r){
//            if(a[lIndex] > a[rIndex]){
//
//            }
//        }
    }


    public static int[] merge(int l[], int r[]) {
        int[] temp = new int[l.length + r.length];
        int lIndex = 0;
        int rIndex = 0;
        int index = 0;
        while (lIndex < l.length && rIndex < r.length) {
            if (l[lIndex] < r[rIndex]) {
                temp[index++] = l[lIndex++];
            } else {
                temp[index++] = r[rIndex++];
            }
        }
        while (lIndex < l.length) {
            temp[index++] = l[lIndex++];
        }
        while (rIndex < r.length) {
            temp[index++] = r[rIndex++];
        }
        return temp;
    }
}
