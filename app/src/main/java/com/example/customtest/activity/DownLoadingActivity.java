package com.example.customtest.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.customtest.R;
import com.example.customtest.databinding.ActivityDownLoadingBinding;
import com.example.customtest.databinding.ActivityShapeButtonBinding;
import com.example.customtest.viewmodel.MainViewModel;
import com.example.customtest.widget.down360style.Down360LoadingView;
import com.example.customtest.widget.down360style.Down360ViewGroup;
import com.hivescm.common.widget.ToastUtils;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class DownLoadingActivity extends BaseActivity<MainViewModel, ActivityDownLoadingBinding>
        implements Injectable, Down360LoadingView.OnProgressStateChangeListener {

    private static final String TAG = DownLoadingActivity.class.getSimpleName();
    //模拟进度的计时器
    private Timer timer;
    private int progress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_down_loading;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (!mBinding.downLoadingViewgroup.isStop()) {
                        if (mBinding.downLoadingViewgroup.getStatus() == Down360LoadingView.Status.Load) {
                            progress++;
                            Log.d(TAG, "DownActivity:" + progress);
                            mBinding.downLoadingViewgroup.setProgress(progress);
                        }
                    }
                    if (!mBinding.loading.isStop()) {
                        if (mBinding.loading.getStatus() == Down360LoadingView.Status.Load) {
                            progress++;
                            Log.d(TAG, "DownActivity:" + progress);
                            mBinding.loading.setProgress(progress);
                        }
                    }
                });
            }
        }, 0, 500);
        mBinding.downLoadingViewgroup.setOnProgressStateChangeListener(this);


        mBinding.loading.setOnProgressStateChangeListener(this);

        findViewById(R.id.cancel).setOnClickListener(v -> mBinding.loading.setCancel());

        findViewById(R.id.stop).setOnClickListener(v -> {
            boolean stop = mBinding.loading.isStop();
            mBinding.loading.setStop(!stop);
        });
    }

    @Override
    public void onSuccess() {
        timer.cancel();
        ToastUtils.showToast(this, "下载完成");
    }

    @Override
    public void onStopDown() {
        ((Button) findViewById(R.id.stop)).setText("继续");
    }

    @Override
    public void onCancel() {
        progress = 0;

        ((Button) findViewById(R.id.stop)).setText("暂停");
    }

    @Override
    public void onContinue() {
        ((Button) findViewById(R.id.stop)).setText("暂停");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
