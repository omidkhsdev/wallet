package com.bitcoin.wallet.APIDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Prev_out{
  @SerializedName("spent")
  @Expose
  private Boolean spent;
  @SerializedName("spending_outpoints")
  @Expose
  private List<Spending_outpoints> spending_outpoints;
  @SerializedName("tx_index")
  @Expose
  private Integer tx_index;
  @SerializedName("type")
  @Expose
  private Integer type;
  @SerializedName("addr")
  @Expose
  private String addr;
  @SerializedName("value")
  @Expose
  private Integer value;
  @SerializedName("n")
  @Expose
  private Integer n;
  @SerializedName("script")
  @Expose
  private String script;
  public void setSpent(Boolean spent){
   this.spent=spent;
  }
  public Boolean getSpent(){
   return spent;
  }
  public void setSpending_outpoints(List<Spending_outpoints> spending_outpoints){
   this.spending_outpoints=spending_outpoints;
  }
  public List<Spending_outpoints> getSpending_outpoints(){
   return spending_outpoints;
  }
  public void setTx_index(Integer tx_index){
   this.tx_index=tx_index;
  }
  public Integer getTx_index(){
   return tx_index;
  }
  public void setType(Integer type){
   this.type=type;
  }
  public Integer getType(){
   return type;
  }
  public void setAddr(String addr){
   this.addr=addr;
  }
  public String getAddr(){
   return addr;
  }
  public void setValue(Integer value){
   this.value=value;
  }
  public Integer getValue(){
   return value;
  }
  public void setN(Integer n){
   this.n=n;
  }
  public Integer getN(){
   return n;
  }
  public void setScript(String script){
   this.script=script;
  }
  public String getScript(){
   return script;
  }
}