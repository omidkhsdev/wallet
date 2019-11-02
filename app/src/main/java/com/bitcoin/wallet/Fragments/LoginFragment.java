package com.bitcoin.wallet.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bitcoin.wallet.API.ApiLogin;
import com.bitcoin.wallet.APIDTO.ServerResponse;
import com.bitcoin.wallet.ApiService.RetrofitSingleton;
import com.bitcoin.wallet.Constants;
import com.bitcoin.wallet.CustomProgressBar;
import com.bitcoin.wallet.HomepageActivity;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.SharedManager;
import com.bitcoin.wallet.Utility.Utility;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private TextInputEditText edInputEmail, edInputPass;
    private Button btnLoginRegister;
    private TextView tvForgotPass, tvPrivacyPolicy;
    private View view,loadingLayout;
    private SharedManager sharedManager;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        init();

        btnLoginRegister.setOnClickListener(view1 -> checkEditTexts());
        tvForgotPass.setOnClickListener(view1 ->
                changeFragment(new ForgetPassFragment())
        );

        tvPrivacyPolicy.setOnClickListener(view1 -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Constants.webViewLink));
            startActivity(i);
        });

        return view;
    }

    private void init() {
        edInputEmail = view.findViewById(R.id.login_input_email);
        edInputPass = view.findViewById(R.id.login_input_password);
        btnLoginRegister = view.findViewById(R.id.activity_login_btn_login_register);
        tvForgotPass = view.findViewById(R.id.activity_login_tv_forget_pass);
        tvPrivacyPolicy = view.findViewById(R.id.activity_login_tv_privacy_policy);
        loadingLayout = view.findViewById(R.id.layout_loading_frame_container);
        sharedManager = new SharedManager(Objects.requireNonNull(getActivity()));
    }


    private void sendLoginRequest(String email, String password) {
        ApiLogin api = RetrofitSingleton.initRetrofit().create(ApiLogin.class);
        Call<ServerResponse> call = api.login(email, password);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    //dismiss progress bar if response is ok
                    assert response.body() != null;
                    if (response.body().getToken()!=null) {
                        //if response is successful change view to import or create new wallet
                        Log.d(TAG, "onResponse login req : has a wallet and save token");
                        sharedManager.setData(response.body().getToken(), Constants.KEY_TOKEN);
                        sharedManager.setData(email, Constants.KEY_EMAIL);
                        sharedManager.setLogin(true);
                        Utility.hideKeypad(Objects.requireNonNull(getActivity()),getView());
                        CustomProgressBar.dismissProgress(loadingLayout);
                        if (response.body().getHasWallet()){
                            getActivity().startActivity(new Intent(getActivity(), HomepageActivity.class));
                        }else {
                            changeFragment(new ImportWalletFragment());
                        }
                    }
                    else {
                        Toasty.error(Objects.requireNonNull(getActivity()),getString(R.string.wrong_password)).show();
                        CustomProgressBar.dismissProgress(loadingLayout);
                    }
                } else {
                    Toasty.error(Objects.requireNonNull(getActivity()),getString(R.string.something_went_wrong)).show();
                    CustomProgressBar.dismissProgress(loadingLayout);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                CustomProgressBar.dismissProgress(loadingLayout);
                Log.e(TAG, "onFailure: send login request " + t.getMessage());
            }
        });

    }

    private void changeFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().
                beginTransaction().
                replace(R.id.activity_login_fragment_container, fragment)
                .addToBackStack(null).commit();
    }

    /*
  First checks if email is valid and not empty,
  then checks if password is not empty then sends request to server
  */
    private void checkEditTexts() {

        if (!validateEmail(Objects.requireNonNull(edInputEmail.getText()).toString().trim())) {
            Toasty.error(Objects.requireNonNull(getActivity()), getString(R.string.error_invalid_email), Toasty.LENGTH_SHORT, true).show();
        } else {

            if (Objects.requireNonNull(edInputPass.getText()).toString().trim().equalsIgnoreCase("")) {

                Toasty.error(Objects.requireNonNull(getActivity()), getString(R.string.error_empty_password), Toasty.LENGTH_SHORT, true).show();

            } else {
                sendLoginRequest(edInputEmail.getText().toString(), edInputPass.getText().toString());
                CustomProgressBar.showProgress(loadingLayout);
            }
        }
    }


    /**
     * Gets email and validates with "VALID_EMAIL_PATTERN" pattern.
     *
     * @param email
     * @return true or false
     */
    private boolean validateEmail(String email) {
        return Constants.VALID_EMAIL_PATTERN.matcher(email).find();
    }

}
