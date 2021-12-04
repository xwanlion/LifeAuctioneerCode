package com.github.xwanlion.lifeauctioneer.repository;

public interface OnDeleteListener<T> {
    public void onDeleted(T object);
}
