package org.qcode.qskinloader.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.qcode.qskinloader.ISkinAttributeParser;
import org.qcode.qskinloader.IViewCreateListener;
import org.qcode.qskinloader.base.utils.Logging;
import org.qcode.qskinloader.base.utils.ReflectUtils;

/***
 * 代理View的创建，解析与换肤相关的属性
 */
class SkinInflaterFactoryImpl implements LayoutInflater.Factory {

    private static final String TAG = "SkinInflaterFactoryImpl";
    private IViewCreateListener mViewCreateListener;

    private ISkinAttributeParser mSkinAttributeParser;

    public SkinInflaterFactoryImpl(ISkinAttributeParser parser) {
        mSkinAttributeParser = parser;
    }

    public void setViewCreateListener(IViewCreateListener viewCreateListener) {
        mViewCreateListener = viewCreateListener;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;

        //给框架外创建View的机会
        if(null != mViewCreateListener) {
            view = mViewCreateListener.beforeCreate(name, context, attrs);
        }

        //判断是否支持换肤
        if(!mSkinAttributeParser.isSupportSkin(name, context, attrs)) {
            return null;
        }

        if(null == view) {
            //代理创建View
            view = createView(context, name, attrs);
        }

        if (view == null) {
            return null;
        }

        //解析属性
        mSkinAttributeParser.parseAttribute(view, name, context, attrs);

        //给框架外解析属性的机会
        if(null != mViewCreateListener) {
            mViewCreateListener.afterCreated(view, name, context, attrs);
        }

        return view;
    }

    /***
     * 根据名称创建view
     *
     * @param context
     * @param name
     * @param attrs
     * @return
     */
    private View createView(Context context,
                            String name,
                            AttributeSet attrs) {
        View view = null;
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            setupInflater(inflater, context);

            if (-1 == name.indexOf('.')) {
                if ("View".equals(name)
                        || "ViewStub".equals(name)
                        || "ViewGroup".equals(name)) {
                    view = inflater.createView(
                            name, "android.view.", attrs);
                }
                if (view == null) {
                    view = inflater.createView(
                            name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = inflater.createView(
                            name, "android.webkit.", attrs);
                }
            } else {
                view = inflater.createView(name, null, attrs);
            }

        } catch (Exception ex) {
            Logging.d(TAG, "createView()| create view failed", ex);
            view = null;
        }

        return view;
    }

    private void setupInflater(LayoutInflater inflater, Context context) {
        //异常，处理context为空，一般不会发生
        Context inflaterContext = inflater.getContext();
        if (null == inflaterContext) {
            ReflectUtils.setFieldValueOpt(inflater, "mContext", context);
        }

        //设置mConstructorArgs的第一个参数context
        Object[] constructorArgs = ReflectUtils.getFieldValueOpt(inflater, "mConstructorArgs");
        if (null == constructorArgs || constructorArgs.length < 2) {
            //异常，一般不会发生
            constructorArgs = new Object[2];
            ReflectUtils.setFieldValueOpt(inflater, "mConstructorArgs", constructorArgs);
        }

        //如果mConstructorArgs的第一个参数为空，则设置为mContext
        if (null == constructorArgs[0]) {
            constructorArgs[0] = inflater.getContext();
        }
    }
}
