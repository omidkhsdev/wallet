package com.bitcoin.wallet.ApiService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static Retrofit retrofit = null;


    public static Retrofit initRetrofit() {
        if (retrofit == null) {


            // Define the interceptor, add authentication headers
            Interceptor interceptor = chain -> {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(newRequest);
            };

            int timeOut = 5; //time out is 5 units
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor) // This is used to add ApplicationInterceptor.
                    .addNetworkInterceptor(interceptor) //This is used to add NetworkInterceptor.
                    .connectTimeout(timeOut, TimeUnit.SECONDS)
                    .readTimeout(timeOut, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();

            return retrofit;

        } else {
            return retrofit;
        }
    }
}