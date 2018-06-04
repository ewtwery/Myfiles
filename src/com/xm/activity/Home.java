package com.xm.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socketserialport.R;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.xm.Bean.ContentBean;
import com.xm.Bean.MessageBean;
import com.xm.Bean.ProgramBean;
import com.xm.Bean.ReplyBean;
import com.xm.Bean.UserBean;
import com.xm.Widget.CustomadeDialog;
import com.xm.Widget.CustomadeToast;
import com.xm.Widget.DragGridView;
import com.xm.adapter.DragAdapter;
import com.xm.mina.Client;
import com.xm.mina.ClientCallBack;
import com.xm.mina.RequestCallBack;
import com.xm.thread.GetTemperature;
import com.xm.thread.ReLoginThread;
import com.xm.tools.Dwin;
import com.xm.tools.Serialport;
import com.xm.tools.Tools;
import com.xm.tools.WifiAdmin;
import com.xm.var.StaticVar;

public class Home extends Activity implements OnClickListener {

	public static String MACHINEID="1708HT020200";
	public static State currentState = State.UNKNOWN;

	static Activity currentActivity;
	static ArrayList<HashMap<String, Object>> gridviewList; // 用来保存gridview数据
	static DragAdapter gridViewAdapter;
	static DragGridView gridview;
	public Intent intent;
	CustomadeToast toast;
	private ListView slidingmenulist;
	public static Handler mainHandler;
//    public static ProgramManageListAdapter manageAdapter;
	int count = 0;
	static Socket socket = null;
	public static String TheActivityIs = null;
	SharedPreferences sharedPreferences;
	CustomadeDialog griviewDialog;
	Timer heatbeattimer;
	Timer uvtimer;
	int pagenumber;

	protected ImageButton connect;

	protected Timer checktimer;
	public static GetTemperature gettemperature;

	Boolean start_flag = false, pauseFlag = false;
	long timercount = 0, timeSet = 0;

	public static String real_temp;
	public static JSONObject messageJsonObject;
	int m = 0;
	private State wifiState;

	public static Serialport serialport;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 */

		setContentView(R.layout.layout_main);
		TheActivityIs = "Home";
		SharedPreferences machineinfo = getSharedPreferences("machineinfo",
				Context.MODE_PRIVATE);
		MACHINEID = machineinfo.getString("MACHINEID", "1708HT0202001");
		initMainHandler();
		initView();
		initSerialPort();
		clearFlagSharedPrefenence();

//		manageAdapter = new ProgramManageListAdapter(this, getFilesDir());
		
		gettemperature = new GetTemperature();
		gettemperature.start();
		
		// System.out.println("getChipID>>>"+Dwin.getInstance().getChipID());
		
//		System.out.println("Client.getInstance().isNetworkAvailable(getApplicationContext())   "+Client.getInstance().isNetworkAvailable(getApplicationContext()));
		if(Client.getInstance().isNetworkAvailable(getApplicationContext()))
			socketConnect();
	}
	

	void putParamsToFile(){
//		SharedPreferences blending_param = getSharedPreferences("blending",Context.MODE_PRIVATE);
//		Editor editor = blending_param.edit();
//		editor.putInt("temp", paramsBean.getTemperature());
//		editor.putInt("speed", paramsBean.getSpeed());
//		editor.putInt("time", paramsBean.getTime());
//		editor.commit();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		}
	}
	
