package com.xm.tools;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.xm.activity.Home;
import com.xm.activity.Set;
import com.xm.var.StaticVar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.ScanResult;
import android.os.Environment;
import android.os.Message;


public class Tools {
	public static boolean runCommand(String command) {   
	    Process process = null;   
	        try {   
	            process = Runtime.getRuntime().exec(command);   
	            process.waitFor();   
	        } catch (Exception e) {   
	                return false;   
	        } finally {   
	                try {   
	                        process.destroy();   
	                } catch (Exception e) {   
	                }   
	        }   
	        return true;   
	} 
	
	
	/** 
	 * 执行shell命令 
	 *  
	 * @param cmd 
	 */  
	public static void execShellCmd(String cmd) {  
	  
	    try {  
	        // 申请获取root权限，这一步很重要，不然会没有作用  
	        Process process = Runtime.getRuntime().exec("su");  
	        // 获取输出流  
	        OutputStream outputStream = process.getOutputStream();  
	        DataOutputStream dataOutputStream = new DataOutputStream(  
	                outputStream);  
	        dataOutputStream.writeBytes(cmd);  
	        dataOutputStream.flush();  
	        dataOutputStream.close();  
	        outputStream.close(); 
	    } catch (Throwable t) {  
	        t.printStackTrace();  
	    }  
	}  
	
	/** 
	 * 执行shell命令 
	 *  
	 * @param cmd 
	 */  
	public static void execShellCmdWait4End(String cmd) {  
	  
	    try {  
	        // 申请获取root权限，这一步很重要，不然会没有作用  
	        Process process = Runtime.getRuntime().exec("su");  
	        // 获取输出流  
	        OutputStream outputStream = process.getOutputStream();  
	        DataOutputStream dataOutputStream = new DataOutputStream(  
	                outputStream);  
	        dataOutputStream.writeBytes(cmd);  
	        dataOutputStream.flush();  
	        dataOutputStream.close();  
	        outputStream.close();  
	        process.waitFor();
	    } catch (Throwable t) {  
	        t.printStackTrace();  
	    }  
	}  
	
	
	
	
//	public static void removeStatusBarAndNavigationBar(Context context){
//		((Activity) context).getWindow().getDecorView().setSystemUiVisibility(
//				View.GONE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//	}
	
	/**
	 * 排序
	 * @param list 数据源
	 * @param isAsc
	 */
	
	public static void sort(List<ScanResult> list, boolean isAsc){
		Collections.sort(list, new Comparator<ScanResult>() {
			@Override
			public int compare(ScanResult o1, ScanResult o2) {
				try {
					Object f1 = o1.level;
					Object f2 = o2.level;
					if(f1 instanceof Number && f2 instanceof Number){
					return ((Number)f2).intValue() - ((Number)f1).intValue();
					}else{
					return f2.toString().compareTo(f1.toString());
					}
				} catch (Exception e) {
					// TODO: handle exception
					return 0;
				}
			
			}
			});
	}

	static int strToInt(String str){
		int res = 0;
		try {
			res = Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}
		return res;
	}
	
	public static void sendMotionParams(Context context){
		SharedPreferences preferences = context.getSharedPreferences("motionParams", Context.MODE_PRIVATE);
		
		int temp = strToInt(preferences.getString("xdelt", "0"))  * 80;
		Home.serialport.SendMsg(Tools.packagebag("x3f", temp+""));
		
		temp = strToInt(preferences.getString("y1firstmov", "0")) * 393 / 100;
		Home.serialport.SendMsg(Tools.packagebag("y1f", temp+""));

		temp = strToInt(preferences.getString("y2firstmov", "0")) * 393 / 100;
		Home.serialport.SendMsg(Tools.packagebag("y2f", temp+""));
		
		/*edit by Qhq*/
		Home.serialport.SendMsg(Tools.packagebag("t4f", preferences.getString("t4firsttemp", "0")));
		Home.serialport.SendMsg(Tools.packagebag("t5f", preferences.getString("t5firsttemp", "0")));
	}
	
/**
 * 含校验
 */
//	public static String packagebag(String name, String value) {		//package函数
//		int sum = 0;
//		StringBuffer st = new StringBuffer();
//		String temp;
//		int f;
//		byte[] n = name.getBytes();
//		byte[] v = value.getBytes();
//		int[] in = new int[n.length];
//		int[] iv = new int[v.length];
//		for (int i = 0; i < in.length; i++) {
//			in[i] = n[i] & 0xff;
//			sum = sum + Integer.parseInt(Integer.toHexString(in[i]), 16);
//		}
//		for (int i = 0; i < v.length; i++) {
//			iv[i] = v[i] & 0xff;
//			sum = sum + Integer.parseInt(Integer.toHexString(iv[i]), 16);
//		}
//		sum = sum + 61;
//		sum = sum % 255;
//		String i = Integer.toBinaryString(~sum);
//		String subs = i.substring((i.length() - 8), i.length());
//		String s = Integer.toBinaryString(Integer.parseInt(subs, 2) + 1);
//		if (s.length() < 8) {
//			for (int t = 0; t < 8 - s.length(); t++) {
//				st.append("0");
//			}
//		}
//		st.append(s);
//		f = Integer.parseInt(String.valueOf(st.charAt(st.length() - 5)))
//				+ Integer.parseInt(String.valueOf(st.charAt(st.length() - 6)))
//				* 2
//				+ Integer.parseInt(String.valueOf(st.charAt(st.length() - 7)))
//				* 4
//				+ Integer.parseInt(String.valueOf(st.charAt(st.length() - 8)))
//				* 8;
//		int o = Integer.parseInt(String.valueOf(st.charAt(st.length() - 1)))
//				+ Integer.parseInt(String.valueOf(st.charAt(st.length() - 2)))
//				* 2
//				+ Integer.parseInt(String.valueOf(st.charAt(st.length() - 3)))
//				* 4
//				+ Integer.parseInt(String.valueOf(st.charAt(st.length() - 4)))
//				* 8;
//
//		temp = ":" + name + "=" + value + Integer.toHexString(f)
//				+ Integer.toHexString(o) + "\r\n";
//
//		return temp;
//
//	}
	
