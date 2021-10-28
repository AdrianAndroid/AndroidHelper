package com.flannery.dynamic_process;

import java.util.Arrays;
import java.util.List;

public class A120_triangle {

    private static List<List<Integer>> getListList() {
        return Arrays.asList(
                Arrays.asList(2),
                Arrays.asList(3, 4),
                Arrays.asList(6, 5, 7),
                Arrays.asList(4, 1, 8, 3)
        );
    }

    public static void main(String[] args) {

//        final int dynamic = new Solution().dynamic2(getListList());
//        System.out.println(dynamic);
        List<List<Integer>> listList = getListList();
        System.out.println(minimumTotal(listList));
    }

    public static int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) return 0;
        // 存放数据
        int[] dp = new int[triangle.size()+1]; //冗余出一行
        //全部遍历一遍  从后往前
        for (int i = triangle.size()-1; i >= 0; i--) { //倒数第二行开始
            List<Integer> cols = triangle.get(i);
            for (int j = 0; j < cols.size(); j++) { //本行比下一行大一个
                dp[j] = Math.min(dp[j], dp[j+1])+ cols.get(j);
            }
           //System.out.println(Arrays.toString(dp));
        }
        return dp[0];//存放在最前边
    }

    // 动态规划，用一个int[]保存
    public static int minimumTotal3(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) return 0;
        // 存放数据
        List<Integer> dp = triangle.get(triangle.size() - 1); //使用最后一行算作dp
        //全部遍历一遍  从后往前
        for (int i = triangle.size()-2; i >= 0; i--) { //倒数第二行开始
            List<Integer> cols = triangle.get(i);
            for (int j = 0; j < cols.size(); j++) { //本行比下一行大一个
                int res = Math.min(dp.get(j) , dp.get(j+1)) + cols.get(j);
                dp.set(j, res);
            }
            System.out.println(dp.toString());
        }
        return dp.get(0);//存放在最前边
    }
    // 动态规划， 替换里面的值
    public static int minimumTotal2(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) return 0;
        // 存放数据
        List<List<Integer>> dp = triangle;
        //全部遍历一遍  从后往前
        for (int i = dp.size() - 1; i > 0; i--) { //从后往前
            List<Integer> cols = dp.get(i);
            for (int j = 0; j < cols.size()-1; j++) { //从前往后
                // 上一个节点的值
                Integer num = dp.get(i - 1).get(j);
                dp.get(i-1).set(j, num + Math.min(cols.get(j), cols.get(j+1)));
            }
        }
        return dp.get(0).get(0);
    }

    // 递归return helper(triangle, 0, 0);//最顶点的就是结果
    public static int helper(List<List<Integer>> triangle, int level, int index) {
        // 终止条件
        if (level == triangle.size() || index == triangle.get(level).size()) {
            return 0; //越界了
        }
        // 处理相邻的两个路径和
        int left = helper(triangle, level + 1, index);
        int right = helper(triangle, level + 1, index + 1);

        return Math.min(left, right) + triangle.get(level).get(index);
    }

    static class Solution {

        Integer[][] memo;

        public int minimumTotal(List<List<Integer>> triangle) {
            //return dfs(triangle, 0, 0);//从头节点开始
            memo = new Integer[triangle.size()][triangle.size()]; //创建一个矩阵
            return -1;
        }

        private int dfs(List<List<Integer>> triangle, int i, int j) {
            if (i == triangle.size()) return 0; //到底了
            return Math.min(dfs(triangle, i + 1, j), dfs(triangle, i + 1, j + 1)) + triangle.get(i).get(j);
        }

        private int dfs2(List<List<Integer>> triangle, int i, int j) {
            if (i == triangle.size()) return 0;//到底了
            if (memo[i][j] != null) return memo[i][j];
            return memo[i][j] = Math.min(dfs2(triangle, i + 1, j), dfs2(triangle, i + 1, j + 1)) + triangle.get(i).get(j);
        }

        private int dynamic(List<List<Integer>> triangle) {
            int[][] dp = new int[triangle.size() + 1][triangle.size() + 1];
            //倒着来
            for (int i = triangle.size() - 1; i >= 0; i--) { //倒数第二行开始
                for (int j = 0; j < triangle.get(i).size(); j++) {//正着来
                    //找到所有的数
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
                }
            }

            return dp[0][0];//最顶点的就是结果
        }

        /**
         * 在上述代码中，我们定义了一个 NN 行 NN 列 的 dpdp 数组（NN 是三角形的行数）。
         * 但是在实际递推中我们发现，计算 dp[i][j]dp[i][j] 时，只用到了下一行的 dp[i + 1][j]dp[i+1][j] 和 dp[i + 1][j + 1]dp[i+1][j+1]。
         * 因此 dpdp 数组不需要定义 NN 行，只要定义 11 行就阔以啦。
         * 所以我们稍微修改一下上述代码，将 ii 所在的维度去掉（如下），就可以将 O(N^2)O(N
         * 2
         * ) 的空间复杂度优化成 O(N)O(N) 啦～
         * <p>
         * 作者：sweetiee
         * 链接：https://leetcode-cn.com/problems/triangle/solution/di-gui-ji-yi-hua-dp-bi-xu-miao-dong-by-sweetiee/
         * 来源：力扣（LeetCode）
         * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
         *
         * @param triangle
         * @return
         */
        private int dynamic2(List<List<Integer>> triangle) {
            int[] dp = new int[triangle.size() + 1]; //记录作用
            //倒着来
            for (int i = triangle.size() - 1; i >= 0; i--) { //倒数第二行开始
                System.out.println("->" + i);
                for (int j = 0; j < triangle.get(i).size(); j++) {//正着来
                    //找到所有的数
                    dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
                    System.out.println("---->" + Arrays.toString(dp));
                }
            }

            return dp[0];//最顶点的就是结果
        }
    }


}