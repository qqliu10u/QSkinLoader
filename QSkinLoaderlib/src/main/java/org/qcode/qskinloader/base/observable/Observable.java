package org.qcode.qskinloader.base.observable;

import java.util.ArrayList;

/**
 * 观察者通用逻辑
 * qqliu
 * 2016/9/19.
 */
public class Observable<T> implements IObservable<T> {

    private ArrayList<T> mObservers;

    @Override
    public void addObserver(T observer) {
        if (mObservers == null) {
            mObservers = new ArrayList<T>();
        }

        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    @Override
    public void removeObserver(T observer) {
        if (mObservers == null) {
            return;
        }

        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    @Override
    public void notifyUpdate(INotifyUpdate<T> listener, String identifier, Object... params) {
        if (mObservers == null || null == listener) {
            return;
        }

        ArrayList<T> tmpListeners
                = (ArrayList<T>) mObservers.clone();
        for (T observer : tmpListeners) {
            listener.notifyEvent(observer, identifier, params);
        }
    }
}
