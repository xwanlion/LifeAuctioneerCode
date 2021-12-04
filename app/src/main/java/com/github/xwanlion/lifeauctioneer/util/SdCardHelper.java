package com.github.xwanlion.lifeauctioneer.util;

import android.os.Environment;

public class SdCardHelper {
    public static String getSdCardPath() {
        java.io.File sdPath = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdPath = Environment.getExternalStorageDirectory();
            return sdPath.toString();
        } else {
            return null;
        }
    }

    public static boolean createPath(String fileName) {
        try {
            java.io.File path = null;
            java.io.File file = new java.io.File(fileName);
            if (fileName.indexOf(".") > 0) {
                path = new java.io.File(file.getParentFile().getAbsolutePath());
            } else {
                path = file;
            }

            if (path.getParentFile().exists()) {
                path.mkdir();
                return true;
            } else {
                SdCardHelper.createPath(path.getParentFile().getAbsolutePath());
                file.mkdir();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

        return true;

    }


}
