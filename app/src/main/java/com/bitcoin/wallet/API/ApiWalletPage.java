package com.bitcoin.wallet.API;

import com.bitcoin.wallet.APIDTO.WalletPageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWalletPage {

    @GET("firstpage")
    Call<WalletPageResponse> walletPageRequest(@Query("appsession") String email);
}
