package com.jimmy.javalib.test.nulltest;

/**
 * Created by jinguochong on 16-6-30.
 */
public class A {
    public static void main(String... args) {
        A[] as = new A[5];
        A a;
        for (int i = 0; i < as.length; i++) {
            if (i == 2) {
                a = null;
                as[i] = a;
                continue;
            }
            a = new A();
            as[i] = a;
        }
        for (A aa : as){
            System.out.println(aa);
        }
    }
}
