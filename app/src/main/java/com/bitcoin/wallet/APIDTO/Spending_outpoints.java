package com.bitcoin.wallet.APIDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Spending_outpoints{
  @SerializedName("tx_index")
  @Expose
  private Integer tx_index;
  @SerializedName("n")
  @Expose
  private Integer n;
  public void setTx_index(Integer tx_index){
   this.tx_index=tx_index;
  }
  public Integer getTx_index(){
   return tx_index;
  }
  public void setN(Integer n){
   this.n=n;
  }
  public Integer getN(){
   return n;
  }
}