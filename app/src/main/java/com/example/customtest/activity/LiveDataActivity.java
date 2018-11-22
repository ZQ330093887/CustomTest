package com.example.customtest.activity;

import android.os.Bundle;
import android.widget.SeekBar;

import com.example.customtest.R;
import com.example.customtest.databinding.ActivityLiveDataBinding;
import com.example.customtest.utils.livedatabus.LiveDataBus;
import com.example.customtest.viewmodel.MainViewModel;
import com.hivescm.common.widget.ToastUtils;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class LiveDataActivity extends BaseActivity<MainViewModel, ActivityLiveDataBinding> implements Injectable {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_data;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding.setLifecycleOwner(this);
        mBinding.tv3.setOnClickListener(v -> {
            LiveDataBus.get().with("key_active_level").postValue("Send Msg To Prevent");
            ToastUtils.showToast(LiveDataActivity.this, "发送成功");
        });

        mBinding.progressBar2.setProgress(40);
        mBinding.progressBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mBinding.imageElasticView.setFlexibility(progress / 10f + 1f);
                mBinding.seekBarText.setText("Flexibility is" + mBinding.imageElasticView.getFlexibility());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}
