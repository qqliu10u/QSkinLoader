package org.qcode.qskinloader;

import android.view.View;

import org.qcode.qskinloader.impl.ActivitySkinEventHandlerImpl;
import org.qcode.qskinloader.impl.SkinManagerImpl;
import org.qcode.qskinloader.impl.SkinViewHelperImpl;
import org.qcode.qskinloader.impl.WindowViewManager;

/**
 * 皮肤框架对外接口
 * qqliu
 * 2016/10/8.
 */

public class SkinManager {
    /***
     * 获取皮肤管理类实例
     * @return
     */
    public static ISkinManager getInstance() {
        return SkinManagerImpl.getInstance();
    }

    /***
     * 获取View的皮肤属性管理类
     * @param view
     * @return
     */
    public static ISkinViewHelper with(View view) {
        return new SkinViewHelperImpl(view);
    }

    /***
     * 获取Window View的管理类
     * @return
     */
    public static IWindowViewManager getWindowViewManager() {
        return WindowViewManager.getInstance();
    }

    /***
     * 创建一个新的Activity的皮肤事件处理器
     * @return
     */
    public static IActivitySkinEventHandler newActivitySkinEventHandler() {
        return new ActivitySkinEventHandlerImpl();
    }
}
