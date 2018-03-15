package com.jimmy.javalib.test.inherit;

/**
 * Created by jinguochong on 16-6-28.
 */
public class B extends A {

    public B() {
        super();
        print();
    }

    public void print() {
        System.out.println("print B");
    }

    public void print(String s) {
        System.out.println("print " + s);
    }
}
