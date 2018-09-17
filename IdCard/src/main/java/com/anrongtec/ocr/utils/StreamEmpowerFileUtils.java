package com.anrongtec.ocr.utils;

import android.app.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ZQiong on 2018/8/24.
 */
public class StreamEmpowerFileUtils {
    /************ 将授权文件从assets目录下获取，并且保存到data/data/包名 目录下 *********/
    public static void copyDataBase(Activity activity) throws IOException {
        //获取手机 data/data/包名目录下的授权文件路径
        String cacheDir = (activity.getExternalCacheDir()).getPath();
        String dst = cacheDir + "/" + UserIdUtils.UserID + ".lic";
        //如果文件已存在，则删除文件
        File file = new File(dst);
        if (file.exists())
        activity.deleteFile(file.getName());
        try {
            //在assets资产目录下获取授权文件
            InputStream myInput = activity.getAssets().open(UserIdUtils.UserID + ".lic");
            //将授权文件写到 data/data/包名 目录下
            OutputStream myOutput = new FileOutputStream(dst);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
        }
    }
}
