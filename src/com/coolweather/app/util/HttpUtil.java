package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	public static void  sendHttpRequest(final String address, final HttpCallbackListener listener) {
		// �������߳�
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					// ��ȡHttpURLConnectionʵ��
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					// ��������ύ����
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					// ��ȡ���������ص�������
					InputStream in = connection.getInputStream();
					
					// ����Ի�ȡ�������������ж�ȡ
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					
					if (listener != null) {
						// �ص�onFinish��������
						listener.onFinish(response.toString());
					}
					
				} catch (Exception e) {
					if (listener != null) {
						// �ص�onError��������
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						// �ر�HTTP����
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
