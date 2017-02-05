package org.qcode.qskinloader.resourceloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.base.utils.Logging;

/**
 * 资源管理类的实现，获取资源的行为会代理到其内部的mBase实现
 * <p>
 * qqliu
 * 2016/9/18.
 */
public class ResourceManager implements IResourceManager {
    private static final String TAG = "ResourceManager";

    private Context mContext;
    private Resources mDefaultResources;
    private IResourceManager mBase;
    private String mSkinIdentifier;

    public ResourceManager(Context context) {
        mContext = context;
        mDefaultResources = mContext.getResources();
    }

    @Override
    public void setBaseResource(String skinIdentifier,
                                IResourceManager baseResource) {
        mSkinIdentifier = skinIdentifier;
        mBase = baseResource;
    }

    @Override
    public boolean isDefault() {
        if(null != mBase) {
            return mBase.isDefault();
        }

        return true;
    }

    @Override
    public int getColor(int resId) {
        if (null != mBase) {
            try {
                return mBase.getColor(resId);
            } catch (Exception ex) {
                Logging.d(TAG, "getColor()| error happened", ex);
            }
        }

        return mDefaultResources.getColor(resId);
    }

    @Override
    public int getColor(int resId, String resName) {
        if (null != mBase) {
            try {
                return mBase.getColor(resId, resName);
            } catch (Exception ex) {
                Logging.d(TAG, "getColor()| error happened", ex);
            }
        }

        return mDefaultResources.getColor(resId);
    }

    @Override
    public ColorStateList getColorStateList(int resId) {
        if (null != mBase) {
            try {
                return mBase.getColorStateList(resId);
            } catch (Exception ex) {
                Logging.d(TAG, "getColorStateList()| error happened", ex);
            }
        }

        return convertToColorStateList(resId);
    }

    @Override
    public ColorStateList getColorStateList(int resId, String resName) {
        if (null != mBase) {
            try {
                return mBase.getColorStateList(resId, resName);
            } catch (Exception ex) {
                Logging.d(TAG, "getColorStateList()| error happened", ex);
            }
        }

        return convertToColorStateList(resId);
    }

    @Override
    public ColorStateList getColorStateList(int resId, String typeName, String resName) {
        if (null != mBase) {
            try {
                return mBase.getColorStateList(resId, typeName, resName);
            } catch (Exception ex) {
                Logging.d(TAG, "getColorStateList()| error happened", ex);
            }
        }

        return convertToColorStateList(resId);
    }

    public Drawable getDrawable(int resId) {
        if (null != mBase) {
            try {
                return mBase.getDrawable(resId);
            } catch (Exception ex) {
                Logging.d(TAG, "getDrawable()| error happened", ex);
            }
        }

        return mDefaultResources.getDrawable(resId);
    }

    @SuppressLint("NewApi")
    public Drawable getDrawable(int resId, String resName) {
        if (null != mBase) {
            try {
                return mBase.getDrawable(resId, resName);
            } catch (Exception ex) {
                Logging.d(TAG, "getDrawable()| error happened", ex);
            }
        }

        return mDefaultResources.getDrawable(resId);
    }

    /**
     * 加载指定资源颜色drawable,转化为ColorStateList，保证selector类型的Color也能被转换。
     * 无皮肤包资源返回默认主题颜色
     *
     * @param resId
     * @return
     */
    private ColorStateList convertToColorStateList(int resId) {
        ColorStateList colorList = null;
        try {
            colorList = mDefaultResources.getColorStateList(resId);
        } catch (Resources.NotFoundException ex) {
            Logging.d(TAG, "convertToColorStateList()| error happened", ex);
        }

        if (colorList != null) {
            return colorList;
        }

        int[][] states = new int[1][1];
        colorList =
                new ColorStateList(states, new int[]{
                        mDefaultResources.getColor(resId)});
        return colorList;
    }

    @Override
    public String getSkinIdentifier() {
        return mSkinIdentifier;
    }
}
