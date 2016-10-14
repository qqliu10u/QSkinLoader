package org.qcode.qskinloader;

/**
 * 加载皮肤过程的事件回调
 * qqliu
 * 2016/9/24.
 */
public interface ILoadSkinListener {
    /***
     * 加载皮肤开始
     * @param skinIdentifier
     */
    void onLoadStart(String skinIdentifier);

    /***
     * 加载皮肤完成
     * @param skinIdentifier
     */
    void onLoadSuccess(String skinIdentifier);

    /***
     * 加载皮肤失败
     * @param skinIdentifier
     */
    void onLoadFail(String skinIdentifier);
}
