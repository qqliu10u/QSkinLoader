package org.qcode.demo.ui.customattr;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.View;

import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinConstant;

import static org.qcode.qskinloader.entity.SkinConstant.RES_TYPE_NAME_COLOR;

/**
 * qqliu
 * 2016/10/9.
 */

public class DefTextColorAttrHandler implements ISkinAttrHandler {
    public static final String DEF_TEXT_COLOR = "defTextColor";

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if(null == view
                || null == skinAttr
                || !(DEF_TEXT_COLOR.equals(skinAttr.mAttrName))) {
            //自定义属性处理时，需要明确当前处理器能处理的属性，此处是DEF_TEXT_COLOR
            return;
        }

        if(!(view instanceof CustomTextView)) {
            //防止在错误的View上设置了此属性
            return;
        }

        CustomTextView tv = (CustomTextView) view;

        if (RES_TYPE_NAME_COLOR.equals(skinAttr.mAttrValueTypeName)) {
            if (SkinConstant.RES_TYPE_NAME_COLOR.equals(skinAttr.mAttrValueTypeName)) {
                // FIXME 不支持默认int型颜色，皮肤包内是ColorStateList型颜色的情况
                try {
                    //先尝试按照int型颜色解析
                    int textColor = resourceManager.getColor(
                            skinAttr.mAttrValueRefId, skinAttr.mAttrValueRefName);
                    tv.setTextColor(textColor);

                } catch (Resources.NotFoundException ex) {
                    //不是int型则按照ColorStateList引用来解析
                    ColorStateList textColor = resourceManager.getColorStateList(
                            skinAttr.mAttrValueRefId, skinAttr.mAttrValueRefName);
                    tv.setTextColor(textColor);
                }
            }
        }
    }
}