//	void startTimer(){
//		checktimer = new Timer();
//		checktimer.schedule(new TimerTask() { // 开启心跳包定时检测定时器
//			@Override
//			public void run() {
//				if(++timercount < timeSet){
//					mainHandler.obtainMessage(2, timeSet - timercount).sendToTarget();
//				}else{
//					mainHandler.obtainMessage(3).sendToTarget();
//				}
//			}
//		}, 1000, 1000);
//	}
	
	
	int stringToInt(String str) {
		int res = 0;
		try{
			res = Integer.parseInt(str);
		}catch (Exception e){
		}
        return res;
    }

	/*void setParams(){
		LayoutInflater layoutInflater = LayoutInflater.from(this);
        View editparams_view = layoutInflater.inflate(R.layout.item_params_set, null);
		final EditText edit_param_temp = (EditText) editparams_view.findViewById(R.id.edit_param_temp);
		final EditText edit_param_speed = (EditText) editparams_view.findViewById(R.id.edit_param_speed);
		final EditText edit_param_time = (EditText) editparams_view.findViewById(R.id.edit_param_time);
		edit_param_temp.setText(paramsBean.getTemperature()+"");
		edit_param_speed.setText(paramsBean.getSpeed()+"");
		edit_param_time.setText(paramsBean.getTime()+"");
		
		CustomadeDialog.Builder builder = new CustomadeDialog.Builder(this);
        builder.setTitle("配置参数")
		.setContentView(editparams_view)
		.setCancelable(false)
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						int temperature, speed, time;
						temperature = stringToInt(edit_param_temp.getText().toString());
						if(temperature < 4 || temperature > 99){
							showTextToast("温度设置范围：4-99℃");
							return;
						}
						speed = stringToInt(edit_param_speed.getText().toString());
						if(speed > 1500){
							showTextToast("速度不能高于1500r/min");
							return;
						}
						time = stringToInt(edit_param_time.getText().toString());
						if(time == 0){
							showTextToast("时间不能为0");
							return;
						}
						paramsBean.setTemperature(temperature);
						paramsBean.setSpeed(speed);
						paramsBean.setTime(time);
						putParamsToFile();
						refreshParams();
						dialog.dismiss();
					}
				})
		.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				}).setAutoDismiss(false);
        CustomadeDialog customadeDialog = builder.create();
        customadeDialog.show();
        customadeDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
	}*/

	private void clearFlagSharedPrefenence() {
		// TODO Auto-generated method stub
		SharedPreferences sp = getSharedPreferences("flag", MODE_PRIVATE);
		if (sp != null) {
			sp.edit().clear().commit();
		}
	}

	public void initMainHandler() {
		// TODO Auto-generated method stub
		mainHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case StaticVar.INTERRUPT_PROCESS:

					break;
				case StaticVar.CAPTURE_SCREEN:
					try {
						saveToSD(myShot(Home.TheActivityIs, currentActivity),
								"screenshot.png");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case StaticVar.RELOGIN:
//System.out.println("StaticVar.RELOGIN");
					connect.setImageResource(R.drawable.offline);
					ReLoginThread reconnectionThread = ReLoginThread
							.getInstance();
					reconnectionThread.startReconnectionThread();
					break;
				case StaticVar.LOGIN_SUCCEED:
					Client.getInstance().NewUser(MACHINEID);
					if (ReLoginThread.getReconnectionThread() != null) {
						ReLoginThread.stopReconnectionThread();
					}
					connect.setImageResource(R.drawable.online);
//					if (heatbeattimer != null) {
//						heatbeattimer.cancel();
//						heatbeattimer = null;
//					}
//					heatbeattimer = new Timer();
//					heatbeattimer.schedule(new TimerTask() { // 开启心跳包发送定时器
//
//								@Override
//								public void run() {
//									// TODO Auto-generated method stub
//									try {
//										Home.client.sendFlag(" ");
//									} catch (Exception e) {
//										// TODO: handle exception
//									}
//
//								}
//							}, 0, 20000);
//					if (checktimer != null) {
//						checktimer.cancel();
//					}
//					checktimer = new Timer();
//					checktimer.schedule(new TimerTask() { // 开启心跳包定时检测定时器
//
//								@Override
//								public void run() {
//									// TODO Auto-generated method stub
//									checkcount++;
////									System.out.println("checkcount>>>"
////											+ checkcount);
//									if (checkcount > 1) {
//
//										ReLoginThread reconnectionThread = ReLoginThread
//												.getInstance();
//										reconnectionThread
//												.startReconnectionThread();
//
//										checkcount = 0;
//										checktimer.cancel();
//										heatbeattimer.cancel();
//										checktimer = null;
//										heatbeattimer = null;
//									}
//								}
//							}, 0, 50000);

					break;
				case StaticVar.RESPONSE_COMMAND:
					switch (msg.obj.toString()) {
					case "zw_door_2":
						SharedPreferences preferences = getSharedPreferences(
								"flag", Context.MODE_PRIVATE);
						Editor editor = preferences.edit();
						if (!preferences.getBoolean("uvflag", true))
							for (int i = 0; i < gridviewList.size(); i++) {
								if (gridviewList.get(i).get("ItemText")
										.toString().contains("紫外灯")) {
									if (uvtimer != null)
										uvtimer.cancel();

									gridviewList.get(i).put("ItemImage",
											String.valueOf(R.drawable.uv_off));
									editor.putBoolean("uvflag", true);
									serialport.SendMsg(":tzw=0\r\n");
									serialport.SendMsg(":uuu=3\r\n");
									editor.commit();
									gridViewAdapter.notifyDataSetChanged();
									showTextToast("门被打开，紫外灯自动关闭");
									sendNote(1, true);
									break;
								}
							}

						break;

					default:
						String str = msg.obj.toString();
						if (str.equals("Stop")) {
							preferences = getSharedPreferences("flag",
									Context.MODE_PRIVATE);
							editor = preferences.edit();
							for (int i = 0; i < gridviewList.size(); i++) {
								if (gridviewList.get(i).get("ItemText")
										.toString().contains("紫外灯")) {
									if (Integer.parseInt((String) gridviewList
											.get(i).get("ItemImage")) == R.drawable.uv_on) {
										gridviewList
												.get(i)
												.put("ItemImage",
														String.valueOf(R.drawable.uv_off));
										editor.putBoolean("uvflag", true);
										editor.commit();
										serialport.SendMsg(":tzw=0\r\n");
										serialport.SendMsg(":uuu=3\r\n");
										if (uvtimer != null)
											uvtimer.cancel();
										Home.messageJsonObject = null;
										sendNote(1, true);
										gridViewAdapter.notifyDataSetChanged();
										break;
									}
								}

							}
						}
						break;
					}
					break;
				case StaticVar.UV_TIMER_CANCEL:
					if (uvtimer != null)
						uvtimer.cancel();
					break;
				case StaticVar.SAVE_DROP:
					saveDrop(Home.this);
					break;
				case StaticVar.UPDATE_HINT:
					final String st = msg.obj.toString();
					JSONObject jsonObject;
					String DataBaseVersion = null;
					String tempVersion = null;
					try {
						jsonObject = new JSONObject(st);
						tempVersion = "V" + jsonObject.getString("Version")
								+ ".0";
						DataBaseVersion = "V" + getDataBaseVersion() + ".0";
						if (getDataBaseVersion() == -1)
							DataBaseVersion = "空";

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
							Home.this);
					builder.setCancelable(false)
							.setTitle("提示")
							.setMessage(
									"数据库有最新版本为 " + tempVersion + ",本地数据库版本为 "
											+ DataBaseVersion + " ,是否更新？")
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											// Tools.removeStatusBarAndNavigationBar(Home.this);
										}
									})
							.setPositiveButton("更新",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											Tools.SaveToFile(Home.this, st);
											// Tools.removeStatusBarAndNavigationBar(Home.this);
										}
									}).create().show();
					break;
				case StaticVar.NO_NEED_UPDATE:
					Toast.makeText(Home.this, "数据库是最新的", Toast.LENGTH_LONG)
							.show();
					break;
				case StaticVar.LOGIN:
					LoginThread loginThread = new LoginThread();
					loginThread.start();
					break;
				case StaticVar.LOGOUT:
					connect.setImageResource(R.drawable.offline);
