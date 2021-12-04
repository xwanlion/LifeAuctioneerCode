package com.github.xwanlion.lifeauctioneer.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.fragment.NavHostFragment;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.model.app.BidderMoney;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;

import java.util.List;

import cn.hutool.json.JSONUtil;

public class ReviewFragment<onRequestPermissionsResult> extends Fragment {

    private int activityId = 0;
    private Auctions auctions = null;
    private int bidderNum = 0;
    private int lotNum = 0;

    private TextView textLots;
    private TextView textBidders;
    private TextView textMoney;
    private TextView textHostMan;
    private Button backHomeButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.review, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String jsonAuctions = bundle.getString(System.KEY_AUCTION);
        auctions = JsonUtils.parseJson(jsonAuctions, Auctions.class);
        bidderNum = bundle.getInt(System.KEY_BIDDER_COUNT);
        lotNum = bundle.getInt(System.KEY_LOT_COUNT);

        textLots = view.findViewById(R.id.txt_auction_lot_items);
        textBidders = view.findViewById(R.id.txt_auction_bidders);
        textMoney = view.findViewById(R.id.txt_auction_money);
        textHostMan = view.findViewById(R.id.txt_auction_host);
        backHomeButton = view.findViewById(R.id.btn_back_home);

        // why minus 1, because list contain new button
        textLots.setText(String.valueOf(lotNum));
        textBidders.setText(String.valueOf(bidderNum));
        textHostMan.setText(auctions.getAuctioneerName());
        if (auctions.getMoneyCreationWay() == MoneyCreationWay.FIX_AMOUNT) {
            String patton = getString(R.string.txt_activity_list_item_money);
            textMoney.setText(String.format(patton, auctions.getMoney()));
        } else {
            String patton = getString(R.string.txt_activity_list_item_money_per_age);
            textMoney.setText(String.format(patton, auctions.getAmountPerAge()));
        }

        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 int id = R.id.action_reviewFragment_to_activityListFragment;
                 NavHostFragment.findNavController(ReviewFragment.this).navigate(id);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.txt_review_fragment_title);
    }
}
