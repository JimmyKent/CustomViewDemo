package com.jimmy.customviewdemo.retrofit.request;


import com.jimmy.customviewdemo.retrofit.bean.MeizhiData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jinguochong on 16-10-18.
 */
//http://gank.io/api/data/福利/10/1
public interface RetGankMeizhi {
    @GET("data/福利/10/{page}")
    Call<MeizhiData> getMeizhiList(@Path("page") int page);
}