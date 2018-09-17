package com.example.customtest.widget.shapebutton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

import android.util.AttributeSet;

import com.example.customtest.R;

import java.lang.reflect.Field;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;


/**
 * 公共的按下特效button
 * Created by ZQiong on 2018/8/30.
 */

public class CommonShapeButton extends AppCompatButton {

    private static final int TOP_LEFT = 1;
    private static final int TOP_RIGHT = 2;
    private static final int BOTTOM_RIGHT = 4;
    private static final int BOTTOM_LEFT = 8;

    private final GradientDrawable normalGradientDrawable = new GradientDrawable();
    private final GradientDrawable pressedGradientDrawable = new GradientDrawable();
    private final StateListDrawable stateListDrawable = new StateListDrawable();

    /**
     * shape模式
     * 矩形（rectangle）、椭圆形(oval)、线形(line)、环形(ring)
     */
    private int mShapeMode = 0;

    /**
     * 填充颜色
     */
    private int mFillColor = 0;

    /**
     * 按压颜色
     */
    private int mPressedColor = 0;

    /**
     * 描边颜色
     */
    private int mStrokeColor = 0;

    /**
     * 描边宽度
     */
    private int mStrokeWidth = 0;

    /**
     * 圆角半径
     */
    private int mCornerRadius = 0;
    /**
     * 圆角位置
     */
    private int mCornerPosition = 0;

    /**
     * 点击动效
     */
    private boolean mActiveEnable = false;

    /**
     * 起始颜色
     */
    private int mStartColor = 0;

    /**
     * 结束颜色
     */
    private int mEndColor = 0;

    /**
     * 渐变方向
     * 0-GradientDrawable.Orientation.TOP_BOTTOM
     * 1-GradientDrawable.Orientation.LEFT_RIGHT
     */
    private int mOrientation = 0;

    /**
     * drawable位置
     * -1-null、0-left、1-top、2-right、3-bottom
     */
    private int mDrawablePosition = -1;

    // button内容总宽度
    private float contentWidth = 0f;
    // button内容总高度
    private float contentHeight = 0f;


