package org.qcode.qskinloader.attrhandler;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;

import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrName;
import org.qcode.qskinloader.entity.SkinConstant;
import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.view.ShadowImageView;

/**
 * 蒙层阴影属性，仅支持ImageView，且蒙层只能是int型颜色
 * qqliu
 * 2016/9/25.
 */
class ShadowAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(SkinAttrName.DRAW_SHADOW.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof ImageView)) {
            return;
        }

        if (resourceManager.isDefault()) {
            if (view instanceof ShadowImageView) {
                ShadowImageView imageView = (ShadowImageView) view;
                imageView.setShadowColor(Color.WHITE);
            } else {
                ((ImageView) view).setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
        } else {
            if (SkinConstant.RES_TYPE_NAME_COLOR.equals(skinAttr.mAttrValueTypeName)) {
                int bgColor = resourceManager.getColor(
                        skinAttr.mAttrValueRefId, skinAttr.mAttrValueRefName);
                if (view instanceof ShadowImageView) {
                    ShadowImageView imageView = (ShadowImageView) view;
                    imageView.setShadowColor(bgColor);
                } else {
                    ((ImageView) view).setColorFilter(bgColor, PorterDuff.Mode.MULTIPLY);
                }
            }
        }
    }
}
