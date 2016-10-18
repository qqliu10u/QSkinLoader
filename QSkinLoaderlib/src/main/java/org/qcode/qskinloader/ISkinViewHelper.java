package org.qcode.qskinloader;

import org.qcode.qskinloader.entity.DynamicAttr;
import org.qcode.qskinloader.entity.SkinAttr;

import java.util.List;

/**
 * 皮肤框架中View相关皮肤属性的管理器接口抽象
 *
 * The abstract interface for registering skin attributes dynamically.
 * qqliu
 * 2016/10/8.
 */

public interface ISkinViewHelper {
    /***
     * 给指定View注册一个属性名称为attrName，属性值为resId的皮肤属性；
     * 此View之前注册的属性会被覆盖；
     *
     * apply a skin attribute(named as attrName, value is resId) to View,
     * the skin attributes previously registered is removed.
     * @param attrName
     * @param resId
     * @return
     */
    ISkinViewHelper setViewAttrs(String attrName, int resId);

    /***
     * 给指定View注册多个皮肤属性；
     * 此View之前注册的属性会被覆盖；
     *
     * apply skin attributes(
     * named as attrName, value is resId, indicated in DynamicAttr) to View,
     * the skin attributes previously registered is removed.
     *
     * @param dynamicAttrs
     * @return
     */
    ISkinViewHelper setViewAttrs(DynamicAttr... dynamicAttrs);

    /***
     * 给指定View注册多个皮肤属性；
     * 此View之前注册的属性会被覆盖；
     *
     * apply skin attributes(
     * named as attrName, value is resId, indicated in SkinAttr) to View,
     * the skin attributes previously registered is removed.
     *
     * @param skinAttrs
     * @return
     */
    ISkinViewHelper setViewAttrs(SkinAttr... skinAttrs);

    /***
     * 给指定View注册多个皮肤属性；
     * 此View之前注册的属性会被覆盖；
     *
     * apply skin attributes(
     * named as attrName, value is resId, indicated in DynamicAttr) to View,
     * the skin attributes previously registered is removed.
     *
     * @param dynamicAttrs
     * @return
     */
    ISkinViewHelper setViewAttrs(List<DynamicAttr> dynamicAttrs);

    /***
     * 给指定View添加一个属性名称为attrName，属性值为resId的皮肤属性；
     * 此View之前注册的属性不会被覆盖；
     *
     * add a skin attribute(named as attrName, value is resId) to View,
     * the skin attributes previously registered is maintained.
     * @param attrName
     * @param resId
     * @return
     */
    ISkinViewHelper addViewAttrs(String attrName, int resId);

    /***
     * 给指定View添加多个皮肤属性；
     * 此View之前注册的属性不会被覆盖；
     *
     * add skin attributes(
     * named as attrName, value is resId, indicated in DynamicAttr) to View,
     * the skin attributes previously registered is maintained.
     *
     * @param dynamicAttrs
     * @return
     */
    ISkinViewHelper addViewAttrs(DynamicAttr... dynamicAttrs);

    /***
     * 给指定View添加多个皮肤属性；
     * 此View之前注册的属性不会被覆盖；
     *
     * add skin attributes(
     * named as attrName, value is resId, indicated in SkinAttr) to View,
     * the skin attributes previously registered is maintained.
     *
     * @param skinAttrs
     * @return
     */
    ISkinViewHelper addViewAttrs(SkinAttr... skinAttrs);

    /***
     * 给指定View添加多个皮肤属性；
     * 此View之前注册的属性不会被覆盖；
     *
     * add skin attributes(
     * named as attrName, value is resId, indicated in DynamicAttr) to View,
     * the skin attributes previously registered is maintained.
     *
     * @param dynamicAttrs
     * @return
     */
    ISkinViewHelper addViewAttrs(List<DynamicAttr> dynamicAttrs);

    /***
     * 移除View内注册的皮肤属性
     *
     * remove all skin attributes for a view
     * @param clearChild true表示同时移除View的子元素的皮肤属性；
     *                   false只移除View的皮肤属性；
     *                   true indicates also removing skin attributes for the view's children,
     *                   false means only remove skin attributes for the view itself;
     * @return
     */
    ISkinViewHelper cleanAttrs(boolean clearChild);

    /***
     * 对View应用当前的皮肤设置；
     *
     * apply current skin for the view
     * @param applyChild true表示对View子元素也应用皮肤；
     *                   false表示只对View应用皮肤；
     *                   true indicates apply skin for the view's children,
     *                   false otherwise;
     */
    void applySkin(boolean applyChild);
}
