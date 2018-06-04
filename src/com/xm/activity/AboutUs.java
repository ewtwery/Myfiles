package com.xm.activity;

import java.util.Hashtable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.example.socketserialport.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.xm.tools.Tools;

public class AboutUs extends Activity{
	
	private TextView title;
	private Button btn_title;
	private ImageView IDcode, appImg;
	private TextView ID;
	private ImageView weichat;
	private Bitmap idBitmap ,appBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_aboutus);
		initView();
		idBitmap = createQRImage("唯一ID="+Home.MACHINEID);
		appBitmap = createQRImage("http://115.159.185.15:8080/PNAEServer/app.apk");
        IDcode.setImageBitmap(idBitmap);
        appImg.setImageBitmap(appBitmap);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Home.currentActivity=AboutUs.this;
		super.onResume();
	}

	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.versionname)).setText(Tools.getAppVersionName(this));
		Home.TheActivityIs = "AboutUs";
		IDcode=(ImageView)findViewById(R.id.IDCode);
		appImg=(ImageView)findViewById(R.id.app);
		weichat=(ImageView)findViewById(R.id.weichat);
		title = (TextView) findViewById(R.id.title);
		btn_title = (Button) findViewById(R.id.actionbar_title);
		ID = (TextView)findViewById(R.id.ID);
		ID.setText(Home.MACHINEID);
		title.setText("关于");
		btn_title.setText("主页");
		btn_title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		weichat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showBiggerImage(v,BitmapFactory.decodeResource(getResources(), R.drawable.qrcode_for_weichat),null);
			}
		});
		IDcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showBiggerImage(v,idBitmap,Home.MACHINEID);
			}
		});
	}
	
	public void home(View v){
		finish();
	}
	
	protected void showBiggerImage(View v,Bitmap bitmap,String tintStr) {
		// TODO Auto-generated method stub
		LinearLayout layout = new LinearLayout(AboutUs.this);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams layoutParams ;
		if(tintStr!=null){
			layoutParams = new  LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			TextView textView = new TextView(AboutUs.this);
			textView.setText(tintStr);
			textView.setBackgroundColor(Color.WHITE);
			textView.setLayoutParams(layoutParams);
			layout.addView(textView);
		}
		layoutParams = new  LinearLayout.LayoutParams(200,200);
		ImageView img= new ImageView(AboutUs.this);
		img.setImageBitmap(bitmap);
		img.setLayoutParams(layoutParams);
		layout.addView(img);
		PopupWindow popupWindow = new PopupWindow(layout, 200, 250);
//		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(v);
	}

	/**
	 * 生成二维码图片
	 * @param url
	 */
	public Bitmap createQRImage(String url)
    {
		Bitmap bitmap = null; 
		int QR_WIDTH=300;
		int QR_HEIGHT=300;
        try
        {
            //判断URL合法性
            if (TextUtils.isEmpty(url))
            {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            
			//图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++)
            {
                for (int x = 0; x < QR_WIDTH; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            bitmap = zoomImg(bitmap, 200, 200);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }
	
	/**
	 * 缩放图片到指定大小
	 * @param bm
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	 public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){   
		    // 获得图片的宽高   
		    int width = bm.getWidth();   
		    int height = bm.getHeight();   
		    // 计算缩放比例   
		    float scaleWidth = ((float) newWidth) / width;   
		    float scaleHeight = ((float) newHeight) / height;   
		    // 取得想要缩放的matrix参数   
		    Matrix matrix = new Matrix();   
		    matrix.postScale(scaleWidth, scaleHeight);   
		    // 得到新的图片
		    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);   
		    return newbm;   
		}  

}

