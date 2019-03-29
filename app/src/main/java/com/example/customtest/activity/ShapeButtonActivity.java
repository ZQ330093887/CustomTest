package com.example.customtest.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.customtest.R;
import com.example.customtest.databinding.ActivityShapeButtonBinding;
import com.example.customtest.utils.livedatabus.LiveDataBus;
import com.example.customtest.viewmodel.MainViewModel;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;
import com.hivescm.commonbusiness.util.ToastUtils;

public class ShapeButtonActivity extends BaseActivity<MainViewModel, ActivityShapeButtonBinding> implements Injectable {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shape_button;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LiveDataBus.get()
                .with("key_active_level", String.class)
                .observeSticky(this, s -> {
                    ToastUtils.showToast(ShapeButtonActivity.this, s);
                    mBinding.tvSticky1.setText("observeSticky注册的观察者收到消息: " + s);
                });
    }
}
