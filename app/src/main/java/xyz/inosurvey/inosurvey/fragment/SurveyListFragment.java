package xyz.inosurvey.inosurvey.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;

import xyz.inosurvey.inosurvey.DBHelper;
import xyz.inosurvey.inosurvey.ItemData.SurveyListData;
import xyz.inosurvey.inosurvey.adapter.SurveyListAdapter;
import xyz.inosurvey.inosurvey.R;
import xyz.inosurvey.inosurvey.bean.SurveyList;

public class SurveyListFragment extends Fragment {

    private ArrayList<SurveyList> surveyListArray = new ArrayList<>();
    private Context context;
    private RecyclerView surveyListView;
    private RecyclerView.LayoutManager surveyListLayoutManager;
    private RecyclerView.Adapter surveyListAdapter;
    private ArrayList<SurveyListData> surveyListDataSet;

    private int getId, getRespondentNumber, getRespondentCount, getIsCompleted, getIsSale;
    private String getTitle, getCoin, getDescription, getCreatedAt, getClosedAt, getBackgroundColor ;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        //http://dev.youngkyu.kr/36  context 관련 글 참고하기
        //https://arabiannight.tistory.com/entry/272 이거도
        View v = inflater.inflate(R.layout.fragment_survey_list,container,false);
        surveyListView = v.findViewById(R.id.surveyListView);
        surveyListView.setHasFixedSize(true);
        surveyListLayoutManager = new LinearLayoutManager(getActivity());
        surveyListView.setLayoutManager(surveyListLayoutManager);
        context = getActivity();
        surveyListDataSet = new ArrayList<>();

        return v;
    }

    public void selectSurveyList(Context context){
        surveyListArray.clear();
        DBHelper helper = new DBHelper(context, "survey_list", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from survey_list where is_done=0";
        Cursor cursor = db.rawQuery(sql, null);
        SurveyList surveyList;
        int i = 0;
        while(cursor.moveToNext()){
            getId = cursor.getInt(0);
            getTitle = cursor.getString(1);
            getDescription = cursor.getString(2);
            getCoin = String.valueOf(cursor.getInt(3));
            getCreatedAt = cursor.getString(4);
            getClosedAt = cursor.getString(5);
            getRespondentCount = cursor.getInt(6);
            getRespondentNumber = cursor.getInt(7);
            getIsCompleted = cursor.getInt(8);
            getIsSale = cursor.getInt(9);
            getBackgroundColor = cursor.getString(10);
            surveyList = new SurveyList(getId, getTitle, getDescription, getCoin, getCreatedAt, getClosedAt,
                    getRespondentCount, getRespondentNumber, getIsCompleted, getIsSale, getBackgroundColor);
            surveyListArray.add(i, surveyList);
            System.out.println("flag");
            i++;
        }
        helper.close();
        db.close();
        cursor.close();
    }

    public void addList(){
        String title, coin, closedAt, percentText;
        int progressBarPercent, count, number;
        for(int i =0; i<surveyListArray.size(); i++){
            title = surveyListArray.get(i).getTitle();
            coin = surveyListArray.get(i).getCoin();
            closedAt = surveyListArray.get(i).getClosedAt();
            count = surveyListArray.get(i).getRespondentCount();
            number = surveyListArray.get(i).getRespondentNumber();
            percentText =  count/number + "%";
            progressBarPercent = Math.round(count/number)*100;
            //System.out.println(percent + " " +  " " + "counting");

            surveyListDataSet.add(new SurveyListData(title,"INO", "마감기한", coin, closedAt, percentText, progressBarPercent, R.drawable.list_ino, R.drawable.list_time));
        }
    }

    public void onResume() {
        System.out.println("onResume");
        selectSurveyList(context);
        surveyListAdapter = new SurveyListAdapter(surveyListDataSet, surveyListArray);
        surveyListDataSet.clear();
        surveyListAdapter.notifyDataSetChanged();
        surveyListView.setAdapter(surveyListAdapter);
        addList();
        super.onResume();
    }
}
