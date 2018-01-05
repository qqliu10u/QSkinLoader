package org.qcode.qskinloader.entity;

import org.qcode.qskinloader.base.utils.CollectionUtils;
import org.qcode.qskinloader.base.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 皮肤框架支持的属性集合
 * qqliu
 * 2016/9/24.
 */
public class SkinAttrSet {
    private HashMap<String, SkinAttr> mAttrMap = new HashMap<String, SkinAttr>();

    public SkinAttrSet(SkinAttr... skinAttrs) {
        this(Arrays.asList(skinAttrs));
    }

    public SkinAttrSet(List<SkinAttr> skinAttrs) {
        saveAttrs(skinAttrs);
    }

    private void saveAttrs(List<SkinAttr> skinAttrs) {
        if (CollectionUtils.isEmpty(skinAttrs)) {
            return;
        }

        for (SkinAttr attr : skinAttrs) {
            if (null == attr || StringUtils.isEmpty(attr.mAttrName)) {
                continue;
            }
            mAttrMap.put(attr.mAttrName, attr);
        }
    }

    public synchronized void addSkinAttrSet(SkinAttrSet skinAttrSet) {
        if (null == skinAttrSet) {
            return;
        }

        List<SkinAttr> setAttrList = skinAttrSet.getAttrList();
        saveAttrs(setAttrList);
    }

    public synchronized void addSkinAttr(SkinAttr skinAttr) {
        if (null == skinAttr || StringUtils.isEmpty(skinAttr.mAttrName)) {
            return;
        }

        mAttrMap.put(skinAttr.mAttrName, skinAttr);
    }

    public synchronized List<SkinAttr> getAttrList() {
        ArrayList<SkinAttr> resultList = new ArrayList<SkinAttr>();

        Collection<SkinAttr> valueAttrList = mAttrMap.values();
        if (!CollectionUtils.isEmpty(valueAttrList)) {
            resultList.addAll(valueAttrList);
        }

        return resultList;
    }
}
