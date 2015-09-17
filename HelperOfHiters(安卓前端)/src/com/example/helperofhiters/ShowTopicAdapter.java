package com.example.helperofhiters;

import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ShowTopicAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String,String>> data;
	ShowTopicAdapter(Context context,List<Map<String,String>> data)
	{
		this.context = context;
		this.data = data;
	}
	public void changeMessage(int position,Map<String,String> item)
	{
		data.set(position, item);
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data==null)
			return 0;
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return Integer.parseInt(data.get(arg0).get("id"));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TopicItem item;
		if(convertView==null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.listitem_topic, null);
			item = new TopicItem();
			item.Title = (TextView)convertView.findViewById(R.id.TopicTitleView);
			item.Text = (TextView)convertView.findViewById(R.id.TopicTextView);
			item.Part = (TextView)convertView.findViewById(R.id.TopicPartView);
			item.Date = (TextView)convertView.findViewById(R.id.TopicDateView);
			item.Author = (TextView)convertView.findViewById(R.id.TopicAuthorView);
			convertView.setTag(item);
		}
		else
		{
			item = (TopicItem)convertView.getTag();
		}
		String title = data.get(position).get("Title");
		if(title!=null)
		{
			item.Title.setText(title);
			item.Title.setTextSize(40f);
			item.Text.setTextSize(30f);
		}
		else if(data.get(position).get("Recommand").equals("true"))
		{
			item.Title.setText("ÂúÒâ´ð°¸");
			item.Title.setTextSize(30f);
		}
		else
		{
			item.Title.setText("");
			item.Title.setTextSize(0f);
		}
		String part = data.get(position).get("Part");
		if(part!=null)
			item.Part.setText(part);
		else
			item.Part.setText("");
		item.Text.setText(data.get(position).get("Text"));
		item.Date.setText(data.get(position).get("Date").substring(0, 10));
		item.Author.setText(data.get(position).get("Author"));
		return convertView;
	}
	public class TopicItem
	{
		TextView Title;
		TextView Text;
		TextView Part;
		TextView Date;
		TextView Author;
	}
}
