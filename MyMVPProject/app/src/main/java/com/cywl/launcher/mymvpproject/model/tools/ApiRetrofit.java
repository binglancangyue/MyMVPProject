package com.cywl.launcher.mymvpproject.model.tools;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.ContentValues.TAG;

public class ApiRetrofit {
    private Retrofit mRetrofit;
    private OkHttpClient mClient;
    //baseUrl
    public final static String API_SERVER_URL = "https://api.github.com/";

    private ApiRetrofit() {
        mClient = new OkHttpClient.Builder()
                //添加log拦截器
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mClient)
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private static class SingletonHolder {
        private static final ApiRetrofit INSTANCE = new ApiRetrofit();
    }

    public static ApiRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.e(TAG, "----------Request Start----------------");
            Log.e(TAG, "| " + request.toString() + request.headers().toString());
            Log.e(TAG, "| Response:" + content);
            Log.e(TAG, "----------Request End:" + duration + "毫秒----------");
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    };

}
