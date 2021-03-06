package com.example.hao.hbut.model.api;

import com.example.hao.hbut.base.BaseActivity;
import com.example.hao.hbut.model.bean.Setting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hao on 2016/10/25.
 */

public class Network {

    private static HbutApi sHbutApi;
    private Retrofit retrofit;

    OkHttpClient okHttpClient_received = new OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.SECONDS)
            .addInterceptor(new ReceivedCookiesInterceptor())
            .build();
    OkHttpClient okHttpClient_add = new OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.SECONDS)
            .addInterceptor(new AddCookiesInterceptor())
            .build();

    Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    CallAdapter.Factory rxjavaCallAdapterFactory = RxJava2CallAdapterFactory.create();


    /*
    * 持久化cookie
    * 2016-10-25 16:21:49 by haohaozaici
    * */
    private HashSet<String> cookies = new HashSet<>();
    private Setting setting = BaseActivity.getSetting();

    public HbutApi getHbutApi(String instance) {

        switch (instance) {
            case HbutApi.Account_HOST:
                retrofit = new Retrofit.Builder()
                        .client(okHttpClient_received)
                        .baseUrl(HbutApi.Account_HOST)
                        .addConverterFactory(gsonConverterFactory)
                        .addCallAdapterFactory(rxjavaCallAdapterFactory)
                        .build();
                sHbutApi = retrofit.create(HbutApi.class);

                return sHbutApi;
            case HbutApi.StuGrade_HOST:
                retrofit = new Retrofit.Builder()
                        .client(okHttpClient_add)
                        .baseUrl(HbutApi.StuGrade_HOST)
//                    .addConverterFactory(gsonConverterFactory)
                        .addCallAdapterFactory(rxjavaCallAdapterFactory)
                        .build();
                sHbutApi = retrofit.create(HbutApi.class);

                return sHbutApi;
            case HbutApi.Schedule_Host:
                retrofit = new Retrofit.Builder()
                        .client(okHttpClient_add)
                        .baseUrl(HbutApi.Schedule_Host)
                        .addCallAdapterFactory(rxjavaCallAdapterFactory)
                        .build();
                sHbutApi = retrofit.create(HbutApi.class);

                return sHbutApi;
            case HbutApi.StuAllGrade_HOST:
                retrofit = new Retrofit.Builder()
                        .client(okHttpClient_add)
                        .baseUrl(HbutApi.StuAllGrade_HOST)
                        .addCallAdapterFactory(rxjavaCallAdapterFactory)
                        .build();
                sHbutApi = retrofit.create(HbutApi.class);

                return sHbutApi;
            case HbutApi.Jsoup_Host:
                retrofit = new Retrofit.Builder()
                        .client(okHttpClient_add)
                        .baseUrl(HbutApi.Jsoup_Host)
                        .addCallAdapterFactory(rxjavaCallAdapterFactory)
                        .build();
                sHbutApi = retrofit.create(HbutApi.class);

                return sHbutApi;
            default:
                return null;
        }
    }

    public class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());

            cookies.clear();
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }

                setting.setLogin(true);

                ArrayList<String> cookiesArray = new ArrayList<>();
                for (String cookie : cookies) {
                    cookie = cookie.substring(0, cookie.length() - 16);
                    cookiesArray.add(cookie);
                }
                int position = cookiesArray.size();
                String cookie2 = "";
                for (int i = 0; i < position; i++) {
                    String cookie = cookiesArray.get(position - i - 1);
                    cookie2 += cookie;
                }

                setting.setCookies(cookie2);

//                Log.e("login name + cookies", Setting.getUserName() + cookie2);
            }
            return originalResponse;
        }
    }

    public class AddCookiesInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

//            Log.e("get information name", Setting.getUserName());
            builder.addHeader("Cookie", setting.getCookies());
//            builder.addHeader("Cookie", "");
//            Log.e("OkHttp", "Adding Header: " + Setting.getCookies());

            // This is done so I know which headers are being added;
            // this interceptor is used after the normal logging of OkHttp
            return chain.proceed(builder.build());
        }
    }

}
