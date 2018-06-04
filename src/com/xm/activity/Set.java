package com.xm.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socketserialport.R;
import com.xm.Bean.ContentBean;
import com.xm.Bean.MessageBean;
import com.xm.Bean.UserBean;
import com.xm.Widget.CustomadeDialog;
import com.xm.Widget.CustomadeToast;
import com.xm.Widget.LoadingDialog;
import com.xm.mina.Client;
import com.xm.mina.RequestCallBack;
import com.xm.tools.SilentInstall;
import com.xm.tools.Tools;
import com.xm.var.StaticVar;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

public class Set extends Activity implements OnClickListener,
		 OnToggleChanged {
	public Button updateparams;
	public Button updateapp;
	Button admin;
	private CustomadeToast toast;
	public static Timer timer;
	private boolean lightstate;
	private boolean citaostate;
	private int UVstate = 0;
	private SharedPreferences sharedPreferences; // 私有数据
	private TextView title;
	private Button btn_title;
	int count = 0;
	private LinearLayout actionbuttonlayout;
	public static ImageButton pause_continue;
	public static ImageButton stop;
	private LinearLayout uvprogresslayout;
	private TextView total;
	private TextView surplus;
	private LinearLayout setlayout;
	private EditText editText;
	private Button confirm;
	private Button cancel;
	TextView textview;
	Intent intent;
	private ToggleButton heat1;
	private ToggleButton heat2;
	private ToggleButton heat3;
	private ToggleButton heat4;
	private ToggleButton heat5;
	private ToggleButton heat6;
	private ToggleButton heat7;
	private ToggleButton heat8;
	private ToggleButton fan;
	static CustomadeDialog UVdialog;
	CustomadeDialog.Builder builder = null;
	int ItemImage = 0;
	String ItemText = null;
	Editor editor;
	ProgressDialog progressDialog = null;
	protected boolean uvIsHsowing = true;
	public static Handler handler;
	LoadingDialog loadingDialog;
	private Timer updateTimer;
	boolean isDownLoaded = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		/*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
		setContentView(R.layout.layout_set);
		registerReciver();
		// Tools.removeStatusBarAndNavigationBar(this);
		init();
		initHandler();
		lightstate = sharedPreferences.getBoolean("light", true);
		citaostate = sharedPreferences.getBoolean("citao", true);

		intent = getIntent();
		if ((intent.getAction()) != null) {
			String str = intent.getAction();
			String temp[] = str.split(";");
			switch (temp[1]) {
			case "Start":
				// if(popupWindow==null)
				// showUVPopuWindow(str);
				/*
				 * new Thread(new Runnable() {
				 * 
				 * @Override public void run() { // TODO Auto-generated method
				 * stub Looper.prepare(); showUVPopuWindow(null); Looper.loop();
				 * } }).start();
				 */
				handler.sendMessageDelayed(Message.obtain(handler, 2, str), 10);
				// Message.obtain(handler, 2, str).sendToTarget();

				break;
			case "Pause":

				break;
			case "Stop":

				break;

			default:
				break;
			}
		}

	}

	private void registerReciver() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction(StaticVar.FINISH_ACTIVITY);
		registerReceiver(broadcastReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}

	public void init() {

		heat1 = (com.zcw.togglebutton.ToggleButton) findViewById(R.id.heat1);
		heat2 = (ToggleButton) findViewById(R.id.heat2);
		heat3 = (ToggleButton) findViewById(R.id.heat3);
		heat4 = (ToggleButton) findViewById(R.id.heat4);
		heat5 = (ToggleButton) findViewById(R.id.heat5);
		heat6 = (ToggleButton) findViewById(R.id.heat6);
		heat7 = (ToggleButton) findViewById(R.id.heat7);
		heat8 = (ToggleButton) findViewById(R.id.heat8);
		fan = (ToggleButton) findViewById(R.id.fan);
		heat1.setOnToggleChanged(this);
		heat2.setOnToggleChanged(this);
		heat3.setOnToggleChanged(this);
		heat4.setOnToggleChanged(this);
		heat5.setOnToggleChanged(this);
		heat6.setOnToggleChanged(this);
		heat7.setOnToggleChanged(this);
		heat8.setOnToggleChanged(this);
		fan.setOnToggleChanged(this);

		sharedPreferences = getSharedPreferences("flag", Context.MODE_PRIVATE);
		updateparams = (Button) findViewById(R.id.updateparams);
		updateapp = (Button) findViewById(R.id.update);
		admin = (Button) findViewById(R.id.admin);
		title = (TextView) findViewById(R.id.title);
		btn_title = (Button) findViewById(R.id.actionbar_title);
		title.setText("配置");
		btn_title.setText("主页");
		updateparams.setOnClickListener(this);
		updateapp.setOnClickListener(this);
		btn_title.setOnClickListener(this);
		admin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		String str = null;
		switch (v.getId()) {
		case R.id.light:
			/*
			 * new Thread(new Runnable() {
			 * 
			 * @Override public void run() { // TODO Auto-generated method stub
			 * try { ObjectOutputStream os;
			 * 
			 * os = new ObjectOutputStream(
			 * SocketServer.socket.getOutputStream()); OperationDao operationDao
			 * = new OperationDao( "振动1", 1, 10,12,12,12,12);
			 * os.writeObject(operationDao); os.flush(); } catch (IOException
			 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
			 * } }).start(); System.out.println("到这了"); OperationDao
			 * operationDao = new OperationDao( "振动1", 2, 5,35,35,35,35);
			 * SocketServer.sendMsg(operationDao);
			 */
		case R.id.confirm:
			if (Home.messageJsonObject == null) {
				Home.messageJsonObject = new JSONObject();
				try {
					Home.messageJsonObject.put("machinetype", "pnae32");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Home.messageJsonObject.put("runningstate", "停止");

				if (editText.getText().toString().equals("")
						|| Integer.parseInt(editText.getText().toString()) <= 0) {
					Toast.makeText(Set.this, "输入有误", Toast.LENGTH_SHORT).show();
				} else {
					total.setText(editText.getText().toString());
					surplus.setText(editText.getText().toString());
					setlayout.setVisibility(View.GONE);
					actionbuttonlayout.setVisibility(View.VISIBLE);
					uvprogresslayout.setVisibility(View.VISIBLE);
					sharedPreferences = getSharedPreferences("saveSet",
							Context.MODE_PRIVATE);
					editor = sharedPreferences.edit();
					editor.putString("uvtime", editText.getText().toString());
					editor.commit();

					try {
						Home.messageJsonObject
								.put("messagetype", "synchronous");
						Home.messageJsonObject.put("paramstype", "config");
						Home.messageJsonObject.put("device", "紫外灯");
						Home.messageJsonObject.put("currentstep",
								Integer.parseInt(total.getText().toString())
										* 60 + "");
						Home.messageJsonObject.put("totalsteps",
								Integer.parseInt(total.getText().toString())
										* 60 + "");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case R.id.cancel:
			if (UVdialog != null) {
				UVdialog.dismiss();
				UVdialog = null;
				uvIsHsowing = true;
			}

			// Tools.removeStatusBarAndNavigationBar(Set.this);
			break;
		case R.id.update:
			if(!Client.getInstance().isServerIsConnected()){
				Toast.makeText(this, "未登录服务器", Toast.LENGTH_SHORT).show();
				return;
			}
			// System.out.println("update");
			// try {
			// Home.client.sendFlag("update");
			// Home.client.sendFlag("machine");
			// Home.client.sendFlag("checkversion");
			// } catch (Exception e) {
			// // TODO: handle exception
			// Toast.makeText(Set.this, "未登录到服务器", 2000).show();
			// }
			MessageBean messageBean = Client.getInstance()
					.getDownloadFileRequestBean("machine/snaeii32",
							"version.txt");
			Client.getInstance().sendRquestForResponse(messageBean, false,
					new RequestCallBack<MessageBean>() {

						@Override
						public void Response(MessageBean t) {
							// TODO Auto-generated method stub
							try {
								Message.obtain(
										Set.handler,
										0,
										new String(t.getContent()
												.getBytecontent(), "utf-8"))
										.sendToTarget();
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
			if (loadingDialog == null)
				loadingDialog = new LoadingDialog(Set.this, "检查更新...");

			loadingDialog.show();

			updateTimer = new Timer();
			updateTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					runOnUiThread(new Runnable() {
						public void run() {

							if (loadingDialog != null
									&& loadingDialog.isShowing()) {
								loadingDialog.dismiss();
								loadingDialog = null;
								showTextToast("网络异常");
							}
						}
					});
				}
			}, 10000);

			break;
		case R.id.updateparams:
			if(!Client.getInstance().isServerIsConnected()){
				Toast.makeText(this, "未登录服务器", Toast.LENGTH_SHORT).show();
				return;
			}
				
			
			messageBean = Client.getInstance().getDownloadFileRequestBean(
					"machine/snaeii32", "params.txt");
			Client.getInstance().sendRquestForResponse(messageBean, false,
					new RequestCallBack<MessageBean>() {

						@Override
						public void Response(MessageBean t) {
							// TODO Auto-generated method stub
							try {
								if (Home.TheActivityIs.equals("Set")) {
									Message.obtain(
											Set.handler,
											StaticVar.DOWNLOAD_PARAMS,
											new String(t.getContent()
													.getBytecontent(), "utf-8"))
											.sendToTarget();
								} else {
									Message.obtain(
											Home.mainHandler,
											StaticVar.DOWNLOAD_PARAMS,
											new String(t.getContent()
													.getBytecontent(), "utf-8"))
											.sendToTarget();
								}

								if (updateTimer != null) {
									updateTimer.cancel();
									updateTimer = null;
								}

								if (loadingDialog != null
										&& loadingDialog.isShowing()) {
									loadingDialog.dismiss();
									loadingDialog = null;
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

						}
					});
			if (loadingDialog == null)
				loadingDialog = new LoadingDialog(Set.this, "检查更新...");

			loadingDialog.show();

			updateTimer = new Timer();
			updateTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					runOnUiThread(new Runnable() {
						public void run() {
							if (loadingDialog != null
									&& loadingDialog.isShowing()) {
								loadingDialog.dismiss();
								loadingDialog = null;
								showTextToast("网络异常");
							}
						}
					});

				}
			}, 10000);
			// Thread thread = new Thread() {
			// @Override
			// public void run() {
			// HttpURLConnection con;
			// try {
			// String urlstr =
			// "http://lw101192.xicp.net:14416/FINE-BIODATA/data.txt";
			// String line = null;
			// InputStream inputStream = null;
			// URL url = new URL(urlstr);
			// con = (HttpURLConnection) url.openConnection();
			// con.setConnectTimeout(4 * 1000);
			// inputStream = con.getInputStream();
			// BufferedReader bufferedReader = new BufferedReader(
			// new InputStreamReader(inputStream));
			// StringBuffer sb = new StringBuffer();
			// while ((line = bufferedReader.readLine()) != null) {
			// sb.append(line + "\r\n");
			// }
			// // System.out.println("收到数据>>>>"+sb.toString());
			// if (Tools.CheckVersion(sb.toString(),
			// getDataBaseVersion())) {
			// handler.obtainMessage(1, sb.toString())
			// .sendToTarget();
			//
			// } else {
			// handler.obtainMessage(0, null).sendToTarget();
			//
			// }
			// } catch (Exception e) {
			// // TODO: handle exception
			// runOnUiThread(new Runnable() {
			// public void run() {
			// showTextToast("网络故障");
			// }
			// });
			//
			// }
			// }
			// };
			// thread.start();
			break;
		case R.id.admin:
			// System.out.println("admin");
			CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
					Set.this);
			final EditText password = new EditText(Set.this);
			password.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			password.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			builder.setAutoDismiss(true)
					.setContentView(password)
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									if(password.getText().toString().equals("jmsw123")){
										Intent intent = new Intent();
										intent.setClass(Set.this, Admin.class);
										startActivity(intent);
									 }else{
										 Toast.makeText(getApplicationContext(), "给功能仅对维修人员开放", Toast.LENGTH_SHORT).show();
									 }
									// Tools.removeStatusBarAndNavigationBar(Set.this);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									// Tools.removeStatusBarAndNavigationBar(Set.this);
								}
							}).create().show();
			break;
		case R.id.actionbar_title:
			finish();
			break;

		}
	}

	public void home(View v) {
		finish();
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

	public void showTextToast(String msg) {
		toast = new CustomadeToast(this, msg, true);
		toast.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		Home.TheActivityIs = "Set";
		super.onResume();
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Set.this.finish();
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		if (toast != null)
			toast.dismiss();

		super.onStop();
	}

	void UVdialog(String str) {
		SharedPreferences preferences = getSharedPreferences("saveSet",
				Context.MODE_PRIVATE);

		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_uvset, null);

		actionbuttonlayout = (LinearLayout) view
				.findViewById(R.id.actionbuttonlayout);
		pause_continue = (ImageButton) view.findViewById(R.id.pause_continue);
		pause_continue.setTag(R.drawable.start);
		stop = (ImageButton) view.findViewById(R.id.stop);
		uvprogresslayout = (LinearLayout) view
				.findViewById(R.id.uvprogresslayout);
		total = (TextView) view.findViewById(R.id.total);
		surplus = (TextView) view.findViewById(R.id.surplus);
		textview = (TextView) view.findViewById(R.id.textview);
		setlayout = (LinearLayout) view.findViewById(R.id.setlayout);
		editText = (EditText) view.findViewById(R.id.editText);
		confirm = (Button) view.findViewById(R.id.confirm);
		cancel = (Button) view.findViewById(R.id.cancel);
		pause_continue.setOnClickListener(this);
		stop.setOnClickListener(this);
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);

		editText.setText(preferences.getString("uvtime", "30"));

		builder = new CustomadeDialog.Builder(this);

		if (str == null) {
			actionbuttonlayout.setVisibility(View.GONE);
			uvprogresslayout.setVisibility(View.GONE);
		} else {
			String temp[] = str.split(";");
			setlayout.setVisibility(View.GONE);
			if (temp[1].equals("Start")) {
				total.setText(Integer.parseInt(temp[2]) / 60 + "");
				surplus.setText(Integer.parseInt(temp[2]) / 60 + "");
				pause_continue.performClick();

			}
			if (temp[1].equals("Pause")) {
				pause_continue.setImageResource(R.drawable.start);
				pause_continue.setTag(R.drawable.start);
			}
		}

		UVdialog = builder.setCancelable(false).setTitle("设置")
				.setContentView(view).create();

		UVdialog.show();

	}

	void initHandler() {
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					if (loadingDialog != null) {
						loadingDialog.dismiss();
					}
					if (updateTimer != null)
					updateTimer.cancel();
					builder = new CustomadeDialog.Builder(Set.this);
					try {
						int localVersionCode = Tools
								.getAppVersionCode(Set.this);
						JSONObject jsonObject = new JSONObject(
								msg.obj.toString());
						int remotVersionCode = jsonObject.getInt("versioncode");
						if (localVersionCode < remotVersionCode) {
							builder.setTitle(
									"发现新版本 "
											+ jsonObject
													.getString("versionname"))
									.setMessage(jsonObject.getString("info"))
									.setPositiveButton(
											"下载",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
													
													runOnUiThread(new Runnable() {
														public void run() {
															if (progressDialog == null) {
																progressDialog = new ProgressDialog(Set.this);
																progressDialog
																		.setCanceledOnTouchOutside(false);
																progressDialog
																		.show();
															} else {
																progressDialog
																		.show();
															}
														}
													});
													
													new Thread(new Runnable() {

														@Override
														public void run() {
															// TODO
															// Auto-generated
															// method stub
															// Home.downloadclient
															// = new
															// ConnctionServer();
															// Home.downloadclient.sendFlag("update");
															// Home.downloadclient.sendFlag("machine");
															// Home.downloadclient.sendFlag("download32");
															//
															// try {
															//
															// Home.downloadclient.Client_in.readObject().toString();
															//
															// Tools.downloadFromServer();
															// } catch
															// (Exception e) {
															// // TODO
															// Auto-generated
															// catch block
															// e.printStackTrace();
															// }

															

															MessageBean messageBean = Client
																	.getInstance()
																	.getDownloadFileRequestBean(
																			"machine/snaeii32",
																			"app.apk");
															Client.getInstance()
																	.sendRquestForResponse(
																			messageBean,
																			false,
																			new RequestCallBack<MessageBean>() {

																				@Override
																				public void Response(
																						MessageBean t) {
																					// TODO
																					// Auto-generated
																					// method
																					// stub
																					if (t.getContent()
																							.getContenttype() != null) {
																						if (t.getContent()
																								.getContenttype()
																								.equals("filelength"))
																							Client.getInstance().fileLength = Long
																									.parseLong(t
																											.getContent()
																											.getStringcontent());

																						// System.out.println("fileLength  "+
																						// Client.getInstance().fileLength);
																					} else {
																						try {

																							File filePath = Environment
																									.getExternalStorageDirectory();
																							String savePath = filePath
																									+ "/"
																									+ "app.apk";
																							System.out
																									.print("savePath>>"
																											+ savePath);
																							FileOutputStream out = new FileOutputStream(
																									savePath);
																							FileChannel fc = out
																									.getChannel();
																							fc.write(ByteBuffer
																									.wrap(t.getContent()
																											.getBytecontent()));
																							System.out
																									.println("文件接收完成");
																							Message.obtain(
																									handler,
																									StaticVar.COMPLETE_DOWNLOAD,
																									"app.apk")
																									.sendToTarget();
																							Tools.runCommand("chmod 777 "
																									+ savePath);
																						} catch (Exception e) {
																							// TODO:
																							// handle
																							// exception
																						}
																					}
																				}
																			});

														}
													}).start();

												}
											})
									.setNegativeButton(
											"取消",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
												}
											});
						} else {
							builder.setTitle("提示")
									.setMessage("当前为最新版本，无需更新")
									.setNegativeButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub
												}
											});
						}
						if (!Set.this.isFinishing()) {
							builder.create().show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 1:
					if (loadingDialog != null) {
						loadingDialog.dismiss();
					}
					if (updateTimer != null)
					updateTimer.cancel();
					final String str = msg.obj.toString();
//					JSONObject jsonObject;
//					String DataBaseVersion = null;
//					String tempVersion = null;
//					try {
//						jsonObject = new JSONObject(str);
//						tempVersion = "V" + jsonObject.getString("Version")
//								+ ".0";
//						DataBaseVersion = "V" + Home.manageAdapter.version + ".0";
//						if (Home.manageAdapter.version == 0)
//							DataBaseVersion = "空";
//
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
							Set.this);
					builder.setCancelable(false)
							.setTitle("数据更新")
							.setMessage("是否更新？")
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											// Tools.removeStatusBarAndNavigationBar(Set.this);
										}
									})
							.setPositiveButton("更新",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											int num = 0;
											JSONObject jsonObject = null;
											try {
												jsonObject = new JSONObject(str);
												num = jsonObject.getInt("num");
											} catch (JSONException e) {
											}
											for(int i = 0; i < num; i++){
												try {
													downloadPrograms(jsonObject.getJSONObject(i+1+""));
												} catch (JSONException e1) {
												}
												int count = 0;
												while(!isDownLoaded){
													try {
														Thread.sleep(100);
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													if(count++>50){
														isDownLoaded = false;
														Toast.makeText(Set.this, "更新超时！",
																Toast.LENGTH_SHORT).show();
														return;
													}
												}
												isDownLoaded = false;
											}
											Toast.makeText(Set.this, "更新完成！",
													Toast.LENGTH_SHORT).show();
											// Tools.removeStatusBarAndNavigationBar(Set.this);
										}
									}).create().show();

					break;
				case 2:
					UVdialog(msg.obj.toString());
					break;
				case 3:
					switch (msg.obj.toString()) {
					case "zw_door_2":
						if (UVdialog != null) {
							stop.performClick();
							Toast.makeText(Set.this, "门被打开，紫外灯自动关闭",
									Toast.LENGTH_SHORT).show();
						}

						break;

					default:
						break;
					}
					break;

				case StaticVar.DOWNLOAD_PARAMS:
					/*if (Tools.CheckVersion(msg.obj.toString(),
							Home.manageAdapter.version)) {
						handler.obtainMessage(1, msg.obj.toString())
								.sendToTarget();

					} else {
						handler.obtainMessage(5, null).sendToTarget();

					}*/
					break;
				case 5:
					if (loadingDialog != null) {
						loadingDialog.dismiss();
					}
					if (updateTimer != null)
					updateTimer.cancel();
					// showTextToast("当前数据库为最新版本");
					// Toast.makeText(Set.this, "数据库是最新的",
					// Toast.LENGTH_LONG).show();
					builder = new CustomadeDialog.Builder(Set.this);
					builder.setTitle("提示")
							.setMessage("当前为最新版本，无需更新")
							.setNegativeButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
										}
									}).create().show();
					break;
				case StaticVar.UPDATE_PROGRESSDIALOG:
//					int percent = (int) ((float) msg.arg1 / msg.arg2 * 100);
//					if (progressDialog == null) {
//						progressDialog = new ProgressDialog(Set.this);
//						progressDialog.setCanceledOnTouchOutside(false);
//						progressDialog.show();
//					} else {
//						progressDialog.show();
//					}
//					progressDialog.setMessage("已下载" + percent + "%");
					
					
					if(progressDialog!=null&&progressDialog.isShowing()){
                        int percent = (int)((float)msg.arg1/msg.arg2*100);
                        progressDialog.setMessage("已下载"+percent+"%");
                    }
					break;
				case StaticVar.COMPLETE_DOWNLOAD:
					final String filename = msg.obj.toString();
					// System.out.println("filename"+msg.obj);
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					builder = new CustomadeDialog.Builder(Set.this);
					builder.setTitle("提示")
							.setMessage("现在就要安装吗？")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											// System.out.println(filename);
											// System.out.println(msg.obj.toString());
											// AutoInstall autoInstall = new
											// AutoInstall();
											// String path =
											// Environment.getExternalStorageDirectory().toString()
											// + "/"+msg.obj.toString();
											// System.out.println(path);
											// autoInstall.setUrl(path);
											// autoInstall.install(Set.this);
											// Tools.removeStatusBarAndNavigationBar(Set.this);
											Intent ite = new Intent();
									        ite.setAction("startme");