//					if (SocketServerThread.getSocketThread() != null)
//						SocketServerThread.getSocketThread()
//								.stopSocketServerThread();
//					if(client != null)
//						client.StreamClose();
					break;
				case StaticVar.DOWNLOAD_PARAMS:
					if (Tools.CheckVersion(msg.obj.toString(),
							getDataBaseVersion())) {
						mainHandler.obtainMessage(StaticVar.UPDATE_HINT,
								msg.obj.toString()).sendToTarget();

					} else {
						mainHandler.obtainMessage(StaticVar.NO_NEED_UPDATE,
								null).sendToTarget();

					}
					break;
				
				}

			}
		};
	}

	private void initView() {
		intent = new Intent();
		connect = (ImageButton) findViewById(R.id.connect);
		
		
		RxView.clicks(connect).debounce(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer() { //500毫秒内未接收到点击，才会响应点击事件

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onNext(Object arg0) {
				// TODO Auto-generated method stub
				if(!Client.getInstance().isNetWorkAvailable){
		        	showTextToast("网络未连接");
		        	return;
		        }
		        connect.setImageResource(R.drawable.connect1);
				if (ReLoginThread.getReconnectionThread() != null) {
					ReLoginThread.stopReconnectionThread();
				}
				Client.getInstance().closeNow(true);
				LoginThread loginThread = new LoginThread();
				loginThread.start();
			}
		});
		
//		connect.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				  
//		        if(!Client.getInstance().isNetWorkAvailable){
//		        	
//		        	showTextToast("网络未连接");
//		        	return;
//		        }
//				
//		        connect.setImageResource(R.drawable.connect1);
////				if (SocketServerThread.getSocketThread() != null) {
////					SocketServerThread.getSocketThread()
////							.stopSocketServerThread();
////				}
//				if (ReLoginThread.getReconnectionThread() != null) {
//					ReLoginThread.stopReconnectionThread();
//				}
////				if(Client.getInstance().isLogin())
//				Client.getInstance().closeNow(true);
//				LoginThread loginThread = new LoginThread();
//				loginThread.start();
//			}
//		});
		
		
		initGridView();

		sharedPreferences = getSharedPreferences("gridviewlist",
				Context.MODE_PRIVATE);
		for (int i = 0; i < sharedPreferences.getAll().size() / 2; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", sharedPreferences.getAll()
					.get("ItemImage" + i));
			map.put("ItemText", sharedPreferences.getAll().get("ItemText" + i));
			gridviewList.add(map);
		}
		gridViewAdapter.notifyDataSetChanged();

		initSlidingMenu();

		SharedPreferences preferences = getSharedPreferences("wifiinfo",
				Context.MODE_PRIVATE);
		
		if(wifiState!=State.CONNECTED)
		if (preferences.getString("ssid", null) != null
				&& preferences.getString("password", null) != null) {
			WifiAdmin wifiAdmin = new WifiAdmin(this);
			wifiAdmin.openWifi();

			WifiConfiguration config = wifiAdmin.createWifiInfo(
					preferences.getString("ssid", null),
					preferences.getString("password", null), 3);

			wifiAdmin.addNetwork(config);
		}

	}

	private void socketConnect() {
//		System.out.println("开启socketconnect");
		LoginThread loginThread = new LoginThread();
		loginThread.start();
	}

	private void initSerialPort() {
		// TODO Auto-generated method stub
		// 设置数据更新
		serialport = new Serialport(1, 19200, 8, 1, 0);
		serialport.openSerialPort2();
	}

	public void initGridView() {
		// TODO Auto-generated method stub
		gridview = (DragGridView) findViewById(R.id.gridview);

		gridviewList = new ArrayList<HashMap<String, Object>>();
		/*
		 * for(int i = 1;i < 10;i++) { HashMap<String, Object> map = new
		 * HashMap<String, Object>(); map.put("ItemImage", R.drawable.menu5);
		 * map.put("ItemText", ""+i); meumList.add(map); }
		 */
		gridViewAdapter = new DragAdapter(this, gridviewList); // 对应R的Id

		// 添加Item到网格中
		gridview.setAdapter(gridViewAdapter);

		// 添加点击事件
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				griviewDialog = GridViewDialog(arg0, arg2);
				griviewDialog.show();

			}
		});

		gridview.setOnItemLongClickListener(new OnItemLongClickListener() { // gridview长按监听器

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub

				return true;
			}
		});

	}

	void resetCiTao(){
		SharedPreferences preferences = getSharedPreferences("flag",Context.MODE_PRIVATE);
		if (!preferences.getBoolean("citao", true)){
			String str = Tools.packagebag("lll", "7");
			serialport.SendMsg(str);
			Editor editor = preferences.edit();// 获取编辑器
			editor.putBoolean("citao", true);
			editor.commit();
		}
	}
	
	private void initSlidingMenu() {
		// TODO Auto-generated method stub

		slidingmenulist = (ListView) findViewById(R.id.slidingmenulist);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, getdata(),
				R.layout.item_slidingmenu, new String[] { "img", "tv" },
				new int[] { R.id.img, R.id.tv });
		slidingmenulist.setAdapter(simpleAdapter);
		slidingmenulist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
