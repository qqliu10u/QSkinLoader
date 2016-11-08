package org.qcode.demo;

import android.app.Application;
import android.content.Context;

import org.qcode.demo.base.Settings;
import org.qcode.demo.skin.SkinChangeHelper;

/**
 * qqliu
 * 2016/9/9.
 */
public class SkinDemoApp extends Application {

    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = this;

        Settings.createInstance(this);

        initSkinLoader();
    }

    /**
     * Must call init first
     */
    private void initSkinLoader() {
        // 初始化皮肤框架
        SkinChangeHelper.getInstance().init(this);
        //初始化上次缓存的皮肤
        SkinChangeHelper.getInstance().refreshSkin(null);
    }

    public static Context getAppContext() {
        return mContext;
    }
}
