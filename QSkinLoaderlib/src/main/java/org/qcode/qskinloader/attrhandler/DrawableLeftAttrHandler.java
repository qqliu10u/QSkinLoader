package org.qcode.qskinloader.attrhandler;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrName;
import org.qcode.qskinloader.IResourceManager;

/**
 * TextView的drawableLeft属性处理
 * qqliu
 * 2016/9/27.
 */
class DrawableLeftAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if(null == view
                || null == skinAttr
                || !(SkinAttrName.DRAWABLE_LEFT.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof TextView)) {
            return;
        }

        Drawable drawable = SkinAttrUtils.getDrawable(resourceManager,
                skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName,
                skinAttr.mAttrValueRefName);

        if(null != drawable) {
            ((TextView)view).setCompoundDrawablesWithIntrinsicBounds(
                    drawable, null, null, null);
        }
    }
}
