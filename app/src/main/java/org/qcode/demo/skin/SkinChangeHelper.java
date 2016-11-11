package org.qcode.demo.skin;

import android.content.Context;

import org.qcode.demo.SkinDemoApp;
import org.qcode.demo.utils.UITaskRunner;
import org.qcode.demo.utils.UIUtil;
import org.qcode.qskinloader.ILoadSkinListener;
import org.qcode.qskinloader.SkinManager;
import org.qcode.qskinloader.resourceloader.impl.ConfigChangeResourceLoader;
import org.qcode.qskinloader.resourceloader.impl.SuffixResourceLoader;

import java.io.File;

/**
 * qqliu
 * 2016/9/26.
 */
public class SkinChangeHelper {
    private static final String TAG = "SkinChangeHelper";
    //基于suffix换肤
    private static final int TYPE_SUFFIX = 1;
    //基于apk换肤
    private static final int TYPE_APK = 2;
    //基于UIMode换肤
    private static final int TYPE_UIMODE = 3;

    private static volatile SkinChangeHelper mInstance;
    private final Context mContext;

    //目前框架支持三种换肤方式，后缀换肤/APK资源包换肤/UIMode换肤
    private int mSkinChangeType = TYPE_SUFFIX;


    private SkinChangeHelper() {
        mContext = SkinDemoApp.getAppContext();
        mIsDefaultMode = SkinConfigHelper.isDefaultSkin();
    }

    public static SkinChangeHelper getInstance() {
        if (null == mInstance) {
            synchronized (SkinChangeHelper.class) {
                if (null == mInstance) {
                    mInstance = new SkinChangeHelper();
                }
            }
        }
        return mInstance;
    }

    private volatile boolean mIsDefaultMode = false;

    private volatile boolean mIsSwitching = false;

    public void init(Context context) {
        SkinManager.getInstance().init(context);
    }

    public void switchSkinMode(OnSkinChangeListener listener) {
        mIsSwitching = true;
        mIsDefaultMode = !mIsDefaultMode;
        refreshSkin(listener);
    }

    public void refreshSkin(OnSkinChangeListener listener) {
        if (mIsDefaultMode) {
            switch (mSkinChangeType) {
                case TYPE_SUFFIX:
                case TYPE_APK:
                    //恢复到默认皮肤
                    SkinManager.getInstance().restoreDefault(
                            SkinConstant.DEFAULT_SKIN,
                            new MyLoadSkinListener(listener));
                    break;

                case TYPE_UIMODE:
                    //基于UIMode换肤只能通过改回配置才能换肤
                    changeSkinByConfig(ConfigChangeResourceLoader.MODE_DAY, listener);
                    break;
            }

        } else {
            switch (mSkinChangeType) {
                case TYPE_SUFFIX:
                    changeSkinBySuffix(listener);
                    break;

                case TYPE_APK:
                    changeSkinByApk(listener);
                    break;

                case TYPE_UIMODE:
                    //基于UIMode换肤只能通过改回配置才能换肤
                    changeSkinByConfig(ConfigChangeResourceLoader.MODE_NIGHT, listener);
                    break;
            }
        }
    }

    public boolean isDefaultMode() {
        return mIsDefaultMode;
    }

    public boolean isSwitching() {
        return mIsSwitching;
    }

    private void changeSkinByApk(OnSkinChangeListener listener) {
        SkinUtils.copyAssetSkin(mContext);

        File skin = new File(
                SkinUtils.getTotalSkinPath(mContext));

        if (skin == null || !skin.exists()) {
            UIUtil.showToast(mContext, "皮肤初始化失败");
            return;
        }

        SkinManager.getInstance().loadAPKSkin(
                skin.getAbsolutePath(), new MyLoadSkinListener(listener));
    }

    private void changeSkinBySuffix(OnSkinChangeListener listener) {
                SkinManager.getInstance().loadSkin("_night",
                new SuffixResourceLoader(mContext),
                new MyLoadSkinListener(listener));
    }

    private void changeSkinByConfig(String mode, OnSkinChangeListener listener) {
        SkinManager.getInstance().loadSkin(mode,
                new ConfigChangeResourceLoader(mContext),
                new MyLoadSkinListener(listener));
    }

    private class MyLoadSkinListener implements ILoadSkinListener {

        private final OnSkinChangeListener mListener;

        public MyLoadSkinListener(OnSkinChangeListener listener) {
            mListener = listener;
        }

        @Override
        public void onLoadStart(String skinIdentifier) {
        }

        @Override
        public void onLoadSuccess(String skinIdentifier) {
            mIsSwitching = false;

            //存储皮肤标识
            SkinConfigHelper.saveSkinIdentifier(skinIdentifier);

            UITaskRunner.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    if(null != mListener) {
                        mListener.onSuccess();
                    }
                }
            });
        }

        @Override
        public void onLoadFail(String skinIdentifier) {
            mIsSwitching = false;

            UITaskRunner.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (null != mListener) {
                        mListener.onError();
                    }
                }
            });
        }
    };

    public interface OnSkinChangeListener {
        void onSuccess();

        void onError();
    }
}
