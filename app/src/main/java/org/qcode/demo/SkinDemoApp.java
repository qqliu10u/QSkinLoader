package org.qcode.demo;

import android.app.Application;
import android.content.Context;

import org.qcode.demo.base.Settings;
import org.qcode.demo.skin.SkinChangeHelper;
import org.qcode.qskinloader.SkinManager;

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
        // 加载皮肤框架
        SkinManager.getInstance().init(this);
        //皮肤初始化
        SkinChangeHelper.getInstance().refreshSkin(null);
    }

    public static Context getAppContext() {
        return mContext;
    }
}
