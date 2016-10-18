package org.qcode.qskinloader;

import android.view.View;

import java.util.List;

/**
 * 皮肤框架对直接加载在WindowManager上的View的管理器；
 * 包含：悬浮窗、popWindow、Dialog等持有的View；
 * 一般是将Activity的View树以外的View加入框架内管理；
 * 建议成对调用{@ref{addWindowView}和{@ref{removeWindowView}；
 *
 * The manager of Views
 * (added to WindowManager, such as PopWindow/Dialog/Float View...);
 * Basically, the views not add to Activity's View tree
 * should be added to IWindowViewManager for skin changing purpose.
 * addWindowView should be used with removeWindowView in pairs.
 * qqliu
 * 2016/10/8.
 */

public interface IWindowViewManager {
    /***
     * 在框架内增加View的引用，刷新皮肤时会刷新此View及其所有子元素；
     * ，应与{@ref{removeWindowView}成对使用
     *
     * add a view in framework, so that we can refresh
     * the view(and its children)'s skin immediately.
     * should be used with {@ref{removeWindowView} in pairs.
     * @param view
     * @return
     */
    IWindowViewManager addWindowView(View view);

    /***
     * 从框架内移除View的引用；
     * 应与{@ref{addWindowView}成对使用;
     *
     * remove a view from framework, see {@ref{addWindowView}
     * @param view
     * @return
     */
    IWindowViewManager removeWindowView(View view);

    /***
     * 清空框架内持有的所有View的引用；
     * clear all views maintained in framework
     * @return
     */
    IWindowViewManager clear();

    /***
     * 对框架内持有的所有View刷新皮肤；
     *
     * refresh skin for views maintained in framework
     * @param applyChild 表示刷新是否同时刷新View的子元素，一般传入true;
     *                   true means we also refresh the views' children,
     *                   most time true is needed.
     */
    void applySkinForViews(boolean applyChild);

    /***
     * 获取注册到框架内维护的所有View
     *
     * return all the views maintained in the framework.
     * @return
     */
    List<View> getWindowViewList();
}
