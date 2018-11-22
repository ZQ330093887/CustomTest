package com.example.customtest.widget.elastic_view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by ZQiong on 2018/11/7.
 */
public class DebugPath extends CentrePointProvider {
    private final Path _horizontalPath;
    private final Path _verticalPath;

    private Paint _pathPaint;
    private Paint _circlePaint;

    public DebugPath(View parentView) {
        super(parentView);
        _horizontalPath = new Path();
        _verticalPath = new Path();
        initPaint();
    }


    public final void onDispatchDraw(@Nullable Canvas canvas) {
        _verticalPath.reset();
        _horizontalPath.reset();
        _horizontalPath.moveTo(getCx(), 0.0F);
        _horizontalPath.lineTo(getCx(), (float) getParentView().getHeight());
        _verticalPath.moveTo(0.0F, getCy());
        _verticalPath.lineTo((float) getParentView().getWidth(), getCy());
        if (canvas != null) {
            canvas.drawPath(_horizontalPath, _pathPaint);
            canvas.drawPath(_verticalPath, _pathPaint);
            canvas.drawCircle(getCx(), getCy(), 15.0F, _circlePaint);
        }
    }


    private void initPaint() {
        _pathPaint = new Paint();
        _pathPaint.setColor(Color.WHITE);
        _pathPaint.setStyle(Paint.Style.FILL);
        _pathPaint.setStrokeWidth(2f);
        _pathPaint.setPathEffect(new DashPathEffect(new float[]{20f, 10f}, 0f));


        _circlePaint = new Paint();
        _circlePaint.setColor(Color.WHITE);
        _circlePaint.setStyle(Paint.Style.FILL);
    }
}
