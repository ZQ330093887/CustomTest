package com.anrongtec.ocr;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anrongtec.ocr.utils.NavigationBarHeightUtils;
import com.anrongtec.ocr.utils.StreamEmpowerFileUtils;
import com.anrongtec.ocr.utils.UserIdUtils;
import com.anrongtec.ocr.view.SIDViewfinderView;
import com.etop.SIDCard.SIDCardAPI;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ZQiong on 2018/8/24.
 */
public class PersonActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private static final String PATH = Environment.getExternalStorageDirectory() + "/alpha/SIDCard/";
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private RelativeLayout mainRl;
    private SurfaceHolder surfaceHolder;
    private SIDCardAPI sidApi = null;
    private Bitmap bitmap;
    private int preWidth = 0;
    private int preHeight = 0;
    private int screenWidth;
    private int screenHeight;
    private SIDViewfinderView myView = null;
    private boolean bInitKernal = false;
    private ImageButton ibBack;
    private ImageButton ibFlashOn;
    private ImageButton ibFlashOff;
    private ImageButton ibChange;
    private TextView tvSign;
    private AlertDialog alertDialog = null;
    private Vibrator mVibrator;
    private MediaPlayer mediaplayer;//提示音
    private boolean isBackside = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        try {
            StreamEmpowerFileUtils.copyDataBase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(PATH);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        Configuration cf = this.getResources().getConfiguration(); //获取设置的配置信息
        int noriention = cf.orientation;
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(this).create();
        }
        if (noriention == Configuration.ORIENTATION_LANDSCAPE) {
            initCardKernal();//初始化识别核心
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // // 屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_etmain);
        findView();

    }

    private void findView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.etop_sv);
        mainRl = (RelativeLayout) findViewById(R.id.etop_rl_main);
        ibBack = (ImageButton) findViewById(R.id.etop_ib_back);
        ibFlashOn = (ImageButton) findViewById(R.id.etop_ib_flash_on);
        ibFlashOff = (ImageButton) findViewById(R.id.etop_ib_flash_off);
        ibChange = (ImageButton) findViewById(R.id.etop_ib_change);
        tvSign = (TextView) findViewById(R.id.etop_tv_sign);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels; // 屏幕宽度（像素）
        screenHeight = metric.heightPixels; // 屏幕高度（像素）

        int back_w = (int) (screenWidth * 0.066796875);
        int back_h = (int) (back_w * 1);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(back_w, back_h);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        layoutParams.leftMargin = (int) ((back_h / 2));
        layoutParams.bottomMargin = (int) (screenHeight * 0.15);
        ibBack.setLayoutParams(layoutParams);

        int flash_w = (int) (screenWidth * 0.066796875);
        int flash_h = (int) (flash_w * 69 / 106);
        layoutParams = new RelativeLayout.LayoutParams(flash_w, flash_h);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.leftMargin = (int) ((back_h / 2));
        layoutParams.topMargin = (int) (screenHeight * 0.15);
        ibFlashOn.setLayoutParams(layoutParams);
        ibFlashOff.setLayoutParams(layoutParams);
        ibFlashOff.setVisibility(View.INVISIBLE);

        int change_w = (int) (screenWidth * 0.066796875);
        int change_h = (int) (change_w * 1);
        layoutParams = new RelativeLayout.LayoutParams(change_w, change_h);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        layoutParams.leftMargin = (int) ((back_h / 2));
        ibChange.setLayoutParams(layoutParams);

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        tvSign.setLayoutParams(layoutParams);
        if (isBackside) {
            tvSign.setText("二代证背面");
            tvSign.setTextColor(Color.GREEN);
            tvSign.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenHeight / 20);
        } else {
            tvSign.setText("二代证正面");
            tvSign.setTextColor(Color.GREEN);
            tvSign.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenHeight / 20);
        }
        if (myView == null) {
            myView = new SIDViewfinderView(PersonActivity.this, screenWidth, screenHeight, isBackside);
            mainRl.addView(myView);
        }

        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(PersonActivity.this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceView.setFocusable(true);

        mOnClick();
    }

    private void mOnClick() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibFlashOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    String mess = "当前设备不支持闪光灯";
                    Toast.makeText(PersonActivity.this, mess, Toast.LENGTH_SHORT).show();
                } else {
                    if (mCamera != null) {
                        Camera.Parameters parameters = mCamera.getParameters();
                        String flashMode = parameters.getFlashMode();

                        if (flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            try {
                                mCamera.setParameters(parameters);
                            } catch (Exception e) {
                                String mess = "当前设备不支持闪光灯";
                                Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
                            }
                            ibFlashOff.setVisibility(View.INVISIBLE);
                            ibFlashOn.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        ibFlashOn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    String mess = "当前设备不支持闪光灯";
                    Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
                } else {
                    if (mCamera != null) {

                        Camera.Parameters parameters = mCamera.getParameters();
                        String flashMode = parameters.getFlashMode();
                        if (!(flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH))) {
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);// 闪光灯常亮
                            try {
                                mCamera.setParameters(parameters);
                            } catch (Exception e) {
                                String mess = "当前设备不支持闪光灯";
                                Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
                            }
                            ibFlashOn.setVisibility(View.INVISIBLE);
                            ibFlashOff.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        ibChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sidApi != null) {
                    if (isBackside) {
                        sidApi.SIDCardSetRecogType(1);
                        tvSign.setText("二代证正面");
                        tvSign.setTextColor(Color.GREEN);
                        tvSign.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenHeight / 20);
                        isBackside = false;

                    } else {
                        sidApi.SIDCardSetRecogType(2);
                        tvSign.setText("二代证背面");
                        tvSign.setTextColor(Color.GREEN);
                        tvSign.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenHeight / 20);
                        isBackside = true;
                    }
                    myView.setImageResourse(isBackside);
                }
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (sidApi == null) {
            initCardKernal();//如果第一次没有初始化成功，则重新初始化一次
        }
        if (mCamera == null) {
            try {
                mCamera = Camera.open();
                Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
                mCamera.setParameters(mParameters);
            } catch (Exception e) {
                e.printStackTrace();
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                }
                String mess = getResources().getString(R.string.toast_camera);
                Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        initCamera();
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(this).create();
        }
    }

    @Override
    public void surfaceChanged(final SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();//相机用完，资源释放
        if (bInitKernal) {
            sidApi.SIDCardKernalUnInit();
            bInitKernal = false;
            sidApi = null;
        }
    }

    /***********************初始化识别核心************************/
    private void initCardKernal() {
        if (!bInitKernal) {
            if (sidApi == null) {
                sidApi = new SIDCardAPI();
            }
            String cacheDir = (this.getExternalCacheDir()).getPath();
            String useridPath = cacheDir + "/" + UserIdUtils.UserID + ".lic";
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            int nRet = sidApi.SIDCardKernalInit("", useridPath, UserIdUtils.UserID, 0x02, 0x02, telephonyManager, this);
            if (nRet != 0) {
                Toast.makeText(PersonActivity.this, "激活失败", Toast.LENGTH_SHORT).show();
                System.out.print("nRet=" + nRet);
                bInitKernal = false;
            } else {
                System.out.print("nRet=" + nRet);
                bInitKernal = true;
                if (!isBackside) {
                    sidApi.SIDCardSetRecogType(1);
                } else {
                    sidApi.SIDCardSetRecogType(2);
                }
            }
        }
    }

    @TargetApi(14)
    private void initCamera() {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> list = parameters.getSupportedPreviewSizes();
        Camera.Size size;

        int navigationBarHeight = NavigationBarHeightUtils.getNavigationBarHeight(this);
        Camera.Size tmpsize = getOptimalPreviewSize(list, screenWidth + navigationBarHeight, screenHeight);

        int length = list.size();
        int previewWidth;
        int previewheight;
        int second_previewWidth = 0;
        int second_previewheight = 0;
        previewWidth = tmpsize.width;
        previewheight = tmpsize.height;

        if (length == 1) {
            preWidth = previewWidth;
            preHeight = previewheight;
        } else {
            second_previewWidth = previewWidth;
            second_previewheight = previewheight;
            for (int i = 0; i < length; i++) {
                size = list.get(i);
                if (size.height > 700 && size.height < previewheight) {
                    if (size.width * previewheight == size.height * previewWidth && size.height < second_previewheight) {
                        second_previewWidth = size.width;
                        second_previewheight = size.height;
                    }
                }
            }

            preWidth = second_previewWidth;
            preHeight = second_previewheight;
        }

        /**
         * 如果预览的宽度/屏幕的宽度  与  预览的高度/屏幕的高度 不成比例
         * 让横屏下 的宽度还顶到头 高度按比例适应
         */
        double scaleWidth = preWidth / (double) screenWidth;
        double scaleHeight = preHeight / (double) screenHeight;
        if (scaleWidth != scaleHeight) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mSurfaceView.getLayoutParams();
            lp.width = screenWidth;
            lp.height = (int) (preHeight / scaleWidth);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mSurfaceView.setLayoutParams(lp);
        }

        parameters.setPictureFormat(PixelFormat.JPEG);

        parameters.setPreviewSize(preWidth, preHeight);
        if (parameters.getSupportedFocusModes().contains(
                parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        if (parameters.isZoomSupported()) {
            parameters.setZoom(2);
        }
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.setPreviewCallback(PersonActivity.this);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }

    public String pictureName() {
        String str = "";
        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month + 1;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;
        if (month < 10) {
            str = String.valueOf(year) + "0" + String.valueOf(month);
        } else {
            str = String.valueOf(year) + String.valueOf(month);
        }
        if (date < 10) {
            str = str + "0" + String.valueOf(date + "_");
        } else {
            str = str + String.valueOf(date + "_");
        }
        if (hour < 10) {
            str = str + "0" + String.valueOf(hour);
        } else {
            str = str + String.valueOf(hour);
        }
        if (minute < 10) {
            str = str + "0" + String.valueOf(minute);
        } else {
            str = str + String.valueOf(minute);
        }
        if (second < 10) {
            str = str + "0" + String.valueOf(second);
        } else {
            str = str + String.valueOf(second);
        }
        return str;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        mediaplayer = new MediaPlayer();

        try {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.baidu_beep);
            mediaplayer.setDataSource(this, uri);
            mediaplayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!alertDialog.isShowing()) {
            int buffl = 256;
            char recogval[] = new char[buffl];
            int r = sidApi.SIDCardRecognizeNV21(data, preWidth, preHeight,
                    recogval, buffl);
            if (r == 0) {
//                mVibrator = (Vibrator) getApplication().getSystemService(
//                        Service.VIBRATOR_SERVICE);
//                mVibrator.vibrate(300);
                mediaplayer.start();

                // 删除正常识别保存图片功能
                int[] datas = convertyuv420Nv21toargb8888(data, preWidth, preHeight);
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inInputShareable = true;
                opts.inPurgeable = true;
                bitmap = Bitmap.createBitmap(datas, preWidth, preHeight, Bitmap.Config.RGB_565);
                String strCaptureFilePath = PATH + "_SIDCard_" + pictureName() + ".jpg";
                String strCaptureFileHeadPath = PATH + "_SIDCard_Head_" + pictureName() + ".jpg";
                sidApi.SIDCardSaveCardImage(strCaptureFilePath);
                int nRet = sidApi.SIDCardSaveHeadImage(strCaptureFileHeadPath);

                int ncheckcopy = sidApi.SIDCardCheckIsCopy();
                //0表示不是，1表示是，-1错误

                int nRecog = sidApi.SIDCardGetRecogType();
                //身份证号
                String sfzh = sidApi.SIDCardGetResult(5);
                //
                String recogName = sidApi.SIDCardGetResult(0);
                //
                String recogSex = sidApi.SIDCardGetResult(1);
                //
                String recogNation = sidApi.SIDCardGetResult(2);
                //
                String recogBorn = sidApi.SIDCardGetResult(3);
                //
                String recogAdress = sidApi.SIDCardGetResult(4);
                Intent intent = new Intent();
                if (!TextUtils.isEmpty(sfzh)) {
                    intent.putExtra("recogResult", sfzh);
                    intent.putExtra("recogName", recogName);
                    intent.putExtra("recogSex", recogSex);
                    intent.putExtra("recogBorn", recogBorn);
                    intent.putExtra("recogAdress", recogAdress);
                    intent.putExtra("recogNation", recogNation);
                    intent.putExtra("recogHead", strCaptureFileHeadPath);
                } else {
                    intent.putExtra("recogResult", "");
                }
                setResult(RESULT_OK, intent);
                finish();
//
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (alertDialog != null) {
            alertDialog.cancel();
            alertDialog.dismiss();
        }
        /*********核心用完释放，否则下次初始化核心会失败*********/
        if (bInitKernal) {
            sidApi.SIDCardKernalUnInit();
            bInitKernal = false;
            sidApi = null;
        }
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        releaseCamera();//释放相机资源
    }

    @Override
    protected void onDestroy() {
        if (mediaplayer != null) {
            mediaplayer.stop();
            mediaplayer.release();
            mediaplayer = null;
        }
        super.onDestroy();
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @TargetApi(14)
    private void NewApis(Camera.Parameters parameters) {
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
    }

    public int[] convertyuv420Nv21toargb8888(byte[] data, int width, int height) {
        int size = width * height;
        int offset = size;
        int[] pixels = new int[size];
        int u, v, y1, y2, y3, y4;

        for (int i = 0, k = 0; i < size; i += 2, k += 2) {
            y1 = data[i] & 0xff;
            y2 = data[i + 1] & 0xff;
            y3 = data[width + i] & 0xff;
            y4 = data[width + i + 1] & 0xff;

            u = data[offset + k] & 0xff;
            v = data[offset + k + 1] & 0xff;
            u = u - 128;
            v = v - 128;

            pixels[i] = convertYUVtoARGB(y1, u, v);
            pixels[i + 1] = convertYUVtoARGB(y2, u, v);
            pixels[width + i] = convertYUVtoARGB(y3, u, v);
            pixels[width + i + 1] = convertYUVtoARGB(y4, u, v);

            if (i != 0 && (i + 2) % width == 0) {
                i += width;
            }
        }

        return pixels;
    }

    private int convertYUVtoARGB(int y, int u, int v) {
        int r, g, b;

        r = y + (int) 1.402f * u;
        g = y - (int) (0.344f * v + 0.714f * u);
        b = y + (int) 1.772f * v;
        r = r > 255 ? 255 : r < 0 ? 0 : r;
        g = g > 255 ? 255 : g < 0 ? 0 : g;
        b = b > 255 ? 255 : b < 0 ? 0 : b;
        return 0xff000000 | (r << 16) | (g << 8) | b;
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) {
            return null;
        }

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (size.height < 700) {
                continue;
            }
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                continue;
            }
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (size.height < 700) {
                    continue;
                }
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                } else if (Math.abs(size.height - targetHeight) == minDiff) {
                    optimalSize = size;
                }
            }
        }
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                } else if (Math.abs(size.height - targetHeight) == minDiff) {
                    optimalSize = size;
                }
            }
        }
        return optimalSize;
    }
}


