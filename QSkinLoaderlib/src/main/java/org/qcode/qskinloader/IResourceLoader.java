package org.qcode.qskinloader;

import org.qcode.qskinloader.resourceloader.ILoadResourceCallback;

/**
 * 皮肤资源加载器接口
 * qqliu
 * 2016/9/25.
 */
public interface IResourceLoader {
    void loadResource(String skinIdentifier,
                      ILoadResourceCallback loadCallBack);
}
