package com.xm.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socketserialport.R;
import com.xm.Interface.OnRefreshListener;
import com.xm.Widget.CustomadeDialog;
import com.xm.Widget.CustomadeToast;
import com.xm.Widget.RefreshListView;
import com.xm.tools.WifiAdmin;
import com.xm.var.StaticVar;


public class Wifi extends Activity implements OnClickListener {

	private WifiManager wifiManager;

	private WifiInfo currentWifiInfo;// 当前所连接的wifi

	private List<ScanResult> wifiList;// wifi列表


	private String wifiSSID;

	private ProgressDialog progressDialog;

	boolean flag = true;

	Timer timer;

	private TextView title;
	private Button btn_title;
	CustomadeDialog dialog;

	RefreshListView listview;
	SimpleAdapter adapter;
	List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
	List<Map<String, Object>> cloneResultList = new ArrayList<Map<String,Object>>();

	SharedPreferences sharedpreferences;
	Editor editor;

	CustomadeToast toast;
	String tempPassword;
	private boolean item_click=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_wifi);
		Home.TheActivityIs = "WifiActivity";
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		openWifi();
		setupViews();
		init();
		ScanWifiThread scanWifiThread = new ScanWifiThread();
		scanWifiThread.start();
	}

	

	public void setupViews() {
		listview = (RefreshListView) findViewById(R.id.listview);
		title = (TextView) findViewById(R.id.title);
		btn_title = (Button) findViewById(R.id.actionbar_title);
		title.setText("网络设置");
		btn_title.setText("主页");
		btn_title.setOnClickListener(this);

		sharedpreferences = getSharedPreferences("wifiinfo",
				Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		
		resultList = new ArrayList<Map<String,Object>>();
	}

	/**
	 * 打开wifi
	 */
	public void openWifi() {
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
	}

	/**
	 * 扫描wifi线程
	 * 
	 * @author passing
	 * 
	 */
	class ScanWifiThread extends Thread {

		@Override
		public void run() {

			currentWifiInfo = wifiManager.getConnectionInfo();
			refreshListView();
			

		}
	}

	/**
	 * 扫描wifi
	 */
	public void startScan() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  
        State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();  
		Map<String, Object> map;
		wifiManager.startScan();
		// 获取扫描结果
		wifiList = wifiManager.getScanResults();
		String tempStr = null;
		if(wifiList!=null)
		for (int i = 0; i < wifiList.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("ssid", wifiList.get(i).SSID);
			int level = wifiList.get(i).level;
			if (level <= 0 && level >= -50) {
				map.put("signal_strength", R.drawable.strenght1);
			} else if (level < -50 && level >= -70) {
				map.put("signal_strength", R.drawable.strenght2);
			} else if (level < -70 && level >= -80) {
				map.put("signal_strength", R.drawable.strenght3);
			} else if (level < -80 && level >= -100) {
				map.put("signal_strength", R.drawable.strenght4);
			}
			if(currentWifiInfo != null)
			if(currentWifiInfo.getSSID().replace("\"", "").equals(wifiList.get(i).SSID)&&(wifiState==State.CONNECTED)){
			map.put("state", R.drawable.selected);
//			System.out.println(currentWifiInfo.getSSID());
//			System.out.println("信号强度"+wifiList.get(i).level);
			}else{
				map.put("state", R.drawable.not_selected);
			}
			resultList.add(map);
			
		}
		
	}

	/**
	 * 弹出框 查看扫描结果
	 */
	public void init() {

		adapter = new SimpleAdapter(this, cloneResultList,
				R.layout.item_wifilistview, new String[] { "ssid",
						"signal_strength", "state" }, new int[] { R.id.ssid,
						R.id.signal_strength, R.id.state });
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position>0)
				if(item_click){
					item_click =false;
					TextView textview = (TextView) view.findViewById(R.id.ssid);
					wifiSSID = textview.getText().toString();
					handler.sendEmptyMessage(3);
				}
			}
		});
		
		listview.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				refreshListView();
				
			}
			
			
						
						
					
		});
	}


	/**
	 * 获取网络ip地址
	 * 
	 * @author passing
	 * 
	 */
	class RefreshSsidThread extends Thread {

		@Override
		public void run() {
			flag = true;
			while (flag) {
				currentWifiInfo = wifiManager.getConnectionInfo();
				if (null != currentWifiInfo.getSSID()
						&& 0 != currentWifiInfo.getIpAddress()) {
					handler.sendEmptyMessage(4);
					if (timer != null) {
						timer.cancel();
						timer = null;
					}
					if (null != progressDialog) {
						progressDialog.dismiss();
						progressDialog = null;
					}
					flag = false;
				}/*
				 * else{ handler.sendEmptyMessage(1);
				 * +"    "+currentWifiInfo.getIpAddress()); flag = false; }
				 */
				
			}
		}

	}

	/**
	 * 连接网络
	 * 
	 * @param index
	 * @param password
	 */
	public void connetionConfiguration(String ssid, String password) {
		progressDialog = ProgressDialog.show(Wifi.this, "正在连接...", "请稍候...");
		if(timer==null){
			timer = new Timer();
			timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(1);
			}
		}, 20000);}
		new ConnectWifiThread().execute(ssid, password);
	}

	/**
	 * 连接wifi
	 * 
	 * @author passing
	 * 
	 */
	class ConnectWifiThread extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// 连接配置好指定ID的网络
			WifiAdmin admin = new WifiAdmin(getApplicationContext());
			WifiConfiguration config = admin.createWifiInfo(params[0],
					params[1], 3);

			int networkId = wifiManager.addNetwork(config);
			if ((null != config) && (networkId != -1)) {
				boolean b = wifiManager.enableNetwork(networkId, true);

				return params[0];
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			if (null != result) {
				handler.sendEmptyMessage(0);

			}
			if (null == result) {
				if(progressDialog!=null&&progressDialog.isShowing())
				progressDialog.setMessage("连接失败！检查网络状况和密码");
				handler.sendEmptyMessageDelayed(1, 3000);
			}
		}

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if(progressDialog!=null&&progressDialog.isShowing())
				progressDialog.setMessage("正在连接...");
				new RefreshSsidThread().start();
				if(timer!=null)
					timer.cancel();
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						flag = false;
						handler.sendEmptyMessageDelayed(1, 3000);
						runOnUiThread(new Runnable() {
							public void run() {
								if(progressDialog!=null&&progressDialog.isShowing())
								progressDialog.setMessage("请检查密码和网络状况！");
							}
						});

					}
				}, 15000);
				break;
			case 1:
				SharedPreferences preferences = getSharedPreferences("wifiinfo",
						Context.MODE_PRIVATE);
				if (preferences.getString("ssid", null) != null
				&& preferences.getString("password", null) != null) {
			WifiAdmin wifiAdmin = new WifiAdmin(getApplicationContext());
			wifiAdmin.openWifi();

			WifiConfiguration config = wifiAdmin.createWifiInfo(
					preferences.getString("ssid", null),
					preferences.getString("password", null), 3);

			wifiAdmin.addNetwork(config);
//			new ConnectWifiThread().execute(preferences.getString("ssid", null),
//					preferences.getString("password", null));
		}
				Toast.makeText(Wifi.this, "连接失败！", Toast.LENGTH_SHORT).show();
