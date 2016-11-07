package org.qcode.qskinloader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建View的过程中的回调
 * qqliu
 * 2016/10/17.
 */

public interface IViewCreateListener {
    /***
     * 创建View之前的回调; invoked before view create,
     * should be used to create view outside the framework if needed
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    View beforeCreate(String name, Context context, AttributeSet attrs);

    /***
     * 创建View之后的回调; invoked after view create,
     * should be used to parse view attributes outside the framework if needed
     * @param view
     * @param name
     * @param context
     * @param attrs
     */
    void afterCreated(View view, String name, Context context, AttributeSet attrs);
}
