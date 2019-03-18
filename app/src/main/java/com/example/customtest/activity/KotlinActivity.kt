package com.example.customtest.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customtest.BR
import com.example.customtest.R
import com.example.customtest.api.ApiManager
import com.example.customtest.api.ApiService
import com.example.customtest.api.RequestCallBack
import com.example.customtest.api.vo.BaseResponse
import com.example.customtest.databinding.ActivityTestBinding
import com.example.customtest.viewmodel.MainViewModel
import com.example.customtest.vo.GanHuoDataBean
import com.example.customtest.widget.listener.EndlessRecyclerOnScrollListener
import com.example.customtest.widget.statuslayoutmanage.OnStatusChildClickListener
import com.example.customtest.widget.statuslayoutmanage.StatusLayoutManager
import com.hivescm.commonbusiness.adapter.SimpleCommonRecyclerAdapter
import com.hivescm.commonbusiness.base.BaseActivity
import com.hivescm.commonbusiness.di.Injectable
import com.hivescm.commonbusiness.util.RecyclerUtils
import com.hivescm.commonbusiness.util.ToastUtils
import com.jcodecraeer.xrecyclerview.XRecyclerView

/**
 * Created by ZQiong on 2019/3/18.
 */
class KotlinActivity : BaseActivity<MainViewModel, ActivityTestBinding>(), Injectable {

    private var recyclerAdapter: SimpleCommonRecyclerAdapter<GanHuoDataBean>? = null
    private var statusLayoutManager: StatusLayoutManager? = null

    private var page: Int = 0


    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun getViewModel(): MainViewModel {
        return ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        initStatusLayoutManager()
        loadingData(true, page, this)
    }

    private fun loadingData(isRefresh: Boolean, page: Int, context: Context) {
        val service = ApiManager.getInstance().create(ApiService::class.java, "http://gank.io/api/")
        ApiManager.request(service.getGanHuo("all", page),
                object : RequestCallBack<BaseResponse<List<GanHuoDataBean>>> {
                    override fun success(result: BaseResponse<List<GanHuoDataBean>>?) {
                        statusLayoutManager!!.showSuccessLayout()
                        Log.e("-------", "成功")
                        if (result!!.isError) {
                            statusLayoutManager!!.showErrorLayout()
                        } else {
                            if (isRefresh) {
                                if (result.results != null && result.results.isNotEmpty()) {
                                    mBinding.recyclerList.refreshComplete()
                                    recyclerAdapter!!.replace(result.results)
                                } else {
                                    statusLayoutManager!!.showEmptyLayout()
                                }
                            } else {
                                if (result.results != null && result.results.isNotEmpty()) {
                                    mBinding.recyclerList.setNoMore(false)
                                    recyclerAdapter!!.add(result.results)
                                } else {
                                    mBinding.recyclerList.setNoMore(true)
                                }
                            }
                        }
                    }

                    override fun error(msg: String?) {
                        statusLayoutManager!!.showErrorLayout()
                        ToastUtils.showToast(context, msg)
                    }
                })

    }

    private fun setupRecyclerView() {
        RecyclerUtils.initLinearLayoutVertical(mBinding.recyclerList)
        val layoutManager = LinearLayoutManager(this)
        mBinding.recyclerList.layoutManager = layoutManager
        recyclerAdapter = SimpleCommonRecyclerAdapter(R.layout.item_test_layout, BR.dataBean)
        mBinding.recyclerList.adapter = recyclerAdapter

        mBinding.recyclerList.addOnScrollListener(
                object : EndlessRecyclerOnScrollListener(layoutManager) {
                    override fun onLoadMore(current_page: Int) {
                        Log.e("好", current_page.toString())
                        loadingData(false, current_page, this@KotlinActivity)
                    }
                })

        mBinding.recyclerList.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                page = 1
                loadingData(true, page, this@KotlinActivity)
            }

            override fun onLoadMore() {
                Log.e("loadMore", page.toString())
            }

        })

    }

    private fun initStatusLayoutManager() {
        statusLayoutManager = StatusLayoutManager.Builder(mBinding.refreshLayout)
                // 设置重试事件监听器
                .setOnStatusChildClickListener(object : OnStatusChildClickListener {
                    override fun onEmptyChildClick(view: View) {
                        statusLayoutManager!!.showLoadingLayout()
                    }

                    override fun onErrorChildClick(view: View) {
                        statusLayoutManager!!.showLoadingLayout()
                    }

                    override fun onCustomerChildClick(view: View) {

                    }
                })
                .build()


        statusLayoutManager!!.showLoadingLayout()
    }
}