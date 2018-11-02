package com.example.customtest.api;

import com.example.customtest.api.vo.ArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by ZQiong on 2018/9/30.
 */
public interface ApiService {
    @GET("article/list")
    Observable<ArticleBean> getArticleList();
}
