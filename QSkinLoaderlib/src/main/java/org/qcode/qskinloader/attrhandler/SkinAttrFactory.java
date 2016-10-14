package org.qcode.qskinloader.attrhandler;


import android.text.TextUtils;

import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrName;

import java.util.HashMap;
import java.util.Map;

/***
 * 获取支持的属性处理器工厂
 */
public class SkinAttrFactory {

    //存放支持的换肤属性和对应的处理器
    private static Map<String, ISkinAttrHandler> mSupportAttrHandler = new HashMap<String, ISkinAttrHandler>();

    //静态注册支持的属性和处理器
    static {
        registerSkinAttrHandler(SkinAttrName.BACKGROUND, new BackgroundAttrHandler());
        registerSkinAttrHandler(SkinAttrName.SRC, new SrcAttrHandler());
        registerSkinAttrHandler(SkinAttrName.TEXT_COLOR, new TextColorAttrHandler());
        registerSkinAttrHandler(SkinAttrName.TEXT_COLOR_HINT, new TextColorHintAttrHandler());
        registerSkinAttrHandler(SkinAttrName.LIST_SELECTOR, new ListSelectorAttrHandler());
        registerSkinAttrHandler(SkinAttrName.DIVIDER, new DividerAttrHandler());
        registerSkinAttrHandler(SkinAttrName.DRAWABLE_LEFT, new DrawableLeftAttrHandler());
        registerSkinAttrHandler(SkinAttrName.DRAW_SHADOW, new ShadowAttrHandler());
        registerSkinAttrHandler(SkinAttrName.CLEAR_RECYCLER_VIEW, new RecyclerViewClearSubAttrHandler());
    }

    /***
     * 创建一个新的皮肤对象
     * @param attrName
     * @param attrValueRefId
     * @param attrValueRefName
     * @param typeName
     * @return
     */
    public static SkinAttr newSkinAttr(
            String attrName, int attrValueRefId,
            String attrValueRefName, String typeName) {
        SkinAttr skinAttr = new SkinAttr();

        skinAttr.mAttrName = attrName;
        skinAttr.mAttrValueRefId = attrValueRefId;
        skinAttr.mAttrValueRefName = attrValueRefName;
        skinAttr.mAttrValueTypeName = typeName;
        return skinAttr;
    }

    /***
     * 获取特定属性的换肤处理器
     *
     * @param attrName
     * @return
     */
    public static ISkinAttrHandler getSkinAttrHandler(String attrName) {
        return mSupportAttrHandler.get(attrName);
    }

    /***
     * 是否支持某属性换肤
     *
     * @param attrName
     * @return
     */
    public static boolean isSupportedAttr(String attrName) {
        return null != getSkinAttrHandler(attrName);
    }

    /****
     * 注册对某个属性的换肤支持
     *
     * @param attrName
     */
    public static void registerSkinAttrHandler(String attrName, ISkinAttrHandler skinAttrHandler) {
        if (TextUtils.isEmpty(attrName) || null == skinAttrHandler) {
            return;
        }

        mSupportAttrHandler.put(attrName, skinAttrHandler);
    }

    /***
     * 移除对某个属性的换肤支持
     *
     * @param attrName
     */
    public static void removeSkinAttrHandler(String attrName) {
        if (TextUtils.isEmpty(attrName)) {
            return;
        }
        mSupportAttrHandler.remove(attrName);
    }
}
