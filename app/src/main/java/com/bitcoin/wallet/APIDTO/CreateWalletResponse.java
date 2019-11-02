package com.bitcoin.wallet.APIDTO;

import com.google.gson.annotations.SerializedName;

public class CreateWalletResponse {
    @SerializedName("token")
    private String token;
    @SerializedName("segmentwitness")
    private String walletAddress;
    @SerializedName("privatekey")
    private String privateKey;
    @SerializedName("added")
    private boolean added;
    @SerializedName("transaction")
    private boolean transaction;


    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public boolean isTransaction() {
        return transaction;
    }

    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
    }
}