//				case 0:
//					pagenumber=0;
//					resetCiTao();
//					intent.putExtra("title", "标准流程");
//					intent.setClass(Home.this, Yangben.class);
//					startActivity(intent);
//					break;

				//流程管理
				case 0:
					break;
				
//				case 1:
//					pagenumber=1;
//					resetCiTao();
//					intent.setClass(Home.this, StandardUserareaListView.class);
//					Home.this.startActivity(intent);
//					break;
//				case 2:
//					pagenumber=2;
//					resetCiTao();
//					intent.setClass(Home.this, NonStandard_RuntimeView.class);
//					Home.this.startActivity(intent);
//					break;
				case 1:
					if(start_flag){
						showTextToast("请先停止振荡！");
						return;
					}
					intent.setClass(Home.this, Set.class);
					Home.this.startActivity(intent);
					break;
				case 2:

					intent.setClass(Home.this, Wifi.class);
					Home.this.startActivity(intent);

					break;
				case 3:

					intent.setClass(Home.this, AboutUs.class);
					Home.this.startActivity(intent);

					break;
				default:
					break;
				}
			}
		});
	}

	private List<? extends Map<String, ?>> getdata() { // slidingmenulist数据源
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tv", "标准流程");
//		map.put("img", R.drawable.set);
//		list.add(map);

		map = new HashMap<String, Object>();
		map.put("tv", "流程管理");
		map.put("img", R.drawable.set);
		list.add(map);

//		map = new HashMap<String, Object>();
//		map.put("tv", "非标用户区");
//		map.put("img", R.drawable.set);
//		list.add(map);

		map = new HashMap<String, Object>();
		map.put("tv", "配置");
		map.put("img", R.drawable.set);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("tv", "网络设置");
		map.put("img", R.drawable.ic_menu_network);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("tv", "关于");
		map.put("img", R.drawable.ic_menu_about);
		list.add(map);

		return list;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		// 记录登陆标志(是否记住密码、是否自动登陆)
		SharedPreferences sharedPreferences = getSharedPreferences("flag",
				Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();

		sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		if (!sharedPreferences.getBoolean("user_remember", false))
			editor.putString("password", null);
		if (!sharedPreferences.getBoolean("ip_remember", false))
			editor.putString("port", null);
		editor.putString("iplogin", "登陆");
		editor.putString("userlogin", "登陆");
		editor.commit();

		super.onDestroy();
	}

	@Override
	protected void onStop() {
		// TODO 自动生成的方法存根
		saveDrop(this);
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/**
	 * 退出菜单
	 * 
	 * @param context
	 * @return
	 */
	private Dialog ExitDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("系统信息");
		builder.setMessage("确定要退出程序吗?");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		return builder.create();
	}

	/*
	 * public static String getRunningActivityName(Context context) { String
	 * contextString = context.toString(); return
	 * contextString.substring(contextString.lastIndexOf(".") + 1,
	 * contextString.indexOf("@")); }
	 */

	/**
	 * 互不影响的Toast
	 * 
	 * @param msg
	 */
	public void showTextToast(String msg) {
		/*
		 * if (toast == null) { toast = Toast.makeText(getApplicationContext(),
		 * msg, Toast.LENGTH_SHORT); } else { toast.setText(msg); }
		 * toast.show();
		 */
		toast = new CustomadeToast(getApplicationContext(), msg, true);
		toast.show();
	}

	// 覆写back点击事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// System.out.println("menu");
		if (toast != null)
			toast.dismiss();
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ExitDialog(Home.this).show();
		}

		if (keyCode == KeyEvent.KEYCODE_HOME) {
			// System.out.println("home");
		}

		if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		TheActivityIs = "Home";
		currentActivity = Home.this;
		// Tools.removeStatusBarAndNavigationBar(Home.this);
			SharedPreferences preferences = getSharedPreferences("flag",Context.MODE_PRIVATE);
			for (int i = 0; i < gridviewList.size(); i++) {
				if (gridviewList.get(i).get("ItemText").toString().contains("照明")) {
					if (preferences.getBoolean("light", true)||pagenumber==3) {
						gridviewList.get(i).put("ItemImage",
								String.valueOf(R.drawable.light_on));
						gridviewList.get(i).put("ItemText", "照明停止");
					} else {
						gridviewList.get(i).put("ItemImage",
								String.valueOf(R.drawable.light_off));
						gridviewList.get(i).put("ItemText", "照明启动");
					}
				}
	
				if (gridviewList.get(i).get("ItemText").toString().contains("磁套")) {
					if (preferences.getBoolean("light", true)||pagenumber==3) {
					gridviewList.get(i).put("ItemImage",
							String.valueOf(R.drawable.citaodown));
					gridviewList.get(i).put("ItemText", "磁套下降");
					} else {
						gridviewList.get(i).put("ItemImage",
								String.valueOf(R.drawable.citaoup));
						gridviewList.get(i).put("ItemText", "磁套上升");
					}
				}
				
				if (gridviewList.get(i).get("ItemText").toString().contains("紫外灯")) {
	
					if (preferences.getBoolean("uvflag", true)) {
						gridviewList.get(i).put("ItemImage",
								String.valueOf(R.drawable.uv_off));
					} else {
						gridviewList.get(i).put("ItemImage",
								String.valueOf(R.drawable.uv_on));
					}
				}
			}
			gridViewAdapter.notifyDataSetChanged();
		
