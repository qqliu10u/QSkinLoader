package org.qcode.demo.skin;

import org.qcode.demo.base.Settings;

public class SkinConfigHelper {

    /***
     * 获取当前皮肤包的标识
     */
    public static String getSkinIdentifier() {
        return Settings.getInstance().getString(
                SkinConstant.CUSTOM_SKIN_IDENTIFIER,
                SkinConstant.DEFAULT_SKIN);
    }

    /**
     * 保存皮肤包的标识
     */
    public static void saveSkinIdentifier(String identifier) {
        Settings.getInstance().setSetting(
                SkinConstant.CUSTOM_SKIN_IDENTIFIER,
                identifier);
    }

    /**
     * 是否默认皮肤
     */
    public static boolean isDefaultSkin() {
        return SkinConstant.DEFAULT_SKIN.equals(getSkinIdentifier());
    }
}
