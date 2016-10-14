package org.qcode.qskinloader.attrhandler;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ListView;

import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrName;
import org.qcode.qskinloader.IResourceManager;

/***
 * ListView divider属性的换肤支持（android:divider）
 */
class DividerAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(SkinAttrName.DIVIDER.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof ListView)) {
            return;
        }

        ListView tv = (ListView) view;
        Drawable drawable = SkinAttrUtils.getDrawable(
                resourceManager, skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);

        if (null != drawable) {
            tv.setDivider(drawable);
        }
    }
}
