package com.example.customtest.activity;

import android.animation.IntEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.customtest.R;
import com.example.customtest.api.vo.ArticleBean;
import com.example.customtest.databinding.ActivitySearchBinding;
import com.example.customtest.utils.StatusBarUtil;
import com.example.customtest.viewmodel.MainViewModel;
import com.example.customtest.widget.search.DensityUtil;
import com.example.customtest.widget.search.ObservableScrollView;
import com.example.customtest.widget.search.ScrollViewListener;
import com.gyf.barlibrary.ImmersionBar;
import com.hivescm.commonbusiness.base.BaseActivity;
import com.hivescm.commonbusiness.di.Injectable;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class SearchActivity extends BaseActivity<MainViewModel, ActivitySearchBinding> implements Injectable {

    private static final float ENDMARGINLEFT = 50;
    private static final float ENDMARGINTOP = 5;
    private static final float STARTMARGINLEFT = 20;
    private static final float STARTMARGINTOP = 140;

    private int scrollLength;//顶部栏从透明变成不透明滑动的距离
    private ImmersionBar immersionBar;
    private ArrayList<ArticleBean.DataBean> beanArrayList = new ArrayList<>();
    private searchAdapter searchAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immersionBar = ImmersionBar.with(SearchActivity.this);
        immersionBar.statusBarColor(R.color.red).statusBarDarkFont(true, 0.2F).init();
        getSupportActionBar().hide();
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this, true);
        searchAdapter = new searchAdapter(this, beanArrayList);
        mBinding.lvSearchview.setAdapter(searchAdapter);
        mBinding.svSearch.smoothScrollTo(0, 0);

        mBinding.svSearch.setScrollViewListener(new ScrollViewListener() {

            private int evaluatemargin;
            private int evaluatetop;
            private FrameLayout.LayoutParams layoutParams;

            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

                int abs_y = Math.abs(y);
                //滑动距离小于顶部栏从透明到不透明所需的距离
                if ((scrollLength - abs_y) > 0) {
                    ImmersionBar.with(SearchActivity.this).statusBarColor(R.color.red).statusBarDarkFont(true, 0.2F).init();
                    //估值器
                    IntEvaluator evaluator = new IntEvaluator();
                    float percent = (float) (scrollLength - abs_y) / scrollLength;

                    if (percent <= 1) {

                        //透明度
                        int evaluate = evaluator.evaluate(percent, 255, 0);
                        mBinding.rvBar.getBackground().setAlpha(evaluate);
                        //搜索栏左右margin值
                        evaluatemargin = evaluator.evaluate(percent, DensityUtil.dip2px(SearchActivity.this, ENDMARGINLEFT), DensityUtil.dip2px(SearchActivity.this, STARTMARGINLEFT));
                        //搜索栏顶部margin值
                        evaluatetop = evaluator.evaluate(percent, DensityUtil.dip2px(SearchActivity.this, ENDMARGINTOP), DensityUtil.dip2px(SearchActivity.this, STARTMARGINTOP));

                        layoutParams = (FrameLayout.LayoutParams) mBinding.rvSearch.getLayoutParams();
                        layoutParams.setMargins(evaluatemargin, evaluatetop, evaluatemargin, 0);
                        mBinding.rvSearch.requestLayout();
                        immersionBar.statusBarColor(R.color.red).init();
                    }

                } else {
                    mBinding.rvBar.getBackground().setAlpha(255);
                    if (layoutParams != null) {
                        layoutParams.setMargins(DensityUtil.dip2px(SearchActivity.this, ENDMARGINLEFT), DensityUtil.dip2px(SearchActivity.this, 5), DensityUtil.dip2px(SearchActivity.this, ENDMARGINLEFT), 0);
                        mBinding.rvSearch.requestLayout();
                        immersionBar.statusBarColor(R.color.btn_green).init();
                    }
                }
            }
        });

        new Thread(this::LoadingData).start();
    }

    private void LoadingData() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.170.14:8080/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//        ApiService service = retrofit.create(ApiService.class);
//        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        compositeDisposable.add(service.getArticleList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver() {
//                    @Override
//                    public void onNext(Object o) {
//                        if (o != null) {
//                            ArticleBean articleBean = (ArticleBean) o;
//                            beanArrayList.clear();
//                            beanArrayList.addAll(articleBean.getData());
//                            searchAdapter.notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        ToastUtils.showToast(SearchActivity.this, e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        ToastUtils.showToast(SearchActivity.this, "success");
//                    }
//                }));


        ArticleBean articleBean = new ArticleBean();
        ArrayList<ArticleBean.DataBean> dataBeanArrayList = new ArrayList<>();
        dataBeanArrayList.clear();
        for (int i = 0; i < 10; i++) {
            ArticleBean.DataBean dataBean = new ArticleBean.DataBean();
            dataBean.setCategoryName("成功的密码" + i);
            dataBean.setTitle("金秋岁月");
            dataBean.setSummary("激情岁月" + i);
            dataBeanArrayList.add(dataBean);
        }
        articleBean.setData(dataBeanArrayList);
        beanArrayList.clear();
        beanArrayList.addAll(articleBean.getData());
        searchAdapter.notifyDataSetChanged();


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        int height_rv = mBinding.rvBar.getHeight();
        int height_iv = mBinding.ivSearch.getHeight();

        scrollLength = Math.abs(height_iv - height_rv);

        //把顶部bar设置为透明
        mBinding.rvBar.getBackground().setAlpha(0);
    }

    class searchAdapter extends BaseAdapter {
        Context context;
        ArrayList<ArticleBean.DataBean> strings;
        private View view;
        private TextView tv_search;
        private TextView tv_detail;
        private TextView tv_content;

        public searchAdapter(Context context, ArrayList<ArticleBean.DataBean> strings) {
            this.context = context;
            this.strings = strings;
        }

        @Override
        public int getCount() {
            return strings.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                view = View.inflate(context, R.layout.layout_search, null);
                tv_search = view.findViewById(R.id.tv_search);
                tv_detail = view.findViewById(R.id.tv_detail);
                tv_content = view.findViewById(R.id.tv_content);
            } else {
                view = convertView;
            }

            tv_search.setText(strings.get(position).getTitle());
            tv_detail.setText(strings.get(position).getCategoryName());
            tv_content.setText(strings.get(position).getSummary());
            return view;
        }
    }


}
