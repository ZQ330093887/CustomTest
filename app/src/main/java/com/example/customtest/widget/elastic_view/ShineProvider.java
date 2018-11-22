package com.example.customtest.widget.elastic_view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.View;

import com.example.customtest.R;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Created by ZQiong on 2018/11/7.
 */
public class ShineProvider extends CentrePointProvider {
    private Paint _paint;

    private int _centreColor;
    private float shineRadius;

    public ShineProvider(View parentView) {
        super(parentView);
        initPaint();

        shineRadius = parentView.getHeight() / 2.5F;
        _centreColor = ContextCompat.getColor(parentView.getContext(), R.color.startColor);
    }


    public final void onDispatchDraw(@Nullable Canvas canvas) {
        _paint.setShader((new RadialGradient(getCx(), getCy(), shineRadius, _centreColor, Color.TRANSPARENT, Shader.TileMode.CLAMP)));
        if (canvas != null) {
            canvas.drawCircle(getCx(), getCy(), shineRadius, _paint);
        }
    }

    private void initPaint() {
        _paint = new Paint();
        _paint.setColor(Color.BLACK);
        _paint.setStyle(Paint.Style.FILL);

    }
}
