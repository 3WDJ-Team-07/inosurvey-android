package xyz.inosurvey.inosurvey.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xyz.inosurvey.inosurvey.ItemData.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import xyz.inosurvey.inosurvey.LoginActivity;
import xyz.inosurvey.inosurvey.MainActivity;
import xyz.inosurvey.inosurvey.adapter.DonationAdapter;
import xyz.inosurvey.inosurvey.R;
import xyz.inosurvey.inosurvey.bean.DonationList;

public class DonationListFragment extends Fragment {

    private RecyclerView donationView;
    private RecyclerView.Adapter donationAdapter;
    private RecyclerView.LayoutManager donationLayoutManager;
    private ArrayList<DonationData> donationDataSet;
    private String imageURL;
    private ArrayList<DonationList> donationListArray = new ArrayList<>();
    private Drawable imageDrawable;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*
        ************************************************************************
        fragment에서 findViewById를 호출 할 때, nullPointException?
        inflation 이전에 View 컴포넌트 함수 호출 안 됨
        View 객체를 이용해 함수 호출을 한다.
        ************************************************************************
        */
        getDonationList("http://172.26.2.77:8000/api/donation/index", "GET");
        View v = inflater.inflate(R.layout.fragment_donation_list, container, false);
        donationView = v.findViewById(R.id.donationView);

        return v;
    }


    public void getDonationList(String url, String method) {

        class getJson extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            @Override
            protected void onPreExecute(){
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("잠시만 기다려주세요.");
                progressDialog.show();
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String method = params[1];

                if (params == null || params.length < 1)
                    return null;

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod(method);
                    con.setRequestProperty("Accept", "application/json");
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    con.setReadTimeout(100000); //10초동안 서버로부터 반응없으면 에러
                    con.setConnectTimeout(15000); //접속하는 커넥션 타임 15초동안 접속안되면 접속안되는 것으로 간주 (ms)
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    con.connect();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if(result == null){
                    System.out.println("donation result is null");
                    Toast.makeText(getContext(), "기부리스트 값이 null입니다", Toast.LENGTH_LONG).show();
                    return;
                }
                donationView.setHasFixedSize(true);
                donationLayoutManager = new LinearLayoutManager(getActivity());
                donationView.setLayoutManager(donationLayoutManager);
                donationDataSet = new ArrayList<>();
                donationAdapter = new DonationAdapter(donationDataSet, donationListArray);
                donationView.setAdapter(donationAdapter);
                System.out.println("donationList's result = " + result);
                String resultJSON = result;
                addDonationList(resultJSON);
                progressDialog.dismiss();
            }
        }
        getJson g = new getJson();
        g.execute(url, method);
    }

    private void addDonationList(String getJSON) {
        try {
            JSONObject donationListJSONObject = new JSONObject(getJSON);
            JSONArray donationJSONArray = donationListJSONObject.getJSONArray("donation");
            for (int i = 0; i < donationJSONArray.length(); i++) {
                JSONObject donationJSONObject = donationJSONArray.getJSONObject(i);
                int id = donationJSONObject.getInt("id");
                String title = donationJSONObject.getString("title");
                String content = donationJSONObject.getString("content");
                String company = "기부회사";
                String image = donationJSONObject.getString("image");
                int targetAmount = donationJSONObject.getInt("target_amount");
                int currentAmount = donationJSONObject.getInt("current_amount");
                int donatorID = donationJSONObject.getInt("donator_id");
                String createdAt = donationJSONObject.getString("created_at");
                String closedAt = donationJSONObject.getString("closed_at");
                System.out.println(id + title + content + company + image + targetAmount + currentAmount + donatorID + createdAt + closedAt + "addDonation's List index");
                DonationList donationList = new DonationList(id, title, content, company, image, targetAmount,
                        currentAmount, donatorID, createdAt, closedAt);
                donationListArray.add(i, donationList);
            }
            addList();
            }catch (JSONException e) {
                Toast.makeText(getContext(), "도네이션 json parsing error!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return;
            }
    }
    public void addList(){
        for(int i=0; i<donationListArray.size(); i++) {
            DonationList donationList = donationListArray.get(i);
            imageURL = donationList.getImage();
            Thread thread = new Thread(){
                @Override
                public void run(){
                    try{
                        URL url = new URL(imageURL);
                        HttpURLConnection con = (HttpURLConnection)url.openConnection();
                        con.setDoInput(true);
                        con.connect();
                        InputStream inputStream = (InputStream)url.getContent();
                        imageDrawable = Drawable.createFromStream(inputStream, "");
                        inputStream.close();
                    }catch(MalformedURLException e){
                        e.printStackTrace();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            try{
                thread.join();
            }catch(InterruptedException e){
                System.out.println("mzmzmz");
                e.printStackTrace();
            }
            String title = donationList.getTitle().trim();
            int targetAmount = donationList.getTargetAmount();
            int currentAmount = donationList.getCurrentAmount();
            int amount = donationList.getTargetAmount()-donationList.getCurrentAmount();
            int amountPercent;
            if(currentAmount != 0) {
                amountPercent = (currentAmount / targetAmount) * 100;
                System.out.println(amountPercent + "남은 금액 퍼센트");
            }else {
                amountPercent = 0;
            }
            System.out.println(amount + "amount");
            donationDataSet.add(new DonationData(imageDrawable, title, "남은 금액 : "+ amount, amountPercent , amountPercent+"%"));
        }
    }

    public void onResume(){

        super.onResume();
    }
}
