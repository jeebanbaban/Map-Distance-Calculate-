package com.jeeban.map.network;

import com.jeeban.map.util.AllUrls;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit=null;

    public static synchronized ApiInterface getClient(){
        if (null==ApiClient.retrofit){
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            Retrofit.Builder builder=new Retrofit.Builder()
                    .baseUrl(AllUrls.BASE_URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            ApiClient.retrofit= builder.build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
