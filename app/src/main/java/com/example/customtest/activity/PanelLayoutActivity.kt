package com.example.customtest.activity

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.customtest.R
import com.example.customtest.databinding.ActivityPanelLayoutBinding
import com.example.customtest.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.hivescm.commonbusiness.base.BaseActivity
import com.hivescm.commonbusiness.di.Injectable

class PanelLayoutActivity : BaseActivity<MainViewModel, ActivityPanelLayoutBinding>(), Injectable {
    override fun getViewModel(): MainViewModel {
        return ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_panel_layout
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        mBinding.mainContent.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        mBinding.mainContent.tabLayout.addTab(mBinding.mainContent.tabLayout.newTab().setText("费用说明"))
        mBinding.mainContent.tabLayout.addTab(mBinding.mainContent.tabLayout.newTab().setText("预定须知"))
        mBinding.mainContent.tabLayout.addTab(mBinding.mainContent.tabLayout.newTab().setText("退款政策"))
        mBinding.mainContent.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0!!.position) {
                    1 -> mBinding.mainContent.frameLayout.setBackgroundColor(Color.parseColor("#ff0000"))
                    2 -> mBinding.mainContent.frameLayout.setBackgroundColor(Color.parseColor("#0000ff"))
                    3 -> mBinding.mainContent.frameLayout.setBackgroundColor(Color.parseColor("#00ff00"))
                }

            }
        })

        mBinding.mainContent.nestedScrollView.animate().translationY(-150f).alpha(1.0f).duration = 500
//        mBinding.snpl.setStateCallback(object : StateCallback {
//            override fun onExpandedState() {
//                Log.i("-->", "onExpandedState")
//            }
//
//            override fun onCollapsedState() {
//                Log.i("-->", "onCollapsedState");
//            }
//
//        })
    }

}