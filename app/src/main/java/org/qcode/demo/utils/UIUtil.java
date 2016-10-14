package org.qcode.demo.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * 与UI相关的帮助类
 */
public final class UIUtil {

	private static final String TAG = "UIUtil";

	private UIUtil() { }

	/**
	* 根据手机的分辨率从 dip 的单位 转成为 px(像素)
	*/
	public static int dip2px(Context context, double dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5);
	}

    /**
     * 显示Toast
     */
    public static void showToast(Context context, String toast) {
        toast(context, toast, false);
    }

	private static Toast mStaticToastImpl = null;
    public static void toast(final Context context, final String msg, final boolean isLong) {
		if (null == mStaticToastImpl) {
			//需要在主线程创建toast实例
			if (Looper.myLooper() != Looper.getMainLooper()) {
				UITaskRunner.getHandler().post(new Runnable() {
					@Override
					public void run() {
						toast(context, msg, isLong);
					}
				});
				return;
			}
		}

		if(null == mStaticToastImpl) {
			synchronized (UIUtil.class) {
				if(null == mStaticToastImpl) {
					mStaticToastImpl = Toast.makeText(
							context.getApplicationContext(), "", Toast.LENGTH_SHORT);
				}
			}
		}

		if(Looper.myLooper() == Looper.getMainLooper()) {
			mStaticToastImpl.setText(msg);
			mStaticToastImpl.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
			//当前在主线程
			mStaticToastImpl.show();
		} else {
			UITaskRunner.getHandler().post(new Runnable() {
				@Override
				public void run() {
					mStaticToastImpl.setText(msg);
					mStaticToastImpl.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
					mStaticToastImpl.show();
				}
			});
		}
    }

    public static void toast(Context context, String msg) {
        toast(context, msg, true);
    }
}
