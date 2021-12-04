package com.github.xwanlion.lifeauctioneer.andserver;

import java.net.UnknownHostException;

public interface AndServerListener {
    public void onServerStarted(String ip, int port) throws UnknownHostException;
    public void onServerError(String message);
    public void onServerStopped();
}
