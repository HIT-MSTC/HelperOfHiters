package com.example.helperofhiters;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class ShowTopicListActivity extends ActionBarActivity{
	
	private HttpConnect connect;
	private int connectTryTimes = 5;
	
	private String userName;
	
	private ListView listview;
	private ShowTopicAdapter adapter;
	
	private List<Map<String,String>> data;

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			Bundle bd = msg.getData();
			if(msg.what==HttpURLConnection.HTTP_OK)
			{
				if(bd.containsKey("GetPartTopic"))
				{
					data = XmlDeal.getData(XmlDeal.getXml(XmlDeal.getString(bd.getString("GetPartTopic"))));
				}
				else if(bd.containsKey("GetAllRecommandTopic"))
				{
					data = XmlDeal.getData(XmlDeal.getXml(XmlDeal.getString(bd.getString("GetAllRecommandTopic"))));
				}
				else if(bd.containsKey("GetUserTopic"))
				{
					data = XmlDeal.getData(XmlDeal.getXml(XmlDeal.getString(bd.getString("GetUserTopic"))));
				}
				else if(bd.containsKey("GetRecommandTopic"))
				{
					data = XmlDeal.getData(XmlDeal.getXml(XmlDeal.getString(bd.getString("GetRecommandTopic"))));
				}
				else if(bd.containsKey("GetUserFocusPart"))
				{
					String s = XmlDeal.getString(bd.getString("GetUserFocusPart"));
					if(s.equals(""))
						connect = new HttpConnect("GetAllRecommandTopic","",handler);
					else
						connect = new HttpConnect("GetRecommandTopic","Part="+s,handler);
					connectTryTimes++;
					connect.start();
				}
				if(data!=null)
				{
					if(data.size()==0)
						Toast.makeText(ShowTopicListActivity.this, "该项下还没有问题", Toast.LENGTH_LONG).show();
					adapter = new ShowTopicAdapter(ShowTopicListActivity.this,data);
					listview.setAdapter(adapter);
				}
			}
			else if(connectTryTimes>0)
			{
				connectTryTimes--;
				//connect.start();
			}
			else
				Toast.makeText(ShowTopicListActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_topic_list);
		listview = (ListView)findViewById(R.id.listView1);
		
		Intent intent = getIntent();
		userName = intent.getStringExtra("UserName");
		String s = intent.getStringExtra("Method");
		if("Part".equals(s))
		{
			connect = new HttpConnect("GetPartTopic","Part="+intent.getStringExtra("Part"),handler);
			connect.start();
		}
		else if("Recommand".equals(s))
		{
			if(userName==null)
				connect = new HttpConnect("GetAllRecommandTopic","",handler);
			else
				connect = new HttpConnect("GetUserFocusPart","UserName="+userName,handler);
			connect.start();
		}
		else if("Author".equals(s))
		{
			connect = new HttpConnect("GetUserTopic","Author="+userName,handler);
			connect.start();
		}
		
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(ShowTopicListActivity.this, ShowTopicAnswerActivity.class);
				intent.putExtra("UserName", userName);
				intent.putExtra("TopicId", (int)arg3);
				startActivity(intent);
			}
		});
		
	}
}
