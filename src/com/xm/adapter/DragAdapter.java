package com.xm.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;





import com.example.socketserialport.R;
import com.xm.Interface.DragGridBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @blog http://blog.csdn.net/xiaanming 
 * 
 * @author xiaanming
 *
 */
public class DragAdapter extends BaseAdapter implements DragGridBaseAdapter{
	private List<HashMap<String, Object>> list;
	private LayoutInflater mInflater;
	private int mHidePosition = -1;
	
	public DragAdapter(Context context, List<HashMap<String, Object>> list){
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 由于复用convertView导致某些item消失了，所以这里不复用item，
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder viewHolder = null;
//		if(convertView==null){
//			viewHolder = new ViewHolder();
//			convertView = mInflater.inflate(R.layout.item_gridview, null);
//			viewHolder.img = (ImageView) convertView.findViewById(R.id.ItemImage);
//			viewHolder.tv = (TextView) convertView.findViewById(R.id.ItemText);
//			convertView.setTag(viewHolder);
//		}
//		viewHolder = (ViewHolder) convertView.getTag();
//		viewHolder.img.setImageResource(Integer.parseInt(list.get(position).get("ItemImage").toString()));
//		viewHolder.tv.setText((CharSequence) list.get(position).get("ItemText"));
		convertView = mInflater.inflate(R.layout.item_gridview, null);
		ImageView mImageView = (ImageView) convertView.findViewById(R.id.ItemImage);
		TextView mTextView = (TextView) convertView.findViewById(R.id.ItemText);
		
		mImageView.setImageResource(Integer.parseInt(list.get(position).get("ItemImage").toString()));
		mTextView.setText((CharSequence) list.get(position).get("ItemText"));
		
		if(position == mHidePosition){
			convertView.setVisibility(View.INVISIBLE);
		}
		
		return convertView;
	}
	
	private class ViewHolder{
		ImageView img;
		TextView tv;
	}

	@Override
	public void reorderItems(int oldPosition, int newPosition) {
		HashMap<String, Object> temp = list.get(oldPosition);
		if(oldPosition < newPosition){
			for(int i=oldPosition; i<newPosition; i++){
				Collections.swap(list, i, i+1);
			}
		}else if(oldPosition > newPosition){
			for(int i=oldPosition; i>newPosition; i--){
				Collections.swap(list, i, i-1);
			}
		}
		
		list.set(newPosition, temp);
	}

	@Override
	public void setHideItem(int hidePosition) {
		this.mHidePosition = hidePosition; 
		notifyDataSetChanged();
	}


}
