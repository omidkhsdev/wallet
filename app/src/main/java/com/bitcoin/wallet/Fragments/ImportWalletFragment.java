package com.bitcoin.wallet.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bitcoin.wallet.API.ApiWallet;
import com.bitcoin.wallet.APIDTO.CreateWalletResponse;
import com.bitcoin.wallet.ApiService.RetrofitSingleton;
import com.bitcoin.wallet.Constants;
import com.bitcoin.wallet.CustomProgressBar;
import com.bitcoin.wallet.Fragments.FragmentDialog.ImportWalletDialogFragment;
import com.bitcoin.wallet.HomepageActivity;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.SharedManager;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImportWalletFragment extends Fragment {
    private static final String TAG = "ImportWalletFragment";
    private Button btnImportWallet, btnCreateNew;
    private View view,loadingLayout;
    private SharedManager sharedManager;

    public ImportWalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_import_wallet, container, false);
        init();
        AppCompatActivity activity = (AppCompatActivity) container.getContext();

        btnImportWallet.setOnClickListener(view -> {
            CustomProgressBar.showProgress(loadingLayout);
            ImportWalletDialogFragment dialogImport = new ImportWalletDialogFragment();
            dialogImport.show(activity.getSupportFragmentManager(), "fragment_import_wallet");
            CustomProgressBar.dismissProgress(loadingLayout);

        });
        btnCreateNew.setOnClickListener(view -> createNewWalletRequest(sharedManager.getData(Constants.KEY_TOKEN)));
        return view;
    }

    private void init() {
        btnImportWallet = view.findViewById(R.id.fragment_import_wallet_import);
        btnCreateNew = view.findViewById(R.id.fragment_import_wallet_create_new);
        sharedManager = new SharedManager(Objects.requireNonNull(getActivity()));
        loadingLayout = view.findViewById(R.id.layout_loading_frame_container);
        loadingLayout=view.findViewById(R.id.layout_loading_frame_container);
    }

    private void createNewWalletRequest(String token) {
        CustomProgressBar.showProgress(loadingLayout);
        ApiWallet api = RetrofitSingleton.initRetrofit().create(ApiWallet.class);
        Call<CreateWalletResponse> call = api.createWallet(token);
        call.enqueue(new Callback<CreateWalletResponse>() {
            @Override
            public void onResponse(Call<CreateWalletResponse> call, Response<CreateWalletResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    //response is ok
                    if (response.body().getToken() != null) {
                        Toasty.error(Objects.requireNonNull(getActivity()), R.string.error).show();
                    } else {
                        sharedManager.setData(response.body().getWalletAddress(), Constants.KEY_WALLET_ADDRESS);
                        sharedManager.setData(response.body().getPrivateKey(), Constants.KEY_PRIVATE_KEY);
                        startActivity(new Intent(getActivity(), HomepageActivity.class));
                    }
                }
                CustomProgressBar.dismissProgress(loadingLayout);
            }

            @Override
            public void onFailure(Call<CreateWalletResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: create new wallet" + t.getMessage());
                CustomProgressBar.dismissProgress(loadingLayout);
            }
        });
    }


}
