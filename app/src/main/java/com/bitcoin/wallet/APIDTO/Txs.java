package com.bitcoin.wallet.APIDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Txs {
    @SerializedName("ver")
    @Expose
    private Integer ver;
    @SerializedName("inputs")
    @Expose
    private List<Inputs> inputs;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("block_height")
    @Expose
    private Integer block_height;
    @SerializedName("relayed_by")
    @Expose
    private String relayed_by;
    @SerializedName("out")
    @Expose
    private List<Out> out;
    @SerializedName("lock_time")
    @Expose
    private Integer lock_time;
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("block_index")
    @Expose
    private Integer block_index;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("tx_index")
    @Expose
    private long tx_index;
    @SerializedName("vin_sz")
    @Expose
    private Integer vin_sz;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("vout_sz")
    @Expose
    private Integer vout_sz;

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Integer getVer() {
        return ver;
    }

    public void setInputs(List<Inputs> inputs) {
        this.inputs = inputs;
    }

    public List<Inputs> getInputs() {
        return inputs;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setBlock_height(Integer block_height) {
        this.block_height = block_height;
    }

    public Integer getBlock_height() {
        return block_height;
    }

    public void setRelayed_by(String relayed_by) {
        this.relayed_by = relayed_by;
    }

    public String getRelayed_by() {
        return relayed_by;
    }

    public void setOut(List<Out> out) {
        this.out = out;
    }

    public List<Out> getOut() {
        return out;
    }

    public void setLock_time(Integer lock_time) {
        this.lock_time = lock_time;
    }

    public Integer getLock_time() {
        return lock_time;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setBlock_index(Integer block_index) {
        this.block_index = block_index;
    }

    public Integer getBlock_index() {
        return block_index;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTime() {
        return time;
    }

    public void setTx_index(long tx_index) {
        this.tx_index = tx_index;
    }

    public long getTx_index() {
        return tx_index;
    }

    public void setVin_sz(Integer vin_sz) {
        this.vin_sz = vin_sz;
    }

    public Integer getVin_sz() {
        return vin_sz;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setVout_sz(Integer vout_sz) {
        this.vout_sz = vout_sz;
    }

    public Integer getVout_sz() {
        return vout_sz;
    }
}