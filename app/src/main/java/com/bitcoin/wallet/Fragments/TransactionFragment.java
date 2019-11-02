package com.bitcoin.wallet.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitcoin.wallet.API.ApiTransaction;
import com.bitcoin.wallet.APIDTO.TransactionsDTO;
import com.bitcoin.wallet.APIDTO.Txs;
import com.bitcoin.wallet.Adapters.TransactionFragmentAdapter;
import com.bitcoin.wallet.ApiService.RetrofitSingleton;
import com.bitcoin.wallet.Constants;
import com.bitcoin.wallet.CustomProgressBar;
import com.bitcoin.wallet.Models.TransactionModel;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.SharedManager;
import com.bitcoin.wallet.Utility.CurrencyEnum;
import com.bitcoin.wallet.Utility.Utility;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionFragment extends Fragment {

    private static final String TAG = "TransactionFragment";
    private TransactionFragmentAdapter adapter;
    private SharedManager sharedManager;
    private View layoutEmpty;
    boolean isFragmentLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        init(view);


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded) {
            //Load Your Data Here like.... new GetContacts().execute();
            isFragmentLoaded = true;
            getTransActionRequest(sharedManager.getData(Constants.KEY_WALLET_ADDRESS));
        }
    }


    private void init(View view) {
        sharedManager = new SharedManager(Objects.requireNonNull(getActivity()));
        RecyclerView recyclerView = view.findViewById(R.id.fragment_transaction_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new TransactionFragmentAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        layoutEmpty = view.findViewById(R.id.layout_empty_frame_container);
    }

    public TransactionFragment newInstance() {

        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void parseData(TransactionsDTO dto) {

        for (Txs tx :
                dto.getTxs()) {
                if (tx.getInputs().get(0).getPrev_out().getAddr().equals(sharedManager.getData(Constants.KEY_WALLET_ADDRESS))) {
                /**
                 *send from wallet to another wallet
                 */
                adapter.addDataSingle(new TransactionModel("Bitcoin",
                        "BTC",
                        convertTimeStamp(tx.getTime()),
                        BigDecimal.valueOf(tx.getInputs().get(0).getPrev_out().getValue()).divide(BigDecimal.valueOf(100000000)),
                        tx.getHash(),
                        Utility.TRANSACTION_SEND,
                        CurrencyEnum.BTC
                ));
            } else {

                /**
                 * receive from another wallet
                 */
                adapter.addDataSingle(new TransactionModel("Bitcoin",
                                "BTC",
                                convertTimeStamp(tx.getTime()),
                                BigDecimal.valueOf(tx.getOut().get(0).getValue()).divide(BigDecimal.valueOf(100000000)),
                                tx.getHash(),
                                Utility.TRANSACTION_RECEIVE,
                                CurrencyEnum.BTC
                        )
                );

            }
        }

        if (adapter.getItemCount() > 0) {
            CustomProgressBar.dismissProgress(layoutEmpty);
        } else {
            CustomProgressBar.showProgress(layoutEmpty);
        }
    }

    private String convertTimeStamp(int time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000L);
        return DateFormat.format("dd-MM-yyyy", cal).toString();
    }


    private void getTransActionRequest(String walletAddress) {
        ApiTransaction api = RetrofitSingleton.initRetrofit().create(ApiTransaction.class);
        Call<TransactionsDTO> call = api.getTransActions(walletAddress);
        call.enqueue(new Callback<TransactionsDTO>() {
            @Override
            public void onResponse(Call<TransactionsDTO> call, Response<TransactionsDTO> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    parseData(response.body());
                }
                Log.d(TAG, "onResponse: getTransaction" + response.code());
            }

            @Override
            public void onFailure(Call<TransactionsDTO> call, Throwable t) {
                Log.e(TAG, "onFailure: get TransAction" + t.getMessage());
                Toasty.error(Objects.requireNonNull(getContext()), getString(R.string.toast_use_vpn)).show();
            }
        });

    }
}
