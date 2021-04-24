package com.flannery.union;

import java.util.LinkedList;
import java.util.Queue;

public class A200_number_of_islands {

    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}};
        System.out.println(new Solution().numIslands(grid));
    }

    static class Solution {
        public int numIslands(char[][] grid) {
            int count = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == '1') {
                        dfs(grid, i, j);//深度搜索，找到后并置为0
                        count++;
                    }
                }
            }
            return count;
        }

        private void dfs(char[][] grid, int i, int j) {
            //找到边界
            if (i < 0 || j < 0 || i >= grid.length || j >= grid[i].length || grid[i][j] == '0')
                return; //不合格了，
            grid[i][j] = '0';
            //左上右下
            dfs(grid, i - 1, j);
            dfs(grid, i, j - 1);
            dfs(grid, i, j + 1);
            dfs(grid, i + 1, j);
        }

        // 保存索引位置
        class Index {
            final int i;
            final int j;

            Index(int i, int j) {
                this.i = i;
                this.j = j;
            }

            boolean isValidate(char[][] grid) {
                return 0 <= i && i < grid.length && 0 <= j && j < grid[0].length && grid[i][j] == '1';
            }
        }


        private void bfs(char[][] grid, int i, int j) {
            Queue<Index> queue = new LinkedList<>();
            queue.add(new Index(i, j)); //将这个点索引保存起来
            while (!queue.isEmpty()) {
                final Index poll = queue.poll();//取出前面的
                if (poll.isValidate(grid)) {
                    //process
                    grid[i][j] = '0';
                    //左上右下
                    queue.offer(new Index(i + 1, j));
                    queue.offer(new Index(i - 1, j));
                    queue.offer(new Index(i, j + 1));
                    queue.offer(new Index(i, j - 1));
                }
            }
        }
    }

}
