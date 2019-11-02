package com.bitcoin.wallet.Models;

public class ExchangeModel {

    private String currencyUnit;
    private String subtitle;

    public ExchangeModel() {
    }

    public ExchangeModel(String currencyUnit, String subtitle) {
        this.currencyUnit = currencyUnit;
        this.subtitle = subtitle;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
