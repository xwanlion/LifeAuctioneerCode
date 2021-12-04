/*
 * Copyright 2018 Zhenjie Yan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xwanlion.lifeauctioneer.util;

import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.github.xwanlion.lifeauctioneer.App;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Zhenjie Yan on 2018/6/9.
 */
public class FileUtils {

    /**
     * Create a random file based on mimeType.
     *
     * @param file file.
     *
     * @return file object.
     */
    public static File createRandomFile(MultipartFile file) {
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(file.getContentType().toString());
        if (TextUtils.isEmpty(extension)) {
            extension = MimeTypeMap.getFileExtensionFromUrl(file.getFilename());
        }
        String uuid = UUID.randomUUID().toString();
        return new File(App.getInstance().getRootDir(), uuid + "." + extension);
    }

    public static String generateFileName() {
        return generateFileName(System.SYSTEM_FOLDER);
    }

    public static String generateFileName(String folder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strDate = simpleDateFormat.format(new Date());

        StringBuffer fileName = new StringBuffer();
        fileName.append(SdCardHelper.getSdCardPath());
        fileName.append(java.io.File.separator + folder + java.io.File.separator);
        fileName.append(strDate);
        return fileName.toString();

    }

    /**
     * SD is available.
     *
     * @return true, otherwise is false.
     */
    public static boolean storageAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sd.canWrite();
        } else {
            return false;
        }
    }
}