package org.qcode.demo.ui.customattr;

import android.graphics.drawable.Drawable;
import android.view.View;

import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.attrhandler.SkinAttrUtils;
import org.qcode.qskinloader.entity.SkinAttr;

/**
 * qqliu
 * 2016/10/9.
 */

public class DefBackgroundAttrHandler implements ISkinAttrHandler {
    public static final String DEF_BACKGROUND = "defBackground";

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(DEF_BACKGROUND.equals(skinAttr.mAttrName))) {
            //自定义属性处理时，需要明确当前处理器能处理的属性，此处是DEF_BACKGROUND
            return;
        }

        if (!(view instanceof CustomTextView)) {
            //防止在错误的View上设置了此属性
            return;
        }

        //封装了取ColorDrawable和取普通Drawable的逻辑
        Drawable drawable = SkinAttrUtils.getDrawable(
                resourceManager, skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);

        if (null != drawable) {
            view.setBackgroundDrawable(drawable);
        }
    }
}
