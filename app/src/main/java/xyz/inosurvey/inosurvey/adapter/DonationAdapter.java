package xyz.inosurvey.inosurvey.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import xyz.inosurvey.inosurvey.DonationActivity;
import xyz.inosurvey.inosurvey.ItemData.*;
import java.util.ArrayList;
import xyz.inosurvey.inosurvey.R;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder>{

    private ArrayList<DonationData> donationDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView titleTextView, donationTextView, companyTextView;

        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.imageView);
            titleTextView = view.findViewById(R.id.titleTextView);
            companyTextView = view.findViewById(R.id.companyTextView);
            donationTextView = view.findViewById(R.id.donationTextView);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DonationActivity.class);
            v.getContext().startActivity(intent);
        }
    }

    public DonationAdapter(ArrayList<DonationData> newDonationDataSet){
        donationDataSet = newDonationDataSet;
    }

    @Override
    public DonationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_donation, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.imageView.setImageResource(donationDataSet.get(position).imageView);
        holder.titleTextView.setText(donationDataSet.get(position).titleTextView);
        holder.companyTextView.setText(donationDataSet.get(position).companyTextView);
        holder.donationTextView.setText(donationDataSet.get(position).donationTextView);
    }

    @Override
    public int getItemCount(){
        return donationDataSet.size();
    }
}