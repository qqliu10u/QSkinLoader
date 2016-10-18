package org.qcode.qskinloader;

import android.view.View;

import org.qcode.qskinloader.impl.ActivitySkinEventHandlerImpl;
import org.qcode.qskinloader.impl.SkinManagerImpl;
import org.qcode.qskinloader.impl.SkinViewHelperImpl;
import org.qcode.qskinloader.impl.WindowViewManager;

/**
 * 皮肤框架对外接口;
 *
 * the base entrance class for the QSkinLoader library
 * qqliu
 * 2016/10/8.
 */

public class SkinManager {

    /***
     * 获取皮肤管理类实例;
     *
     * return an ISkinManager object to deal with skin manager events
     * such as init/skin change/apply skin for view ...
     * @return
     */
    public static ISkinManager getInstance() {
        return SkinManagerImpl.getInstance();
    }

    /***
     * 获取View的皮肤属性管理类，支持链式编程，可动态操作View的皮肤属性;
     *
     * return an ISinViewHelper to add/remove skin attrs dynamically;
     * ISkinViewHelper supports the chain programming style;
     * @param view
     * @return
     */
    public static ISkinViewHelper with(View view) {
        return new SkinViewHelperImpl(view);
    }

    /***
     * 获取Window View的管理类；
     * 框架只能自动支持刷新Activity的ContentView，
     * 对于PopupWindow/对话框/悬浮窗等View，
     * 只能通过此方法注册到框架内来保证换肤效果；
     *
     * return an IWindowViewManager to add/remove view to the framework;
     * the framework only supports apply skin for Activity's
     * content view(by findViewById(android.R.id.content));
     * so that other views(PopupWindow/Dialog/View directly added to WindowManager)
     * should be add to framework for skin changing.
     * @return
     */
    public static IWindowViewManager getWindowViewManager() {
        return WindowViewManager.getInstance();
    }

    /***
     * 创建一个新的Activity的皮肤事件处理器；
     * IActivitySkinEventHandler用于代理完成
     * Activity内各生命周期与皮肤相关的逻辑；
     *
     * return a new IActivitySkinEventHandler object for Activity;
     * IActivitySkinEventHandler handles event for the skin framework,
     * and should be notified when activity life state change(onCreate/onResume/onPause...)
     * @return
     */
    public static IActivitySkinEventHandler newActivitySkinEventHandler() {
        return new ActivitySkinEventHandlerImpl();
    }
}
