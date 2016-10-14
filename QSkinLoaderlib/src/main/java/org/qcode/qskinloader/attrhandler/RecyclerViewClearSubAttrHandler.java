package org.qcode.qskinloader.attrhandler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.qcode.qskinloader.IResourceManager;
import org.qcode.qskinloader.ISkinAttrHandler;
import org.qcode.qskinloader.base.utils.Logging;
import org.qcode.qskinloader.entity.SkinAttr;
import org.qcode.qskinloader.entity.SkinAttrName;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 清除RecyclerView的缓存View池，保证不出现夜间模式与白天模式共存的问题
 * qqliu
 * 2016/9/27.
 */
class RecyclerViewClearSubAttrHandler implements ISkinAttrHandler {
    private static final String TAG = "RecyclerViewClearSubAttr";

    @Override
    public void apply(View view, SkinAttr skinAttr, IResourceManager resourceManager) {
        if(null == view
                || null == skinAttr
                || !(SkinAttrName.CLEAR_RECYCLER_VIEW.equals(skinAttr.mAttrName))) {
            return;
        }

        if(!(view instanceof RecyclerView)) {
            return;
        }

        refreshRecyclerView((RecyclerView) view);
    }

    private void refreshRecyclerView(RecyclerView recyclerView) {
        Logging.d(TAG, "refreshRecyclerView()| clear recycler view");
        Class<RecyclerView> recyclerViewClass = RecyclerView.class;
        try {
            Field declaredField = recyclerViewClass.getDeclaredField("mRecycler" );
            declaredField.setAccessible(true);
            Method declaredMethod = Class.forName(RecyclerView.Recycler. class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(recyclerView), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = recyclerView.getRecycledViewPool();
            recycledViewPool.clear();

        } catch (Exception ex) {
            Logging.d(TAG, "refreshRecyclerView()| error happened", ex);
        }
    }
}
