package com.offer;

public class MatrixPrint {

    public static void main(String[] args) {
//        int[][] a =
//                {
//                        {1, 2, 3},
//                        {4, 5, 6},
//                        {7, 8, 9}
//                };

        int[][] a = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        int[] ints = spiralOrder(a);
        for (int anInt : ints) {
            System.out.print(anInt + "  ");
        }
    }

    public static int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        // 定义边界
        int l = 0, r = matrix[0].length - 1; // 左右
        int t = 0, b = matrix.length - 1;//上下

        // 记录结果
        int[] res = new int[(r + 1) * (b + 1)];
        int index = 0;//索引
        while(true) {
            for (int i = l; i <= r; i++) res[index++] = matrix[t][i];
            if(++t > b) break;
            for (int i = t; i <= b; i++) res[index++] = matrix[i][r];
            if(l > --r) break;
            for (int i = r; i >= l; i--) res[index++] = matrix[b][i];
            if(t > --b) break;
            for (int i = b; i >= t; i--) res[index++] = matrix[i][l];
            if(++l > r) break;
        }
        return res;
    }


    public static int[] spiralOrder4(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        int l = 0, r = matrix[0].length - 1; // 从左到右
        int t = 0, b = matrix.length - 1;    // 从上到下
        int x = 0; // 统计个数
        int[] res = new int[(r + 1) * (b + 1)];
        while (true) {
            for (int i = l; i <= r; i++) res[x++] = matrix[t][i]; // left to right.
            if (++t > b) break;
            for (int i = t; i <= b; i++) res[x++] = matrix[i][r]; // top to bottom.
            if (l > --r) break;
            for (int i = r; i >= l; i--) res[x++] = matrix[b][i]; // right to left.
            if (t > --b) break;
            for (int i = b; i >= t; i--) res[x++] = matrix[i][l]; // bottom to top.
            if (++l > r) break;
        }
        return res;
    }

    // 环形打印
    public static int[] spiralOrder3(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return new int[0];
        int[] res = new int[matrix.length * matrix[0].length];
        int index = 0;
        for (int s = 0, e = matrix.length; s < e; s++, e--) {
            // 上
            for (int i = s; i < e; i++) res[index++] = matrix[s][i];
            // 右
            for (int i = s + 1; i < e; i++) res[index++] = matrix[i][e - 1];
            // 下
            for (int i = e - 2; i >= s; i--) res[index++] = matrix[e - 1][i];
            // 左
            for (int i = e - 2; i > s; i--) res[index++] = matrix[i][s];
        }
        return res;
    }


    public static void main3(String[] args) {
        int[][] a =
                {
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}
                };

        int length = a.length;
        int[] res = new int[3 * 3];
        int index = 0;
        for (int s = 0, e = length; s < e; e--, s++) { // start end
            for (int i = s; i < e; i++)
                res[index++] = (a[s][i]); // 上纵
            for (int i = s + 1; i < e; i++) // 竖
                res[index++] = (a[i][e - 1]);
            for (int i = e - 2; i >= s; i--) // 下
                res[index++] = (a[e - 1][i]);
            for (int i = e - 2; i > s; i--)
                res[index++] = (a[i][s]);
        }
        for (int re : res) {
            System.out.print(re);
        }
    }

    //环形打印二维数组
    //比如{
    //{1,2,3},
    //{4,5,6},
    //{7,8,9}} ,输出为1,2,3,6,9,8,7,4,5
    public static void main2(String[] args) {
        System.out.println("请输入元素个数：");

        int[][] a =
                {
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}
                };

//        Scanner sc = new Scanner(System.in);
//        int length = sc.nextInt();
//        length =(int) Math.sqrt(length);

//        int[][] a = new int[length][length];
        int length = a.length;
        int count = 1;
        for (int k = 0, n = length; k < n; n--, k++)        //环路在不断向内缩小，所以要n--
        {
            for (int i = k; i < n; i++)  //上横元素
                a[k][i] = count++;
            for (int i = k + 1; i < n; i++)  //右纵元素
                a[i][n - 1] = count++;
            for (int i = n - 2; i >= k; i--)  //下横元素
                a[n - 1][i] = count++;
            for (int i = n - 2; i > k; i--)  //左纵元素
                a[i][k] = count++;
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++)
                System.out.print(a[i][j] + " ");
            System.out.print("\n");
        }
    }

}
