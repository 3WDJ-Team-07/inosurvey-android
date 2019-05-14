package xyz.inosurvey.inosurvey.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.inosurvey.inosurvey.ItemData.InoData;
import xyz.inosurvey.inosurvey.R;

public class InoAdapter extends RecyclerView.Adapter<InoAdapter.ViewHolder> {

    private ArrayList<InoData> inoDataSet = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, dateTextView, priceTextView;
        public ImageView priceImageView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            priceImageView = view.findViewById(R.id.priceImageView);
            priceTextView = view.findViewById(R.id.priceTextView);
        }
    }

    public InoAdapter(ArrayList<InoData> newInoDataSet){
        inoDataSet = newInoDataSet;
    }

    @Override
    public InoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ino, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(inoDataSet.get(position).titleTextView);
        holder.dateTextView.setText(inoDataSet.get(position).dateTextView);
        holder.priceImageView.setImageResource(inoDataSet.get(position).priceImageView);
        holder.priceTextView.setText(inoDataSet.get(position).priceTextView);
    }

    @Override
    public int getItemCount() {
        return inoDataSet.size();
    }

}
