package com.tangcco170205_ftp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {

	// 判断是否可用
	public static boolean isNetworkAvailable(Context context) {

		// 1.Monitor network connections (Wi-Fi, GPRS, UMTS, etc.)
		// 监控网络连接(wifi,GPRS等);
		// 2.Send broadcast intents when network connectivity changes
		// 当网络连接状态发生改变的时候,发送一个广播意图;
		// 3.Attempt to "fail over" to another network when connectivity to a
		// network is lost
		// 当与一个网络的连接丢失的时候,尝试进行"故障转移"到一个新的网络.
		// 4.Provide an API that allows applications to query the coarse-grained
		// or fine-grained state of the available networks
		// 提供了一套API方法,供开发者查询网络的好与坏的状态.

		// ConnectivityManager:我们可以在这个类中,查询与网络连接状态相关的信息.
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取可用的网络信息
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			// 判断网络是否可用
			return info.isAvailable();
		}
		return false;
	}

	// 判断网络是否已连接
	public static boolean isNetworkConnected(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info != null) {
			return info.isConnected();
		}
		return false;
	}

	// 判断是否连接的是WiFi网络
	public static boolean isWiFiConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// WiFi类型的网络信息
		NetworkInfo info = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (info != null) {
			return info.isConnected();
		}

		return false;
	}

	// 判断是否连接的是Mobile网络
	public static boolean isMobileConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// WiFi类型的网络信息
		NetworkInfo info = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (info != null) {
			return info.isConnected();
		}

		return false;
	}

	// 当前连接的网络类型名称
	public static String getNetworkTypename(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			// 网络类型
			// info.getType();
			// 网络类型名称
			return info.getTypeName();
		}

		return null;
	}
}
