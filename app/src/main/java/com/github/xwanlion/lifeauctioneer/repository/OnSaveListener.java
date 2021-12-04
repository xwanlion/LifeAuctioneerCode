package com.github.xwanlion.lifeauctioneer.repository;

public interface OnSaveListener<T> {
    public void onSaved(T object, long id);
}
