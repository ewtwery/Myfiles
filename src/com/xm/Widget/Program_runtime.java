package com.xm.Widget;

import com.example.socketserialport.R.color;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.provider.Telephony.Sms.Conversations;
import android.util.AttributeSet;
import android.widget.TextView;

public class Program_runtime extends TextView {

	int[] vol = new int[6];
	int select_hole = -1;
	public Program_runtime(Context context) {
		super(context);
		Paint myPaint = new Paint();
	}

	public Program_runtime(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public Program_runtime(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setVol(int[] vol){
		this.vol = vol;
	}

	public void setVol(String[] vol_str){
		for(int i = 0; i < 6; i++){
			vol[i] = Integer.parseInt(vol_str[i]);
		}
	}

	public void setSelectHole(int select_hole){
		this.select_hole = select_hole - 1;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint myPaint = new Paint();
		myPaint.setColor(getResources().getColor(color.draw_tube));
//		p.setAntiAlias(true);
		float startX, startY;
		startX = (getWidth()-380)/2;
		startY = (getHeight() - 220)/2;
		canvas.drawRect(startX, startY + 40, getWidth()-startX, startY + 60, myPaint);
		drawArrow(canvas, startX + 30, startY);
		for(int i = 0; i < 6; i++){
			drawtube(canvas, i * 60 + startX + 20, startY + 60, i);
		}
	}

	void drawArrow(Canvas canvas, float x, float y){
		if(select_hole == -1) return;
		int offset =  select_hole * 60;
		Path path = new Path();
		path.moveTo(offset + x, y);
		path.lineTo(offset + x, y + 30);
		path.lineTo(offset + x + 10, y + 38);
		path.lineTo(offset + x + 20, y + 30);
		path.lineTo(offset + x + 20, y);
		path.close();
		Paint myPaint = new Paint();
		myPaint.setStyle(Style.FILL);
		myPaint.setColor(getResources().getColor(color.draw_rect));
		canvas.drawPath(path, myPaint);
	}
	
	void drawtube(Canvas canvas, float x, float y, int hole){
		Paint myPaint = new Paint();
		myPaint.setAntiAlias(true);
		myPaint.setColor(getResources().getColor(color.draw_tube));
		canvas.drawRect(x, y, x + 40, y + 120, myPaint);
		if(vol[hole] > 0){
			myPaint.setColor(getResources().getColor(color.draw_rect));
			int n = vol[hole] / 10;
			canvas.drawRect(x, y + 120 - n, x + 40, y + 120, myPaint);
		}
		RectF oval = new RectF(x, y + 100, x + 40, y + 140);
		canvas.drawArc(oval, 0, 180, true, myPaint);
		String str = (hole + 1) + ""; 
		myPaint.setColor(Color.WHITE);
		myPaint.setTextSize(30);
		canvas.drawText(str, x + 12, y + 30, myPaint);

		myPaint.setColor(Color.BLACK);
		myPaint.setTextSize(16);
		str = vol[hole] + "μL";
		Rect rect=new Rect();
		myPaint.getTextBounds(str, 0, str.length(), rect);  
		float start = x + 20 - rect.centerX();  
		float end =  y + 160;
		canvas.drawText(str, start, end, myPaint);
//		myPaint.setStyle(Style.STROKE);
//		myPaint.setStrokeWidth(2);
//		canvas.drawCircle(x + 20, y + 20, 16, myPaint);
	}
	
}
