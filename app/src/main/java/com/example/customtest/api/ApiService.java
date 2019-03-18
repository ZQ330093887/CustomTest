package com.example.customtest.api;

import com.example.customtest.api.vo.ArticleBean;
import com.example.customtest.api.vo.BaseResponse;
import com.example.customtest.vo.GanHuoDataBean;
import com.hivescm.commonbusiness.api.ApiResponse;
import com.hivescm.commonbusiness.util.ApiCallback;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ZQiong on 2018/9/30.
 */
public interface ApiService {
    @GET("article/list")
    Observable<ArticleBean> getArticleList();

    //http://gank.io/api/data/all/20/2

    /***
     * 根据类别查询干货
     */
    @GET("data/{category}/20/{pageIndex}")
    Observable<BaseResponse<List<GanHuoDataBean>>> getGanHuo(
            @Path("category") String category,
            @Path("pageIndex") int pageIndex);

}
