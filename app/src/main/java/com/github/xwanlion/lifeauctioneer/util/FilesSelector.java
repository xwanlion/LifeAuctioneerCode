package com.github.xwanlion.lifeauctioneer.util;

import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.github.xwanlion.lifeauctioneer.R;

public class FilesSelector {
    public final static int SELECT_EXTRACTING_FILE_CODE = 0;
    public final static int SELECT_SHOWING_FILE_CODE = 1;
    public final static int SELECT_HIDDEN_FILE_CODE = 2;

    public static void toSelectMedia(ActivityResultLauncher<Intent> launcher) {
        String[] mimeTypes = {"video/*", "image/*", "audio/*"};
        FilesSelector.toSelectFile(launcher, mimeTypes);
    }

    public static void toSelectVideo(ActivityResultLauncher<Intent> launcher) {
        String[] mimeTypes = {"video/*"};
        FilesSelector.toSelectFile(launcher, mimeTypes);
    }

    public static void toSelectAudio(ActivityResultLauncher<Intent> launcher) {
        String[] mimeTypes = {"audio/*"};
        FilesSelector.toSelectFile(launcher, mimeTypes);
    }

    public static void toSelectImage(ActivityResultLauncher<Intent> launcher) {
        String[] mimeTypes = {"image/*"};
        FilesSelector.toSelectFile(launcher, mimeTypes);
    }

    public static void toSelectFile(ActivityResultLauncher<Intent> launcher, String[] mimeTypes) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        launcher.launch(intent);
    }

}

