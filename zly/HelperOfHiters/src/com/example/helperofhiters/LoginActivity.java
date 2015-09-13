package com.example.helperofhiters;

import java.net.HttpURLConnection;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class LoginActivity extends ActionBarActivity {
	private Button registerButton;
	private Button loginButton;
	private String userName;
	private Intent messageIntent;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if(msg.what==HttpURLConnection.HTTP_OK)
			{
				Bundle bd = msg.getData();
				if(bd.containsKey("Login"))
				{
					if(XmlDeal.getBoolean(bd.getString("Login")))
					{
						//login success
						loginButton.setEnabled(false);
						registerButton.setEnabled(false);
						Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
						messageIntent = new Intent();
						messageIntent.putExtra("UserName", userName);
						LoginActivity.this.finish();
					}
					else
					{
						//userName or password error
						Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
					}
				}
				else if(bd.containsKey("Register"))
				{
					if(XmlDeal.getBoolean(bd.getString("Register")))
					{
						//register success
						loginButton.setEnabled(false);
						registerButton.setEnabled(false);
						Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
						messageIntent = new Intent();
						messageIntent.putExtra("UserName", userName);
						LoginActivity.this.finish();
					}
					else
					{
						//userName has been used
						Toast.makeText(LoginActivity.this, "此用户名可能已被使用", Toast.LENGTH_SHORT).show();
					}
				}
			}
			else
			{
				//connect error
				Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		registerButton = (Button)findViewById(R.id.button1);
		loginButton = (Button)findViewById(R.id.button2);
		Button nonameButton = (Button)findViewById(R.id.button3);
		final EditText userNameEditText = (EditText)findViewById(R.id.editText1);
		final EditText passwordEditText = (EditText)findViewById(R.id.editText2);
		registerButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = userNameEditText.getText()+"";
				String password = passwordEditText.getText()+"";
				if(userName.isEmpty()||password.isEmpty())
					return;
				HttpConnect hc = new HttpConnect("Register","UserName="+userName+"&Password="+password,handler);
				hc.start();
			}
			
		});
		loginButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userName = userNameEditText.getText()+"";
				String password = passwordEditText.getText()+"";
				HttpConnect hc = new HttpConnect("Login","UserName="+userName+"&Password="+password,handler);
				hc.start();
			}
			
		});
		nonameButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(LoginActivity.this, ShowTopicAnswerActivity.class);
				intent.putExtra("TopicId", 1);
				intent.putExtra("UserName", "zhaoluyuan");
				startActivity(intent);
				LoginActivity.this.finish();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
