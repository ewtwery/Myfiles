package com.xm.mina;



import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.xm.Bean.MessageBean;
import com.xm.activity.Home;
import com.xm.activity.NonStandard_RuntimeView;
import com.xm.activity.Set;
import com.xm.activity.Standard_RuntimeView;
import com.xm.tools.Falcon;
import com.xm.tools.MyApplication;
import com.xm.tools.MyBitmapFactory;
import com.xm.tools.Tools;
import com.xm.var.StaticVar;

/**
 * Created by liuwei on 2017/1/20.
 */
public class ClientHandler extends IoHandlerAdapter {
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
            super.messageReceived(session,message);
        System.out.println("获得服务器传过来的数据");
        if(message instanceof MessageBean){
            MessageBean messageBean = (MessageBean)message;
            System.out.println("action>>"+messageBean.getAction());
//            File filePath = Environment.getExternalStorageDirectory();
//            String savePath = filePath + "/" + "32.doc";
//            System.out.print("savePath>>" + savePath);
//            FileOutputStream out = new FileOutputStream(savePath);
//            FileChannel fc = out.getChannel();
//            fc.write(ByteBuffer.wrap(messageBean.getContent().getBytecontent()));
//            System.out.println("文件接收完成");
            switch (messageBean.getAction()){
                case "login":
                    System.out.println("messageBean.getAckcode()>>"+messageBean.getAckcode());
                    if(messageBean.getAckcode()==10){
                            Client.getInstance().onSuccess(messageBean.getAckcode(),messageBean.getAction());
                        }else{
                            Client.getInstance().onFaliure(1);
                        }

                    break;
                    
                case "SendCmd":
                	System.out.println("SendCmd:"+messageBean.getContent().getStringcontent());
                	
                	handleCmd(messageBean);
                    	break;
                    	
                    	
                default:
                    Client.getInstance().onResopnse(messageBean);
                    break;
            }
        }

