package org.qcode.qskinloader.resourceloader.impl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;

import org.qcode.qskinloader.resourceloader.ILoadResourceCallback;
import org.qcode.qskinloader.IResourceLoader;
import org.qcode.qskinloader.base.utils.Logging;
import org.qcode.qskinloader.base.utils.StringUtils;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 基于APK方式的资源加载器
 * qqliu
 * 2016/9/25.
 */
public class APKResourceLoader implements IResourceLoader {

    private static final String TAG = "APKResourceLoader";

    private Context mContext;

    private String mPackageName;

    private Resources mResources;

    public APKResourceLoader(Context context) {
        this.mContext = context;
    }

    @Override
    public void loadResource(final String skinIdentifier,
                             final ILoadResourceCallback loadCallBack) {
        if (StringUtils.isEmpty(skinIdentifier)) {
            return;
        }

        new AsyncTask<String, Void, APkLoadResult>() {

            @Override
            protected void onPreExecute() {
                if (loadCallBack != null) {
                    loadCallBack.onLoadStart(skinIdentifier);
                }
            }

            @Override
            protected APkLoadResult doInBackground(String... params) {
                if (null == mContext || null == params || params.length <= 0) {
                    return null;
                }

                try {
                    String skinPkgPath = params[0];

                    File file = new File(skinPkgPath);
                    if (file == null || !file.exists()) {
                        return null;
                    }

                    PackageManager packageManager = mContext.getPackageManager();
                    PackageInfo packageInfo = packageManager.getPackageArchiveInfo(
                            skinPkgPath, PackageManager.GET_ACTIVITIES);
                    String skinPkgName = packageInfo.packageName;

                    AssetManager assetManager = AssetManager.class.newInstance();
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    addAssetPath.invoke(assetManager, skinPkgPath);

                    Resources superResources = mContext.getResources();
                    Resources skinResource = new Resources(
                            assetManager, superResources.getDisplayMetrics(),
                            superResources.getConfiguration());
                    return new APkLoadResult(skinPkgName, skinResource);
                } catch (Exception ex) {
                    Logging.d(TAG, "doInBackground()| exception happened", ex);
                }

                return null;
            }

            @Override
            protected void onPostExecute(APkLoadResult result) {
                if (null != result) {
                    if (loadCallBack != null) {
                        loadCallBack.onLoadSuccess(skinIdentifier,
                                new APKResourceManager(
                                        mContext, result.pkgName, result.resources));
                    }
                } else {
                    if (loadCallBack != null) {
                        loadCallBack.onLoadFail(skinIdentifier, -1);
                    }
                }
            }

        }.execute(skinIdentifier);
    }

    private static class APkLoadResult {
        String pkgName;
        Resources resources;

        public APkLoadResult(String pkgName, Resources resources) {
            this.pkgName = pkgName;
            this.resources = resources;
        }
    }
}
