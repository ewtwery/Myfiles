package com.xm.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.socketserialport.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridviewAdapter extends BaseAdapter {

	private ArrayList<HashMap<String, Object>> arrayList;
	private int resourceID;
	private View view;
	private LayoutInflater mInflater = null;
	public GridviewAdapter(Context context, ArrayList<HashMap<String, Object>> arrayList, int resourceID){
		mInflater = LayoutInflater.from(context);
		this.arrayList = arrayList;
		this.resourceID = resourceID;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			view = mInflater.inflate(resourceID, null,false);
			holder = new ViewHolder();
			holder.imageView = (ImageView)view.findViewById(R.id.ItemImage);
			holder.textView = (TextView)view.findViewById(R.id.ItemText);
			view.setTag(holder);
			
		}else{
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		holder.imageView.setImageResource(Integer.valueOf((String) arrayList.get(position).get("ItemImage")));
		holder.textView.setText((String)arrayList.get(position).get("ItemText"));
		return view;
	}
	
	static class ViewHolder{
		ImageView imageView;
		TextView textView;
	}

}
