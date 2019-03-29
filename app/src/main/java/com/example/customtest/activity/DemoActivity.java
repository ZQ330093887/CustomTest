package com.example.customtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.example.customtest.R;
import com.example.customtest.databinding.MainBinding;
import com.example.customtest.utils.notification.NotificationUtil;
import com.example.customtest.viewmodel.MainViewModel;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

public class DemoActivity extends BaseActivity<MainViewModel, MainBinding>
        implements Injectable {
    private int notificationId = 1;

    private DemoActivity mActivity;

    String channelId = "notification_simple";
    String channelName = "系统通知";
    String title = "This is notification title";
    String content = "This is notification content";
    String notificationTag = "notifyTag";

    @Override
    protected int getLayoutId() {
        return R.layout.main;
    }


    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    public void onClick(View v) {
        Intent skipIntent = new Intent(mActivity, DemoActivity.class);
        switch (v.getId()) {
            case R.id.mBtnSendNomalNotify:
                NotificationUtil.with(mActivity).showNotification(skipIntent, notificationId,
                        channelId, channelName, title, content, false);
//                notificationId++;
                break;


            case R.id.mBtnSendStickNotify:
                NotificationUtil.with(mActivity).showNotification(skipIntent, notificationId,
                        channelId, channelName, title, content, true);
//                notificationId++;
                break;

            case R.id.mBtnSendFoldNotify:
                NotificationUtil.with(mActivity).showRemoteNotification(skipIntent, notificationId, "",
                        channelId, channelName, title, content, true);
//                notificationId++;
                break;

            case R.id.mBtnSendTagNotify:
                NotificationUtil.with(mActivity).showNotification(skipIntent, notificationId, notificationTag,
                        channelId, channelName, title, content, false);
//                notificationId++;
                break;
            case R.id.mBtnCancelNotificationTag:
//                notificationId--;
                NotificationUtil.with(mActivity).removeNotiWithTag(notificationId, notificationTag);
                break;

            case R.id.mBtnCancelNotification:
//                notificationId--;
                NotificationUtil.with(mActivity).removeNotification(notificationId);
                break;

            case R.id.mBtnCancelAll:
//                notificationId = 1;
                NotificationUtil.with(mActivity).removeAll();
                break;

            case R.id.mBtnNewUtil:
                startActivity(new Intent(mActivity, NotificationActivity.class));
                break;
            default:
                break;

        }
    }
}