       /* FileInfoBean mes = (FileInfoBean) message;
        System.out.println("总长度：" + mes.getTotallength());
        System.out.println("imagename:" + mes.getFilename());
        System.out.println("图片长度：" + mes.getFilelength());
        File filePath = Environment.getExternalStorageDirectory();
//                        System.out.println("读" + new File("/data/pointercal/").canRead());
//                        System.out.println("写" + new File("/data/pointercal/").canWrite());

        String savePath = filePath + "/" + "32.apk";
        System.out.print("savePath>>" + savePath);
        FileOutputStream out = new FileOutputStream(savePath);
        FileChannel fc = out.getChannel();
        out.write(mes.getFilecontent());*/

    }

    private void handleCmd(final MessageBean messageBean) {
		// TODO Auto-generated method stub
    	final String[] msg = new String[3];
		msg[0] = messageBean.getFrom().getId();
		msg[1] = messageBean.getTo().getId();
		
		messageBean.getFrom().setId(msg[1]);
		messageBean.getTo().setId(msg[0]);
		String str = msg[2] = messageBean.getContent().getStringcontent();
//		System.out.println(msg[0]+msg[1]+msg[2]);
		str = str.replace("\n", "").trim();
		if (str != null) {

			if (str.startsWith("RE:")) {
				str = str.substring(3, str.length());
//				System.out.println(str);
				final String m[] = str.split("&&");
				switch (m[0]) {
				case "SynchronousRequest":
					switch (m[1]) {
					case "paramsmode":
						if(Home.messageJsonObject!=null){
						messageBean.getContent().setStringcontent(Home.messageJsonObject.toString());
						}else{
							messageBean.getContent().setStringcontent("NULL");
						}
						
						Client.getInstance().sendRquest(false, messageBean);
						
						break;
					case "imagemode":
						new AsyncTask<Void, MessageBean, Void>(){

							@Override
							protected Void doInBackground(
									Void... params) {
								// TODO Auto-generated method stub
//								Bitmap screenShotAsBitmap = null;
								try {
//									Activity activity = MyApplication.getTopActivity();
//									screenShotAsBitmap = Falcon.takeScreenshotBitmap(activity);
									
									
//									View viewRoot = activity.getWindow().getDecorView().getRootView();
//									viewRoot.setDrawingCacheEnabled(true);
//									screenShotAsBitmap = Bitmap.createBitmap(viewRoot.getDrawingCache());
//									viewRoot.setDrawingCacheEnabled(false);
									
//									if(m[2].equals("HD")){
//										return screenShotAsBitmap;
//									}else if(m[2].equals("SD")){
//										return MyBitmapFactory.ratio(screenShotAsBitmap, 400, 240);
//									}
										
									
									
									if(m[2].equals("HD")){
										Tools.execShellCmdWait4End("screencap /mnt/sdcard/screenshothd.jpg");
										return null;
									}else if(m[2].equals("SD")){
										Tools.execShellCmdWait4End("screencap /mnt/sdcard/screenshothd.jpg");
										MyBitmapFactory.storeImage(MyBitmapFactory.ratio("/mnt/sdcard/screenshothd.jpg", 400, 240), "/mnt/sdcard/screenshotsd.jpg");
										return null;
									}
////									
//									
								} catch (Exception e) {
									// TODO: handle exception
//									System.out.println("请求screencap "+e);
								}
//								return screenShotAsBitmap;
								return null;
							}
							
							

							@Override
							protected void onPostExecute(Void result) {
								// TODO Auto-generated method stub
//								if(result!=null)
//									messageBean.getContent().setStringcontent(MyBitmapFactory.GetImageStr(result));
//								else
//									messageBean.getContent().setStringcontent("");
//								
//								Client.getInstance().sendRquest(false, messageBean);
								
								switch (m[2]) {
								case "HD":
									messageBean.getContent().setStringcontent(MyBitmapFactory.GetImageStr("/mnt/sdcard/screenshothd.jpg"));
									Client.getInstance().sendRquest(false, messageBean);
									break;
								case "SD":
									messageBean.getContent().setStringcontent(MyBitmapFactory.GetImageStr("/mnt/sdcard/screenshotsd.jpg"));
									Client.getInstance().sendRquest(false, messageBean);
									break;

								default:
									break;
								}
							}
							
							
						}.execute();
						break;

					default:
						break;
					}
					
					
					
					break;
				default:
					break;
				}

			} else if (str.startsWith("TR:")) {
				str = str.substring(3, str.length());
				if(str.startsWith("input")){
					final String n[] = str.split("&&");
					Tools.execShellCmd(n[0]);
					
				}else if (Home.TheActivityIs != null) {
					if (Home.TheActivityIs
							.equals("NonStandard_RuntimeView")) {
						Message.obtain(NonStandard_RuntimeView.handler,StaticVar.INTERRUPT_PROCESS,str).sendToTarget();
					} else if (Home.TheActivityIs
							.equals("Standard_RuntimeView")) {
						Message.obtain(Standard_RuntimeView.handler,StaticVar.INTERRUPT_PROCESS,str).sendToTarget();
					} else if(Home.TheActivityIs
							.equals("Set")){
						Message.obtain(Set.handler,StaticVar.INTERRUPT_PROCESS,str).sendToTarget();
					}else {
						Message.obtain(Home.mainHandler,
								StaticVar.RESPONSE_COMMAND, str)
								.sendToTarget();
					}
				}

			}
		}
	}

    
    
    public static Bitmap captureScreen(Activity activity) {
    	// 获取屏幕大小：
    	DisplayMetrics metrics = new DisplayMetrics();
    	WindowManager WM = (WindowManager) activity
    	.getSystemService(Context.WINDOW_SERVICE);
    	Display display = WM.getDefaultDisplay();
    	display.getMetrics(metrics);
    	int height = metrics.heightPixels; // 屏幕高
    	int width = metrics.widthPixels; // 屏幕的宽
    	// 获取显示方式
    	int pixelformat = display.getPixelFormat();
    	PixelFormat localPixelFormat1 = new PixelFormat();
    	PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);
    	int deepth = localPixelFormat1.bytesPerPixel;// 位深
    	byte[] piex = new byte[height * width * deepth];
    	try {
    	Runtime.getRuntime().exec(
    	new String[] { "/system/bin/su", "-c",
    	"chmod 777 /dev/graphics/fb0" });
    	} catch (Exception e) {
    	e.printStackTrace();
    	}
    	try {
    	// 获取fb0数据输入流
    	InputStream stream = new FileInputStream(new File(
    	"/dev/graphics/fb0"));
    	DataInputStream dStream = new DataInputStream(stream);
    	dStream.readFully(piex);
    	} catch (Exception e) {
    	e.printStackTrace();
    	}
    	// 保存图片
    	int[] colors = new int[height * width];
    	for (int m = 0; m < colors.length; m++) {
    	int r = (piex[m * 4] & 0xFF);
    	int g = (piex[m * 4 + 1] & 0xFF);
    	int b = (piex[m * 4 + 2] & 0xFF);
    	int a = (piex[m * 4 + 3] & 0xFF);
    	colors[m] = (a << 24) + (r << 16) + (g << 8) + b;
    	}
    	// piex生成Bitmap
    	
    	Bitmap bitmap = Bitmap.createBitmap(colors, width, height,
    	Bitmap.Config.ARGB_8888);
    	return bitmap;
    	}

	@Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        System.out.println("messageSent");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);

        Client.getInstance().closeNow(true);
        Home.mainHandler.sendEmptyMessage(StaticVar.RELOGIN);
        System.out.println("sessionClosed");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("sessionCreated");
        
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        System.out.println("sessionOpened");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        System.out.println("sessionIdle");
    }
}
