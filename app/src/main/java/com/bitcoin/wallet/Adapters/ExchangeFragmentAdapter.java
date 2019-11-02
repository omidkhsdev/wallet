package com.bitcoin.wallet.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitcoin.wallet.Fragments.FragmentDialog.ExchangeDialogFragment;
import com.bitcoin.wallet.Models.ExchangeModel;
import com.bitcoin.wallet.R;
import com.bitcoin.wallet.Utility.Utility;

import java.util.List;
import java.util.Random;

public class ExchangeFragmentAdapter extends RecyclerView.Adapter<ExchangeFragmentAdapter.MyViewHolder> {

    private Random r;
    private List<ExchangeModel> list;
    private AppCompatActivity activity;
    private FragmentManager fm;

    public ExchangeFragmentAdapter(List<ExchangeModel> list) {
        this.list = list;

    }

    public ExchangeFragmentAdapter() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_exchange, parent, false);
        activity = (AppCompatActivity) parent.getContext();
        fm = activity.getSupportFragmentManager();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        r = new Random();
        int random1 = r.nextInt(255) + 1;
        int random2 = r.nextInt(255) + 1;
        int random3 = r.nextInt(255) + 1;
        int cc = Color.rgb(random1, random2, random3);
        holder.bind(list.get(position), cc);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View line;
        TextView tvCurrencyUnit, tvSubtitle;
        LinearLayout cardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.adapter_exchange_line_bar);
            tvCurrencyUnit = itemView.findViewById(R.id.adapter_exchange_tv_currency_unit);
            tvSubtitle = itemView.findViewById(R.id.adapter_exchange_tv_subtitle);
            cardView = itemView.findViewById(R.id.adapter_exchange_card_linear_layout);
        }

        void bind(ExchangeModel model, int color) {
            line.setBackgroundColor(color);
            tvCurrencyUnit.setText(model.getCurrencyUnit());
            tvSubtitle.setText("exchange BTC to "+ model.getSubtitle());

            cardView.setOnClickListener(view -> {

                int currenyIconId = (Utility.getCoinIcons().get(model.getSubtitle()) != null) ? Utility.getCoinIcons().get(model.getSubtitle()) : R.drawable.bitcoin;

                ExchangeDialogFragment dialogExchange = ExchangeDialogFragment.newInstance("Bitcoin to " + model.getCurrencyUnit(),currenyIconId );
                dialogExchange.show(fm, "fragment_exchange");

            });
        }
    }
}
