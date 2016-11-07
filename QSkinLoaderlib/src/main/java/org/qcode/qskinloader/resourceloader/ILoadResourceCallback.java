package org.qcode.qskinloader.resourceloader;

import org.qcode.qskinloader.IResourceManager;

/**
 * 加载皮肤资源的回调
 * qqliu
 * 2016/9/25.
 */
public interface ILoadResourceCallback {
    /***
     * 加载皮肤资源开始
     *
     * @param identifier
     */
    void onLoadStart(String identifier);

    /***
     * 加载皮肤资源成功
     *
     * @param identifier
     * @param result
     */
    void onLoadSuccess(String identifier, IResourceManager result);

    /***
     * 加载皮肤资源失败
     *
     * @param identifier
     * @param errorCode
     */
    void onLoadFail(String identifier, int errorCode);
}
