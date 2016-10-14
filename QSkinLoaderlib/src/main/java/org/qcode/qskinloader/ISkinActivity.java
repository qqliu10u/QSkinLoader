package org.qcode.qskinloader;

/**
 * 支持换肤的Activity应实现的接口
 *
 * qqliu
 * 2016/9/25.
 */
public interface ISkinActivity {

    /***
     * 是否需要立刻刷新皮肤；默认不立刻换肤
     *
     * @return
     */
    boolean isSwitchSkinImmediately();

    /***
     * 确定是否支持换肤
     *
     * @return
     */
    boolean isSupportSkinChange();

    /***
     * 刷新皮肤；
     * 此处刷新的是皮肤框架管理之外的界面
     */
    void handleSkinChange();
}
