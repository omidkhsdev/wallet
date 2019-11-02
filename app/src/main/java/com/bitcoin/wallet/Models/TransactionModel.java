package com.bitcoin.wallet.Models;

import com.bitcoin.wallet.Utility.CurrencyEnum;

import java.math.BigDecimal;

public class TransactionModel {
    private String currencyName;
    private String currencyShortName;
    private String date;
    private BigDecimal currencyAmount;
    private String transactionLink;
    private int getOrSend;
    private CurrencyEnum currencyEnum;

    public TransactionModel() {
    }

    public TransactionModel(String currencyName,
                            String currencyShortName,
                            String date,
                            BigDecimal currencyAmount,
                            String transactionLink,
                            int getOrSend,
                            CurrencyEnum currencyEnum) {
        this.currencyName = currencyName;
        this.currencyShortName = currencyShortName;
        this.date = date;
        this.currencyAmount = currencyAmount;
        this.transactionLink = transactionLink;
        this.getOrSend = getOrSend;
        this.currencyEnum = currencyEnum;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyShortName() {
        return currencyShortName;
    }

    public void setCurrencyShortName(String currencyShortName) {
        this.currencyShortName = currencyShortName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(BigDecimal currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getTransactionLink() {
        return transactionLink;
    }

    public void setTransactionLink(String transactionLink) {
        this.transactionLink = transactionLink;
    }

    public int getGetOrSend() {
        return getOrSend;
    }

    public void setGetOrSend(int getOrSend) {
        this.getOrSend = getOrSend;
    }

    public CurrencyEnum getCurrencyEnum() {
        return currencyEnum;
    }

    public void setCurrencyEnum(CurrencyEnum currencyEnum) {
        this.currencyEnum = currencyEnum;
    }
}

