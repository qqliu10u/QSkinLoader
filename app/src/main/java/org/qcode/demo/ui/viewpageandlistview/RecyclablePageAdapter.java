package org.qcode.demo.ui.viewpageandlistview;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.qcode.qskinloader.base.utils.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 可回收利用的PageAdapter
 * T代表界面展示的View
 * qqliu
 * 2016/5/6.
 */
public abstract class RecyclablePageAdapter<T extends View> extends PagerAdapter {
    protected static final int TYPE_DEFAULT = 0;

    private static final String TAG = "RecyclablePageAdapter";

    private SparseArray<List<T>> mRecycledViews = new SparseArray<List<T>>();
    private HashMap<Object, ViewBundle> mUsingViewsMap = new HashMap<Object, ViewBundle>();
    //上一次展示的位置
    private int mLastItemPosition = -1;

    @Override
    public void startUpdate(ViewGroup container) {
//        Logging.d(TAG, "startUpdate()");
        super.startUpdate(container);
    }

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        Logging.d(TAG, "instantiateItem()| position= " + position);

        Object dataObject = getItemObject(position);

        //如果要实例化的元素就是当前位置，则直接绑定数据
//        Logging.d(TAG, "instantiateItem()| get view for " + dataObject);
        ViewBundle currentUsingView = mUsingViewsMap.get(dataObject);
        if (null != currentUsingView) {
            onBindView(currentUsingView.view, position, currentUsingView.viewType);
            return dataObject;
        }

        int itemViewType = getItemViewType(position);

        //获取新的View
        T itemView = null;
        List<T> recyclableViewList = mRecycledViews.get(itemViewType);
        if (null != recyclableViewList && recyclableViewList.size() > 0) {
            itemView = recyclableViewList.remove(0);
        } else {
            itemView = createItemView(itemViewType);
        }

        //绑定数据
        onBindView(itemView, position, itemViewType);
        container.addView(itemView);
//        Logging.d(TAG, "instantiateItem()| position= " + position + " itemView= " + itemView.hashCode() + " dataObject= " + dataObject);
        mUsingViewsMap.put(dataObject, new ViewBundle(itemView, itemViewType));

        return dataObject;
    }

    protected int getItemViewType(int position) {
        return TYPE_DEFAULT;
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        Logging.d(TAG, "destroyItem()| position= " + position);

//        Logging.d(TAG, "destroyItem()| object= " + object);
        ViewBundle itemViewForDestroy = mUsingViewsMap.get(object);

        if (null != itemViewForDestroy) {
            //销毁item view
            destroyItemView(itemViewForDestroy.view);
            //移除item view, 并挪到待处理列表内
            container.removeView(itemViewForDestroy.view);
//            Logging.d(TAG, "destroyItem()| remove item for " + position + " object= " + object);
            mUsingViewsMap.remove(object);

            ensureListNotEmpty(itemViewForDestroy.viewType);
            List<T> recyclableViewList = mRecycledViews.get(itemViewForDestroy.viewType);
            recyclableViewList.add(itemViewForDestroy.view);
        } else {
            Logging.d(TAG, "destroyItem()| but item view is null for position: " + position);
        }
    }

    @Override
    public final void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        onItemPositionChange(mLastItemPosition, position);
        mLastItemPosition = position;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
//        Logging.d(TAG, "finishUpdate()");
        super.finishUpdate(container);
    }

    @Override
    public Parcelable saveState() {
//        Logging.d(TAG, "saveState()");
        return super.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
//        Logging.d(TAG, "restoreState()");
        super.restoreState(state, loader);
    }

    @Override
    public int getItemPosition(Object object) {
//        Logging.d(TAG, "getItemPosition() object= " + object);
        return super.getItemPosition(object);
    }

    protected void onItemPositionChange(int oldPosition, int newPosition) {
        //hook
    }

    protected T getCreatedView(int position) {
        Object dataObject = getItemObject(position);
        ViewBundle viewBundle = mUsingViewsMap.get(dataObject);
        if (null == viewBundle) {
            return null;
        }
        return viewBundle.view;
    }

    protected List<T> getUsingViews() {
        List<T> resultList = new ArrayList<T>();
        Set<Map.Entry<Object, ViewBundle>> entrySet = mUsingViewsMap.entrySet();
        Iterator<Map.Entry<Object, ViewBundle>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, ViewBundle> entry = iterator.next();
            ViewBundle viewBundle = entry.getValue();
            resultList.add(viewBundle.view);
        }
        return resultList;
    }

    protected abstract Object getItemObject(int position);

    protected abstract T createItemView(int itemViewType);

    protected abstract void onBindView(T itemView, int position, int itemViewType);

    protected abstract void destroyItemView(T view);


    private void ensureListNotEmpty(int viewType) {
        List<T> viewList = mRecycledViews.get(viewType);
        if (null == viewList) {
            mRecycledViews.put(viewType, new ArrayList<T>());
        }
    }

    private class ViewBundle {
        T view;
        int viewType;

        public ViewBundle(T view, int viewType) {
            this.view = view;
            this.viewType = viewType;
        }
    }
}
