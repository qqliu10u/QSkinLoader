package org.qcode.qskinloader;

import android.app.Activity;

/**
 * 与Activity相关的皮肤框架逻辑处理抽象接口
 *
 * qqliu
 * 2016/9/25.
 */
public interface IActivitySkinEventHandler {
    /***
     * 应该在setContentView之前调用
     */
    void onCreate(Activity activity);

    /**在setContentView之后调用*/
    void onViewCreated();

    void onStart();

    void onResume();

    void onWindowFocusChanged(boolean hasFocus);

    void onPause();

    void onStop();

    void onDestroy();

    IActivitySkinEventHandler setSwitchSkinImmediately(boolean isImmediate);

    IActivitySkinEventHandler setSupportSkinChange(boolean supportChange);

    IActivitySkinEventHandler setWindowBackgroundResource(int resId);

    void handleSkinUpdate();
}
