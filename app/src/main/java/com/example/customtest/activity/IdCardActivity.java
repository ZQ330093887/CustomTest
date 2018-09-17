package com.example.customtest.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.anrongtec.ocr.PersonActivity;
import com.bumptech.glide.Glide;
import com.example.customtest.R;
import com.example.customtest.databinding.ActivityIdCardBinding;
import com.example.customtest.viewmodel.MainViewModel;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class IdCardActivity extends BaseActivity<MainViewModel, ActivityIdCardBinding> implements Injectable {

    public static final int REQUEST_PERSON_CODE = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_card;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void scanIdCard(View view) {
        Intent intent = new Intent(this, PersonActivity.class);
        startActivityForResult(intent, REQUEST_PERSON_CODE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERSON_CODE && resultCode == RESULT_OK) {
            String recogResult = data.getStringExtra("recogResult");
            String recogName = data.getStringExtra("recogName");
            String recogSex = data.getStringExtra("recogSex");
            String recogBorn = data.getStringExtra("recogBorn");
            String recogAdress = data.getStringExtra("recogAdress");
            String recogNation = data.getStringExtra("recogNation");
            String recogHead = data.getStringExtra("recogHead");

            Glide.with(this).load(recogHead).into((ImageView) findViewById(R.id.iv_head));
            mBinding.tvMessage.setText(
                    "身份证号：" + recogResult
                            + "\n姓     名：" + recogName
                            + "\n性     别：" + recogSex
                            + "\n名     族：" + recogNation
                            + "\n出生年月：" + recogBorn
                            + "\n家庭住址：" + recogAdress);
        }

    }

}
