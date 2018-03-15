package com.jimmy.oj;

/**
 * Created by jinguochong on 16-7-12.
 */
public class StringReverse {

    public static String reverseString(String s){

        StringBuffer sb = new StringBuffer();
        char array[] = s.toCharArray();
        int len = s.length();
        for(int i = 0; i < len; i++){
            sb.append(s.charAt(len -1 -i));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(reverseString("hello"));
    }
}
