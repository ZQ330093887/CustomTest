package com.example.customtest.widget.slidenestedpanel;

import android.view.View;

/**
 * Created by ZQiong on 2018/11/22.
 * TODO: 面板滑动回调
 */
public interface PanelSlideListener {

    //当面板滑动位置发送变化
    void onPanelSlide(View panel, float slideOffset);


    //当面板状态发送变化
    void onPanelStateChanged(View panel, PanelState preState, PanelState newState);
}
