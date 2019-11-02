package com.bitcoin.wallet.APIDTO;

import com.google.gson.annotations.SerializedName;

public class WalletPageResponse {

    @SerializedName("price")
    private String price;
    @SerializedName("addresses")
    private String addresses;
    @SerializedName("balance")
    private String balance;
    @SerializedName("privatekey")
    private String privatekey;


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }


}
