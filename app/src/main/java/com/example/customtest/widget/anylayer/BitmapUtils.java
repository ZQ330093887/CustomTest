package com.example.customtest.widget.anylayer;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import androidx.annotation.NonNull;

/**
 * Created by ZQiong on 2018/11/22.
 */
final class BitmapUtils {

    private BitmapUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    static Bitmap scale(@NonNull Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        if (width != newWidth || height != newHeight) {
            float scaleWidth = newWidth / (float) width;
            float scaleHeight = newHeight / (float) height;
            matrix.setScale(scaleWidth, scaleHeight);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
