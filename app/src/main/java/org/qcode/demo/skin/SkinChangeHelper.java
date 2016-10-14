package org.qcode.demo.skin;

import android.content.Context;

import org.qcode.demo.utils.UITaskRunner;
import org.qcode.qskinloader.ILoadSkinListener;
import org.qcode.qskinloader.SkinManager;
import org.qcode.demo.SkinDemoApp;
import org.qcode.demo.utils.UIUtil;
import org.qcode.qskinloader.resourceloader.impl.SuffixResourceLoader;

import java.io.File;

/**
 * qqliu
 * 2016/9/26.
 */
public class SkinChangeHelper {
    private static final String TAG = "SkinChangeHelper";

    private static volatile SkinChangeHelper mInstance;
    private final Context mContext;

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

    public void switchSkinMode(OnSkinChangeListener listener) {
        mIsSwitching = true;
        mIsDefaultMode = !mIsDefaultMode;
        refreshSkin(listener);
    }

    public void refreshSkin(OnSkinChangeListener listener) {
        if (mIsDefaultMode) {
            //恢复到默认皮肤
            SkinManager.getInstance().restoreDefault(SkinConstant.DEFAULT_SKIN, new MyLoadSkinListener(listener));
        } else {
            changeSkin(listener);
        }
    }

    public boolean isDefaultMode() {
        return mIsDefaultMode;
    }

    public boolean isSwitching() {
        return mIsSwitching;
    }

    private void changeSkin(OnSkinChangeListener listener) {
        SkinUtils.copyAssetSkin(mContext);

        File skin = new File(
                SkinUtils.getTotalSkinPath(mContext));

        if (skin == null || !skin.exists()) {
            UIUtil.showToast(mContext, "皮肤初始化失败");
            return;
        }

        SkinManager.getInstance().loadSkin("_night",
                new SuffixResourceLoader(mContext),
                new MyLoadSkinListener(listener));
//        SkinManager.getInstance().loadAPKSkin(
//                skin.getAbsolutePath(), new MyLoadSkinListener(listener));
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
