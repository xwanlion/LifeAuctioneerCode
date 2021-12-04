package com.github.xwanlion.lifeauctioneer.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 参考来源: https://blog.csdn.net/shenyuanqing/article/details/49249775
 */
public class NetworkUtils {

	// 是否联网
	public static boolean networkIsAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		context = null;
		if (connectivityManager == null) return false;

		NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
		if (networkInfo == null) return false;
		if (networkInfo.length <= 0) return  false;

		for (int i = 0; i < networkInfo.length; i++) {
			if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}

		return false;

	}

	// wifi 是否开启
	public static boolean wifiIsEnabled(Context context) {
		ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		context = null;
		if (networkInfo == null) return false;
		return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
	}

	public boolean wifiIsEnabled2(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		context = null;
		return wifiNetworkInfo.isConnected() ;
	}

	// 移动网络是否开启
	public static boolean mobileNetworkIsEnabled(Context context) {
		ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		context = null;
		if (networkInfo == null) return false;
		return networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
	}

	public static boolean hotspotWifiIsEnabled(Context context) {
		boolean r = HotspotWifiUtils.isEnabled(context);
		context = null;
		return r;
	}

}