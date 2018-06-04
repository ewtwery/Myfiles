package com.xm.receiver;

import com.xm.activity.Home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartReceiver extends BroadcastReceiver {

	@Override
    public void onReceive(Context context, Intent intent) {
		System.out.println("StartReceiver onReceive>>>>"+intent.getAction());
        if (intent.getAction().equalsIgnoreCase("appstart")) {
            Intent intent2 = new Intent(context, Home.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//注意,不能少
            context.startActivity(intent2);
        }
    }


}
