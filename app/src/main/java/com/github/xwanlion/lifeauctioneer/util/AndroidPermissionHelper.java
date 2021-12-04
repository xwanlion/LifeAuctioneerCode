package com.github.xwanlion.lifeauctioneer.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class AndroidPermissionHelper {
    private static final int EXTERNAL_STORAGE_REQ_CODE = 1 ;

    private static final String[] permissions = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    };

    public  static boolean  permissionHasGranted (Context context, String permission) {
        int hasGranted = ContextCompat.checkSelfPermission(context, permission);
        return (hasGranted == PackageManager.PERMISSION_GRANTED);
    }

    public  static void  requirePermission (Activity context, String[] permissions) {
        ActivityCompat.requestPermissions(context, permissions, EXTERNAL_STORAGE_REQ_CODE);
    }

    public  static void requireExternalStoragePermissionIfNeeded(Activity activity) {
        if (permissionHasGranted(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) return;

        ActivityCompat.requestPermissions(activity, permissions, EXTERNAL_STORAGE_REQ_CODE);
        ActivityCompat.requestPermissions(activity, permissions, EXTERNAL_STORAGE_REQ_CODE);

    }

    public  static void requireExternalStoragePermissionIfNeeded(Fragment fragment) {
        if (permissionHasGranted(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) return;

        fragment.requestPermissions(permissions, EXTERNAL_STORAGE_REQ_CODE);
        fragment.requestPermissions(permissions, EXTERNAL_STORAGE_REQ_CODE);

    }

}
