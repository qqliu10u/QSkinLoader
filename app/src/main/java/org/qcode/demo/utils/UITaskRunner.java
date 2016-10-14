package org.qcode.demo.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * 在主线程中执行runnable
 */
public class UITaskRunner {
    private Handler mHandler;

    private UITaskRunner() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    private static class SingletonHolder {
        static UITaskRunner sRunner = new UITaskRunner();
    }

    public static Handler getHandler() {
        return SingletonHolder.sRunner.mHandler;
    }
}
