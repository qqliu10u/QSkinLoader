package org.qcode.qskinloader;

/**
 * 支持换肤的Activity应实现的接口
 *
 * A interface indicates that this activity supports skin change.
 *
 * qqliu
 * 2016/9/25.
 */
public interface ISkinActivity {

    /***
     * 是否需要立刻刷新皮肤；默认不立刻换肤
     * tells whether refresh skin immediately, if return false, the activity will
     * be refreshed after focus obtained.
     *
     * @return
     */
    boolean isSwitchSkinImmediately();

    /***
     * 确定是否支持换肤
     *tells whether the activity support skin change,
     * if return false, the activity will not refresh when skin change.
     * NOTICE: SkinManager.getInstance().applySkin() will ignore the setting
     *
     * @return
     */
    boolean isSupportSkinChange();

    /***
     * 刷新皮肤；
     * 此处刷新的是皮肤框架管理之外的界面
     *
     * when skin changes, this method will be called,
     * to notify activity doing something beyond the framework's ability.
     */
    void handleSkinChange();
}
