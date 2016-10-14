package org.qcode.qskinloader.resourceloader.impl;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import org.qcode.qskinloader.IResourceLoader;
import org.qcode.qskinloader.base.utils.StringUtils;
import org.qcode.qskinloader.resourceloader.ILoadResourceCallback;

/**
 * 基于Android原生夜间模式的ConfigChange方式的资源加载器
 * qqliu
 * 2016/10/9.
 */
public class ConfigChangeResourceLoader implements IResourceLoader {

    private static final String TAG = "ConfigChangeResourceLoader";

    public static final String MODE_DAY = "day";
    public static final String MODE_NIGHT = "night";

    private Context mContext;

    public ConfigChangeResourceLoader(Context context) {
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

        switchMode(MODE_NIGHT.equals(skinIdentifier));

        if (loadCallBack != null) {
            loadCallBack.onLoadSuccess(skinIdentifier,
                    new ConfigChangeResourceManager(mContext, skinIdentifier));
        }
    }

    private void switchMode(boolean isNightMode) {
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
        config.uiMode |= isNightMode ?
                Configuration.UI_MODE_NIGHT_YES :
                Configuration.UI_MODE_NIGHT_NO;
        resources.updateConfiguration(config, dm);
    }
}
