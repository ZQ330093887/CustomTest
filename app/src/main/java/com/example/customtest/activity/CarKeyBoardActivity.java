package com.example.customtest.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customtest.R;
import com.example.customtest.databinding.ActivityCarKeyBoardBinding;
import com.example.customtest.utils.CarKeyboardUtil;
import com.example.customtest.viewmodel.MainViewModel;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class CarKeyBoardActivity extends BaseActivity<MainViewModel, ActivityCarKeyBoardBinding>
        implements Injectable, View.OnClickListener, View.OnTouchListener {

    private CarKeyboardUtil keyboardUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_key_board;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyboardUtil = new CarKeyboardUtil(this, mBinding.etPlateNumber);
        mBinding.btnSubmit.setEnabled(false);
        mBinding.etPlateNumber.setOnTouchListener(this);

        mBinding.etPlateNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                checkViewState(text);
                if (s.length() == 8) {
                    mBinding.rlPlateNumber.setBackgroundResource(R.drawable.btn_round_blue);
                    mBinding.imgIcon.setVisibility(View.VISIBLE);
                } else {
                    mBinding.rlPlateNumber.setBackgroundResource(R.drawable.btn_round_green);
                    mBinding.imgIcon.setVisibility(View.GONE);
                }
                if (text.contains("港") || text.contains("澳") || text.contains("学")) {
                    mBinding.etPlateNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
                } else {
                    mBinding.etPlateNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mBinding.btnSubmit.setOnClickListener(this);

        testArray();
    }

    //获取随机数
    private int[] array = new int[]{0, 2, 3, 4, 5, 6, 7, 8, 9};

    private void testArray() {
        TextView textView = findViewById(R.id.textView);
        String str = "";
        for (int i = 0; i < array.length; i++) {
            int index = new Random().nextInt(array.length);
            if (str.contains(array[index] + "")) {
                i--;
            } else {
                if (i == 4) {
                    str = str + " ";
                } else {
                    str = str + array[index];
                }
            }
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        textView.setText(str);
    }

    /**
     * 检查状态
     */
    private void checkViewState(String s) {
        if (s.length() >= 7) {
            mBinding.btnSubmit.setEnabled(true);
            return;
        }
        mBinding.btnSubmit.setEnabled(false);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.etPlateNumber:
                keyboardUtil.hideSystemKeyBroad();
                keyboardUtil.hideSoftInputMethod();
                if (!keyboardUtil.isShow())
                    keyboardUtil.showKeyboard();
                break;
            default:
                if (keyboardUtil.isShow())
                    keyboardUtil.hideKeyboard();
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (keyboardUtil.isShow()) {
            keyboardUtil.hideKeyboard();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit://添加车牌
                mBinding.etPlateNumber.setText("");
                mBinding.btnSubmit.setEnabled(false);
                break;
        }
    }


    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }
}
