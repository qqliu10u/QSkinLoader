package org.qcode.qskinloader.impl;

import android.view.View;

import org.qcode.qskinloader.IWindowViewManager;
import org.qcode.qskinloader.SkinManager;
import org.qcode.qskinloader.base.utils.CollectionUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 直接加载在Window上的view的管理器
 * qqliu
 * 2016/10/8.
 */

public class WindowViewManager implements IWindowViewManager {
    private ArrayList<WeakReference<View>> mSkinViewList = new ArrayList<WeakReference<View>>();

    //==========================Singleton========================//
    private static volatile WindowViewManager mInstance;

    private WindowViewManager() {}

    public static WindowViewManager getInstance() {
        if(null == mInstance) {
            synchronized (WindowViewManager.class) {
                if(null == mInstance) {
                    mInstance = new WindowViewManager();
                }
            }
        }
        return mInstance;
    }

    //==========================interfaces========================//
    @Override
    public IWindowViewManager addWindowView(View view) {
        ArrayList<WeakReference<View>> tmpList
                = (ArrayList<WeakReference<View>>) mSkinViewList.clone();
        for (WeakReference<View> viewRef : tmpList) {
            if (null == viewRef) {
                continue;
            }
            //已添加此View，则不需要再添加
            if (view == viewRef.get()) {
                return this;
            }
        }

        mSkinViewList.add(new WeakReference<View>(view));

        return this;
    }

    @Override
    public IWindowViewManager removeWindowView(View view) {
        ArrayList<WeakReference<View>> tmpList
                = (ArrayList<WeakReference<View>>) mSkinViewList.clone();
        for (WeakReference<View> viewRef : tmpList) {
            if (null == viewRef) {
                continue;
            }
            //找到了指定View
            if (view == viewRef.get()) {
                mSkinViewList.remove(viewRef);
                break;
            }
        }

        return this;
    }

    @Override
    public IWindowViewManager clear() {
        if(CollectionUtils.isEmpty(mSkinViewList)) {
            return this;
        }

        mSkinViewList.clear();
        return this;
    }

    @Override
    public void applySkinForViews(boolean applyChild) {
        if(CollectionUtils.isEmpty(mSkinViewList)) {
            return;
        }

        for(WeakReference<View> viewRef : mSkinViewList) {
            if(null == viewRef || null == viewRef.get()) {
                continue;
            }

            SkinManager.getInstance().applySkin(viewRef.get(), applyChild);
        }
    }

    @Override
    public List<View> getWindowViewList() {
        ArrayList<View> resultList = new ArrayList<View>();

        ArrayList<WeakReference<View>> tmpList
                = (ArrayList<WeakReference<View>>) mSkinViewList.clone();
        for (WeakReference<View> viewRef : tmpList) {
            if (null == viewRef || null == viewRef.get()) {
                continue;
            }

            resultList.add(viewRef.get());
        }

        return resultList;
    }
}
