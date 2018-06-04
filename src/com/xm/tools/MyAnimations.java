package com.xm.tools;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class MyAnimations {

	private static int xOffset = 0;
	private static int yOffset = -0;

	public static void initOffset(Context context) {// 由布局文件
		xOffset = (int) (10.667 * context.getResources().getDisplayMetrics().density);
		yOffset = -(int) (8.667 * context.getResources().getDisplayMetrics().density);
	}

	public static Animation getRotateAnimation(float fromDegrees,
			float toDegrees, int durationMillis) {
		RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(durationMillis);
		rotate.setFillAfter(true);
		
		
		return rotate;
	}

	public static void startAnimationsIn(ViewGroup viewgroup, int durationMillis) {
		for (int i = 0; i < viewgroup.getChildCount(); i++) {
			Button inoutimagebutton = (Button) viewgroup
					.getChildAt(i);
			inoutimagebutton.setVisibility(0);
			MarginLayoutParams mlp = (MarginLayoutParams) inoutimagebutton
					.getLayoutParams();
			Animation animation = new TranslateAnimation(0-mlp.leftMargin,0F,480-mlp.bottomMargin,0F);
//			System.out.println(mlp.rightMargin- xOffset+"");
			animation.setFillAfter(true);
			animation.setDuration(durationMillis);
			animation.setStartOffset((i * 100)
					/ (-1 + viewgroup.getChildCount()));
			animation.setInterpolator(new OvershootInterpolator(2F));
			inoutimagebutton.startAnimation(animation);

		}
	}

	public static void startAnimationsOut(ViewGroup viewgroup,
			int durationMillis) {
		for (int i = 0; i < viewgroup.getChildCount(); i++) {
			final Button inoutimagebutton = (Button) viewgroup
					.getChildAt(i);
			MarginLayoutParams mlp = (MarginLayoutParams) inoutimagebutton
					.getLayoutParams();
			Animation animation = new TranslateAnimation(0F,0-mlp.leftMargin,0F,480-mlp.bottomMargin);
//			System.out.println(mlp.rightMargin- xOffset+"");
			animation.setFillAfter(true);
			animation.setDuration(durationMillis);
			animation.setStartOffset(((viewgroup.getChildCount() - i) * 100)
					/ (-1 + viewgroup.getChildCount()));// 顺序倒一下比较舒服
			animation.setInterpolator(new AnticipateInterpolator(2F));
			animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation arg0) {
				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					inoutimagebutton.clearAnimation();
					inoutimagebutton.layout(0, 480-inoutimagebutton.getHeight(), 0+inoutimagebutton.getWidth(), 480);
					inoutimagebutton.setVisibility(View.GONE);
				}
			});
			inoutimagebutton.startAnimation(animation);
		}

	}

}