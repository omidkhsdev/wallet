package com.bitcoin.wallet.Fragments.FragmentDialog;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.bitcoin.wallet.API.ApiWallet;
import com.bitcoin.wallet.APIDTO.CreateWalletResponse;
import com.bitcoin.wallet.ApiService.RetrofitSingleton;
import com.bitcoin.wallet.Constants;
import com.bitcoin.wallet.CustomProgressBar;
import com.bitcoin.wallet.HomepageActivity;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.SharedManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportWalletDialogFragment extends AppCompatDialogFragment {
    private static final String TAG = "ImportWalletDialogFragm";
    private TextInputEditText edWalletAddress;
    private Button btnConfirm, btnDismiss;
    private SharedManager sharedManager;
    private View layoutLoading;

    public ImportWalletDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_import_wallet, container);
        init(view);
        btnConfirm.setOnClickListener(view1 -> {
            CustomProgressBar.showProgress(layoutLoading);
            importWalletRequest(sharedManager.getData(Constants.KEY_TOKEN),
                    Objects.requireNonNull(edWalletAddress.getText()).toString());
        });
        return view;
    }


    private void init(View view) {
        Rect displayRectangle = new Rect();
        Window window = getDialog().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        view.setMinimumWidth((int) (displayRectangle.width() * 0.8f));
        view.setMinimumHeight((int) (displayRectangle.height() * 0.6f));
        layoutLoading = view.findViewById(R.id.layout_loading_frame_container);
        sharedManager = new SharedManager(Objects.requireNonNull(getActivity()));

        // Get field from view
        edWalletAddress = view.findViewById(R.id.fragment_dialog_import_wallet_tiet_wallet_address);
        btnConfirm = view.findViewById(R.id.fragment_dialog_import_wallet_btn_confirm);
        btnDismiss = view.findViewById(R.id.fragment_dialog_import_wallet_btn_dismiss);
        // Fetch arguments from bundle and set title

        btnDismiss.setOnClickListener(view1 -> getDialog().dismiss());

        // Show soft keyboard automatically and request focus to field
        edWalletAddress.requestFocus();
    }

    private void importWalletRequest(String token, String privateKey) {
        ApiWallet api = RetrofitSingleton.initRetrofit().create(ApiWallet.class);
        Call<CreateWalletResponse> call = api.importWallet(token, privateKey);
        call.enqueue(new Callback<CreateWalletResponse>() {
            @Override
            public void onResponse(Call<CreateWalletResponse> call, Response<CreateWalletResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    //response is ok
                    if (response.body().isAdded()) {
                        startActivity(new Intent(getActivity(), HomepageActivity.class));
                    } else {
                        Toasty.error(Objects.requireNonNull(getActivity()), R.string.error).show();
                    }
                }
                CustomProgressBar.dismissProgress(layoutLoading);
            }

            @Override
            public void onFailure(Call<CreateWalletResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: create new wallet" + t.getMessage());
                CustomProgressBar.dismissProgress(layoutLoading);
            }
        });
    }
}

