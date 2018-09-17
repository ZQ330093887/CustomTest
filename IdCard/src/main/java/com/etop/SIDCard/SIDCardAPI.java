package com.etop.SIDCard;

import android.content.Context;
import android.telephony.TelephonyManager;
/**
 * Created by ZQiong on 2018/8/24.
 */
public class SIDCardAPI {
	static {
		System.loadLibrary("AndroidSIDCard");
	}
	
	
	public native int SIDCardKernalInit(String szSysPath, String FilePath, String CommpanyName, int nProductType, int nAultType, TelephonyManager telephonyManager, Context context);
	public native void SIDCardKernalUnInit();
	public native int SIDCardRecognizeNV21(byte[] ImageStreamNV21, int Width, int Height, char[] Buffer, int BufferLen);
	public native String SIDCardGetResult(int nIndex);
	public native int SIDCardSaveCardImage(String path);
	public native int SIDCardSaveHeadImage(String path);
	public native int SIDCardRecogOtherImgaeFileW(String path, char[] Buffer, int BufferLen);
	public native int SIDCardSetRecogType(int nType);
	public native int SIDCardGetRecogType();
	public native int SIDCardGetImgDirection();
	public native int SIDCardCheckIsCopy();
}
