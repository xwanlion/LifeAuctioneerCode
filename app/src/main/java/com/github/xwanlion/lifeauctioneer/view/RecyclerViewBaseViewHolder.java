package com.github.xwanlion.lifeauctioneer.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewBaseViewHolder extends RecyclerView.ViewHolder {
    public View root;
    private RecyclerViewListItemType.Type type;

    public RecyclerViewBaseViewHolder(@NonNull View itemView, RecyclerViewListItemType.Type type) {
        super(itemView);
        this.root = itemView;
        this.type = type;
    }

    public RecyclerViewListItemType.Type getType() {
        return type;
    }

}
