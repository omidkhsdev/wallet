package com.bitcoin.wallet.APIDTO;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    @SerializedName("token")
    private String  token;
    @SerializedName("havewallet")
    private boolean hasWallet;
    @SerializedName("exists")
    private boolean exists;
    @SerializedName("sent")
    private boolean sent;

    public boolean getHasWallet() {
        return hasWallet;
    }

    public void setHasWallet(boolean hasWallet) {
        this.hasWallet = hasWallet;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
