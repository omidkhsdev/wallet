package com.bitcoin.wallet.API;

import com.bitcoin.wallet.APIDTO.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiForgetPassword {

    @GET("forget")
    Call<ServerResponse> forgetPassword(
            @Query("email") String email);
}
