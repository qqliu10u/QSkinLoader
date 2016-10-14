package org.qcode.qskinloader.attrhandler;

import android.graphics.drawable.Drawable;
import android.view.View;

import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrName;
import org.qcode.qskinloader.IResourceManager;

/***
 * 背景属性的换肤支持（android:background）
 */
class BackgroundAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if(null == view
                || null == skinAttr
                || !(SkinAttrName.BACKGROUND.equals(skinAttr.mAttrName))) {
            return;
        }

        Drawable drawable = SkinAttrUtils.getDrawable(
                resourceManager, skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);

        if(null != drawable) {
            view.setBackgroundDrawable(drawable);
        }
    }
}
