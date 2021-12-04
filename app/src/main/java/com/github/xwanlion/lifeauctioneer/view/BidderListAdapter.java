package com.github.xwanlion.lifeauctioneer.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.repository.OnDeleteListener;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class BidderListAdapter extends RecyclerView.Adapter<RecyclerViewBaseViewHolder> implements OnDeleteListener<Bidder> {
    private View visibleMenuPanel = null;
    private BidderListFragment fragment;
    private List<RecyclerViewBaseMultiData> bidderList = new ArrayList<>();

    public BidderListAdapter(BidderListFragment fragment) {
        this.fragment = fragment;
    }

//    public void setBidderList(List<Bidder> list) {
//        this.bidderList = list;
//    }

    public void setDataList(List<RecyclerViewBaseMultiData> list) {
        this.bidderList = list;
    }

    @NonNull
    @Override
    public RecyclerViewBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (RecyclerViewListItemType.Type.getItemTypeByCode(viewType)) {
            case COMMON_ITEM:
                return new BidderListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bidder_list_item, parent, false), RecyclerViewListItemType.Type.COMMON_ITEM);

            case ADD_NEW:
                return new RecyclerViewBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_add_button_item_2, parent, false), RecyclerViewListItemType.Type.ADD_NEW);

        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewBaseViewHolder holders, final int position) {

        switch (holders.getType()) {

            case COMMON_ITEM:
                Bidder bidder = (Bidder) bidderList.get(position);
                handleBidderItem((BidderListHolder) holders, bidder);
                break;

            case ADD_NEW:
                handleNewItem(holders);
                break;

        }

    }

    private void handleNewItem(@NonNull RecyclerViewBaseViewHolder holders) {
        RecyclerViewBaseViewHolder recyclerHolder = holders;
        holders.root.findViewById(R.id.btn_recycle_view_add_item_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = fragment.getArguments();
//                String jsonAuctions = bundle.getString(System.KEY_AUCTION);
//                Auctions auctions = JsonUtils.parseJson(jsonAuctions, Auctions.class);
                int id = R.id.action_bidderListFragment_to_bidderAddingFragment;
                NavHostFragment.findNavController(fragment).navigate(id, bundle);
            }
        });
    }

    private void handleBidderItem(@NonNull BidderListHolder holders, Bidder bidder) {
        BidderListHolder holder = holders;
        final TextView bidderName = holder.bidderName;
        final View delButton = holder.delButton;
        final View commonPanel = holder.commonPanel;
        final View menuPanel = holder.menuPanel;


        bidderName.setText(bidder.getUsername());

        commonPanel.setVisibility(View.VISIBLE);
        menuPanel.setVisibility(View.INVISIBLE);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = fragment.getArguments();
                String jsonAuctions = bundle.getString(System.KEY_AUCTION);
                Auctions auctions = JsonUtils.parseJson(jsonAuctions, Auctions.class);

                BeanManager.bidderMoneyRepository.delBidderMoney(auctions.getId(), bidder.getId(), null);
                BeanManager.bidLogRepository.delBidLog(auctions.getId(), bidder.getId(), null);
                BeanManager.bidderRepository.delBidder(bidder.getId(), BidderListAdapter.this);
            }
        });

        commonPanel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (visibleMenuPanel != null) return false;
                visibleMenuPanel = menuPanel;

                menuPanel.setVisibility(View.VISIBLE);
                return true;
            }
        });

        // invisible panel2, which display delete button and copy button.
        commonPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPanel.performClick();
            }
        });

        menuPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibleMenuPanel == null) return;
                visibleMenuPanel.setVisibility(View.INVISIBLE);
                visibleMenuPanel = null;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return bidderList.get(position).typeCode();
    }

    @Override
    public int getItemCount() {
        return bidderList.size();
    }

    public void invisibleMenuPanel() {
        if (visibleMenuPanel == null) return;
        visibleMenuPanel.performClick();
    }

    @Override
    public void onDeleted(Bidder bidder) {
        this.invisibleMenuPanel();
        this.deleteBidderFromList(bidder);
        this.setActivityNotRequireLogin();
        this.notifyDataSetChanged();
    }

    /**
     * why we need this setting?
     * if activity setting bidder's username, it mean that bidder need to provide his username and password when login,<br/>
     * else, anyone can use any name to login.<br/>
     *<br/>
     * where system set to need Login?<br/>
     * see method: BidderAddingFragment.onSaved(Bidder, long).<br/>
     */
    private void setActivityNotRequireLogin() {
        if (getItemCount() > 0) return;
        Bundle bundle = fragment.getArguments();
        String jsonAuctions = bundle.getString(System.KEY_AUCTION);
        Auctions auctions = JsonUtils.parseJson(jsonAuctions, Auctions.class);
        auctions.setNeedLogin(false);
        BeanManager.auctionsRepository.updateAuctions(auctions, null);
    }

    private void deleteBidderFromList(Bidder bidder) {
        RecyclerViewBaseMultiData data = null;
        for(RecyclerViewBaseMultiData d : bidderList) {
            Bidder bidder1 = (Bidder) d;
            if (bidder1.getId() != bidder.getId()) continue;
            data = d;
            break;
        }
        if (data != null) bidderList.remove(data);
    }

    static class BidderListHolder extends RecyclerViewBaseViewHolder {
        TextView bidderName;
        View delButton;
        View commonPanel;
        View menuPanel;

        public BidderListHolder(@NonNull View itemView, RecyclerViewListItemType.Type type) {
            super(itemView, type);
            bidderName = itemView.findViewById(R.id.lbl_bidder_list_item_start_price);
            delButton = itemView.findViewById(R.id.btn_bidder_list_item_del);
            commonPanel = itemView.findViewById(R.id.lyo_bidder_list_item_panel);
            menuPanel = itemView.findViewById(R.id.lyo_bidder_list_item_menus);
        }
    }

}
