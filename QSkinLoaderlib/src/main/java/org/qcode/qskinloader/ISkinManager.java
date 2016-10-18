package org.qcode.qskinloader;

import android.content.Context;
import android.view.View;

import org.qcode.qskinloader.base.observable.IObservable;

/**
 * 皮肤框架管理类接口
 *
 * the skin manager interface, supporting skin operations.
 * qqliu
 * 2016/9/24.
 */
public interface ISkinManager extends IObservable<IActivitySkinEventHandler> {

    /***
     * 初始化皮肤框架
     * initing the skin framework
     * @param context
     */
    void init(Context context);

    /***
     * 恢复到默认皮肤
     *
     * restore to default skin
     * @param defaultSkinIdentifier: default skin identifier, a skin identifier
     *                             identifies a special skin.
     * @param loadListener
     */
    void restoreDefault(String defaultSkinIdentifier, ILoadSkinListener loadListener);

    /***
     * 从指定位置加载一个APK皮肤包；APK皮肤包是另一个未安装的APK应用（只包含资源）.
     * 通过APK皮肤包可以支持动态下载换肤等功能；
     *
     * load an apk resources package from file(indicated by skinPath);
     * an apk resources is an apk application, which only contains resources.
     * APK supports can be applied when dynamically downloading the skin resouces.
     *
     * @param skinPath skinPath is a file path,
     *                 and is also used as the skin identifier.
     * @param loadListener the load result listener
     */
    void loadAPKSkin(String skinPath, ILoadSkinListener loadListener);

    /***
     * 加载指定的皮肤包，皮肤包以skinIdentifier标识，
     * 依靠resourceLoader加载，并通过loadListener告知皮肤切换结果。
     * 由外部指定皮肤加载方式，目前支持APK加载(APKResourceLoader)、后缀方式加载(SuffixResourceLoader)等。
     *
     * load skin for views, the skin is identified by skinIdentifier,
     * loaded by resourceLoader(currently supports APKResourceLoader/SuffixResourceLoader),
     * and the load result is notified by loadListener.
     * @param skinIdentifier the skin identifier
     * @param resourceLoader the resource loader(currently supports APKResourceLoader/SuffixResourceLoader)
     * @param loadListener the skin load result listener
     */
    void loadSkin(String skinIdentifier,
                  IResourceLoader resourceLoader,
                  ILoadSkinListener loadListener);

    /***
     * 对View应用当前的皮肤设置，applyChild 表示对View的子元素设置皮肤
     *
     * apply current skin for the view
     *
     * @param view
     * @param applyChild: true indicates apply skin for view's children
     */
    void applySkin(View view, boolean applyChild);

    /***
     * 注册指定属性的处理器，可以通过此方法覆盖默认的属性处理器，也可以定义自定义属性的属性处理器
     *
     * register a skin attributes handler for attribute(named as attrName)
     *
     * @param attrName : the attribute name
     * @param skinAttrHandler : the attribute handler
     */
    void registerSkinAttrHandler(String attrName, ISkinAttrHandler skinAttrHandler);

    /***
     * 移除指定属性的处理器
     *
     * remove a skin attribute handler for a attribute
     * @param attrName
     */
    void unregisterSkinAttrHandler(String attrName);

    /***
     * 设置一个IResourceManager对象，
     * 可用来替换默认的ResourceManager实现，
     * 在属性处理器内收到替换的ResourceManager实现。
     *
     * set a IResourceManager object to framework to replace the default ResourceManager.
     * @param resourceManager
     */
    void setResourceManager(IResourceManager resourceManager);

    /***
     * 获取框架内的资源管理器对象
     *
     * return the resource manager object used in framework.
     * @return
     */
    IResourceManager getResourceManager();
}
