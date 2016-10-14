package org.qcode.qskinloader.attrhandler;

/***
 * ListView selector属性的换肤支持（android:listSelector）
 */

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;

import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrName;
import org.qcode.qskinloader.IResourceManager;

class ListSelectorAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(SkinAttrName.LIST_SELECTOR.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof AbsListView)) {
            return;
        }

        Drawable drawable = SkinAttrUtils.getDrawable(
                resourceManager, skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);

        if (null != drawable) {
            ((AbsListView) view).setSelector(drawable);
        }
    }
}
