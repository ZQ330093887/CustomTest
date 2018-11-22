package com.example.customtest.widget.elastic_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewPropertyAnimator;

import com.example.customtest.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

/**
 * Created by ZQiong on 2018/11/7.
 */
public class ElasticView extends CardView {

    private final long ANIMATION_DURATION = 200L;
    private final long ANIMATION_DURATION_SHORT = 100L;

    private boolean _isAnimating = false;
    private boolean _isActionUpPerformed = false;
    private DebugPath debugPath = new DebugPath(this);
    private ShineProvider shineProvider = new ShineProvider(this);
    private boolean isShineEnabled = false;
    private float flexibility = 5.0F;
    private boolean isDebugPathEnabled = false;

    public final float getFlexibility() {
        return flexibility;
    }

    public final void setFlexibility(float value) {
        if (value >= 1.0F && value <= 10.0F) {
            flexibility = value;
        } else {
            throw new IllegalArgumentException("Flexibility must be between [1f..10f].");
        }
    }

    public final boolean isDebugPathEnabled() {
        return isDebugPathEnabled;
    }

    public final void setDebugPathEnabled(boolean var1) {
        isDebugPathEnabled = var1;
    }

    public boolean isShineEnabled() {
        return isShineEnabled;
    }

    public void setShineEnabled(boolean shineEnabled) {
        isShineEnabled = shineEnabled;
    }

    public ElasticView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setClickable(true);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ElasticView);
        flexibility = a.getFloat(R.styleable.ElasticView_flexibility, 0);
        a.recycle();//释放资源
    }

    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        processTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    protected void dispatchDraw(@Nullable Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isDebugPathEnabled) {
            debugPath.onDispatchDraw(canvas);
        }

        if (isShineEnabled) {
            shineProvider.onDispatchDraw(canvas);
        }

    }

    private void processTouchEvent(MotionEvent event) {
        final float verticalRotation = calculateRotation(event.getX() * flexibility * (float) 2 / (float) getWidth());
        final float horizontalRotation = -calculateRotation(event.getY() * flexibility * (float) 2 / (float) getHeight());
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                animator(verticalRotation, horizontalRotation, ANIMATION_DURATION_SHORT, true);

                break;
            case MotionEvent.ACTION_MOVE:
                setRotationY(verticalRotation);
                setRotationX(horizontalRotation);
                break;
            case MotionEvent.ACTION_UP:
                _isActionUpPerformed = true;
                if (!_isAnimating) {
                    animateToOriginalPosition();
                }
                break;
        }

    }

    private float calculateRotation(float value) {
        float tempValue = value < (float) 0 ? 1.0F : (value > flexibility * (float) 2 ? flexibility * (float) 2 : value);
        tempValue -= flexibility;
        return tempValue;
    }

    private void animateToOriginalPosition() {
        animator(0f, 0f, ANIMATION_DURATION, false);
    }


    private void animator(float rx, float ry, long duration, boolean isShow) {
        ViewPropertyAnimator animate = animate();
        animate.setInterpolator(new FastOutSlowInInterpolator());
        animate.rotationX(rx);
        animate.rotationY(ry);
        animate.setDuration(duration);

        if (isShow) {
            animate.withEndAction(() -> {
                if (_isActionUpPerformed) {
                    animateToOriginalPosition();
                } else {
                    _isAnimating = false;
                }
            });
            animate.withStartAction(() -> {
                _isActionUpPerformed = false;
                _isAnimating = true;
            });
        }

        animate.start();
    }


}
