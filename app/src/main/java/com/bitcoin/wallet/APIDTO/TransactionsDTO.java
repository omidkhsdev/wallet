package com.bitcoin.wallet.APIDTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class TransactionsDTO {

    @SerializedName("final_balance")
    private Integer final_balance;
    @SerializedName("address")
    private String address;
    @SerializedName("n_tx")
    private Integer n_tx;
    @SerializedName("total_sent")
    private Integer total_sent;
    @SerializedName("total_received")
    private Integer total_received;
    @SerializedName("txs")
    private List<Txs> txs;
    @SerializedName("hash160")
    private String hash160;


    public void setFinal_balance(Integer final_balance) {
        this.final_balance = final_balance;
    }

    public Integer getFinal_balance() {
        return final_balance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setN_tx(Integer n_tx) {
        this.n_tx = n_tx;
    }

    public Integer getN_tx() {
        return n_tx;
    }

    public void setTotal_sent(Integer total_sent) {
        this.total_sent = total_sent;
    }

    public Integer getTotal_sent() {
        return total_sent;
    }

    public void setTotal_received(Integer total_received) {
        this.total_received = total_received;
    }

    public Integer getTotal_received() {
        return total_received;
    }

    public void setTxs(List<Txs> txs) {
        this.txs = txs;
    }

    public List<Txs> getTxs() {
        return txs;
    }

    public void setHash160(String hash160) {
        this.hash160 = hash160;
    }

    public String getHash160() {
        return hash160;
    }

}