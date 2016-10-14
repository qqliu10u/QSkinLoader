package org.qcode.qskinloader;

import android.view.View;

import java.util.List;

/**
 * 皮肤框架对直接加载在WindowManager上的View的管理器；
 * 包含：悬浮窗、popWindow、Dialog等持有的View；
 * 建议成对调用{@ref{addWindowView}和{@ref{removeWindowView}；
 *
 * qqliu
 * 2016/10/8.
 */

public interface IWindowViewManager {
    IWindowViewManager addWindowView(View view);
    IWindowViewManager removeWindowView(View view);
    IWindowViewManager clear();
    void applySkinForViews(boolean applyChild);
    List<View> getWindowViewList();
}
