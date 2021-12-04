package com.github.xwanlion.lifeauctioneer.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;

import java.util.List;

import cn.hutool.json.JSONUtil;

public class LotListFragment<onRequestPermissionsResult> extends Fragment {

    private Auctions auctions = null;

    private int bidderNum = 0;

    private RecyclerView recyclerView;

    private ImageButton nextStepButton;

    private LotListAdapter auctionLotListAdapter;

    private LotListViewModel auctionLotListViewModel;

    private MutableLiveData<List<RecyclerViewBaseMultiData>> listData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lot_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String jsonAuctions = bundle.getString(System.KEY_AUCTION);
        auctions = JsonUtils.parseJson(jsonAuctions, Auctions.class);
        bidderNum = bundle.getInt(System.KEY_BIDDER_COUNT);

        recyclerView =  view.findViewById(R.id.rcyv_auction_lot_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        auctionLotListAdapter = new LotListAdapter(LotListFragment.this);
        recyclerView.setAdapter(auctionLotListAdapter);
        this.auctionLotListViewModel = ViewModelProviders.of(this).get(LotListViewModel.class);
        listData = (MutableLiveData<List<RecyclerViewBaseMultiData>>) auctionLotListViewModel.listData(auctions.getId());
        listData.observe(getViewLifecycleOwner(), new Observer<List<RecyclerViewBaseMultiData>>() {
            @Override
            public void onChanged(List<RecyclerViewBaseMultiData> list) {
                auctionLotListAdapter.setDataList(list);
                auctionLotListAdapter.notifyDataSetChanged();
                if (list == null || list.size() <= 1) {
                    nextStepButton.setEnabled(false);
                    nextStepButton.setBackgroundResource(android.R.drawable.btn_default);
                } else {
                    nextStepButton.setEnabled(true);
                    nextStepButton.setBackgroundColor(getResources().getColor(R.color.list_item_background_color));
                }
            }
        });

        nextStepButton = view.findViewById(R.id.btn_lot_list_next_step);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = R.id.action_auctionLotListFragment_to_reviewFragment;
                // why minus 1, because contain new button
                bundle.putInt(System.KEY_LOT_COUNT, auctionLotListAdapter.getItemCount() - 1);
                NavHostFragment.findNavController(LotListFragment.this).navigate(id, bundle);
            }
        });

        view.findViewById(R.id.lyo_auction_lot_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auctionLotListAdapter.invisibleMenuPanel();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.txt_Lot_list_fragment_title);
    }

}
