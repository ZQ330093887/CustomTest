package com.example.customtest.utils.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.customtest.R;

/**
 * File: NotificationUtil.java
 * Author: chenyihui
 * Date: 2018/12/13
 */
public class NotificationUtil {
    private static NotificationUtil mInstance;
    private NotificationManager mNotiManager;
    private Context mContext;

    private NotificationUtil(Context context) {
        this.mContext = context;
        mNotiManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static NotificationUtil with(Context context) {
        if (mInstance == null) {
            synchronized (NotificationUtil.class) {
                if (mInstance == null) {
                    mInstance = new NotificationUtil(context);
                }
            }
        }
        return mInstance;
    }

    public void removeNotification(int notificationId) {
        if (mNotiManager != null) {
            mNotiManager.cancel(notificationId);
        }
    }

    public void removeNotiWithTag(int notificationId, String tag) {
        if (mNotiManager != null) {
            mNotiManager.cancel(tag, notificationId);
        }
    }

    public void removeAll() {
        if (mNotiManager != null) {
            mNotiManager.cancelAll();
        }
    }


    /**
     * @param skipIntent     跳转intent
     * @param notificationId 通知id
     * @param channelId      通知渠道 适配于Android O 8.0以上系统有效
     * @param title          通知标题
     * @param content        通知内容
     * @param isTicker       是否需要通知栏悬停
     * @Description: 显示通知
     * @Author: chenyihui
     * @Time: 2018/12/13 3:44 PM
     */
    public void showNotification(Intent skipIntent, int notificationId,
                                 String channelId, String channelName, String title, String content, boolean isTicker) {
        showNotification(skipIntent, notificationId, "", channelId, channelName, title, content, isTicker);
    }

    /**
     * @param skipIntent     跳转intent
     * @param notificationId 通知id
     * @param notiTag        通知tag
     * @param channelId      通知渠道 适配于Android O 8.0以上系统有效
     * @param title          通知标题
     * @param content        通知内容
     * @param isTicker       是否需要通知栏悬停
     * @Description: 显示带notificationtag通知
     * @Author: chenyihui
     * @Time: 2018/12/13 3:44 PM
     */
    public void showNotification(Intent skipIntent, int notificationId, String notiTag,
                                 String channelId, String channelName, String title, String content, boolean isTicker) {

        createNotificationChannel(mNotiManager, channelId, channelName);
        NotificationCompat.Builder builder = initNotifyBuilder(skipIntent, notificationId,
                channelId, title, content, isTicker);
        Notification notification = builder.build();
        if (notiTag != null && !"".equals(notiTag)) {
            mNotiManager.notify(notiTag, notificationId, notification);
        } else {
            mNotiManager.notify(notificationId, notification);
        }

    }


    public void showRemoteNotification(Intent skipIntent, int notificationId, String notiTag,
                                       String channelId, String channelName, String title, String content, boolean isTicker){
        createNotificationChannel(mNotiManager, channelId, channelName);
        NotificationCompat.Builder builder = initNotifyBuilder(skipIntent, notificationId,
                channelId, title, content, isTicker);
        builder = initRemoteViews(builder);
        Notification notification = builder.build();
        if (notiTag != null && !"".equals(notiTag)) {
            mNotiManager.notify(notiTag, notificationId, notification);
        } else {
            mNotiManager.notify(notificationId, notification);
        }
    }

    private NotificationCompat.Builder initRemoteViews(NotificationCompat.Builder builder){
        RemoteViews collapsed = new RemoteViews(mContext.getPackageName(), R.layout.notify_collapsed);
        RemoteViews show = new RemoteViews(mContext.getPackageName(), R.layout.notify_show);
        builder.setCustomContentView(collapsed);
        builder.setCustomBigContentView(show);
        //有设置悬停的时候需要设置，显示悬停view 不然会显示CustomBigContentView
        builder.setCustomHeadsUpContentView(show);
        return builder;
    }

    /**
     * @param skipIntent     跳转intent
     * @param notificationId 通知id
     * @param channelId      通知渠道 适配于Android O 8.0以上系统有效
     * @param title          通知标题
     * @param content        通知内容
     * @param isTicker       是否需要通知栏悬停
     * @Description: 创建通知
     * @Author: chenyihui
     * @Time: 2018/12/13 3:44 PM
     */
    private NotificationCompat.Builder initNotifyBuilder(Intent skipIntent, int notificationId,
                                                         String channelId, String title, String content,
                                                         boolean isTicker) {


        /**
         * setPriority() 方法共有5个等级：
         * 1. PRIORITY_MIN - 最低级别（-2）
         * 2. PRIORITY_LOW - 较低级别（-1）
         * 3. PRIORITY_DEFAULT - 默认级别（0）
         * 4. PRIORITY_HIGH - 较高级别（1）
         * 5. PRIORITY_MAX - 最高级别（2） 当发出此类型的通知时，通知会以悬挂的方法显示在屏幕上
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId);
        //设置通知标题
        builder.setContentTitle(title)
                //打开呼吸灯，声音，震动，触发系统默认行为
                /**
                 * Notification.DEFAULT_VIBRATE     //添加默认震动提醒  需要VIBRATE permission
                 * Notification.DEFAULT_SOUND       //添加默认声音提醒
                 * Notification.DEFAULT_LIGHTS      //添加默认三色灯提醒
                 * Notification.DEFAULT_ALL         //添加默认以上3种全部提醒
                 */
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                //设置通知内容
                .setContentText(content)
                //设置通知时间，默认为系统发出通知的时间，通常可以不用设置
                .setWhen(System.currentTimeMillis())
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher_round)
                //调用自己提供的铃声，位于 /res/values/raw 目录下
//                .setSound(Uri.parse("android.resource://com.cyh.demo/" + R.raw.sound))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                //设置点击通知后清除
                .setAutoCancel(true);
        /**
         * android5.0加入了一种新的模式Notification的显示等级，共有三种
         * setVisibility() 方法共有三个选值：
         * 1.VISIBILITY_PRIVATE : 显示基本信息，如通知的图标，但隐藏通知的全部内容；
         * 2.VISIBILITY_PUBLIC : 显示通知的全部内容；
         * 3.VISIBILITY_SECRET : 不显示任何内容，包括图标。
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
            builder.setVibrate(new long[]{100, 200, 300, 400});
        }
        //单独设置呼吸灯，一般三种颜色：红，绿，蓝，经测试，小米支持黄色
        builder.setLights(Color.YELLOW, 300, 0);

        if (isTicker) {
            //首次收到的时候，在状态栏中，图标的右侧显示的文字（设置之后可以显示悬停通知）
            builder.setTicker(content);
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
        }


        if (skipIntent != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationId,
                    skipIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //设置通知交互
            builder.setContentIntent(pendingIntent);
            if (isTicker) {
                //在5.0以上可以悬停之后可以正常收起，6.0以上会一直停留知道用户手动操作消失
//                builder.setFullScreenIntent(pendingIntent, true);
            }
        }


        return builder;
    }

    /**
     * @Description: create NotificationManager
     * @Author: chenyihui
     * @Time: 2018/12/13 11:08 AM
     * getId() —  获取 ChannleId
     * enableLights() —  开启指示灯，如果设备有的话。
     * setLightColor() —  设置指示灯颜色
     * enableVibration() —  开启震动
     * setVibrationPattern() —  设置震动频率
     * setImportance() —  设置频道重要性
     * getImportance() —  获取频道重要性
     * setSound() —  设置声音
     * getSound() —  获取声音
     * setGroup() —  设置 ChannleGroup
     * getGroup() —  得到 ChannleGroup
     * setBypassDnd() —  设置绕过免打扰模式
     * canBypassDnd() —  检测是否绕过免打扰模式
     * getName() —  获取名称
     * setLockScreenVisibility() —  设置是否应在锁定屏幕上显示此频道的通知
     * getLockscreenVisibility() —  检测是否应在锁定屏幕上显示此频道的通知
     * setShowBadge() 设置是否显示角标
     * canShowBadge() —  检测是否显示角标
     */

    private void createNotificationChannel(NotificationManager manager, String channelId, String channelName) {
        //获取NotificationManager实例
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400});

            if (channel.canBypassDnd()) {
                channel.setBypassDnd(true);
            }

            if (channel.canShowBadge()) {
                channel.setShowBadge(true);
            }

            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager.createNotificationChannel(channel);
        }

    }
}
