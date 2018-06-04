package com.xm.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.socketserialport.R;
import com.xm.Model.ChildData;
import com.xm.Model.GroupData;
import com.xm.activity.Home;

public class ExpandableAdapter extends BaseExpandableListAdapter {

	private Context context;
	public List<GroupData> groupdata;
	public List<List<ChildData>> childdata;
	private GroupData gdata;
	private ChildData cdata;
	private View tempView;
	private LayoutInflater mInflater = null;
	private ChildViewHolder childHolder;
	private GroupViewHolder groupHolder;
	private OnClickListener onclickListener;

	public ExpandableAdapter(Context context, List<GroupData> groupdata,
			List<List<ChildData>> childdata, OnClickListener clickListener) {
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.groupdata = groupdata;
		this.childdata = childdata;
		this.onclickListener = clickListener;
	}

	public void addChild(int groupIndex, ChildData cdata) {
		if (childdata.get(groupIndex) == null) {
			ArrayList<ChildData> tempDatas = new ArrayList<ChildData>();
			childdata.set(groupIndex, tempDatas);
		}
		childdata.get(groupIndex).add(cdata);

	}

	public void addChild(int groupIndex, int childIndex, ChildData cdata) {
		if (childdata.get(groupIndex) == null) {
			ArrayList<ChildData> tempDatas = new ArrayList<ChildData>();
			childdata.set(groupIndex, tempDatas);
		}
		childdata.get(groupIndex).add(childIndex, cdata);

	}

	public void setChild(int groupIndex, int childIndex, ChildData cdata) {
		childdata.get(groupIndex).set(childIndex, cdata);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupdata.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (childdata != null) {
			if (childdata.size() > groupPosition
					&& childdata.get(groupPosition) != null) {
				childdata.get(groupPosition).size();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
		// TODO Auto-generated method stub
		return (childdata != null) ? childdata.get(groupPosition).size() : 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupdata.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childdata.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView != null) {
			tempView = convertView;
		} else {
			tempView = mInflater.inflate(R.layout.item_expandablelistview_group, null);
		}
		groupHolder = (GroupViewHolder) tempView.getTag();
		if (groupHolder == null) {
			groupHolder = new GroupViewHolder();
			groupHolder.flag = 1;
			groupHolder.name = (TextView) tempView.findViewById(R.id.function);
			groupHolder.add_child = (Button) tempView
					.findViewById(R.id.add_child);
			tempView.setTag(groupHolder);
		}
//		System.out.println("setTag:"+String.valueOf(groupPosition));
		groupHolder.add_child.setTag(String.valueOf(groupPosition));
		groupHolder.add_child.setOnClickListener(onclickListener);
		gdata = groupdata.get(groupPosition);
		groupHolder.name.setText(gdata.getName());
		return tempView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView != null) {
			tempView = convertView;
		} else {
			tempView = mInflater.inflate(R.layout.item_expandablelistview_child, parent, false);
		}
		childHolder = (ChildViewHolder) tempView.getTag();
		if (childHolder == null) {
			childHolder = new ChildViewHolder();
			childHolder.no = (TextView) tempView.findViewById(R.id.no);
			childHolder.spinner1TV = (TextView) tempView
					.findViewById(R.id.spinner1);
			childHolder.spinner2TV = (TextView) tempView
					.findViewById(R.id.spinner2);
			childHolder.vibVelTV = (TextView) tempView
					.findViewById(R.id.vibVel);
			childHolder.vibAmpTV = (TextView) tempView
					.findViewById(R.id.vibAmp);
			childHolder.vibBottom = (TextView) tempView
					.findViewById(R.id.vibBottom);
			childHolder.minTV = (TextView) tempView.findViewById(R.id.min);
			childHolder.linkageVelTV = (TextView) tempView
					.findViewById(R.id.linkageVel);
			tempView.setTag(childHolder);
		}
		cdata = childdata.get(groupPosition).get(childPosition);
		childHolder.no.setText(getChildId(groupPosition, childPosition)+1+"");
		childHolder.spinner1TV.setText(cdata.getSpinner1_());
		childHolder.spinner2TV.setText(cdata.getSpinner2_());
		childHolder.vibVelTV.setText(cdata.getVibVel_());
		childHolder.vibAmpTV.setText(cdata.getVibAmp_());
		childHolder.vibBottom.setText(cdata.getVibBottom_());
		childHolder.minTV.setText(cdata.getMin_());
		childHolder.linkageVelTV.setText(cdata.getLinkageVel_());
//		System.out.println("checkflag:"+cdata.isCheckflag());
		if (cdata.isCheckflag()) {
			tempView.setBackgroundColor(0xFF9ACD32);
		} else {
			tempView.setBackgroundColor(0xFFE8C89D);
		}
		return tempView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
		try {
			if(Home.messageJsonObject==null){
				Home.messageJsonObject = new JSONObject();
				try {
					Home.messageJsonObject.put("machinetype", "pnae32");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			Home.messageJsonObject.put("messagetype", "synchronous");
			Home.messageJsonObject.put("paramstype", "nonstandard");
			
			JSONArray array = new JSONArray();
			JSONObject jsonObject;
			if(groupdata!=null)
			for (int i = 0; i < groupdata.size(); i++) {
				array.put(groupdata.get(i).getName());
			}
			Home.messageJsonObject.put("paramsgroup", array);
			
			JSONArray childarray = new JSONArray();
			if(childdata!=null&&childdata.size()>0)
			for (int i = 0; i < childdata.size(); i++) {
				array = new JSONArray();
				if(childdata.get(i)!=null&&childdata.get(i).size()>0)
				for (int j = 0; j < childdata.get(i).size(); j++) {
					jsonObject = new JSONObject();
					 jsonObject.put("工位", childdata.get(i).get(j).getSpinner1_());
					 jsonObject.put("动作", childdata.get(i).get(j).getSpinner2_());
					 jsonObject.put("振动速度", childdata.get(i).get(j).getVibVel_());
					 jsonObject.put("振动幅度", childdata.get(i).get(j).getVibAmp_());
					 jsonObject.put("振动起始位", childdata.get(i).get(j).getVibBottom_());
					 jsonObject.put("时间", childdata.get(i).get(j).getMin_());
					 jsonObject.put("联动速度", childdata.get(i).get(j).getLinkageVel_());
					 
//					 System.out.println(childdata.get(i).get(j).getSpinner1_());
//					 System.out.println(childdata.get(i).get(j).getSpinner2_());
//					 System.out.println(childdata.get(i).get(j).getVibVel_());
//					 System.out.println(childdata.get(i).get(j).getVibAmp_());
//					 System.out.println(childdata.get(i).get(j).getMin_());
//					 System.out.println(childdata.get(i).get(j).getLinkageVel_());
					 array.put(jsonObject);
				}
				childarray.put(array);
				
			}
			Home.messageJsonObject.put("paramschild", childarray);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ChildViewHolder {
		TextView no;
		TextView spinner1TV;
		TextView spinner2TV;
		TextView vibVelTV;
		TextView vibAmpTV;
		TextView vibBottom;
		TextView minTV;
		TextView linkageVelTV;
	}
	
	public class GroupViewHolder {
		public int flag = 0;
		public TextView name;
		public Button add_child;
	}

}
