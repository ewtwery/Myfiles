package com.xm.thread;

import com.xm.activity.Home;
import com.xm.var.StaticVar;


/**
 * Created by liuwei on 2016/8/10.
 */
public class ReLoginThread extends Thread {

    static ReLoginThread reconnectionThread;
    private int waiting=0;
    private static boolean flag=false;

    public static ReLoginThread getInstance(){
        if(reconnectionThread==null){
            reconnectionThread = new ReLoginThread();
        }
        return reconnectionThread;
    }

    public static ReLoginThread getReconnectionThread(){
        return reconnectionThread;
    }

    @Override
    public void run() {
        try {
            while (flag) {
//            	System.out.println("等待"+(long) waiting() * 1000L+"重连");
                Thread.sleep((long) waiting() * 1000L);
                
//                xmppManager.connect();
                if(flag)
                Home.mainHandler.sendEmptyMessage(StaticVar.LOGIN);
                waiting++;
            }
            System.out.println("重连线程已停止");
        } catch (final InterruptedException e) {
//            xmppManager.getHandler().post(new Runnable() {
//                public void run() {
//                    xmppManager.getConnectionListener().reconnectionFailed(e);
//                }
//            });
        }
    }


    public static void startReconnectionThread(){
        synchronized (reconnectionThread) {
            if(reconnectionThread!=null&&!reconnectionThread.isAlive()&&!flag){
                flag=true;
                reconnectionThread.start();
                System.out.println("启动重连线程");
            }
        }
    }

    public static synchronized void stopReconnectionThread(){
        flag=false;
        reconnectionThread=null;
        
//        System.out.println("停止重连线程");
    }

    private int waiting(){
        if (waiting > 20) {
            return 600;
        }
        if (waiting > 13) {
            return 300;
        }
        return waiting <= 7 ? 10 : 60;
    }
}
