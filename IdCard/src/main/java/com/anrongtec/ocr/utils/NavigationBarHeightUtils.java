package com.anrongtec.ocr.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ZQiong on 2018/8/24.
 */
public class NavigationBarHeightUtils {
    /**
     * 如果有NavigationBar则返回高度值，没有返回 0
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {
        boolean isHasNavigationBar = checkDeviceHasNavigationBar(activity);
        if (isHasNavigationBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        }else {
            return 0;
        }
    }
    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }

        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return hasNavigationBar;
    }
}
