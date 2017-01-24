package org.qcode.qskinloader;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * 皮肤资源管理器接口
 *
 * the interface defines what a resource manager should do.
 * qqliu
 * 2016/9/25.
 */
public interface IResourceManager {

    /***
     * 为IResourceManager设置一个真正工作的资源管理器，IResourceManager是baseResource的封装
     * set a resource manager as a base worker for the IResourceManager.
     * @param skinIdentifier 皮肤的唯一标识;
     *                       the skin identifier
     * @param baseResource 真正读取资源的ResourceManager;
     *                     the real resource manager which defines how to attach resources.
     */
    void setBaseResource(
            String skinIdentifier,
            IResourceManager baseResource);

    /***
     * get skin identifier
     * @return
     */
    String getSkinIdentifier();

    /***
     * return whether current is default skin
     * @return
     */
    boolean isDefault();

    /***
     * get drawable by resource id
     * @param resId
     * @return
     * @throws Resources.NotFoundException
     */
    Drawable getDrawable(int resId) throws Resources.NotFoundException;

    /***
     * get drawable by resource id and name
     * @param resId
     * @param resName
     * @return
     * @throws Resources.NotFoundException
     */
    Drawable getDrawable(int resId, String resName) throws Resources.NotFoundException;

    /***
     * get color by resource id
     * @param resId
     * @return
     * @throws Resources.NotFoundException
     */
    int getColor(int resId) throws Resources.NotFoundException;

    /***
     * get color by resource id and resource name
     * @param resId
     * @param resName
     * @return
     * @throws Resources.NotFoundException
     */
    int getColor(int resId, String resName) throws Resources.NotFoundException;

    /***
     * get ColorStateList by resource id
     * @param resId
     * @return
     * @throws Resources.NotFoundException
     */
    ColorStateList getColorStateList(int resId) throws Resources.NotFoundException;

    /***
     * get ColorStateList by resource id and name
     * @param resId
     * @param resName
     * @return
     * @throws Resources.NotFoundException
     */
    ColorStateList getColorStateList(int resId, String resName) throws Resources.NotFoundException;

    /***
     * get ColorStateList by resource id and name
     * @param resId
     * @param typeName
     * @param resName
     * @return
     * @throws Resources.NotFoundException
     */
    ColorStateList getColorStateList(int resId, String typeName, String resName) throws Resources.NotFoundException;
}
