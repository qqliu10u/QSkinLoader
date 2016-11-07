package org.qcode.qskinloader.attrhandler;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrName;

/***
 * src属性的换肤支持（android:src）
 */
class SrcAttrHandler implements ISkinAttrHandler {

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if (null == view
                || null == skinAttr
                || !(SkinAttrName.SRC.equals(skinAttr.mAttrName))) {
            return;
        }

        if (!(view instanceof ImageView)) {
            return;
        }

        boolean isAnimationDrawable = false;
        boolean isRunning = false;
        Drawable originalDrawable = ((ImageView) view).getDrawable();
        if (originalDrawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) originalDrawable;
            isAnimationDrawable = true;
            isRunning = animationDrawable.isRunning();
        }

        Drawable drawable = SkinAttrUtils.getDrawable(
                resourceManager, skinAttr.mAttrValueRefId,
                skinAttr.mAttrValueTypeName, skinAttr.mAttrValueRefName);

        if (null != drawable) {
            ((ImageView) view).setImageDrawable(drawable);

            if (isAnimationDrawable && drawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = ((AnimationDrawable) drawable);
                if (isRunning) {
                    animationDrawable.start();
                } else {
                    animationDrawable.stop();
                }
            }
        }
    }
}
