package com.github.xwanlion.lifeauctioneer.repository;

public interface OnQueryObjectListener<T> {
    public void onQueried(T obj);
}
