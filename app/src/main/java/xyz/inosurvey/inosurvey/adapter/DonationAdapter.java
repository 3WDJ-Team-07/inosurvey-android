package xyz.inosurvey.inosurvey.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import xyz.inosurvey.inosurvey.DonationActivity;
import xyz.inosurvey.inosurvey.ItemData.*;
import java.util.ArrayList;
import xyz.inosurvey.inosurvey.R;
import xyz.inosurvey.inosurvey.bean.DonationList;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder>{

    private ArrayList<DonationData> donationDataSet;
    private ArrayList<DonationList> donationListArray;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView titleTextView, amountTextView, percentTextView;
        public ProgressBar progressBar;

        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.imageView);
            titleTextView = view.findViewById(R.id.titleTextView);
            amountTextView = view.findViewById(R.id.amountTextView);
            progressBar = view.findViewById(R.id.progressBar);
            percentTextView = view.findViewById(R.id.percentTextView);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(), DonationActivity.class);
            intent.putParcelableArrayListExtra("data", donationListArray);
            intent.putExtra("position", position);

            v.getContext().startActivity(intent);
        }
    }

    public DonationAdapter(ArrayList<DonationData> newDonationDataSet, ArrayList<DonationList> donationLists){
        donationDataSet = newDonationDataSet;
        this.donationListArray = donationLists;
    }

    @Override
    public DonationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_donation, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.imageView.setImageDrawable(donationDataSet.get(position).imageView);
        holder.titleTextView.setText(donationDataSet.get(position).titleTextView);
        holder.amountTextView.setText(donationDataSet.get(position).amountTextView);
        holder.progressBar.setProgress(donationDataSet.get(position).progressBar);
        holder.percentTextView.setText(donationDataSet.get(position).percentTextView);
    }

    @Override
    public int getItemCount(){
        return donationDataSet.size();
    }

    public void refresh(){
        notifyDataSetChanged();
    }
}
