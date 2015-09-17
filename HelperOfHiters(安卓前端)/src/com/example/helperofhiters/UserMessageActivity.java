package com.example.helperofhiters;

import java.net.HttpURLConnection;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class UserMessageActivity extends ActionBarActivity {
	private String userName;
	private EditText userNameText;
	private EditText emailText;
	private EditText studentNumberText;
	private EditText majorText;
	private TextView focusPartText;
	private AlertDialog modifyPasswordDialog;
	int num;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if(msg.what==HttpURLConnection.HTTP_OK)
			{
				Bundle bd = msg.getData();
				if(bd.containsKey("GetUserXml"))
				{
					Map<String,String> data = XmlDeal.getData(XmlDeal.getXml(XmlDeal.getString(bd.getString("GetUserXml")))).get(0);
					emailText.setText(data.get("Email"));
					studentNumberText.setText(data.get("StudentNumber"));
					majorText.setText(data.get("Major"));
					if(data.get("FocusPart").length()>0)
						focusPartText.setText(data.get("FocusPart").substring(0, data.get("FocusPart").length()-1));
					else
						focusPartText.setText("");
				}
				else if(bd.containsKey("ModifyUserEmail"))
				{
					if(XmlDeal.getBoolean(bd.getString("ModifyUserEmail")))
						num++;
					else
						Toast.makeText(UserMessageActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("ModifyUserStudentNumber"))
				{
					if(XmlDeal.getBoolean(bd.getString("ModifyUserStudentNumber")))
						num++;
					else
						Toast.makeText(UserMessageActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("ModifyUserMajor"))
				{
					if(XmlDeal.getBoolean(bd.getString("ModifyUserMajor")))
						num++;
					else
						Toast.makeText(UserMessageActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("ModifyUserFocusPart"))
				{
					if(XmlDeal.getBoolean(bd.getString("ModifyUserFocusPart")))
						num++;
					else
						Toast.makeText(UserMessageActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
				}
				else if(bd.containsKey("ModifyUserPassword"))
				{
					if(XmlDeal.getBoolean(bd.getString("ModifyUserPassword")))
						Toast.makeText(UserMessageActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(UserMessageActivity.this, "密码修改失败，请重试", Toast.LENGTH_SHORT).show();
				}
				if(num==4)
					Toast.makeText(UserMessageActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
			}
			else
			{
				//connect error
				Toast.makeText(UserMessageActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_message);
		userNameText = (EditText)findViewById(R.id.editText1);
		emailText = (EditText)findViewById(R.id.editText2);
		studentNumberText = (EditText)findViewById(R.id.editText3);
		majorText = (EditText)findViewById(R.id.editText4);
		focusPartText = (TextView)findViewById(R.id.editText5);
		
		Intent intent = getIntent();
		userName = intent.getStringExtra("UserName");
		if(userName==null)
			finish();
		userNameText.setText(userName);
		
		HttpConnect hc = new HttpConnect("GetUserXml","UserName="+userName,handler);
		hc.start();
		
		Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				num = 0;
				HttpConnect hc1 = new HttpConnect("ModifyUserEmail","UserName="+userName+"&Email="+emailText.getText(),handler);
				hc1.start();
				HttpConnect hc2 = new HttpConnect("ModifyUserStudentNumber","UserName="+userName+"&StudentNumber="+studentNumberText.getText(),handler);
				hc2.start();
				HttpConnect hc3 = new HttpConnect("ModifyUserMajor","UserName="+userName+"&Major="+majorText.getText(),handler);
				hc3.start();
				HttpConnect hc4 = new HttpConnect("ModifyUserFocusPart","UserName="+userName+"&FocusPart="+focusPartText.getText()+",",handler);
				hc4.start();
			}
		});
		focusPartText.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = new AlertDialog.Builder(UserMessageActivity.this);
				dialog.setTitle("关注领域");
				final boolean[] choose = new boolean[NameListOfPart.list.length];
				for(int i=0;i<NameListOfPart.list.length;i++)
					choose[i] = (focusPartText.getText()+"").contains(NameListOfPart.list[i]);
				dialog.setMultiChoiceItems(NameListOfPart.list, choose, new OnMultiChoiceClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						// TODO Auto-generated method stub
						choose[which] = isChecked;
					}
				});
				dialog.setPositiveButton("完成", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						String s = "";
						for(int i=0;i<choose.length;i++)
							if(choose[i])
								s += NameListOfPart.list[i]+",";
						if(s.length()>0)
							focusPartText.setText(s.substring(0, s.length()-1));
						else
							focusPartText.setText("");
					}
				});
				dialog.setNegativeButton("取消", null);
				dialog.show();
			}
		});
		
		AlertDialog.Builder builder = new AlertDialog.Builder(UserMessageActivity.this);
    	final EditText editText1 = new EditText(UserMessageActivity.this);
    	final EditText editText2 = new EditText(UserMessageActivity.this);
    	editText1.setHint("请输入新密码");
    	editText2.setHint("请重复新密码");
    	editText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
    	editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
    	final LinearLayout layout = new LinearLayout(UserMessageActivity.this);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	layout.addView(editText1);
    	layout.addView(editText2);
    	builder.setView(layout);
    	builder.setTitle("修改密码");
    	builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String s1 = editText1.getText()+"";
				String s2 = editText2.getText()+"";
				if(s1.equals(s2))
				{
					HttpConnect hc = new HttpConnect("ModifyUserPassword","UserName="+userName+"&Password="+s1,handler);
					hc.start();
				}
				else
					Toast.makeText(UserMessageActivity.this, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
			}
    	});
    	builder.setNegativeButton("取消", null);
    	modifyPasswordDialog = builder.create();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_user_message, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.item1) {
			modifyPasswordDialog.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
