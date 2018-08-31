package com.example.customtest;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.customtest.adapter.SimpleRecyclerViewAdapter;
import com.example.customtest.databinding.ActivityMainBinding;
import com.example.customtest.widget.statuslayoutmanage.OnStatusChildClickListener;
import com.example.customtest.widget.statuslayoutmanage.StatusLayoutManager;
import com.hivescm.common.widget.ToastUtils;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements Injectable {


    private StatusLayoutManager statusLayoutManager;
    private SimpleRecyclerViewAdapter mAdapter;

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
        setupRecyclerView();
        initRefreshLayout();
        initStatusLayoutManager();

        statusLayoutManager.showLoadingLayout();
        refreshData();
    }


    private void initRefreshLayout() {
        mBinding.refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mBinding.refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mBinding.refreshLayout.setOnRefreshListener(rf -> rf.getLayout().postDelayed(() -> {
            mAdapter.loadMore();
            statusLayoutManager.showSuccessLayout();
            rf.finishRefresh();
        }, 1000));

        mBinding.refreshLayout.setOnLoadMoreListener(rf -> rf.getLayout().postDelayed(() -> {
            if (mAdapter.getItemCount() > 30) {
                ToastUtils.showToast(MainActivity.this, "数据全部加载完毕");
                rf.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
            } else {
                mAdapter.loadMore();
                rf.finishLoadMore();
            }
        }, 2000));
    }

    private void initStatusLayoutManager() {
        statusLayoutManager = new StatusLayoutManager.Builder(mBinding.refreshLayout)
                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        ToastUtils.showToast(MainActivity.this, "空数据状态布局");
                        statusLayoutManager.showLoadingLayout();
                        refreshData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        ToastUtils.showToast(MainActivity.this, "出错状态布局");
                        statusLayoutManager.showLoadingLayout();
                        refreshData();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }
                })
                .build();
    }

    private void refreshData() {
        mBinding.refreshLayout.postDelayed(() -> {
            mAdapter.loadMore();
            mBinding.refreshLayout.finishRefresh();
            statusLayoutManager.showSuccessLayout();
        }, 1000);
    }

    private void setupRecyclerView() {
        mAdapter = new SimpleRecyclerViewAdapter();
        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(mBinding.rvContent.getContext()));
        mBinding.rvContent.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvContent.addItemDecoration(new DividerItemDecoration(mBinding.rvContent.getContext(), LinearLayout.VERTICAL));
        mBinding.rvContent.setAdapter(mAdapter);
    }
}
