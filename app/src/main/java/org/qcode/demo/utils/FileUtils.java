package org.qcode.demo.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * qqliu
 * 2016/9/12.
 */
public class FileUtils {
    public static String getDirectory(Context context) {
        return context.getExternalCacheDir() + File.separator + "NightMode";
    }

    public static String getFileName() {
        return "resultFile.txt";
    }

    public static OutputStream openFileStream(Context context) {
        return openFileStream(getDirectory(context), getFileName());
    }

    public static OutputStream openFileStream(String directory, String filePath) {
        if (TextUtils.isEmpty(directory) || TextUtils.isEmpty(filePath)) {
            return null;
        }

        File directoryFile = new File(directory);
        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }

        File file = new File(directory + File.separator + filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            return new FileOutputStream(file, true);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void writeData(OutputStream stream, String str) {
        if (null == stream || TextUtils.isEmpty(str)) {
            return;
        }

        str = str + "\r\n";

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(stream);
            bufferedOutputStream.write(str.getBytes());
            bufferedOutputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    public static void closeStream(OutputStream stream) {
        if (null != stream) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean copyAssetFile(Context context, String originFileName,
                                     String destFilePath, String destFileName) {
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = context.getAssets().open(originFileName);
            File destPathFile = new File(destFilePath);
            if(!destPathFile.exists()) {
                destPathFile.mkdirs();
            }

            File destFile = new File(destFilePath + File.separator + destFileName);
            if(!destFile.exists()) {
                destFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(destFile);
            bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[256];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.flush();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}
