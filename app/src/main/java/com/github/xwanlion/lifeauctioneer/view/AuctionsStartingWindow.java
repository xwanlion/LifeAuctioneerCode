package com.github.xwanlion.lifeauctioneer.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.andserver.AndServerListener;
import com.github.xwanlion.lifeauctioneer.andserver.ServerManager;
import com.github.xwanlion.lifeauctioneer.conf.ActivitySate;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;
import com.github.xwanlion.lifeauctioneer.repository.ActivityUpdateListenerType;
import com.github.xwanlion.lifeauctioneer.repository.OnQueryObjectListener;
import com.github.xwanlion.lifeauctioneer.repository.OnUpdateListener;
import com.github.xwanlion.lifeauctioneer.socket.SocketServer;
import com.github.xwanlion.lifeauctioneer.util.HotspotWifiUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;
import com.github.xwanlion.lifeauctioneer.util.NetworkUtils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public class AuctionsStartingWindow implements OnUpdateListener<Auctions>, AndServerListener {
    private AuctionsListFragment fragment;
    private ServerManager andServerManager;

    private Button stopButton;
    private Button startButton;
    private Button setupHotspotButton;
    private Button showInBrowserButton;
    private Button exitsButton;
    private Button haveNoWifiButton;

    private TextView tipsEditText;
    private TextView lotsTextView;
    private TextView biddersTextView;
    private TextView moneyTextView;
    private TextView auctioneerTextView;
    private PopupWindow popupWindow;
    AuctionsStartingWindow me;

    private String ip;
    private int port;
    private ActivityUpdateListenerType activityUpdateType;

    public AuctionsStartingWindow(AuctionsListFragment fragment) {
        this.fragment = fragment;
        registerAndServer();
    }

    public void show(Auctions auctions) {

        Activity activity = fragment.getActivity();
        popupWindow = new PopupWindow();
//        View popupView = LayoutInflater.from(activity).inflate(R.layout.activity_start, null);
        View popupLayout = this.fragment.getLayoutInflater().inflate(R.layout.activity_start, null);
        popupLayout.getBackground().setAlpha(System.DEFAULT_POPUP_WINDOW_TRANSPARENCY);

        popupWindow.setContentView(popupLayout);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(activity.findViewById(R.id.lyo_activity_list), Gravity.CENTER, 0, 0);

        stopButton = popupLayout.findViewById(R.id.btn_stop_activity);
        startButton = popupLayout.findViewById(R.id.btn_start_activity);
        setupHotspotButton = popupLayout.findViewById(R.id.btn_set_up_hotspot);
        showInBrowserButton = popupLayout.findViewById(R.id.btn_show_in_browser);
        exitsButton = popupLayout.findViewById(R.id.btn_exit_window);
        haveNoWifiButton = popupLayout.findViewById(R.id.btn_have_no_wifi);

        biddersTextView = popupLayout.findViewById(R.id.txt_auction_start_bidders);
        lotsTextView = popupLayout.findViewById(R.id.txt_auction_start_lot_items);
        moneyTextView = popupLayout.findViewById(R.id.txt_auction_start_money);
        auctioneerTextView = popupLayout.findViewById(R.id.txt_auction_start_host);
        tipsEditText =  popupLayout.findViewById(R.id.lbl_starting_tips);

        me = this;
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUpdateType = ActivityUpdateListenerType.STOPPING;
                BeanManager.auctionsRepository.stopAuctions(auctions, me);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkAvailable() == false) {
                    wifiHasDisabled();

                } else {
                    activityUpdateType = ActivityUpdateListenerType.STARTING;
                    BeanManager.auctionsRepository.startAuctions(auctions, me);

                    if (auctions.getState() >= ActivitySate.FILL_TESTIMONIALS)  return;
                    BeanManager.bidLogRepository.clearBidLog(auctions.getId(), null);
                }
            }
        });

        setupHotspotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotspotWifiUtils.toHotspotWifiUI(fragment.getActivity());
                tipsEditText.setText(R.string.tips_can_share_hotspot);
                stopButton.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);
                setupHotspotButton.setVisibility(View.INVISIBLE);
                showInBrowserButton.setVisibility(View.INVISIBLE);
                exitsButton.setVisibility(View.VISIBLE);
                haveNoWifiButton.setVisibility(View.INVISIBLE);
            }
        });

        showInBrowserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = String.format(fragment.getString(R.string.lbl_auctions_url), ip, port);
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                fragment.startActivity(intent);
            }
        });

        exitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // default to wifi disabled.
                wifiHasDisabled();
                popupWindow.dismiss();
                popupWindow = null;
            }
        });

        haveNoWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopButton.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);
                setupHotspotButton.setVisibility(View.VISIBLE);
                showInBrowserButton.setVisibility(View.INVISIBLE);
                exitsButton.setVisibility(View.VISIBLE);
                haveNoWifiButton.setVisibility(View.INVISIBLE);
                tipsEditText.setText(fragment.getString(R.string.tips_can_share_hotspot));
            }
        });

        this.setTextViewData(auctions);
        startButton.performClick();

    }

    @Override
    public void onUpdated(Auctions auctions) {
        if (this.activityUpdateType == ActivityUpdateListenerType.STOPPING) {
            this.andServerManager.stopServer();

        } else if (this.activityUpdateType == ActivityUpdateListenerType.STARTING) {
            this.andServerManager.startServer();

        }

        this.activityUpdateType = null;
    }

    @Override
    public void onServerStarted(String ip, int port) {
        this.ip = ip;
        this.port = port;

        if (SocketServer.start(System.WEB_SOCKET_SERVER_PORT) == false) {
            String str = fragment.getResources().getString(R.string.msg_server_started_fail);
            Toast.makeText(fragment.getContext(), str, Toast.LENGTH_LONG);
            return;
        }

        String format = fragment.getResources().getString(R.string.lbl_activity_url_tips);
        String urlTips = String.format(format, this.ip, this.port);

        tipsEditText.setText(urlTips);
        stopButton.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        setupHotspotButton.setVisibility(View.INVISIBLE);
        showInBrowserButton.setVisibility(View.VISIBLE);
        exitsButton.setVisibility(View.INVISIBLE);
        haveNoWifiButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onServerError(String message) {
        try {
            Logger.i(message);
            tipsEditText.setText(R.string.tips_and_server_error);
            SocketServer.stops();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onServerStopped() {
        try {
            SocketServer.stops();

            tipsEditText.setText(R.string.tips_and_server_stopped);
            stopButton.setVisibility(View.INVISIBLE);
            startButton.setVisibility(View.VISIBLE);
            setupHotspotButton.setVisibility(View.INVISIBLE);
            showInBrowserButton.setVisibility(View.INVISIBLE);
            exitsButton.setVisibility(View.VISIBLE);
            haveNoWifiButton.setVisibility(View.INVISIBLE);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public boolean registerAndServer() {
        if (andServerManager != null) return true;
        andServerManager = new ServerManager(fragment, this);
        andServerManager.register();
        return true;
    }

    public void unregisterAndServer() {
        if (this.andServerManager == null) return;
        this.andServerManager.unRegister();
        andServerManager = null;
    }

    private void wifiHasDisabled() {
        if (stopButton != null) stopButton.setVisibility(View.INVISIBLE);
        if (startButton != null) startButton.setVisibility(View.INVISIBLE);
        if (setupHotspotButton != null) setupHotspotButton.setVisibility(View.INVISIBLE);
        if (showInBrowserButton != null) showInBrowserButton.setVisibility(View.INVISIBLE);
        if (exitsButton != null) exitsButton.setVisibility(View.VISIBLE);
        if (haveNoWifiButton != null) haveNoWifiButton.setVisibility(View.VISIBLE);
        if (tipsEditText != null) tipsEditText.setText(fragment.getString(R.string.tips_need_join_internet));
    }

    private boolean networkAvailable() {
        if (NetworkUtils.wifiIsEnabled(fragment.getActivity()))  return true;
        if (NetworkUtils.hotspotWifiIsEnabled(fragment.getActivity())) return true;
        return false;
    }

    private void setTextViewData(Auctions auctions) {
        LiveData<List<Lot>> lots = BeanManager.lotRepository.listLot(auctions.getId());
        LiveData<List<Bidder>> bidders = BeanManager.bidderRepository.listBidder(auctions.getId());

        lots.observe(fragment.getViewLifecycleOwner(), new Observer<List<Lot>>() {
            @Override
            public void onChanged(List<Lot> lotsList) {
                lotsTextView.setText(String.valueOf(lotsList.size()));
            }
        });

        bidders.observe(fragment.getViewLifecycleOwner(), new Observer<List<Bidder>>() {
            @Override
            public void onChanged(List<Bidder> biddersList) {
                biddersTextView.setText(String.valueOf(biddersList.size()));
            }
        });

        auctioneerTextView.setText(auctions.getAuctioneerName());
        moneyTextView.setText(String.valueOf(auctions.getMoney()));
        if (auctions.getMoneyCreationWay() == MoneyCreationWay.RETRIEVE_BY_AGE) {
            String format = fragment.getString(R.string.txt_activity_list_item_money_per_age);
            moneyTextView.setText(String.format(format, auctions.getAmountPerAge()));
        }
    }

}
