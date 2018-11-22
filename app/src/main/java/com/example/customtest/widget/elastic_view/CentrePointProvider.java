package com.example.customtest.widget.elastic_view;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ZQiong on 2018/11/7.
 */
public class CentrePointProvider {
    private float screenOffDistance = -300.0F;
    private float cx = screenOffDistance;
    private float cy = screenOffDistance;
    private View parentView;

    protected final float getCx() {
        return this.cx;
    }

    protected final void setCx(float var1) {
        this.cx = var1;
    }

    protected final float getCy() {
        return this.cy;
    }

    protected final void setCy(float var1) {
        this.cy = var1;
    }

    protected final View getParentView() {
        return this.parentView;
    }

    private void attach(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            this.cx = event.getX();
            this.cy = event.getY();
        } else {
            this.cx = this.screenOffDistance;
            this.cy = this.screenOffDistance;
        }

        this.parentView.invalidate();
    }


    @SuppressLint("ClickableViewAccessibility")
    public CentrePointProvider(View parentView) {
        this.parentView = parentView;

        this.parentView.setOnTouchListener((v, event) -> {
            attach(event);
            return v.onTouchEvent(event);
        });
    }
}
