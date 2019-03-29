package com.example.customtest.utils.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import android.widget.RemoteViews;

import androidx.annotation.ColorInt;
import androidx.core.app.NotificationCompat;

/**
 * File: NotificationUtils.java
 * Author: chenyihui
 * Date: 2018/12/17
 */
public class NotificationUtils {

    private NotificationManager mNotiManager;
    private Context mContext;

    /**
     * 通知渠道 适配于Android O 8.0以上系统有效
     */
    private String mChannelId = "defaultId";
    /**
     * 设置渠道名称
     */
    private String mChannelName = "default";
    /**
     * 通知id
     */
    private int mNotificationId;
    /**
     * 通知tag
     */
    private String mNotificationTag;
    /**
     * 通知标题
     */
    private String mTitle;
    /**
     * 通知内容
     */
    private String mContentText;
    /**
     * 设置通知状态栏图标
     * 当 setSmallIcon() 与 setLargeIcon() 同时存在时, mSmallIcon 显示在通知的右下角, mLargeIcon 显示在左侧；
     * 当只设置 setSmallIcon() 时, mSmallIcon 显示在左侧。
     */
    private int mSmallIcon;
    /**
     * 通知图标
     */
    private int mLargeIcon;
    /**
     * 通知图标bitmap
     */
    private Bitmap mLargeIconBitmap;
    /**
     * 通知时间
     */
    private long mTimeMillis;
    /**
     * 点击通知后自动清除通知
     */
    private boolean isAutoCancel = true;
    /**
     * 打开呼吸灯，声音，震动，触发系统默认行为
     */
    private int mNotificationDefault;
    /**
     * 呼吸灯颜色
     */
    private int mLedARGB;
    /**
     * 呼吸灯唤醒时间
     */
    private int mLedOnMS;
    /**
     * 呼吸灯关闭时间
     */
    private int mLedOffMS;

    /**
     * android5.0加入了一种新的模式Notification的显示等级，共有三种
     * setVisibility() 方法共有三个选值：
     * 1.VISIBILITY_PRIVATE : 显示基本信息，如通知的图标，但隐藏通知的全部内容；
     * 2.VISIBILITY_PUBLIC : 显示通知的全部内容；
     * 3.VISIBILITY_SECRET : 不显示任何内容，包括图标。
     */
    private int mVisibility = NotificationCompat.VISIBILITY_PRIVATE;
    /**
     * 自定义震动效果
     */
    private long[] vibrate = null;

    /**
     * 首次收到的时候，在状态栏中，图标的右侧显示的文字（设置之后可以显示悬停通知）
     */
    private String mTickText;
    /**
     * 通知优先级
     */
    private int mPriority = NotificationCompat.PRIORITY_DEFAULT;

    /**
     * 处理通知的交互事件
     */
    private PendingIntent mPendingIntent;
    /**
     * 处理通知的交互事件可与 PendingIntent相同
     */
    private PendingIntent mFullScreenIntent;
    /**
     * 设置悬停 在5.0以上可以悬停之后可以正常收起，6.0以上会一直停留知道用户手动操作消失
     */
    private boolean isHighPriority = false;
    /**
     * 频道重要性
     */
    private int mImportance;
    /**
     * 设置音效
     */
    private Uri mSound;
    /**
     * 设置绕过免打扰模式
     * true 绕过 false 不绕过
     */
    private boolean bypassDnd;
    /**
     * 设置是否显示通知右边角标
     */
    private boolean showBadge;
    /**
     * 通知构造类
     */
    private NotificationCompat.Builder mBuilder;
    /**
     * 通知渠道 Android O 新特性
     */
    @TargetApi(Build.VERSION_CODES.O)
    private NotificationChannel mNotificationChannel;
    /**
     * 是否悬停
     */
    private boolean mIsStick;
    /**
     * 自定义通知
     */
    private int mContentLayout;
    /**
     * 自定义通知展开模式
     */
    private int mBigContentLayout;
    /**
     * 通知渠道组id
     */
    private String groupId;
    /**
     * 通知渠道组名称
     */
    private String groupName;

    /**
     * 通知渠道组管理
     */
    private NotificationChannelGroup mChannelGroup;

