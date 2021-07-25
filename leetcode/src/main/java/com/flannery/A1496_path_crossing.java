package com.flannery;

import java.util.HashSet;

public class A1496_path_crossing {


    public static void main(String[] args) {

    }

    public boolean isPathCrossing(String path) {
        // N 北 S 南 E 东 w 西
        HashSet<Integer> set = new HashSet<>();
        int x = 0, y = 0;
        set.add(getHash(x, y));
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);

            if (c == 'N') ++y;
            else if (c == 'S') --y;
            else if (c == 'E') ++x;
            else if (c == 'W') --x;

            if (!set.add(getHash(x, y))) return true;
        }
        return false;
    }

    private int getHash(int x, int y) {
        return x * 20001 + y;// 10^-4 ~ 10^4
    }

}
