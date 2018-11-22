package com.example.customtest.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.customtest.R;
import com.example.customtest.databinding.ActivityAnyLayerBinding;
import com.example.customtest.utils.StatusBarUtil;
import com.example.customtest.viewmodel.MainViewModel;
import com.example.customtest.widget.anylayer.AnimHelper;
import com.example.customtest.widget.anylayer.AnyLayer;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class AnyLayerActivity extends BaseActivity<MainViewModel, ActivityAnyLayerBinding>
        implements Injectable, View.OnClickListener {

    private static final long ANIM_DURATION = 350;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_any_layer;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.translucentStatusBar(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_show_full).setOnClickListener(this);
        findViewById(R.id.tv_show_top).setOnClickListener(this);
        findViewById(R.id.tv_show_top_view_group).setOnClickListener(this);
        findViewById(R.id.tv_show_target_right).setOnClickListener(this);
        findViewById(R.id.tv_show_target_top).setOnClickListener(this);
        findViewById(R.id.tv_show_target_bottom).setOnClickListener(this);
        findViewById(R.id.tv_show_target_left).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom).setOnClickListener(this);
        findViewById(R.id.tv_show_blur_bg).setOnClickListener(this);
        findViewById(R.id.tv_show_dark_bg).setOnClickListener(this);
        findViewById(R.id.tv_show_tran_bg).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom_in).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom_alpha_in).setOnClickListener(this);
        findViewById(R.id.tv_show_top_in).setOnClickListener(this);
        findViewById(R.id.tv_show_top_alpha_in).setOnClickListener(this);
        findViewById(R.id.tv_show_top_bottom).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom_top).setOnClickListener(this);
        findViewById(R.id.tv_show_top_bottom_alpha).setOnClickListener(this);
        findViewById(R.id.tv_show_bottom_top_alpha).setOnClickListener(this);
        findViewById(R.id.tv_show_left_in).setOnClickListener(this);
        findViewById(R.id.tv_show_left_alpha_in).setOnClickListener(this);
        findViewById(R.id.tv_show_right_in).setOnClickListener(this);
        findViewById(R.id.tv_show_right_alpha_in).setOnClickListener(this);
        findViewById(R.id.tv_show_left_right).setOnClickListener(this);
        findViewById(R.id.tv_show_right_left).setOnClickListener(this);
        findViewById(R.id.tv_show_left_right_alpha).setOnClickListener(this);
        findViewById(R.id.tv_show_right_left_alpha).setOnClickListener(this);
        findViewById(R.id.tv_show_reveal).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_show_full:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_1)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .onClickToDismiss(R.id.iv_1)
                        .show();
                break;
            case R.id.tv_show_top:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_3)
                        .backgroundColorRes(R.color.dialog_bg)
                        .gravity(Gravity.TOP)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v143) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_top_view_group:
                AnyLayer.with(mBinding.flContent)
                        .contentView(R.layout.dialog_test_3)
                        .backgroundColorRes(R.color.dialog_bg)
                        .gravity(Gravity.TOP)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v1) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_target_right:
                AnyLayer.target(findViewById(R.id.tv_show_target_right))
                        .direction(AnyLayer.Direction.RIGHT)
                        .contentView(R.layout.dialog_test_5)
                        .gravity(Gravity.LEFT | Gravity.CENTER_VERTICAL)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_target_left:
                AnyLayer.target(findViewById(R.id.tv_show_target_left))
                        .direction(AnyLayer.Direction.LEFT)
                        .contentView(R.layout.dialog_test_5)
                        .gravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_target_top:
                AnyLayer.target(findViewById(R.id.tv_show_target_top))
                        .direction(AnyLayer.Direction.TOP)
                        .contentView(R.layout.dialog_test_4)
                        .backgroundColorRes(R.color.dialog_bg)
                        .gravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_target_bottom:
                AnyLayer.target(findViewById(R.id.tv_show_target_bottom))
                        .direction(AnyLayer.Direction.BOTTOM)
                        .contentView(R.layout.dialog_test_4)
                        .backgroundColorRes(R.color.dialog_bg)
                        .gravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .show();
                break;
            case R.id.tv_show_bottom:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_3)
                        .backgroundColorRes(R.color.dialog_bg)
                        .gravity(Gravity.BOTTOM)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v142) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_blur_bg:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundBlurRadius(8)
                        .backgroundBlurScale(8)
                        .backgroundColorInt(Color.WHITE)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v140) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v141) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_dark_bg:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v139) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v138) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_tran_bg:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v137) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v136) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_bottom_in:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v135) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v134) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_bottom_alpha_in:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v133) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v132) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_top_in:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v130) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v131) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_top_alpha_in:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v128) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v129) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_top_bottom:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v127) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v126) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_bottom_top:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v125) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v124) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_top_bottom_alpha:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startTopAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startBottomAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v123) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v122) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_bottom_top_alpha:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startBottomAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startTopAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v121) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v120) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_left_in:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v119) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v118) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_left_alpha_in:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v117) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v116) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_right_in:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v115) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v114) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_right_alpha_in:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v113) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v112) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_left_right:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v111) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v110) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_right_left:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v19) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v18) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_left_right_alpha:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startLeftAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startRightAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v17) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v16) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_right_left_alpha:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                AnimHelper.startRightAlphaInAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                AnimHelper.startLeftAlphaOutAnim(content, ANIM_DURATION);
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v15) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v14) -> AnyLayer.dismiss())
                        .show();
                break;
            case R.id.tv_show_reveal:
                AnyLayer.with(AnyLayerActivity.this)
                        .contentView(R.layout.dialog_test_2)
                        .backgroundColorRes(R.color.dialog_bg)
                        .cancelableOnTouchOutside(true)
                        .cancelableOnClickKeyBack(true)
                        .contentAnim(new AnyLayer.IAnim() {
                            @Override
                            public long inAnim(View content) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    AnimHelper.startCircularRevealInAnim(content, content.getMeasuredWidth() / 2, content.getMeasuredHeight() / 2, ANIM_DURATION);
                                }
                                return ANIM_DURATION;
                            }

                            @Override
                            public long outAnim(View content) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    AnimHelper.startCircularRevealOutAnim(content, content.getMeasuredWidth() / 2, content.getMeasuredHeight() / 2, ANIM_DURATION);
                                }
                                return ANIM_DURATION;
                            }
                        })
                        .onClick(R.id.fl_dialog_no, (AnyLayer, v13) -> AnyLayer.dismiss())
                        .onClick(R.id.fl_dialog_yes, (AnyLayer, v12) -> AnyLayer.dismiss())
                        .show();
                break;
        }
    }
}