package com.example.helperofhiters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowPartListActivity extends ActionBarActivity{
	private ListView listview;
	private String userName;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_part_list);
		userName = getIntent().getStringExtra("UserName");
		listview = (ListView)findViewById(R.id.listView1);
		listview.setAdapter(new ArrayAdapter<String>(this,R.layout.listitem_part,NameListOfPart.list));
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent().setClass(ShowPartListActivity.this, ShowTopicListActivity.class);
				intent.putExtra("UserName", userName);
				intent.putExtra("Method", "Part");
				intent.putExtra("Part", NameListOfPart.list[arg2]);
				startActivity(intent);
			}
		});
	}
}
