package org.qcode.qskinloader.impl;

import android.view.View;

import org.qcode.qskinloader.R;
import org.qcode.qskinloader.entity.SkinAttrSet;

/**
 * 存取View的皮肤属性的帮助类
 * qqliu
 * 2016/10/8.
 */

class ViewSkinTagHelper {

    /***
     * 设置View的皮肤属性
     * @param view
     * @param skinAttrSet
     */
    public static void setSkinAttrs(View view, SkinAttrSet skinAttrSet) {
        if(null == view) {
            return;
        }
        view.setTag(R.id.tag_skin_attr, skinAttrSet);
    }

    /***
     * 添加View的皮肤属性
     * @param view
     * @param skinAttrSet
     */
    public static void addSkinAttrs(View view, SkinAttrSet skinAttrSet) {
        if(null == view) {
            return;
        }

        SkinAttrSet attrSet = getSkinAttrs(view);
        if (null == attrSet) {
            view.setTag(R.id.tag_skin_attr, skinAttrSet);
        } else {
            attrSet.addSkinAttrSet(skinAttrSet);
        }
    }

    /***
     * 获取View的皮肤属性
     * @param view
     * @return  skinAttrSet
     */
    public static SkinAttrSet getSkinAttrs(View view) {
        if(null == view) {
            return null;
        }

        return (SkinAttrSet) view.getTag(R.id.tag_skin_attr);
    }
}
