package com.bitcoin.wallet.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.bitcoin.wallet.API.ApiForgetPassword;
import com.bitcoin.wallet.APIDTO.ServerResponse;
import com.bitcoin.wallet.ApiService.RetrofitSingleton;
import com.bitcoin.wallet.Constants;
import com.bitcoin.wallet.CustomProgressBar;
import com.bitcoin.wallet.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassFragment extends Fragment {
    private static final String TAG = "ForgetPassFragment";
    private Button buttonSendRequest, buttonBackToLogin;
    private View view, loadingLayout;
    private TextInputEditText edInputEmail;

    public ForgetPassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forget_pass, container, false);
        init();
        buttonSendRequest.setOnClickListener(view1 -> checkEditTexts());
        buttonBackToLogin.setOnClickListener(view -> Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStackImmediate());
        return view;
    }

    private void init() {
        buttonSendRequest = view.findViewById(R.id.fragment_forget_pass_btn_submit);
        buttonBackToLogin = view.findViewById(R.id.fragment_forget_pass_btn_back_login);
        edInputEmail = view.findViewById(R.id.login_input_email);
        loadingLayout = view.findViewById(R.id.layout_loading_frame_container);
    }

    /*
        First checks if email is valid and not empty,
        then checks if password is not empty then sends request to server
      */
    private void checkEditTexts() {
        if (!validateEmail(Objects.requireNonNull(edInputEmail.getText()).toString().trim())) {
            Toasty.error(Objects.requireNonNull(getActivity()), getString(R.string.error_invalid_email), Toasty.LENGTH_SHORT, true).show();
        } else {
            sendForgetPasswordRequest(edInputEmail.getText().toString());
            CustomProgressBar.showProgress(loadingLayout);
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

    private void sendForgetPasswordRequest(String email) {
        ApiForgetPassword api = RetrofitSingleton.initRetrofit().create(ApiForgetPassword.class);
        Call<ServerResponse> call = api.forgetPassword(email);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().isSent() && response.body().isExists()) {
                        //response is ok
                        Toasty.info(Objects.requireNonNull(getActivity()), R.string.email_send, Toasty.LENGTH_SHORT, true).show();
                        CustomProgressBar.dismissProgress(loadingLayout);
                    } else {
                        Toasty.error(Objects.requireNonNull(getActivity()), R.string.error_invalid_email, Toasty.LENGTH_SHORT, true).show();
                        CustomProgressBar.dismissProgress(loadingLayout);
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: forget password" + t.getMessage());
                CustomProgressBar.dismissProgress(loadingLayout);

            }
        });
    }

}
