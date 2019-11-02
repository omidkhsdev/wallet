package com.bitcoin.wallet.API;

import com.bitcoin.wallet.APIDTO.CreateWalletResponse;
import com.bitcoin.wallet.APIDTO.TransactionsDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiTransaction {
    @GET("https://blockchain.info/rawaddr/{walletAddress}")
    Call<TransactionsDTO> getTransActions(@Path("walletAddress") String walletAddress);

    @GET("sendtransactions")
    Call<CreateWalletResponse> sendTransactions(@Query("appsession") String token,
                                                @Query("distadress") String walletAddress,
                                                @Query("amount")String amount);
}
