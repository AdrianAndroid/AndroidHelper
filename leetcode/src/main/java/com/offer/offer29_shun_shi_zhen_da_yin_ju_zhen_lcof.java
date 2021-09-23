package com.offer;

public class offer29_shun_shi_zhen_da_yin_ju_zhen_lcof {


    public static void main(String[] args) {
        System.out.println("[1,2,3,6,9,8,7,4,5]");
        for (int i : new offer29_shun_shi_zhen_da_yin_ju_zhen_lcof().spiralOrder(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        })) {
            System.out.print(i);
        }
    }

    public int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return new int[0];
        int left = 0, right = matrix[0].length - 1;
        int top = 0, bottom = matrix.length - 1; //真正的索引
        int[] res = new int[matrix[0].length * matrix.length]; //所有的值
        int index = 0; //从头开始
        while (true) {
            // 从左到右
            for (int i = left; i <= right; i++) res[index++] = matrix[top][i];
            if (++top > bottom) break;
            // 从上到下
            for (int i = top; i <= bottom; i++) res[index++] = matrix[i][right];
            if (--right < left) break;
            // 从右到左
            for (int i = right; i >= left; i--) res[index++] = matrix[bottom][i];
            if (--bottom < top) break;
            // 从下到上
            for (int i = bottom; i >= top; i--) res[index++] = matrix[i][left];
            if (++left > right) break;
        }

        return res;
    }


}