//				refreshListView();
				ScanWifiThread scanWifiThread = new ScanWifiThread();
				scanWifiThread.start();
				if (null != progressDialog) {
					progressDialog.dismiss();
					progressDialog=null;
				}
				if(timer!=null){
					timer.cancel();
					timer=null;
				}
				
				break;
			case 3:
				final EditText passwordText = new EditText(Wifi.this);
				passwordText.setBackgroundResource(R.drawable.edittext_bac);
				CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
						Wifi.this);
				builder.setTitle("请输入密码").setContentView(passwordText);
				builder.setPositiveButton("连接",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								connetionConfiguration(wifiSSID, passwordText
										.getText().toString());
								tempPassword = passwordText.getText().toString();
								item_click=true;
							}
						})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										item_click=true;
									}
								}).create().show();
				break;
			case 4:
				editor.putString("ssid",
						currentWifiInfo.getSSID().replace("\"", ""));
				editor.putString("password",tempPassword);
				editor.commit();
				Toast.makeText(Wifi.this, "连接成功！", Toast.LENGTH_SHORT).show();
				refreshListView();

				break;
			case 5:
				toast = CustomadeToast.MakeText(
						Wifi.this, "已登录到服务器", true);
				break;
			case 6:
				toast = CustomadeToast.MakeText(Wifi.this,
						"登录服务器失败", true);
				break;
				
			case StaticVar.REFRESH_WIFI_LIST:
				listview.startRefreshAnim();
				break;
			}
		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.actionbar_title:
			finish();
			break;
		}
	}
	public void home(View v){
		finish();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (toast != null)
			toast.dismiss();
		super.onStop();
	}

	private void refreshListView() {
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				resultList.clear();
				startScan();
				Message.obtain(handler, StaticVar.REFRESH_WIFI_LIST).sendToTarget();
				SystemClock.sleep(2000);
				cloneResultList.clear();
				cloneResultList.addAll(resultList);
				return null;
			}
			
			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				// 隐藏头布局
				listview.onRefreshFinish();
				
			}
		}.execute(new Void[]{});
	}

}
