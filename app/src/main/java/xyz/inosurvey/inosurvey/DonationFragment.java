package xyz.inosurvey.inosurvey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import xyz.inosurvey.inosurvey.ItemData.*;
import java.util.ArrayList;
import xyz.inosurvey.inosurvey.adapter.DonationAdapter;

public class DonationFragment extends Fragment {

    private RecyclerView donationView;
    private RecyclerView.Adapter donationAdapter;
    private RecyclerView.LayoutManager donationLayoutManager;
    private ArrayList<DonationData> donationDataSet;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        /*
        ************************************************************************
        fragment에서 findViewById를 호출 할 때, nullPointException?
        inflation 이전에 View 컴포넌트 함수 호출 안 됨
        View 객체를 이용해 함수 호출을 한다.
        ************************************************************************
        */
        View v = inflater.inflate(R.layout.fragment_donation_list,container,false);
        donationView = v.findViewById(R.id.donationView);
        donationView.setHasFixedSize(true);
        //fragment에서 context를 받아오기 위해서 getActivity()
        donationLayoutManager = new LinearLayoutManager(getActivity());
        //레이아웃 매니저: linearLayout.
        donationView.setLayoutManager(donationLayoutManager);

        //adapter에 데이터 셋팅을 해주고, recyclerView에 탑재.
        donationDataSet = new ArrayList<>();
        donationAdapter = new DonationAdapter(donationDataSet);
        donationView.setAdapter(donationAdapter);

        donationDataSet.add(new DonationData(R.drawable.bbadda, "??? :훠훠훠줴뮈있눈청원이군요", "그래서줜쥉화자는겁니꽈?", "이 기부처에 x이노 기부하셨습니다"));
        donationDataSet.add(new DonationData(R.drawable.verysadpepe, "기부 제목", "기부처", "얼마기부했냐?"));
        donationDataSet.add(new DonationData(R.drawable.qualitypepe, "기부 제목", "기부처", "얼마기부했냐?"));
        donationDataSet.add(new DonationData(R.drawable.birthdaypepe, "기부 제목", "기부처", "얼마기부했냐?"));
        donationDataSet.add(new DonationData(R.drawable.violetpepe, "기부 제목", "기부처", "얼마기부했냐?"));
        donationDataSet.add(new DonationData(R.drawable.lovepepe, "기부 제목", "기부처", "얼마기부했냐?"));

        //GetData g = new GetData();
        //g.getJson("http://172.26.3.14:8000/generateToken", "post");

        return v;
    }
}
