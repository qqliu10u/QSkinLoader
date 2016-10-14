package org.qcode.qskinloader;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * 皮肤资源管理器接口
 * qqliu
 * 2016/9/25.
 */
public interface IResourceManager {

    void setBaseResource(
            String skinIdentifier,
            IResourceManager baseResource);

    String getSkinIdentifier();

    boolean isDefault();

    Drawable getDrawable(int resId) throws Resources.NotFoundException;

    Drawable getDrawable(int resId, String resName) throws Resources.NotFoundException;

    int getColor(int resId) throws Resources.NotFoundException;

    int getColor(int resId, String resName) throws Resources.NotFoundException;

    ColorStateList getColorStateList(int resId) throws Resources.NotFoundException;

    ColorStateList getColorStateList(int resId, String resName) throws Resources.NotFoundException;
}
