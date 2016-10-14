package org.qcode.qskinloader.entity;

import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.base.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 皮肤框架支持的属性集合
 * qqliu
 * 2016/9/24.
 */
public class SkinAttrSet {
    private ArrayList<SkinAttr> mAttrList = new ArrayList<SkinAttr>();

    public SkinAttrSet(SkinAttr... skinAttrs) {
        if (null != skinAttrs) {
            for (SkinAttr attr : skinAttrs) {
                if (null == attr) {
                    continue;
                }
                mAttrList.add(attr);
            }
        }
    }

    public SkinAttrSet(List<SkinAttr> skinAttrs) {
        if (null != skinAttrs) {
            for (SkinAttr attr : skinAttrs) {
                if (null == attr) {
                    continue;
                }
                mAttrList.add(attr);
            }
        }
    }

    public void addSkinAttrSet(SkinAttrSet skinAttrSet) {
        if(null == skinAttrSet || CollectionUtils.isEmpty(skinAttrSet.mAttrList)) {
            return;
        }

        mAttrList.addAll(skinAttrSet.mAttrList);
    }

    public void addSkinAttr(SkinAttr skinAttr) {
        mAttrList.add(skinAttr);
    }

    public <T extends ISkinAttrHandler> T findSkinAttr(Class<T> skinAttrClz) {
        ArrayList<SkinAttr> attrArrayList = (ArrayList<SkinAttr>) mAttrList.clone();
        if (CollectionUtils.isEmpty(attrArrayList)) {
            return null;
        }

        for (SkinAttr attr : attrArrayList) {
            if(attr.getClass() == skinAttrClz) {
                return (T) attr;
            }
        }

        return null;
    }

    public List<SkinAttr> getAttrList() {
        return (ArrayList<SkinAttr>) mAttrList.clone();
    }
}
