package com.bitcoin.wallet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bitcoin.wallet.Models.TransactionModel;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Transaction2ndAdapter extends RecyclerView.Adapter<Transaction2ndAdapter.MyViewHolder> {

    private List<TransactionModel> list=new ArrayList<>();
    private Context context;

    public Transaction2ndAdapter(Context context) {
        this.context = context;

    }
    public void addListData(List<TransactionModel> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addSingleData(TransactionModel model){
        this.list.add(model);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_transaction_2nd, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrencyName, tvCurrencyShortName, tvDate, tvCurrencyAmount, tvTransactionLink, tvCurrencyUnit;
        AppCompatImageView imgvReceive, imgvSend;
        ImageView currenyIcon;
        HashMap<String, Integer> coinIcons;
        Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrencyName = itemView.findViewById(R.id.adapter_transaction_currency_full_name);
            tvCurrencyShortName = itemView.findViewById(R.id.adapter_transaction_currency_short_name);
            tvDate = itemView.findViewById(R.id.adapter_transaction_date);
            tvCurrencyAmount = itemView.findViewById(R.id.adapter_transaction_amount);
            currenyIcon = itemView.findViewById(R.id.adapter_transaction_currency_icon);
            coinIcons = Utility.getCoinIcons();
            context = itemView.getContext();
        }

        void bind(TransactionModel model) {
            tvCurrencyName.setText(model.getCurrencyName());
            tvCurrencyShortName.setText(model.getCurrencyShortName());
            tvDate.setText(model.getDate());
            tvCurrencyAmount.setText(String.valueOf(model.getCurrencyAmount()));

            if (model.getGetOrSend() == Utility.TRANSACTION_SEND) {
                tvCurrencyName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sent, 0);
            } else if (model.getGetOrSend() == Utility.TRANSACTION_RECEIVE) {
                tvCurrencyName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_receive, 0);
            }

            if (coinIcons.get(model.getCurrencyShortName()) != null) {
                currenyIcon.setImageResource(coinIcons.get(model.getCurrencyShortName()));
            }
        }
    }
}

