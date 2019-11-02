package com.bitcoin.wallet;

import java.util.regex.Pattern;

public class Constants {
    public static String webViewLink = "https://mysecurewallet.info/privacypolicy/";
    // Patterns
    public static final Pattern VALID_EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final String COPY_WALLET_ADDRESS = "copy_wallet_address";
    public static String TARGET_PUBLIC_KEY_SCAN = "";
    public static boolean refreshAble = false;

    //shared preference key
    public static final String KEY_TOKEN = "key_api_token";
    public static final String KEY_EMAIL = "key_email";
    public static final String KEY_WALLET_ADDRESS = "key_wallet_address";
    public static final String KEY_PRIVATE_KEY = "key_private_key";

}
