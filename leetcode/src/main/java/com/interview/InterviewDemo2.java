package com.interview;

public class InterviewDemo2 {

    public static void main(String[] args) {
        Integer a =  new Integer(1234);
        Integer b =  new Integer(1234);
        boolean result= a.intValue() == b.intValue();
        System.out.println(result);
        //4. 给定 a、b 两个文件，各存放 50 亿个互不相同的URL，
        // 每个 URL 各占 64B，（320G）内存限制是 4G。请找出 a、b 两个文件共同的 URL。
    }


}
