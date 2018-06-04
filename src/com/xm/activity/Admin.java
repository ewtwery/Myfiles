package com.xm.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socketserialport.R;
import com.xm.Bean.MessageBean;
import com.xm.Widget.CustomadeDialog;
import com.xm.Widget.CustomadeToast;
import com.xm.mina.Client;
import com.xm.mina.RequestCallBack;
import com.xm.tools.Serialport;
import com.xm.tools.SilentInstall;
import com.xm.tools.Tools;
import com.xm.var.StaticVar;

public class Admin extends Activity implements OnClickListener{

	Button editParams;
	Button test;
	Button reset;
	Button btn_title;
	Button btn_save;
	Button btn_uninstall;
	Button btn_install;
	Button btn_showbar;
	Button btn_hidebar;
	TextView title , version;
	EditText xdelt;
	EditText y1firstmov;
	EditText y2firstmov;
	EditText t4firsttemp;
	EditText t5firsttemp;
	private Spinner baudspinner;
//	private TextView baud;
	EditText input_info;
	ProgressDialog progressDialog = null;
	public static Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		Tools.removeStatusBarAndNavigationBar(Admin.this);
		setContentView(R.layout.layout_admin);
		init();
		initHandler();
		Home.serialport.SendMsg(Tools.packagebag("sss", "6"));
	}
	
	void initHandler() {
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String[] str = new String[2];
					str = (String[]) msg.obj;
					if(str[0].equals("version")) version.setText(str[1]);
					break;
				default:
					break;
				}
			};
		};
	}
	
	public void baud(View v){
		SharedPreferences preferences = getSharedPreferences("baud", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("baud", Integer.valueOf(baudspinner.getSelectedItem().toString()));
		editor.commit();
		Home.serialport.closeSerialPort();
		Home.serialport = new Serialport(0, Integer.valueOf(baudspinner.getSelectedItem().toString()), 8, 1, 0);
		Home.serialport.openSerialPort();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		btn_title=(Button)findViewById(R.id.actionbar_title);
		btn_title.setText("返回");
		btn_title.setOnClickListener(this);
		title=(TextView)findViewById(R.id.title);
		version = (TextView)findViewById(R.id.version);
//		baud=(TextView)findViewById(R.id.baud);
//		baudspinner = (Spinner)findViewById(R.id.baudrate);
		
		title.setText("管理员");
		
		
		editParams=(Button)findViewById(R.id.editParams);
		editParams.setOnClickListener(this);
		
		test=(Button)findViewById(R.id.test);
		test.setOnClickListener(this);
		
		reset=(Button)findViewById(R.id.reset);
		reset.setOnClickListener(this);
		
		xdelt = (EditText)findViewById(R.id.xdelt);
		y1firstmov = (EditText)findViewById(R.id.y1firstmov);
		y2firstmov = (EditText)findViewById(R.id.y2firstmov);
		/*edit by Qhq*/
		t4firsttemp = (EditText)findViewById(R.id.t4firsttemp);
		t5firsttemp = (EditText)findViewById(R.id.t5firsttemp);
		
		SharedPreferences preferences = getSharedPreferences("motionParams", Context.MODE_PRIVATE);
		xdelt.setText(preferences.getString("xdelt", ""));
		y1firstmov.setText(preferences.getString("y1firstmov", ""));
		y2firstmov.setText(preferences.getString("y2firstmov", ""));
		/*edit by Qhq*/
		t4firsttemp.setText(preferences.getString("t4firsttemp", "0"));
		t5firsttemp.setText(preferences.getString("t5firsttemp", "0"));
		
//		SharedPreferences baudpreferences = getSharedPreferences("baud", Context.MODE_PRIVATE);
//		baud.setText(baudpreferences.getInt("baud", 19200)+"");
		
		input_info = (EditText)findViewById(R.id.input_info);
		input_info.setText(Home.MACHINEID);
		btn_save = (Button)findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		btn_uninstall = (Button)findViewById(R.id.btn_uninstall);
		btn_uninstall.setOnClickListener(this);
		btn_install = (Button)findViewById(R.id.btn_install);
		btn_install.setOnClickListener(this);
		btn_showbar = (Button)findViewById(R.id.btn_showbar);
		btn_showbar.setOnClickListener(this);
		btn_hidebar = (Button)findViewById(R.id.btn_hidebar);
		btn_hidebar.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.editParams:
			SharedPreferences preferences = getSharedPreferences("motionParams", Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putString("xdelt", xdelt.getText().toString());
			editor.putString("y1firstmov", y1firstmov.getText().toString());
			editor.putString("y2firstmov", y2firstmov.getText().toString());
			
			/*edit by Qhq*/
			editor.putString("t4firsttemp", t4firsttemp.getText().toString());
			editor.putString("t5firsttemp", t5firsttemp.getText().toString());
			
			editor.commit();
			Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
			break;
		case R.id.test:
			Tools.sendMotionParams(Admin.this);
			Home.serialport.SendMsg(Tools.packagebag("fla", "5"));
			Home.serialport.SendMsg(Tools.packagebag("alb", "5"));
			Home.serialport.SendMsg(Tools.packagebag("tlc", "0"));
			Home.serialport.SendMsg(Tools.packagebag("tld", "0"));
			Home.serialport.SendMsg(Tools.packagebag("vjv", "6"));
			Home.serialport.SendMsg(Tools.packagebag("mode", "0"));
			Home.serialport.SendMsg(Tools.packagebag("sss", "3"));
			break;
		case R.id.reset:
			Home.serialport.SendMsg(Tools.packagebag("sss", "5"));
			SharedPreferences sharedPreferences = getSharedPreferences("flag",
					Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();// 获取编辑器
			editor.putBoolean("citao", true);
			editor.putBoolean("light", true);
			editor.commit();
			break;
		case R.id.actionbar_title:
			finish();
			break;
		case R.id.btn_save:
			String str = input_info.getText().toString();
			if(!str.equals("")){
				Home.MACHINEID = str;
				SharedPreferences sharedpreferences = getSharedPreferences("machineinfo",
						Context.MODE_PRIVATE);
				editor = sharedpreferences.edit();
				editor.putString("MACHINEID",str);
				editor.commit();
				Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show(); 
			}else{
				Toast.makeText(getApplicationContext(), "设备号不能为空！", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_uninstall:
			Uri packageUri = Uri.parse("package:com.xm_biotech.launcher");  
			Intent intent = new Intent(Intent.ACTION_DELETE,packageUri);  
			startActivity(intent); 
			break;
		case R.id.btn_install:
			downLoadLauncher(); 
			break;
		case R.id.btn_showbar:
			navbarShow(true); 
			break;
		case R.id.btn_hidebar:
			navbarShow(false); 
			break;
		}
	}
	
	void navbarShow(boolean show){
		Intent intent=new Intent();
		intent.setAction("ACTION_SHOW_NAVBAR");
		intent.putExtra("cmd",show?"show":"hide");
		sendBroadcast(intent,null);
	}
	
	public void home(View v){
		Intent intent = new Intent();
        intent.setAction(StaticVar.FINISH_ACTIVITY);
        sendBroadcast(intent);
		finish();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		reset.performClick();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Home.TheActivityIs = null;
		super.onDestroy();
		reset.performClick();
	}
	
	void downLoadLauncher(){
		if(!Client.getInstance().isServerIsConnected()){
			Toast.makeText(this, "未登录服务器", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(this, "下载中...", Toast.LENGTH_LONG).show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				MessageBean messageBean = Client.getInstance().getDownloadFileRequestBean(
								"machine/snaeii32","launcher.apk");
				Client.getInstance().sendRquestForResponse(messageBean,false,
				new RequestCallBack<MessageBean>() {
					@Override
					public void Response(MessageBean t) {
						if (t.getContent().getContenttype() != null) {
							if (t.getContent().getContenttype().equals("filelength"))
								Client.getInstance().fileLength = Long.parseLong(t.getContent().getStringcontent());
						} else {
							try {
								File filePath = Environment
										.getExternalStorageDirectory();
								String savePath = filePath + "/" + "launcher.apk";
								FileOutputStream out = new FileOutputStream(savePath);
								FileChannel fc = out.getChannel();
								fc.write(ByteBuffer.wrap(t.getContent().getBytecontent()));
								Tools.runCommand("chmod 777 " + savePath);
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(Admin.this, "下载完成，安装中...", Toast.LENGTH_SHORT).show();
									}
								});
								Intent ite = new Intent();
						        ite.setAction("startme");
						        Admin.this.sendBroadcast(ite);
								SilentInstall.install("launcher.apk");
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(Admin.this, "安装完成", Toast.LENGTH_SHORT).show();
									}
								});
							} catch (Exception e) {
							}
						}
					}
				});
			}
		}).start();
	}
}
