package com.example.customtest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.customtest.R;
import com.example.customtest.adapter.SimpleRecyclerViewAdapter;
import com.example.customtest.databinding.ActivityLoadingTestBinding;
import com.example.customtest.viewmodel.MainViewModel;
import com.example.customtest.widget.catloading.CatLoadingView;
import com.example.customtest.widget.statuslayoutmanage.OnStatusChildClickListener;
import com.example.customtest.widget.statuslayoutmanage.StatusLayoutManager;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;
import com.hivescm.commonbusiness.util.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class LoadingActivity extends BaseActivity<MainViewModel, ActivityLoadingTestBinding> implements Injectable {


    private StatusLayoutManager statusLayoutManager;
    private SimpleRecyclerViewAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading_test;
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
//        mBinding.refreshLayout.setRefreshHeader(new ClassicsHeader(this));
//        mBinding.refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        mBinding.rvContent.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mBinding.llRoot.postDelayed(() -> {
                    mAdapter.loadMore();
                    statusLayoutManager.showSuccessLayout();
                    mBinding.rvContent.refreshComplete();
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mBinding.llRoot.postDelayed(() -> {
                    if (mAdapter.getItemCount() > 30) {
                        ToastUtils.showToast(LoadingActivity.this, "数据全部加载完毕");
                        mBinding.rvContent.setNoMore(true);//将不会再次触发加载更多事件

                    } else {
                        mAdapter.loadMore();
                        mBinding.rvContent.loadMoreComplete();
                    }
                }, 2000);
            }
        });
    }

    private void initStatusLayoutManager() {
        statusLayoutManager = new StatusLayoutManager.Builder(mBinding.rvContent)
                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        ToastUtils.showToast(LoadingActivity.this, "空数据状态布局");
                        statusLayoutManager.showLoadingLayout();
                        refreshData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        ToastUtils.showToast(LoadingActivity.this, "出错状态布局");
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
        mBinding.llRoot.postDelayed(() -> {
            mAdapter.loadMore();
            mBinding.rvContent.refreshComplete();
            statusLayoutManager.showSuccessLayout();
        }, 1000);
    }

    private void setupRecyclerView() {
        mAdapter = new SimpleRecyclerViewAdapter();
        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(mBinding.rvContent.getContext()));
        mBinding.rvContent.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvContent.addItemDecoration(new DividerItemDecoration(mBinding.rvContent.getContext(), LinearLayout.VERTICAL));
        mBinding.rvContent.setAdapter(mAdapter);

        mAdapter.setCallBackListener(v -> showDialog());
    }


    public void showDialog() {
        new CatLoadingView().show(getSupportFragmentManager(), "");
    }
}
