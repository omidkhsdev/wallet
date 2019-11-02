package com.bitcoin.wallet.API;

import com.bitcoin.wallet.APIDTO.CreateWalletResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWallet {

    @GET("createbtc")
    Call<CreateWalletResponse> createWallet(@Query("appsession") String email);

    @GET("importwallet")
    Call<CreateWalletResponse> importWallet(@Query("appsession") String email,
                                            @Query("privatekey") String privateKet);
}
