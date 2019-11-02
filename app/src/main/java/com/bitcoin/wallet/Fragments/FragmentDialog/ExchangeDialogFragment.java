package com.bitcoin.wallet.Fragments.FragmentDialog;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bitcoin.wallet.API.ApiExchangeBTC;
import com.bitcoin.wallet.APIDTO.CreateWalletResponse;
import com.bitcoin.wallet.ApiService.RetrofitSingleton;
import com.bitcoin.wallet.Constants;
import com.bitcoin.wallet.CustomProgressBar;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.SharedManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeDialogFragment extends AppCompatDialogFragment {
    private static final String TAG = "ExchangeDialogFragment";
    private TextInputEditText tietWalletAddress, tietAmount;
    private Button btnConfirm, btnDismiss;
    private ImageView imgvCurrenyIcon;
    private TextView tvSubtitle;
    private SharedManager sharedManager;
    private String currency;
    private View layoutLoading;

    private static final String SUBTITLE_KEY = "subtitle";
    private static final String CURRENCY_ICON_ID_KEY = "currenyIconId";

    public ExchangeDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }


    public static ExchangeDialogFragment newInstance(String subtitle, int currencyIconId) {
        ExchangeDialogFragment frag = new ExchangeDialogFragment();
        Bundle args = new Bundle();
        args.putString(SUBTITLE_KEY, subtitle);
        args.putInt(CURRENCY_ICON_ID_KEY, currencyIconId);
        frag.setArguments(args);
        return frag;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_exchange, container);
        init(view);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            imgvCurrenyIcon.setImageResource(bundle.getInt(CURRENCY_ICON_ID_KEY));
            tvSubtitle.setText(bundle.getString(SUBTITLE_KEY));
            currency = bundle.getString(SUBTITLE_KEY);
        }


        btnConfirm.setOnClickListener(view1 -> changeBTCRequest(
                tietWalletAddress.getText().toString(),
                sharedManager.getData(Constants.KEY_TOKEN),
                tietAmount.getText().toString(),
                currency));

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view

        // Fetch arguments from bundle and set title

        btnDismiss.setOnClickListener(view1 -> getDialog().dismiss());

        // Show soft keyboard automatically and request focus to field
        tietWalletAddress.requestFocus();
        // getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    private void init(View view) {
        layoutLoading=view.findViewById(R.id.layout_loading_frame_container);
        tietWalletAddress = view.findViewById(R.id.fragment_dialog_exchange_input_wallet_address);
        tietAmount = view.findViewById(R.id.fragment_dialog_exchange_input_amount);
        btnConfirm = view.findViewById(R.id.fragment_dialog_exchange_btn_confirm);
        btnDismiss = view.findViewById(R.id.fragment_dialog_exchange_btn_dismiss);
        imgvCurrenyIcon = view.findViewById(R.id.fragment_dialog_exchange_imgv_currency_icon);
        tvSubtitle = view.findViewById(R.id.fragment_dialog_exchange_tv_subtitle);
        sharedManager = new SharedManager(Objects.requireNonNull(getActivity()));

        Rect displayRectangle = new Rect();
        Window window = getDialog().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        view.setMinimumWidth((int) (displayRectangle.width() * 0.8f));
        view.setMinimumHeight((int) (displayRectangle.height() * 0.6f));
    }

    private void changeBTCRequest(String address, String token, String price, String crypto) {
        CustomProgressBar.showProgress(layoutLoading);
        ApiExchangeBTC api = RetrofitSingleton.initRetrofit().create(ApiExchangeBTC.class);
        Call<CreateWalletResponse> call = api.exchangeBTC(address, token, price, crypto);
        call.enqueue(new Callback<CreateWalletResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateWalletResponse> call, @NonNull Response<CreateWalletResponse> response) {
                if (response.isSuccessful()&&response.code()==200){
                    assert response.body() != null;
                    if (response.body().isAdded()){
                        Toasty.info(Objects.requireNonNull(getActivity()),"Your request submitted").show();
                        dismiss();
                    }
                }else {
                    Toasty.error(Objects.requireNonNull(getActivity()),"Something went wrong.please try again!").show();
                    dismiss();
                }
                CustomProgressBar.dismissProgress(layoutLoading);
            }

            @Override
            public void onFailure(@NonNull Call<CreateWalletResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: exchange method" + t.getMessage());
                dismiss();
                CustomProgressBar.dismissProgress(layoutLoading);
            }
        });
    }

}

