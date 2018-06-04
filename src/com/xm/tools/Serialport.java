package com.xm.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Message;
import android.util.Log;

import com.org.ruanfangserialport.android_serialport_api.SerailPortOpt;
import com.xm.Bean.ReplyBean;
import com.xm.activity.Admin;
import com.xm.activity.Home;
import com.xm.activity.NonStandard_RuntimeView;
import com.xm.activity.Set;
import com.xm.activity.Standard_RuntimeView;
import com.xm.var.StaticVar;

public class Serialport {
	private InputStream mInputStream;
	private OutputStream mOutputStream;
	private SerailPortOpt serialPort;
	private ReadThread mReadThread;
//	private String tempMessage;
	public StringBuffer buffer = new StringBuffer();
	private BufferHandlerThread mBufferHandlerThread;
	ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	/**
	 * 串口构造函数
	 * @param port 端口号
	 * @param baudrate 波特率
	 * @param databit 数据位
	 * @param stopbit 停止位
	 * @param paritybit 校验位
	 * 
	 */
	public Serialport(int port,int baudrate, int databit, int stopbit, int paritybit
			) {
		if(serialPort==null)
			try {
				String port_file= "/dev/ttyS" + port;
				serialPort = new SerailPortOpt(new File(port_file), baudrate, 0);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
//		serialPort.mDevNum = port;
//		serialPort.mSpeed = baudrate;
//		serialPort.mDataBits = databit;
//		serialPort.mStopBits = stopbit;
//		serialPort.mParity = paritybit;
	}
	
	public InputStream getInputStream() {
		return mInputStream;
	}


	public OutputStream getOutputStream() {
		return mOutputStream;
	}


	public void openSerialPort() {

//		if (serialPort.mFd == null) {
//			serialPort.openDev(serialPort.mDevNum);
//
//			//Log.i("uart port operate", "Mainactivity.java==>uart open");
//			serialPort.setSpeed(serialPort.mFd, serialPort.mSpeed);
//			//Log.i("uart port operate", "Mainactivity.java==>uart set speed..."
//			//		+ serialPort.mSpeed);
//			serialPort.setParity(serialPort.mFd, serialPort.mDataBits,
//					serialPort.mStopBits, serialPort.mParity);
			//Log.i("uart port operate",
			//		"Mainactivity.java==>uart other params..."
			//				+ serialPort.mDataBits + "..."
			//				+ serialPort.mStopBits + "..." + serialPort.mParity);

			mInputStream = serialPort.getInputStream();
			mOutputStream = serialPort.getOutputStream();
			
			mBufferHandlerThread = new BufferHandlerThread();
			mBufferHandlerThread.start();
			
			mReadThread = new ReadThread();
			mReadThread.start();
//		}
	}

	public void openSerialPort2() {
		mInputStream = serialPort.getInputStream();
		mOutputStream = serialPort.getOutputStream();
	}

	public synchronized ReplyBean  sendToTemperUnits(byte[] buff) {
			BufferedInputStream bufferedInputStream=new BufferedInputStream(mInputStream);
			byte[] recvbuff = new byte[50];
			ReplyBean reply = new ReplyBean();
			for(int i = 0 ;i < 3; i++){
				try {
					if(bufferedInputStream.available()>0){
						bufferedInputStream.read(recvbuff);
						Arrays.fill(recvbuff, (byte)0x00);
						Thread.sleep(50);
					}
				} catch (Exception e) {
				}
				if(!SendBytes(buff)){
					reply.error = 1;
					reply.error_str = "发送失败！";
					return reply;
				}else {
					try {
						for(int j = 0 ;j < 10; j++){
							Thread.sleep(50);
							if(bufferedInputStream.available()>0){
								Thread.sleep(100);
								int length = bufferedInputStream.read(recvbuff);
								if(recvbuff[2] == buff[0] && checkDataFormat(recvbuff, length)){
									reply.buff = new byte[length - 6];
									System.arraycopy(recvbuff, 2, reply.buff, 0, length - 6);
									int x = 0;
									x++;
									return reply;
								}else{
//									reply.error = 1;
//									reply.error_str = "错误！";
//									return reply;
									continue;
								}
							}
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			reply.error = 1;
			reply.error_str = "未回复！";
			return reply;
	}
	
	Boolean checkDataFormat(byte[] buff,int length){
		try {
			if(buff[0]!=-1||buff[1]!=-2||buff[length-2]!=-2||buff[length-1]!=-1) return false;
			int crc = verify_crc(buff, 2,length-6);
			int crcRecv = byteToInt(buff[length - 4]) * 256 + byteToInt(buff[length - 3]);
			if(crc != crcRecv) return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	int byteToInt(byte b) {
        return (int)b & 0xff;
    }

	Boolean SendBytes(byte[] buff) {
		int length = buff.length + 6;
		byte[] sendbuff = new byte[length];
		sendbuff[0] = (byte) 0xff;
		sendbuff[1] = (byte) 0xfe;
//		sendbuff[2] = (byte) length;
        System.arraycopy(buff, 0, sendbuff, 2, buff.length);
        int crc = verify_crc(buff, 0, buff.length);
        sendbuff[length - 4] = (byte) (crc >> 8);
        sendbuff[length - 3] = (byte) (crc & 0xff);
        sendbuff[length - 2] = (byte) 0xfe;
        sendbuff[length - 1] = (byte) 0xff;
        try {
			mOutputStream.write(sendbuff);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public int verify_crc(byte[] buff, int offset, int length)
    {
        int crc16 = 0xffff;
        for (int i = offset; i < length + offset; i++)
        {
            if (buff[i] < 0) {
                crc16 ^= (int) buff[i] + 256;
            } else {
                crc16 ^= buff[i];
            }

            for (int j = 0; j < 8; j++)
            {
                if ((crc16 & 0x01) == 1)
                {
                    crc16 = (crc16 >> 1) ^ 0xA001;
                }
                else
                {
                    crc16 = crc16 >> 1;
                }
            }
        }
        return crc16;
    }
	
	class BufferHandlerThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				handleStringBuffer();
				
			}
		}

		private void handleStringBuffer() {
			// TODO Auto-generated method stub
			try {
				
				int headAppearPos = buffer.indexOf(":");
				int tailAppearPos = buffer.indexOf("!");
//				System.out.println("headAppearPos"+headAppearPos+"tailAppearPos"+tailAppearPos+"  "+buffer.toString());
				if(tailAppearPos>headAppearPos){
					if(headAppearPos>=0){
						String str =buffer.substring(headAppearPos, tailAppearPos+1);
						buffer.delete(0, tailAppearPos+1);
						onDataReceived(str);
					}
				}else{
					buffer.delete(0, tailAppearPos+1);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void closeSerialPort() {

		if (mReadThread != null) {
			mReadThread.interrupt();
			mReadThread = null;
		}

		if (serialPort.mFd != null) {
			Log.i("uart port operate", "Mainactivity.java==>uart stop");
//			serialPort.closeDev(serialPort.mFd);
			serialPort.close();
			Log.i("uart port operate", "Mainactivity.java==>uart stoped");
		}
	}

	private class ReadThread extends Thread {
		byte[] buf = new byte[512];

		@Override
		public void run() {
			super.run();
//			Log.i(TAG, "ReadThread==>buffer:" + buf.length);
			while (!Thread.currentThread().isInterrupted()) {
				int size;
				if (mInputStream == null)
					return;
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(mInputStream));
				try {
					String str =null;
					while ((str=reader.readLine())!=null) {
//						System.out.println("string>>>"+str);
						add2StringBuffer(str);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				size = serialPort.readBytes(buf);
//				if (size > 0) {
//					onDataReceived(buf, size);
////					Log.i(TAG, "ReadThread==>" + size);
//				}

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					mReadThread = null;
				}
			}
		}
		
		private void add2StringBuffer(String str) {
			// TODO Auto-generated method stub
			
//			System.out.println("字符长度:"+str.length()+"  报尾ASCII:"+(byte)str.charAt(str.length()-1));
//			
//			System.out.println("str报文头："+str.startsWith(":")+"   str报文末尾："+str.endsWith("0"));
//			
//			if(str.startsWith(":")&&str.endsWith("\n")){
//				onDataReceived(buffer .toString());
//				buffer.setLength(0);
//			}else{
//				buffer.append(str);
//				if(buffer.toString().startsWith(":")&&buffer.toString().endsWith("0")){
//					onDataReceived(buffer.toString());
//					buffer.setLength(0);
//				}
//			}
			
			buffer.append(str);
			
			
		}
	}

	protected void onDataReceived(final String resString) {
//		String resString="";
//		try {
//			resString = new String(buf, 0, size, "gbk");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try {
//			System.out.println("resString>>>"+resString);
//			String str[] = resString.trim().split("\r\n");
			
//			for(int i = 0;i<str.length;i++){
				
					String tempstr = resString.replace(":", "").replace("!", "").trim();
//					tempstr = tempstr.trim().substring(0,tempstr.length());
					String m[] = tempstr.split("=");
					
					if(m[0].toString().equals("zw_door_2")){
						
						Message msg = Home.mainHandler.obtainMessage();
						msg.what=StaticVar.RESPONSE_COMMAND;
						msg.obj = "zw_door_2";
						Home.mainHandler.sendMessage(msg);
					}
					
					
					if(Home.TheActivityIs.equals("Standard_RuntimeView")){
						Message msg = Standard_RuntimeView.handler.obtainMessage();
						msg.obj = m;
						Standard_RuntimeView.handler.sendMessage(msg);
					}else if(Home.TheActivityIs.equals("NonStandard_RuntimeView")){
						Message msg = NonStandard_RuntimeView.handler.obtainMessage();
						msg.what=1;
						msg.obj = m;
						NonStandard_RuntimeView.handler.sendMessage(msg);
					}else if(Home.TheActivityIs.equals("Set")){
						Message msg = Set.handler.obtainMessage();
						msg.what=3;
						msg.obj = m[0];
						Set.handler.sendMessage(msg);
					}else if(Home.TheActivityIs.equals("Admin")){
						Message msg = Admin.handler.obtainMessage();
						msg.what=1;
						msg.obj = m;
						Admin.handler.sendMessage(msg);
					}
				
				
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		

	/*
	 * public static String bytesToHexString(byte[] src,int size){ StringBuilder
	 * stringBuilder = new StringBuilder(""); if (src == null || size <= 0) {
	 * return null; } for (int i = 0; i < size; i++) { int v = src[i] & 0xFF;
	 * String hv = Integer.toHexString(v); if (hv.length() < 2) {
	 * stringBuilder.append(0); } stringBuilder.append(hv); } return
	 * stringBuilder.toString(); }
	 */

	public static String bytesToHexString(byte[] src, int size) {
		String ret = "";
		if (src == null || size <= 0) {
			return null;
		}
		for (int i = 0; i < size; i++) {
			String hex = Integer.toHexString(src[i] & 0xFF);
//			Log.i(TAG, hex);
			if (hex.length() < 2) {
				hex = "0" + hex;
			}
			hex += " ";
			ret += hex;
		}
		return ret.toUpperCase();
	}

	
//	private class TimerSendThread extends Thread {
//
//		private long m_lTimer = 100; // default 100ms
//		private boolean m_bRunFlag = true;
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
////			Log.i(TAG, "TimerSendThread==>run");
//			super.run();
//			while (m_bRunFlag) {
////				Log.i(TAG, "TimerSendThread==>" + m_lTimer);
//				SendMsg();
//				if (m_lTimer <= 0) { // must over 0ms
//					m_lTimer = 100;
//				}
//				try {
//					Thread.sleep(m_lTimer);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//		public void setSleepTimer(long timer) {
//			m_lTimer = timer;
//		}
//
//		public void stopThread() {
//			m_bRunFlag = false;
//		}
//	}

	public void  SendMsg(String str) {
		WriteRunnable msgThread = new WriteRunnable((String) str);
		executorService.execute(msgThread);
	}
	
	class WriteRunnable implements Runnable{
		String res = null;

		public WriteRunnable(String str) {
			// TODO Auto-generated constructor stub
			this.res = str;
		}

		@Override
		public  void run() {
			// TODO Auto-generated method stub
			write(res);
			
		}
	}


	public static boolean Rule(String str) {
		boolean result = false;

		String reg = "[a-fA-F0-9 ]*";
		if (str.matches(reg)) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	public synchronized void write(String res) {
		// TODO Auto-generated method stub
		try {
			mOutputStream.write(res.getBytes());
//			tempMessage = res.replace(":", ";");
//			System.out.println("WriteThread>>"+res);
//			Thread.sleep(5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ������ASCII�ַ��ϳ�һ���ֽڣ� �磺"EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * ��ָ���ַ���src����ÿ�����ַ��ָ�ת��Ϊ16������ʽ �磺"2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
	 * 0xD9}
	 * 
	 * @param src
	 *            String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[src.length() / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < tmp.length / 2; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	
	
}
