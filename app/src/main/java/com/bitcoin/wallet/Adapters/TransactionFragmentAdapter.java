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
import java.util.List;


public class TransactionFragmentAdapter extends RecyclerView.Adapter<TransactionFragmentAdapter.MyViewHolder> {

    private List<TransactionModel> list=new ArrayList<>();;
    private Context context;
    public TransactionFragmentAdapter() {

    }

    public TransactionFragmentAdapter(Context context) {
        this.context = context;
    }

    public void addDataList(List<TransactionModel> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void addDataSingle(TransactionModel model){
        this.list.add(model);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_transaction, parent, false);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrencyName = itemView.findViewById(R.id.adapter_transaction_currency_full_name);
            tvCurrencyShortName = itemView.findViewById(R.id.adapter_transaction_currency_short_name);
            tvDate = itemView.findViewById(R.id.adapter_transaction_date);
            tvCurrencyAmount = itemView.findViewById(R.id.adapter_transaction_amount);
            tvTransactionLink = itemView.findViewById(R.id.adapter_transaction_link);
            imgvReceive = itemView.findViewById(R.id.adapter_transaction_imgv_get);
            imgvSend = itemView.findViewById(R.id.adapter_transaction_imgv_send);
            currenyIcon = itemView.findViewById(R.id.adapter_transaction_currency_icon);
            tvCurrencyUnit = itemView.findViewById(R.id.adapter_transaction_currency_unit);
        }

        void bind(TransactionModel model) {
            tvCurrencyName.setText(model.getCurrencyName());
            tvCurrencyShortName.setText(model.getCurrencyShortName());
            tvDate.setText(model.getDate());
            tvCurrencyAmount.setText(String.valueOf(model.getCurrencyAmount()));
            tvTransactionLink.setText(model.getTransactionLink());
            tvCurrencyUnit.setText(model.getCurrencyShortName());

            if (model.getGetOrSend() == Utility.TRANSACTION_SEND) {
                imgvSend.setVisibility(View.VISIBLE);
                imgvReceive.setVisibility(View.GONE);
            } else if (model.getGetOrSend() == Utility.TRANSACTION_RECEIVE) {
                imgvSend.setVisibility(View.GONE);
                imgvReceive.setVisibility(View.VISIBLE);
            }

            switch (model.getCurrencyEnum()) {
                case BTC:
                    currenyIcon.setImageResource(R.drawable.bitcoin);
                    break;
                case DASH:
                    currenyIcon.setImageResource(R.drawable.dash);
                    break;
                case BTH:
                    currenyIcon.setImageResource(R.drawable.bitcoin_cash);
                    break;
                case ETH:
                    currenyIcon.setImageResource(R.drawable.ethereum);
                    break;
                case EOS:
                    currenyIcon.setImageResource(R.drawable.eos);
                    break;
                case LTC:
                    currenyIcon.setImageResource(R.drawable.litecoin);
                    break;
                case NEO:
                    currenyIcon.setImageResource(R.drawable.neo);
                    break;
                case XRP:
                    currenyIcon.setImageResource(R.drawable.ripple);
                    break;
                case Zcash:
                    currenyIcon.setImageResource(R.drawable.zcash);
                    break;
            }
        }
    }
}
