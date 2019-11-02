package com.bitcoin.wallet.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitcoin.wallet.Adapters.ExchangeFragmentAdapter;
import com.bitcoin.wallet.Generators.DataGenerator;
import com.bitcoin.wallet.R;


public class ExchangeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExchangeFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);
        recyclerView = view.findViewById(R.id.fragment_exchange_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new ExchangeFragmentAdapter(DataGenerator.generateExchangeList());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public ExchangeFragment newInstance() {

        Bundle args = new Bundle();

        ExchangeFragment fragment = new ExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
