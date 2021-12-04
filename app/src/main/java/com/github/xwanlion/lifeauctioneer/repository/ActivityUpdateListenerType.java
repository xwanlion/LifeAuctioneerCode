package com.github.xwanlion.lifeauctioneer.repository;

public enum ActivityUpdateListenerType {
    STARTING("starting", 0),
    STOPPING("stopping", 1);

    private String name;
    private int index;

    ActivityUpdateListenerType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int index() {
        return this.index;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
