package xyz.inosurvey.inosurvey.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import xyz.inosurvey.inosurvey.ItemData.*;
import xyz.inosurvey.inosurvey.R;

public class SurveyListAdapter extends RecyclerView.Adapter<SurveyListAdapter.ViewHolder>{

    private ArrayList<SurveyListData> surveyListDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView surveyTitleTextView, coinTextView, timeTextView, surveyCoinTextView, surveyTimeTextView;

        public ViewHolder(View view){
            super(view);
            surveyTitleTextView = view.findViewById(R.id.surveyTitleTextView);
            coinTextView = view.findViewById(R.id.coinTextView);
            timeTextView = view.findViewById(R.id.timeTextView);
            surveyCoinTextView = view.findViewById(R.id.surveyCoinTextView);
            surveyTimeTextView = view.findViewById(R.id.surveyTimeTextView);
        }
    }

    public SurveyListAdapter(ArrayList<SurveyListData> newSurveyListDataSet){
        surveyListDataSet = newSurveyListDataSet;
    }

    @Override
    public SurveyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_survey_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SurveyListAdapter.ViewHolder holder, int position){
        holder.surveyTitleTextView.setText(surveyListDataSet.get(position).surveyTitleText);
        holder.coinTextView.setText(surveyListDataSet.get(position).coinText);
        holder.timeTextView.setText(surveyListDataSet.get(position).timeText);
        holder.surveyCoinTextView.setText(surveyListDataSet.get(position).surveyCoinText);
        holder.surveyTimeTextView.setText(surveyListDataSet.get(position).surveyTimeText);
    }

    @Override
    public int getItemCount(){
        return surveyListDataSet.size();
    }
}
