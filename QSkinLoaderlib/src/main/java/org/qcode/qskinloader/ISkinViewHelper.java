package org.qcode.qskinloader;

import org.qcode.qskinloader.entity.DynamicAttr;
import org.qcode.qskinloader.entity.SkinAttr;

import java.util.List;

/**
 * 皮肤框架中View相关皮肤属性的管理器接口抽象
 *
 * qqliu
 * 2016/10/8.
 */

public interface ISkinViewHelper {
    ISkinViewHelper setViewAttrs(String attrName, int resId);
    ISkinViewHelper setViewAttrs(DynamicAttr... dynamicAttrs);
    ISkinViewHelper setViewAttrs(SkinAttr... skinAttrs);
    ISkinViewHelper setViewAttrs(List<DynamicAttr> dynamicAttrs);

    ISkinViewHelper addViewAttrs(String attrName, int resId);
    ISkinViewHelper addViewAttrs(DynamicAttr... dynamicAttrs);
    ISkinViewHelper addViewAttrs(SkinAttr... skinAttrs);
    ISkinViewHelper addViewAttrs(List<DynamicAttr> dynamicAttrs);

    ISkinViewHelper cleanAttrs(boolean clearChild);

    void applySkin(boolean applyChild);
}
