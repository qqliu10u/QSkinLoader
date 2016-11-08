package org.qcode.qskinloader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 框架解析皮肤属性的解析帮助类
 *
 * the skin attribute parser defines
 * how to parse attributes when view is created.
 *
 * qqliu
 * 2016/11/8.
 */

public interface ISkinAttributeParser {
    /***
     * 是否支持换肤
     *
     * return the parse result whether the view supports skin-change
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    boolean isSupportSkin(String name, Context context, AttributeSet attrs);

    /***
     * 解析View的皮肤属性
     *
     * parse skin attributes from view-creating process
     *
     * @param view
     * @param name
     * @param context
     * @param attrs
     */
    void parseAttribute(View view, String name, Context context, AttributeSet attrs);
}
