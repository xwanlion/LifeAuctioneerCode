package com.github.xwanlion.lifeauctioneer.repository;

import java.util.List;

public interface OnQueryListListener<T> {
    public void onQueried(List<T> list);
}
