package com.github.xwanlion.lifeauctioneer.model;

import com.github.xwanlion.lifeauctioneer.util.JsonUtils;
import com.github.xwanlion.lifeauctioneer.view.RecyclerViewBaseMultiData;
import com.github.xwanlion.lifeauctioneer.view.RecyclerViewListItemType;

import cn.hutool.json.JSONUtil;

public class JsonPo extends RecyclerViewBaseMultiData {
    public String toJson() {
        return JsonUtils.toJsonString(this);
    }

    @Override
    public int typeCode() {
        return RecyclerViewListItemType.COMMON_ITEM_TYPE;
    }
}
