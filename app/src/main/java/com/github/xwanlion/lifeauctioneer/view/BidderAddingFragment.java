package com.github.xwanlion.lifeauctioneer.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.repository.OnSaveListener;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;

public class BidderAddingFragment<onRequestPermissionsResult> extends Fragment implements OnSaveListener<Bidder> {

    private Auctions auctions = null;
    private View view = null;
    private TextView textBoxName = null;
    private TextView textBoxPassword = null;
    private View submitButton = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bidder_adding, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        Bundle bundle = getArguments();
        String jsonAuctions = bundle.getString(System.KEY_AUCTION);
        auctions = JsonUtils.parseJson(jsonAuctions, Auctions.class);

        textBoxName = view.findViewById(R.id.txt_new_bidder_name);
        textBoxPassword = view.findViewById(R.id.txt_new_bidder_password);
        submitButton = view.findViewById(R.id.btn_submit_bidder);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitButton.setEnabled(false);
                    CharSequence name = textBoxName.getText();
                    CharSequence password = textBoxPassword.getText();

                    if (name == null || name.length() == 0) {
                        String tips = getText(R.string.hint_new_bidder_name).toString();
                        Toast.makeText(v.getContext(), tips, Toast.LENGTH_LONG).show();
                        return;
                    }

                    ////  password can be null, let him login freely
                    // if (password == null || password.length() == 0) {
                    //     String tips = getText(R.string.hint_new_bidder_password).toString();
                    //     Toast.makeText(v.getContext(), tips, Toast.LENGTH_LONG).show();
                    //     return;
                    // }

                    int activityId = auctions.getId();
                    if (activityId <= 0) return;

                    Bidder bidder = new Bidder();
                    bidder.setAuctionsId(activityId);
                    bidder.setUsername(name.toString());
                    bidder.setPassword(password.toString());
                    BeanManager.bidderRepository.saveBidder(bidder, BidderAddingFragment.this);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    submitButton.setEnabled(true);
                }
            }
        });

//        textBoxName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
////                submitButton.setBackgroundColor(getResources().getColor(R.color.textColorPrimary));
////                submitButton.setBackground(ContextCompat.getDrawable(BidderAddingFragment.this.getContext(), R.drawable.sh_text_edit_background));
//                submitButton.setEnabled(true);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.txt_add_bidder_fragment_title);
    }

    @Override
    public void onSaved(Bidder bidder, long id) {
        Logger.i("save bidder:" + bidder.toJson());
        Toast.makeText(view.getContext(), R.string.msg_add_bidder_success, Toast.LENGTH_LONG).show();
        this.setActivityRequireLogin();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                submitButton.setEnabled(true);

                Bundle bundle = new Bundle();
                bundle.putString(System.KEY_AUCTION, auctions.toJson());

                int navId = R.id.action_bidderAddingFragment_to_bidderListFragment;
                NavHostFragment.findNavController(BidderAddingFragment.this).navigate(navId, bundle);

            }
        },2000);

    }

    /**
     * why we need to set need Loin?<br/>
     * if activity setting bidder's username, it mean that bidder need to provide his username and password when login,<br/>
     * else, anyone can use any name to login.<br/>
     *<br/>
     * where system set not to need Login?<br/>
     * see method: BidderListAdapter.onDeleted(Bidder).<br/>
     */
    private void setActivityRequireLogin() {
        auctions.setNeedLogin(true);
        BeanManager.auctionsRepository.updateAuctions(auctions, null);
    }
}
