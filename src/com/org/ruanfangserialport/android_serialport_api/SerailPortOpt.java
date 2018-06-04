package com.org.ruanfangserialport.android_serialport_api;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

	public class SerailPortOpt extends SerialPort{

	public SerailPortOpt(File device, int baudrate, int flags)
				throws SecurityException, IOException {
			super(device, baudrate, flags);
			// TODO Auto-generated constructor stub
		}

//	private static final String TAG = "SerialPort";

//	private FileInputStream mFileInputStream;
//	private FileOutputStream mFileOutputStream;

//	public FileDescriptor openDev(int devNum){
//		super.mFd = super.openDev(devNum);
//		if (super.mFd == null) {
//			Log.e(TAG, "native open returns null");
//			return null;
//		}
//		mFileInputStream = new FileInputStream(super.mFd);
//		mFileOutputStream = new FileOutputStream(super.mFd);
//		return super.mFd;
//	}
//	
//	public FileDescriptor open485Dev(int devNum){
//		super.mFd = super.open485Dev(devNum);
//		if (super.mFd == null) {
//			Log.e(TAG, "native open returns null");
//			return null;
//		}
//		mFileInputStream = new FileInputStream(super.mFd);
//		mFileOutputStream = new FileOutputStream(super.mFd);
//		return super.mFd;
//	}
	
//	public InputStream getInputStream() {
//		return mFileInputStream;
//	}
//
//	public OutputStream getOutputStream() {
//		return mFileOutputStream;
//	}

    
    
}
