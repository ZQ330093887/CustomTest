package com.example.customtest.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.customtest.BR;
import com.example.customtest.R;
import com.example.customtest.api.ApiManager;
import com.example.customtest.api.ApiService;
import com.example.customtest.api.RequestCallBack;
import com.example.customtest.api.vo.BaseResponse;
import com.example.customtest.databinding.ActivityTestBinding;
import com.example.customtest.viewmodel.MainViewModel;
import com.example.customtest.vo.GanHuoDataBean;
import com.example.customtest.widget.listener.EndlessRecyclerOnScrollListener;
import com.example.customtest.widget.statuslayoutmanage.OnStatusChildClickListener;
import com.example.customtest.widget.statuslayoutmanage.StatusLayoutManager;
import com.hivescm.commonbusiness.adapter.SimpleCommonRecyclerAdapter;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;
import com.hivescm.commonbusiness.util.RecyclerUtils;
import com.hivescm.commonbusiness.util.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TestActivity extends BaseActivity<MainViewModel, ActivityTestBinding>
        implements Injectable {

    private StatusLayoutManager statusLayoutManager;
    private SimpleCommonRecyclerAdapter<GanHuoDataBean> recyclerAdapter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView();
        initStatusLayoutManager();
        loadingData(true, page);


    }


    private void initStatusLayoutManager() {
        statusLayoutManager = new StatusLayoutManager.Builder(mBinding.refreshLayout)
                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }
                })
                .build();


        statusLayoutManager.showLoadingLayout();
    }


    private void setupRecyclerView() {
        RecyclerUtils.initLinearLayoutVertical(mBinding.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerList.setLayoutManager(layoutManager);
        recyclerAdapter = new SimpleCommonRecyclerAdapter<>(R.layout.item_test_layout, BR.dataBean);
        mBinding.recyclerList.setAdapter(recyclerAdapter);

        mBinding.recyclerList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadingData(true, page);
            }

            @Override
            public void onLoadMore() {
//                page++;
                Log.e("loadMore", String.valueOf(page));
//                loadingData(false, page);
            }
        });


        mBinding.recyclerList.addOnScrollListener(
                new EndlessRecyclerOnScrollListener(layoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        Log.e("好", String.valueOf(current_page));
                        loadingData(false, current_page);
                    }
                });
    }


    private void loadingData(boolean isRefresh, int page) {
        ApiService service = ApiManager.getInstance().create(ApiService.class, "http://gank.io/api/");
        ApiManager.request(service.getGanHuo("all", page),
                new RequestCallBack<BaseResponse<List<GanHuoDataBean>>>() {

                    @Override
                    public void success(BaseResponse<List<GanHuoDataBean>> result) {
                        statusLayoutManager.showSuccessLayout();
                        Log.e("-------", "成功");
                        if (result.isError()) {
                            statusLayoutManager.showErrorLayout();
                        } else {
                            if (isRefresh) {
                                if (result.getResults() != null && result.getResults().size() > 0) {
                                    mBinding.recyclerList.refreshComplete();
                                    recyclerAdapter.replace(result.getResults());
                                } else {
                                    statusLayoutManager.showEmptyLayout();
                                }
                            } else {
                                if (result.getResults() != null && result.getResults().size() > 0) {
                                    mBinding.recyclerList.setNoMore(false);
                                    recyclerAdapter.add(result.getResults());
                                } else {
                                    mBinding.recyclerList.setNoMore(true);
                                }
                            }
                        }
                    }

                    @Override
                    public void error(String msg) {
                        statusLayoutManager.showErrorLayout();
                        ToastUtils.showToast(TestActivity.this, msg);
                    }
                });

    }
}
