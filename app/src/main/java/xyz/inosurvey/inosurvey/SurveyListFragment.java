package xyz.inosurvey.inosurvey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.inosurvey.inosurvey.ItemData.SurveyListData;
import xyz.inosurvey.inosurvey.adapter.SurveyListAdapter;

public class SurveyListFragment extends Fragment {

    private RecyclerView surveyListView;
    private RecyclerView.LayoutManager surveyListLayoutManager;
    private RecyclerView.Adapter surveyListAdapter;
    private ArrayList<SurveyListData> surveyListDataSet;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        //GetData g = new GetData();
        //g.getJson("http://172.26.2.232:8000/test");
        View v = inflater.inflate(R.layout.fragment_survey_list,container,false);
        surveyListView = v.findViewById(R.id.surveyListView);
        surveyListView.setHasFixedSize(true);

        surveyListLayoutManager = new LinearLayoutManager(getActivity());
        surveyListView.setLayoutManager(surveyListLayoutManager);

        surveyListDataSet = new ArrayList<>();
        surveyListAdapter = new SurveyListAdapter(surveyListDataSet);
        surveyListView.setAdapter(surveyListAdapter);

        surveyListDataSet.add(new SurveyListData("설문제목1","적립 코인","남은기간","xxx코인", "10일남음"));
        surveyListDataSet.add(new SurveyListData("설문제목2","적립 코인","남은기간","xyy코인", "10일남음"));
        surveyListDataSet.add(new SurveyListData("설문제목3","적립 코인","남은기간","xyz코인", "10일남음"));

        return v;
    }
}
