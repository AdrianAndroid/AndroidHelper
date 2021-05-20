package com.flannery.string;

public class A58_length_of_last_word {
    public static void main(String[] args) {
        System.out.println(new A58_length_of_last_word().lengthOfLastWord("Hello world"));
    }


    public int lengthOfLastWord(String s) {
        if (s == null || s.equals("")) return 0;
        s = s.trim();
        int len = 0;
        for (int i = s.length()-1; i >= 0; i--) {
            if(!(' ' == s.charAt(i))) {
                len++;
            } else if(len != 0){
                break;
            }
        }

        return len;
    }


}
