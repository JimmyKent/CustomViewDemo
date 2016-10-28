package com.jimmy.customviewdemo.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.retrofit.bean.MeizhiData;
import com.jimmy.customviewdemo.retrofit.request.RetGankMeizhi;
import com.jimmy.customviewdemo.retrofit.request.YouKnowService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * http://gank.io/api/search/query/listview/category/Android/count/10/page/1
 * 注：
 * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
 * count 最大 50
 */
public class RetrofitActivity extends AppCompatActivity {

    private String TEST_URL = "http://gank.io/api/data/Android/";

    private int month = 10;
    private int date = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)//baseUlr 必须以 /（斜线） 结束，不然会抛出一个IllegalArgumentException
                .build();*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .build();

        YouKnowService youKnowService = retrofit.create(YouKnowService.class);

        Call<ResponseBody> call = youKnowService.getYouKownList(month, date);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("onResponse", "response:"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });

    }
}