    private NotificationUtils(Context context) {
        this.mContext = context;
        mNotiManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static NotificationUtils with(Context context) {
        return new NotificationUtils(context);
    }

    /**
     * 通过通知id清除通知
     *
     * @param notificationId 通知id
     */
    public void removeNotification(int notificationId) {
        if (mNotiManager != null) {
            mNotiManager.cancel(notificationId);
        }
    }

    /**
     * 清除带tag通知 id和tag需要同时存在
     *
     * @param notificationId 通知id
     * @param tag            通知tag
     */
    public void removeNotiWithTag(int notificationId, String tag) {
        if (mNotiManager != null) {
            mNotiManager.cancel(tag, notificationId);
        }
    }

    /**
     * 清除所有通知
     */
    public void removeAll() {
        if (mNotiManager != null) {
            mNotiManager.cancelAll();
        }
    }

    /**
     * 通知渠道 适配于Android O 8.0以上系统有效
     *
     * @param channelId 全局唯一
     */
    public NotificationUtils setChannelId(String channelId) {
        this.mChannelId = channelId;
        return this;
    }

    public String getmChannelId() {
        return mChannelId;
    }

    /**
     * 设置渠道名称
     *
     * @param channelName 渠道名称
     */
    public NotificationUtils setChannelName(String channelName) {
        this.mChannelName = channelName;
        return this;
    }

    /**
     * 设置通知id
     *
     * @param notificationId 通知id
     */
    public NotificationUtils setNotificationId(int notificationId) {
        this.mNotificationId = notificationId;
        return this;
    }

    /**
     * 获取通知id
     */
    public int getNotificationId() {
        return mNotificationId;
    }

    /**
     * 设置通知tag
     */
    public NotificationUtils setNotificationTag(String notificationTag) {
        this.mNotificationTag = notificationTag;
        return this;
    }

    /**
     * 设置通知标题
     *
     * @param title 通知标题
     */
    public NotificationUtils setContentTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 获取通知标题
     */
    public String getContentTitle() {
        return mTitle;
    }

    /**
     * 设置通知内容
     *
     * @param contentText 通知内容
     */
    public NotificationUtils setContentText(String contentText) {
        this.mContentText = contentText;
        return this;
    }

    /**
     * 获取通知内容
     */
    public String getContentText() {
        return mContentText;
    }

    /**
     * 设置通知状态栏图标
     * 当 setSmallIcon() 与 setLargeIcon() 同时存在时, mSmallIcon 显示在通知的右下角, mLargeIcon 显示在左侧；
     * 当只设置 setSmallIcon() 时, mSmallIcon 显示在左侧。
     *
     * @param icon 透明图层图标
     */
    public NotificationUtils setSmallIcon(int icon) {
        this.mSmallIcon = icon;
        return this;
    }

    /**
     * 获取通知小图标
     */
    public int getSmallIcon() {
        return mSmallIcon;
    }

    /**
     * 设置通知图标，不设置时使用应用icon
     * 当 setSmallIcon() 与 setLargeIcon() 同时存在时, mSmallIcon 显示在通知的右下角, mLargeIcon 显示在左侧；
     * 当只设置 setSmallIcon() 时, mSmallIcon 显示在左侧。
     *
     * @param largeIcon
     */
    public NotificationUtils setLargeIcon(int largeIcon) {
        this.mLargeIcon = largeIcon;
        return this;
    }

    public int getLargeIcon() {
        return mLargeIcon;
    }

    /**
     * 设置通知图标
     *
     * @param largeIcon bitmap
     */
    public NotificationUtils setLargeIcon(Bitmap largeIcon) {
        this.mLargeIconBitmap = largeIcon;
        return this;
    }

    /**
     * 获取图片bitmap
     */
    public Bitmap getLargeIconBitmap() {
        try {
            if (mLargeIconBitmap != null) {
                return mLargeIconBitmap;
            } else {
                if (mLargeIcon > 0) {
                    return BitmapFactory.decodeResource(mContext.getResources(), mLargeIcon);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 设置通知创建时间
     * 默认为系统发出通知的时间，通常可以不用设置
     *
     * @param timeMillis 时间戳 系统会自动格式化
     */
    public NotificationUtils setWhen(long timeMillis) {
        this.mTimeMillis = timeMillis;
        return this;
    }

    /**
     * 获取设置通知创建时间
     */
    public long getWhen() {
        return mTimeMillis;
    }

    /**
     * 设置点击通知后清除
     *
     * @param isAutoCancel true 清除 false 不清除
     */
    public NotificationUtils setAutoCancel(boolean isAutoCancel) {
        this.isAutoCancel = isAutoCancel;
        return this;
    }

    /**
     * 是否设置点击通知清除缓存
     *
     * @return true 清除 false 不清楚
     */
    public boolean isAutoCancel() {
        return isAutoCancel;
    }

    /**
     * 打开呼吸灯，声音，震动，触发系统默认行为
     * NotificationCompat.DEFAULT_VIBRATE     添加默认震动提醒  需要VIBRATE permission
     * NotificationCompat.DEFAULT_SOUND       添加默认声音提醒
     * NotificationCompat.DEFAULT_LIGHTS      添加默认三色灯提醒
     * NotificationCompat.DEFAULT_ALL         添加默认以上3种全部提醒
     */
    public NotificationUtils setDefault(int defaults) {
        this.mNotificationDefault = defaults;
        return this;
    }

    /**
     * 获取通知默认设置
     */
    public int getNotificationDefault() {
        return mNotificationDefault;
    }

    /**
     * android5.0加入了一种新的模式Notification的显示等级，共有三种
     * setVisibility() 方法共有三个选值：
     * 1.VISIBILITY_PRIVATE : 显示基本信息，如通知的图标，但隐藏通知的全部内容；
     * 2.VISIBILITY_PUBLIC : 显示通知的全部内容；
     * 3.VISIBILITY_SECRET : 不显示任何内容，包括图标。
     */
    public NotificationUtils setVisibility(int visibility) {
        this.mVisibility = visibility;
        return this;
    }

//    public int getVisibility(){
//        return mVisibility;
//    }

    /**
     * 自定义震动效果
     * 设置震动，用一个 long 的数组来表示震动状态，这里表示的是先震动1秒、静止1秒、再震动1秒，这里以毫秒为单位
     * 如果要设置先震动1秒，然后停止0.5秒，再震动2秒则可设置数组为：long[]{1000, 500, 2000}。
     *
     * @param pattern
     */
    public NotificationUtils setVibrate(long[] pattern) {
        this.vibrate = pattern;
        return this;
    }

    /**
     * 设置手机的LED灯为蓝色并且灯亮2秒，熄灭1秒，达到灯闪烁的效果，不过这些效果在模拟器上是看不到的，
     * 需要将程序安装在真机上才能看到对应效果，如果不想设置这些通知提示效果，
     * 可以直接设置：setDefaults(Notification.DEFAULT_ALL);
     * 意味将通知的提示效果设置为系统的默认提示效果
     *
     * @param argb  颜色 一般三种颜色：红，绿，蓝，经测试，小米支持黄色 Color.YELLOW Color.RED Color.GREEN
     * @param onMs  显示时间
     * @param offMs 消失时间
     */
    public NotificationUtils setLights(@ColorInt int argb, int onMs, int offMs) {
        this.mLedARGB = argb;
        this.mLedOnMS = onMs;
        this.mLedOffMS = offMs;
        return this;
    }

    /**
     * 首次收到的时候，在状态栏中，图标的右侧显示的文字（设置之后可以显示悬停通知）
     *
     * @param tickerText
     */
    public NotificationUtils setTicker(String tickerText) {
        this.mTickText = tickerText;
        return this;
    }

    /**
     * 设置通知悬停效果
     *
     * @param mIsStick true 悬停 false 不需要
     */
    public NotificationUtils isStick(boolean mIsStick) {
        this.mIsStick = mIsStick;
        return this;
    }

    /**
     * 设置通知优先级
     *
     * @param pri NotificationCompat.PRIORITY_DEFAULT
     */
    public NotificationUtils setPriority(int pri) {
        this.mPriority = pri;
        return this;
    }

    /**
     * 设置处理通知的交互事件
     * example:
     * Intent intent = new Intent(mActivity,  MainActivity.class);
     * PendingIntent pendingIntent = PendingIntent.getActivity(mActivity, 2,
     * intent, PendingIntent.FLAG_UPDATE_CURRENT);
     */
    public NotificationUtils setContentIntent(PendingIntent pendingIntent) {
        this.mPendingIntent = pendingIntent;
        return this;
    }


    /**
     * 设置全局悬停效果
     *
     * @param highPriority true 需要悬停 false 不需要
     */
    public NotificationUtils setFullScreen(boolean highPriority) {
        this.isHighPriority = highPriority;
        return this;
    }

    /**
     * 设置渠道重要性，用于NotificationChannel
     * example:
     * NotificationManager.IMPORTANCE_HIGH
     */
    public NotificationUtils setImportance(int importance) {
        this.mImportance = importance;
        return this;
    }

    /**
     * 设置通知声音
     *
     * @param sound eg:
     *              调用自己提供的铃声，位于 /res/values/raw 目录下
     *              Uri.parse("android.resource://com.cyh.demo/" + R.raw.sound)
     */
    public NotificationUtils setSound(Uri sound) {
        this.mSound = sound;
        return this;
    }

    /**
     * 设置绕过免打扰模式
     *
     * @param bypassDnd true 绕过免打扰 false 不绕过
     */
    public NotificationUtils setBypassDnd(boolean bypassDnd) {
        this.bypassDnd = bypassDnd;
        return this;
    }

    /**
     * 设置是否显示通知右边角标
     *
     * @param showBadge true 显示 false 不显示
     */
    public NotificationUtils setShowBadge(boolean showBadge) {
        this.showBadge = showBadge;
        return this;
    }

    /**
     * 设置通知构造
     */
    public NotificationUtils setBuilder(NotificationCompat.Builder builder) {
        this.mBuilder = builder;
        return this;
    }

    /**
     * 设置通知渠道 Android O 新特性
     * 如果通知渠道 channelId 一样，重新创建渠道，会使用已经被删除的通知渠道
     * channel一旦被create出来。修改属性没有作用，可以通过删除掉。再重新create。
     * 但是要记得要删除的channelId和要新建的channelId不能一致
     * @param notificationChannel
     */
    @TargetApi(Build.VERSION_CODES.O)
    public NotificationUtils setNotificationChannel(NotificationChannel notificationChannel) {
        this.mNotificationChannel = notificationChannel;
        return this;
    }

    /**
     * 删除通知渠道
     * channel一旦被create出来。修改属性没有作用，可以通过删除掉。再重新create。
     * 但是要记得要删除的channelId和要新建的channelId不能一致
     * @param channelId 渠道id
     */
    @TargetApi(Build.VERSION_CODES.O)
    public NotificationUtils deleteNotificationChannel(String channelId) {
        if (TextUtil.isValidate(channelId)) {
            mNotiManager.deleteNotificationChannel(channelId);
        }
        return this;
    }


    /**
     * 设置自定义通知
     *
     * @param layout
     */
    public NotificationUtils setCustomContentView(int layout) {
        this.mContentLayout = layout;
        return this;
    }

    /**
     * 设置自定义通知 展开状态
     *
     * @param layout
     */
    public NotificationUtils setCustomBigContentView(int layout) {
        this.mBigContentLayout = layout;
        return this;
    }

//    public NotificationUtils setCustomHeadsUpContentView(){
//        return this;
//    }

    /**
     * 设置渠道组别
     * @param channelGroup 渠道组别
     */
    public NotificationUtils setNotificationChannelGroup(NotificationChannelGroup channelGroup) {
        this.mChannelGroup = channelGroup;
        return this;
    }

    /**
     * 设置渠道id
     * @param groupId 组id
     */
    public NotificationUtils setChannelGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    /**
     * 设置渠道组名称
     * @param groupName 组名
     */
    public NotificationUtils setChannelGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    /**
     * 设置自定义通知view回调
     *
     * @param listener
     */
    public NotificationUtils setContentViewListener(ContentViewListener listener) {
        if (listener != null) {
            mListener = listener;
        }
        return this;
    }

    ContentViewListener mListener;

    /**
     * 自定义通知回调
     */
    public interface ContentViewListener {
        void onContentView(RemoteViews collapsed);

        void onBigContentView(RemoteViews show);
    }


    /**
     * 发送通知
     *
     * @return 1、成功发送通知 -1、发送通知失败
     */
    public int showNotify() {
        try {
            initChannelGroup();
            initNotificationChannel();
            NotificationCompat.Builder builder = initBuilder();
            builder = initRemoteViews(builder);
            Notification notification = builder.build();
            if (TextUtil.isValidate(mNotificationTag)) {
                mNotiManager.notify(mNotificationTag, mNotificationId, notification);
            } else {
                mNotiManager.notify(mNotificationId, notification);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void initChannelGroup(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mChannelGroup != null) {
                mNotiManager.createNotificationChannelGroup(mChannelGroup);
            } else {
                if (TextUtil.isValidate(groupId) && TextUtil.isValidate(groupName)) {
                    NotificationChannelGroup group = new NotificationChannelGroup(groupId, groupName);
                    mNotiManager.createNotificationChannelGroup(group);
                }
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public NotificationUtils deleteChannelGroup(String groupId){
        if (TextUtil.isValidate(groupId)) {
            mNotiManager.deleteNotificationChannelGroup(groupId);
        }
        return this;
    }

    /**
     * 创建自定义通知
     */
    private NotificationCompat.Builder initRemoteViews(NotificationCompat.Builder builder) {
        try {
            RemoteViews collapsed = null;
            if (mContentLayout > 0) {
                collapsed = new RemoteViews(mContext.getPackageName(), mContentLayout);
                if (mListener != null) {
                    mListener.onContentView(collapsed);
                }
                builder.setCustomContentView(collapsed);
            }
            RemoteViews show;
            if (mBigContentLayout > 0) {
                show = new RemoteViews(mContext.getPackageName(), mBigContentLayout);
                if (mListener != null) {
                    mListener.onBigContentView(show);
                }
                builder.setCustomBigContentView(show);
            }

            if (collapsed != null) {
                builder.setCustomHeadsUpContentView(collapsed);
            }
            return builder;

        } catch (Exception e) {
            e.printStackTrace();
            return builder;
        }
    }

    /**
     * 通知构造
     */
    private NotificationCompat.Builder initBuilder() {

        if (mBuilder != null) {
            return mBuilder;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, mChannelId);

        try {
            if (TextUtil.isValidate(mTitle)) {
                builder.setContentTitle(mTitle);
            }

            if (TextUtil.isValidate(mContentText)) {
                builder.setContentText(mContentText);
            }

            if (mSmallIcon > 0) {
                builder.setSmallIcon(mSmallIcon);
            }

            if (mLargeIconBitmap != null) {
                builder.setLargeIcon(mLargeIconBitmap);
            } else {
                if (mLargeIcon > 0) {
                    builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), mLargeIcon));
                }
            }
            if (mTimeMillis > 0) {
                builder.setWhen(mTimeMillis);
            }

            builder.setAutoCancel(isAutoCancel);

            builder.setVisibility(mVisibility);

            if (mNotificationDefault != 0) {
                builder.setDefaults(mNotificationDefault);
            }

            builder.setVisibility(mVisibility);

            if (TextUtil.isValidate(vibrate)) {
                builder.setVibrate(vibrate);
            }

            if (mLedARGB > 0 && mLedOnMS > 0) {
                builder.setLights(mLedARGB, mLedOnMS, mLedOffMS);
            }

            if (mIsStick || TextUtil.isValidate(mTickText)) {
                builder.setTicker(mTickText);
            }

            builder.setPriority(mPriority);

            if (mPendingIntent != null) {
                builder.setContentIntent(mPendingIntent);
            }

            if (isHighPriority) {
                if (mPendingIntent != null) {
                    builder.setFullScreenIntent(mPendingIntent, isHighPriority);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            return builder;
        }

        return builder;
    }

    /**
     * 创建通知渠道
     */
    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mNotificationChannel != null) {
                mNotiManager.createNotificationChannel(mNotificationChannel);
            } else {
                NotificationChannel channel = new NotificationChannel(mChannelId, mChannelName, mImportance);
                if (mLedARGB > 0) {
                    channel.enableLights(true);
                    channel.setLightColor(mLedARGB);
                }

                if (TextUtil.isValidate(vibrate)) {
                    channel.enableVibration(true);
                    channel.setVibrationPattern(vibrate);
                } else {
                    channel.enableVibration(false);
                    channel.setVibrationPattern(new long[]{0});
                }

                channel.setBypassDnd(bypassDnd);
                channel.setShowBadge(showBadge);
                channel.setLockscreenVisibility(mVisibility);
                if (TextUtil.isValidate(groupId)) {
                    channel.setGroup(groupId);
                }
                mNotiManager.createNotificationChannel(channel);
            }
        }
    }
}
