package org.qcode.qskinloader.impl;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import org.qcode.qskinloader.ISkinViewHelper;
import org.qcode.qskinloader.attrhandler.SkinAttrFactory;
import org.qcode.qskinloader.base.utils.CollectionUtils;
import org.qcode.qskinloader.base.utils.Logging;
import org.qcode.qskinloader.base.utils.StringUtils;
import org.qcode.qskinloader.entity.DynamicAttr;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrSet;

import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * View皮肤设置管理
 * qqliu
 * 2016/10/8.
 */

public class SkinViewHelperImpl implements ISkinViewHelper {

    private View mView;

    public SkinViewHelperImpl(View view) {
        mView = view;
    }

    //=========================interfaces================================//

    @Override
    public ISkinViewHelper setViewAttrs(String attrName, int resId) {
        if (StringUtils.isEmpty(attrName)) {
            return this;
        }

        SkinAttr attr = parseSkinAttr(attrName, resId);
        return setViewAttrs(attr);
    }

    @Override
    public ISkinViewHelper setViewAttrs(DynamicAttr... dynamicAttrs) {
        if (CollectionUtils.isEmpty(dynamicAttrs)) {
            return this;
        }

        return setViewAttrs(Arrays.asList(dynamicAttrs));
    }

    @Override
    public ISkinViewHelper setViewAttrs(SkinAttr... skinAttrs) {
        if (CollectionUtils.isEmpty(skinAttrs)) {
            return this;
        }

        ViewSkinTagHelper.setSkinAttrs(mView, new SkinAttrSet(skinAttrs));

        return this;
    }

    @Override
    public ISkinViewHelper setViewAttrs(List<DynamicAttr> dynamicAttrs) {
        if (CollectionUtils.isEmpty(dynamicAttrs)) {
            return this;
        }

        SkinAttrSet skinAttrSet = parseSkinAttrSet(dynamicAttrs);

        ViewSkinTagHelper.setSkinAttrs(mView, skinAttrSet);

        return this;
    }

    @Override
    public ISkinViewHelper addViewAttrs(String attrName, int resId) {
        if (StringUtils.isEmpty(attrName)) {
            return this;
        }

        SkinAttr attr = parseSkinAttr(attrName, resId);
        return addViewAttrs(attr);
    }

    @Override
    public ISkinViewHelper addViewAttrs(DynamicAttr... dynamicAttrs) {
        if (CollectionUtils.isEmpty(dynamicAttrs)) {
            return this;
        }

        return addViewAttrs(Arrays.asList(dynamicAttrs));
    }

    @Override
    public ISkinViewHelper addViewAttrs(SkinAttr... skinAttrs) {
        if (CollectionUtils.isEmpty(skinAttrs)) {
            return this;
        }

        ViewSkinTagHelper.addSkinAttrs(mView, new SkinAttrSet(skinAttrs));

        return this;
    }

    @Override
    public ISkinViewHelper addViewAttrs(List<DynamicAttr> dynamicAttrs) {
        if (CollectionUtils.isEmpty(dynamicAttrs)) {
            return this;
        }

        SkinAttrSet skinAttrSet = parseSkinAttrSet(dynamicAttrs);

        ViewSkinTagHelper.addSkinAttrs(mView, skinAttrSet);

        return this;
    }

    @Override
    public ISkinViewHelper cleanAttrs(boolean clearChild) {
        if (null == mView) {
            return this;
        }

        cleanAttrs(mView, clearChild);

        return this;
    }

    @Override
    public void applySkin(boolean applyChild) {
        SkinManagerImpl.getInstance().applySkin(mView, applyChild);
    }

    private static void cleanAttrs(View view, boolean clearChild) {
        if (null == view) {
            return;
        }

        ViewSkinTagHelper.setSkinAttrs(view, null);

        if (clearChild) {
            if (view instanceof ViewGroup) {
                //遍历子元素清除皮肤
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    cleanAttrs(viewGroup.getChildAt(i), true);
                }
            }
        }
    }

    private SkinAttr parseSkinAttr(String attrName, int resId) {
        SkinAttr skinAttr = null;

        Resources resources = mView.getResources();

        try {
            String attrValueName = resources.getResourceEntryName(resId);
            String attrValueType = resources.getResourceTypeName(resId);
            skinAttr = SkinAttrFactory.newSkinAttr(attrName, resId, attrValueName, attrValueType);
        } catch (Exception ex) {
            Logging.d(TAG, "dynamicAddView()| error happened", ex);
        }

        return skinAttr;
    }

    @NonNull
    private SkinAttrSet parseSkinAttrSet(List<DynamicAttr> dynamicAttrs) {
        SkinAttrSet skinAttrSet = new SkinAttrSet();

        for (DynamicAttr dynamicAttr : dynamicAttrs) {
            if (null == dynamicAttr) {
                continue;
            }

            SkinAttr attr;
            //没有设置value
            if (!dynamicAttr.hasSetValueRef) {
                attr = SkinAttrFactory.newSkinAttr(
                        dynamicAttr.mAttrName, 0, null, null);

                skinAttrSet.addSkinAttr(attr);
                continue;
            }

            //设置了value，则解析resId的名称和类型
            attr = parseSkinAttr(dynamicAttr.mAttrName, dynamicAttr.mAttrValueRefId);
            if (null == attr) {
                continue;
            }

            skinAttrSet.addSkinAttr(attr);
        }

        return skinAttrSet;
    }
}
