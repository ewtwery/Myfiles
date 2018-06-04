package com.xm.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.AlteredCharSequence;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socketserialport.R;
import com.xm.Widget.CustomadeDialog;
import com.xm.var.StaticVar;

public class StandardUserareaListView extends ListActivity {
	private ListView list;
	private Toast toast;
	private CustomadeDialog alertDialog;
	CustomadeDialog dialog;
	private SharedPreferences sharedPreferences; // 私有数据
	private String user1;
	private String user2;
	private String user3;
	private String user4;
	private String user5;
	private String user6;
	private String user7;
	private String user8;
	private TextView title;
	private Button btn_title;
	String checkedTxt;
	ProgressDialog loadProgressDialog;
	boolean flag=true;

	// private List<String> data = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_standarduserarealistview);
		/*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
//		Tools.removeStatusBarAndNavigationBar(this);
		Home.TheActivityIs = "StandardUserareaListView";
		initView();
		registerReciver();
		
	}
	public void home(View v){
		finish();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}
	private void registerReciver() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
        filter.addAction(StaticVar.FINISH_ACTIVITY);
        registerReceiver(broadcastReceiver, filter);
	}
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            StandardUserareaListView.this.finish();
        }
    };
	private void initView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		btn_title = (Button) findViewById(R.id.actionbar_title);
		title.setText("用户列表");
		btn_title.setText("主页");
		btn_title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		list = getListView();
		list.setBackgroundResource(R.drawable.backg);
		list.setPadding(10, 10, 10, 10);
		list.setCacheColorHint(00000000);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.item_standarduserarealistview, new String[] { "moren", "title" }, new int[] {
						R.id.moren, R.id.name });
		setListAdapter(adapter);
		sharedPreferences = getSharedPreferences("username",
				Context.MODE_PRIVATE);
		user1 = sharedPreferences.getString("user1", "默认");
		user2 = sharedPreferences.getString("user2", "默认");
		user3 = sharedPreferences.getString("user3", "默认");
		user4 = sharedPreferences.getString("user4", "默认");
		user5 = sharedPreferences.getString("user5", "默认");
		user6 = sharedPreferences.getString("user6", "默认");
		user7 = sharedPreferences.getString("user7", "默认");
		user8 = sharedPreferences.getString("user8", "默认");
		UpdateList(0, user1);
		UpdateList(1, user2);
		UpdateList(2, user3);
		UpdateList(3, user4);
		UpdateList(4, user5);
		UpdateList(5, user6);
		UpdateList(6, user7);
		UpdateList(7, user8);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView text = (TextView) arg1.findViewById(R.id.moren);
				TextView text2 = (TextView) arg1.findViewById(R.id.name);
				checkedTxt = text.getText().toString()+text2.getText().toString();
				alertDialog = (CustomadeDialog) popDialog(arg1,arg2);
				alertDialog.show();
				
				return true;
			}

		});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				if(flag){
				loadProgressDialog = ProgressDialog.show(StandardUserareaListView.this, "正在加载...",
						"请稍候...");
				
				TextView text = (TextView) view.findViewById(R.id.moren);
				TextView text2 = (TextView) view.findViewById(R.id.name);
				String s = text.getText().toString()+text2.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("name", s);
				intent.putExtra("fromactivity", "StandardUserareaListView");
				intent.putExtra("usernumber", position);
				
				intent.setClass(StandardUserareaListView.this,
						Standard_RuntimeView.class);
				startActivity(intent);}
				flag = false;
			}
		});
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("moren", "用户1-");
		map.put("title", "默认");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("moren", "用户2-");
		map.put("title", "默认");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("moren", "用户3-");
		map.put("title", "默认");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("moren", "用户4-");
		map.put("title", "默认");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("moren", "用户5-");
		map.put("title", "默认");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("moren", "用户6-");
		map.put("title", "默认");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("moren", "用户7-");
		map.put("title", "默认");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("moren", "用户8-");
		map.put("title", "默认");
		list.add(map);

		return list;
	}

	public void UpdateList(int selectedItem, String name) {
		ListView m_List = getListView();
		ListAdapter la = m_List.getAdapter();
		int itemNum = m_List.getCount();
		for (int i = 0; i < itemNum; i++) {
			HashMap<String, Object> map = (HashMap<String, Object>) la
					.getItem(i);
			if (i == selectedItem) {
				map.put("title", name);
			}

		}

		((SimpleAdapter) la).notifyDataSetChanged();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 1:
			Bundle bundle = data.getExtras();
			String s = bundle.getString("namechanged");
			UpdateList(requestCode, s);
			sharedPreferences = getSharedPreferences("username",
					Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();// 获取编辑器
			editor.putString("user" + (requestCode + 1), s);
			editor.commit();
			
			for(int i = 0; i<Home.gridviewList.size();i++){
				if(Home.gridviewList.get(i).containsValue(checkedTxt)){
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("ItemImage",
							String.valueOf(R.drawable.standardexe));
					map.put("ItemText", checkedTxt.substring(0, 4)+s);
					Home.gridviewList.set(i, map);
					Home.gridViewAdapter.notifyDataSetChanged();
				}
			}
			break;
		}
	}

	public void showTextToast(String msg) {
		if (toast == null) {
			toast = Toast.makeText(getApplicationContext(), msg,
					Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	public CustomadeDialog popDialog(final View arg1, final int arg2) {
		CustomadeDialog.Builder builder = new CustomadeDialog.Builder(this);
		ListView view = new ListView(this);
		SimpleAdapter adapter = new SimpleAdapter(this, getdata(), android.R.layout.simple_list_item_1, new String[]{"action"}, new int[]{android.R.id.text1});
		view.setAdapter(adapter);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					TextView text = (TextView) arg1
							.findViewById(R.id.name);
					String s = text.getText().toString();
					dialog = changenameDialog(s,arg2);
					dialog.show();
					
					alertDialog.dismiss();
					break;
				case 1:
					boolean key = false;
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("ItemImage",
							String.valueOf(R.drawable.standardexe));
					map.put("ItemText",
							"用户"
									+ (arg2 + 1)
									+ "-"
									+ sharedPreferences.getString(
											"user" + (arg2 + 1), "默认"));
					for (int i = 0; i < Home.gridviewList
							.size(); i++) {
						//if (MainActivity.serialport.gridviewList.get(i).containsValue("用户"+ (arg2 + 1)+ "-"+ sharedPreferences.getString("user"+ (arg2 + 1),"默认"))) {
							if (Home.gridviewList.get(i).get("ItemText").toString().contains("用户"+ (arg2 + 1))) {
							key = true;
							showTextToast("该用户快捷方式已存在！");
							break;
						}
					}
					if (key == false) {
						Home.gridviewList.add(map);
						Home.gridViewAdapter.notifyDataSetChanged();
						new Home().saveDrop(StandardUserareaListView.this);
					}
//					Tools.removeStatusBarAndNavigationBar(StandardUserareaListView.this);
					alertDialog.dismiss();
					break;
				}
			}
		});
		builder.setTitle("您要做的是？")
		.setContentView(view)
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				// popupWindow.dismiss();
//				Tools.removeStatusBarAndNavigationBar(StandardUserareaListView.this);
				alertDialog.dismiss();
			}
		});
		builder.setCancelable(false);
		return builder.create();

	}
	
	private CustomadeDialog changenameDialog(String str,final int arg){
		LayoutInflater inflater = getLayoutInflater();
		final EditText editText = new EditText(this);
		editText.setBackgroundResource(R.drawable.edittext_bac);
		editText.setText(str);
		CustomadeDialog.Builder builder = new CustomadeDialog.Builder(this);
		builder.setTitle("请输入").setContentView(editText).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String s = editText.getText().toString();
				UpdateList(arg, s);
				sharedPreferences = getSharedPreferences("username",
						Context.MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();// 获取编辑器
				editor.putString("user" + (arg + 1), s);
				editor.commit();
				
				for(int i = 0; i<Home.gridviewList.size();i++){
					if(Home.gridviewList.get(i).containsValue(checkedTxt)){
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("ItemImage",
								String.valueOf(R.drawable.standardexe));
						map.put("ItemText", checkedTxt.substring(0, 4)+s);
						Home.gridviewList.set(i, map);
						Home.gridViewAdapter.notifyDataSetChanged();
					}
				}
				dialog.dismiss();
//				Tools.removeStatusBarAndNavigationBar(StandardUserareaListView.this);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
//				Tools.removeStatusBarAndNavigationBar(StandardUserareaListView.this);
			}
		});
		
		
		return builder.create();
		
	}
	
	private List<Map<String,String>> getdata() {
		// TODO Auto-generated method stub
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		Map<String, String> map1 = new HashMap<String, String>();  
        map1.put("action", "重命名");                 
        data.add(map1);  
        Map<String, String> map2 = new HashMap<String, String>();  
        map2.put("action", "添加到桌面快捷方式");  
        data.add(map2);  
		return data;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		Tools.removeStatusBarAndNavigationBar(StandardUserareaListView.this);
		flag = true;
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if(toast!=null)
			toast.cancel();
		super.onStop();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(loadProgressDialog!=null){
			loadProgressDialog.dismiss();
			loadProgressDialog=null;
		}
			
		super.onPause();
	}

}
