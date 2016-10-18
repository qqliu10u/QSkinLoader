package org.qcode.qskinloader;

import android.app.Activity;

/**
 * 与Activity相关的皮肤框架逻辑处理抽象接口
 *
 * a interface defines how to implement an activity skin event handler,
 * which handles activity's life events like onCreate/onResume...
 *
 * qqliu
 * 2016/9/25.
 */
public interface IActivitySkinEventHandler {
    /***
     * 应该在setContentView之前调用
     *
     * invoked in activity's onCreate method,
     * and should be invoked before setContentView.
     */
    void onCreate(Activity activity);

    /***
     * 在setContentView之后调用
     *
     * should be invoked after setContentView.
     */
    void onViewCreated();

    /***
     * 在onStart()回调内调用
     * invoked in activity's onStart()
     */
    void onStart();

    /***
     * 在onResume()回调内调用
     * invoked in activity's onResume()
     */
    void onResume();

    /***
     * 在onWindowFocusChanged()回调内调用
     *
     * invoked in activity's onWindowFocusChanged()
     */
    void onWindowFocusChanged(boolean hasFocus);

    /***
     * 在onPause()回调内调用
     *
     * invoked in activity's onPause()
     */
    void onPause();

    /***
     * 在onStop()回调内调用
     * invoked in activity's onStop()
     */
    void onStop();

    /***
     * 在onDestroy()回调内调用
     * invoked in activity's onDestroy()
     */
    void onDestroy();

    /***
     * 告知当前界面是否在换肤事件发生时立刻刷新皮肤，
     * false表示Activity获取到focus时才会刷新
     *
     * notify the handler whether the activity handles
     * skin-change event immediately.
     * @param isImmediate
     * @return
     */
    IActivitySkinEventHandler setSwitchSkinImmediately(boolean isImmediate);

    /***
     * 告知当前界面是否支持换肤
     *
     * notify the handler whether the activity
     * supports skin change.
     * @param supportChange
     * @return
     */
    IActivitySkinEventHandler setSupportSkinChange(boolean supportChange);

    /***
     * 告知当前界面的Window的背景色，需要传入资源id
     *
     * tell handler the activity's background color,
     * refered as resource id
     * @param resId
     * @return
     */
    IActivitySkinEventHandler setWindowBackgroundResource(int resId);

    /***
     * 当皮肤发生变化时，此方法会被调用，来完成Activity的皮肤切换工作
     *
     * when skin changes, @ref{handleSkinUpdate} will be
     * called to refresh the activity's skin.
     */
    void handleSkinUpdate();
}
