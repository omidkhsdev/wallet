package com.bitcoin.wallet.APIDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Inputs {
    @SerializedName("sequence")
    @Expose
    private long sequence;
    @SerializedName("witness")
    @Expose
    private String witness;
    @SerializedName("prev_out")
    @Expose
    private Prev_out prev_out;
    @SerializedName("script")
    @Expose
    private String script;

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public long getSequence() {
        return sequence;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }

    public String getWitness() {
        return witness;
    }

    public void setPrev_out(Prev_out prev_out) {
        this.prev_out = prev_out;
    }

    public Prev_out getPrev_out() {
        return prev_out;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }
}