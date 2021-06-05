package com.flannery.string;

public class A709_to_lower_case {

    public static void main(String[] args) {
        System.out.println(new A709_to_lower_case().toLowerCase("Hello"));
    }


    public String toLowerCase(String str) {
        StringBuffer ans = new StringBuffer();
        for(int i = 0 ; i < str.length() ; i++){
            if(str.charAt(i) <= 'Z' && str.charAt(i) >= 'A'){
                char c = (char)(str.charAt(i) - 'A' + 'a');
                ans.append(c);
            }
            else {
                ans.append(str.charAt(i));
            }
        }
        return ans.toString();
    }


}
