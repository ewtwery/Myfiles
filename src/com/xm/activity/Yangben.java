package com.xm.activity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socketserialport.R;
import com.xm.var.StaticVar;

public class Yangben extends Activity implements OnClickListener {
	public TextView title;
	public Button btn_title;
	private Button yangben1;
	private Button yangben2;
	private Button yangben3;
	private Button yangben4;
	private Button yangben5;
	private Button yangben6;
	private Button yangben7;
	private Button yangben8;
	private Intent intent;
	String centertitle;
	Toast toast;
	String[] yangbenArrayItems;
	ProgressDialog loadProgressDialog;
	private TextView actionbar_title;
	boolean beclicked = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		/*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 */
		setContentView(R.layout.layout_yangben);
		Home.TheActivityIs = "Yangben";
//		Tools.removeStatusBarAndNavigationBar(this);
		title = (TextView) findViewById(R.id.title);
		btn_title = (Button) findViewById(R.id.actionbar_title);
		btn_title.setText("主页");
		Button back = (Button) findViewById(R.id.back);
		Intent intent = getIntent();
		centertitle = intent.getStringExtra("title");
		title.setText(centertitle);
		yangbenArrayItems = getResources().getStringArray(R.array.yangbenming);
		init();
		registerReciver();
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
	}
	

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		
		
		JSONObject jsonObject = null;
		String line = null;
		String jsonStr = null;
		
		try {
			FileInputStream inputStream = openFileInput("DNA&RNA.txt");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));
			StringBuffer sb = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			jsonObject = new JSONObject(sb.toString().trim());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(beclicked){
		switch (v.getId()) {

		case R.id.yangben1:
			
				
			
			try {
					jsonStr = jsonObject.getJSONObject("yangben1")
							.toString();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			intent.putExtra("name", yangbenArrayItems[0]);
			break;
		case R.id.yangben2:
			try {
					jsonStr = jsonObject.getJSONObject("yangben2")
							.toString();
			} catch (Exception e) {
				// TODO: handle exception
			}

			intent.putExtra("name", yangbenArrayItems[1]);

			break;
		case R.id.yangben3:
			
			try {
					jsonStr = jsonObject.getJSONObject("yangben3")
							.toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			intent.putExtra("name", yangbenArrayItems[2]);
			break;
		case R.id.yangben4:
			try {
					jsonStr = jsonObject.getJSONObject("yangben4")
							.toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			intent.putExtra("name", yangbenArrayItems[3]);
			break;
		case R.id.yangben5:
			try {
					jsonStr = jsonObject.getJSONObject("yangben5")
							.toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			intent.putExtra("name", yangbenArrayItems[4]);
			break;
		case R.id.yangben6:
			try {
					jsonStr = jsonObject.getJSONObject("yangben6")
							.toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			intent.putExtra("name", yangbenArrayItems[5]);
			break;
		case R.id.yangben7:
			try {
					jsonStr = jsonObject.getJSONObject("yangben7")
							.toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			intent.putExtra("name", yangbenArrayItems[6]);
			break;
		case R.id.yangben8:
			try {
					jsonStr = jsonObject.getJSONObject("yangben8")
							.toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			intent.putExtra("name", yangbenArrayItems[7]);
			break;
		}
		
		if(TextUtils.isEmpty(jsonStr)){
			if(toast==null)
			toast = Toast.makeText(Yangben.this, "当前项数据为空", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		intent.putExtra("fromactivity", "Yangben");
		intent.setAction("from yangben");
		intent.putExtra("jsonStr", jsonStr);
		intent.setClass(Yangben.this, Standard_RuntimeView.class);
		loadProgressDialog = ProgressDialog.show(Yangben.this, "正在加载...",
				"请稍候...");
		startActivity(intent);
		}
		beclicked=false;
	}

	void init() {
		intent = new Intent();

		yangben1 = (Button) findViewById(R.id.yangben1);
		yangben2 = (Button) findViewById(R.id.yangben2);
		yangben3 = (Button) findViewById(R.id.yangben3);
		yangben4 = (Button) findViewById(R.id.yangben4);
		yangben5 = (Button) findViewById(R.id.yangben5);
		yangben6 = (Button) findViewById(R.id.yangben6);
		yangben7 = (Button) findViewById(R.id.yangben7);
		yangben8 = (Button) findViewById(R.id.yangben8);
		actionbar_title = (TextView) findViewById(R.id.actionbar_title);

		yangben1.setText(yangbenArrayItems[0]);
		yangben2.setText(yangbenArrayItems[1]);
		yangben3.setText(yangbenArrayItems[2]);
		yangben4.setText(yangbenArrayItems[3]);
		yangben5.setText(yangbenArrayItems[4]);
		yangben6.setText(yangbenArrayItems[5]);
		yangben7.setText(yangbenArrayItems[6]);
		yangben8.setText(yangbenArrayItems[7]);
		
		yangben1.setOnClickListener(this);
		yangben2.setOnClickListener(this);
		yangben3.setOnClickListener(this);
		yangben4.setOnClickListener(this);
		yangben5.setOnClickListener(this);
		yangben6.setOnClickListener(this);
		yangben7.setOnClickListener(this);
		yangben8.setOnClickListener(this);
		btn_title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		actionbar_title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
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
            Yangben.this.finish();
        }
    };
    
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(loadProgressDialog!=null){
			loadProgressDialog.dismiss();
			loadProgressDialog=null;
		}
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		Tools.removeStatusBarAndNavigationBar(Yangben.this);
		beclicked=true;
		super.onResume();
	}

}
