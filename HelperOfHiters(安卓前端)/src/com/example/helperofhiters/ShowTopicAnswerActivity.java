package com.example.helperofhiters;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class ShowTopicAnswerActivity extends ActionBarActivity{
	
	private int connectTryTimes = 5;
	
	private String userName;
	private int topicId;
	
	private ListView listview;
	private ShowTopicAdapter adapter;
	private EditText answerEditor;
	private List<Map<String,String>> answer;
	private List<Map<String,String>> topic;
	
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			Bundle bd = msg.getData();
			if(msg.what==HttpURLConnection.HTTP_OK)
			{
				if(bd.containsKey("GetTopicAnswer"))
				{
					answer = XmlDeal.getData(XmlDeal.getXml(XmlDeal.getString(bd.getString("GetTopicAnswer"))));
				}
				else if(bd.containsKey("GetTopicXml"))
				{
					topic = XmlDeal.getData(XmlDeal.getXml(XmlDeal.getString(bd.getString("GetTopicXml"))));
				}
				if(topic!=null&&answer!=null)
				{
					topic.addAll(1, answer);
					adapter = new ShowTopicAdapter(ShowTopicAnswerActivity.this,topic);
					listview.setAdapter(adapter);
				}
			}
			else if(connectTryTimes>0)
			{
				connectTryTimes--;
				connect();
			}
			else
				Toast.makeText(ShowTopicAnswerActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
		}
	};
	private Handler editHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			Bundle bd = msg.getData();
			if(msg.what==HttpURLConnection.HTTP_OK)
			{
				if(bd.containsKey("ModifyAnswerText"))
				{
					if(XmlDeal.getBoolean((bd.getString("ModifyAnswerText"))))
						Toast.makeText(ShowTopicAnswerActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(ShowTopicAnswerActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("ModifyTopicText"))
				{
					if(XmlDeal.getBoolean((bd.getString("ModifyTopicText"))))
						Toast.makeText(ShowTopicAnswerActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(ShowTopicAnswerActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("DeleteAnswer"))
				{
					if(XmlDeal.getBoolean((bd.getString("DeleteAnswer"))))
					{
						Toast.makeText(ShowTopicAnswerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
						connectTryTimes++;
						connect();
					}
					else
						Toast.makeText(ShowTopicAnswerActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("DeleteTopic"))
				{
					if(XmlDeal.getBoolean((bd.getString("DeleteTopic"))))
					{
						Toast.makeText(ShowTopicAnswerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
						ShowTopicAnswerActivity.this.finish();
					}
					else
						Toast.makeText(ShowTopicAnswerActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("PostAnswer"))
				{
					if(XmlDeal.getBoolean((bd.getString("PostAnswer"))))
					{
						Toast.makeText(ShowTopicAnswerActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
						answerEditor.setText("");
						connectTryTimes++;
						connect();
					}
					else
						Toast.makeText(ShowTopicAnswerActivity.this, "回复失败", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("RecommandAnswer"))
				{
					if(XmlDeal.getBoolean((bd.getString("RecommandAnswer"))))
					{
						Toast.makeText(ShowTopicAnswerActivity.this, "设置满意答案成功", Toast.LENGTH_SHORT).show();
						connectTryTimes++;
						connect();
					}
					else
						Toast.makeText(ShowTopicAnswerActivity.this, "设置满意答案失败", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("CancelRecommandAnswer"))
				{
					if(XmlDeal.getBoolean((bd.getString("CancelRecommandAnswer"))))
					{
						Toast.makeText(ShowTopicAnswerActivity.this, "取消满意答案成功", Toast.LENGTH_SHORT).show();
						connectTryTimes++;
						connect();
					}
					else
						Toast.makeText(ShowTopicAnswerActivity.this, "取消满意答案失败", Toast.LENGTH_SHORT).show();
				}
			}
			else
				Toast.makeText(ShowTopicAnswerActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_topic_answer);
		listview = (ListView)findViewById(R.id.listView1);
		Intent mIntent = getIntent();
		topicId = mIntent.getExtras().getInt("TopicId");
		userName = mIntent.getExtras().getString("UserName");
		connect();
		listview.setOnItemLongClickListener(new OnItemLongClickListener(){

			@SuppressWarnings("unchecked")
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				final Map<String,String> data = (Map<String, String>) adapter.getItem(arg2);
				if(data.get("Author").equals(userName))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(ShowTopicAnswerActivity.this);
	                final String[] choice = {"编辑", "删除", "取消"};
	                builder.setItems(choice, new DialogInterface.OnClickListener()
	                {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which)
	                    {
	                        switch(which)
	                        {
	                        case 0:
	                        {
	                        	AlertDialog.Builder editor = new AlertDialog.Builder(ShowTopicAnswerActivity.this);
	                        	final EditText editText = new EditText(ShowTopicAnswerActivity.this);
	                        	editText.setText(data.get("Text"));
	                        	editText.setHeight(600);
	                        	editor.setView(editText);
	                        	editor.setPositiveButton("确定", new DialogInterface.OnClickListener(){
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										String text = editText.getText()+"";
										if(text.equals(""))
											return;
										HttpConnect hc;
										if(data.containsKey("Title"))
											hc = new HttpConnect("ModifyTopicText","TopicId="+data.get("id")+"&Text="+text,editHandler);
										else
											hc = new HttpConnect("ModifyAnswerText","id="+data.get("id")+"&Text="+text,editHandler);
										hc.start();
										data.put("Text", text);
										adapter.changeMessage(arg2, data);
									}
	                        	});
	                        	editor.setNegativeButton("取消", null);
	                        	editor.show();
	                        	break;
	                        }
	                        case 1:
	                        {
	                        	AlertDialog.Builder editor = new AlertDialog.Builder(ShowTopicAnswerActivity.this);
	                        	editor.setMessage("是否确认删除该贴");
	                        	editor.setPositiveButton("确定", new DialogInterface.OnClickListener(){
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										HttpConnect hc;
										if(data.containsKey("Title"))
											hc = new HttpConnect("DeleteTopic","TopicId="+data.get("id"),editHandler);
										else
											hc = new HttpConnect("DeleteAnswer","id="+data.get("id"),editHandler);
										hc.start();
									}
	                        	});
	                        	editor.setNegativeButton("取消", null);
	                        	editor.show();
	                        	break;
	                        }
	                        case 2:
	                        	break;
	                        }
	                    }
	                });
	                builder.show();
				}
				else if(((Map<String, String>) adapter.getItem(0)).get("Author").equals(userName))
				{
					if(data.get("Recommand").equals("false"))
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(ShowTopicAnswerActivity.this);
		                final String[] choice = {"设为满意答案", "删除", "取消"};
		                builder.setItems(choice, new DialogInterface.OnClickListener()
		                {
		                    @Override
		                    public void onClick(DialogInterface dialog, int which)
		                    {
		                        switch(which)
		                        {
		                        case 0:
		                        {
		                        	AlertDialog.Builder editor = new AlertDialog.Builder(ShowTopicAnswerActivity.this);
			                        editor.setMessage("是否标记为满意答案");
			                       	editor.setPositiveButton("确定", new DialogInterface.OnClickListener(){
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											HttpConnect hc = new HttpConnect("RecommandAnswer","id="+data.get("id"),editHandler);
											hc.start();
										}
		                        	});
		                        	editor.setNegativeButton("取消", null);
		                        	editor.show();
		                        	break;
		                        }
		                        case 1:
		                        {
		                        	AlertDialog.Builder editor = new AlertDialog.Builder(ShowTopicAnswerActivity.this);
		                        	editor.setMessage("是否确认删除该贴");
		                        	editor.setPositiveButton("确定", new DialogInterface.OnClickListener(){
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											HttpConnect hc;
											if(data.containsKey("Title"))
												hc = new HttpConnect("DeleteTopic","TopicId="+data.get("id"),editHandler);
											else
												hc = new HttpConnect("DeleteAnswer","id="+data.get("id"),editHandler);
											hc.start();
										}
		                        	});
		                        	editor.setNegativeButton("取消", null);
		                        	editor.show();
		                        	break;
		                        }
		                        case 2:
		                        	break;
		                        }
		                    }
		                });
		                builder.show();
					}
					else
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(ShowTopicAnswerActivity.this);
		                final String[] choice = {"取消满意答案", "删除", "取消"};
		                builder.setItems(choice, new DialogInterface.OnClickListener()
		                {
		                    @Override
		                    public void onClick(DialogInterface dialog, int which)
		                    {
		                        switch(which)
		                        {
		                        case 0:
		                        {
		                        	AlertDialog.Builder editor = new AlertDialog.Builder(ShowTopicAnswerActivity.this);
			                        editor.setMessage("是否取消该满意答案");
			                       	editor.setPositiveButton("确定", new DialogInterface.OnClickListener(){
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											HttpConnect hc = new HttpConnect("CancelRecommandAnswer","id="+data.get("id"),editHandler);
											hc.start();
										}
		                        	});
		                        	editor.setNegativeButton("取消", null);
		                        	editor.show();
		                        	break;
		                        }
		                        case 1:
		                        {
		                        	AlertDialog.Builder editor = new AlertDialog.Builder(ShowTopicAnswerActivity.this);
		                        	editor.setMessage("是否确认删除该贴");
		                        	editor.setPositiveButton("确定", new DialogInterface.OnClickListener(){
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											HttpConnect hc;
											if(data.containsKey("Title"))
												hc = new HttpConnect("DeleteTopic","TopicId="+data.get("id"),editHandler);
											else
												hc = new HttpConnect("DeleteAnswer","id="+data.get("id"),editHandler);
											hc.start();
										}
		                        	});
		                        	editor.setNegativeButton("取消", null);
		                        	editor.show();
		                        	break;
		                        }
		                        case 2:
		                        	break;
		                        }
		                    }
		                });
		                builder.show();
					}
				}
				return false;
			}
			
		});
		answerEditor = (EditText)findViewById(R.id.PostAnswerEdit);
		Button answerButton = (Button)findViewById(R.id.PostAnswerButton);
		answerButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userName!=null)
				{
					String text = answerEditor.getText()+"";
					if(text.equals(""))
						return;
					HttpConnect hc = new HttpConnect("PostAnswer","TopicId="+topicId+"&Author="+userName+"&Text="+text,editHandler);
					hc.start();
				}
			}
			
		});
	}
	public void connect()
	{
		topic = null;
		answer = null;
		HttpConnect hc1 = new HttpConnect("GetTopicAnswer","TopicId="+topicId,handler);
		hc1.start();
		HttpConnect hc2 = new HttpConnect("GetTopicXml","TopicId="+topicId,handler);
		hc2.start();
	}
}
