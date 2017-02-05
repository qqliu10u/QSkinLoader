package org.qcode.qskinloader.resourceloader.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.base.utils.HashMapCache;
import org.qcode.qskinloader.entity.SkinConstant;

/**
 * 基于APK方式的资源管理类
 * qqliu
 * 2016/9/18.
 */
public class APKResourceManager implements IResourceManager {
    private static final String TAG = "APKResourceManager";

    private Context mContext;
    private Resources mDefaultResources;
    private String mPackageName;
    private Resources mResources;

    private HashMapCache<String, Integer> mColorCache
            = new HashMapCache<String, Integer>(true);

    public APKResourceManager(Context context, String pkgName, Resources resources) {
        mContext = context;
        mDefaultResources = mContext.getResources();
        mPackageName = pkgName;
        mResources = resources;
    }

    @Override
    public void setBaseResource(String skinIdentifier, IResourceManager baseResource) {
        //do nothing
    }

    @Override
    public String getSkinIdentifier() {
        return null;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public int getColor(int resId) {
        String resName = mDefaultResources.getResourceEntryName(resId);
        return getColor(resId, resName);
    }

    @Override
    public int getColor(int resId, String resName) {
        String resKey = getResKey(mPackageName, resName);

        Integer color = mColorCache.getCache(resKey);
        if (null != color) {
            return color;
        }

        int trueResId = mResources.getIdentifier(
                resName, SkinConstant.RES_TYPE_NAME_COLOR, mPackageName);
        int trueColor = mResources.getColor(trueResId);
        mColorCache.addCache(resKey, trueColor);
        return trueColor;
    }

    public Drawable getDrawable(int resId) {
        String resName = mDefaultResources.getResourceEntryName(resId);
        return getDrawable(resId, resName);
    }

    @SuppressLint("NewApi")
    public Drawable getDrawable(int resId, String resName) {
        int trueResId = mResources.getIdentifier(resName,
                SkinConstant.RES_TYPE_NAME_DRAWABLE, mPackageName);

        if (0 == trueResId) {
            trueResId = mResources.getIdentifier(resName,
                    SkinConstant.RES_TYPE_NAME_MIPMAP, mPackageName);
            if (0 == trueResId) {
                throw new Resources.NotFoundException(resName);
            }
        }

        Drawable trueDrawable;
        if (android.os.Build.VERSION.SDK_INT < 22) {
            trueDrawable = mResources.getDrawable(trueResId);
        } else {
            trueDrawable = mResources.getDrawable(trueResId, null);
        }

        return trueDrawable;
    }

    @Override
    public ColorStateList getColorStateList(int resId) {
        String resName = mDefaultResources.getResourceEntryName(resId);
        return getColorStateList(resId, resName);
    }

    /**
     * 读取ColorStateList
     * @param resId
     * @return
     */
    @Override
    public ColorStateList getColorStateList(int resId, String resName) {
        return getColorStateList(resId, SkinConstant.RES_TYPE_NAME_COLOR, resName);
    }

    @Override
    public ColorStateList getColorStateList(int resId, String typeName, String resName) {
        int trueResId = mResources.getIdentifier(
                resName, typeName, mPackageName);
        ColorStateList colorList = mResources.getColorStateList(trueResId);

        return colorList;
    }

    private String getResKey(String skinPackageName, String resName) {
        return (null == skinPackageName ? "" : skinPackageName) + "_" + resName;
    }
}
