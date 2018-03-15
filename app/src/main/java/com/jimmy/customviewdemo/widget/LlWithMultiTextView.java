package com.jimmy.customviewdemo.widget;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.utils.DensityUtils;

/**
 * Created by jinguochong on 16-9-6.
 */
public class LlWithMultiTextView extends LinearLayout {
    private Context mContext;

    public LlWithMultiTextView(Context context) {
        this(context, null);
    }

    public LlWithMultiTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LlWithMultiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOrientation(VERTICAL);
    }


    public void setText(String text, String splitTag) {
        String[] strs = text.split(splitTag);//"<br/>"
        TextView textView;
        if (strs != null && strs.length != 0) {
            for (int i = 0; i < strs.length; i++) {
                textView = new TextView(mContext);
                textView.setTextSize(12);
                textView.setTextColor(R.color.white);
                if (i != 0) {
                    LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, DensityUtils.dp2px(8), 0, 0);
                    textView.setLayoutParams(lp);
                }
                textView.setLineSpacing(DensityUtils.dip2px(4), 1);
                textView.setText(Html.fromHtml(strs[i]));
                addView(textView);
            }
        }
    }
}
