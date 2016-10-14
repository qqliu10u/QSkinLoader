package org.qcode.qskinloader;

import android.content.Context;
import android.view.View;

import org.qcode.qskinloader.base.observable.IObservable;

/**
 * 皮肤框架管理类接口
 *
 * qqliu
 * 2016/9/24.
 */
public interface ISkinManager extends IObservable<IActivitySkinEventHandler> {
    void init(Context context);
    void restoreDefault(String defaultSkinIdentifier, ILoadSkinListener loadListener);
    void loadAPKSkin(String skinPath, ILoadSkinListener loadListener);
    void loadSkin(String skinIdentifier,
                  IResourceLoader resourceLoader,
                  ILoadSkinListener loadListener);
    void applySkin(View view, boolean applyChild);
    void registerSkinAttrHandler(String attrName, ISkinAttrHandler skinAttrHandler);
    void unregisterSkinAttrHandler(String attrName);
    void setResourceManager(IResourceManager resourceManager);
    IResourceManager getResourceManager();
}
