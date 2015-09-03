package com.coolweather.app.receiver;

import com.coolweather.app.service.AutoUpdateService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 再次启动AutoUpdateService就可以实现后台定时更新的功能
		Intent i = new Intent(context, AutoUpdateService.class);
		context.startService(i);
	}

}
