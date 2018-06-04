package com.xm.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socketserialport.R;
import com.xm.Bean.ContentBean;
import com.xm.Bean.MessageBean;
import com.xm.Bean.UserBean;
import com.xm.Model.ChildData;
import com.xm.Model.GroupData;
import com.xm.Widget.CustomadeDialog;
import com.xm.Widget.CustomadeProgressBar;
import com.xm.adapter.ExpandableAdapter;
import com.xm.adapter.ExpandableAdapter.GroupViewHolder;
import com.xm.adapter.GroupAdapter;
import com.xm.mina.Client;
import com.xm.tools.MyAnimations;
import com.xm.tools.Tools;
import com.xm.var.StaticVar;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

public class NonStandard_RuntimeView extends ExpandableListActivity implements
		OnClickListener, OnItemSelectedListener, OnToggleChanged {
	public static final int SMOOTHTOTOP = 0;
	private ListView lv_group;
	private List<String> data;
	private List<GroupData> groupData;// 定义组数据
	private List<List<ChildData>> childData;// 定义组中的子数据
	private List<ChildData> tempchild;
	Button collapse;
	Button empty;
	static Button implement;
	Button stop;
	Button savelist;
	Button saveas;
	Button list;

	Button cancel_btn;
	TextView name;
	TextView title;
	Button btn_title;
	Toast toast;
	Button add;
	ImageButton up;
	ImageButton down;
	ImageButton insert;
	ImageButton edit;
	ImageButton remove;
	ImageButton cancel;
	private LinearLayout action;
	private LinearLayout linearlayout;
	static ExpandableAdapter adapter;
	GroupAdapter groupAdapter;
	LayoutInflater inflater;
	View setDialogView; // 非标用户区添加子listview的dialog
	View tempview = null;
	CustomadeDialog customadeDialog;
	CustomadeDialog renameDialog;
	ExpandableListView expandablelistview;
	int gposition = -1;// 记录被点击的组Listview的position
	int cposition = -1;// 记录被点击的子Listview的position
	int gclickp;

	String tag;
	String stpstr;
	String uskstr;
	String usrstr;
	String vibbottomstr;
	String ussstr;
	String us9str;
	String us0str;
	String usystr;
	public static Handler handler;
	ByteArrayOutputStream outStream;
	private PopupWindow popupWindow;
	int Number;
	private CustomadeDialog alertDialog;

	private CustomadeDialog addChildDialog;
	private CustomadeDialog insertChildDialog;
	private CustomadeDialog editChildDialog;
	DoThread thread;

	int flAg = 0;
	Spinner t0k;
	Spinner t1k;
	Spinner t2k;
	Spinner t3k;
	Spinner t4k;
	Spinner t5k;
	Spinner t6k;
	Spinner t7k;
	TextView temp1;
	TextView temp2;
	TextView temp3;
	TextView temp4;
	TextView temp5;
	TextView temp6;
	TextView temp7;
	TextView temp8;
	private ToggleButton heat0Switch;
	private ToggleButton heat1Switch;
	private ToggleButton heat2Switch;
	private ToggleButton heat3Switch;
	private ToggleButton heat4Switch;
	private ToggleButton heat5Switch;
	private ToggleButton heat6Switch;
	private ToggleButton heat7Switch;
	boolean heat1bol = false;
	boolean heat2bol = false;
	boolean heat3bol = false;
	boolean heat4bol = false;
	boolean heat5bol = false;
	boolean heat6bol = false;
	boolean heat7bol = false;
	boolean heat8bol = false;
	private String heat0str = Tools.packagebag("w0r", "0");
	private String heat1str = Tools.packagebag("w1r", "0");
	private String heat2str = Tools.packagebag("w2r", "0");
	private String heat3str = Tools.packagebag("w3r", "0");
	private String heat4str = Tools.packagebag("w4r", "0");
	private String heat5str = Tools.packagebag("w5r", "0");
	private String heat6str = Tools.packagebag("w6r", "0");
	private String heat7str = Tools.packagebag("w7r", "0");
	Drawable drawable;
	boolean gateState; // 门状态，true为关，false为开
	CustomadeDialog dialog = null;
	boolean expAllow = true; // 是否允许实验

	private boolean areButtonsShowing;
	private RelativeLayout composerButtonsWrapper;
	private ImageView composerButtonsShowHideButtonIcon;
	private RelativeLayout composerButtonsShowHideButton;

	private CustomadeProgressBar progressBar;
	private boolean beinterrupted;
	private int temp_threshold = 29;
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner vibVel;
	private Spinner vibAmp;
	private Spinner vibBottom;
	private EditText second;
	private Spinner linkageVel;
	private Object TAG;

	String saveFileName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 */

		setContentView(R.layout.layout_nonstandard_runtimeview);
		Home.TheActivityIs = "NonStandard_RuntimeView";
		// MainActivity.activity = MainActivity.getRunningActivityName(this);
		// LoadListDate();
		init();
		initpath();
		datainit();
		initHandler();
		adapter = new ExpandableAdapter(this, groupData, childData, this);
		expandablelistview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		Intent intent = getIntent();
		if (intent.getAction() != null) {
			loadData(intent);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Home.currentActivity = NonStandard_RuntimeView.this;
	}

	private void initpath() {
		// TODO Auto-generated method stub
		MyAnimations.initOffset(NonStandard_RuntimeView.this);
		composerButtonsWrapper = (RelativeLayout) findViewById(R.id.composer_buttons_wrapper);

		composerButtonsShowHideButton = (RelativeLayout) findViewById(R.id.composer_buttons_show_hide_button);
		composerButtonsShowHideButtonIcon = (ImageView) findViewById(R.id.composer_buttons_show_hide_button_icon);

		composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (!areButtonsShowing) {
					composerButtonsWrapper
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									if (areButtonsShowing)
										composerButtonsShowHideButton
												.performClick();
								}
							});

					MyAnimations.startAnimationsIn(composerButtonsWrapper, 300);
					composerButtonsShowHideButtonIcon
							.startAnimation(MyAnimations.getRotateAnimation(0,
									-45, 300));
					composerButtonsWrapper
							.setBackgroundResource(R.drawable.menu_back);

					action.setVisibility(View.INVISIBLE);
					if (tempview != null) {
						tempview.setBackgroundResource(R.color.transplant);
						tempview = null;
						childData.get(gposition).get(cposition)
								.setCheckflag(false);
					}

					/**
					 * 透明度动画
					 */
					ValueAnimator animator = ValueAnimator.ofFloat(0.2f, 1f);
					animator.addUpdateListener(new AnimatorUpdateListener() {

						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							// TODO Auto-generated method stub
							// v.setAlpha((float) animation.getAnimatedValue());
							composerButtonsWrapper.setAlpha(((float) animation
									.getAnimatedValue() - 0.2f) * 5 / 4);
						}
					});
					animator.setDuration(300);
					animator.start();
				} else {
					composerButtonsWrapper.setOnClickListener(null);
					composerButtonsWrapper.setClickable(false);
					MyAnimations
							.startAnimationsOut(composerButtonsWrapper, 300);
					composerButtonsShowHideButtonIcon
							.startAnimation(MyAnimations.getRotateAnimation(
									-45, 0, 300));
					// composerButtonsWrapper.setBackgroundResource(0);

					/**
					 * 透明度动画
					 */
					ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.2f);
					animator.addUpdateListener(new AnimatorUpdateListener() {

						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							// TODO Auto-generated method stub
							// v.setAlpha((float) animation.getAnimatedValue());
							composerButtonsWrapper.setAlpha(((float) animation
									.getAnimatedValue() - 0.2f) * 5 / 4);
						}
					});
					animator.setDuration(300);
					animator.start();
				}
				areButtonsShowing = !areButtonsShowing;
			}
		});

		composerButtonsShowHideButton.startAnimation(MyAnimations
				.getRotateAnimation(0, 360, 200));

	}

	private void loadData(Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("from shortcut")) {
			try {
				FileInputStream inStream = NonStandard_RuntimeView.this
						.openFileInput(intent.getStringExtra("name") + ".txt");// 只需传文件名
				outStream = new ByteArrayOutputStream();//
				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);//
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

			datatoview(outStream);

		}
	}

	private void datatoview(ByteArrayOutputStream outStream) {
		// TODO Auto-generated method stub
		byte[] content_byte = outStream.toByteArray();
		String content = new String(content_byte);
		String gstr[] = content.split("<group>");
		String gname[] = gstr[1].split(";");
		String cstr[] = content.split("<child>");
		String sgstr[] = cstr[1].split(";");
		String tempstr[] = content.split("<temperature>");
		String stempstr[] = tempstr[1].split(" ");
		for (int i = 0; i < gname.length; i++) {
			groupData.add(new GroupData(gname[i]));
			childData.add(null);
		}

		for (int l = 0; l < sgstr.length; l++) {
			List<ChildData> child = new ArrayList<ChildData>();

			try {
				String ch[] = sgstr[l].split(":");
				for (int m = 0; m < ch.length; m++) {

					String cdata[] = ch[m].split(" ");
					ChildData item = new ChildData();
					item.setSpinner1_(cdata[0]);
					item.setSpinner2_(cdata[1]);
					item.setVibVel_(cdata[2]);
					item.setVibAmp_(cdata[3]);
					item.setVibBottom_(cdata[4]);
					item.setMin_(cdata[5]);
					item.setLinkageVel_(cdata[6]);
					item.setCheckflag(false);
					child.add(item);

				}
				childData.set(l, child);
			} catch (Exception e) {
				// TODO: handle exception
				childData.set(l, null);
			}
		}

		adapter.notifyDataSetChanged();
		t0k.setSelection(Integer.parseInt(stempstr[0]));
		t1k.setSelection(Integer.parseInt(stempstr[1]));
		t2k.setSelection(Integer.parseInt(stempstr[2]));
		t3k.setSelection(Integer.parseInt(stempstr[3]));
		t4k.setSelection(Integer.parseInt(stempstr[4]));
		t5k.setSelection(Integer.parseInt(stempstr[5]));
		t6k.setSelection(Integer.parseInt(stempstr[6]));
		t7k.setSelection(Integer.parseInt(stempstr[7]));
		heat0Switch.setChecked(Boolean.parseBoolean(stempstr[8]));
		heat1Switch.setChecked(Boolean.parseBoolean(stempstr[9]));
		heat2Switch.setChecked(Boolean.parseBoolean(stempstr[10]));
		heat3Switch.setChecked(Boolean.parseBoolean(stempstr[11]));
		heat4Switch.setChecked(Boolean.parseBoolean(stempstr[12]));
		heat5Switch.setChecked(Boolean.parseBoolean(stempstr[13]));
		heat6Switch.setChecked(Boolean.parseBoolean(stempstr[14]));
		heat7Switch.setChecked(Boolean.parseBoolean(stempstr[15]));

		putTemperatureDataIntoMessageJsonObject();

		if (popupWindow != null)
			popupWindow.dismiss();
		if (alertDialog != null)
			alertDialog.dismiss();
		for (int i = 0; i < adapter.groupdata.size(); i++) {
			expandablelistview.collapseGroup(i);
			collapse.setText("展开列表");
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

	private void datainit() {
		// TODO Auto-generated method stub
		groupData = new ArrayList<GroupData>();
		childData = new ArrayList<List<ChildData>>();

		putTemperatureDataIntoMessageJsonObject();
	}

	private void init() {
		// TODO Auto-generated method stub
		inflater = getLayoutInflater();
		getSetDialogView();
		if (Home.messageJsonObject == null) {
			Home.messageJsonObject = new JSONObject();
			try {
				Home.messageJsonObject.put("machinetype", "pnae32");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		t0k = (Spinner) findViewById(R.id.hw1);
		t1k = (Spinner) findViewById(R.id.hw2);
		t2k = (Spinner) findViewById(R.id.hw3);
		t3k = (Spinner) findViewById(R.id.hw4);
		t4k = (Spinner) findViewById(R.id.hw5);
		t5k = (Spinner) findViewById(R.id.hw6);
		t6k = (Spinner) findViewById(R.id.hw7);
		t7k = (Spinner) findViewById(R.id.hw8);
		t0k.setOnItemSelectedListener(this);
		t1k.setOnItemSelectedListener(this);
		t2k.setOnItemSelectedListener(this);
		t3k.setOnItemSelectedListener(this);
		t4k.setOnItemSelectedListener(this);
		t5k.setOnItemSelectedListener(this);
		t6k.setOnItemSelectedListener(this);
		t7k.setOnItemSelectedListener(this);

		temp1 = (TextView) findViewById(R.id.temp1);
		temp2 = (TextView) findViewById(R.id.temp2);
		temp3 = (TextView) findViewById(R.id.temp3);
		temp4 = (TextView) findViewById(R.id.temp4);
		temp5 = (TextView) findViewById(R.id.temp5);
		temp6 = (TextView) findViewById(R.id.temp6);
		temp7 = (TextView) findViewById(R.id.temp7);
		temp8 = (TextView) findViewById(R.id.temp8);
		heat0Switch = (ToggleButton) findViewById(R.id.heat0set);
		heat1Switch = (ToggleButton) findViewById(R.id.heat1set);
		heat2Switch = (ToggleButton) findViewById(R.id.heat2set);
		heat3Switch = (ToggleButton) findViewById(R.id.heat3set);
		heat4Switch = (ToggleButton) findViewById(R.id.heat4set);
		heat5Switch = (ToggleButton) findViewById(R.id.heat5set);
		heat6Switch = (ToggleButton) findViewById(R.id.heat6set);
		heat7Switch = (ToggleButton) findViewById(R.id.heat7set);
		collapse = (Button) findViewById(R.id.collapse);
		empty = (Button) findViewById(R.id.empty);
		implement = (Button) findViewById(R.id.implement);
		stop = (Button) findViewById(R.id.stop);
		savelist = (Button) findViewById(R.id.save);
		saveas = (Button) findViewById(R.id.saveas);
		list = (Button) findViewById(R.id.list);

		action = (LinearLayout) findViewById(R.id.action);
		progressBar = (CustomadeProgressBar) findViewById(R.id.progressBar);
		up = (ImageButton) findViewById(R.id.up);
		down = (ImageButton) findViewById(R.id.down);
		insert = (ImageButton) findViewById(R.id.insert);
		edit = (ImageButton) findViewById(R.id.edit);
		remove = (ImageButton) findViewById(R.id.remove);
		cancel = (ImageButton) findViewById(R.id.cancel);
		title = (TextView) findViewById(R.id.title);
		btn_title = (Button) findViewById(R.id.actionbar_title);
		title.setText("非标用户区");
		btn_title.setText("首页");

		expandablelistview = getExpandableListView();

		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(this);
		insert.setOnClickListener(this);
		edit.setOnClickListener(this);
		remove.setOnClickListener(this);
		cancel.setOnClickListener(this);
		collapse.setOnClickListener(this);
		empty.setOnClickListener(this);
		implement.setOnClickListener(this);
		stop.setOnClickListener(this);
		savelist.setOnClickListener(this);
		saveas.setOnClickListener(this);
		list.setOnClickListener(this);
		up.setOnClickListener(this);
		down.setOnClickListener(this);
		btn_title.setOnClickListener(this);
		heat0Switch.setOnToggleChanged(this);
		heat1Switch.setOnToggleChanged(this);
		heat2Switch.setOnToggleChanged(this);
		heat3Switch.setOnToggleChanged(this);
		heat4Switch.setOnToggleChanged(this);
		heat5Switch.setOnToggleChanged(this);
		heat6Switch.setOnToggleChanged(this);
		heat7Switch.setOnToggleChanged(this);
		setOnClickListeners();
		drawable = getResources().getDrawable(R.drawable.start);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		implement.setCompoundDrawables(null, drawable, null, null);

		drawable = getResources().getDrawable(R.drawable.stop);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		stop.setCompoundDrawables(null, drawable, null, null);
	}

	private void initHandler() {
		// TODO Auto-generated method stub
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub

				try {
					switch (msg.what) {
					case SMOOTHTOTOP:
						expandablelistview.setSelectedChild(0, 0, true);
						System.out.println("SMOOTHTOTOP");
						break;
					case StaticVar.INTERRUPT_PROCESS:
						if (msg.obj.toString().equals("Start")) {
							if (!implement.getText().toString().equals("暂停"))
								implement.performClick();
						} else {
							stop.performClick();
						}

						break;
					case 1:
						int value = 0;
						float floatvalue = 0;
						String[] res = new String[2];
						res = (String[]) msg.obj;
						String s = res[0].toString();
						// for (int i = 0; i < res.length; i++) {
						// System.out.print("m"+i+">>"+res[i]+"    ");
						// }
						if (res.length > 1)
							if (res[1].contains(".")) {
								floatvalue = Float.parseFloat(res[1]);
							} else {
								value = Integer.parseInt(res[1]);
							}

						switch (s) {
						case "t0k":
							if (floatvalue > temp_threshold) {

								temp1.setText(floatvalue + "");
								try {
									Home.messageJsonObject.put("工位1",
											floatvalue + "");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								temp1.setText("室温");
								try {
									Home.messageJsonObject.put("工位1", "室温");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case "t1k":
							if (floatvalue > temp_threshold) {

								temp2.setText(floatvalue + "");
								try {
									Home.messageJsonObject.put("工位2",
											floatvalue + "");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								temp2.setText("室温");
								try {
									Home.messageJsonObject.put("工位2", "室温");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case "t2k":
							if (floatvalue > temp_threshold) {

								temp3.setText(floatvalue + "");
								try {
									Home.messageJsonObject.put("工位3",
											floatvalue + "");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								temp3.setText("室温");
								try {
									Home.messageJsonObject.put("工位3", "室温");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case "t3k":
							if (floatvalue > temp_threshold) {

								temp4.setText(floatvalue + "");
								try {
									Home.messageJsonObject.put("工位4",
											floatvalue + "");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								temp4.setText("室温");
								try {
									Home.messageJsonObject.put("工位4", "室温");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case "t4k":
							if (floatvalue > temp_threshold) {

								temp5.setText(floatvalue + "");
								try {
									Home.messageJsonObject.put("工位5",
											floatvalue + "");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								temp5.setText("室温");
								try {
									Home.messageJsonObject.put("工位5", "室温");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case "t5k":
							if (floatvalue > temp_threshold) {

								temp6.setText(floatvalue + "");
								try {
									Home.messageJsonObject.put("工位6",
											floatvalue + "");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								temp6.setText("室温");
								try {
									Home.messageJsonObject.put("工位6", "室温");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case "t6k":
							if (floatvalue > temp_threshold) {

								temp7.setText(floatvalue + "");
								try {
									Home.messageJsonObject.put("工位7",
											floatvalue + "");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								temp7.setText("室温");
								try {
									Home.messageJsonObject.put("工位7", "室温");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case "t7k":
							if (floatvalue > temp_threshold) {

								temp8.setText(floatvalue + "");
								try {
									Home.messageJsonObject.put("工位8",
											floatvalue + "");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								temp8.setText("室温");
								try {
									Home.messageJsonObject.put("工位8", "室温");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;

						case "overflag":
							// System.out.println("overflag="+value);
							thread.nextStep(value);
							beinterrupted = true;
							if (value == 2) {
								beinterrupted = false;
								stop.performClick();

								// sendNote(1);
							}

							// com.xm.fine_bio.Userset.implement.performClick();
							break;
						case "dialogbox":
							CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
									NonStandard_RuntimeView.this);
							builder.setCancelable(false);
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
														+ "加热模块超温，请联系售后人员！")
										.setPositiveButton(
												"确定",
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														stop.performClick();
													}
												}).create().show();
								break;
							case 5: // 运行中开门
								if (dialog == null || !dialog.isShowing()) {
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
															// TODO
															// Auto-generated
															// method
															// stub
															// Home.serialport.SendMsg(Tools.packagebag("sss",
															// "5"));
															stop.performClick();
															dialog.dismiss();
															// drawable =
															// getResources()
															// .getDrawable(
															// R.drawable.start);
															// drawable.setBounds(
															// 0,
															// 0,
															// drawable.getMinimumWidth(),
															// drawable.getMinimumHeight());
															// implement
															// .setCompoundDrawables(
															// null,
															// drawable,
															// null, null);
															// implement.setText("运行");
															// setOnClickListeners();
														}
													})
											.setNegativeButton(
													"继续实验",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface sdialog,
																int which) {
															// TODO
															// Auto-generated
															// method stub
															Home.serialport
																	.SendMsg(Tools
																			.packagebag(
																					"sss",
																					"0"));
														}
													}).create();
									dialog.show();
								}
								break;
							case 6: // 运行中加热托盘移动
								builder.setTitle("提醒")
										.setMessage("运行中加热托盘移动,实验停止")
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
														implement
																.setCompoundDrawables(
																		null,
																		drawable,
																		null,
																		null);
														implement.setText("运行");
														setOnClickListeners();
													}
												}).create().show();
								break;
							case 7: // 电机未复位
								expAllow = false;
								builder.setTitle("提醒")
										.setMessage("电机未复位，请检查")
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
														implement
																.setCompoundDrawables(
																		null,
																		drawable,
																		null,
																		null);
														implement.setText("运行");
														setOnClickListeners();
													}
												}).create().show();
								break;
							case 8: // 门未关
								expAllow = false;
								builder.setTitle("提醒")
										.setMessage("门未关，请检查")
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
														implement
																.setCompoundDrawables(
																		null,
																		drawable,
																		null,
																		null);
														implement.setText("运行");
														setOnClickListeners();
													}
												}).create().show();
								break;
							case 9: // 托盘未复位
								expAllow = false;
								builder.setTitle("提醒")
										.setMessage("托盘未复位，请检查")
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
														implement
																.setCompoundDrawables(
																		null,
																		drawable,
																		null,
																		null);
														implement.setText("运行");
														setOnClickListeners();
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
								if (dialog != null && dialog.isShowing()) {
									dialog.dismiss();
									if (implement.getText().toString()
											.equals("继续"))
										implement.performClick();
								}
								gateState = true;
								break;
							case 1: // 门打开
								if (dialog != null && dialog.isShowing())
									showTextToast("请先关闭实验舱门！");
								gateState = false;
								break;

							default:
								break;
							}
							break;
						}
						break;
					case 5:
						drawable = getResources().getDrawable(R.drawable.start);
						drawable.setBounds(0, 0, drawable.getMinimumWidth(),
								drawable.getMinimumHeight());
						implement.setCompoundDrawables(null, drawable, null,
								null);
						implement.setText("运行");
						setOnClickListeners();
						break;

					default:

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
					res = "热井1";
					break;
				case 2:
					res = "热井2";
					break;
				case 3:
					res = "热井3";
					break;
				case 4:
					res = "热井4";
					break;
				case 10:
					res = "热井5";
					break;
				case 11:
					res = "热井6";
					break;
				case 12:
					res = "热井7";
					break;
				case 13:
					res = "热井8";
					break;

				}
				return res;
			}
		};
	}

	void getSetDialogView() {
		setDialogView = inflater.inflate(R.layout.layout_setdialog, null);
		// System.out.println((new Date()).getTime());
		spinner1 = (Spinner) setDialogView.findViewById(R.id.spinner1);
		spinner2 = (Spinner) setDialogView.findViewById(R.id.spinner2);
		vibVel = (Spinner) setDialogView.findViewById(R.id.vibVel);
		vibAmp = (Spinner) setDialogView.findViewById(R.id.vibAmp);
		vibBottom = (Spinner) setDialogView.findViewById(R.id.vibbottom);
		second = (EditText) setDialogView.findViewById(R.id.second);
		linkageVel = (Spinner) setDialogView.findViewById(R.id.linkageVel);
		// System.out.println((new Date()).getTime());
		// String[] number = { "1", "2", "3", "4", "5", "6" };
		// String[] action_ = { "振动混匀", "等待时间", "吸磁珠", "放磁珠" };
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.number, R.layout.item_spinner);
		adapter1.setDropDownViewResource(R.layout.item_spinner_dropdown);

		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.motion, R.layout.item_spinner);
		adapter2.setDropDownViewResource(R.layout.item_spinner_dropdown);

		// ArrayAdapter<CharSequence> adapter3 =
		// ArrayAdapter.createFromResource(
		// this, R.array.min_sec, R.layout.item_spinner);
		// adapter3.setDropDownViewResource(R.layout.item_spinner_dropdown);

		ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
				this, R.array.vib_bottom, R.layout.item_spinner);
		adapter4.setDropDownViewResource(R.layout.item_spinner_dropdown);
		// System.out.println((new Date()).getTime());
		spinner1.setAdapter(adapter1);
		spinner2.setAdapter(adapter2);
		vibVel.setAdapter(adapter1);
		vibAmp.setAdapter(adapter1);
		vibBottom.setAdapter(adapter4);
		linkageVel.setAdapter(adapter1);
		// second.setAdapter(adapter3);
		// System.out.println((new Date()).getTime());

		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				String s = parent.getItemAtPosition(position).toString();
				// vibVel.setSelection(0);
				// vibAmp.setSelection(0);
				// min.setSelection(0);
				// linkageVel.setSelection(0);
				switch (s) {
				case "振动混匀":
					vibVel.setEnabled(true);
					vibAmp.setEnabled(true);
					second.setEnabled(true);
					linkageVel.setEnabled(false);
					vibBottom.setEnabled(true);
					// vibVel.requestFocusFromTouch();
					break;
				case "等待时间":
					vibVel.setEnabled(false);
					vibAmp.setEnabled(false);
					second.setEnabled(true);
					linkageVel.setEnabled(false);
					second.requestFocus();
					vibBottom.setEnabled(false);
					// vibVel.requestFocusFromTouch();
					break;
				case "吸磁珠":
					vibVel.setEnabled(false);
					vibAmp.setEnabled(false);
					second.setEnabled(true);
					linkageVel.setEnabled(true);
					linkageVel.requestFocus();
					vibBottom.setEnabled(false);
					// vibVel.requestFocusFromTouch();
					break;

				case "放磁珠":
					vibVel.setEnabled(false);
					vibAmp.setEnabled(false);
					second.setEnabled(false);
					linkageVel.setEnabled(true);
					linkageVel.requestFocus();
					vibBottom.setEnabled(false);
					// vibVel.requestFocusFromTouch();
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO 自动生成的方法存根

			}
		});

	}

	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub

		TAG = v.getTag();

		// System.out.println((new Date()).getTime());

		ChildData temp;
		switch (v.getId()) {
		case R.id.add:
			// if(action.getVisibility()==0){Toast.makeText(MainActivity.this,
			// "请先取消操作", 500).show();break;}
			final EditText edittext = new EditText(NonStandard_RuntimeView.this);
			edittext.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT));
			edittext.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
					NonStandard_RuntimeView.this);
			builder.setTitle("组条目名称")
					.setContentView(edittext)
					.setCancelable(false)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									String str = null;
									if (edittext.getText().toString()
											.equals("")) {
										str = "(空)";
									} else {
										if (edittext.getText().toString()
												.contains(";")
												|| edittext.getText()
														.toString()
														.contains("；")) {
											showTextToast("不能包含字符 ‘;’");
											return;
										} else
											str = edittext.getText().toString();
									}
									groupData.add(new GroupData(str));
									childData.add(null);
									adapter.notifyDataSetChanged();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
								}
							});
			customadeDialog = builder.create();
			customadeDialog.show();
			break;
		case R.id.edit:
			ChildData tempdata = childData.get(gposition).get(cposition);
			// System.out.println("getSpinner1_:"+tempdata.getSpinner1_());
			// System.out.println("getSpinner2_:"+tempdata.getSpinner2_());
			// System.out.println("getVibVel_:"+tempdata.getVibVel_());
			// System.out.println("getVibAmp_:"+tempdata.getVibAmp_());
			// System.out.println("getVibBottom_:"+tempdata.getVibBottom_());
			// System.out.println("getMin_:"+tempdata.getMin_());
			// System.out.println("getLinkageVel_:"+tempdata.getLinkageVel_());

			((Spinner) setDialogView.findViewById(R.id.spinner1))
					.setSelection(tempdata.getSpinner1_().equals("/") ? 0
							: (Integer.valueOf(tempdata.getSpinner1_()) - 1));
			switch (tempdata.getSpinner2_()) {
			case "振动混匀":
				((Spinner) setDialogView.findViewById(R.id.spinner2))
						.setSelection(0);
				break;
			case "等待时间":
				((Spinner) setDialogView.findViewById(R.id.spinner2))
						.setSelection(1);
				break;
			case "吸磁珠":
				((Spinner) setDialogView.findViewById(R.id.spinner2))
						.setSelection(2);
				break;
			case "放磁珠":
				((Spinner) setDialogView.findViewById(R.id.spinner2))
						.setSelection(3);
				break;

			default:
				break;
			}
			((Spinner) setDialogView.findViewById(R.id.vibVel))
					.setSelection(tempdata.getVibVel_().equals("/") ? 0
							: (Integer.valueOf(tempdata.getVibVel_())) - 1);
			((Spinner) setDialogView.findViewById(R.id.vibAmp))
					.setSelection(tempdata.getVibAmp_().equals("/") ? 0
							: (Integer.valueOf(tempdata.getVibAmp_())) - 1);
			((Spinner) setDialogView.findViewById(R.id.vibbottom))
					.setSelection(tempdata.getVibBottom_().equals("/") ? 0
							: (Integer.valueOf(tempdata.getVibBottom_())));
			((EditText) setDialogView.findViewById(R.id.second))
					.setText(tempdata.getMin_().equals("/") ? "" : tempdata
							.getMin_());
			((Spinner) setDialogView.findViewById(R.id.linkageVel))
					.setSelection(tempdata.getLinkageVel_().equals("/") ? 0
							: (Integer.valueOf(tempdata.getLinkageVel_()) - 1));

			builder = new CustomadeDialog.Builder(this);
			if (cposition != -1) {
				builder.setTitle("修改")
						.setContentView(setDialogView)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										/*
										 * String str=null;
										 * if(name.getText().toString
										 * ().equals("")){ str="(空)"; }else{
										 * str=name.getText().toString(); }
										 * groupData.add(new GroupData(str));
										 * adapter.notifyDataSetChanged();
										 */
										ChildData item = null;
										item = new ChildData();
										item.setSpinner1_(spinner1
												.getSelectedItem().toString());
										item.setSpinner2_(spinner2
												.getSelectedItem().toString());
										item.setVibVel_(vibVel
												.getSelectedItem().toString());
										item.setVibAmp_(vibAmp
												.getSelectedItem().toString());
										item.setVibBottom_(vibBottom
												.getSelectedItem().toString());
										item.setMin_(second.getText()
												.toString());
										item.setLinkageVel_(linkageVel
												.getSelectedItem().toString());
										item.setCheckflag(true);
										if (!vibVel.isEnabled())
											item.setVibVel_("/");
										if (!vibAmp.isEnabled())
											item.setVibAmp_("/");
										if (!vibBottom.isEnabled())
											item.setVibBottom_("/");
										if (!second.isEnabled())
											item.setMin_("/");
										if (!linkageVel.isEnabled())
											item.setLinkageVel_("/");
										adapter.setChild(gposition, cposition,
												item);
										adapter.notifyDataSetChanged();
										expandablelistview
												.expandGroup(gposition);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
									}
								});

				CustomadeDialog cd = builder.create();
				android.view.WindowManager.LayoutParams params = new android.view.WindowManager.LayoutParams();
				params.width = 1010;
				cd.getWindow().setAttributes(params);
				cd.getWindow().setBackgroundDrawableResource(
						android.R.color.transparent);
				cd.show();
			}

			break;
		case R.id.add_child:
			// System.out.println("add_child"+(new Date()).getTime());
			// if(action.getVisibility()==0){Toast.makeText(MainActivity.this,
			// "请先取消操作", 500).show();break;}
			// System.out.println("add_child TAG1"+v.getTag());
			if (areButtonsShowing) {
				MyAnimations.startAnimationsOut(composerButtonsWrapper, 300);
				composerButtonsShowHideButtonIcon.startAnimation(MyAnimations
						.getRotateAnimation(-270, 0, 300));
				composerButtonsWrapper.setBackgroundResource(0);
				// ValueAnimator animator = ValueAnimator.ofFloat(1f,0.2f);
				// animator.addUpdateListener(new AnimatorUpdateListener() {
				//
				// @Override
				// public void onAnimationUpdate(ValueAnimator animation) {
				// // TODO Auto-generated method stub
				// composerButtonsShowHideButton.setAlpha((float)
				// animation.getAnimatedValue());
				// }
				// });
				// animator.setDuration(300);
				// animator.start();
				areButtonsShowing = false;

			}

			if (implement.getText().toString().equals("运行")) {
				resetSpinner();

				builder = new CustomadeDialog.Builder(this);
				builder.setTitle("请设置")
						.setCancelable(false)
						.setContentView(setDialogView)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										/*
										 * String str=null;
										 * if(name.getText().toString
										 * ().equals("")){ str="(空)"; }else{
										 * str=name.getText().toString(); }
										 * groupData.add(new GroupData(str));
										 * adapter.notifyDataSetChanged();
										 */
										ChildData item = new ChildData();
										item.setSpinner1_(spinner1
												.getSelectedItem().toString());
										item.setSpinner2_(spinner2
												.getSelectedItem().toString());
										item.setVibVel_(vibVel
												.getSelectedItem().toString());
										item.setVibAmp_(vibAmp
												.getSelectedItem().toString());
										item.setVibBottom_(vibBottom
												.getSelectedItem().toString());
										item.setMin_(second.getText()
												.toString());
										item.setLinkageVel_(linkageVel
												.getSelectedItem().toString());
										item.setCheckflag(false);
										if (!vibVel.isEnabled())
											item.setVibVel_("/");
										if (!vibAmp.isEnabled())
											item.setVibAmp_("/");
										if (!vibBottom.isEnabled())
											item.setVibBottom_("/");
										if (!second.isEnabled())
											item.setMin_("/");
										if (!linkageVel.isEnabled())
											item.setLinkageVel_("/");
										// System.out.println("TAG:"+v.getTag().toString());
										// System.out.println("TAG:"+TAG);
										adapter.addChild(Integer.parseInt(TAG
												.toString()), item);
										adapter.notifyDataSetChanged();
										expandablelistview.expandGroup(Integer
												.parseInt(TAG.toString()));
									}

								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
									}
								});

				CustomadeDialog cd = builder.create();
				android.view.WindowManager.LayoutParams params = new android.view.WindowManager.LayoutParams();
				params.width = 1010;
				cd.getWindow().setAttributes(params);
				cd.getWindow().setBackgroundDrawableResource(
						android.R.color.transparent);
				cd.show();

				// System.out.println("add_child"+(new Date()).getTime());
			} else {
				showTextToast("请先停止设备");
			}

			break;
		case R.id.up:
			if (cposition == 0) {
				showTextToast("已在顶部！");
			} else {
				temp = childData.get(gposition).get(cposition);
				childData.get(gposition).remove(cposition);
				cposition--;
				adapter.addChild(gposition, cposition, temp);
				adapter.notifyDataSetChanged();
			}
			break;
		case R.id.down:
			if (cposition == childData.get(gposition).size() - 1) {
				showTextToast("已在底部！");
			} else {
				temp = childData.get(gposition).get(cposition);
				childData.get(gposition).remove(cposition);
				cposition++;
				adapter.addChild(gposition, cposition, temp);
				adapter.notifyDataSetChanged();
			}
			break;
		case R.id.insert:

			if (cposition != -1) {
				resetSpinner();

				builder = new CustomadeDialog.Builder(this);
				builder.setTitle("插入")
						.setContentView(setDialogView)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										/*
										 * String str=null;
										 * if(name.getText().toString
										 * ().equals("")){ str="(空)"; }else{
										 * str=name.getText().toString(); }
										 * groupData.add(new GroupData(str));
										 * adapter.notifyDataSetChanged();
										 */
										childData.get(gposition).get(cposition)
												.setCheckflag(false);
										ChildData item = null;
										item = new ChildData();
										item.setSpinner1_(spinner1
												.getSelectedItem().toString());
										item.setSpinner2_(spinner2
												.getSelectedItem().toString());
										item.setVibVel_(vibVel
												.getSelectedItem().toString());
										item.setVibAmp_(vibAmp
												.getSelectedItem().toString());
										item.setVibBottom_(vibBottom
												.getSelectedItem().toString());
										item.setMin_(second.getText()
												.toString());
										item.setLinkageVel_(linkageVel
												.getSelectedItem().toString());
										item.setCheckflag(true);
										if (!vibVel.isEnabled())
											item.setVibVel_("/");
										if (!vibAmp.isEnabled())
											item.setVibAmp_("/");
										if (!vibBottom.isEnabled())
											item.setVibBottom_("/");
										if (!second.isEnabled())
											item.setMin_("/");
										if (!linkageVel.isEnabled())
											item.setLinkageVel_("/");
										adapter.addChild(gposition, cposition,
												item);
										adapter.notifyDataSetChanged();
										expandablelistview
												.expandGroup(gposition);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
									}
								});

				CustomadeDialog cd = builder.create();
				android.view.WindowManager.LayoutParams params = new android.view.WindowManager.LayoutParams();
				params.width = 1010;
				cd.getWindow().setAttributes(params);
				cd.getWindow().setBackgroundDrawableResource(
						android.R.color.transparent);
				cd.show();

			}
			break;

		case R.id.remove:
			builder = new CustomadeDialog.Builder(this);
			builder.setCancelable(false)
					.setTitle("提示")
					.setMessage("删除选中条目？")
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									if (cposition != -1) {
										try {
											adapter.childdata.get(gposition)
													.remove(cposition);
											adapter.notifyDataSetChanged();
											action.setVisibility(View.INVISIBLE);
											gposition = -1;
											cposition = -1;
										} catch (Exception e) {
											// TODO: handle exception
											showTextToast("删除无效");
										}
									} else {
									}
								}
							}).create().show();

			break;
		case R.id.cancel:
			if (tempview != null) {
				tempview.setBackgroundResource(R.color.transplant);
				tempview = null;
				childData.get(gposition).get(cposition).setCheckflag(false);
			}
			action.setVisibility(View.INVISIBLE);
			gposition = -1;
			cposition = -1;
			break;
		case R.id.collapse:
			if (collapse.getText().toString().equals("收起列表")) {
				for (int i = 0; i < adapter.groupdata.size(); i++)
					expandablelistview.collapseGroup(i);
				drawable = getResources().getDrawable(R.drawable.expand);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				collapse.setCompoundDrawables(null, drawable, null, null);
				collapse.setText("展开列表");
			} else {
				for (int i = 0; i < adapter.groupdata.size(); i++)
					expandablelistview.expandGroup(i);
				drawable = getResources().getDrawable(R.drawable.colla);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				collapse.setCompoundDrawables(null, drawable, null, null);
				collapse.setText("收起列表");
			}
			break;
		case R.id.empty:
			builder = new CustomadeDialog.Builder(this);
			builder.setCancelable(false)
					.setTitle("提示")
					.setMessage("清除页面内容")
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									childData.clear();
									groupData.clear();
									adapter.notifyDataSetChanged();
								}
							}).create().show();

			break;
		case R.id.implement:
			try {
				if (childData.size() != 0) {
					for (int i = 0; i < childData.size(); i++) {
						childData.get(i).get(0);
					}

				} else {
					showTextToast("存在空流程，无法运行");
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
				showTextToast("存在空流程，无法运行");
				break;
			}

			switch (implement.getText().toString()) {
			case "运行":

				tempchild = getdata(childData);
				action.setVisibility(View.INVISIBLE);
				int n = tempchild.size();
				drawable = getResources().getDrawable(R.drawable.pause);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				implement.setCompoundDrawables(null, drawable, null, null);
				implement.setText("暂停");
				try {
					Home.messageJsonObject.put("runningstate", "运行");
					// System.out.println("runningstate"+new Date().getTime());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					thread = new DoThread(n);
					thread.start();
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case "暂停":
				drawable = getResources().getDrawable(R.drawable.continue_img);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				implement.setCompoundDrawables(null, drawable, null, null);
				Home.serialport.SendMsg(Tools.packagebag("sss", "1"));
				implement.setText("继续");
				try {
					Home.messageJsonObject.put("runningstate", "暂停");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "继续":
				drawable = getResources().getDrawable(R.drawable.pause);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				implement.setCompoundDrawables(null, drawable, null, null);
				Home.serialport.SendMsg(Tools.packagebag("sss", "0"));
				implement.setText("暂停");
				try {
					Home.messageJsonObject.put("runningstate", "运行");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			setOnClickListeners();
			break;
		case R.id.stop:
			if (!implement.getText().toString().equals("运行"))
				try {

					progressBar.setVisibility(View.INVISIBLE);
					if (thread != null) {
						thread.stopThread();
					}
					drawable = getResources().getDrawable(R.drawable.start);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());
					implement.setCompoundDrawables(null, drawable, null, null);
					implement.setText("运行");
					setOnClickListeners();

					Home.messageJsonObject.put("runningstate", "停止");
					// System.out.println("runningstate"+new Date().getTime());
					Home.messageJsonObject.put("currentstep", "0");
					Home.serialport.SendMsg(Tools.packagebag("sss", "5"));

					// beinterrupted=true;
					sendNote(1);

				} catch (Exception e) {
					// TODO 自动生成的 catch 块
				}
			break;
		case R.id.save:
			if (existEmptyGroup())
				return;
			ArrayList<String> StringList = dataBuild();
			if (saveFileName.equals("")) {
				customadeDialog = saveDialog(StringList);
				customadeDialog.show();
			} else {
				saveFile(saveFileName, StringList);
			}
			break;
		case R.id.saveas:
			if (existEmptyGroup())
				return;
			StringList = dataBuild();
			customadeDialog = saveDialog(StringList);
			customadeDialog.show();
			break;
		case R.id.list:
			data = new ArrayList<String>();
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File path = this.getFilesDir();// 获得SD卡路径
				// File path = new File("/mnt/sdcard/");
				File[] files = path.listFiles();// 读取
				Arrays.sort(files, new Comparator<File>() {

					@Override
					public int compare(File lhs, File rhs) {
						// TODO Auto-generated method stub
						long diff = lhs.lastModified() - rhs.lastModified();
						if (diff > 0)
							return 1;
						else if (diff == 0)
							return 0;
						else
							return -1;
					}

				});
				getFileName(files);
				showWindow(v);
			}
			break;
		case R.id.actionbar_title:
			if (implement.getText().equals("运行")) {
				finish();
			} else {
				showTextToast("试验流程中不可离开");
			}

			break;
		default:
			break;
		}
	}

	Boolean existEmptyGroup() {
		try {
			if (groupData.size() == 0) {
				showTextToast("存在空流程，无法保存");
				return true;
			}
			if (childData.size() != 0) {
				for (int i = 0; i < childData.size(); i++) {
					childData.get(i).get(0);
				}
				return false;
			} else {
				showTextToast("存在空流程，无法保存");
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			showTextToast("存在空流程，无法保存");
			return true;
		}
	}

	ArrayList<String> dataBuild() {
		ArrayList<String> StringList = new ArrayList<String>();
		StringList.add("<group>");
		for (int i = 0; i < groupData.size(); i++) {
			GroupData gdata = groupData.get(i);
			StringList.add(gdata.getName() + ";");
		}
		StringList.add("<group>");
		StringList.add("<child>");
		for (int l = 0; l < groupData.size(); l++) {
			try {
				for (int m = 0; m < childData.get(l).size(); m++) {

					ChildData cdata = childData.get(l).get(m);
					StringList.add(cdata.getSpinner1_() + " "
							+ cdata.getSpinner2_() + " " + cdata.getVibVel_()
							+ " " + cdata.getVibAmp_() + " "
							+ cdata.getVibBottom_() + " " + cdata.getMin_()
							+ " " + cdata.getLinkageVel_() + ":");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			StringList.add(";");
		}
		StringList.add("<child>");
		StringList.add("<temperature>");
		StringList.add(t0k.getSelectedItemPosition() + " "
				+ t1k.getSelectedItemPosition() + " "
				+ t2k.getSelectedItemPosition() + " "
				+ t3k.getSelectedItemPosition() + " "
				+ t4k.getSelectedItemPosition() + " "
				+ t5k.getSelectedItemPosition() + " "
				+ t6k.getSelectedItemPosition() + " "
				+ t7k.getSelectedItemPosition() + " " + heat1bol + " "
				+ heat2bol + " " + heat3bol + " " + heat4bol + " " + heat5bol
				+ " " + heat6bol + " " + heat7bol + " " + heat8bol);
		StringList.add("<temperature>");
		return StringList;
	}

	void saveFile(String filename, ArrayList<String> StringList) {
		FileOutputStream out = null;
		try {
			out = this.openFileOutput(filename + ".txt", Context.MODE_PRIVATE);
			for (int m = 0; m < StringList.size(); m++) {
				out.write((StringList.get(m)).getBytes("UTF-8"));
				showTextToast("保存成功");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void resetSpinner() {
		// TODO Auto-generated method stub
		((Spinner) setDialogView.findViewById(R.id.spinner1)).setSelection(0);
		((Spinner) setDialogView.findViewById(R.id.spinner2)).setSelection(0);
		((Spinner) setDialogView.findViewById(R.id.vibVel)).setSelection(0);
		((Spinner) setDialogView.findViewById(R.id.vibAmp)).setSelection(0);
		((Spinner) setDialogView.findViewById(R.id.vibbottom)).setSelection(0);
		((EditText) setDialogView.findViewById(R.id.second)).setText("");
		((Spinner) setDialogView.findViewById(R.id.linkageVel)).setSelection(0);

	}

	public void home(View v) {
		if (implement.getText().equals("运行")) {
			finish();
		} else {
			showTextToast("试验流程中不可离开");
		}
	}

	protected void sendNote(int i) {
		// TODO Auto-generated method stub
		switch (i) {
		case 1:
			try {
				// Object[] objmsg = new Object[3];
				// objmsg[0] = Home.MACHINEID;
				// objmsg[1] = "ALL";

				JSONObject object = new JSONObject();
				object.put("messagetype", "notice");
				object.put("paramstype", "nonstandard");
				object.put("paramstitle", title.getText().toString());
				if (beinterrupted) {
					object.put("noticecontent", title.getText().toString()
							+ "的实验流程被终止");
					// System.out.println("终止");
				} else {
					object.put("noticecontent", title.getText().toString()
							+ "的实验流程已完成");
					// System.out.println("完成");
				}
				// objmsg[2] = object.toString();
				// Home.client.sendFlag("SendCmd");
				// Home.client.sendMsg(objmsg);

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

	/**
	 * 
	 * @param files
	 */
	private void getFileName(File[] files) {
		if (files != null) {// 先判断目录是否为空，否则会报空指针
			for (File file : files) {

				String fileName = file.getName();
				if (fileName.endsWith(".txt")
						&& !fileName.equals("DNA&RNA.txt")) {
					// Log.i("zeng", "文件名txt：：   " + s);
					data.add(fileName.substring(0, fileName.lastIndexOf(".")));

				}
			}
		}
	}

	private void showWindow(View parent) {

		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(R.layout.layout_popupwindow,
				null);
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		lv_group = (ListView) popupView.findViewById(R.id.lvGroup);
		// 加载数据
		groupAdapter = new GroupAdapter(this, data);
		lv_group.setAdapter(groupAdapter);

		// 创建一个PopuWidow对象
		popupWindow = new PopupWindow(popupView, 160, windowManager
				.getDefaultDisplay().getHeight());

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(false);

		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
			}
		});

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		/*
		 * int xPos = windowManager.getDefaultDisplay().getWidth() / 20 -
		 * popupWindow.getWidth() / 2;
		 * 
		 * Log.i("coder", "windowManager.getDefaultDisplay().getWidth()/2:" +
		 * windowManager.getDefaultDisplay().getWidth() / 2); // Log.i("coder",
		 * "popupWindow.getWidth()/2:" + popupWindow.getWidth() / 2);
		 * 
		 * Log.i("coder", "xPos:" + xPos);
		 */

		// popupWindow.showAsDropDown(parent, xPos, -100);
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2;
		popupWindow.showAtLocation(parent, Gravity.CENTER, xPos, 0);

		lv_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				try {

					Home.messageJsonObject.put("paramstitle", data
							.get(position).toString());
					FileInputStream inStream = NonStandard_RuntimeView.this
							.openFileInput(data.get(position) + ".txt");// 只需传文件名
					/* edit by Qhq */
					saveFileName = data.get(position);
					title.setText(saveFileName);
					outStream = new ByteArrayOutputStream();//
					int len = 0;
					byte[] buffer = new byte[1024];
					while ((len = inStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);//
					}
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				childData.clear();
				groupData.clear();

				datatoview(outStream);

			}
		});
		lv_group.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				Number = position;
				alertDialog = popDialog();
				alertDialog.show();
				return true;
			}
		});

	}

	public CustomadeDialog groupDialog() {

		CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
				NonStandard_RuntimeView.this);

		ListView view = new ListView(this);
		final SimpleAdapter groupDialogadapter = new SimpleAdapter(this,
				getdata1(), R.layout.item_listview, new String[] { "action" },
				new int[] { android.R.id.text1 });
		view.setAdapter(groupDialogadapter);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				// TODO 自动生成的方法存根
				CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
						NonStandard_RuntimeView.this);
				switch (position) {
				case 0:
					CustomadeDialog dialog = new CustomadeDialog(
							NonStandard_RuntimeView.this);
					final EditText name = new EditText(
							NonStandard_RuntimeView.this);
					builder.setTitle("重命名")
							.setContentView(name)
							.setCancelable(false)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated
											// method stub
											String str = null;
											if (name.getText().toString()
													.equals("")) {
												Toast.makeText(
														NonStandard_RuntimeView.this,
														"重命名无效", 500).show();
											} else {
												if (name.getText().toString()
														.contains(";")
														|| name.getText()
																.toString()
																.contains("；")) {
													showTextToast("不能包含字符 ‘;’");
													return;
												} else
													str = name.getText()
															.toString();
												groupData.set(gclickp,
														new GroupData(str));
												adapter.notifyDataSetChanged();
											}
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated
											// method stub
										}
									});
					dialog = builder.create();
					dialog.show();
					break;
				case 1:

					// adapter.childdata.remove(gclickp-1);
					// for(int
					// i=0;i<=(childrenData.get(gclickp).size()+1);i++)
					// {childrenData.get(gclickp).remove(0);}
					// adapter.childdata.remove(gclickp);
					// adapter.groupdata.remove(gclickp);
					childData.remove(gclickp);
					groupData.remove(gclickp);
					// childrenData.remove(gclickp);
					// groupData.remove(gclickp);
					adapter.notifyDataSetChanged();

					break;
				case 2:
					final EditText name_ = new EditText(
							NonStandard_RuntimeView.this);
					builder = new CustomadeDialog.Builder(
							NonStandard_RuntimeView.this);
					builder.setTitle("组条目名称")
							.setCancelable(false)
							.setContentView(name_)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated
											// method stub
											String str = null;
											if (name_.getText().toString()
													.equals("")) {
												str = "(空)";
											} else {
												str = name_.getText()
														.toString();
											}

											groupData.add(gclickp,
													new GroupData(str));
											childData.add(gclickp, null);
											adapter.notifyDataSetChanged();
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated
											// method stub
										}
									}).create().show();
					break;
				default:
					break;
				}
				alertDialog.dismiss();
			}
		});

		builder.setTitle(
				"您想要对" + "“" + groupData.get(gclickp).getName() + "”" + "进行")
				.setContentView(view)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						// popupWindow.dismiss();
					}
				});

		return builder.create();

	}

	private List<Map<String, String>> getdata1() {
		// TODO Auto-generated method stub
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "重命名");
		data.add(map);
		map = new HashMap<String, String>();
		map.put("action", "删除");
		data.add(map);
		map = new HashMap<String, String>();
		map.put("action", "插入");
		data.add(map);
		return data;
	}

	public CustomadeDialog popDialog() {

		ListView view = new ListView(this);
		SimpleAdapter simpleadapter = new SimpleAdapter(this, getdata(),
				R.layout.item_listview, new String[] { "action" },
				new int[] { android.R.id.text1 });
		view.setAdapter(simpleadapter);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg,
					int position, long id) {
				CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
						NonStandard_RuntimeView.this);
				builder = new CustomadeDialog.Builder(
						NonStandard_RuntimeView.this);
				// TODO 自动生成的方法存根
				switch (position) {
				case 0:
					final EditText rename = new EditText(
							NonStandard_RuntimeView.this);

					builder.setTitle("请输入。。")
							.setCancelable(false)
							.setContentView(rename)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO 自动生成的方法存根
											if (rename.getText().equals("")) {

											} else {
												File path = NonStandard_RuntimeView.this
														.getFilesDir();
												File[] files = path.listFiles();
												for (File file : files) {
													if (file.getName().equals(
															data.get(Number)
																	+ ".txt"))
														file.renameTo(new File(
																NonStandard_RuntimeView.this
																		.getFilesDir()
																		+ "/"
																		+ rename.getText()
																				.toString()
																		+ ".txt"));
												}
												for (int i = 0; i < Home.gridviewList
														.size(); i++) {
													if (Home.gridviewList
															.get(i)
															.containsValue(
																	data.get(Number))) {
														HashMap<String, Object> map = new HashMap<String, Object>();
														map.put("ItemImage",
																String.valueOf(R.drawable.nonstandardexe));
														map.put("ItemText",
																rename.getText()
																		.toString());
														Home.gridviewList.set(
																i, map);
														Home.gridViewAdapter
																.notifyDataSetChanged();
													}
												}

											}
											data.remove(Number);
											data.add(Number, rename.getText()
													.toString());
											groupAdapter.notifyDataSetChanged();
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO 自动生成的方法存根
										}
									});
					renameDialog = builder.create();
					renameDialog.show();
					alertDialog.dismiss();
					break;
				case 1:
					try {
						FileInputStream inStream = NonStandard_RuntimeView.this
								.openFileInput(data.get(Number) + ".txt");// 只需传文件名
						try {
							Home.messageJsonObject.put("paramstitle",
									data.get(Number));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						/* edit by Qhq */
						saveFileName = data.get(Number);
						title.setText(saveFileName);

						outStream = new ByteArrayOutputStream();//

						int len = 0;
						byte[] buffer = new byte[1024];
						while ((len = inStream.read(buffer)) != -1) {
							outStream.write(buffer, 0, len);//
						}
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					childData.clear();
					groupData.clear();

					datatoview(outStream);

					break;
				case 2:
					alertDialog.dismiss();
					builder.setCancelable(false)
							.setTitle("提示")
							.setMessage("确认删除？")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											if (Environment
													.getExternalStorageState()
													.equals(Environment.MEDIA_MOUNTED)) {
												File path = NonStandard_RuntimeView.this
														.getFilesDir();// 获得SD卡路径
												// File path = new
												// File("/mnt/sdcard/");
												File[] files = path.listFiles();// 读取
												if (files != null) {// 先判断目录是否为空，否则会报空指针
													for (File file : files) {
														if (file.getName()
																.equals(data
																		.get(Number)
																		+ ".txt"))
															file.delete();
													}
												}

												for (int i = 0; i < Home.gridviewList
														.size(); i++) {
													if (Home.gridviewList
															.get(i)
															.containsValue(
																	data.get(Number))) {
														HashMap<String, Object> map = new HashMap<String, Object>();

														Home.gridviewList
																.remove(i);
														Home.gridViewAdapter
																.notifyDataSetChanged();
														new Home()
																.saveDrop(NonStandard_RuntimeView.this);
													}
												}
											}
											data.remove(Number);
											groupAdapter.notifyDataSetChanged();

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
				case 3:
					boolean key = false;
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("ItemImage",
							String.valueOf(R.drawable.nonstandardexe));
					map.put("ItemText", data.get(Number));
					for (int i = 0; i < Home.gridviewList.size(); i++) {
						if (Home.gridviewList.get(i).containsValue(
								data.get(Number))) {
							key = true;
							showTextToast("重复快捷方式！");
							break;
						}
					}
					if (key == false) {
						Home.gridviewList.add(map);
						Home.gridViewAdapter.notifyDataSetChanged();
						new Home().saveDrop(NonStandard_RuntimeView.this);
					}
					alertDialog.dismiss();
					break;

				default:
					break;
				}
			}
		});
		CustomadeDialog.Builder builder = new CustomadeDialog.Builder(
				NonStandard_RuntimeView.this);
		builder.setTitle("您要做的是？").setCancelable(false).setContentView(view)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						// popupWindow.dismiss();
					}
				});
		return builder.create();

	}

	private List<Map<String, String>> getdata() {
		// TODO Auto-generated method stub
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "重命名");
		data.add(map);
		map = new HashMap<String, String>();
		map.put("action", "编辑");
		data.add(map);
		map = new HashMap<String, String>();
		map.put("action", "删除");
		data.add(map);
		map = new HashMap<String, String>();
		map.put("action", "添加到桌面快捷方式");
		data.add(map);
		return data;
	}

	private CustomadeDialog saveDialog(final ArrayList<String> StringList) {
		CustomadeDialog.Builder builder = new CustomadeDialog.Builder(this);
		final EditText editText = new EditText(this);
		editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		/*
		 * SimpleDateFormat sDateFormat = new
		 * SimpleDateFormat("yy-MM-dd-hh-mm"); String date =
		 * sDateFormat.format(new java.util.Date());
		 * editText.setText(date);Edited by QHQ
		 */

		builder.setTitle("输入文件名")
				.setAutoDismiss(false)
				.setCancelable(false)
				.setContentView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (editText.getText().toString().equals("")) {
							showTextToast("不能为空！");
						} else {
							String filename = editText.getText().toString();
							File path = getFilesDir();// 获得SD卡路径
							File[] files = path.listFiles();// 读取
							if (flAg == 0) {
								for (File file : files) {
									if (file.getName()
											.equals(filename + ".txt")) {
										showTextToast("有同名文件,保存将替换原文件,仍要保存?");
										flAg = 1;
										return;
									}
								}
							}
							saveFile(filename, StringList);
							saveFileName = filename;
							title.setText(saveFileName);
							customadeDialog.dismiss();
							flAg = 0;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						customadeDialog.dismiss();
					}
				});
		return builder.create();

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

	private List<ChildData> getdata(List<List<ChildData>> childrenData) {
		List<ChildData> child = new ArrayList<ChildData>();
		for (int l = 0; l < childrenData.size(); l++) {
			for (int m = 0; m < childrenData.get(l).size(); m++) {
				child.add(childrenData.get(l).get(m));
			}
		}
		return child;
	}

	void clearCheckflag() {
		for (int i = 0; i < childData.size(); i++) {
			for (int j = 0; j < childData.get(i).size(); j++) {
				childData.get(i).get(j).setCheckflag(false);
			}
		}
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				adapter.notifyDataSetChanged();
			}
		});
	}

	/**
	 * 非标流程执行线程
	 * 
	 * @author liuwei
	 *
	 */
	public class DoThread extends Thread {

		boolean flag = true;
		int overflag = 1;
		int s = 0;
		int n = 0;
		int positionA = 1;
		int positionB;
		int[] postition = new int[2];

		public DoThread(int steps) {
			super();
			this.n = steps;
		}

		public void nextStep(int flag) {
			// TODO Auto-generated method stub
			overflag = flag;
		}

		public void stopThread() {
			flag = false;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Looper.prepare();
				Tools.sendMotionParams(NonStandard_RuntimeView.this);
				// Home.serialport.SendMsg(Tools.packagebag("mode", "1"));
				Home.serialport.SendMsg(Tools.packagebag("t0k", t0k
						.getSelectedItem().toString()));
				Home.serialport.SendMsg(Tools.packagebag("t1k", t2k
						.getSelectedItem().toString()));
				Home.serialport.SendMsg(Tools.packagebag("t2k", t4k
						.getSelectedItem().toString()));
				Home.serialport.SendMsg(Tools.packagebag("t3k", t6k
						.getSelectedItem().toString()));
				Home.serialport.SendMsg(Tools.packagebag("t4k", t1k
						.getSelectedItem().toString()));
				Home.serialport.SendMsg(Tools.packagebag("t5k", t3k
						.getSelectedItem().toString()));
				Home.serialport.SendMsg(Tools.packagebag("t6k", t5k
						.getSelectedItem().toString()));
				Home.serialport.SendMsg(Tools.packagebag("t7k", t7k
						.getSelectedItem().toString()));
				Home.serialport.SendMsg(heat0str);
				Home.serialport.SendMsg(heat1str);
				Home.serialport.SendMsg(heat2str);
				Home.serialport.SendMsg(heat3str);
				Home.serialport.SendMsg(heat4str);
				Home.serialport.SendMsg(heat5str);
				Home.serialport.SendMsg(heat6str);
				Home.serialport.SendMsg(heat7str);
				Home.serialport.SendMsg(Tools.packagebag("sss", "9"));

				clearCheckflag();
				handler.sendEmptyMessage(SMOOTHTOTOP);
				runOnUiThread(new Runnable() {
					public void run() {
						// expandablelistview.smoothScrollToPosition(0);
						// expandablelistview.setSelectedGroup(0);

						if (tempview != null) {
							tempview.setBackgroundResource(R.color.transplant);
							tempview = null;
							childData.get(gposition).get(cposition)
									.setCheckflag(false);
						}
						//
						// expandablelistview.expandGroup(0);

						progressBar.setVisibility(View.VISIBLE);
						progressBar.setMax(n);
						progressBar.setProgress(0);
						try {
							Home.messageJsonObject.put("currentstep", 0);
							Home.messageJsonObject.put("totalsteps", n);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

				while (flag) {
					// if(checkTemp()){
					Thread.sleep(1);
					if (overflag == 1) {
						overflag = 0;

						if ((s == n) && flag) {
							// System.out.println("s" + s + "n" + n);

							usystr = Tools.packagebag("usy", String.valueOf(1));
							Home.serialport.SendMsg(usystr);

							// System.out.println("发送了复位USY指令");
						}

						if ((s < n) && flag) {
							// System.out.println("s" + s + "n" + n);
							Home.messageJsonObject.put("runningstate", "运行");
							positionB = Integer.parseInt(tempchild.get(s)
									.getSpinner1_());
							us0str = Tools.packagebag("s3p",
									String.valueOf(positionB - positionA));
							uskstr = Tools.packagebag("s1p", String
									.valueOf(tempchild.get(s).getVibVel_()));
							usrstr = Tools.packagebag("s2p", String
									.valueOf(tempchild.get(s).getVibAmp_()));
							vibbottomstr = Tools.packagebag("s6p", String
									.valueOf(tempchild.get(s).getVibBottom_()));
							ussstr = Tools.packagebag("s5p",
									String.valueOf(tempchild.get(s).getMin_()));
							us9str = Tools
									.packagebag("s4p", String.valueOf(tempchild
											.get(s).getLinkageVel_()));
							vibbottomstr = Tools.packagebag("m0_", String
									.valueOf(tempchild.get(s).getVibBottom_()));
							if (tempchild.get(s).getSpinner2_().equals("振动混匀"))
								tag = "1";
							if (tempchild.get(s).getSpinner2_().equals("等待时间"))
								tag = "2";
							if (tempchild.get(s).getSpinner2_().equals("吸磁珠"))
								tag = "3";
							if (tempchild.get(s).getSpinner2_().equals("放磁珠"))
								tag = "4";
							stpstr = Tools.packagebag("s0p", tag);

							Home.serialport.SendMsg(us0str);
							Home.serialport.SendMsg(uskstr);
							Home.serialport.SendMsg(usrstr);
							Home.serialport.SendMsg(vibbottomstr);
							Home.serialport.SendMsg(ussstr);
							Home.serialport.SendMsg(us9str);
							Home.serialport.SendMsg(us9str);
							Home.serialport.SendMsg(stpstr);

							s++;
							positionA = positionB;

							// System.out.println("flag"+childData.get(0).get(0).isCheckflag());
							postition = getPosition(s, 0, childData.get(0)
									.size(), 0);

							// System.out.println("flag"+childData.get(0).get(0).isCheckflag());
							runOnUiThread(new Runnable() {
								public void run() {
									if (postition[0] > 1)
										expandablelistview
												.collapseGroup(postition[0] - 2);

									expandablelistview.expandGroup(
											postition[0] - 1, true);
									expandablelistview.setSelectedChild(
											postition[0] - 1, postition[1] - 1,
											true);
								}
							});
							// System.out.println("flag"+childData.get(0).get(0).isCheckflag());
							// System.out.println("gposition:"+postition[0]+"  "+"cposition:"+postition[1]);
							childData.get(postition[0] - 1)
									.get(postition[1] - 1).setCheckflag(true);
							// System.out.println("flag"+childData.get(0).get(0).isCheckflag());
							if (s > 1) {
								postition = getPosition(s - 1, 0, childData
										.get(0).size(), 0);
								// System.out.println("gposition:"+postition[0]+"  "+"cposition:"+postition[1]);
								childData.get(postition[0] - 1)
										.get(postition[1] - 1)
										.setCheckflag(false);
							}

							// System.out.println("s:"+s);
							runOnUiThread(new Runnable() {
								public void run() {
									// System.out.println("flag"+childData.get(0).get(0).isCheckflag());
									adapter.notifyDataSetChanged();
								}
							});
						}
						progressBar.setProgress(s - 1);
						Home.messageJsonObject.put("currentstep", s - 1);

					}

					if ((overflag == 2) && flag) {
						Message message = handler.obtainMessage();
						message.what = 5;
						handler.sendMessage(message);
						showTextToast("实验结束");

						positionA = 1;
						overflag = 1;
						progressBar.setProgress(s);
						Home.messageJsonObject.put("currentstep", s);
						s = 0;
						Home.messageJsonObject.put("runningstate", "停止");
						flag = false;

						runOnUiThread(new Runnable() {
							public void run() {
								if (tempview != null) {
									tempview.setBackgroundResource(R.color.transplant);
									tempview = null;
									childData.get(gposition).get(cposition)
											.setCheckflag(false);
								}

								progressBar.setVisibility(View.INVISIBLE);
							}
						});
					}
					// }

				}
				Looper.loop();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		/*
		 * private boolean checkTemp() { // TODO Auto-generated method stub
		 * while(true){
		 * if(Integer.parseInt(tlk_disk1.getText().toString())>=(Integer
		 * .parseInt
		 * (tlk.getSelectedItem().toString())-2)&&Integer.parseInt(tlk_disk2
		 * .getText
		 * ().toString())>=(Integer.parseInt(tlk.getSelectedItem().toString
		 * ())-2)
		 * &&Integer.parseInt(ttr_disk1.getText().toString())>=(Integer.parseInt
		 * (
		 * ttr.getSelectedItem().toString())-2)&&Integer.parseInt(ttr_disk2.getText
		 * (
		 * ).toString())>=(Integer.parseInt(ttr.getSelectedItem().toString())-2)
		 * ) return true; }
		 * 
		 * }
		 */

		/**
		 * 
		 * @param s
		 *            当前步骤
		 * @param a
		 *            下阈
		 * @param b
		 *            上阈
		 * @param i
		 *            循环次数
		 * @return groupposition，childposition
		 */
		private int[] getPosition(int s, int a, int b, int i) {
			// TODO Auto-generated method stub

			i++;
			if (s > a && s <= b) {
				int[] result = { i, s - a };
				return result;
			} else {
				a = b;
				b = a + childData.get(i).size();
				int[] s1 = new int[2];
				s1 = getPosition(s, a, b, i);
				return s1;
			}

		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (toast != null)
			toast.cancel();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Home.messageJsonObject = null;
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Home.TheActivityIs = null;
		super.onPause();
	}

	void setOnClickListeners() {
		if (implement.getText().toString().equals("运行")) {
			expandablelistview
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							// Toast.makeText(MainActivity.this, position+"",
							// 500).show();

							try {
								GroupViewHolder groupHolder;
								groupHolder = (GroupViewHolder) view.getTag();
								gclickp = Integer.parseInt(view
										.findViewById(R.id.add_child).getTag()
										.toString());
								if (groupHolder.flag == 1) {

									// Toast.makeText(MainActivity.this,
									// "gclickp:"+gclickp,
									// 500).show();
									for (int i = 0; i < adapter.groupdata
											.size(); i++) {
										expandablelistview.collapseGroup(i);
										collapse.setText("展开列表");
									}
									if (tempview != null) {
										tempview.setBackgroundResource(R.color.transplant);
										tempview = null;
										childData.get(gposition).get(cposition)
												.setCheckflag(false);
									}
									action.setVisibility(View.INVISIBLE);
									alertDialog = groupDialog();
									alertDialog.show();
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
							return true;
						}
					});
			expandablelistview
					.setOnChildClickListener(new OnChildClickListener() {

						@Override
						public boolean onChildClick(ExpandableListView parent,
								View v, int groupPosition, int childPosition,
								long id) {
							try {
								if (tempview != null) {
									tempview.setBackgroundResource(R.color.transplant);
									childData.get(gposition).get(cposition)
											.setCheckflag(false);
								}
							} catch (Exception e1) {
							}
							try {
								for (int i = 0; i < groupData.size(); i++)
									for (int m = 0; m < childData.get(i).size(); m++)
										childData.get(i).get(m)
												.setCheckflag(false);
							} catch (Exception e) {
								// TODO: handle exception
							}

							childData.get(groupPosition).get(childPosition)
									.setCheckflag(true);
							adapter.notifyDataSetChanged();
							v.setBackgroundColor(0xFF9ACD32);
							action.setVisibility(View.VISIBLE);
							gposition = groupPosition;
							cposition = childPosition;
							tempview = v;
							if (areButtonsShowing) {
								MyAnimations.startAnimationsOut(
										composerButtonsWrapper, 300);
								composerButtonsShowHideButtonIcon
										.startAnimation(MyAnimations
												.getRotateAnimation(-270, 0,
														300));
								composerButtonsWrapper.setBackgroundResource(0);
								// ValueAnimator animator =
								// ValueAnimator.ofFloat(1f,0.2f);
								// animator.addUpdateListener(new
								// AnimatorUpdateListener() {
								//
								// @Override
								// public void onAnimationUpdate(ValueAnimator
								// animation) {
								// // TODO Auto-generated method stub
								// composerButtonsShowHideButton.setAlpha((float)
								// animation.getAnimatedValue());
								// }
								// });
								// animator.setDuration(300);
								// animator.start();
								areButtonsShowing = false;
							}

							return true;
						}
					});
			setEditable(true);

		} else {
			expandablelistview
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							showTextToast("请先停止设备");
							return true;
						}
					});
			expandablelistview
					.setOnChildClickListener(new OnChildClickListener() {

						@Override
						public boolean onChildClick(ExpandableListView parent,
								View v, int groupPosition, int childPosition,
								long id) {
							// TODO Auto-generated method stub
							showTextToast("请先停止设备");
							return true;
						}
					});
			setEditable(false);
		}
	}

	private void setEditable(boolean en) {
		// TODO Auto-generated method stub
		t0k.setEnabled(en);
		t1k.setEnabled(en);
		t2k.setEnabled(en);
		t3k.setEnabled(en);
		t4k.setEnabled(en);
		t5k.setEnabled(en);
		t6k.setEnabled(en);
		t7k.setEnabled(en);
		heat0Switch.setEnabled(en);
		heat1Switch.setEnabled(en);
		heat2Switch.setEnabled(en);
		heat3Switch.setEnabled(en);
		heat4Switch.setEnabled(en);
		heat5Switch.setEnabled(en);
		heat6Switch.setEnabled(en);
		heat7Switch.setEnabled(en);
		add.setEnabled(en);
		empty.setEnabled(en);
		saveas.setEnabled(en);
		list.setEnabled(en);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (Home.messageJsonObject != null)
			try {
				switch (parent.getId()) {
				case R.id.hw1:
					((JSONArray) (Home.messageJsonObject
							.get("paramstemperature"))).put(0, position + 29
							+ "");
					break;
				case R.id.hw2:
					((JSONArray) (Home.messageJsonObject
							.get("paramstemperature"))).put(1, position + 29
							+ "");
					break;
				case R.id.hw3:
					((JSONArray) (Home.messageJsonObject
							.get("paramstemperature"))).put(2, position + 29
							+ "");
					break;
				case R.id.hw4:
					((JSONArray) (Home.messageJsonObject
							.get("paramstemperature"))).put(3, position + 29
							+ "");
					break;
				case R.id.hw5:
					((JSONArray) (Home.messageJsonObject
							.get("paramstemperature"))).put(4, position + 29
							+ "");
					break;
				case R.id.hw6:
					((JSONArray) (Home.messageJsonObject
							.get("paramstemperature"))).put(5, position + 29
							+ "");
					break;
				case R.id.hw7:
					((JSONArray) (Home.messageJsonObject
							.get("paramstemperature"))).put(6, position + 29
							+ "");
					break;
				case R.id.hw8:
					((JSONArray) (Home.messageJsonObject
							.get("paramstemperature"))).put(7, position + 29
							+ "");
					break;

				default:
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
				// System.out.println(e);
			}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

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
				((JSONArray) (Home.messageJsonObject.get("paramstemperature")))
						.put(8, isChecked);
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
				((JSONArray) (Home.messageJsonObject.get("paramstemperature")))
						.put(9, isChecked);
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
				((JSONArray) (Home.messageJsonObject.get("paramstemperature")))
						.put(10, isChecked);
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
				((JSONArray) (Home.messageJsonObject.get("paramstemperature")))
						.put(11, isChecked);
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
				((JSONArray) (Home.messageJsonObject.get("paramstemperature")))
						.put(12, isChecked);
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
				((JSONArray) (Home.messageJsonObject.get("paramstemperature")))
						.put(13, isChecked);
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
				((JSONArray) (Home.messageJsonObject.get("paramstemperature")))
						.put(14, isChecked);
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
				((JSONArray) (Home.messageJsonObject.get("paramstemperature")))
						.put(15, isChecked);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

}
