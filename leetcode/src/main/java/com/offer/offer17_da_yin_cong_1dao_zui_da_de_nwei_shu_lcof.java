package com.offer;

public class offer17_da_yin_cong_1dao_zui_da_de_nwei_shu_lcof {

    public static void main(String[] args) {
        int[] ints = new offer17_da_yin_cong_1dao_zui_da_de_nwei_shu_lcof().printNumbers(2);
        for (int anInt : ints) {
            System.out.print(anInt);
        }
    }

    public int[] printNumbers(int n) {
        if (n < 1) return new int[0];
        // 先算出容器大小
        int max = 1;
        while (n-- > 0) max *= 10;
        int res[] = new int[max - 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }
        return res;
    }
}
