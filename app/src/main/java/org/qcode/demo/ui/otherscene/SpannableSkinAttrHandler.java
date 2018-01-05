package org.qcode.demo.ui.otherscene;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import org.qcode.demo.ui.customattr.CustomTextView;
import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinConstant;

import static org.qcode.qskinloader.entity.SkinConstant.RES_TYPE_NAME_COLOR;

/***
 * author: qqliu
 * created at 2018/1/5
 */
public class SpannableSkinAttrHandler implements ISkinAttrHandler {
    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if(null == view
                || null == skinAttr
                || !(SpannableSkinAttr.HIGHLIGHT_SPANNABLE.equals(skinAttr.mAttrName))) {
            return;
        }

        if(!(view instanceof TextView)) {
            return;
        }

        SpannableSkinAttr spannableSkinAttr = (SpannableSkinAttr) skinAttr.mDynamicAttr;
        if (null == spannableSkinAttr) {
            return;
        }

        TextView tv = (TextView) view;

        if (RES_TYPE_NAME_COLOR.equals(skinAttr.mAttrValueTypeName)) {
            int textColor = resourceManager.getColor(
                    skinAttr.mAttrValueRefId, skinAttr.mAttrValueRefName);
            SpannableString spannableString = new SpannableString(spannableSkinAttr.mText);
            spannableString.setSpan(new ForegroundColorSpan(textColor), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv.setText(spannableString);
        }
    }
}
