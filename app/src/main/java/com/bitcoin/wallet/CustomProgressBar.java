package com.bitcoin.wallet;

import android.view.View;

public class CustomProgressBar {

    public static void showProgress(View progressContainer){
        progressContainer.bringToFront();
        progressContainer.setVisibility(View.VISIBLE);
    }

    public static void dismissProgress(View progressContainer){
        progressContainer.setVisibility(View.GONE);
    }

}