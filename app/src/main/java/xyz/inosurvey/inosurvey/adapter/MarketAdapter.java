package xyz.inosurvey.inosurvey.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import xyz.inosurvey.inosurvey.ItemData.*;
import java.util.ArrayList;

import xyz.inosurvey.inosurvey.R;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder>{

    private ArrayList<MarketData> marketDataSet;
    //public MarketAdapter(){}
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productImageView;
        public TextView productTextView, coinTextView, companyTextView;

        public ViewHolder(View view){
            super(view);
            productImageView = view.findViewById(R.id.productImageView);
            productTextView = view.findViewById(R.id.productTextView);
            coinTextView = view.findViewById(R.id.coinTextView);
            companyTextView = view.findViewById(R.id.companyTextView);
        }
    }

    public MarketAdapter(ArrayList<MarketData> newMarketDataSet){
        marketDataSet = newMarketDataSet;
    }

    @Override
    public MarketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_market, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.productImageView.setImageResource(marketDataSet.get(position).productImg);
        holder.productTextView.setText(marketDataSet.get(position).productText);
        holder.coinTextView.setText(marketDataSet.get(position).coinText);
        holder.companyTextView.setText(marketDataSet.get(position).companyText);
    }

    @Override
    public int getItemCount(){
        return marketDataSet.size();
    }
}


