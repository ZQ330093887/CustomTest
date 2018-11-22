package com.example.customtest.widget.anylayer;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by ZQiong on 2018/11/22.
 */
class Utils {

    /**
     * 从当前上下文获取Activity
     */
    @Nullable
    static Activity getActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof Activity) {
                return (Activity) baseContext;
            }
        }
        return null;
    }

    static Bitmap snapshot(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        view.destroyDrawingCache();
        return view.getDrawingCache();
    }
}
