package xyz.inosurvey.inosurvey;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import xyz.inosurvey.inosurvey.adapter.SurveyViewPagerAdapter;
import xyz.inosurvey.inosurvey.fragment.SurveyFragment;

public class SurveyActivity extends AppCompatActivity {

    ViewPager viewPager;
    Button previousButton, nextButton, finishButton, startButton;
    TextView introTitleTextView, introContentTextView;

    public int lastPosition;
    public static int exPo=-1;   //질문 json의 번호 컨트롤 0 = 첫번째 질문의 json 파싱
    //public static int exCount = 4; //viewpager의 총 페이지 갯수
    //public static final int INTRO_PAGE = 0;
    //public static final int RADIO_PAGE = 1;
    //public static final int CHECK_PAGE = 2;
    ArrayList<Integer> pageArray = new ArrayList<>();   //0=소개, 1=라디오 2=체크 3=별점 4=주관식

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        introTitleTextView = findViewById(R.id.introTitleTextView);
        introContentTextView = findViewById(R.id.introContentTextView);

        introTitleTextView.setText("에에에");
        introContentTextView.setText("이겁니다");

        pageArray.add(0);
        pageArray.add(1);
        pageArray.add(2);
        pageArray.add(3);
        pageArray.add(4);
        pageArray.add(4);

        viewPager = findViewById(R.id.viewPager);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        finishButton = findViewById(R.id.finishButton);
        startButton = findViewById(R.id.startButton);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("설문소개");
        ab.setDisplayHomeAsUpEnabled(true);
        //getJson("url", "POST");

        viewPager.setAdapter(new SurveyViewPagerAdapter(getSupportFragmentManager(), pageArray));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("position : " + position);
                if(position == 0){
                    startButton.setVisibility(View.VISIBLE);
                    previousButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                }else if(position == 1){
                    startButton.setVisibility(View.GONE);
                    previousButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.VISIBLE);
                    introTitleTextView.setVisibility(View.GONE);
                    introContentTextView.setVisibility(View.GONE);
                } else if(position +1 == pageArray.size()){
                    startButton.setVisibility(View.GONE);
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.GONE);
                    finishButton.setVisibility(View.VISIBLE);
                }else if(position>=1 && exPo<pageArray.size()){
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    finishButton.setVisibility(View.GONE);
                    startButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                exPo--;
                viewPager.setCurrentItem(currentPage-1, true);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                exPo++;
                System.out.println("exPo : " + exPo);
                viewPager.setCurrentItem(currentPage + 1, true);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                exPo++;
                System.out.println("exPo : " + exPo);
                viewPager.setCurrentItem(currentPage + 1, false);
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finishAlert();
            }
        });

    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this); 최상위 액티비티로 이동 후 아래에 있는 모든 activity destroy
                this.finish();
                exPo = 0;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void finishAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("설문이 완료되었습니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exPo = -1;
                finish();
            }
        });

        builder.show();
    }


    public void getJson(String url, String method) {

        class GetDataJson extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(String... params) {
                System.out.println("flag20");
                String uri = params[0];
                System.out.println("flag30");
                String method = params[1];
                System.out.println("flag40");
                if (params == null || params.length < 1)
                    return null;
                BufferedReader bufferedReader = null;
                try {
                    System.out.println("flag50");
                    URL url = new URL(uri);
                    System.out.println("flag60");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    System.out.println("flag2");
                    con.setRequestMethod(method);
                    System.out.println("flag3");
                    con.setRequestProperty("Accept", "application/json");
                    System.out.println("flag4");
                    con.setDoInput(true);
                    System.out.println("flag5");
                    con.setUseCaches(false);
                    System.out.println("flag6");
                    con.setDefaultUseCaches(false);
                    System.out.println("flag7");
                    con.setDoOutput(true);
                    System.out.println("flag8");
                    OutputStream os = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("id="+1);
                    //writer.write("user_id="+idText+"&password="+pwText);
                    writer.flush();
                    writer.close();
                    os.close();
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

            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url, method);
    }
}
