package org.qcode.qskinloader.attrhandler;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.base.utils.CollectionUtils;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrSet;
import org.qcode.qskinloader.entity.SkinConstant;

import java.util.List;

/**
 * 皮肤属性的辅助帮助类
 * qqliu
 * 2016/9/25.
 */
public class SkinAttrUtils {

    /***
     * 获取指定资源的drawable，支持的resId为int型颜色和drawable
     * @param resourceManager
     * @param resId
     * @param resTypeName
     * @param resName
     * @return
     */
    public static Drawable getDrawable(IResourceManager resourceManager,
                                       int resId,
                                       String resTypeName,
                                       String resName) {
        if (SkinConstant.RES_TYPE_NAME_COLOR.equals(resTypeName)) {
            int bgColor = resourceManager.getColor(
                    resId, resName);
            return new ColorDrawable(bgColor);

        } else if (SkinConstant.RES_TYPE_NAME_DRAWABLE.equals(resTypeName)) {
            Drawable drawable = resourceManager.getDrawable(
                    resId, resName);
            return drawable;
        } else if (SkinConstant.RES_TYPE_NAME_MIPMAP.equals(resTypeName)) {
            Drawable drawable = resourceManager.getDrawable(
                    resId, resName);
            return drawable;
        }

        return null;
    }

    /***
     * 对View应用指定的属性集合
     * @param view
     * @param skinAttrSet
     * @param resourceManager
     */
    public static void applySkinAttrs(View view, SkinAttrSet skinAttrSet, IResourceManager resourceManager) {
        if(null == view || null == skinAttrSet) {
            return;
        }

        List<SkinAttr> attrArrayList = skinAttrSet.getAttrList();
        if (CollectionUtils.isEmpty(attrArrayList)) {
            return;
        }

        for (SkinAttr attr : attrArrayList) {
            ISkinAttrHandler attrHandler = SkinAttrFactory.getSkinAttrHandler(attr.mAttrName);
            if(null != attrHandler) {
                attrHandler.apply(view, attr, resourceManager);
            }
        }
    }
}
