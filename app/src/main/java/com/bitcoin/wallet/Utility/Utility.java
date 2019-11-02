package com.bitcoin.wallet.Utility;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bitcoin.wallet.R;

import java.util.HashMap;

public class Utility {

    public static final int TRANSACTION_RECEIVE = 0;
    public static final int TRANSACTION_SEND = 1;


    public static HashMap<String, Integer> getCoinIcons() {
        HashMap<String, Integer> coinIcons = new HashMap<>();

        coinIcons.put("BTH", R.drawable.bitcoin_cash);
        coinIcons.put("DASH", R.drawable.dash);
        coinIcons.put("BTC", R.drawable.bitcoin);
        coinIcons.put("EOS", R.drawable.eos);
        coinIcons.put("ETH", R.drawable.ethereum);
        coinIcons.put("LTC", R.drawable.litecoin);
        coinIcons.put("NEO", R.drawable.neo);
        coinIcons.put("XRP", R.drawable.ripple);
        coinIcons.put("Zcash", R.drawable.zcash);
        return coinIcons;
    }

    public static void hideKeypad(Context context, View view) {
        {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}
