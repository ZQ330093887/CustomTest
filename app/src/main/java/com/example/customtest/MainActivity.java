package com.example.customtest;

import android.content.Intent;
import android.os.Bundle;

import com.example.customtest.databinding.ActivityMainBinding;
import com.example.customtest.viewmodel.MainViewModel;
import com.example.customtest.vo.ActivityItem;
import com.hivescm.commonbusiness.adapter.SimpleCommonRecyclerAdapter;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;
import com.hivescm.commonbusiness.util.RecyclerUtils;
import com.hivescm.commonbusiness.widget.RecyclerGridSpace;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements Injectable {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerUtils.initLinearLayoutVertical(mBinding.recyclerList);
        mBinding.recyclerList.addItemDecoration(new RecyclerGridSpace(10, getResources().getColor(R.color.colorPrimary)));
        mToolbar.setNavigation(false);

        SimpleCommonRecyclerAdapter<ActivityItem> simpleCommonRecyclerAdapter = new SimpleCommonRecyclerAdapter<>(R.layout.item_activity, BR.item);
        simpleCommonRecyclerAdapter.setOnItemClickListener((view, position) -> {
            ActivityItem activityItem = simpleCommonRecyclerAdapter.getItem(position);
            try {
                Intent intent = new Intent(view.getContext(), Class.forName(activityItem.getName()));
                view.getContext().startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        mBinding.recyclerList.setAdapter(simpleCommonRecyclerAdapter);
        mViewModel.getUsers(this).observe(this, o -> {
            //
            simpleCommonRecyclerAdapter.add((ActivityItem) o);
        });
    }
}