	public static String packagebag(String name, String value) {		//package函数
	
	String temp;
	

	temp = ":" + name + "=" + value + "\r\n";

	return temp;

}
	
	public static void SaveToFile(Context context,String sb) {
		// TODO Auto-generated method stub
		String filename = "DNA&RNA.txt";
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			//fileOutputStream = new FileOutputStream(filename);
			fileOutputStream.write(sb.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fileOutputStream != null)
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}

	public static void SaveToFileByName(Context context,String filename,String sb) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = context.openFileOutput(filename+".xml", Context.MODE_PRIVATE);
			//fileOutputStream = new FileOutputStream(filename);
			fileOutputStream.write(sb.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fileOutputStream != null)
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}
	
	public static boolean CheckVersion(String str,int version){
		try {
			JSONObject jsonObject = new JSONObject(str);
			return jsonObject.getInt("Version")>version;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}

	public static int getAppVersionCode(Context context) {
		// TODO Auto-generated method stub
		try {
			PackageManager packageManager= context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getAppVersionName(Context context) {
		// TODO Auto-generated method stub
		try {
			PackageManager packageManager= context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
//	public static void downloadFromServer(){
//		if(Home.messageJsonObject==null){
//			Home.messageJsonObject = new JSONObject();
//			
//		}
//		try {
//			Home.messageJsonObject.put("messagetype", "synchronous");
//			Home.messageJsonObject.put("paramstype", "config");
//			Home.messageJsonObject.put("device", "下载");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
//			
//		DataInputStream inputStream = new DataInputStream(new BufferedInputStream(Home.downloadclient.Client_Socket.getInputStream()));
//          String filename = inputStream.readUTF();
//          File filePath = Environment.getExternalStorageDirectory();
//
//          String savePath = filePath + "/" + filename;
//          int bufferSize = 8192;
//          byte[] buf = new byte[bufferSize];
//          int passedlen = 0;
//          long len = 0;
//
//          len = inputStream.readLong();
//          File file = new File(savePath);
////          System.out.println("File" + (file == null));
////          System.out.println("1");
//          FileOutputStream fos = null;
//          try {
//              fos = new FileOutputStream(file);
//          } catch (Exception e) {
//              // TODO: handle exception
////              System.out.println(e);
//          }
////          System.out.println("FileOutputStream" + (fos == null));
////          System.out.println("2");
////         while ((length = inputStream.read(buf, 0, buf.length)) > 0) {
////         	passedlen += length;
////         	System.out.println("长度:"+length+"文件接收了" +passedlen+"  "+ (passedlen * 100 / len) + "%\n");
////         	Message.obtain(Set.handler, 1, passedlen, (int)len).sendToTarget();
////         	fos.write(buf, 0, length);
////         	fos.flush();
////         	if((passedlen * 100 / len)==100)
////         		break;
////         }
//          while (true) {
//              int read = 0;
//              if (inputStream != null) {
//                  read = inputStream.read(buf, 0, buf.length);
//              }
//              passedlen += read;
//
//              // 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
//              Message.obtain(Set.handler, StaticVar.UPDATE_PROGRESSDIALOG, passedlen, (int) len).sendToTarget();
////              System.out.println("长度:" + read + "文件接收了" + passedlen + "  " + (passedlen * 100 / len) + "%\n");
//              fos.write(buf, 0, read);
//              if (passedlen >= len) {
//                  Message.obtain(Set.handler, 7, filename).sendToTarget();
//                  break;
//              }
//          }
//          //System.out.println("接收完成，文件存为" + savePath + "\n");
//          fos.close();
//          Home.downloadclient.StreamClose();
//          
//          Home.messageJsonObject=null;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
