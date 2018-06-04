package com.xm.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socketserialport.R;
import com.xm.Bean.ContentBean;
import com.xm.Bean.MessageBean;
import com.xm.Bean.UserBean;
import com.xm.Widget.CustomadeDialog;
import com.xm.mina.Client;
import com.xm.tools.Tools;
import com.xm.var.StaticVar;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

public class Standard_RuntimeView extends Activity implements OnClickListener, OnItemSelectedListener, OnToggleChanged {
	private static Spinner fla;
	private static Spinner alb;
	private static Spinner tlc;
	private static Spinner tld;
	private static Spinner t0k;
	private static Spinner t2k;
	private static Spinner t4k;
	private static Spinner t6k;
	private static Spinner fhe;
	private static Spinner ahf;
	private static Spinner thg;
	private static Spinner thi;
	private static Spinner vjv;
	private static Spinner fjm;
	private static Spinner ajn;
	private static Spinner tjo;
	private static Spinner tjq;
	private static Spinner tjt;
	private static Spinner f1A;
	private static Spinner a1B;
	private static Spinner t1C;
	private static Spinner t1D;
	private static Spinner t1E;
	private static Spinner f2G;
	private static Spinner a2H;
	private static Spinner t2I;
	private static Spinner t2J;
	private static Spinner t2K;
	private static Spinner f3M;
	private static Spinner a3N;
	private static Spinner t3O;
	private static Spinner t3P;
	private static Spinner t3Q;
	private static Spinner t3R;
	private static Spinner ftS;
	private static Spinner atT;
	private static Spinner ttU;
	private static Spinner ttV;
	private static Spinner ttW;
	private static Spinner t1k;
	private static Spinner t3k;
	private static Spinner t5k;
	private static Spinner t7k;
	private static Spinner fsY;
	private static Spinner asZ;
	private static Spinner tsz;
	private static Spinner vibbottom0;
	private static Spinner vibbottom1;
	private static Spinner vibbottom2;
	private static Spinner vibbottom3;
	private static Spinner vibbottom4;
	private static Spinner vibbottom5;
	private static Spinner vibbottom6;
	private static Spinner vibbottom7;
	

	private static TextView tlk_disk1;
	private static TextView ttr_disk1;
	private static TextView tlk_disk2;
	private static TextView ttr_disk2;
	private static TextView tlk_disk3;
	private static TextView ttr_disk3;
	private static TextView tlk_disk4;
	private static TextView ttr_disk4;
	private static ToggleButton heat0Switch;
	private static ToggleButton heat1Switch;
	private static ToggleButton heat2Switch;
	private static ToggleButton heat3Switch;
	private static ToggleButton heat4Switch;
	private static ToggleButton heat5Switch;
	private static ToggleButton heat6Switch;
	private static ToggleButton heat7Switch;
	private Button start;
	private Button stop;
	private Button stl;
	private Button rfl;
	private Boolean heat1bol = false;
	private Boolean heat2bol = false;
	private Boolean heat3bol = false;
	private Boolean heat4bol = false;
	private Boolean heat5bol = false;
	private Boolean heat6bol = false;
	private Boolean heat7bol = false;
	private Boolean heat8bol = false;
	public static Handler handler;
	private String usernumber;
	private String check;
	private String heat0str = Tools.packagebag("w0r", "0");
	private String heat1str = Tools.packagebag("w1r", "0");
	private String heat2str = Tools.packagebag("w2r", "0");
	private String heat3str = Tools.packagebag("w3r", "0");
	private String heat4str = Tools.packagebag("w4r", "0");
	private String heat5str = Tools.packagebag("w5r", "0");
	private String heat6str = Tools.packagebag("w6r", "0");
	private String heat7str = Tools.packagebag("w7r", "0");
	private Toast toast;
	private int buttonflag = 0;
	private int count = 0;

