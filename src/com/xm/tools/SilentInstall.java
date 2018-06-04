package com.xm.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.xm.activity.Home;

/** 
 * 静默安装的实现类，调用install()方法执行具体的静默安装逻辑。 
 * 原文地址：http://blog.csdn.net/guolin_blog/article/details/47803149 
 * @author guolin 
 * @since 2015/12/7 
 */  
public class SilentInstall {  
  
    /** 
     * 执行具体的静默安装逻辑，需要手机ROOT。 
     * @param apkPath 
     *          要安装的apk文件的路径 
     * @return 安装成功返回true，安装失败返回false。 
     */  
    public static boolean install(String apkPath) { 


    	//主要就是通过,安装程序之前,启动一个定时任务,任务发送一个广播,广播收到之后,启动程序
        
    	
    	File apkfile = new File(Environment.getExternalStorageDirectory(), apkPath);  
        boolean result = false;  
        DataOutputStream dataOutputStream = null;  
        BufferedReader errorStream = null;  
        try {  
            // 申请su权限  
            Process process = Runtime.getRuntime().exec("su");  
            dataOutputStream = new DataOutputStream(process.getOutputStream());  
            // 执行pm install命令  
            String command = "pm install -r " + apkfile + "\n";  
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));  
            dataOutputStream.flush();  
            dataOutputStream.writeBytes("exit\n");  
            dataOutputStream.flush();  
            process.waitFor();  
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));  
            String msg = "";  
            String line;  
            // 读取命令的执行结果  
            while ((line = errorStream.readLine()) != null) {  
                msg += line;  
            }  
            Log.d("TAG", "install msg is " + msg);  
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功  
            if (!msg.contains("Failure")) {  

            	
                result = true;  
            }  
        } catch (Exception e) {  
            Log.e("TAG", e.getMessage(), e);  
        } finally {  
            try {  
                if (dataOutputStream != null) {  
                    dataOutputStream.close();  
                }  
                if (errorStream != null) {  
                    errorStream.close();  
                }  
            } catch (IOException e) {  
                Log.e("TAG", e.getMessage(), e);  
            }  
        }  
        return result;  
    }  
  
}  