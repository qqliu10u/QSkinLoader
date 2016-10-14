package org.qcode.demo.ui.otherscene;

import android.content.Context;
import android.widget.ImageView;

import org.qcode.qskinloader.SkinManager;
import org.qcode.qskinloader.entity.SkinAttrName;
import org.qcode.skintestdemo.R;

/**
 * qqliu
 * 2016/10/14.
 */

public class FloatView extends ImageView {
    public FloatView(Context context) {
        super(context);

        //动态设置皮肤
        SkinManager
                .with(this)
                .setViewAttrs(SkinAttrName.SRC, R.drawable.drawable_float_view)
                .applySkin(false);
    }

    public void dismiss() {
        SkinManager
                .getWindowViewManager()
                .removeWindowView(this);
    }

    public void show() {
        //因为悬浮窗直接加载在WindowManager上,我们需要将View添加到框架内维护
        SkinManager
                .getWindowViewManager()
                .addWindowView(this);
    }
}
