package com.xm.Widget;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.socketserialport.R;

public class CustomadeToast {
	boolean mShowTime;
	boolean mIsShow;
	WindowManager mWdm;
	View mToastView;
	Timer mTimer;
	WindowManager.LayoutParams mParams;
	Handler handler;
	
	public CustomadeToast(Context context, String text, boolean showTime) {
		// TODO Auto-generated constructor stub
		mShowTime = showTime;//记录Toast的显示长短类型
		  mIsShow = false;//记录当前Toast的内容是否已经在显示
		  mWdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		  //通过Toast实例获取当前android系统的默认Toast的View布局
		  mToastView = Toast.makeText(context, text, Toast.LENGTH_SHORT).getView();
		  mTimer = new Timer();
		  //设置布局参数
		  setParams();
		  
		  handler = new Handler(){
			 public void handleMessage(Message msg){
				  if(msg.what==1){
					  if(mIsShow)
					  mWdm.removeView(mToastView);
				        mIsShow = false;
				  }
			  }
		  };
	}
	
	
	
	public static CustomadeToast MakeText(Context context, String text, boolean showTime) {
		CustomadeToast result = new CustomadeToast(context, text, showTime);
	    return result;
	}

	private void setParams() {
		// TODO Auto-generated method stub
		mParams = new WindowManager.LayoutParams();
		  mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;  
		  mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;  
		  mParams.format = PixelFormat.TRANSLUCENT;  
		  mParams.windowAnimations = R.style.anim_view;//设置进入退出动画效果
		  mParams.type = WindowManager.LayoutParams.TYPE_TOAST;  
		  mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON  
		      | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  
		      | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		  mParams.gravity = Gravity.CENTER_HORIZONTAL;
		  mParams.y = 250;
	}
	
	public void show(){
		  if(!mIsShow){//如果Toast没有显示，则开始加载显示
		    mWdm.addView(mToastView, mParams);//将其加载到windowManager上
		    mIsShow = true;
		    mTimer.schedule(new TimerTask() {
		      @Override
		      public void run() {
		    	  handler.sendEmptyMessage(1);
		      }
		    }, (long)(mShowTime ? 3500 : 2000));
		  }
		}
	
	public void dismiss() {
		// TODO Auto-generated method stub
		if(mIsShow){
			mWdm.removeView(mToastView);
			mIsShow=false;
		}
	}
}
