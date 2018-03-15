package com.jimmy.javalib.test;

/**
 * Created by jinguochong on 16-9-21.
 */
public class EnumTest {

    public static void main(String[] args) {
        String type = UrlType.getName(1);

        System.out.println("type:" + type);

    }

    public static enum UrlType {
        activity(1), detail(2), hotgame(3), special(4), gift(5), bbs(6), assess(7), guide(8);

        public int type;

        UrlType(int type) {
            this.type = type;
        }

        public static String getName(int index) {
            for (UrlType c : UrlType.values()) {
                if (c.type == index) {
                    return c.toString();
                }
            }
            return null;
        }

    }
}
