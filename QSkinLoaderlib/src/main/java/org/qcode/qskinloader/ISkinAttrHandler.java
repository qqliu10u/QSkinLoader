package org.qcode.qskinloader;

import android.view.View;

import org.qcode.qskinloader.entity.SkinAttr;

/**
 * 皮肤属性处理器的接口
 * qqliu
 * 2016/9/24.
 */
public interface ISkinAttrHandler {
    /***
     * 将属性应用到View上
     */
    void apply(View view,
               SkinAttr skinAttr,
               IResourceManager resourceManager);
}
