package com.bitcoin.wallet.API;

import com.bitcoin.wallet.APIDTO.CreateWalletResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiExchangeBTC {
    @GET("exchange/")
    Call<CreateWalletResponse> exchangeBTC(@Query("address") String address,
                                           @Query("appsession") String token,
                                           @Query("price") String price,
                                           @Query("crypto") String crypto);
}
