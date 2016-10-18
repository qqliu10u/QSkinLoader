package org.qcode.qskinloader;

import org.qcode.qskinloader.resourceloader.ILoadResourceCallback;

/**
 * 皮肤资源加载器接口
 *
 * A interface defines the load resource behaviour.
 * A resource loader loads resource and notify load behaviour by @ref{ILoadResourceCallback}
 * qqliu
 * 2016/9/25.
 */
public interface IResourceLoader {

    /***
     * 定义资源加载的行为接口，加载的皮肤以skinIdentifier标识，
     * 加载结果以loadCallBack通知加载资源结果
     *
     * loads the skin identified by skinIdentifier,
     * notifies load behaviour by loadCallBack
     * @param skinIdentifier
     * @param loadCallBack
     */
    void loadResource(String skinIdentifier,
                      ILoadResourceCallback loadCallBack);
}
