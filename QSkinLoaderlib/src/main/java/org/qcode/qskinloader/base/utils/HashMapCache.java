package org.qcode.qskinloader.base.utils;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * 维护一个缓存Map，包括强引用和弱引用两种维护方式；
 * 两种方式中key都是强引用;
 * qqliu
 * 2016/9/19.
 */
public class HashMapCache<K, V> {
    private HashMap<K, V> mCacheMap = null;
    private HashMap<K, WeakReference<V>> mWeakCacheMap = null;

    /***
     * @param isStrongReference true 强引用，false弱引用
     */
    public HashMapCache(boolean isStrongReference) {
        if (isStrongReference) {
            mCacheMap = new HashMap<K, V>();
        } else {
            mWeakCacheMap = new HashMap<K, WeakReference<V>>();
        }
    }

    public V getCache(K key) {
        if(null == key) {
            return null;
        }

        if(null != mCacheMap) {
            return mCacheMap.get(key);
        } else {
            WeakReference<V> refValue = mWeakCacheMap.get(key);
            if(null != refValue) {
                return refValue.get();
            }
            return null;
        }
    }

    public void addCache(K key, V value) {
        if(null == key) {
            return;
        }

        if(null != mCacheMap) {
            mCacheMap.put(key, value);
        } else {
            WeakReference<V> refValue = new WeakReference<V>(value);
            mWeakCacheMap.put(key, refValue);
        }
    }
}
