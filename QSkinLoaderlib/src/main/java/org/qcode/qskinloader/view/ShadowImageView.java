package org.qcode.qskinloader.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * 支持蒙层的ImageView，建议使用此类作为蒙层处理的ImageView的替代，
 * 给ImageView设置ColorFilter可能会失败，此类为drawable设置蒙层，效果更好点。
 *
 * qqliu
 * 2016/9/28.
 */
public class ShadowImageView extends ImageView {

    private PorterDuffColorFilter mColorFilter;

    private boolean hasFilterSet = true;
    private WeakReference<Drawable> mSetFilteredDrawable = null;

    public ShadowImageView(Context context) {
        this(context, null);
    }

    public ShadowImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setShadowColor(int color) {
        hasFilterSet = false;
        mColorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if(null != mSetFilteredDrawable && drawable != mSetFilteredDrawable.get()) {
            //drawable 发生了变化，重新设置filter
            hasFilterSet = false;
        }

        if(!hasFilterSet) {
            if (null != drawable) {
                drawable.setColorFilter(mColorFilter);
                mSetFilteredDrawable = new WeakReference<Drawable>(drawable);
                hasFilterSet = true;
            }
        }

        super.onDraw(canvas);
    }
}
