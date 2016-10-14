package org.qcode.qskinloader.resourceloader.impl;

import android.content.Context;

import org.qcode.qskinloader.IResourceLoader;
import org.qcode.qskinloader.base.utils.StringUtils;
import org.qcode.qskinloader.resourceloader.ILoadResourceCallback;

/**
 * 基于资源名称后缀拼接方式的资源加载器
 * qqliu
 * 2016/10/9.
 */
public class SuffixResourceLoader implements IResourceLoader {

    private static final String TAG = "SuffixResourceLoader";

    private Context mContext;

    private String mSkinSuffix;

    public SuffixResourceLoader(Context context) {
        this.mContext = context;
    }

    @Override
    public void loadResource(final String skinIdentifier,
                             final ILoadResourceCallback loadCallBack) {
        if (StringUtils.isEmpty(skinIdentifier)) {
            return;
        }

        if (loadCallBack != null) {
            loadCallBack.onLoadStart(skinIdentifier);
        }

        mSkinSuffix = skinIdentifier;

        if (loadCallBack != null) {
            loadCallBack.onLoadSuccess(skinIdentifier,
                    new SuffixResourceManager(mContext, mSkinSuffix));
        }
    }
}
