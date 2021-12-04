package com.github.xwanlion.lifeauctioneer.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.repository.AuctioneerRepository;
import com.github.xwanlion.lifeauctioneer.repository.OnQueryObjectListener;
import com.github.xwanlion.lifeauctioneer.repository.OnSaveAuctioneerListener;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;

public class AuctioneerSettingFragment<onRequestPermissionsResult> extends Fragment implements OnSaveAuctioneerListener<Auctioneer>, OnQueryObjectListener<Auctioneer> {

    private Auctions auctions;
    private EditText textAuctioneer;
    private EditText textPassword;
//    private ImageButton nextStepButton;
    private AppCompatImageView nextStepButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auctioneer_setting, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String json = bundle.getString(System.KEY_AUCTION);
        auctions = JsonUtils.parseJson(json, Auctions.class);

        textAuctioneer = view.findViewById(R.id.txt_auction_host_man);
        textPassword = view.findViewById(R.id.txt_auction_host_password);
        nextStepButton = view.findViewById(R.id.btn_host_man_setting_next_step);

        textAuctioneer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                enabledNextStepButton();
            }
        });

        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hostMan = textAuctioneer.getText().toString();
                String password = textPassword.getText().toString();

                if (hostMan == null || hostMan.length() == 0) {
                    Toast.makeText(getContext(), R.string.msg_need_host_man, Toast.LENGTH_LONG).show();
                    enabledNextStepButton();
                    return;
                }

                // save auctioneer and get callback at onSaved method.
                // navigate to next fragment action is also at onSaved method too.
                BeanManager.auctioneerRepository.saveAuctioneer(auctions.getAuctioneerId(), hostMan, password, AuctioneerSettingFragment.this);

                //BeanManager.auctioneerRepository.saveAuctioneer(auctions.getId(), auctions.getAuctioneerId(), hostMan, password ,AuctioneerSettingFragment.this);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        this.queryAuctioneerIfPossible(auctions);
    }

    private void queryAuctioneerIfPossible(Auctions auctions) {
        if (auctions.getId() == 0 && auctions.getAuctioneerId() == 0) return;
        // query auctioneer and fill data to EditText on OnQueried(Auctioneer) method.
        BeanManager.auctioneerRepository.getAuctioneer(auctions.getAuctioneerId(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.txt_host_man_setting_fragment_title);
        enabledNextStepButton();
    }

    private void enabledNextStepButton() {
        String text = textAuctioneer.getText().toString();
        if (text == null || text.length() == 0) {
            nextStepButton.setEnabled(false);
            nextStepButton.setBackgroundColor(android.R.drawable.btn_default);
        } else {
            nextStepButton.setEnabled(true);
            nextStepButton.setBackgroundColor(getResources().getColor(R.color.list_item_background_color));
        }
    }


    public void onSaved(Auctioneer auctioneer, long id) {
        Bundle bundle = new Bundle();
        auctioneer.setId((int) id);
        bundle.putString(System.KEY_AUCTIONEER, auctioneer.toJson());
        bundle.putString(System.KEY_AUCTION, auctions.toJson());

        int fragmentId = R.id.action_hostManSettingFragment_to_moneySettingFragment;
        NavHostFragment.findNavController(AuctioneerSettingFragment.this).navigate(fragmentId, bundle);
    }

    @Override
    public void onSaved(Auctioneer auctioneer, Auctions auctions) {
        Bundle bundle = new Bundle();
        bundle.putString(System.KEY_AUCTIONEER, auctioneer.toJson());
        bundle.putString(System.KEY_AUCTION, auctions.toJson());

        int fragmentId = R.id.action_hostManSettingFragment_to_moneySettingFragment;
        NavHostFragment.findNavController(AuctioneerSettingFragment.this).navigate(fragmentId, bundle);
    }

    @Override
    public void onQueried(Auctioneer obj) {
        if (obj == null) return;
        textAuctioneer.setText(obj.getUsername());
        textPassword.setText(obj.getPassword());
    }

}
