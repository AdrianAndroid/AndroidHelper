package com.flannery.union;

import java.util.Arrays;

public class find_circle {
    public static void main(String[] args) {
        System.out.println(new find_circle().findCircleNum(new int[][]
                //{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}

                {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}
        ));
    }

    public int findCircleNum(int[][] isConnected) {
        int result = 0;
        boolean[] visited = new boolean[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) { //所有的城市遍历一遍
            if (!visited[i]) { //没有被访问过
                dfs(isConnected, visited, i);
                result++;
            }
        }
        return result;
    }

    // 深度优先
    private void dfs(int[][] isConnected, boolean[] visited, int i) {
        // 再来一遍城市
        for (int j = 0; j < isConnected.length; j++) { //所有的城市再来一遍
            if (isConnected[i][j] == 1 && !visited[j]) { //下一个城市也没被访问过
                visited[j] = true;
                dfs(isConnected, visited, j);
            }

        }
    }
}

