package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	public static void  sendHttpRequest(final String address, final HttpCallbackListener listener) {
		// 开启子线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					// 获取HttpURLConnection实例
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					// 向服务器提交数据
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					// 获取服务器返回的输入流
					InputStream in = connection.getInputStream();
					
					// 下面对获取到的输入流进行读取
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					
					if (listener != null) {
						// 回调onFinish（）方法
						listener.onFinish(response.toString());
					}
					
				} catch (Exception e) {
					if (listener != null) {
						// 回调onError（）方法
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						// 关闭HTTP连接
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
