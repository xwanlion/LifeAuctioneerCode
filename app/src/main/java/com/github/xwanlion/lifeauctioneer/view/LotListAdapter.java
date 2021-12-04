package com.github.xwanlion.lifeauctioneer.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;
import com.github.xwanlion.lifeauctioneer.repository.OnDeleteListener;
import com.github.xwanlion.lifeauctioneer.util.AndroidPermissionHelper;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class LotListAdapter extends RecyclerView.Adapter<RecyclerViewBaseViewHolder> implements OnDeleteListener<Lot> {
    private View visibleMenuPanel = null;
    private LotListFragment fragment;
    private List<RecyclerViewBaseMultiData> lotList = new ArrayList<>();

    public LotListAdapter(LotListFragment fragment) {
        this.fragment = fragment;
    }

    public void setDataList(List<RecyclerViewBaseMultiData> list) {
        this.lotList = list;
    }

    @NonNull
    @Override
    public RecyclerViewBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (RecyclerViewListItemType.Type.getItemTypeByCode(viewType)) {
            case COMMON_ITEM:
                return new LotListAdapter.AuctionLotListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lot_list_item, parent, false), RecyclerViewListItemType.Type.COMMON_ITEM);

            case ADD_NEW:
                return new RecyclerViewBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_add_button_item_2, parent, false), RecyclerViewListItemType.Type.ADD_NEW);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewBaseViewHolder holder, final int position) {
        switch (holder.getType()) {

            case COMMON_ITEM:
                Lot lot = (Lot) lotList.get(position);
                handleBidderItem((LotListAdapter.AuctionLotListHolder) holder, lot);
                break;

            case ADD_NEW:
                handleNewItem(holder);
                break;

        }
    }

    private void handleNewItem(@NonNull RecyclerViewBaseViewHolder holders) {
        RecyclerViewBaseViewHolder recyclerHolder = holders;
        holders.root.findViewById(R.id.btn_recycle_view_add_item_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = fragment.getArguments();
                int id = R.id.action_auctionLotListFragment_to_auctionLotAddingFragment;
                NavHostFragment.findNavController(fragment).navigate(id, bundle);
            }
        });
    }

    private void handleBidderItem(@NonNull LotListAdapter.AuctionLotListHolder holder, Lot lot) {
        final TextView lotName = holder.lotName;
        final TextView lotStartPrice = holder.lotStartPrice;
        final TextView lotIncrement = holder.lotIncrement;
        final ImageView lotPicture = holder.lotPicture;
        final ImageButton delLotButton = holder.delLotButton;
        final View commonPanel = holder.commonPanel;
        final View menuPanel = holder.menuPanel;

        String startPrice = String.format(fragment.getString(R.string.txt_lot_list_item_start_price), lot.getStartPrice());
        String increment = String.format(fragment.getString(R.string.txt_lot_list_item_increment), lot.getIncrement());

        AndroidPermissionHelper.requireExternalStoragePermissionIfNeeded(fragment.getActivity());
        Bitmap bitmap = BitmapFactory.decodeFile(lot.getImageFile());

        lotName.setText(lot.getName());
        lotStartPrice.setText(startPrice);
        lotIncrement.setText(increment);
        lotPicture.setImageBitmap(bitmap);
        lotPicture.setDrawingCacheEnabled(true);

        commonPanel.setVisibility(View.VISIBLE);
        menuPanel.setVisibility(View.INVISIBLE);

        delLotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeanManager.lotRepository.delLot(lot.getId(), LotListAdapter.this);
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
        return lotList.get(position).typeCode();
    }

    @Override
    public int getItemCount() {
        return lotList.size();
    }

    public void invisibleMenuPanel() {
        if (visibleMenuPanel == null) return;
        visibleMenuPanel.performClick();
    }

    @Override
    public void onDeleted(Lot lot) {
        invisibleMenuPanel();

        boolean success = lotList.remove(lot);
        this.deleteLotFromList(lot);
        this.notifyDataSetChanged();

    }

    private void deleteLotFromList(Lot lot) {
        RecyclerViewBaseMultiData data = null;
        for(RecyclerViewBaseMultiData d : lotList) {
            Lot lot1  = (Lot) d;
            if (lot1.getId() != lot.getId()) continue;
            data = d;
            break;
        }
        if (data != null) lotList.remove(data);
    }

    static class AuctionLotListHolder extends RecyclerViewBaseViewHolder {
        TextView lotName;
        TextView lotStartPrice;
        TextView lotIncrement;
        ImageView lotPicture;
        ImageButton delLotButton;
        View commonPanel;
        View menuPanel;

        public AuctionLotListHolder(@NonNull View itemView, RecyclerViewListItemType.Type type) {
            super(itemView, type);
            lotName = itemView.findViewById(R.id.lbl_auction_lot_list_item_name);
            lotStartPrice = itemView.findViewById(R.id.lbl_auction_lot_list_item_start_price);
            lotIncrement = itemView.findViewById(R.id.lbl_auction_lot_list_item_increment);
            lotPicture = itemView.findViewById(R.id.pic_auction_lot_list_item_picture);
            delLotButton = itemView.findViewById(R.id.btn_auction_lot_list_item_del);
            commonPanel = itemView.findViewById(R.id.lyo_auction_lot_list_item_panel);
            menuPanel = itemView.findViewById(R.id.lyo_auction_lot_list_item_menus);
        }
    }

}