	private SharedPreferences sharedPreferences;
	private Editor editor;
	private Timer timer1 = new Timer();
	CustomadeDialog dialog = null;
	boolean gateState; // 门状态，true为关，false为开
	boolean expAllow = true; // 是否允许实验
	// private Timer timer2=new Timer();
//	byte cmdon[] = new byte[] { 0x3A, 0x73, 0x73, 0x73, 0x3D, 0x31, 0x33, 0x38,
//			0x0D, 0x0A };
//	byte cmdoff[] = new byte[] { 0x3A, 0x73, 0x73, 0x73, 0x3D, 0x30, 0x33,
//			0x38, 0x0D, 0x0A };
	private Button btn_title;
	String fromactivity;
	boolean atThisActivity = false;
	Drawable drawable;
	Intent intent;
	LinearLayout layout_tab;
	FrameLayout content;
	private ValueAnimator animator;
	private TextView title;
	private boolean beinterrupted;
	private int temp_threshold=29;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		/*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
		setContentView(R.layout.layout_standard_runtimeview);
//		Tools.removeStatusBarAndNavigationBar(this);
		Home.TheActivityIs = "Standard_RuntimeView";
		intent = getIntent();
		init();

		initHandler();
		if (intent.getAction() == null) {
			loadData();
		} else {
			loadJsonData();
			findViewById(R.id.notice).setVisibility(View.VISIBLE);
		}
		saveToMessageJson();

	}

	void initHandler() {

		handler = new Handler() {

			@Override
			/**
			 * 主线程消息处理中心
			 */
			public void handleMessage(Message msg) {
//System.out.println("mag.what>>"+msg.what+"  msg.obj.toString()>>"+msg.obj.toString());
				// 接收子线程的消息
				if(msg.what==StaticVar.INTERRUPT_PROCESS){
					if(msg.obj.toString().equals("Start")){
						if(!start.getText().toString().equals("暂停"))
							start.performClick();
					}else{
						stop.performClick();
					}
				}
				
				

				try {
					int value = 0;
					float floatvalue = 0;
					String[] res = new String[2];
					res = (String[]) msg.obj;
					String s = res[0].toString();
					if(res[1].contains(".")){
						floatvalue = Float.parseFloat(res[1]);
					}else{
						value = Integer.parseInt(res[1]);
					}
//					try {
//						value = Integer.parseInt(res[1]);
//					} catch (Exception e) {
//						// TODO: handle exception
//						
//					}
					switch (s) {
					
					case "t0k":
						if(floatvalue>temp_threshold){
							
							tlk_disk1.setText(floatvalue + "");
							try {
								Home.messageJsonObject.put("工位1", floatvalue+"");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							tlk_disk1.setText("室温");
							try {
								Home.messageJsonObject.put("工位1", "室温");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case "t1k":
						if(floatvalue>temp_threshold){
							
							ttr_disk1.setText(floatvalue + "");
							try {
								Home.messageJsonObject.put("工位2", floatvalue+"");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							ttr_disk1.setText("室温");
							try {
								Home.messageJsonObject.put("工位2", "室温");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case "t2k":
						if(floatvalue>temp_threshold){
							
							tlk_disk2.setText(floatvalue + "");
							try {
								Home.messageJsonObject.put("工位3", floatvalue+"");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							tlk_disk2.setText("室温");
							try {
								Home.messageJsonObject.put("工位3", "室温");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case "t3k":
						if(floatvalue>temp_threshold){
							
							ttr_disk2.setText(floatvalue + "");
							try {
								Home.messageJsonObject.put("工位4", floatvalue+"");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							ttr_disk2.setText("室温");
							try {
								Home.messageJsonObject.put("工位4", "室温");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case "t4k":
						if(floatvalue>temp_threshold){
							
							tlk_disk3.setText(floatvalue + "");
							try {
								Home.messageJsonObject.put("工位5", floatvalue+"");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							tlk_disk3.setText("室温");
							try {
								Home.messageJsonObject.put("工位5", "室温");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case "t5k":
						if(floatvalue>temp_threshold){
							
							ttr_disk3.setText(floatvalue + "");
							try {
								Home.messageJsonObject.put("工位6", floatvalue+"");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							ttr_disk3.setText("室温");
							try {
								Home.messageJsonObject.put("工位6", "室温");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case "t6k":
						if(floatvalue>temp_threshold){
							
							tlk_disk4.setText(floatvalue + "");
							try {
								Home.messageJsonObject.put("工位7", floatvalue+"");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							tlk_disk4.setText("室温");
							try {
								Home.messageJsonObject.put("工位7", "室温");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case "t7k":
						if(floatvalue>temp_threshold){
							
							ttr_disk4.setText(floatvalue + "");
							try {
								Home.messageJsonObject.put("工位8", floatvalue+"");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							ttr_disk4.setText("室温");
							try {
								Home.messageJsonObject.put("工位8", "室温");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
//					case "tlk":
//						tlk.setSelection(value - 29);
//						break;
//					case "ttr":
//						ttr.setSelection(value - 29);
//						break;
//					case "wl0":
//						if (value == 0) {
//							heat1.setChecked(true);
//						} else {
//							heat1.setChecked(false);
//						}
//						break;
//					case "wl9":
//						if (value == 0) {
//							heat2.setChecked(true);
//						} else {
//							heat2.setChecked(false);
//						}
//						break;
//					case "wtp":
//						if (value == 0) {
//							heat3.setChecked(true);
//						} else {
//							heat3.setChecked(false);
//						}
//						break;
//					case "wty":
//						if (value == 0) {
//							heat4.setChecked(true);
//						} else {
//							heat4.setChecked(false);
//						}
//						break;

					case "s":
						startCurrentStepAnim(layout_tab.getChildAt(0));
						showTextToast("设备已启动");
						Home.messageJsonObject.put("currentstep", "1");
//						Tools.execShellCmd("input tap 68 80");
						uiChange(0);
						break;
					case "h":
						startCurrentStepAnim(layout_tab.getChildAt(1));
						((TextView)layout_tab.getChildAt(0)).setTextColor(Color.argb(255 , 0, 0, 0));
						// reclen=Integer.parseInt(hzm.getText().toString())*60+Integer.parseInt(hzs.getText().toString())+Integer.parseInt(hd1m.getText().toString())*60+Integer.parseInt(hd1s.getText().toString())+Integer.parseInt(hxm.getText().toString())*60+Integer.parseInt(hxs.getText().toString())+Integer.parseInt(hd2m.getText().toString())*60+Integer.parseInt(hd2s.getText().toString());
						// Message hmsg=handler.obtainMessage(1);
						// hmsg.arg1=reclen;
						// handler.sendMessage(hmsg);
						Home.messageJsonObject.put("currentstep", "2");
//						Tools.execShellCmd("input tap 204 80");
						uiChange(1);
						break;
					case "j":
						startCurrentStepAnim(layout_tab.getChildAt(2));
						((TextView)layout_tab.getChildAt(1)).setTextColor(Color.argb(255 , 0, 0, 0));
						// reclen=Integer.parseInt(jzm.getText().toString())*60+Integer.parseInt(jzs.getText().toString())+Integer.parseInt(jd1m.getText().toString())*60+Integer.parseInt(jd1s.getText().toString())+Integer.parseInt(jxm.getText().toString())*60+Integer.parseInt(jxs.getText().toString())+Integer.parseInt(jd2m.getText().toString())*60+Integer.parseInt(jd2s.getText().toString());
						// Message jmsg=handler.obtainMessage(1);
						// jmsg.arg1=reclen;
						// handler.sendMessage(jmsg);
						Home.messageJsonObject.put("currentstep", "3");
//						Tools.execShellCmd("input tap 340 80");
						uiChange(2);
						break;
					case "w1":
						startCurrentStepAnim(layout_tab.getChildAt(3));
						((TextView)layout_tab.getChildAt(2)).setTextColor(Color.argb(255 , 0, 0, 0));
						// reclen=Integer.parseInt(w1zm.getText().toString())*60+Integer.parseInt(w1zs.getText().toString())+Integer.parseInt(w1d1m.getText().toString())*60+Integer.parseInt(w1d1s.getText().toString())+Integer.parseInt(w1xm.getText().toString())*60+Integer.parseInt(w1xs.getText().toString())+Integer.parseInt(w1d2m.getText().toString())*60+Integer.parseInt(w1d2s.getText().toString());
						// Message w1msg=handler.obtainMessage(1);
						// w1msg.arg1=reclen;
						// handler.sendMessage(w1msg);
						Home.messageJsonObject.put("currentstep", "4");
//						Tools.execShellCmd("input tap 476 80");
						uiChange(3);
						break;
					case "w2":
						startCurrentStepAnim(layout_tab.getChildAt(4));
						((TextView)layout_tab.getChildAt(3)).setTextColor(Color.argb(255 , 0, 0, 0));
						// reclen=Integer.parseInt(w2zm.getText().toString())*60+Integer.parseInt(w2zs.getText().toString())+Integer.parseInt(w2d1m.getText().toString())*60+Integer.parseInt(w2d1s.getText().toString())+Integer.parseInt(w2xm.getText().toString())*60+Integer.parseInt(w2xs.getText().toString())+Integer.parseInt(w2d2m.getText().toString())*60+Integer.parseInt(w2d2s.getText().toString());
						// Message w2msg=handler.obtainMessage(1);
						// w2msg.arg1=reclen;
						// handler.sendMessage(w2msg);
						Home.messageJsonObject.put("currentstep", "5");
//						Tools.execShellCmd("input tap 612 80");
						uiChange(4);
						break;
					case "w3":
						startCurrentStepAnim(layout_tab.getChildAt(5));
						((TextView)layout_tab.getChildAt(4)).setTextColor(Color.argb(255 , 0, 0, 0));
						// reclen=Integer.parseInt(w3zm.getText().toString())*60+Integer.parseInt(w3zs.getText().toString())+Integer.parseInt(w3d1m.getText().toString())*60+Integer.parseInt(w3d1s.getText().toString())+Integer.parseInt(w3xm.getText().toString())*60+Integer.parseInt(w3xs.getText().toString())+Integer.parseInt(w3d2m.getText().toString())*60+Integer.parseInt(w3d2s.getText().toString());
						// Message w3msg=handler.obtainMessage(1);
						// w3msg.arg1=reclen;
						// handler.sendMessage(w3msg);
						Home.messageJsonObject.put("currentstep", "6");
//						Tools.execShellCmd("input tap 748 80");
						uiChange(5);
						break;
					case "t":
						startCurrentStepAnim(layout_tab.getChildAt(6));
						((TextView)layout_tab.getChildAt(5)).setTextColor(Color.argb(255 , 0, 0, 0));
						// reclen=Integer.parseInt(tzm.getText().toString())*60+Integer.parseInt(tzs.getText().toString())+Integer.parseInt(td1m.getText().toString())*60+Integer.parseInt(td1s.getText().toString())+Integer.parseInt(txm.getText().toString())*60+Integer.parseInt(txs.getText().toString())+Integer.parseInt(td2m.getText().toString())*60+Integer.parseInt(td2s.getText().toString());
						// Message tmsg=handler.obtainMessage(1);
						// tmsg.arg1=reclen;
						// handler.sendMessage(tmsg);
						Home.messageJsonObject.put("currentstep", "7");
//						Tools.execShellCmd("input tap 850 80");
						uiChange(6);
						break;
					case "f":
						startCurrentStepAnim(layout_tab.getChildAt(7));
						((TextView)layout_tab.getChildAt(6)).setTextColor(Color.argb(255 , 0, 0, 0));
						// reclen=Integer.parseInt(szm.getText().toString())*60+Integer.parseInt(szs.getText().toString());
						// Message fmsg=handler.obtainMessage(1);
						// fmsg.arg1=reclen;
						// handler.sendMessage(fmsg);
						Home.messageJsonObject.put("currentstep", "8");
//						Tools.execShellCmd("input tap 1000 80");
						uiChange(7);
						break;
					case "z":
						stopCurrentProcess();
//						Tools.execShellCmd("input tap 68 80");
						uiChange(0);
						break;
					case "o":
						stopCurrentProcess();
						((TextView)layout_tab.getChildAt(7)).setTextColor(Color.argb(255 , 0, 0, 0));
						stop.performClick();
						showTextToast("设备已停止");
						Home.messageJsonObject.put("currentstep", "0");
						
						beinterrupted=false;
						sendNote(1);
//						Tools.execShellCmd("input tap 68 80");
						uiChange(0);
						break;
					
					case "dialogbox":
						CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
								Standard_RuntimeView.this);
						builder.setCancelable(false).setAutoDismiss(true);
						switch (value) {
						case 1: // 加热模块1超温
						case 2: // 加热模块2超温
						case 3: // 加热模块3超温
						case 4: // 加热模块4超温
						case 10:
						case 11:
						case 12:
						case 13:	
							builder.setTitle("提醒")
									.setMessage(
											checkStation(value)
													+ "加热模块超温！")
									.setPositiveButton("确定", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											stop.performClick();
										}
									}).create()
									.show();
							break;
						case 5: // 运行中开门
							if(dialog==null||!dialog.isShowing()){
							dialog = builder
									.setAutoDismiss(false)
									.setCancelable(false)
									.setTitle("提醒")
									.setMessage("实验暂停！您想做的是？")
									.setPositiveButton(
											"停止实验",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method
													// stub
													stop.performClick();
													dialog.dismiss();
//													drawable = getResources()
//															.getDrawable(
//																	R.drawable.start);
//													drawable.setBounds(
//															0,
//															0,
//															drawable.getMinimumWidth(),
//															drawable.getMinimumHeight());
//													start.setCompoundDrawables(
//															null, drawable,
//															null, null);
//													start.setText("运行");
//													setEditEnable(false);
//													Tools.removeStatusBarAndNavigationBar(Standard_RuntimeView.this);
												}
											})
									.setNegativeButton(
											"继续实验",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface sdialog,
														int which) {
													// TODO Auto-generated
													// method stub
													Home.serialport.SendMsg(Tools.packagebag("sss", "0"));
												}
											}).create();
							dialog.show();}
							break;
						case 6: // 运行中加热托盘移动
							builder.setTitle("提醒")
									.setMessage("运行中加热托盘移动,实验停止！")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method
													// stub
													drawable = getResources()
															.getDrawable(
																	R.drawable.start);
													drawable.setBounds(
															0,
															0,
															drawable.getMinimumWidth(),
															drawable.getMinimumHeight());
													start.setCompoundDrawables(
															null, drawable,
															null, null);
													start.setText("运行");
													setEditEnable(false);
//													Tools.removeStatusBarAndNavigationBar(Standard_RuntimeView.this);
												}
											}).create().show();
							break;
						case 7: // 电机未复位
							expAllow = false;
							builder.setTitle("提醒")
									.setMessage("电机未复位，请检查！")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method
													// stub
//													Tools.removeStatusBarAndNavigationBar(Standard_RuntimeView.this);
													drawable = getResources()
															.getDrawable(
																	R.drawable.start);
													drawable.setBounds(
															0,
															0,
															drawable.getMinimumWidth(),
															drawable.getMinimumHeight());
													start.setCompoundDrawables(
															null, drawable,
															null, null);
													start.setText("运行");
													if (fromactivity
															.equals("StandardUserareaListView"))
														setEditEnable(true);
												}
											}).create().show();
							break;
						case 8: // 门未关
							expAllow = false;
							builder.setTitle("提醒")
									.setMessage("门未关，请检查！")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method
													// stub
//													Tools.removeStatusBarAndNavigationBar(Standard_RuntimeView.this);
													drawable = getResources()
															.getDrawable(
																	R.drawable.start);
													drawable.setBounds(
															0,
															0,
															drawable.getMinimumWidth(),
															drawable.getMinimumHeight());
													start.setCompoundDrawables(
															null, drawable,
															null, null);
													start.setText("运行");
													if (fromactivity
															.equals("StandardUserareaListView"))
														setEditEnable(true);
												}
											}).create().show();
							break;
						case 9: // 托盘未复位
							expAllow = false;
							builder.setTitle("提醒")
									.setMessage("托盘未复位，请检查！")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method
													// stub
//													Tools.removeStatusBarAndNavigationBar(Standard_RuntimeView.this);
													drawable = getResources()
															.getDrawable(
																	R.drawable.start);
													drawable.setBounds(
															0,
															0,
															drawable.getMinimumWidth(),
															drawable.getMinimumHeight());
													start.setCompoundDrawables(
															null, drawable,
															null, null);
													start.setText("运行");
													if (fromactivity
															.equals("StandardUserareaListView"))
														setEditEnable(true);
												}
											}).create().show();
							break;
						default:
							break;
						}
						break;
					case "door":
						switch (value) {
						case 0: // 门关闭
							if(dialog!=null&&dialog.isShowing()){
								dialog.dismiss();
								if(start.getText().toString().equals("继续"))
									start.performClick();
							}
							gateState = true;
							break;
						case 1: // 门打开
							if(dialog!=null&&dialog.isShowing())
							showTextToast("请先关闭实验舱门！");
							gateState = false;
							break;

						default:
							break;
						}
						break;

					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			private String checkStation(int value) {
				// TODO Auto-generated method stub
				String res = null;
				switch (value) {
				case 1:
					res="热井1";
					break;
				case 2:
					res="热井2";
					break;
				case 3:
					res="热井3";
					break;
				case 4:
					res="热井4";
					break;
				case 10:
					res="热井5";
					break;
				case 11:
					res="热井6";
					break;
				case 12:
					res="热井7";
					break;
				case 13:
					res="热井8";
					break;
				}
				return res;
			}

		};
		/*
		 * handler=new Handler(){
		 * 
		 * @Override
		 * 
		 * public void handleMessage(Message msg){ if(msg.arg1==0){} else{
		 * reclen=msg.arg1;} switch(msg.what){ case 1:
		 * 
		 * gongweim.setText(reclen/60+""); if(totaltime%60>30){int
		 * x=1+totaltime/60;totalprocessm.setText(x+"");}else{
		 * totalprocessm.setText(totaltime/60+"");} reclen--; totaltime--;
		 * break; case 2: break; } } };
		 */

	}

	protected void stopCurrentProcess() {
		// TODO Auto-generated method stub
		if(animator!=null&&animator.isRunning())
		animator.cancel();
	}

	protected void startCurrentStepAnim(final View childAt) {
		// TODO Auto-generated method stub
		if(animator!=null&&animator.isRunning())
			animator.cancel();
		animator = ValueAnimator.ofFloat(1.0f,0.3f,1.0f);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				
				// TODO Auto-generated method stub
				int alpha =(int) (((float)animation.getAnimatedValue())*255);
				((TextView)childAt).setTextColor(Color.argb(alpha , 0, 0, 0));
			}
		});
		animator.setDuration(400);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.start();
	}

	void init() {

		title = (TextView) findViewById(R.id.title);
		title.setText(intent.getStringExtra("name"));
		usernumber = String.valueOf(intent.getIntExtra("usernumber", 1) + 1);
		check = String.valueOf(7 - intent.getIntExtra("usernumber", 1));
		fromactivity = intent.getStringExtra("fromactivity");

		fla = (Spinner) findViewById(R.id.fla);
		alb = (Spinner) findViewById(R.id.alb);
		tlc = (Spinner) findViewById(R.id.tlc);
		tld = (Spinner) findViewById(R.id.tld);
		fhe = (Spinner) findViewById(R.id.fhe);
		ahf = (Spinner) findViewById(R.id.ahf);
		thg = (Spinner) findViewById(R.id.thg);
		thi = (Spinner) findViewById(R.id.thi);
		vjv = (Spinner) findViewById(R.id.vjv);
		fjm = (Spinner) findViewById(R.id.fjm);
		ajn = (Spinner) findViewById(R.id.ajn);
		tjo = (Spinner) findViewById(R.id.tjo);
		tjq = (Spinner) findViewById(R.id.tjq);
		tjt = (Spinner) findViewById(R.id.tjt);
		f1A = (Spinner) findViewById(R.id.f1A);
		a1B = (Spinner) findViewById(R.id.a1B);
		t1C = (Spinner) findViewById(R.id.t1C);
		t1D = (Spinner) findViewById(R.id.t1D);
		t1E = (Spinner) findViewById(R.id.t1E);
		f2G = (Spinner) findViewById(R.id.f2G);
		a2H = (Spinner) findViewById(R.id.a2H);
		t2I = (Spinner) findViewById(R.id.t2I);
		t2J = (Spinner) findViewById(R.id.t2J);
		t2K = (Spinner) findViewById(R.id.t2K);
		f3M = (Spinner) findViewById(R.id.f3M);
		a3N = (Spinner) findViewById(R.id.a3N);
		t3O = (Spinner) findViewById(R.id.t3O);
		t3P = (Spinner) findViewById(R.id.t3P);
		t3Q = (Spinner) findViewById(R.id.t3Q);
		t3R = (Spinner) findViewById(R.id.t3R);
		ftS = (Spinner) findViewById(R.id.ftS);
		atT = (Spinner) findViewById(R.id.atT);
		ttU = (Spinner) findViewById(R.id.ttU);
		ttV = (Spinner) findViewById(R.id.ttV);
		ttW = (Spinner) findViewById(R.id.ttW);
		fsY = (Spinner) findViewById(R.id.fsY);
		asZ = (Spinner) findViewById(R.id.asZ);
		tsz = (Spinner) findViewById(R.id.tsz);
		t0k = (Spinner) findViewById(R.id.tlk1);
		t2k = (Spinner) findViewById(R.id.tlk2);
		t4k = (Spinner) findViewById(R.id.tlk3);
		t6k = (Spinner) findViewById(R.id.tlk4);
		t1k = (Spinner) findViewById(R.id.ttr1);
		t3k = (Spinner) findViewById(R.id.ttr2);
		t5k = (Spinner) findViewById(R.id.ttr3);
		t7k = (Spinner) findViewById(R.id.ttr4);
		vibbottom0 = (Spinner) findViewById(R.id.vibbottom0);
		vibbottom1 = (Spinner) findViewById(R.id.vibbottom1);
		vibbottom2 = (Spinner) findViewById(R.id.vibbottom2);
		vibbottom3 = (Spinner) findViewById(R.id.vibbottom3);
		vibbottom4 = (Spinner) findViewById(R.id.vibbottom4);
		vibbottom5 = (Spinner) findViewById(R.id.vibbottom5);
		vibbottom6 = (Spinner) findViewById(R.id.vibbottom6);
		vibbottom7 = (Spinner) findViewById(R.id.vibbottom7);
		tlk_disk1 = (TextView) findViewById(R.id.tlk_disk1);
		ttr_disk1 = (TextView) findViewById(R.id.ttr_disk1);
		tlk_disk2 = (TextView) findViewById(R.id.tlk_disk2);
		ttr_disk2 = (TextView) findViewById(R.id.ttr_disk2);
		tlk_disk3 = (TextView) findViewById(R.id.tlk_disk3);
		ttr_disk3 = (TextView) findViewById(R.id.ttr_disk3);
		tlk_disk4 = (TextView) findViewById(R.id.tlk_disk4);
		ttr_disk4 = (TextView) findViewById(R.id.ttr_disk4);

		heat0Switch = (ToggleButton) findViewById(R.id.heat0set);
		heat1Switch = (ToggleButton) findViewById(R.id.heat1set);
		heat2Switch = (ToggleButton) findViewById(R.id.heat2set);
		heat3Switch = (ToggleButton) findViewById(R.id.heat3set);
		heat4Switch = (ToggleButton) findViewById(R.id.heat4set);
		heat5Switch = (ToggleButton) findViewById(R.id.heat5set);
		heat6Switch = (ToggleButton) findViewById(R.id.heat6set);
		heat7Switch = (ToggleButton) findViewById(R.id.heat7set);
		
		start = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);
		stl = (Button) findViewById(R.id.stl);
		rfl = (Button) findViewById(R.id.rfl);
		layout_tab = (LinearLayout) findViewById(R.id.layout_tab);
		content = (FrameLayout) findViewById(R.id.content);

		for (int i = 0; i < layout_tab.getChildCount(); i++) {
			layout_tab.getChildAt(i).setOnClickListener(this);
		}
		layout_tab.getChildAt(0).setBackgroundResource(
				R.drawable.tab_bac_pressed);

		btn_title = (Button) findViewById(R.id.actionbar_title);
		if (fromactivity.equals("StandardUserareaListView"))
			btn_title.setText("用户列表");
		if (fromactivity.equals("Home"))
			btn_title.setText("首页");
		if (fromactivity.equals("Yangben")) {
			btn_title.setText("样本种类");
			setEditEnable(false);
			stl.setVisibility(View.GONE);
			rfl.setVisibility(View.GONE);
		}

		btn_title.setOnClickListener(this);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		stl.setOnClickListener(this);
		rfl.setOnClickListener(this);
		heat0Switch.setOnToggleChanged(this);
		heat1Switch.setOnToggleChanged(this);
		heat2Switch.setOnToggleChanged(this);
		heat3Switch.setOnToggleChanged(this);
		heat4Switch.setOnToggleChanged(this);
		heat5Switch.setOnToggleChanged(this);
		heat6Switch.setOnToggleChanged(this);
		heat7Switch.setOnToggleChanged(this);
		
		fla.setOnItemSelectedListener(this);
		alb.setOnItemSelectedListener(this);
		tlc.setOnItemSelectedListener(this);
		tld.setOnItemSelectedListener(this);
		fhe.setOnItemSelectedListener(this);
		ahf.setOnItemSelectedListener(this);
		thg.setOnItemSelectedListener(this);
		thi.setOnItemSelectedListener(this);
		vjv.setOnItemSelectedListener(this);
		fjm.setOnItemSelectedListener(this);
		ajn.setOnItemSelectedListener(this);
		tjo.setOnItemSelectedListener(this);
		tjq.setOnItemSelectedListener(this);
		tjt.setOnItemSelectedListener(this);
		f1A.setOnItemSelectedListener(this);
		a1B.setOnItemSelectedListener(this);
		t1C.setOnItemSelectedListener(this);
		t1D.setOnItemSelectedListener(this);
		t1E.setOnItemSelectedListener(this);
		f2G.setOnItemSelectedListener(this);
		a2H.setOnItemSelectedListener(this);
		t2I.setOnItemSelectedListener(this);
		t2J.setOnItemSelectedListener(this);
		t2K.setOnItemSelectedListener(this);
		f3M.setOnItemSelectedListener(this);
		a3N.setOnItemSelectedListener(this);
		t3O.setOnItemSelectedListener(this);
		t3P.setOnItemSelectedListener(this);
		t3Q.setOnItemSelectedListener(this);
		t3R.setOnItemSelectedListener(this);
		ftS.setOnItemSelectedListener(this);
		atT.setOnItemSelectedListener(this);
		ttU.setOnItemSelectedListener(this);
		ttV.setOnItemSelectedListener(this);
		ttW.setOnItemSelectedListener(this);
		fsY.setOnItemSelectedListener(this);
		asZ.setOnItemSelectedListener(this);
		tsz.setOnItemSelectedListener(this);
		t0k.setOnItemSelectedListener(this);
		t1k.setOnItemSelectedListener(this);
		t2k.setOnItemSelectedListener(this);
		t3k.setOnItemSelectedListener(this);
		t4k.setOnItemSelectedListener(this);
		t5k.setOnItemSelectedListener(this);
		t6k.setOnItemSelectedListener(this);
		t7k.setOnItemSelectedListener(this);
		vibbottom0.setOnItemSelectedListener(this);
		vibbottom1.setOnItemSelectedListener(this);
		vibbottom2.setOnItemSelectedListener(this);
		vibbottom3.setOnItemSelectedListener(this);
		vibbottom4.setOnItemSelectedListener(this);
		vibbottom5.setOnItemSelectedListener(this);
		vibbottom6.setOnItemSelectedListener(this);
		vibbottom7.setOnItemSelectedListener(this);
		atThisActivity = true;

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(Home.messageJsonObject==null){
			Home.messageJsonObject = new JSONObject();
			try {
				Home.messageJsonObject.put("machinetype", "pnae32");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	}
	
	public void home(View v){
		if(start.getText().toString().equals("运行")){
			Intent intent = new Intent();
	        intent.setAction(StaticVar.FINISH_ACTIVITY);
	        sendBroadcast(intent);
			finish();
			}else{
				showTextToast("试验流程中不可离开");
			}
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.start:
//			Tools.removeStatusBarAndNavigationBar(Standard_RuntimeView.this);
			switch (start.getText().toString()) {
			case "运行":
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
					Home.messageJsonObject.put("runningstate", "运行");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Tools.sendMotionParams(Standard_RuntimeView.this);
				String Bag = null;
				Bag = Tools.packagebag("fla", fla.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("alb", alb.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("tlc", String.valueOf(Integer
						.parseInt(tlc.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("tld", String.valueOf(Integer
						.parseInt(tld.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("vjv", vjv.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("fhe", fhe.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("ahf", ahf.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("thg", String.valueOf(Integer
						.parseInt(thg.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("thi", String.valueOf(Integer
						.parseInt(thi.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("fjm", fjm.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("ajn", ajn.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("tjo", String.valueOf(Integer
						.parseInt(tjo.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("tjq", String.valueOf(Integer
						.parseInt(tjq.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("tjt", String.valueOf(Integer
						.parseInt(tjt.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("f1A", f1A.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("a1B", a1B.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t1C", String.valueOf(Integer
						.parseInt(t1C.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t1D", String.valueOf(Integer
						.parseInt(t1D.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t1E", String.valueOf(Integer
						.parseInt(t1E.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("f2G", f2G.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("a2H", a2H.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t2I", String.valueOf(Integer
						.parseInt(t2I.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t2J", String.valueOf(Integer
						.parseInt(t2J.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t2K", String.valueOf(Integer
						.parseInt(t2K.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("f3M", f3M.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("a3N", a3N.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t3O", String.valueOf(Integer
						.parseInt(t3O.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t3P", String.valueOf(Integer
						.parseInt(t3P.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t3Q", String.valueOf(Integer
						.parseInt(t3Q.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t3R", String.valueOf(Integer
						.parseInt(t3R.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("ftS", ftS.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("atT", atT.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("ttU", String.valueOf(Integer
						.parseInt(ttU.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("ttV", String.valueOf(Integer
						.parseInt(ttV.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("ttW", String.valueOf(Integer
						.parseInt(ttW.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("fsY", fsY.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("asZ", asZ.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("tsz", String.valueOf(Integer
						.parseInt(tsz.getSelectedItem().toString()) * 60));
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t0k", t0k.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t1k", t2k.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t2k", t4k.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t3k", t6k.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t4k", t1k.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t5k", t3k.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t6k", t5k.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("t7k", t7k.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("m0_", vibbottom0.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("m1_", vibbottom1.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("m2_", vibbottom2.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("m3_", vibbottom3.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("m4_", vibbottom4.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("m5_", vibbottom5.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("m6_", vibbottom6.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Bag = Tools.packagebag("m7_", vibbottom7.getSelectedItem().toString());
				Home.serialport.SendMsg(Bag);
				Home.serialport.SendMsg(heat0str);
				Home.serialport.SendMsg(heat1str);
				Home.serialport.SendMsg(heat2str);
				Home.serialport.SendMsg(heat3str);
				Home.serialport.SendMsg(heat4str);
				Home.serialport.SendMsg(heat5str);
				Home.serialport.SendMsg(heat6str);
				Home.serialport.SendMsg(heat7str);
				Home.serialport.SendMsg(Bag);
				String str = Tools.packagebag("sss", "3");
				Home.serialport.SendMsg(str);
				drawable = getResources().getDrawable(R.drawable.pause);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				start.setCompoundDrawables(null, drawable, null, null);
				start.setText("暂停");
				showTextToast("启动");
				
				break;
			case "暂停":
				try {
					Home.messageJsonObject.put("runningstate", "暂停");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				drawable = getResources().getDrawable(R.drawable.continue_img);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				start.setCompoundDrawables(null, drawable, null, null);
				Home.serialport.SendMsg(Tools.packagebag("sss", "1"));
				start.setText("继续");
				break;
			case "继续":
				try {
					Home.messageJsonObject.put("runningstate", "运行");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				drawable = getResources().getDrawable(R.drawable.pause);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				start.setCompoundDrawables(null, drawable, null, null);
				Home.serialport.SendMsg(Tools.packagebag("sss", "0"));
				start.setText("暂停");
				break;

			default:
				break;
			}

			setEditEnable(false);
			
			break;
		case R.id.stop:
			if(!start.getText().toString().equals("运行")){
//			Tools.removeStatusBarAndNavigationBar(Standard_RuntimeView.this);
			timer1.cancel();
			drawable = getResources().getDrawable(R.drawable.start);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			start.setCompoundDrawables(null, drawable, null, null);
			start.setText("运行");
			buttonflag = 0;

			Home.serialport.SendMsg(Tools.packagebag("sss", "5"));
			showTextToast("设备已停止");
			// reclen=0;
			// totaltime=0;
			// Message msg=handler.obtainMessage(1);
			// handler.sendMessage(msg);
			if (fromactivity.equals("StandardUserareaListView"))
				setEditEnable(true);
			
			stopCurrentProcess();
			beinterrupted = true;
			sendNote(1);
			try {
				Home.messageJsonObject.put("runningstate", "停止");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			break;
		case R.id.stl:
//			Tools.removeStatusBarAndNavigationBar(Standard_RuntimeView.this);
			sharedPreferences = getSharedPreferences("user" + usernumber
					+ "Date", Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
			/*
			 * for(int i=0;i<var.length;i++){ editor.putInt(var[i],
			 * spinner[i].getSelectedItemPosition()); }
			 */
			editor.putInt("fla", fla.getSelectedItemPosition());
			editor.putInt("alb", alb.getSelectedItemPosition());
			editor.putInt("tlc", tlc.getSelectedItemPosition());
			editor.putInt("tld", tld.getSelectedItemPosition());
			editor.putInt("fhe", fhe.getSelectedItemPosition());
			editor.putInt("ahf", ahf.getSelectedItemPosition());
			editor.putInt("thg", thg.getSelectedItemPosition());
			editor.putInt("thi", thi.getSelectedItemPosition());
			editor.putInt("vjv", vjv.getSelectedItemPosition());
			editor.putInt("fjm", fjm.getSelectedItemPosition());
			editor.putInt("ajn", ajn.getSelectedItemPosition());
			editor.putInt("tjo", tjo.getSelectedItemPosition());
			editor.putInt("tjq", tjq.getSelectedItemPosition());
			editor.putInt("tjt", tjt.getSelectedItemPosition());
			editor.putInt("f1A", f1A.getSelectedItemPosition());
			editor.putInt("a1B", a1B.getSelectedItemPosition());
			editor.putInt("t1C", t1C.getSelectedItemPosition());
			editor.putInt("t1D", t1D.getSelectedItemPosition());
			editor.putInt("t1E", t1E.getSelectedItemPosition());
			editor.putInt("f2G", f2G.getSelectedItemPosition());
			editor.putInt("a2H", a2H.getSelectedItemPosition());
			editor.putInt("t2I", t2I.getSelectedItemPosition());
			editor.putInt("t2J", t2J.getSelectedItemPosition());
			editor.putInt("t2K", t2K.getSelectedItemPosition());
			editor.putInt("f3M", f3M.getSelectedItemPosition());
			editor.putInt("a3N", a3N.getSelectedItemPosition());
			editor.putInt("t3O", t3O.getSelectedItemPosition());
			editor.putInt("t3P", t3P.getSelectedItemPosition());
			editor.putInt("t3Q", t3Q.getSelectedItemPosition());
			editor.putInt("t3R", t3R.getSelectedItemPosition());
			editor.putInt("ftS", ftS.getSelectedItemPosition());
			editor.putInt("atT", atT.getSelectedItemPosition());
			editor.putInt("ttU", ttU.getSelectedItemPosition());
			editor.putInt("ttV", ttV.getSelectedItemPosition());
			editor.putInt("ttW", ttW.getSelectedItemPosition());
			editor.putInt("fsY", fsY.getSelectedItemPosition());
			editor.putInt("asZ", asZ.getSelectedItemPosition());
			editor.putInt("tsz", tsz.getSelectedItemPosition());
			editor.putInt("tlk1", t0k.getSelectedItemPosition());
			editor.putInt("ttr1", t1k.getSelectedItemPosition());
			editor.putInt("tlk2", t2k.getSelectedItemPosition());
			editor.putInt("ttr2", t3k.getSelectedItemPosition());
			editor.putInt("tlk3", t4k.getSelectedItemPosition());
			editor.putInt("ttr3", t5k.getSelectedItemPosition());
			editor.putInt("tlk4", t6k.getSelectedItemPosition());
			editor.putInt("ttr4", t7k.getSelectedItemPosition());
			editor.putInt("vibbottom0", vibbottom0.getSelectedItemPosition());
			editor.putInt("vibbottom1", vibbottom1.getSelectedItemPosition());
			editor.putInt("vibbottom2", vibbottom2.getSelectedItemPosition());
			editor.putInt("vibbottom3", vibbottom3.getSelectedItemPosition());
			editor.putInt("vibbottom4", vibbottom4.getSelectedItemPosition());
			editor.putInt("vibbottom5", vibbottom5.getSelectedItemPosition());
			editor.putInt("vibbottom6", vibbottom6.getSelectedItemPosition());
			editor.putInt("vibbottom7", vibbottom7.getSelectedItemPosition());
			editor.putBoolean("heat1bol", heat1bol);
			editor.putBoolean("heat2bol", heat2bol);
			editor.putBoolean("heat3bol", heat3bol);
			editor.putBoolean("heat4bol", heat4bol);
			editor.putBoolean("heat5bol", heat5bol);
			editor.putBoolean("heat6bol", heat6bol);
			editor.putBoolean("heat7bol", heat7bol);
			editor.putBoolean("heat8bol", heat8bol);
			editor.commit();
			showTextToast("已成功保存到本地");
			/*
			 * Time t=new Time("GMT+8"); t.setToNow(); String
			 * time=String.valueOf
			 * (t.year)+"-"+String.valueOf(t.month)+"-"+String
			 * .valueOf(t.monthDay
			 * )+"-"+String.valueOf(t.hour)+"-"+String.valueOf
			 * (t.minute)+"-"+String.valueOf(t.second); String content="保存的数据";
			 * try { FileOutputStream fileOutputStream=openFileOutput(time,
			 * MODE_PRIVATE); fileOutputStream.write(content);
			 * fileOutputStream.flush(); fileOutputStream.close();
			 * showTextToast("已成功保存到本地"); } catch (Exception e) { // TODO:
			 * handle exception }}
			 */
			break;
		case R.id.rfl:
			loadData();
			break;
		case R.id.actionbar_title:
			if(start.getText().toString().equals("运行")){
			finish();
			}else{
				showTextToast("试验流程中不可离开");
			}
			break;
			
		case R.id.liejie:
			/*for (int i = 0; i < layout_tab.getChildCount(); i++) {
				layout_tab.getChildAt(i).setBackgroundResource(
						R.drawable.tab_bac);
			}
			layout_tab.getChildAt(0).setBackgroundResource(
					R.drawable.tab_bac_pressed);
			for (int i = 0; i < content.getChildCount(); i++) {
				content.getChildAt(i).setVisibility(View.GONE);
			}
			findViewById(R.id.layout_liejie).setVisibility(View.VISIBLE);*/
			uiChange(0);
			break;
		case R.id.cizhuhunyun:
			/*for (int i = 0; i < layout_tab.getChildCount(); i++) {
				layout_tab.getChildAt(i).setBackgroundResource(
						R.drawable.tab_bac);
			}
			layout_tab.getChildAt(1).setBackgroundResource(
					R.drawable.tab_bac_pressed);
			for (int i = 0; i < content.getChildCount(); i++) {
				content.getChildAt(i).setVisibility(View.GONE);
			}
			findViewById(R.id.layout_cizhuhunyun).setVisibility(View.VISIBLE);*/
			uiChange(1);
			break;
		case R.id.jiehe:
			/*for (int i = 0; i < layout_tab.getChildCount(); i++) {
				layout_tab.getChildAt(i).setBackgroundResource(
						R.drawable.tab_bac);
			}
			layout_tab.getChildAt(2).setBackgroundResource(
					R.drawable.tab_bac_pressed);
			for (int i = 0; i < content.getChildCount(); i++) {
				content.getChildAt(i).setVisibility(View.GONE);
			}
			findViewById(R.id.layout_jiehe).setVisibility(View.VISIBLE);*/
			uiChange(2);
			break;
		case R.id.xidi1:
			/*for (int i = 0; i < layout_tab.getChildCount(); i++) {
				layout_tab.getChildAt(i).setBackgroundResource(
						R.drawable.tab_bac);
			}
			layout_tab.getChildAt(3).setBackgroundResource(
					R.drawable.tab_bac_pressed);
			for (int i = 0; i < content.getChildCount(); i++) {
				content.getChildAt(i).setVisibility(View.GONE);
			}
			findViewById(R.id.layout_xidi1).setVisibility(View.VISIBLE);*/
			uiChange(3);
			break;
		case R.id.xidi2:
			/*for (int i = 0; i < layout_tab.getChildCount(); i++) {
				layout_tab.getChildAt(i).setBackgroundResource(
						R.drawable.tab_bac);
			}
			layout_tab.getChildAt(4).setBackgroundResource(
					R.drawable.tab_bac_pressed);
			for (int i = 0; i < content.getChildCount(); i++) {
				content.getChildAt(i).setVisibility(View.GONE);
			}
			findViewById(R.id.layout_xidi2).setVisibility(View.VISIBLE);*/
			uiChange(4);
			break;
		case R.id.xidi3:
			/*for (int i = 0; i < layout_tab.getChildCount(); i++) {
				layout_tab.getChildAt(i).setBackgroundResource(
						R.drawable.tab_bac);
			}
			layout_tab.getChildAt(5).setBackgroundResource(
					R.drawable.tab_bac_pressed);
			for (int i = 0; i < content.getChildCount(); i++) {
				content.getChildAt(i).setVisibility(View.GONE);
			}
			findViewById(R.id.layout_xidi3).setVisibility(View.VISIBLE);*/
			uiChange(5);
			break;
		case R.id.xituo:
			/*for (int i = 0; i < layout_tab.getChildCount(); i++) {
				layout_tab.getChildAt(i).setBackgroundResource(
						R.drawable.tab_bac);
			}
			layout_tab.getChildAt(6).setBackgroundResource(
					R.drawable.tab_bac_pressed);
			for (int i = 0; i < content.getChildCount(); i++) {
				content.getChildAt(i).setVisibility(View.GONE);
			}
			findViewById(R.id.layout_xituo).setVisibility(View.VISIBLE);*/
			uiChange(6);
			break;
		case R.id.cizhushifang:
			/*for (int i = 0; i < layout_tab.getChildCount(); i++) {
				layout_tab.getChildAt(i).setBackgroundResource(
						R.drawable.tab_bac);
			}
			layout_tab.getChildAt(7).setBackgroundResource(
					R.drawable.tab_bac_pressed);
			for (int i = 0; i < content.getChildCount(); i++) {
				content.getChildAt(i).setVisibility(View.GONE);
			}
			findViewById(R.id.layout_cizhushifang).setVisibility(View.VISIBLE);*/
			uiChange(7);
			break;
		default:
			break;

		}
	}
	
	void uiChange(int index){
		for (int i = 0; i < layout_tab.getChildCount(); i++) {
			layout_tab.getChildAt(i).setBackgroundResource(
					R.drawable.tab_bac);
		}
		layout_tab.getChildAt(index).setBackgroundResource(
				R.drawable.tab_bac_pressed);
		for (int i = 0; i < content.getChildCount(); i++) {
			content.getChildAt(i).setVisibility(View.GONE);
		}
		content.getChildAt(index).setVisibility(View.VISIBLE);
	}

	private void loadJsonData() {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(intent.getStringExtra("jsonStr"));
			fla.setSelection(jsonObject.getInt("fla") - 1);
			alb.setSelection(jsonObject.getInt("alb") - 1);
			tlc.setSelection(jsonObject.getInt("tlc"));
			tld.setSelection(jsonObject.getInt("tld"));
			fhe.setSelection(jsonObject.getInt("fhe") - 1);
			ahf.setSelection(jsonObject.getInt("ahf") - 1);
			thg.setSelection(jsonObject.getInt("thg"));
			thi.setSelection(jsonObject.getInt("thi"));
			vjv.setSelection(jsonObject.getInt("vjv"));
			fjm.setSelection(jsonObject.getInt("fjm") - 1);
			ajn.setSelection(jsonObject.getInt("ajn") - 1);
			tjo.setSelection(jsonObject.getInt("tjo"));
			tjq.setSelection(jsonObject.getInt("tjq"));
			tjt.setSelection(jsonObject.getInt("tjt"));
			f1A.setSelection(jsonObject.getInt("f1A") - 1);
			a1B.setSelection(jsonObject.getInt("a1B") - 1);
			t1C.setSelection(jsonObject.getInt("t1C"));
			t1D.setSelection(jsonObject.getInt("t1D"));
			t1E.setSelection(jsonObject.getInt("t1E"));
			f2G.setSelection(jsonObject.getInt("f2G") - 1);
			a2H.setSelection(jsonObject.getInt("a2H") - 1);
			t2I.setSelection(jsonObject.getInt("t2I"));
			t2J.setSelection(jsonObject.getInt("t2J"));
			t2K.setSelection(jsonObject.getInt("t2K"));
			f3M.setSelection(jsonObject.getInt("f3M") - 1);
			a3N.setSelection(jsonObject.getInt("a3N") - 1);
			t3O.setSelection(jsonObject.getInt("t3O"));
			t3P.setSelection(jsonObject.getInt("t3P"));
			t3Q.setSelection(jsonObject.getInt("t3Q"));
			t3R.setSelection(jsonObject.getInt("t3R"));
			ftS.setSelection(jsonObject.getInt("ftS") - 1);
			atT.setSelection(jsonObject.getInt("atT") - 1);
			ttU.setSelection(jsonObject.getInt("ttU"));
			ttV.setSelection(jsonObject.getInt("ttV"));
			ttW.setSelection(jsonObject.getInt("ttW"));
			fsY.setSelection(jsonObject.getInt("fsY") - 1);
			asZ.setSelection(jsonObject.getInt("asZ") - 1);
			tsz.setSelection(jsonObject.getInt("tsz"));
			t0k.setSelection(jsonObject.getInt("tlk")-29);
			t1k.setSelection(jsonObject.getInt("ttr")-29);
			t2k.setSelection(jsonObject.getInt("tlk")-29);
			t3k.setSelection(jsonObject.getInt("ttr")-29);
			t4k.setSelection(jsonObject.getInt("tlk")-29);
			t5k.setSelection(jsonObject.getInt("ttr")-29);
			t6k.setSelection(jsonObject.getInt("tlk")-29);
			t7k.setSelection(jsonObject.getInt("ttr")-29);
			System.out.println("t7k.setSelection");
			System.out.println(jsonObject.getInt("vibbottom0"));
			vibbottom0.setSelection(jsonObject.getInt("vibbottom0"));
			vibbottom1.setSelection(jsonObject.getInt("vibbottom1"));
			vibbottom2.setSelection(jsonObject.getInt("vibbottom2"));
			vibbottom3.setSelection(jsonObject.getInt("vibbottom3"));
			vibbottom4.setSelection(jsonObject.getInt("vibbottom4"));
			vibbottom5.setSelection(jsonObject.getInt("vibbottom5"));
			vibbottom6.setSelection(jsonObject.getInt("vibbottom6"));
			vibbottom7.setSelection(jsonObject.getInt("vibbottom7"));
			System.out.println("setSelectionsetSelectionsetSelectionsetSelection");
			heat0Switch.setChecked(jsonObject.getBoolean("heat1bol"));
			System.out.println(jsonObject.getBoolean("heat1bol"));
			heat1Switch.setChecked(jsonObject.getBoolean("heat2bol"));
			heat2Switch.setChecked(jsonObject.getBoolean("heat3bol"));
			heat3Switch.setChecked(jsonObject.getBoolean("heat4bol"));
			heat4Switch.setChecked(jsonObject.getBoolean("heat5bol"));
			heat5Switch.setChecked(jsonObject.getBoolean("heat6bol"));
			heat6Switch.setChecked(jsonObject.getBoolean("heat7bol"));
			heat7Switch.setChecked(jsonObject.getBoolean("heat8bol"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadData() {
		// TODO Auto-generated method stub
		sharedPreferences = getSharedPreferences("user" + usernumber + "Date",
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		if (sharedPreferences.getInt("fla", -1) == -1) {
			showTextToast("没有保存记录");
		} else {
			/*
			 * for(int i=0;i<var.length;i++){
			 * spinner[i].setSelection(sharedPreferences.getInt(var[i], 0)); }
			 */

			fla.setSelection(sharedPreferences.getInt("fla", 0));
			alb.setSelection(sharedPreferences.getInt("alb", 0));
			tlc.setSelection(sharedPreferences.getInt("tlc", 0));
			tld.setSelection(sharedPreferences.getInt("tld", 0));
			fhe.setSelection(sharedPreferences.getInt("fhe", 0));
			ahf.setSelection(sharedPreferences.getInt("ahf", 0));
			thg.setSelection(sharedPreferences.getInt("thg", 0));
			thi.setSelection(sharedPreferences.getInt("thi", 0));
			vjv.setSelection(sharedPreferences.getInt("vjv", 0));
			fjm.setSelection(sharedPreferences.getInt("fjm", 0));
			ajn.setSelection(sharedPreferences.getInt("ajn", 0));
			tjo.setSelection(sharedPreferences.getInt("tjo", 0));
			tjq.setSelection(sharedPreferences.getInt("tjq", 0));
			tjt.setSelection(sharedPreferences.getInt("tjt", 0));
			f1A.setSelection(sharedPreferences.getInt("f1A", 0));
			a1B.setSelection(sharedPreferences.getInt("a1B", 0));
			t1C.setSelection(sharedPreferences.getInt("t1C", 0));
			t1D.setSelection(sharedPreferences.getInt("t1D", 0));
			t1E.setSelection(sharedPreferences.getInt("t1E", 0));
			f2G.setSelection(sharedPreferences.getInt("f2G", 0));
			a2H.setSelection(sharedPreferences.getInt("a2H", 0));
			t2I.setSelection(sharedPreferences.getInt("t2I", 0));
			t2J.setSelection(sharedPreferences.getInt("t2J", 0));
			t2K.setSelection(sharedPreferences.getInt("t2K", 0));
			f3M.setSelection(sharedPreferences.getInt("f3M", 0));
			a3N.setSelection(sharedPreferences.getInt("a3N", 0));
			t3O.setSelection(sharedPreferences.getInt("t3O", 0));
			t3P.setSelection(sharedPreferences.getInt("t3P", 0));
			t3Q.setSelection(sharedPreferences.getInt("t3Q", 0));
			t3R.setSelection(sharedPreferences.getInt("t3R", 0));
			ftS.setSelection(sharedPreferences.getInt("ftS", 0));
			atT.setSelection(sharedPreferences.getInt("atT", 0));
			ttU.setSelection(sharedPreferences.getInt("ttU", 0));
			ttV.setSelection(sharedPreferences.getInt("ttV", 0));
			ttW.setSelection(sharedPreferences.getInt("ttW", 0));
			fsY.setSelection(sharedPreferences.getInt("fsY", 0));
			asZ.setSelection(sharedPreferences.getInt("asZ", 0));
			tsz.setSelection(sharedPreferences.getInt("tsz", 0));
			t0k.setSelection(sharedPreferences.getInt("tlk1", 0));
			t1k.setSelection(sharedPreferences.getInt("ttr1", 0));
			t2k.setSelection(sharedPreferences.getInt("tlk2", 0));
			t3k.setSelection(sharedPreferences.getInt("ttr2", 0));
			t4k.setSelection(sharedPreferences.getInt("tlk3", 0));
			t5k.setSelection(sharedPreferences.getInt("ttr3", 0));
			t6k.setSelection(sharedPreferences.getInt("tlk4", 0));
			t7k.setSelection(sharedPreferences.getInt("ttr4", 0));
			vibbottom0.setSelection(sharedPreferences.getInt("vibbottom0", 0));
			vibbottom1.setSelection(sharedPreferences.getInt("vibbottom1", 0));
			vibbottom2.setSelection(sharedPreferences.getInt("vibbottom2", 0));
			vibbottom3.setSelection(sharedPreferences.getInt("vibbottom3", 0));
			vibbottom4.setSelection(sharedPreferences.getInt("vibbottom4", 0));
			vibbottom5.setSelection(sharedPreferences.getInt("vibbottom5", 0));
			vibbottom6.setSelection(sharedPreferences.getInt("vibbottom6", 0));
			vibbottom7.setSelection(sharedPreferences.getInt("vibbottom7", 0));
			heat0Switch.setChecked(sharedPreferences.getBoolean("heat1bol", true));
			heat1Switch.setChecked(sharedPreferences.getBoolean("heat2bol", true));
			heat2Switch.setChecked(sharedPreferences.getBoolean("heat3bol", true));
			heat3Switch.setChecked(sharedPreferences.getBoolean("heat4bol", true));
			heat4Switch.setChecked(sharedPreferences.getBoolean("heat5bol", true));
			heat5Switch.setChecked(sharedPreferences.getBoolean("heat6bol", true));
			heat6Switch.setChecked(sharedPreferences.getBoolean("heat7bol", true));
			heat7Switch.setChecked(sharedPreferences.getBoolean("heat8bol", true));

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && buttonflag == 1) {
			count = 0;
			timer1 = new Timer();
			showTextToast("设备正在运行中，返回将保存当前界面并回到桌面，再次点击返回桌面");
			buttonflag = 2;
			timer1.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO 自动生成的方法存根

					count++;
				}
			}, 1000, 1000);

			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK && buttonflag == 2) {
			if (count < 2) {
				buttonflag = 1;
				count = 0;
				timer1.cancel();
				moveTaskToBack(true);
			} else {
				showTextToast("设备正在运行中，返回将保存当前界面并回到桌面，再次点击返回桌面");
				timer1.cancel();
				buttonflag = 1;
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	

	private void setEditEnable(boolean en) {
		/*
		 * for (int i = 0; i < spinner.length; i++) spinner[i].setEnabled(en);
		 */

		fla.setEnabled(en);
		alb.setEnabled(en);
		tlc.setEnabled(en);
		tld.setEnabled(en);
		fhe.setEnabled(en);
		ahf.setEnabled(en);
		thg.setEnabled(en);
		thi.setEnabled(en);
		vjv.setEnabled(en);
		fjm.setEnabled(en);
		ajn.setEnabled(en);
		tjo.setEnabled(en);
		tjq.setEnabled(en);
		tjt.setEnabled(en);
		f1A.setEnabled(en);
		a1B.setEnabled(en);
		t1C.setEnabled(en);
		t1D.setEnabled(en);
		t1E.setEnabled(en);
		f2G.setEnabled(en);
		a2H.setEnabled(en);
		t2I.setEnabled(en);
		t2J.setEnabled(en);
		t2K.setEnabled(en);
		f3M.setEnabled(en);
		a3N.setEnabled(en);
		t3O.setEnabled(en);
		t3P.setEnabled(en);
		t3Q.setEnabled(en);
		t3R.setEnabled(en);
		ftS.setEnabled(en);
		atT.setEnabled(en);
		ttU.setEnabled(en);
		ttV.setEnabled(en);
		ttW.setEnabled(en);
		fsY.setEnabled(en);
		asZ.setEnabled(en);
		tsz.setEnabled(en);
		t0k.setEnabled(en);
		t1k.setEnabled(en);
		t2k.setEnabled(en);
		t3k.setEnabled(en);
		t4k.setEnabled(en);
		t5k.setEnabled(en);
		t6k.setEnabled(en);
		t7k.setEnabled(en);
		vibbottom0.setEnabled(en);
		vibbottom1.setEnabled(en);
		vibbottom2.setEnabled(en);
		vibbottom3.setEnabled(en);
		vibbottom4.setEnabled(en);
		vibbottom5.setEnabled(en);
		vibbottom6.setEnabled(en);
		vibbottom7.setEnabled(en);
		heat0Switch.setEnabled(en);
		heat1Switch.setEnabled(en);
		heat2Switch.setEnabled(en);
		heat3Switch.setEnabled(en);
		heat4Switch.setEnabled(en);
		heat5Switch.setEnabled(en);
		heat6Switch.setEnabled(en);
		heat7Switch.setEnabled(en);

		stl.setEnabled(en);
		rfl.setEnabled(en);
	}
	
	protected void sendNote(int i) {
		// TODO Auto-generated method stub
		switch (i) {
		case 1:
			try {
				
				JSONObject object = new JSONObject();
				object.put("messagetype", "notice");
				object.put("paramstype", "standard");
				if(Home.messageJsonObject.get("paramstitle")!=null)
				object.put("paramstitle", Home.messageJsonObject.get("paramstitle").toString());
				if(beinterrupted){
					object.put("noticecontent", title.getText().toString()+"的实验流程被终止");
				}else{
					object.put("noticecontent", title.getText().toString()+"的实验流程已完成");
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
			
//			Home.server.sendMsg("over");
			break;

		default:
			break;
		}
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Home.TheActivityIs = null;
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Home.messageJsonObject=null;
		super.onDestroy();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		saveToMessageJson();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	private void saveToMessageJson(){
		if(Home.messageJsonObject==null){
			Home.messageJsonObject = new JSONObject();
			try {
				Home.messageJsonObject.put("machinetype", "pnae32");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
		try {
			Home.messageJsonObject.put("messagetype", "synchronous");
			Home.messageJsonObject.put("paramstitle", title.getText().toString());
			Home.messageJsonObject.put("paramstype", "standard");
			JSONArray array = new JSONArray();
			for (int i = 0; i < layout_tab.getChildCount(); i++) {
				array.put(((TextView) layout_tab.getChildAt(i)).getText().toString());
			}
			Home.messageJsonObject.put("paramsgroup", array);
			
			array = new JSONArray();
			
			JSONObject jsonObject= new JSONObject();
			jsonObject.put("混合速度", fla.getSelectedItem().toString());
			jsonObject.put("混合幅度", alb.getSelectedItem().toString());
			jsonObject.put("混合时间", tlc.getSelectedItem().toString());
			jsonObject.put("静置反应时间", tld.getSelectedItem().toString());
			jsonObject.put("振荡起始位", vibbottom0.getSelectedItem().toString());
//			jsonObject.put("裂解1温度", t0k.getSelectedItem().toString());
//			jsonObject.put("裂解2温度", t2k.getSelectedItem().toString());
//			jsonObject.put("裂解3温度", t4k.getSelectedItem().toString());
//			jsonObject.put("裂解4温度", t6k.getSelectedItem().toString());
//			jsonObject.put("裂解1加热器", heat0Switch.isChecked());
//			jsonObject.put("裂解2加热器", heat1Switch.isChecked());
//			jsonObject.put("裂解3加热器", heat2Switch.isChecked());
//			jsonObject.put("裂解4加热器", heat3Switch.isChecked());
			array.put(jsonObject);
			
			jsonObject= new JSONObject();
			jsonObject.put("混合速度", fhe.getSelectedItem().toString());
			jsonObject.put("混合幅度", ahf.getSelectedItem().toString());
			jsonObject.put("混合时间", thg.getSelectedItem().toString());
			jsonObject.put("磁珠吸附时间", thi.getSelectedItem().toString());
			jsonObject.put("磁珠转移速度", vjv.getSelectedItem().toString());
			jsonObject.put("振荡起始位", vibbottom1.getSelectedItem().toString());
			array.put(jsonObject);
			
			jsonObject= new JSONObject();
			jsonObject.put("混合速度", fjm.getSelectedItem().toString());
			jsonObject.put("混合幅度", ajn.getSelectedItem().toString());
			jsonObject.put("混合时间", tjo.getSelectedItem().toString());
			jsonObject.put("静置反应时间", tjq.getSelectedItem().toString());
			jsonObject.put("磁珠吸附时间", tjt.getSelectedItem().toString());
			jsonObject.put("振荡起始位", vibbottom2.getSelectedItem().toString());
			array.put(jsonObject);
			
			jsonObject= new JSONObject();
			jsonObject.put("混合速度", f1A.getSelectedItem().toString());
			jsonObject.put("混合幅度", a1B.getSelectedItem().toString());
			jsonObject.put("混合时间", t1C.getSelectedItem().toString());
			jsonObject.put("静置反应时间", t1D.getSelectedItem().toString());
			jsonObject.put("磁珠吸附时间", t1E.getSelectedItem().toString());
			jsonObject.put("振荡起始位", vibbottom3.getSelectedItem().toString());
			array.put(jsonObject);
			
			jsonObject= new JSONObject();
			jsonObject.put("混合速度", f2G.getSelectedItem().toString());
			jsonObject.put("混合幅度", a2H.getSelectedItem().toString());
			jsonObject.put("混合时间", t2I.getSelectedItem().toString());
			jsonObject.put("静置反应时间", t2J.getSelectedItem().toString());
			jsonObject.put("磁珠吸附时间", t2K.getSelectedItem().toString());
			jsonObject.put("振荡起始位", vibbottom4.getSelectedItem().toString());
			array.put(jsonObject);
			
			jsonObject= new JSONObject();
			jsonObject.put("混合速度", f3M.getSelectedItem().toString());
			jsonObject.put("混合幅度", a3N.getSelectedItem().toString());
			jsonObject.put("混合时间", t3O.getSelectedItem().toString());
			jsonObject.put("静置反应时间", t3P.getSelectedItem().toString());
			jsonObject.put("磁珠吸附时间", t3Q.getSelectedItem().toString());
			jsonObject.put("洗涤剂挥发时间", t3R.getSelectedItem().toString());
			jsonObject.put("振荡起始位", vibbottom5.getSelectedItem().toString());
			array.put(jsonObject);
			
			jsonObject= new JSONObject();
			jsonObject.put("混合速度", ftS.getSelectedItem().toString());
			jsonObject.put("混合幅度", atT.getSelectedItem().toString());
			jsonObject.put("混合时间", ttU.getSelectedItem().toString());
			jsonObject.put("静置反应时间", ttV.getSelectedItem().toString());
			jsonObject.put("磁珠吸附时间", ttW.getSelectedItem().toString());
			jsonObject.put("振荡起始位", vibbottom7.getSelectedItem().toString());
//			jsonObject.put("洗脱1温度", t1k.getSelectedItem().toString());
//			jsonObject.put("洗脱2温度", t3k.getSelectedItem().toString());
//			jsonObject.put("洗脱3温度", t5k.getSelectedItem().toString());
//			jsonObject.put("洗脱4温度", t7k.getSelectedItem().toString());
//			jsonObject.put("洗脱1加热器", heat4Switch.isChecked());
//			jsonObject.put("洗脱2加热器", heat5Switch.isChecked());
//			jsonObject.put("洗脱3加热器", heat6Switch.isChecked());
//			jsonObject.put("洗脱4加热器", heat7Switch.isChecked());
			array.put(jsonObject);
			
			jsonObject= new JSONObject();
			jsonObject.put("混合速度", fsY.getSelectedItem().toString());
			jsonObject.put("混合幅度", asZ.getSelectedItem().toString());
			jsonObject.put("混合时间", tsz.getSelectedItem().toString());
			jsonObject.put("振荡起始位", vibbottom7.getSelectedItem().toString());
			array.put(jsonObject);
			
			Home.messageJsonObject.put("paramschild", array);
			
			putTemperatureDataIntoMessageJsonObject();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void putTemperatureDataIntoMessageJsonObject() {
		// TODO Auto-generated method stub
		JSONArray array = new JSONArray();
		array.put(t0k.getSelectedItem().toString());
		array.put(t1k.getSelectedItem().toString());
		array.put(t2k.getSelectedItem().toString());
		array.put(t3k.getSelectedItem().toString());
		array.put(t4k.getSelectedItem().toString());
		array.put(t5k.getSelectedItem().toString());
		array.put(t6k.getSelectedItem().toString());
		array.put(t7k.getSelectedItem().toString());
		array.put(heat0Switch.isChecked());
		array.put(heat1Switch.isChecked());
		array.put(heat2Switch.isChecked());
		array.put(heat3Switch.isChecked());
		array.put(heat4Switch.isChecked());
		array.put(heat5Switch.isChecked());
		array.put(heat6Switch.isChecked());
		array.put(heat7Switch.isChecked());
		try {
			Home.messageJsonObject.put("paramstemperature", array);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onToggle(View view, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.heat0set:
			if (isChecked) {
				heat0str = Tools.packagebag("w0r", "1");
				heat1bol = true;
			} else {
				heat0str = Tools.packagebag("w0r", "0");
				heat1bol = false;
			}
			try {
				((JSONArray)(Home.messageJsonObject.get("paramstemperature"))).put(8, isChecked);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		case R.id.heat1set:
			if (isChecked) {
				heat1str = Tools.packagebag("w1r", "1");
				heat2bol = true;
			} else {
				heat1str = Tools.packagebag("w1r", "0");
				heat2bol = false;
			}
			try {
				((JSONArray)(Home.messageJsonObject.get("paramstemperature"))).put(9, isChecked);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		case R.id.heat2set:
			if (isChecked) {
				heat2str = Tools.packagebag("w2r", "1");
				heat3bol = true;
			} else {
				heat2str = Tools.packagebag("w2r", "0");
				heat3bol = false;
			}
			try {
				((JSONArray)(Home.messageJsonObject.get("paramstemperature"))).put(10, isChecked);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		case R.id.heat3set:
			if (isChecked) {
				heat3str = Tools.packagebag("w3r", "1");
				heat4bol = true;
			} else {
				heat3str = Tools.packagebag("w3r", "0");
				heat4bol = false;
			}
			try {
				((JSONArray)(Home.messageJsonObject.get("paramstemperature"))).put(11, isChecked);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		case R.id.heat4set:
			if (isChecked) {
				heat4str = Tools.packagebag("w4r", "1");
				heat5bol = true;
			} else {
				heat4str = Tools.packagebag("w4r", "0");
				heat5bol = false;
			}
			try {
				((JSONArray)(Home.messageJsonObject.get("paramstemperature"))).put(12, isChecked);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		case R.id.heat5set:
			if (isChecked) {
				heat5str = Tools.packagebag("w5r", "1");
				heat6bol = true;
			} else {
				heat5str = Tools.packagebag("w5r", "0");
				heat6bol = false;
			}
			try {
				((JSONArray)(Home.messageJsonObject.get("paramstemperature"))).put(13, isChecked);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		case R.id.heat6set:
			if (isChecked) {
				heat6str = Tools.packagebag("w6r", "1");
				heat7bol = true;
			} else {
				heat6str = Tools.packagebag("w6r", "0");
				heat7bol = false;
			}
			try {
				((JSONArray)(Home.messageJsonObject.get("paramstemperature"))).put(14, isChecked);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		case R.id.heat7set:
			if (isChecked) {
				heat7str = Tools.packagebag("w7r", "1");
				heat8bol = true;
			} else {
				heat7str = Tools.packagebag("w7r", "0");
				heat8bol = false;
			}
			try {
				((JSONArray)(Home.messageJsonObject.get("paramstemperature"))).put(15, isChecked);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		}
	}

}
