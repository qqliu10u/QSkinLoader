package org.qcode.demo.ui.customattr;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import org.qcode.skintestdemo.R;

/**
 * qqliu
 * 2016/9/11.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.app);
        int textColor = array.getColor(R.styleable.app_defTextColor, Color.BLACK);
        int bgDrawableId = array.getResourceId(R.styleable.app_defBackground, 0);
        array.recycle();

        setTextColor(textColor);
        setBackgroundResource(bgDrawableId);
    }
}
