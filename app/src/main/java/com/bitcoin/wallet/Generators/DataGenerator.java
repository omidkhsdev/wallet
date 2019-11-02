package com.bitcoin.wallet.Generators;

import com.bitcoin.wallet.Models.ExchangeModel;
import com.bitcoin.wallet.Models.TransactionModel;
import com.bitcoin.wallet.Utility.CurrencyEnum;
import com.bitcoin.wallet.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<ExchangeModel> generateExchangeList() {
        List<ExchangeModel> list = new ArrayList<>();
        list.add(new ExchangeModel("Ethereum", "ETH"));
        list.add(new ExchangeModel("Litecoin", "LTC"));
        list.add(new ExchangeModel("Ripple", "XRP"));
        list.add(new ExchangeModel("EOS", "EOS"));
        list.add(new ExchangeModel("Bitcoin Cash", "BTH"));
        list.add(new ExchangeModel("NEO", "NEO"));
        list.add(new ExchangeModel("Zcash", "Zcash"));
        list.add(new ExchangeModel("Dash", "DASH"));

        return list;
    }

}
