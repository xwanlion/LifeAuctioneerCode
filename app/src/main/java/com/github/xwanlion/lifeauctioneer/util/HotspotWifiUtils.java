package com.github.xwanlion.lifeauctioneer.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

/**
 * 参考来源: https://blog.csdn.net/u013049709/article/details/42235829
 * 需要权限: <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 */
public class HotspotWifiUtils {
 
	private static WifiManager wifiManager;
//	private final static String GET_WIFI_STATE_METHOD_NAME = "getState";
	private final static String GET_WIFI_STATE_METHOD_NAME = "getWifiApState";

	public static boolean isEnabled(Context context) {
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		boolean r = (getState() == HOTSPOT_WIFI_STATE.WIFI_STATE_ENABLED);
		wifiManager = null;
		context = null;
		return r;
	}
 
	private static HOTSPOT_WIFI_STATE getState() {
		try {
			Method method = wifiManager.getClass().getMethod(GET_WIFI_STATE_METHOD_NAME);
			int state = ((Integer) method.invoke(wifiManager));
			if (state > 10) state = state - 10; // Fix for Android 4
			return HOTSPOT_WIFI_STATE.class.getEnumConstants()[state];

		} catch (Exception e) {
			e.printStackTrace();
			return HOTSPOT_WIFI_STATE.WIFI_STATE_FAILED;
			
		}
	}

	/**
	 * 打开网络共享与热点设置页面
	 */
	public static void toHotspotWifiUI(Activity activity) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//打开网络共享与热点设置页面
		ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$TetherSettingsActivity");
		intent.setComponent(comp);
		activity.startActivity(intent);
		activity = null;
	}
 
	public enum HOTSPOT_WIFI_STATE {
		WIFI_STATE_DISABLING, 
		WIFI_STATE_DISABLED, 
		WIFI_STATE_ENABLING,  
		WIFI_STATE_ENABLED, 
		WIFI_STATE_FAILED
	}

}