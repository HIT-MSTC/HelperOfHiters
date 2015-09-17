package com.example.helperofhiters;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HttpConnect extends Thread{
	private String connStr = "http://192.168.17.160:22222/WebService1.asmx";
	private String method;
	private String data;
	private Handler handler;
	private Message response;
	HttpConnect(String method,String data,Handler handler)
	{
		this.method = method;
		this.data = data;
		this.handler = handler;
	}
	public void run()
	{
		if(method==null||data==null||handler==null||method.equals(""))
			return;
		try {
			response = Message.obtain(handler);
			byte b[] = data.getBytes("utf-8");
			URL url = new URL(connStr+"/"+method);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setConnectTimeout(5000);
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Length", ""+b.length);
			OutputStream os = con.getOutputStream();
			os.write(b);
			os.flush();
			os.close();
			response.what = con.getResponseCode();
			if(response.what==HttpURLConnection.HTTP_OK)
			{
				ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
				byte buffer[] = new byte[1024];
				InputStream is = con.getInputStream();
				int count = -1;  
		        while ((count = is.read(buffer, 0, buffer.length)) != -1)  
		        	bos.write(buffer, 0, count);
				is.close();
				Bundle bd = new Bundle();
				bd.putString(method, bos.toString("utf-8"));
				response.setData(bd);
			}
			else
			{
				Bundle bd = new Bundle();
				bd.putString(method, null);
				response.setData(bd);
			}
			con.disconnect();
			response.sendToTarget();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(handler!=null)
			{
				handler.sendEmptyMessage(-1);
			}
		}
	}
}
