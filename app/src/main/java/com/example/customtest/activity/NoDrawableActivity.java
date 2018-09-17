package com.example.customtest.activity;

import android.os.Bundle;

import com.example.customtest.R;
import com.example.customtest.databinding.ActivityLaunchBinding;
import com.example.customtest.viewmodel.MainViewModel;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class NoDrawableActivity extends BaseActivity<MainViewModel, ActivityLaunchBinding> implements Injectable {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
