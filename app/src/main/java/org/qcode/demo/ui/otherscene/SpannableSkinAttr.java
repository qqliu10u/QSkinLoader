package org.qcode.demo.ui.otherscene;

import org.qcode.qskinloader.entity.DynamicAttr;

/***
 * author: qqliu
 * created at 2018/1/5
 */
public class SpannableSkinAttr extends DynamicAttr {

    public static final String HIGHLIGHT_SPANNABLE = "highlightSpannable";
    public final String mText;

    public SpannableSkinAttr(String text, int attrValueRefId) {
        super(HIGHLIGHT_SPANNABLE, attrValueRefId);
        mText = text;

        //这里一定要设置，mText就丢弃了
        keepInstance = true;
    }
}
