/*
 * Copyright © 2018 Zhenjie Yan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xwanlion.lifeauctioneer.andserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.github.xwanlion.lifeauctioneer.util.Logger;
import com.github.xwanlion.lifeauctioneer.view.MainActivity;

import java.net.UnknownHostException;

/**
 * Created by Zhenjie Yan on 2018/6/9.
 */
public class ServerManager extends BroadcastReceiver {

    private static final String ACTION = "com.github.xwanlion.lifeauctioneer";

    private static final String CMD_KEY = "CMD_KEY";
    private static final String MESSAGE_KEY = "MESSAGE_KEY";

    private static final int CMD_VALUE_START = 1;
    private static final int CMD_VALUE_ERROR = 2;
    private static final int CMD_VALUE_STOP = 4;

    private AndServerListener andServerListener;

    /**
     * Notify serverStart.
     *
     * @param context context.
     */
    public static void onServerStart(Context context, String hostAddress) {
        sendBroadcast(context, CMD_VALUE_START, hostAddress);
    }

    /**
     * Notify serverStop.
     *
     * @param context context.
     */
    public static void onServerError(Context context, String error) {
        sendBroadcast(context, CMD_VALUE_ERROR, error);
    }

    /**
     * Notify serverStop.
     *
     * @param context context.
     */
    public static void onServerStop(Context context) {
        sendBroadcast(context, CMD_VALUE_STOP);
    }

    private static void sendBroadcast(Context context, int cmd) {
        sendBroadcast(context, cmd, null);
    }

    private static void sendBroadcast(Context context, int cmd, String message) {
        Intent broadcast = new Intent(ACTION);
        broadcast.putExtra(CMD_KEY, cmd);
        broadcast.putExtra(MESSAGE_KEY, message);
        context.sendBroadcast(broadcast);
    }

//    private MainActivity mActivity;
    private Fragment fragment;
    private Intent mService;

    public ServerManager(Fragment fragment, AndServerListener listener) {
//        this.mActivity = activity;
        this.fragment = fragment;
        this.andServerListener = listener;
        mService = new Intent(fragment.requireActivity(), CoreService.class);
    }

    /**
     * Register broadcast.
     */
    public void register() {
        IntentFilter filter = new IntentFilter(ACTION);
//        mActivity.registerReceiver(this, filter);
        fragment.requireActivity().registerReceiver(this, filter);
    }

    /**
     * UnRegister broadcast.
     */
    public void unRegister() {
//        mActivity.unregisterReceiver(this);
        fragment.requireActivity().unregisterReceiver(this);
    }

    public void startServer() {
//        mActivity.startService(mService);
        fragment.requireActivity().startService(mService);
    }

    public void stopServer() {
//        mActivity.stopService(mService);
        fragment.requireActivity().stopService(mService);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!ACTION.equals(action))  return;

        int cmd = intent.getIntExtra(CMD_KEY, 0);
        String msg = intent.getStringExtra(MESSAGE_KEY);
        switch (cmd) {
            case CMD_VALUE_START: {
                String ip = intent.getStringExtra(MESSAGE_KEY);
                //mActivity.onServerStart(ip);
                Logger.d("ip:" + ip);
                if (TextUtils.isEmpty(ip)) {
                    andServerListener.onServerError("ERR_WIFI_IP_IS_NULL");
                } else {
                    try {
                        andServerListener.onServerStarted(ip, CoreService.HTTP_PORT);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case CMD_VALUE_ERROR: {
                String error = intent.getStringExtra(MESSAGE_KEY);
                //mActivity.onServerError(error);
                andServerListener.onServerError(error);
                break;
            }
            case CMD_VALUE_STOP: {
                //mActivity.onServerStop();
                andServerListener.onServerStopped();
                break;
            }
        }
    }

}