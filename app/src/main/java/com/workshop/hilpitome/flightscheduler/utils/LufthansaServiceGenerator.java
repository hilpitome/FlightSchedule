package com.workshop.hilpitome.flightscheduler.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.workshop.hilpitome.flightscheduler.networking.LufthansaNetworkApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LufthansaServiceGenerator {
    private Retrofit retrofit;
    private LufthansaNetworkApi networkApi;

    public LufthansaServiceGenerator() {
        //  Client request
        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.connectTimeout(60, TimeUnit.SECONDS);
        b.readTimeout(60, TimeUnit.SECONDS);
        b.writeTimeout(60, TimeUnit.SECONDS);
        b.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        b.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Response response = null;
                try {
                    response = chain.proceed(chain.request());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(response.message());
                return response;
            }
        });

        OkHttpClient okHttpClient = b.build();


        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        networkApi = retrofit.create(LufthansaNetworkApi.class);

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public LufthansaNetworkApi getAPI() {
        return networkApi;
    }

    public LufthansaNetworkApi getNetworkAPI(final String authToken) {
        return networkApi;
    }
}