    public CommonShapeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonShapeButton);
        mShapeMode = a.getInt(R.styleable.CommonShapeButton_csb_shapeMode, 0);
        mFillColor = a.getColor(R.styleable.CommonShapeButton_csb_fillColor, 0xFFFFFFFF);
        mPressedColor = a.getColor(R.styleable.CommonShapeButton_csb_pressedColor, 0xFF666666);
        mStrokeColor = a.getColor(R.styleable.CommonShapeButton_csb_strokeColor, 0);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.CommonShapeButton_csb_strokeWidth, 0);
        mCornerRadius = a.getDimensionPixelSize(R.styleable.CommonShapeButton_csb_cornerRadius, 0);
        mCornerPosition = a.getInt(R.styleable.CommonShapeButton_csb_cornerPosition, -1);
        mActiveEnable = a.getBoolean(R.styleable.CommonShapeButton_csb_activeEnable, false);
        mDrawablePosition = a.getInt(R.styleable.CommonShapeButton_csb_drawablePosition, -1);
        mStartColor = a.getColor(R.styleable.CommonShapeButton_csb_startColor, 0xFFFFFFFF);
        mEndColor = a.getColor(R.styleable.CommonShapeButton_csb_endColor, 0xFFFFFFFF);
        mOrientation = a.getColor(R.styleable.CommonShapeButton_csb_orientation, 0);
        a.recycle();//释放资源
    }

    @SuppressLint("DrawAllocation")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        GradientDrawable drawable = this.normalGradientDrawable;
        if (this.mStartColor != (int) 4294967295L && this.mEndColor != (int) 4294967295L) {
            drawable.setColors(new int[]{this.mStartColor, this.mEndColor});
            switch (this.mOrientation) {
                case 0:
                    drawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    break;
                case 1:
                    drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            }
        } else {
            drawable.setColor(this.mFillColor);
        }

        switch (this.mShapeMode) {
            case 0:
                drawable.setShape(GradientDrawable.RECTANGLE);
                break;
            case 1:
                drawable.setShape(GradientDrawable.OVAL);
                break;
            case 2:
                drawable.setShape(GradientDrawable.LINE);
                break;
            case 3:
                drawable.setShape(GradientDrawable.RING);
        }

        if (this.mCornerPosition == -1) {
            drawable.setCornerRadius((float) this.mCornerRadius);
        } else {
            drawable.setCornerRadii(this.getCornerRadiusByPosition());
        }

        if (this.mStrokeColor != 0) {
            drawable.setStroke(this.mStrokeWidth, this.mStrokeColor);
        }

        CommonShapeButton mShapeButton = this;
        Drawable mDb;
        if (this.mActiveEnable) {
            if (Build.VERSION.SDK_INT >= 21) {
                mDb = (new RippleDrawable(ColorStateList.valueOf(this.mPressedColor), drawable, null));
            } else {
                drawable = this.pressedGradientDrawable;
                drawable.setColor(this.mPressedColor);
                switch (this.mShapeMode) {
                    case 0:
                        drawable.setShape(GradientDrawable.RECTANGLE);
                        break;
                    case 1:
                        drawable.setShape(GradientDrawable.OVAL);
                        break;
                    case 2:
                        drawable.setShape(GradientDrawable.LINE);
                        break;
                    case 3:
                        drawable.setShape(GradientDrawable.RING);
                }

                drawable.setCornerRadius((float) this.mCornerRadius);
                drawable.setStroke(this.mStrokeWidth, this.mStrokeColor);
                StateListDrawable var8 = this.stateListDrawable;
                var8.addState(new int[]{16842919}, this.pressedGradientDrawable);
                var8.addState(new int[0], drawable);
                mShapeButton = this;
                mDb = var8;
            }
        } else {
            mDb = drawable;
        }

        mShapeButton.setBackground(mDb);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mDrawablePosition > -1) {
            Drawable drawable = this.getCompoundDrawables()[this.mDrawablePosition];
            if (drawable != null) {
                int drawablePadding = this.getCompoundDrawablePadding();
                int drawableHeight;
                switch (this.mDrawablePosition) {
                    case 0:
                    case 2:
                        drawableHeight = drawable.getIntrinsicWidth();
                        float textWidth = this.getPaint().measureText(this.getText().toString());
                        this.contentWidth = textWidth + (float) drawableHeight + (float) drawablePadding;
                        int rightPadding = (int) ((float) this.getWidth() - this.contentWidth);
                        this.setPadding(0, 0, rightPadding, 0);
                        break;
                    case 1:
                    case 3:
                        drawableHeight = drawable.getIntrinsicHeight();
                        Paint.FontMetrics fm = this.getPaint().getFontMetrics();
                        float singeLineHeight = (float) Math.ceil((double) fm.descent - (double) fm.ascent);
                        float totalLineSpaceHeight = (float) (this.getLineCount() - 1) * this.getLineSpacingExtra();
                        float textHeight = singeLineHeight * (float) this.getLineCount() + totalLineSpaceHeight;
                        this.contentHeight = textHeight + (float) drawableHeight + (float) drawablePadding;
                        int bottomPadding = (int) ((float) this.getHeight() - this.contentHeight);
                        this.setPadding(0, 0, 0, bottomPadding);
                }
            }
        }

        this.setGravity(17);
        this.setClickable(true);
        this.changeTintContextWrapperToActivity();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.contentWidth > (float) 0 && (this.mDrawablePosition == 0 || this.mDrawablePosition == 2)) {
            canvas.translate(((float) this.getWidth() - this.contentWidth) / (float) 2, 0.0F);
        } else if (this.contentHeight > (float) 0 && (this.mDrawablePosition == 1 || this.mDrawablePosition == 3)) {
            canvas.translate(0.0F, ((float) this.getHeight() - this.contentHeight) / (float) 2);
        }

        super.onDraw(canvas);
    }

    private void changeTintContextWrapperToActivity() {
        if (Build.VERSION.SDK_INT < 21) {
            Activity var10000 = this.getActivity();
            if (var10000 != null) {
                Class clazz = this.getClass();

                while (clazz != null) {
                    try {
                        Field field = clazz.getDeclaredField("mContext");
                        field.setAccessible(true);
                        field.set(this, var10000);
                        break;
                    } catch (Exception var6) {
                        var6.printStackTrace();
                        clazz = clazz.getSuperclass();
                    }
                }
            }
        }
    }

    private Activity getActivity() {
        for (Context context = this.getContext();
             context instanceof ContextWrapper;
             context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
        }
        return null;
    }

    private float[] getCornerRadiusByPosition() {
        float[] result = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
        float cornerRadius = (float) this.mCornerRadius;
        if (this.containsFlag(this.mCornerPosition, TOP_LEFT)) {
            result[0] = cornerRadius;
            result[1] = cornerRadius;
        }

        if (this.containsFlag(this.mCornerPosition, TOP_RIGHT)) {
            result[2] = cornerRadius;
            result[3] = cornerRadius;
        }

        if (this.containsFlag(this.mCornerPosition, BOTTOM_RIGHT)) {
            result[4] = cornerRadius;
            result[5] = cornerRadius;
        }

        if (this.containsFlag(this.mCornerPosition, BOTTOM_LEFT)) {
            result[6] = cornerRadius;
            result[7] = cornerRadius;
        }

        return result;
    }


    private boolean containsFlag(int flagSet, int flag) {
        return (flagSet | flag) == flagSet;
    }
}
