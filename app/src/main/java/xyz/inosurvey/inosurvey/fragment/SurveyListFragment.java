package xyz.inosurvey.inosurvey.fragment;

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
import xyz.inosurvey.inosurvey.MainActivity;
import xyz.inosurvey.inosurvey.adapter.SurveyListAdapter;
import xyz.inosurvey.inosurvey.R;

public class SurveyListFragment extends Fragment {

    public MainActivity activity;
    private RecyclerView surveyListView;
    private RecyclerView.LayoutManager surveyListLayoutManager;
    private RecyclerView.Adapter surveyListAdapter;
    private ArrayList<SurveyListData> surveyListDataSet;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        //ActionBar ab = get
        //GetData g = new GetData();
        //g.getJson("http://172.26.2.232:8000/test");
        View v = inflater.inflate(R.layout.fragment_survey_list,container,false);
        surveyListView = v.findViewById(R.id.surveyListView);
        surveyListView.setHasFixedSize(true);

        surveyListLayoutManager = new LinearLayoutManager(getActivity());
        surveyListView.setLayoutManager(surveyListLayoutManager);

        surveyListDataSet = new ArrayList<>();
        //http://dev.youngkyu.kr/36  context 관련 글 참고하기
        //https://arabiannight.tistory.com/entry/272 이거도
        surveyListAdapter = new SurveyListAdapter(surveyListDataSet);
        surveyListView.setAdapter(surveyListAdapter);

        System.out.println(getActivity().getApplication() + "abcd");

        surveyListDataSet.add(new SurveyListData("설문제목1","적립 코인","남은기간","xxx코인", "10일남음"));
        surveyListDataSet.add(new SurveyListData("설문제목2","적립 코인","남은기간","xyy코인", "10일남음"));
        surveyListDataSet.add(new SurveyListData("설문제목3","적립 코인","남은기간","xyz코인", "10일남음"));

        return v;
    }
}
