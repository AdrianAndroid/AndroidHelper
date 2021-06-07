package com.interview;

import org.graalvm.compiler.nodes.calc.IntegerTestNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class InterviewDemo {
    public static void main(String[] args) {
//        System.out.println(fab(1000));

        // String
        // ---- char[]

        // aidl
        // binder

        // create -> 工厂模式

        // Iterator
        // cusor -》

        // SurfaceView
        // TextureView

        //BigDecimal big = new BigDecimal();
//        float a = 0.2f * 10;
//        float b = 1.0f * 10 - 0.8f * 10;
//        boolean c = a/10f == b/10f;
//        System.out.println(c); //

        print("abcnndddda");
    }

    // "asdfasdf"
    static  void print(String strs) {
        // Hash index 无序
        //linked ---

        HashMap<Character, Integer> map =new LinkedHashMap<>();
        for (int i = 0; i < strs.length(); i++) {
            Character c = strs.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        Iterator<Map.Entry<Character, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Character, Integer> next = iterator.next();
            System.out.println(next.getKey() + " - " + next.getValue());
        }
    }



    static int fab(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        // 循环+记录
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n-1];
    }
}
