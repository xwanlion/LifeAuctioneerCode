package com.github.xwanlion.lifeauctioneer.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;
import com.github.xwanlion.lifeauctioneer.repository.OnDeleteListener;
import com.github.xwanlion.lifeauctioneer.repository.OnSaveListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;

public class AuctionsListAdapter extends RecyclerView.Adapter<RecyclerViewBaseViewHolder> implements OnSaveListener<Auctions>, OnDeleteListener<Auctions> {
    private View visibleMenuPanel = null;
    private AuctionsListFragment fragment;
    private List<RecyclerViewBaseMultiData> activityList = new ArrayList<>();
    private AuctionsStartingWindow startingWindow;

    public AuctionsListAdapter(AuctionsListFragment fragment) {
        this.fragment = fragment;
        startingWindow = new AuctionsStartingWindow(fragment);

    }

    public void setDataList(List<RecyclerViewBaseMultiData> list) {
        this.activityList = list;
    }

    @NonNull
    @Override
    public RecyclerViewBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (RecyclerViewListItemType.Type.getItemTypeByCode(viewType)) {
            case COMMON_ITEM:
                itemView = inflater.inflate(R.layout.auctions_list_item, parent, false);
                return new ActivityListHolder(itemView, RecyclerViewListItemType.Type.COMMON_ITEM);

            case ADD_NEW:
                itemView = inflater.inflate(R.layout.recycler_view_add_button_item, parent, false);
                return new RecyclerViewBaseViewHolder(itemView, RecyclerViewListItemType.Type.ADD_NEW);

        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewBaseViewHolder holder, final int position) {

        switch (holder.getType()) {
            case COMMON_ITEM:
                Auctions auctions = (Auctions) activityList.get(position);
                handleActivityItem((ActivityListHolder) holder, auctions);
                break;

            case ADD_NEW:
                handleNewItem(holder);
                break;
        }

    }

    public int getItemViewType(int position) {
        return activityList.get(position).typeCode();
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public void invisibleMenu() {
        if (visibleMenuPanel == null) return;
        visibleMenuPanel.performClick();
    }


    public void onDestroy() {
        startingWindow.unregisterAndServer();
        startingWindow = null;
    }

    @Override
    public void onDeleted(Auctions auctions) {
        invisibleMenu();
        this.deleteActivityFromList(auctions);
        this.notifyDataSetChanged();
    }

    @Override
    public void onSaved(Auctions auctions, long id) {
        invisibleMenu();

        int index = this.activityList.size() - 1;
        auctions.setId((int) id);
        this.activityList.add(index, auctions);
        this.notifyDataSetChanged();

    }

    private void handleNewItem(@NonNull RecyclerViewBaseViewHolder holders) {
        RecyclerViewBaseViewHolder recyclerHolder = holders;
        holders.root.findViewById(R.id.btn_recycle_view_add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // give a false auctions object to next fragment to prevent NullPointedException.
                // it also to mark that it's to add new auctions.
                Auctions auctions = new Auctions();
                auctions.setId(System.UNSAVED_AUCTIONS_ID);
                toAuctioneerSettingFragment(auctions);
            }
        });
    }

    private void handleActivityItem(@NonNull ActivityListHolder holder, Auctions auctions) {
        final TextView textViewAuctioneer = holder.activityItemAuctioneer;
        final TextView textViewDate = holder.activityItemDate;
        final TextView textViewMoney = holder.activityItemMoney;
        final ImageButton startUpButton = holder.startUpActivityButton;
        final ImageButton deleteButton = holder.deleteActivityButton;
        final ImageButton copyButton = holder.copyActivityButton;
        final View commonPanel = holder.commonPanel;
        final View menuPanel = holder.menuPanel;

        Date date = DateUtil.date(auctions.getDate());
        String dateFormat = fragment.getString(R.string.date_format);
        String dataText = DateUtil.format(date, dateFormat);

        String moneyText = String.format(fragment.getString(R.string.txt_activity_list_item_money), auctions.getMoney());
        if (auctions.getMoneyCreationWay() == MoneyCreationWay.RETRIEVE_BY_AGE) {
            moneyText = String.format(fragment.getString(R.string.txt_activity_list_item_money_per_age), auctions.getAmountPerAge());
        }

        textViewAuctioneer.setText(auctions.getAuctioneerName());
        textViewDate.setText(dataText);
        textViewMoney.setText(moneyText);

        commonPanel.setVisibility(View.VISIBLE);
        menuPanel.setVisibility(View.INVISIBLE);

        startUpButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (visibleMenuPanel != null) return false;
                visibleMenuPanel = menuPanel;

                menuPanel.setVisibility(View.VISIBLE);
                return true;
            }
        });

        startUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibleMenuPanel != null) {
                    invisibleMenu();
                } else {
                    startingWindow.show(auctions);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeanManager.auctionsRepository.delAuctions(auctions.getId(), AuctionsListAdapter.this);
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeanManager.auctionsRepository.copyAuctions(auctions, AuctionsListAdapter.this);
            }
        });

        // invisible menuPanel, which display delete button and copy button.
        commonPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPanel.performClick();
                toAuctioneerSettingFragment(auctions);
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

    private void toAuctioneerSettingFragment(Auctions auctions) {
        Bundle bundle = new Bundle();
        bundle.putString(System.KEY_AUCTION, auctions.toJson());
        int id = R.id.action_auctionsListFragment_to_hostManSettingFragment;
        NavHostFragment.findNavController(fragment).navigate(id, bundle);
    }

    private void deleteActivityFromList(Auctions auctions) {
        RecyclerViewBaseMultiData data = null;
        for(RecyclerViewBaseMultiData d : activityList) {
            Auctions activity = (Auctions) d;
            if (activity.getId() != auctions.getId()) continue;
            data = d;
            break;
        }
        if (data != null) activityList.remove(data);
    }


    static class ActivityListHolder extends  RecyclerViewBaseViewHolder {
        TextView activityItemAuctioneer;
        TextView activityItemDate;
        TextView activityItemMoney;
        ImageButton startUpActivityButton;
        ImageButton deleteActivityButton;
        ImageButton copyActivityButton;
        View commonPanel;
        View menuPanel;

        public ActivityListHolder(@NonNull View itemView, RecyclerViewListItemType.Type type) {
            super(itemView, type);
            activityItemAuctioneer = itemView.findViewById(R.id.txt_activity_item_auctioneer);
            activityItemDate = itemView.findViewById(R.id.txt_activity_item_date);
            activityItemMoney = itemView.findViewById(R.id.txt_activity_item_money);
            startUpActivityButton = itemView.findViewById(R.id.btn_to_startup_activity_item);
            deleteActivityButton = itemView.findViewById(R.id.btn_del_activity_item);
            copyActivityButton = itemView.findViewById(R.id.btn_copy_activity_item);
            commonPanel = itemView.findViewById(R.id.lyo_activity_list_item_panel1);
            menuPanel = itemView.findViewById(R.id.lyo_activity_list_item_panel2);
        }
    }

}
