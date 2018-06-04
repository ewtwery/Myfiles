package com.xm.thread;

import android.os.Message;

import com.xm.Bean.ReplyBean;
import com.xm.activity.Home;

public class GetTemperature extends Thread {
	private Boolean stop_flag = false;
	int getTemperFlag = 1;

	void startGetTemp(){
		try {
			Thread.sleep(100);
		} catch (Exception e) {
		}
		getTemperFlag = 1;
	}
	
	void stopGetTemp(){
		getTemperFlag = 2;
		while(!stop_flag){
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			if(getTemperFlag == 1){
				return;
			}
		}
		stop_flag = false;
	}
	
	ReplyBean getTemper(){
		ReplyBean reply = new ReplyBean();
		byte[] buff = new byte[5];
        buff[0] = (byte) 0xf4;
        ReplyBean r1 = Home.serialport.sendToTemperUnits(buff);
        if(r1.error > 0) return r1;
        recvDataHandle(byteToInt(r1.buff),reply.values);
        return reply;
	}
	
	/*public Boolean operaterBlending(int motion, ParamsBean paramsBean){
		stopGetTemp();
		byte[] buff = new byte[5];
        buff[0] = (byte) (0xf0 + motion);
        if(motion == 0){
        	int temp = paramsBean.getTemperature() * 10;
        	buff[1] = (byte) (temp >> 8);
	        buff[2] = (byte) (temp & 0xff);
	        buff[3] = (byte) (paramsBean.getSpeed() >> 8);
	        buff[4] = (byte) (paramsBean.getSpeed() & 0xff);
        }
        ReplyBean r1 = Home.serialport.sendToTemperUnits(buff);
        startGetTemp();
        if(r1.error > 0) return false;
        if(r1.buff[0] == buff[0]){
        	return true;
        }else{
        	return false;
        }
	}*/
	
	int[] byteToInt(byte[] b) {
		int[] res = new int[b.length];
		for(int i = 0; i < b.length; i++){
			res[i] = (int)b[i] & 0xff;
		}
        return res;
    }

	void recvDataHandle (int[] buff,double[] values){
		values[0] = (buff[1] * 256 + buff[2])/10d;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			if(getTemperFlag == 1){
				Message msg = null;
				try {
					if(Home.TheActivityIs.equals("Program_RuntimeView")){
//						msg = Program_RuntimeView.handler.obtainMessage();
					}else{
						msg = Home.mainHandler.obtainMessage();
					}
				} catch (Exception e) {
					msg = Home.mainHandler.obtainMessage();
				}
				ReplyBean reply = getTemper();
				if(reply.error>0){
					msg.what=1;
					msg.arg1 = 2;
					msg.obj = reply.error_str;
					msg.sendToTarget();
				}else{
					msg.what=1;
					msg.arg1 = 1;
					msg.obj = reply.values;
					msg.sendToTarget();
				}
			}else if(getTemperFlag == 2){
				stop_flag = true;
			}
		}
	}
}
