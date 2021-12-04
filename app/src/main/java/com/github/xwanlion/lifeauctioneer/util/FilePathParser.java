package com.github.xwanlion.lifeauctioneer.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class FilePathParser {
    public static String uriToFilePath(Context context, Uri uri) {
        String path = null;
        if (uri == null) return null;

        // start with file://
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            return uri.getPath();
        }
        // start with content:// , such as  content://xxx/extenral/xxx/xxx/17765
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }

        // after version 4.4, start with content://, for example: content://com.xxx.xxx.xxx.documents/xxx/image%3A235600
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!DocumentsContract.isDocumentUri(context, uri))  return  null;

            if (isExternalFile(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadFile(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:"))  return id.replaceFirst("raw:", "");
                final Uri cUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getColumn(context, cUri, null, null);
            } else if (isMediaFile(uri)) {
                final String fileId = DocumentsContract.getDocumentId(uri);
                final String[] arrFileId = fileId.split(":");
                final String type = arrFileId[0];
                Uri cUri = null;
                if ("image".equals(type)) {
                    cUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    cUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    cUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String sel = "_id=?";
                final String[] args = new String[]{arrFileId[1]};
                return getColumn(context, cUri, sel, args);
            }
        }
        return null;
    }

    private static String getColumn(Context context, Uri uri, String selection, String[] args) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] project = {column};
        try {
            cursor = context.getContentResolver().query(uri, project, selection, args, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private static boolean isExternalFile(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadFile(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaFile(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}

