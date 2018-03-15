package com.jimmy.customviewdemo.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.text.style.ParagraphStyle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.utils.DensityUtils;
import com.jimmy.customviewdemo.widget.LlWithMultiTextView;

public class TextAty extends AppCompatActivity {
    private String text1 = "<div style=\"text-align:center;\"><span style=\"line-height:1.5;\">这是一个真实的故事，真的很真实的故事</span>\n" +
            "</div>\n" +
            "你们想听吗？<br />\n" +
            "不想听<br />\n" +
            "<br />"
            +"<div style=\"text-align:left;\"><span style=\"line-height:1.5;color:#E53333;\">是一个真实的故事，真的很真实的故事</span> \n" +
            "</div>\n" +
            "你们想听吗？<br />\n" +
            "不想听<br />\n" +
            "<br />";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_text);
        //setText1();
        //setMultiText();

        LlWithMultiTextView ll = (LlWithMultiTextView) findViewById(R.id.ll);
        ll.setText(text1, "<br />");

        /*WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadDataWithBaseURL(null, text1, "text/html", "utf-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());*/
    }

    /*private void setText1() {
        TextView textView1 = (TextView) findViewById(R.id.text1);
        //TextView textView;
        LeadingMarginSpan.Standard span1 = new LeadingMarginSpan.Standard(10, 20);
        SpannableString spanString = new SpannableString(text1);
        spanString.setSpan(new LeadingMarginSpan.Standard(1, 5), 1, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String[] strs = text1.split("<br/>");
        if (strs != null && strs.length != 0) {
            for (int i = 0; i < strs.length; i++) {

            }
        }
        textView1.setText(spanString);
    }

    private void setMultiText() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.aty_text);
        String[] strs = text1.split("<br/>");
        TextView textView;
        if (strs != null && strs.length != 0) {
            for (int i = 0; i < strs.length; i++) {
                textView = new TextView(this);
                if (i != 0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, DensityUtils.dp2px(8), 0, 0);
                    textView.setLayoutParams(lp);
                }
                textView.setLineSpacing(DensityUtils.dip2px(4), 1);
                textView.setText(Html.fromHtml(strs[i]));
                ll.addView(textView);
            }
        }
        TextView tv = new TextView(this);
        tv.setText("动态添加");
        ((LinearLayout) this.findViewById(R.id.aty_text)).addView(tv);
    }*/
}
