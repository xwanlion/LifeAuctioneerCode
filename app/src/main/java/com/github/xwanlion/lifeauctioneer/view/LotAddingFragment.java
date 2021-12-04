package com.github.xwanlion.lifeauctioneer.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;
import com.github.xwanlion.lifeauctioneer.repository.OnSaveListener;
import com.github.xwanlion.lifeauctioneer.util.AndroidPermissionHelper;
import com.github.xwanlion.lifeauctioneer.util.FilePathParser;
import com.github.xwanlion.lifeauctioneer.util.FileSelector;
import com.github.xwanlion.lifeauctioneer.util.FileUtils;
import com.github.xwanlion.lifeauctioneer.util.FilesSelector;
import com.github.xwanlion.lifeauctioneer.util.ImageUtils;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import cn.hutool.core.io.FileUtil;

public class LotAddingFragment<onRequestPermissionsResult> extends Fragment implements OnSaveListener<Lot> {

    private Auctions auctions = null;
    private View view = null;
    private TextView textBoxName = null;
    private TextView textBoxStartPrice = null;
    private TextView textBoxIncrement = null;
    private TextView textBoxDesc = null;
    private View submitButton = null;
    private ImageView picture = null;
    private String selectedImageFileName;
    private Bitmap bitmap;

    ActivityResultLauncher<Intent> activityResultLauncher;
//    private int activityId = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() != Activity.RESULT_OK) {
                        String toastText = getString(R.string.select_file_fail);
                        Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    selectedImageFileName = FilePathParser.uriToFilePath(getContext(), result.getData().getData());
                    Logger.d("selectedImageFileName:" + selectedImageFileName);
                    if (selectedImageFileName == null) {
                        String toastText = getString(R.string.select_file_fail);
                        Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AndroidPermissionHelper.requireExternalStoragePermissionIfNeeded(getActivity());
                    bitmap = ImageUtils.fixRotateImage(selectedImageFileName);
                    picture.setImageBitmap(bitmap);
                    picture.setDrawingCacheEnabled(true);
                }
            }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lot_adding, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        Bundle bundle = getArguments();
        String jsonAuctions = bundle.getString(System.KEY_AUCTION);
        auctions = JsonUtils.parseJson(jsonAuctions, Auctions.class);

        textBoxName = view.findViewById(R.id.txt_new_auction_item_name);
        textBoxStartPrice = view.findViewById(R.id.txt_new_auction_item_start_price);
        textBoxIncrement = view.findViewById(R.id.txt_new_auction_item_increment);
        textBoxDesc = view.findViewById(R.id.txt_new_auction_item_desc);
        submitButton = view.findViewById(R.id.btn_submit_lot_item);
        picture = view.findViewById(R.id.img_new_auction_item);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitButton.setEnabled(false);

                    CharSequence name = textBoxName.getText();
                    CharSequence startPrice = textBoxStartPrice.getText();
                    CharSequence increment = textBoxIncrement.getText();
                    CharSequence desc = textBoxDesc.getText();

                    if (selectedImageFileName == null || selectedImageFileName.length() == 0) {
                        String tips = getText(R.string.hint_new_auction_picture).toString();
                        Toast.makeText(v.getContext(), tips, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (name == null || name.length() == 0) {
                        String tips = getText(R.string.hint_new_auction_item_name).toString();
                        Toast.makeText(v.getContext(), tips, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (startPrice == null || startPrice.length() == 0) {
                        String tips = getText(R.string.hint_new_auction_item_start_price).toString();
                        Toast.makeText(v.getContext(), tips, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (increment == null || increment.length() == 0) {
                        String tips = getText(R.string.hint_new_auction_item_increment).toString();
                        Toast.makeText(v.getContext(), tips, Toast.LENGTH_LONG).show();
                        return;
                    }

                    //Logger.i("save auctions id:" + auctions.getId());

                    int activityId = auctions.getId();
                    if (activityId <= 0) return;

                    String descFile = FileUtils.generateFileName();
                    // FIXME: fix rotate Image
                    // FileUtil.copy(selectedImageFileName, descFile, true);
                    ImageUtils.saveImage(bitmap, descFile);

                    Lot lot = new Lot();
                    lot.setDesc(desc.toString());
                    lot.setPurchasePrice(0);
                    lot.setIncrement(Long.valueOf(increment.toString()));
                    lot.setStartPrice(Long.valueOf(startPrice.toString()));
                    lot.setName(name.toString());
                    lot.setActivityId(activityId);
                    lot.setBuyerId(0);
                    lot.setBuyer("");
                    lot.setImageFile(descFile);
                    BeanManager.lotRepository.saveLot(lot, LotAddingFragment.this);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                } finally {
//                    submitButton.setBackgroundColor(getResources().getColor(R.color.list_item_background_color));
                    submitButton.setEnabled(true);

                }
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            // There are no request codes
//                            Intent data = result.getData();
//                            //doSomeOperations();
//                        }
//                    }
//                });

                // callback on onActivityResult method
                // FileSelector.toSelectImage(LotAddingFragment.this, System.SELECT_LOT_PICTURE_RESULT_CODE);

                FilesSelector.toSelectImage(activityResultLauncher);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.txt_add_lot_fragment_title);
    }

    @Override
    public void onSaved(Lot lot, long id) {
        Toast.makeText(view.getContext(), "Lot Has Save Successful", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                submitButton.setEnabled(true);

                Bundle bundle = new Bundle();
                bundle.putString(System.KEY_AUCTION, auctions.toJson());

                int navId = R.id.action_auctionLotAddingFragment_to_auctionLotListFragment;
                NavHostFragment.findNavController(LotAddingFragment.this).navigate(navId, bundle);
            }
        },2000);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String json = JsonUtils.toJsonString(data);

        if (resultCode != Activity.RESULT_OK || requestCode != System.SELECT_LOT_PICTURE_RESULT_CODE) {
            String toastText = getString(R.string.select_file_fail);
            Toast.makeText(this.getContext(), toastText, Toast.LENGTH_SHORT).show();
            return;
        }

        this.selectedImageFileName = FilePathParser.uriToFilePath(this.getContext(), data.getData());
        if (this.selectedImageFileName == null) {
            String toastText = getString(R.string.select_file_fail);
            Toast.makeText(this.getContext(), toastText, Toast.LENGTH_SHORT).show();
            return;
        }

        AndroidPermissionHelper.requireExternalStoragePermissionIfNeeded(this.getActivity());
        bitmap = ImageUtils.fixRotateImage(this.selectedImageFileName);
        picture.setImageBitmap(bitmap);
        picture.setDrawingCacheEnabled(true);

    }

}
