package com.bitcoin.wallet.Fragments.FragmentDialog;


import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bitcoin.wallet.API.ApiTransaction;
import com.bitcoin.wallet.APIDTO.CreateWalletResponse;
import com.bitcoin.wallet.ApiService.RetrofitSingleton;
import com.bitcoin.wallet.Constants;
import com.bitcoin.wallet.CustomProgressBar;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.SharedManager;
import com.bitcoin.wallet.Utility.Utility;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferDialogFragment extends DialogFragment {
    private static final String TAG = "TransferDialogFragment";
    private TextInputEditText edWalletAddress, edPrice;
    private Button buttonConfirm, buttonDismiss;
    private View layoutLoading;
    private SharedManager sharedManager;

    public TransferDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transfer_dialog, container, false);
        init(v);
        buttonConfirm.setOnClickListener(view -> transferRequest(
                sharedManager.getData(Constants.KEY_TOKEN),
                Objects.requireNonNull(edWalletAddress.getText()).toString(),
                Objects.requireNonNull(edPrice.getText()).toString()));
        buttonDismiss.setOnClickListener(view -> dismiss());
        return v;
    }

    private void init(View view) {
        layoutLoading = view.findViewById(R.id.layout_loading_frame_container);
        edWalletAddress = view.findViewById(R.id.fragment_dialog_exchange_input_wallet_address);
        edPrice = view.findViewById(R.id.fragment_dialog_exchange_input_amount);
        buttonConfirm = view.findViewById(R.id.fragment_dialog_exchange_btn_confirm);
        buttonDismiss = view.findViewById(R.id.fragment_dialog_exchange_btn_dismiss);
        sharedManager = new SharedManager(Objects.requireNonNull(getActivity()));

        Rect displayRectangle = new Rect();
        Window window = getDialog().getWindow();
        assert window != null;
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        view.setMinimumWidth((int) (displayRectangle.width() * 0.8f));
        view.setMinimumHeight((int) (displayRectangle.height() * 0.6f));
    }

    private void transferRequest(String token, String address, String price) {
        CustomProgressBar.showProgress(layoutLoading);
        ApiTransaction api = RetrofitSingleton.initRetrofit().create(ApiTransaction.class);
        Call<CreateWalletResponse> call = api.sendTransactions(token, address, price);
        call.enqueue(new Callback<CreateWalletResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateWalletResponse> call, @NonNull Response<CreateWalletResponse> response) {
                if (response.code() == 500) {
                    Toasty.error(Objects.requireNonNull(getActivity()), getString(R.string.empty_wallet_price)).show();
                    dismiss();
                }
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().isTransaction()) {
                        Toasty.info(Objects.requireNonNull(getActivity()), "Your request submitted").show();
                    }else {
                        Toasty.error(Objects.requireNonNull(getActivity()), "Your balance is too low to transfer").show();
                    }
                }
                dismiss();
                CustomProgressBar.dismissProgress(layoutLoading);
                Utility.hideKeypad(Objects.requireNonNull(getActivity()),getView());
            }

            @Override
            public void onFailure(@NonNull Call<CreateWalletResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: exchange method" + t.getMessage());
                dismiss();
                Toasty.error(Objects.requireNonNull(getActivity()), getString(R.string.empty_wallet_price)).show();
                CustomProgressBar.dismissProgress(layoutLoading);
                Utility.hideKeypad(Objects.requireNonNull(getActivity()),getView());
            }
        });
    }
}
