package com.example.customtest.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.customtest.R;
import com.example.customtest.adapter.GridViewChannelAdapter;
import com.example.customtest.databinding.ActivityCheckBoxBinding;
import com.example.customtest.viewmodel.MainViewModel;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

/**
 * 自定义checkbox
 */

public class CheckBoxActivity extends BaseActivity<MainViewModel, ActivityCheckBoxBinding>
        implements Injectable {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_box;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.check1.setOnClickListener(view -> mBinding.check1.toggle());
        mBinding.check2.setOnClickListener(view -> mBinding.check2.toggle());
        mBinding.check3.setOnClickListener(view -> mBinding.check3.toggle());
        mBinding.check4.setOnClickListener(view -> mBinding.check4.toggle());


        List<String> list = new ArrayList<>();
        list.add("Hot");
        list.add("Hot");
        list.add("Hot");
        list.add("Hot");
        list.add("Hot");
        list.add("Hot");
        list.add("Hot");
        list.add("Hot");
        mBinding.grid.setAdapter(new GridViewChannelAdapter(CheckBoxActivity.this, list));


        List<String> labels = new ArrayList<>();
        labels.add("花哪儿记账");
        labels.add("给未来写封信");
        labels.add("密码键盘");
        labels.add("抬手唤醒");
        labels.add("Cutisan");
        labels.add("记-专注创作");
        labels.add("我也不知道我是谁");
        labels.add("崩崩崩");
        labels.add("Android");
        labels.add("开发");

        mBinding.stackLabelView.setLabels(labels);
        mBinding.stackLabelView.setOnLabelClickListener((index, v, s) -> {
            if (mBinding.switchDelete.isChecked()) {
                labels.remove(index);
                mBinding.stackLabelView.setLabels(labels);
            } else {
                Toast.makeText(CheckBoxActivity.this, "点击了：" + s, Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.switchDelete.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //
            mBinding.stackLabelView.setDeleteButton(isChecked);
        });

        mBinding.btnAdd.setOnClickListener(v -> {
            String s = mBinding.editAdd.getText().toString().trim();
            if (!s.isEmpty()) {
                labels.add(s);
                mBinding.stackLabelView.setLabels(labels);
            }
        });
    }
}
