package org.qcode.qskinloader.base.utils;

import android.util.Log;

public class Logging {

	protected static boolean mLoggingEnabled = true;

	private static final String PRE_TAG = "SkinLoader_";

	public static void setDebugLogging(boolean enabled) {
		mLoggingEnabled = enabled;
	}
	
	public static boolean isDebugLogging() {
	    return mLoggingEnabled;
	}

	public static int v(String tag, String msg) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.v(PRE_TAG + tag, msg);
		}
		return result;
	}

	public static int v(String tag, String msg, Throwable tr) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.v(PRE_TAG + tag, msg, tr);
		}
		return result;
	}

	public static int d(String tag, String msg) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.d(PRE_TAG + tag, msg);
		}
		return result;
	}

	public static int d(String tag, String msg, Throwable tr) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.d(PRE_TAG + tag, msg, tr);
		}
		return result;
	}

	public static int i(String tag, String msg) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.i(PRE_TAG + tag, msg);
		}
		return result;
	}

	public static int i(String tag, String msg, Throwable tr) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.i(PRE_TAG + tag, msg, tr);
		}
		return result;
	}

	public static int w(String tag, String msg) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.w(PRE_TAG + tag, msg);
		}
		return result;
	}

	public static int w(String tag, String msg, Throwable tr) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.w(PRE_TAG + tag, msg, tr);
		}
		return result;
	}

	public static int w(String tag, Throwable tr) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.w(PRE_TAG + tag, tr);
		}
		return result;
	}

	public static int e(String tag, String msg) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.e(PRE_TAG + tag, msg);
		}
		return result;
	}

	public static int e(String tag, String msg, Throwable tr) {
		int result = 0;
		if (mLoggingEnabled) {
			result = Log.e(PRE_TAG + tag, msg, tr);
		}
		return result;
	}
	
}
