package com.jimmy.customviewdemo.retrofit.request;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jinguochong on 16-10-18.
 */

public interface YouKnowService {
    @GET("{month}/{date}")
    Call<ResponseBody> getYouKownList(@Path("month") int month, @Path("date") int date);
}
