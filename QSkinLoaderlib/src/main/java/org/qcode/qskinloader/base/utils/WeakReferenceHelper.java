package com.iflytek.skin.manager.base.utils;

import java.lang.ref.WeakReference;

/**
 * qqliu
 * 2016/10/19.
 */

public class WeakReferenceHelper<T> {

    private WeakReference<T> mRef;

    public WeakReferenceHelper(T t) {
        setData(t);
    }

    public T getData() {
        if(null == mRef) {
            return null;
        }

        return mRef.get();
    }

    public void setData(T t) {
        this.mRef = new WeakReference<T>(t);
    }

    @Override
    public String toString() {
        return "WeakReferenceHelper{" +
                "mData= " + (null == mRef ? "NULL" : mRef.get()) +
                '}';
    }
}
