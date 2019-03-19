package xyz.inosurvey.inosurvey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import xyz.inosurvey.inosurvey.ItemData.*;

import java.util.ArrayList;

import xyz.inosurvey.inosurvey.adapter.MarketAdapter;

public class MarketFragment extends Fragment {

    private RecyclerView marketView;
    private RecyclerView.Adapter marketAdapter;
    private RecyclerView.LayoutManager marketLayoutManager;
    private ArrayList<MarketData> marketDataSet;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        //fragment에서 findViewById를 호출 할 때, nullPointException이 일어나는 이유?
        //inflation 이전에 View 컴포넌트 함수를 호출 했기 때문이다.
        //그러므로 View 객체를 이용해 함수 호출을 한다.
        View v = inflater.inflate(R.layout.fragment_market,container,false);
        marketView = v.findViewById(R.id.marketView);
        marketView.setHasFixedSize(true);
        //fragment에서 context를 받아오기 위해선 getActivity()를 하면된다.
        marketLayoutManager = new LinearLayoutManager(getActivity());
        //레이아웃 매니저로 linear layout을 사용한다.
        marketView.setLayoutManager(marketLayoutManager);

        //adapter에 데이터 셋팅을 해주고, recyclerView에 탑재해준다.
        marketDataSet = new ArrayList<>();
        marketAdapter = new MarketAdapter(marketDataSet);
        marketView.setAdapter(marketAdapter);

        marketDataSet.add(new MarketData(null, "상품", "가격", "회사"));
        marketDataSet.add(new MarketData(null, "상품", "가격", "회사"));
        marketDataSet.add(new MarketData(null, "상품", "가격", "회사"));

        //GetData g = new GetData();
        //g.getJson("http://172.26.3.14:8000/generateToken", "post");

        return v;
    }
}
