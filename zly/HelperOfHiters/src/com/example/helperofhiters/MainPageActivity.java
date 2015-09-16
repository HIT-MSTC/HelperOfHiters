package com.example.helperofhiters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainPageActivity extends ActionBarActivity{
	private String userName;
	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		textView = (TextView)findViewById(R.id.textView1);
		Intent intent = getIntent();
		if(intent.getStringExtra("UserName")!=null)
			userName = intent.getStringExtra("UserName");
		if(userName!=null)
			textView.setText("Hello "+userName);
		else
			textView.setText("Äú»¹Î´µÇÂ¼");
		ImageView imageview1 = (ImageView)findViewById(R.id.imageView1);
		imageview1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userName!=null)
				{
					Intent intent = new Intent().setClass(MainPageActivity.this, UserMessageActivity.class);
					intent.putExtra("UserName", userName);
					startActivity(intent);
				}
				else
					Toast.makeText(MainPageActivity.this, "ÇëÏÈµÇÂ¼", Toast.LENGTH_SHORT).show();
			}
		});
		ImageView imageview2 = (ImageView)findViewById(R.id.imageView2);
		imageview2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(MainPageActivity.this, ShowPartListActivity.class);
				intent.putExtra("UserName", userName);
				startActivity(intent);
			}
		});
		ImageView imageview3 = (ImageView)findViewById(R.id.imageView3);
		imageview3.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userName!=null)
				{
					Intent intent = new Intent().setClass(MainPageActivity.this, ShowTopicListActivity.class);
					intent.putExtra("UserName", userName);
					intent.putExtra("Method", "Author");
					startActivity(intent);
				}
				else
					Toast.makeText(MainPageActivity.this, "ÇëÏÈµÇÂ¼", Toast.LENGTH_SHORT).show();
			}
		});
		ImageView imageview4 = (ImageView)findViewById(R.id.imageView4);
		imageview4.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(MainPageActivity.this, ShowTopicListActivity.class);
				intent.putExtra("UserName", userName);
				intent.putExtra("Method", "Recommand");
				startActivity(intent);
			}
		});
		ImageView imageview5 = (ImageView)findViewById(R.id.imageView5);
		imageview5.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userName!=null)
				{
					Intent intent = new Intent().setClass(MainPageActivity.this, PostTopicActivity.class);
					intent.putExtra("UserName", userName);
					startActivity(intent);
				}
				else
					Toast.makeText(MainPageActivity.this, "ÇëÏÈµÇÂ¼", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override 
    protected void onNewIntent(Intent intent) {  
		if(intent.getStringExtra("UserName")!=null)
			userName = intent.getStringExtra("UserName");
		if(userName!=null)
			textView.setText("Hello "+userName);
		else
			textView.setText("Äú»¹Î´µÇÂ¼");
        super.onNewIntent(intent);  
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_main_page, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.loginItem) {
			Intent intent = new Intent();
			intent.setClass(MainPageActivity.this, LoginActivity.class);
			startActivity(intent);
			return true;
		}
		else if (id == R.id.ExitItem) {
			userName = null;
			textView.setText("Äú»¹Î´µÇÂ¼");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
