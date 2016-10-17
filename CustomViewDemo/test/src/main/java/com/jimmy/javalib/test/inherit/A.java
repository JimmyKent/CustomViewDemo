package com.jimmy.javalib.test.inherit;


/**
 * Created by jinguochong on 16-6-28.
 *
 */
public class A {

    private String a = print();

    public A() {
        print2
                ();
    }

    private String print() {
        System.out.println("print A");
        return "";
    }

    private final void print2() {
        System.out.println("print2 A");
    }

    static{
        System.out.println("static");
    }
}