//									        PendingIntent SENDER = PendingIntent.getBroadcast(activity, 0, ite,
//									                PendingIntent.FLAG_CANCEL_CURRENT);
//									        AlarmManager ALARM = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//									        ALARM.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000,
//									                SENDER);
									        Set.this.sendBroadcast(ite);
									        
									        
											SilentInstall.install(filename);
												
											
											// if(SilentInstall.install(filename))
											// System.out.println("安装完成");
											// else
											// System.out.println("安装失败");
											// installApk(filename);
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
										}
									}).create().show();
					break;
				case StaticVar.INTERRUPT_PROCESS:
					if (msg.obj.toString().equals("Start")) {
						if (pause_continue.getTag().equals(R.drawable.start)) {
							pause_continue.performClick();
						}
					} else {
						stop.performClick();
					}

					break;
				default:
					break;
				}
			};
		};
	}

//	private void installApk(String filename) {
//		File apkfile = new File(Environment.getExternalStorageDirectory(),
//				filename);
//		if (!apkfile.exists()) {
//			return;
//		}
//		// 通过Intent安装APK文件
//		Intent i = new Intent(Intent.ACTION_VIEW);
//		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//				"application/vnd.android.package-archive");
//		this.startActivity(i);
//	}
	
	
	void downloadPrograms(JSONObject jsonObject){
		if(jsonObject == null) return;
		try {
			final String filename = jsonObject.getString("name");
			final String date = jsonObject.getString("date");
			MessageBean messageBean = Client.getInstance().getDownloadFileRequestBean(
					"machine/snaeii32/programs", filename + ".xml");
			Client.getInstance().sendRquestForResponse(messageBean, false,
					new RequestCallBack<MessageBean>() {

						@Override
						public void Response(MessageBean t) {
							try {
								Tools.SaveToFileByName(Set.this, filename, new String(t.getContent()
										.getBytecontent(), "utf-8"));
//								Home.manageAdapter.updateStandardPrograms(filename,date);
								isDownLoaded = true;
							} catch (Exception e) {
							}

						}
					});
		} catch (JSONException e) {
		}
	}

	protected void sendNote(int i, boolean beInterrupt) {
		// TODO Auto-generated method stub
		switch (i) {
		case 1:
			try {
				JSONObject object = new JSONObject();
				object.put("messagetype", "notice");
				object.put("paramstype", "set");
				object.put("paramstitle", "紫外灯");
				if (beInterrupt) {
					object.put("noticecontent", "紫外灯除菌被终止");
				} else {
					object.put("noticecontent", "紫外灯除菌已完成");
				}
				MessageBean messageBean = new MessageBean();
				messageBean.setAction("SendCmd");
				UserBean from = new UserBean();
				from.setId(Client.getInstance().getUserID());
				messageBean.setFrom(from);
				UserBean to = new UserBean();
				to.setId("ALL");
				messageBean.setTo(to);
				ContentBean contentBean = new ContentBean();
				contentBean.setStringcontent(object.toString());
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

	@Override
	public void onToggle(View view, boolean isChecked) {
		// TODO Auto-generated method stub
		String str = null;
		switch (view.getId()) {
		case R.id.heat1:
			if (!isChecked)
				str = Tools.packagebag("lll", "8");
			if (isChecked)
				str = Tools.packagebag("lll", "9");
			try {
				Home.serialport.SendMsg(str);
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("请检查网络状态");
			}

			break;
		case R.id.heat2:
			if (!isChecked)
				str = Tools.packagebag("lll", "10");
			if (isChecked)
				str = Tools.packagebag("lll", "11");
			try {
				Home.serialport.SendMsg(str);
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("请检查网络状态");
			}
			break;
		case R.id.heat3:
			if (!isChecked)
				str = Tools.packagebag("lll", "12");
			if (isChecked)
				str = Tools.packagebag("lll", "13");
			try {
				Home.serialport.SendMsg(str);
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("请检查网络状态");
			}
			break;
		case R.id.heat4:
			if (!isChecked)
				str = Tools.packagebag("lll", "14");
			if (isChecked)
				str = Tools.packagebag("lll", "15");
			try {
				Home.serialport.SendMsg(str);
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("请检查网络状态");
			}
			break;
		case R.id.heat5:
			if (!isChecked)
				str = Tools.packagebag("lll", "18");
			if (isChecked)
				str = Tools.packagebag("lll", "19");
			try {
				Home.serialport.SendMsg(str);
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("请检查网络状态");
			}

			break;
		case R.id.heat6:
			if (!isChecked)
				str = Tools.packagebag("lll", "20");
			if (isChecked)
				str = Tools.packagebag("lll", "21");
			try {
				Home.serialport.SendMsg(str);
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("请检查网络状态");
			}
			break;
		case R.id.heat7:
			if (!isChecked)
				str = Tools.packagebag("lll", "22");
			if (isChecked)
				str = Tools.packagebag("lll", "23");
			try {
				Home.serialport.SendMsg(str);
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("请检查网络状态");
			}
			break;
		case R.id.heat8:
			if (!isChecked)
				str = Tools.packagebag("lll", "24");
			if (isChecked)
				str = Tools.packagebag("lll", "25");
			try {
				Home.serialport.SendMsg(str);
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("请检查网络状态");
			}
			break;
		case R.id.fan:
			if (isChecked)
				str = Tools.packagebag("lll", "16");
			if (!isChecked)
				str = Tools.packagebag("lll", "17");
			try {
				Home.serialport.SendMsg(str);
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("请检查网络状态");
			}
			break;

		default:
			break;
		}
	}

}
