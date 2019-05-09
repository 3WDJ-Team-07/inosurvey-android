package xyz.inosurvey.inosurvey.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import xyz.inosurvey.inosurvey.ItemData.*;
import xyz.inosurvey.inosurvey.MainActivity;
import xyz.inosurvey.inosurvey.R;
import xyz.inosurvey.inosurvey.SurveyActivity;
import xyz.inosurvey.inosurvey.bean.SurveyList;

//recyclerView 상속, 이 안에는 ViewHolder를 상속 받아야만 함.
public class SurveyListAdapter extends RecyclerView.Adapter<SurveyListAdapter.ViewHolder>{
    private ArrayList<SurveyList> surveyListArray;
    private ArrayList<SurveyListData> surveyListDataSet;

    //어댑터 생성자, 매개변수 : ArrayList<SurveyListData>
    public SurveyListAdapter(ArrayList<SurveyListData> newSurveyListDataSet, ArrayList<SurveyList> surveyListArray){
        surveyListDataSet = newSurveyListDataSet;
        this.surveyListArray = surveyListArray;
    }

    //viewHolder 클래스 정의
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView surveyTitleTextView, coinTextView, timeTextView, surveyCoinTextView, surveyTimeTextView, progressBarTextView;
        public ProgressBar progressBar;
        public ImageView coinImageView, timeImageView;

        //아이템에 들어갈 View를 받은 후 그 View 안에 있는 widget들을 초기화한다.
        public ViewHolder(View view){
            super(view);
            surveyTitleTextView = view.findViewById(R.id.surveyTitleTextView);
            coinTextView = view.findViewById(R.id.coinTextView);
            timeTextView = view.findViewById(R.id.timeTextView);
            surveyCoinTextView = view.findViewById(R.id.surveyCoinTextView);
            surveyTimeTextView = view.findViewById(R.id.surveyTimeTextView);
            progressBarTextView = view.findViewById(R.id.progressBarTextView);
            progressBar = view.findViewById(R.id.progressBar);
            coinImageView = view.findViewById(R.id.coinImageView);
            timeImageView = view.findViewById(R.id.timeImageView);

            //이벤트 리스너 등록
            view.setOnClickListener(this);
        }

        //recyclerView에 대한 onClick 정의
        //adaper 형식의 activity 혹은 fragment가 없는 class는 view 객체에서 context를 받아와야한다.
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(), SurveyActivity.class);
            intent.putParcelableArrayListExtra("data", surveyListArray);
            intent.putExtra("position", position);

            //View 객체 v에서 context를 받아와 activity start
            v.getContext().startActivity(intent);
        }
    }

    //ViewHolder를 create하는 메소드,
    //create하기전 view를 찾는 과정을 거치며,
    //inflate된 view를 ViewHolder의 매개변수로 넣으며, 이 ViewHolder를 리턴한다.
    @Override
    public SurveyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_survey_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //RecyclerView의 item의 셋팅과 사용자가 스크롤링 할 때, 호출되는 메소드로 DataSet의 positon을 보여줌(바인딩)
    @Override
    public void onBindViewHolder(SurveyListAdapter.ViewHolder holder, int position){
        holder.surveyTitleTextView.setText(surveyListDataSet.get(position).surveyTitleText);
        holder.coinTextView.setText(surveyListDataSet.get(position).coinText);
        holder.timeTextView.setText(surveyListDataSet.get(position).timeText);
        holder.surveyCoinTextView.setText(surveyListDataSet.get(position).surveyCoinText);
        holder.surveyTimeTextView.setText(surveyListDataSet.get(position).surveyTimeText);
        holder.progressBarTextView.setText(surveyListDataSet.get(position).progressBarText);
        holder.progressBar.setProgress(surveyListDataSet.get(position).progressBar);
        holder.coinImageView.setImageResource(surveyListDataSet.get(position).coinImageView);
        holder.timeImageView.setImageResource(surveyListDataSet.get(position).timeImageView);
    }

    //아이템의 갯수 카운트
    @Override
    public int getItemCount(){
        return surveyListDataSet.size();
    }
}
