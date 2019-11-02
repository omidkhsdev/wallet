package com.bitcoin.wallet;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bitcoin.wallet.Fragments.ImportWalletFragment;
import com.bitcoin.wallet.Fragments.LoginFragment;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedManager sharedManager = new SharedManager(this);

        if (sharedManager.isLoggedIn()){
            if (sharedManager.getData(Constants.KEY_WALLET_ADDRESS) != null &&
                    !sharedManager.getData(Constants.KEY_WALLET_ADDRESS).isEmpty()){
                sharedManager.checkLogin();
            }else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_login_fragment_container, new ImportWalletFragment())
                        .commit();
            }
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_login_fragment_container, new LoginFragment())
                    .commit();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
