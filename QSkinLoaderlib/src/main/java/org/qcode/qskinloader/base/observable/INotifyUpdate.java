package org.qcode.qskinloader.base.observable;

/**
 * 通知观察者更新
 * qqliu
 * 2016/9/19.
 */
public interface INotifyUpdate<T> {

    /***
     * 通知观察者发生了标识为identifier的事件，事件参数是params
     * @param callback 观察者
     * @param identifier 事件标识
     * @param params 事件参数
     * @return 返回true截断事件传播，false继续事件传播
     */
    boolean notifyEvent(T callback, String identifier, Object... params);
}
