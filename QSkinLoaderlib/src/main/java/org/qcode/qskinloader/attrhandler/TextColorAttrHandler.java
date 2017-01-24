package org.qcode.qskinloader.attrhandler;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrName;
import org.qcode.qskinloader.entity.SkinConstant;

/***
 * 文字颜色属性的换肤支持（android:textColor）
 */
class TextColorAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(SkinAttrName.TEXT_COLOR.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof TextView)) {
            return;
        }

        TextView tv = (TextView) view;
        if (SkinConstant.RES_TYPE_NAME_COLOR.equals(skinAttr.mAttrValueTypeName)
                || SkinConstant.RES_TYPE_NAME_DRAWABLE.equals(skinAttr.mAttrValueTypeName)) {
            //按照ColorStateList引用来解析；
            //context.getResources().getColor()方法可以取纯颜色，也可以取ColorStateList引用内的颜色，
            //如果取的是ColorStateList，则取其中默认颜色；
            //同时，context.getResources().getColorStateList()方法也可以取纯颜色生成一个ColorStateList
            ColorStateList textColor = resourceManager.getColorStateList(
                    skinAttr.mAttrValueRefId, skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);
            tv.setTextColor(textColor);
        }

    }
}
