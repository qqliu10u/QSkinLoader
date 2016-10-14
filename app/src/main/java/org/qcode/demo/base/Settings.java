package org.qcode.demo.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import org.qcode.qskinloader.base.utils.Logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Settings {

    private static final String TAG = "SettingsImpl";

    private static final String NAME = "SKinSettings";

    private SharedPreferences mSharedPref;

    private Settings(Context context) {
        int mode = Context.MODE_PRIVATE;
        mSharedPref = context.getSharedPreferences(NAME, mode);
    }

    private static volatile Settings mInstance;

    public static Settings createInstance(Context context) {
        if(null == mInstance) {
            synchronized (Settings.class) {
                if(null == mInstance) {
                    mInstance = new Settings(context);
                }
            }
        }
        return mInstance;
    }

    public static Settings getInstance() {
        return mInstance;
    }

    public boolean isSetted(String key) {
        return mSharedPref.contains(key);
    }


    public void setSetting(String key, boolean value) {
        try {
            Editor editor = mSharedPref.edit();
            editor.putBoolean(key, value);
            editor.commit();
        } catch (Exception e) {
            Logging.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }


    public void setSetting(String key, int value) {
        try {
            Editor editor = mSharedPref.edit();
            editor.putInt(key, value);
            editor.commit();
        } catch (Exception e) {
            Logging.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }


    public void setSetting(String key, float value) {
        try {
            Editor editor = mSharedPref.edit();
            editor.putFloat(key, value);
            editor.commit();
        } catch (Exception e) {
            Logging.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }


    public void setSetting(String key, long value) {
        try {
            Editor editor = mSharedPref.edit();
            editor.putLong(key, value);
            editor.commit();
        } catch (Exception e) {
            Logging.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }


    public void setSetting(String key, String value) {
        if (null != value) {
            //要过滤'\0',否则会使XML读取异常
            value = value.replace("\0", "");
        }

        try {
            Editor editor = mSharedPref.edit();
            editor.putString(key, value);
            editor.commit();
        } catch (Exception e) {
            Logging.e(TAG, "setSetting(" + key + ", " + value + ")", e);
        }
    }


    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }


    public boolean getBoolean(String key, boolean defaultValue) {
        boolean result = defaultValue;
        try {
            result = mSharedPref.getBoolean(key, result);
        } catch (Exception e) {
            Logging.e(TAG, "getBoolean()", e);
        }
        return result;
    }


    public int getInt(String key) {
        return getInt(key, 0);
    }


    public int getInt(String key, int defaultValue) {
        int value = defaultValue;
        try {
            value = mSharedPref.getInt(key, defaultValue);
        } catch (Exception e) {
            Logging.e(TAG, "getSetting()", e);
        }
        return value;
    }


    public float getFloat(String key) {
        return getFloat(key, 0);
    }


    public float getFloat(String key, float defaultValue) {
        float value = defaultValue;
        try {
            value = mSharedPref.getFloat(key, defaultValue);
        } catch (Exception e) {
            Logging.e(TAG, "getLongSetting()", e);
        }
        return value;
    }


    public long getLong(String key) {
        return getLong(key, 0);
    }


    public long getLong(String key, long defaultValue) {
        long value = defaultValue;
        try {
            value = mSharedPref.getLong(key, defaultValue);
        } catch (Exception e) {
            Logging.e(TAG, "getLongSetting()", e);
        }
        return value;
    }


    public String getString(String key) {
        return getString(key, null);
    }


    public String getString(String key, String defaultValue) {
        String value = defaultValue;
        try {
            value = mSharedPref.getString(key, defaultValue);
        } catch (Exception e) {
            Logging.e(TAG, "getString()", e);
        }
        return value;
    }


    public void saveObject(String fileName, Object object) {
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (Exception e) {
            Logging.e(TAG, "saveObject()", e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    Logging.e(TAG, "saveObject()", e);
                }
            }
        }
    }


    public Object readObject(String fileName) {

        Object object = null;
        ObjectInputStream objectInputStream = null;
        try {

            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            object = objectInputStream.readObject();
        } catch (Exception e) {

            Logging.e(TAG, "readObject()" + e);
        } finally {
            if (objectInputStream != null) {

                try {
                    objectInputStream.close();
                } catch (IOException e) {

                    Logging.e(TAG, "readObject()" + e);
                }
            }
        }
        return object;
    }


    public void clearObject(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
                Logging.d(TAG, "delete file success");
            }
        } catch (Exception e) {
            Logging.e(TAG, " clearObject()", e);
        }
    }


    public void removeSetting(String key) {
        try {
            //如果key不为空，把key删掉
            if (!TextUtils.isEmpty(key)) {
                Editor editor = mSharedPref.edit();
                editor.remove(key);
                editor.commit();
            }
        } catch (Exception e) {
            Logging.e(TAG, "removeSetting(" + key + ")", e);
        }
    }
}
