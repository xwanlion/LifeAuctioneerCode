package com.github.xwanlion.lifeauctioneer.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class BidderListFragment<onRequestPermissionsResult> extends Fragment {

    private Auctions auctions = null;

    private RecyclerView recyclerView;

    private ImageButton nextStepButton;

    private BidderListAdapter bidderListAdapter;

    private BidderListViewModel bidderListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bidder_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String jsonAuctions = bundle.getString(System.KEY_AUCTION);
        auctions = JsonUtils.parseJson(jsonAuctions, Auctions.class);

        recyclerView =  view.findViewById(R.id.rcyv_bidder_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        bidderListAdapter = new BidderListAdapter(BidderListFragment.this);
        recyclerView.setAdapter(bidderListAdapter);
        this.bidderListViewModel = ViewModelProviders.of(this).get(BidderListViewModel.class);
        bidderListViewModel.listData(auctions.getId()).observe(getViewLifecycleOwner(), new Observer<List<RecyclerViewBaseMultiData>>() {
            @Override
            public void onChanged(List<RecyclerViewBaseMultiData> list) {
                //bidderListAdapter.setBidderList(list);
                bidderListAdapter.setDataList(list);
                bidderListAdapter.notifyDataSetChanged();
            }
        });

        nextStepButton = view.findViewById(R.id.btn_bidder_list_item_next_step);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // why minus 1, because contain new button
                bundle.putInt(System.KEY_BIDDER_COUNT, bidderListAdapter.getItemCount() - 1);
                int id = R.id.action_bidderListFragment_to_auctionLotListFragment;
                NavHostFragment.findNavController(BidderListFragment.this).navigate(id, bundle);
            }
        });

        view.findViewById(R.id.lyo_bidder_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bidderListAdapter.invisibleMenuPanel();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.txt_bidder_list_fragment_title);
    }
}
