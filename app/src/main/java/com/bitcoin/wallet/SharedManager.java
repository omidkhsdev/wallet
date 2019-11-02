package com.bitcoin.wallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedManager {
    private static final String TAG = "SharedManager";
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    // Shared preference name
    private static final String SHARED_NAME = "app_shared_manager";

    // Shared preference keys

    private static final String IS_LOGIN = "IsLoggedIn";


    @SuppressLint("CommitPrefEdits")
    public SharedManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Gets an instance of SharedDataModel then extracts its data
     * then stores them in shared preference.
     *
     * @param data
     */
    public void setData(String data, String key){
        editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
        Log.i(TAG, "setData: "+"key is : "+key + " data is : "+data );
    }

    public void setLogin(boolean isLogin){
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN,isLogin);
        editor.apply();
        Log.i(TAG, "user has been login:"+isLogin );
    }


    /**
     * Gets data from shared preference and stores them in an instance of SharedDataModel.
     *
     * @return instance of SharedDataModel
     */
    public String getData(String key){
        Log.i(TAG, "getData: " + key);
        return sharedPreferences.getString(key, "");
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, HomepageActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

}
