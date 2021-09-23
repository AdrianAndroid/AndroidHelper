package com.offer;

/**
 * Time:2021/6/21 19:46
 * Author:
 * Description:
 */
public class offer04 {


    /*
    [
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        System.out.println(new offer04().findNumberIn2DArray(arr, 5));
        System.out.println(new offer04().findNumberIn2DArray(arr, 20));
    }


    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        return method1(matrix, target);
    }
    // 暴力解法
    public boolean method1(int[][] matrix, int target) {
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                if(target == anInt) return true;
            }
        }
        return false;
    }


    // 单独的二分法
    public boolean method2(int[][] matrix, int target) {
        int l,r,m;
        for (int[] ints : matrix) {
            l = 0; r = ints.length - 1;
            while (l < r) {
                m = (l + r) / 2; //中间一个
                if(ints[m] == target)
                    return true;
                else if(ints[m] > ints[l])
                    l = m + 1;
                else
                    r = m - 1;

            }
        }
        return false;
    }

}
