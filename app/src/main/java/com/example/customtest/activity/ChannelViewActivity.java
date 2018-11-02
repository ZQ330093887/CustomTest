package com.example.customtest.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.customtest.R;
import com.example.customtest.databinding.ActivityCannelViewBinding;
import com.example.customtest.viewmodel.MainViewModel;
import com.example.customtest.widget.channelview.ChannelView;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

/**
 * 一款频道选择器，可以进行频道的拖动、排序、增删，动态的改变高度，精简而又流畅
 *
 */

public class ChannelViewActivity extends BaseActivity<MainViewModel, ActivityCannelViewBinding>
        implements Injectable, ChannelView.OnChannelListener {
    private String TAG = getClass().getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cannel_view;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    private void init() {
        String[] myChannel = {"要闻", "视频", "新时代", "娱乐", "体育", "军事", "NBA", "国际", "科技", "财经", "汽车", "电影", "游戏", "独家", "房产",
                "图片", "时尚", "呼和浩特", "三打白骨精"};
        String[] recommendChannel0 = {"综艺", "美食", "育儿", "冰雪", "必读", "政法网事", "都市",
                "NFL", "韩流"};
        String[] recommendChannel = {"问答", "文化", "佛学", "股票", "动漫", "理财", "情感", "职场", "旅游"};
        String[] recommendChannel2 = {"家居", "电竞", "数码", "星座", "教育", "美容", "电视剧",
                "搏击", "健康"};

        Map<String, String[]> channels = new LinkedHashMap<>();
        channels.put("我的频道", myChannel);
        channels.put("推荐频道", recommendChannel0);
        channels.put("国内", recommendChannel);
        channels.put("国外", recommendChannel2);

        mBinding.channelView.setFixedChannel(2);
        mBinding.channelView.setChannels(channels);
        mBinding.channelView.setMyChannelBelong(1, 2);
        mBinding.channelView.setMyChannelBelong(1, 3);
        mBinding.channelView.setMyChannelBelong(5, 4);
        mBinding.channelView.setMyChannelBelong(7, 3);
        mBinding.channelView.setMyChannelBelong(0, 2);
        mBinding.channelView.setChannelNormalBackground(R.drawable.bg_channel_normal);
        mBinding.channelView.setChannelSelectedBackground(R.drawable.bg_channel_selected);
        mBinding.channelView.setChannelFocusedBackground(R.drawable.bg_channel_focused);
        mBinding.channelView.setOnChannelItemClickListener(this);

    }

    @Override
    public void channelItemClick(int itemId, String channel) {
        Log.i(TAG, itemId + ".." + channel);
    }

    @Override
    public void channelFinish(List<String> channels) {
        Log.i(TAG, channels.toString());
    }
}
