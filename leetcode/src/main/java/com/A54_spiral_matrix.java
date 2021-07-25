package com;

import java.util.ArrayList;
import java.util.List;

public class A54_spiral_matrix {

    public static void main(String[] args) {
        List<Integer> integers = new A54_spiral_matrix().spiralOrder(
                new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}
        );
        System.out.println("[1,2,3,6,9,8,7,4,5]");
        for (Integer integer : integers) {
            System.out.print(integer);
        }
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return list;
        // 定义4个方向
        int l = 0, r = matrix[0].length - 1, t = 0, b = matrix.length - 1;
        while (true) {
            // 左到右
            for (int i = l; i <= r; i++) list.add(matrix[t][i]);
            if (++t > b) break;
            // 上到下
            for (int i = t; i <= b; i++) list.add(matrix[i][r]);
            if (--r < l) break;
            // 右到左
            for (int i = r; i >= l; i--) list.add(matrix[b][i]);
            if (--b < t) break;
            // 下到上
            for (int i = b; i >= t; i--) list.add(matrix[i][l]);
            if (++l > r) break;
        }
        return list;
    }
}
