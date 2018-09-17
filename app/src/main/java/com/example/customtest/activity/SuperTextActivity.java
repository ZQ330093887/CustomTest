package com.example.customtest.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.customtest.R;
import com.example.customtest.databinding.ActivitySuperTextBinding;
import com.example.customtest.viewmodel.MainViewModel;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class SuperTextActivity extends BaseActivity<MainViewModel, ActivitySuperTextBinding> implements Injectable {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_super_text;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url1 = "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3860616424,1789830124&fm=80&w=179&h=119&img.PNG";
        String url2 = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=219781665,3032880226&fm=80&w=179&h=119&img.JPEG";
        String url3 = "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3860616424,1789830124&fm=80&w=179&h=119&img.PNG";
        String url4 = "http://osnoex6vf.bkt.clouddn.com/original_label.png";


        Glide.with(this)
                .load(url1)
                .placeholder(R.drawable.head_default)
                .fitCenter()
                .into(mBinding.sp0.getLeftIconIV());
        Picasso.with(this)
                .load(url1)
                .placeholder(R.drawable.head_default)
                .into(mBinding.tv1.getLeftIconIV());
        Glide.with(this).load(url2).placeholder(R.drawable.head_default)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        commonTextView.setLeftDrawableLeft(resource);
                        mBinding.tv1.getLeftIconIV().setImageDrawable(resource);
                    }
                });
        Glide.with(this)
                .load(url2)
                .placeholder(R.drawable.head_default)
                .fitCenter()
                .into(mBinding.superTv2.getRightIconIV());
    }
}
