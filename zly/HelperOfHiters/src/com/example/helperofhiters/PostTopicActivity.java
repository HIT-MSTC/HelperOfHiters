package com.example.helperofhiters;

import java.net.HttpURLConnection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class PostTopicActivity extends ActionBarActivity{
	private String userName;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if(msg.what==HttpURLConnection.HTTP_OK)
			{
				Bundle bd = msg.getData();
				if(bd.containsKey("PostTopic"))
				{
					if(XmlDeal.getBoolean(bd.getString("PostTopic")))
					{
						Toast.makeText(PostTopicActivity.this, "发布成功，请在 我的问题 中查看", Toast.LENGTH_SHORT).show();
						finish();
					}
					else
						Toast.makeText(PostTopicActivity.this, "发布失败，请重试", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_topic);
		final EditText titleText = (EditText)findViewById(R.id.editText1);
		final EditText containText = (EditText)findViewById(R.id.editText2);
		final Spinner spinner = (Spinner)findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,NameListOfPart.list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		Button button = (Button)findViewById(R.id.button1);
		Intent intent = getIntent();
		userName = intent.getStringExtra("UserName");
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!"".equals(titleText.getText()+"")&&!"".equals(containText.getText()+""))
				{
					HttpConnect hc = new HttpConnect("PostTopic",
							"Author="+userName+"&Title="+titleText.getText()+"&Part="+spinner.getSelectedItem()+"&Text="+containText.getText(),handler);
					hc.start();
				}
			}
		});
	}
}
