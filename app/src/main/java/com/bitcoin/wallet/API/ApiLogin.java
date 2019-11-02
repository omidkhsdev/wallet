package com.bitcoin.wallet.API;

import com.bitcoin.wallet.APIDTO.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiLogin {
    @GET("register")
    Call<ServerResponse> login(
            @Query("email") String email,
            @Query("password") String password
    );
}
