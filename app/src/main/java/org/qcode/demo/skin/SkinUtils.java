package org.qcode.demo.skin;

import android.content.Context;

import org.qcode.demo.utils.FileUtils;
import org.qcode.qskinloader.base.utils.Logging;

import java.io.File;

/**
 * qqliu
 * 2016/9/21.
 */
public class SkinUtils {
    private static final String TAG = "NightModeUtils";

    private static final String SKIN_NAME = "nightMode.skin";

    public static String getTotalSkinPath(Context context) {
        String SKIN_PATH = context.getCacheDir().getAbsolutePath();
        String totalPath = SKIN_PATH + File.separator + SKIN_NAME;
        return totalPath;
    }

    public static boolean copyAssetSkin(Context context) {
        String totalPath = getTotalSkinPath(context);
        File skin = new File(totalPath);

        if (skin == null || !skin.exists() || needUpdateSkin()) {
            long currTime = System.currentTimeMillis();

            boolean isSuccess = FileUtils.copyAssetFile(context, SKIN_NAME,
                    context.getCacheDir().getAbsolutePath(),
                    SKIN_NAME);

            long diff = System.currentTimeMillis() - currTime;
            Logging.d(TAG, "copyAssetSkin()| copy file time: " + diff);

            return isSuccess;
        }

        return false;
    }

    private static boolean needUpdateSkin() {
        //每次都拷贝皮肤包
        return true;
    }
}
