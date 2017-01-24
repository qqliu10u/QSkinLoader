package org.qcode.qskinloader.resourceloader.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import org.qcode.qskinloader.IResourceManager;

/**
 * 基于资Android原生夜间模式的ConfigChange方式的资源管理类
 * qqliu
 * 2016/10/9.
 */
public class ConfigChangeResourceManager implements IResourceManager {
    private static final String TAG = "ConfigChangeResourceManager";

    private Context mContext;
    private String mSkinIdentifier;
    private Resources mResources;

    public ConfigChangeResourceManager(Context context, String skinIdentifier) {
        mContext = context;
        mResources = mContext.getResources();
        mSkinIdentifier = skinIdentifier;
    }

    @Override
    public void setBaseResource(String skinIdentifier, IResourceManager baseResource) {
        //do nothing
    }

    @Override
    public String getSkinIdentifier() {
        return mSkinIdentifier;
    }

    @Override
    public boolean isDefault() {
        return ConfigChangeResourceLoader.MODE_DAY.equals(mSkinIdentifier);
    }

    @Override
    public int getColor(int resId) {
        return mResources.getColor(resId);
    }

    @Override
    public int getColor(int resId, String resName) {
        return mResources.getColor(resId);
    }

    public Drawable getDrawable(int resId) {
        return mResources.getDrawable(resId);
    }

    @SuppressLint("NewApi")
    public Drawable getDrawable(int resId, String resName) {
        return mResources.getDrawable(resId);
    }

    @Override
    public ColorStateList getColorStateList(int resId) {
        return mResources.getColorStateList(resId);
    }

    @Override
    public ColorStateList getColorStateList(int resId, String resName) {
        return mResources.getColorStateList(resId);
    }

    @Override
    public ColorStateList getColorStateList(int resId, String typeName, String resName) {
        return mResources.getColorStateList(resId);
    }
}
