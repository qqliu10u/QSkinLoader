package org.qcode.qskinloader.base.observable;

/**
 * 可观察对象的抽象接口，T为观察者
 * qqliu
 * 2016/9/19.
 */
public interface IObservable<T> {
    /***
     * 增加新的观察者
     *
     * @param observer
     */
    void addObserver(T observer);

    /***
     * 删除观察者
     *
     * @param observer
     */
    void removeObserver(T observer);

    /***
     * 告知观察者发生了变化
     * @param callback
     */
    void notifyUpdate(INotifyUpdate<T> callback, String identifier, Object... params);
}