//		if(MACHINEID==null)
//			MACHINEID = Dwin.getInstance().getChipID().toString();
//		MACHINEID = "17d0141b 1dc01003 03310000 866019af";
//		MACHINEID = "1708HT0202001";
        
		super.onResume();
	}

	public void saveDrop(Context context) {
		// 保存桌面快捷方式源数据
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"gridviewlist", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		for (int i = 0; i < gridviewList.size(); i++) {
			// System.out.println(gridviewList.get(0).get("ItemImage").toString());
			editor.putString("ItemImage" + i,
					gridviewList.get(i).get("ItemImage").toString());
			editor.putString("ItemText" + i, gridviewList.get(i)
					.get("ItemText").toString());
		}
		editor.commit();
	}

	public CustomadeDialog GridViewDialog(final AdapterView<?> arg0,
			final int arg2) {
		CustomadeDialog.Builder builder = new CustomadeDialog.Builder(this);

		ListView view = new ListView(this);
		SimpleAdapter simpleadapter = new SimpleAdapter(this, getdialogdata(),
				android.R.layout.simple_list_item_1, new String[] { "action" },
				new int[] { android.R.id.text1 });
		view.setAdapter(simpleadapter);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg,
					int position, long id) {

				final SharedPreferences preferences = getSharedPreferences(
						"flag", Context.MODE_PRIVATE);
				final Editor editor = preferences.edit();
				final HashMap<String, Object> item = (HashMap<String, Object>) arg0
						.getItemAtPosition(arg2); // item用于"ItemImage","ItemText"对应的值
				// TODO 自动生成的方法存根
				switch (position) {
				case 0:

					// Toast.makeText(MainActivity.this,item.get("ItemText").toString(),
					// 500).show();
					String clickedViewText = item.get("ItemText").toString(); // 被点击的buttonview的文本信息

					/*
					 * if(!isValid(clickedViewText)){ showTextToast("无效快捷方式");
					 * return; }
					 */

					if (Integer.parseInt((String) item.get("ItemImage")) == R.drawable.standardexe) {

						Intent intent = new Intent();
						intent.putExtra("fromactivity", "Home");
						intent.putExtra("name", clickedViewText);
						intent.putExtra("usernumber", Integer
								.parseInt(clickedViewText.substring(2, 3)) - 1);
						intent.setClass(Home.this, Standard_RuntimeView.class);
						startActivity(intent);

					}
					// 非标实验流程
					if (Integer.parseInt((String) item.get("ItemImage")) == R.drawable.nonstandardexe) {

						// 实验流程执行代码块
						Intent intent = new Intent();
						intent.setAction("from shortcut");
						intent.putExtra("name", clickedViewText);
						intent.putExtra("fromactivity", "Home");
						intent.setClass(Home.this,
								NonStandard_RuntimeView.class);
						startActivity(intent);
					}

					if (item.get("ItemText").toString().contains("照明")) {

						for (int i = 0; i < gridviewList.size(); i++) {
							if (gridviewList.get(i).get("ItemText").toString()
									.contains("照明")) {
								if (Integer.parseInt((String) gridviewList.get(
										i).get("ItemImage")) == R.drawable.light_on) {
									gridviewList
											.get(i)
											.put("ItemImage",
													String.valueOf(R.drawable.light_off));
									gridviewList.get(i).put("ItemText", "照明启动");
									editor.putBoolean("light", false);
									serialport.SendMsg(":lll=3\r\n");
								} else {
									gridviewList
											.get(i)
											.put("ItemImage",
													String.valueOf(R.drawable.light_on));
									gridviewList.get(i).put("ItemText", "照明停止");
									editor.putBoolean("light", true);
									serialport.SendMsg(":lll=1\r\n");
								}
								break;
							}
						}
						editor.commit();
						gridViewAdapter.notifyDataSetChanged();
					}

					if (item.get("ItemText").toString().contains("复位")) {
						serialport.SendMsg(":sss=5\r\n");
						for (int i = 0; i < gridviewList.size(); i++) {
							if (gridviewList.get(i).get("ItemText").toString()
									.contains("照明")) {
								gridviewList.get(i).put("ItemImage",
										String.valueOf(R.drawable.light_on));
								gridviewList.get(i).put("ItemText", "照明停止");
								editor.putBoolean("light", true);
							}
							if (gridviewList.get(i).get("ItemText").toString()
									.contains("磁套")) {
								gridviewList.get(i).put("ItemImage",
										String.valueOf(R.drawable.citaodown));
								gridviewList.get(i).put("ItemText", "磁套下降");
								editor.putBoolean("citao", true);
							}
							if (gridviewList.get(i).get("ItemText").toString()
									.contains("紫外灯")) {
								gridviewList.get(i).put("ItemImage",
										String.valueOf(R.drawable.uv_off));
								editor.putBoolean("uvflag", true);
							}
						}
						editor.commit();
						gridViewAdapter.notifyDataSetChanged();
					}

					if (item.get("ItemText").toString().contains("数据库更新")) {
						MessageBean messageBean = Client.getInstance().getDownloadFileRequestBean("machine/snaeii32", "params.txt");
						Client.getInstance().sendRquestForResponse(messageBean, false, new RequestCallBack<MessageBean>() {

							@Override
							public void Response(MessageBean t) {
								// TODO Auto-generated method stub
								try {
									if(Home.TheActivityIs.equals("Set")){
										Message.obtain(Set.handler, StaticVar.DOWNLOAD_PARAMS, new String(t.getContent().getBytecontent(),"utf-8")).sendToTarget();
									}else{
										Message.obtain(Home.mainHandler, StaticVar.DOWNLOAD_PARAMS, new String(t.getContent().getBytecontent(),"utf-8")).sendToTarget();
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
								
							}
						});
					}

					if (item.get("ItemText").toString().contains("磁套")) {

						for (int i = 0; i < gridviewList.size(); i++) {
							if (gridviewList.get(i).get("ItemText").toString()
									.contains("磁套")) {
								if (Integer.parseInt((String) gridviewList.get(
										i).get("ItemImage")) == R.drawable.citaodown) {
									gridviewList.get(i).put("ItemImage",
											String.valueOf(R.drawable.citaoup));
									gridviewList.get(i).put("ItemText", "磁套上升");
									editor.putBoolean("citao", false);
									serialport.SendMsg(":lll=5\r\n");
								} else {
									gridviewList
											.get(i)
											.put("ItemImage",
													String.valueOf(R.drawable.citaodown));
									gridviewList.get(i).put("ItemText", "磁套下降");
									editor.putBoolean("citao", true);
									serialport.SendMsg(":lll=7\r\n");
								}
								break;
							}

						}
						editor.commit();
						gridViewAdapter.notifyDataSetChanged();
					}

					if (item.get("ItemText").toString().contains("紫外灯")) {
						SharedPreferences preference = getSharedPreferences(
								"saveSet", Context.MODE_PRIVATE);
						for (int i = 0; i < gridviewList.size(); i++) {
							if (gridviewList.get(i).get("ItemText").toString()
									.contains("紫外灯")) {
								if (Integer.parseInt((String) gridviewList.get(
										i).get("ItemImage")) == R.drawable.uv_on) {
									gridviewList.get(i).put("ItemImage",
											String.valueOf(R.drawable.uv_off));
									editor.putBoolean("uvflag", true);
									serialport.SendMsg(":tzw=0\r\n");
									serialport.SendMsg(":uuu=3\r\n");
									if (uvtimer != null)
										uvtimer.cancel();
									Home.messageJsonObject = null;
									sendNote(1, true);
								} else {
									try {
										Home.messageJsonObject = new JSONObject();
										Home.messageJsonObject.put(
												"messagetype", "synchronous");
										Home.messageJsonObject.put(
												"paramstype", "config");
										Home.messageJsonObject.put("device",
												"紫外灯");
										Home.messageJsonObject.put(
												"runningstate", "运行");
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									gridviewList.get(i).put("ItemImage",
											String.valueOf(R.drawable.uv_on));
									serialport.SendMsg(Tools.packagebag(
											"tzw",
											Integer.parseInt(preference
													.getString("uvtime",
															30 + ""))
													* 60 + ""));
									serialport.SendMsg(Tools.packagebag("uuu", "1"));
									count = Integer.parseInt(preference
											.getString("uvtime", 30 + "")) * 60;
									try {
										Home.messageJsonObject.put(
												"currentstep", count + "");
										Home.messageJsonObject.put(
												"totalsteps", count + "");
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									editor.putBoolean("uvflag", false);
									uvtimer = new Timer();
									uvtimer.schedule(new TimerTask() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											try {
												Home.messageJsonObject.put(
														"currentstep", count
																+ "");
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
											count--;
											runOnUiThread(new Runnable() {

												@Override
												public void run() {
													// TODO Auto-generated
													// method stub
													if (count == 0) {

														for (int i = 0; i < gridviewList
																.size(); i++) {
															if (gridviewList
																	.get(i)
																	.get("ItemText")
																	.toString()
																	.contains(
																			"紫外灯")) {
																if (Integer
																		.parseInt((String) gridviewList
																				.get(i)
																				.get("ItemImage")) == R.drawable.uv_on) {
																	gridviewList
																			.get(i)
																			.put("ItemImage",
																					String.valueOf(R.drawable.uv_off));
																	editor.putBoolean(
																			"uvflag",
																			true);
																	serialport
																			.SendMsg(":tzw=0\r\n");
																	serialport
																			.SendMsg(":uuu=3\r\n");
																}
															}
														}
														editor.commit();
														gridViewAdapter
																.notifyDataSetChanged();
														uvtimer.cancel();

														sendNote(1, false);
													}
												}
											});
										}
									}, 0, 1000);
								}
							}

						}
						editor.commit();
						gridViewAdapter.notifyDataSetChanged();
					}
					// Tools.removeStatusBarAndNavigationBar(Home.this);
					break;
				case 1:

					CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
							Home.this);
					builder.setCancelable(false)
							.setTitle("提示")
							.setMessage("您要删除这个快捷方式吗？")
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											// Tools.removeStatusBarAndNavigationBar(Home.this);
											dialog.dismiss();
										}
									})
							.setPositiveButton("是的",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											if (item.get("ItemImage")
													.toString()
													.equals(String
															.valueOf(R.drawable.uv_on))) {

												if (uvtimer != null)
													uvtimer.cancel();

												editor.putBoolean("uvflag",
														true);
												editor.commit();
												serialport
														.SendMsg(":tzw=0\r\n");
												serialport
														.SendMsg(":uuu=3\r\n");
												sendNote(1, true);
											}
											gridviewList.remove(arg2);
											gridViewAdapter
													.notifyDataSetChanged();
											// Tools.removeStatusBarAndNavigationBar(Home.this);
											dialog.dismiss();
										}
									}).create().show();
					break;

				default:
					break;
				}
				griviewDialog.dismiss();
			}
		});
		builder.setTitle("您要做的是？").setCancelable(false).setContentView(view)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						// popupWindow.dismiss();
						// Tools.removeStatusBarAndNavigationBar(Home.this);
					}
				});
		return builder.create();

	}

	int getDataBaseVersion() {

		try {
			String line;
			FileInputStream inputStream = openFileInput("DNA&RNA.txt");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));
			StringBuffer sb = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			JSONObject jsonObject = new JSONObject(sb.toString());
			return Integer.parseInt(jsonObject.getString("Version"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	private List<Map<String, String>> getdialogdata() {
		// TODO Auto-generated method stub
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "执行");
		data.add(map);
		map = new HashMap<String, String>();
		map.put("action", "删除");
		data.add(map);
		return data;
	}

	/**
	 * 登录线程
	 * 
	 * @author liuwei
	 *
	 */
	class LoginThread extends Thread {

		@Override
		public synchronized void run() {
			// TODO Auto-generated method stub

			
			MessageBean messageBean = new MessageBean();
			messageBean.setAction("login");
			UserBean from = new UserBean();
			from.setType("machine");
			from.setId(MACHINEID);
			messageBean.setFrom(from);
			Client.getInstance().login(messageBean, new ClientCallBack() {
				
				@Override
				public void onSuccess(int var1, String var2) {
					// TODO Auto-generated method stub
					Message.obtain(mainHandler, StaticVar.LOGIN_SUCCEED)
					.sendToTarget();
				}
				
				@Override
				public void onProgress(int var1, String var2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFaliure(int var1) {
					// TODO Auto-generated method stub
					Message.obtain(mainHandler, StaticVar.RELOGIN)
					.sendToTarget();
				}
			});
//			try {
////				System.out.println("开始登陆");
//				Object[] msg = new Object[1];
//				final ConnctionServer cs = new ConnctionServer(); // 连接服务器
//				msg[0] = MACHINEID;
//				cs.sendFlag("MachineLogin");
//
//				cs.sendMsg(msg); // 发送登陆信息
//				String str = cs.inceptMsg();
////				System.out.println("开始登陆" + str);
//				if (str.equals("permit")) {
//					//
//
//					Home.client = cs;
//					if (SocketServerThread.getSocketThread() != null)
//						SocketServerThread.getSocketThread()
//								.stopSocketServerThread();
//
//					SocketServerThread serverThread = SocketServerThread
//							.getInstance(Home.client.Client_Socket);
//
//					serverThread.startSocketServerThread();
//					Message.obtain(mainHandler, StaticVar.LOGIN_SUCCEED)
//							.sendToTarget();
//
//				} else {
//					Message.obtain(mainHandler, StaticVar.RELOGIN)
//							.sendToTarget();
//				}
//			} catch (Exception e) {
//				Message.obtain(mainHandler, StaticVar.RELOGIN).sendToTarget();
//				// TODO: handle exception
//				// toast = CustomadeToast.MakeText(Home.this, "登录服务器失败", true);
//			}

		}
	}

	public Bitmap myShot(String str, Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.buildDrawingCache();

		// 获取状态栏高度
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeights = rect.top;
		Display display = activity.getWindowManager().getDefaultDisplay();

		// 获取屏幕宽和高
		int width = getWindow().getDecorView().getRootView().getWidth();
		int height = getWindow().getDecorView().getRootView().getHeight();

		// 允许当前窗口保存缓存信息
		view.setDrawingCacheEnabled(true);

		// 去掉状态栏
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, width,
				height);

		// 销毁缓存信息
		view.destroyDrawingCache();

		return bmp;
	}

	private void saveToSD(Bitmap bmp, String fileName) throws IOException {
		// 判断sd卡是否存在
		File filePath = Environment.getExternalStorageDirectory();

		String savePath = filePath + "/" + fileName;
//		System.out.println(savePath);
		File file = new File(savePath);
		// 判断文件是否存在，不存在则创建
		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			if (fos != null) {
				// 第一参数是图片格式，第二个是图片质量，第三个是输出流
				bmp.compress(Bitmap.CompressFormat.PNG, 30, fos);
				// 用完关闭
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void sendNote(int i, boolean beInterrupted) {
		// TODO Auto-generated method stub
		switch (i) {
		case 1:
			try {
//				Object[] objmsg = new Object[3];
//				objmsg[0] = Home.MACHINEID;
//				objmsg[1] = "ALL";

				JSONObject object = new JSONObject();
				object.put("messagetype", "notice");
				object.put("paramstype", "set");
				object.put("paramstitle", "紫外灯");
				if (beInterrupted) {
					object.put("noticecontent", "紫外灯除菌被终止");
				} else {
					object.put("noticecontent", "紫外灯除菌已完成");
				}
//				objmsg[2] = object.toString();
//				Home.client.sendFlag("SendCmd");
//				Home.client.sendMsg(objmsg);
				MessageBean messageBean = new MessageBean();
				messageBean.setAction("SendCmd");
				UserBean from = new UserBean();
				from.setType("machine");
				from.setId(MACHINEID);
				UserBean to = new UserBean();
				to.setId("ALL");
				ContentBean contentBean = new ContentBean();
				contentBean.setStringcontent(object.toString());
				messageBean.setFrom(from);
				messageBean.setTo(to);
				messageBean.setContent(contentBean);
				Client.getInstance().sendRquest(false, messageBean);
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}

			// Home.server.sendMsg("over");
			break;

		default:
			break;
		}

	}

}
