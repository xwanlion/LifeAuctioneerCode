package com.github.xwanlion.lifeauctioneer.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.view.MainActivity;

public class FileSelector {
    public final static int SELECT_EXTRACTING_FILE_CODE = 0;
    public final static int SELECT_SHOWING_FILE_CODE = 1;
    public final static int SELECT_HIDDEN_FILE_CODE = 2;

    public static void toSelectMedia(Fragment fragment, int selectCode) {
        String[] mimeTypes = {"video/*", "image/*", "audio/*"};
        FileSelector.toSelectFile(fragment, mimeTypes, selectCode);
    }

    public static void toSelectVideo(Fragment fragment, int selectCode) {
        String[] mimeTypes = {"video/*"};
        FileSelector.toSelectFile(fragment, mimeTypes, selectCode);
    }

    public static void toSelectAudio(Fragment fragment, int selectCode) {
        String[] mimeTypes = {"audio/*"};
        FileSelector.toSelectFile(fragment, mimeTypes, selectCode);
    }

    public static void toSelectImage(Fragment fragment, int selectCode) {
        String[] mimeTypes = {"image/*"};
        FileSelector.toSelectFile(fragment, mimeTypes, selectCode);
    }

    public static void toSelectFile(Fragment fragment, String[] mimeTypes, int selectCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        try {
            String title = fragment.getString(R.string.file_selector_title);
            fragment.startActivityForResult(Intent.createChooser(intent, title), selectCode);
        } catch (android.content.ActivityNotFoundException ex) {
            String toastText = fragment.getString(R.string.file_selector_open_fail);
            Toast.makeText(fragment.getContext(), toastText, Toast.LENGTH_SHORT).show();
        }

    }
}

