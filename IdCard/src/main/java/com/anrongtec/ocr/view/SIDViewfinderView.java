package com.anrongtec.ocr.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.anrongtec.ocr.R;


/**
 * Created by ZQiong on 2018/8/24.
 */
public class SIDViewfinderView extends View {

    private static final long ANIMATION_DELAY = 50;
    private Paint paint;
    private Paint paintLine;
    private int maskColor;
    private int frameColor;
    private int laserColor;
    private Paint mTextPaint;
    private String mText;
    private Rect frame;
    private boolean isBackside;
    private int idResource;
    int w, h;

    public SIDViewfinderView(Context context, int w, int h, boolean isBackside) {
        super(context);
        this.w = w;
        this.h = h;
        this.isBackside = isBackside;
        paint = new Paint();
        paintLine = new Paint();
        Resources resources = getResources();
        //透明灰
        maskColor = resources.getColor(R.color.viewfinder_mask);
        // 绿色
        frameColor = resources.getColor(R.color.viewfinder_frame);
        // 白色
        laserColor = resources.getColor(R.color.viewfinder_laser);
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int t, b, l, r;

        t = height / 10;
        b = height * 9 / 10;
        int ntmpW = (b - t) * 88 / 58;
        l = (width - ntmpW) / 2;
        r = width - l;

        frame = new Rect(l, t, r, b);
        // 画出扫描框外面的阴影部分，共四;个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
        // 扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
        paint.setColor(maskColor);

        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        paintLine.setColor(frameColor);
        paintLine.setStrokeWidth(16);
        paintLine.setAntiAlias(true);
        int num = (b - t) / 5;
        canvas.drawLine(l - 8, t, l + num, t, paintLine);
        canvas.drawLine(l, t, l, t + num, paintLine);

        canvas.drawLine(r + 8, t, r - num, t, paintLine);
        canvas.drawLine(r, t, r, t + num, paintLine);

        canvas.drawLine(l - 8, b, l + num, b, paintLine);
        canvas.drawLine(l, b, l, b - num, paintLine);

        canvas.drawLine(r + 8, b, r - num, b, paintLine);
        canvas.drawLine(r, b, r, b - num, paintLine);

        if (isBackside) {
            idResource = R.drawable.camera_idcard_back;
        } else {
            idResource = R.drawable.camera_idcard_front;
        }

        /****************绘制背景图***************/
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), idResource);
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(l, frame.top, r, frame.bottom);
        canvas.drawBitmap(bitmap, rect, rectF, paint);


        paintLine.setColor(laserColor);
        paintLine.setAlpha(100);
        paintLine.setStrokeWidth(3);
        paintLine.setAntiAlias(true);

        canvas.drawLine(l, t + num, l, b - num, paintLine);
        canvas.drawLine(r, t + num, r, b - num, paintLine);
        canvas.drawLine(l + num, t, r - num, t, paintLine);
        canvas.drawLine(l + num, b, r - num, b, paintLine);

        mText = "请将证件放置于框内";
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(frameColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mText, w / 2, h * 3 / 4, mTextPaint);

        if (frame == null) {
            return;
        }
        /**
         * 当我们获得结果的时候，我们更新整个屏幕的内UI显示
         */
        postInvalidateDelayed(ANIMATION_DELAY);
    }

    public void setImageResourse(boolean isBackside) {
        this.isBackside = isBackside;
        if (isBackside) {
            idResource = R.drawable.camera_idcard_back;
        } else {
            idResource = R.drawable.camera_idcard_front;
        }

        postInvalidate();
    }
}
