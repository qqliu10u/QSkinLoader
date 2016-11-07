package org.qcode.qskinloader.impl;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;

import org.qcode.qskinloader.IActivitySkinEventHandler;
import org.qcode.qskinloader.ILoadSkinListener;
import org.qcode.qskinloader.IResourceLoader;
import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.ISkinManager;
import org.qcode.qskinloader.attrhandler.SkinAttrFactory;
import org.qcode.qskinloader.attrhandler.SkinAttrUtils;
import org.qcode.qskinloader.base.observable.INotifyUpdate;
import org.qcode.qskinloader.base.observable.Observable;
import org.qcode.qskinloader.base.utils.CollectionUtils;
import org.qcode.qskinloader.base.utils.Logging;
import org.qcode.qskinloader.base.utils.StringUtils;
import org.qcode.qskinloader.entity.SkinAttrSet;
import org.qcode.qskinloader.resourceloader.ILoadResourceCallback;
import org.qcode.qskinloader.resourceloader.ResourceManager;
import org.qcode.qskinloader.resourceloader.impl.APKResourceLoader;

import java.util.List;

/**
 * 皮肤加载管理类对外实现接口
 * qqliu
 * 2016/9/24.
 */
public class SkinManagerImpl implements ISkinManager {

    private static final String TAG = "SkinManager";

    //单例相关
    private static volatile SkinManagerImpl mInstance;

    private SkinManagerImpl() {
    }

    public static SkinManagerImpl getInstance() {
        if (null == mInstance) {
            synchronized (SkinManagerImpl.class) {
                if (null == mInstance) {
                    mInstance = new SkinManagerImpl();
                }
            }
        }
        return mInstance;
    }

    private Context mContext;
    private IResourceManager mResourceManager;

    private Observable<IActivitySkinEventHandler> mObservable;

    @Override
    public void init(Context context) {
        mContext = context.getApplicationContext();
        mResourceManager = new ResourceManager(mContext);
        mObservable = new Observable<IActivitySkinEventHandler>();
        new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {
                return null;
            }

        }.execute("");
    }

    @Override
    public void restoreDefault(String defaultSkinIdentifier, ILoadSkinListener loadListener) {
        if (null != loadListener) {
            loadListener.onLoadStart(defaultSkinIdentifier);
        }

        //恢复ResourceManager的行为
        mResourceManager.setBaseResource(null, null);

        refreshAllSkin();

        if (loadListener != null) {
            loadListener.onLoadSuccess(defaultSkinIdentifier);
        }
    }

    private void refreshAllSkin() {
        //刷新正常的Activity内View的皮肤
        refreshSkin();

        //刷新框架内维护的View的皮肤,包括Dialog/popWindow/悬浮窗等应用场景
        applyWindowViewSkin();
    }

    @Override
    public void loadAPKSkin(String skinPath, ILoadSkinListener loadListener) {
        loadSkin(skinPath, new APKResourceLoader(mContext), loadListener);
    }

    @Override
    public void loadSkin(final String skinIdentifier,
                         final IResourceLoader resourceLoader,
                         final ILoadSkinListener loadListener) {
        if(StringUtils.isEmpty(skinIdentifier)
                || null == resourceLoader) {
            if(null != loadListener) {
                loadListener.onLoadFail(skinIdentifier);
            }
            return;
        }

        //当前皮肤就是将要换肤的皮肤，则不执行后续行为
        if (skinIdentifier.equals(mResourceManager.getSkinIdentifier())) {
            Logging.d(TAG, "load()| current skin matches target, do nothing");
            if(null != loadListener) {
                loadListener.onLoadSuccess(skinIdentifier);
            }
            return;
        }

        resourceLoader.loadResource(skinIdentifier, new ILoadResourceCallback() {
            @Override
            public void onLoadStart(String identifier) {
                if (loadListener != null) {
                    loadListener.onLoadStart(skinIdentifier);
                }
            }

            @Override
            public void onLoadSuccess(String identifier, IResourceManager result) {
                Logging.d(TAG, "onLoadSuccess() | identifier= " + identifier);
                mResourceManager.setBaseResource(identifier, result);

                refreshAllSkin();

                Logging.d(TAG, "onLoadSuccess()| notify update");
                if (loadListener != null) {
                    loadListener.onLoadSuccess(skinIdentifier);
                }
            }

            @Override
            public void onLoadFail(String identifier, int errorCode) {
                mResourceManager.setBaseResource(null, null);
                if (loadListener != null) {
                    loadListener.onLoadFail(skinIdentifier);
                }
            }
        });
    }

    @Override
    public void applySkin(View view, boolean applyChild) {
        if (null == view) {
            return;
        }

        SkinAttrSet skinAttrSet = ViewSkinTagHelper.getSkinAttrs(view);
        SkinAttrUtils.applySkinAttrs(view, skinAttrSet, mResourceManager);

        if (applyChild) {
            if (view instanceof ViewGroup) {
                //遍历子元素应用皮肤
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    applySkin(viewGroup.getChildAt(i), true);
                }
            }
        }
    }

    @Override
    public void registerSkinAttrHandler(String attrName, ISkinAttrHandler skinAttrHandler) {
        SkinAttrFactory.registerSkinAttrHandler(attrName, skinAttrHandler);
    }

    @Override
    public void unregisterSkinAttrHandler(String attrName) {
        SkinAttrFactory.removeSkinAttrHandler(attrName);
    }

    @Override
    public void setResourceManager(IResourceManager resourceManager) {
        if(null == resourceManager) {
            return;
        }
        mResourceManager = resourceManager;
    }

    @Override
    public IResourceManager getResourceManager() {
        return mResourceManager;
    }

    @Override
    public void addObserver(IActivitySkinEventHandler observer) {
        mObservable.addObserver(observer);
    }

    @Override
    public void removeObserver(IActivitySkinEventHandler observer) {
        mObservable.removeObserver(observer);
    }

    @Override
    public void notifyUpdate(INotifyUpdate<IActivitySkinEventHandler> callback, String identifier, Object... params) {
        mObservable.notifyUpdate(callback, identifier, params);
    }

    /**刷新Window上添加的View的显示模式*/
    void applyWindowViewSkin() {
        List<View> windowViewList = WindowViewManager.getInstance().getWindowViewList();
        if(CollectionUtils.isEmpty(windowViewList)) {
            return;
        }

        for(View view : windowViewList) {
            applySkin(view, true);
        }
    }

    /***
     * 告知外部观察者当前皮肤发生了变化
     */
    private void refreshSkin() {
        notifyUpdate(new INotifyUpdate<IActivitySkinEventHandler>() {
            @Override
            public boolean notifyEvent(
                    IActivitySkinEventHandler handler,
                    String identifier,
                    Object... params) {
                handler.handleSkinUpdate();
                return false;
            }
        }, null);
    }
}
