package com.jimmy.javalib.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jinguochong on 16-9-20.
 */
public class RegexTest {

    private static String text = "<span style=\"color:#9933E5;\">吹醒了杨柳岸边的新芽，</span>\n" +
            "</strong>也吹醒了乐。<br />" +
            "<span style=\"color:#E53333;\">时光里的我</span>" +
            "<span style=\"font-family:NSimSun;font-size:14px;color:#E56600;\"><u>这是副标题</u></span></em></font><br /> <em></em> 这是内容<br />";

    public static void main(String[] args) {
        String color = "(<span style=\"color:)(.*?);\">";
        Pattern p = Pattern.compile(color);
        Matcher m = p.matcher(text);
        //StringBuffer sb = new StringBuffer();
        if (m.find()) {
            String group = m.group(2);
            //System.out.println("group : " + group);
            //text = m.replaceAll(m.group() + "<font color=\"" + group + "\">");

            text = text.replaceAll("(<span style=\\\"color:)(.*?)(;\\\">)", "$0\"<font color=\\\"\" + $2 + \"\\\">\"");
        }
        text = text.replace("</span>", "</font></span>");
        System.out.println();
        System.out.println();
        System.out.println("text:---" + text);


        //item.content.replaceAll(color,"<font color=\"(1)\">");

        //item.content.replaceAll("</span>","</font>");

    }
}
