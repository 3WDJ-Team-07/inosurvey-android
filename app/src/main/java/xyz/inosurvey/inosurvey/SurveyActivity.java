package xyz.inosurvey.inosurvey;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import xyz.inosurvey.inosurvey.adapter.SurveyViewPagerAdapter;
import xyz.inosurvey.inosurvey.bean.SurveyList;
import xyz.inosurvey.inosurvey.fragment.SurveyFragment;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class SurveyActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button previousButton, nextButton, finishButton, startButton;
    private TextView introTitleTextView, introContentTextView;
    private ActionBar ab;
    private ArrayList<SurveyList> surveyListArray = new ArrayList<>();
    private int listPosition;
    private String surveyTitle, surveyDescription, surveyCoin, surveyBackgrounColor;
    private int surveyID, surveyIsSale;
    public static int controlPosition=-1;   //질문 json의 번호 컨트롤 0 = 첫번째 질문의 json 파싱
    public boolean responsive_filtering = true;
    private String getUserJSON;
    private int userID;
    private ArrayList<String> checkBoxAnswerArrayList;
    public String getSurveyJSON;
    public int questionNumber = 0;
    public int questionTypeId = 0;
    public int questionFiltering = 0;
    public JSONObject surveyJSONObject = null;
    public JSONArray questionJSONArray = null;
    public int questionId = 0;
    public String questionTitle = null;
    public JSONObject questionJSONObject = null;
    public JSONArray questionItemsJSONArray = null;
    public JSONObject questionItemsJSONObject = null;
    public JSONObject resultSurveyJSON = new JSONObject();

    public ArrayList<Integer> questionIdArrayList = new ArrayList<>();
    public ArrayList<String> answerArrayList = new ArrayList<>();

    //*************************************************************************************
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        introTitleTextView = findViewById(R.id.introTitleTextView);
        introContentTextView = findViewById(R.id.introContentTextView);
        viewPager = findViewById(R.id.viewPager);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        finishButton = findViewById(R.id.finishButton);
        startButton = findViewById(R.id.startButton);

        getSurveyData();

        introTitleTextView.setText(surveyTitle);
        introContentTextView.setText(surveyDescription);

        //액션바 컨트롤
        ab = getSupportActionBar();
        ab.setTitle("설문소개");
        ab.setDisplayHomeAsUpEnabled(true);

        getUserJson("http://172.26.2.77:8000/api/user/check", "POST");

        System.out.println(getUserJSON + "bbb");

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
                    ab.setTitle("설문진행");
                    if(questionJSONArray.length() == 1){
                        startButton.setVisibility(View.GONE);
                        previousButton.setVisibility(View.GONE);
                        nextButton.setVisibility(View.GONE);
                        finishButton.setVisibility(View.VISIBLE);
                        introTitleTextView.setVisibility(View.GONE);
                        introContentTextView.setVisibility(View.GONE);
                    }else {
                        startButton.setVisibility(View.GONE);
                        previousButton.setVisibility(View.GONE);
                        nextButton.setVisibility(View.VISIBLE);
                        introTitleTextView.setVisibility(View.GONE);
                        introContentTextView.setVisibility(View.GONE);
                    }
                } else if(position +1 == questionJSONArray.length()+1){
                    startButton.setVisibility(View.GONE);
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.GONE);
                    finishButton.setVisibility(View.VISIBLE);
                }else if(position>=1 && controlPosition<questionJSONArray.length()+1){
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
                controlPosition--;
                int currentPage = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentPage-1, true);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                insertAnswer();
                insertQuestionId();
                int currentPage = viewPager.getCurrentItem();
                controlPosition++;
                System.out.println("nextButton.controlPosition's Value = " + controlPosition);
                getParseJSON();
                viewPager.setCurrentItem(currentPage + 1, true);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                controlPosition++;
                System.out.println("controlPosition's Value = " + controlPosition);
                getParseJSON();
                viewPager.setCurrentItem(currentPage + 1, false);
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                insertAnswer();
                insertQuestionId();
                finishAlert();
            }
        });

    }
    //*****************************************************************************************

    //****************************************************************************
    //설문 리스트에서 받아온 데이터를 복구하고 변수를 넣는 메소드
    private void getSurveyData(){
        Intent intent = getIntent();
        surveyListArray = intent.getParcelableArrayListExtra("data");
        listPosition = intent.getIntExtra("position", 1);
        surveyID = surveyListArray.get(listPosition).getId();
        surveyTitle = surveyListArray.get(listPosition).getTitle();
        surveyDescription = surveyListArray.get(listPosition).getDescription();
        surveyCoin = surveyListArray.get(listPosition).getCoin();
        surveyIsSale = surveyListArray.get(listPosition).getIsSale();
        surveyBackgrounColor = surveyListArray.get(listPosition).getBackgroundColor();
        System.out.println("surveyID's Value = " + surveyID);
        System.out.println("surveyTitle's Value = " + surveyTitle);
    }
    //************************************************************************
    public void parseUserJSON(){
        try {
            JSONObject userJSONObject = new JSONObject(getUserJSON);
            System.out.println("abcd");
            JSONObject userObject = userJSONObject.getJSONObject("user");
            userID = userObject.getInt("id");

            System.out.println(userID + "useruser");
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    public void getParseJSON(){
        try {
            surveyJSONObject = new JSONObject(getSurveyJSON);
            questionJSONArray = surveyJSONObject.getJSONArray("questionItem");
            if(questionJSONArray.length() == 0) {
                System.out.println("getParseJSON's err situation : " + "surveyJSONObject = " + surveyJSONObject + "  questionJSONArray's length = " + questionJSONArray.length());
            }
            questionJSONObject = questionJSONArray.getJSONObject(controlPosition);
            questionId = questionJSONObject.getInt("id");
            System.out.println("getParseJSON's 질문 고유 번호 : " + questionId);
            questionTitle = questionJSONObject.getString("question_title");
            System.out.println("getParseJSON's 질문 제목 : " + questionTitle);
            questionNumber = questionJSONObject.getInt("question_number");
            System.out.println("getParseJSON's 타입 넘버 : " + questionNumber);
            questionTypeId = questionJSONObject.getInt("type_id");
            System.out.println("getParseJSON's 타입 id : " + questionTypeId);
            if(questionTypeId == 7){
                questionFiltering = questionJSONObject.getJSONObject("filtering_item").getInt("item_id");
            }
            System.out.println("getParseJSON's 질문 필터링 번호 : " + questionFiltering);
            questionItemsJSONArray = questionJSONObject.getJSONArray("question_items");
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    public void insertAnswer(){
        SurveyFragment sf = (SurveyFragment)getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager+":"+viewPager.getCurrentItem());
        if(questionTypeId == 1){
            String answer = sf.getRadioAnswer();
            answerArrayList.add(controlPosition, answer);
            System.out.println(answerArrayList.get(controlPosition) + "answer");
        }else if(questionTypeId == 2){
            String answer = sf.getEditTextAnswer();
            answerArrayList.add(controlPosition, answer);
            System.out.println(answerArrayList.get(controlPosition) + "answer");
        }else if(questionTypeId == 3){
            String answer = sf.getCheckBoxAnswer();
            answerArrayList.add(controlPosition, answer);
        }else if(questionTypeId == 4){
            String answer = String.format("%.0f", sf.getRatingScoreAnswer());
            answerArrayList.add(controlPosition, answer);
        }else if(questionTypeId == 5){
            String answer = sf.getOpinionTextAnswer();
            answerArrayList.add(controlPosition, answer);
        }else if(questionTypeId == 6){
            String answer = sf.getRadioAnswer();
            answerArrayList.add(controlPosition, answer);
        }else if(questionTypeId == 7){
            String answer = sf.getType7Answer();
            answerArrayList.add(controlPosition, answer);
        }
    }



    public String postParseJSON(){
        try {
            resultSurveyJSON.put("user_id", userID);
            resultSurveyJSON.put("form_id", surveyID);
            resultSurveyJSON.put("responsive_bool", responsive_filtering);
            JSONArray questionArray = new JSONArray();
            for(int i = 0; i<answerArrayList.size(); i++){
                JSONObject pushItemObject = new JSONObject();
                pushItemObject.put("question_id", questionIdArrayList.get(i));
                pushItemObject.put("item", answerArrayList.get(i));
                questionArray.put(pushItemObject);
            }
            resultSurveyJSON.put("question", questionArray);

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultSurveyJSON.toString();
    }



    public void insertQuestionId(){
        questionIdArrayList.add(controlPosition, questionId);
    }



    public void finishAlert(){
        if(responsive_filtering == false){
            DBHelper helper = new DBHelper(getApplicationContext(), "survey_list", null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "DELETE FROM survey_list WHERE id = " + surveyID;
            db.execSQL(sql);
            db.close();
            helper.close();
            controlPosition = -1;
            postAnswerJSON("http://172.26.2.77:8000/api/response/create", "POST");
            Intent intent = new Intent(getApplicationContext(), SurveyCompleteActivity.class);
            intent.putExtra("ino", surveyCoin);
            startActivity(intent);
            finish();
        }else if(responsive_filtering == true){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("알림");
            builder.setMessage("설문을 완료하시겠습니까?");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DBHelper helper = new DBHelper(getApplicationContext(), "survey_list", null, 1);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    String sql = "DELETE FROM survey_list WHERE id = " + surveyID;
                    db.execSQL(sql);
                    db.close();
                    helper.close();
                    controlPosition = -1;
                    postAnswerJSON("http://172.26.2.77:8000/api/response/create", "POST");
                    Intent intent = new Intent(getApplicationContext(), SurveyCompleteActivity.class);
                    intent.putExtra("ino", surveyCoin);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();
        }
    }

        public void getSurveyItemJSON(String url, String method){

            class GetDataJson extends AsyncTask<String, Void, String> {
                ProgressDialog progressDialog = new ProgressDialog(SurveyActivity.this);
                @Override
                protected void onPreExecute(){
                    if(SurveyActivity.this.isFinishing() == false) {
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMessage("잠시만 기다려주세요.");
                        progressDialog.show();
                        super.onPreExecute();
                    }
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
                        con.setRequestProperty("Accept","application/json");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        con.setReadTimeout(100000); //10초동안 서버로부터 반응없으면 에러
                        con.setConnectTimeout(15000); //접속하는 커넥션 타임 15초동안 접속안되면 접속안되는 것으로 간주 (ms)
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        con.setUseCaches(false);
                        con.setDefaultUseCaches(false);
                        OutputStream os = con.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        System.out.println("async's surveyID : " + surveyID);
                        writer.write("id="+surveyID);
                        writer.flush();
                        writer.close();
                        os.close();
                        writer.close();
                        con.connect();
                        StringBuilder sb = new StringBuilder();
                        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String json;
                        if(isCancelled()){
                            return null;
                        }
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json + "\n");
                        }
                        bufferedReader.close();
                        return sb.toString().trim();
                    } catch (Exception e) {
                        return null;
                    }
                }
                @Override
                protected void onPostExecute(String result){
                    progressDialog.dismiss();
                    System.out.println(result + "bobobo");
                    getSurveyJSON = result;
                    if(getSurveyJSON != null) {
                        getParseJSON();
                        //viewPager 어뎁터 설정
                        if(questionJSONArray == null){
                            if(SurveyActivity.this.isFinishing() == false) {
                                Toast.makeText(getApplicationContext(), "questionJSONArray is null, viewPager null error!", LENGTH_LONG).show();
                            }
                            return;
                        }
                        if(questionJSONArray.length() == 0){
                            if(SurveyActivity.this.isFinishing() == false) {
                                Toast.makeText(getApplicationContext(), "viewPager count가 0이라 에러!", LENGTH_LONG).show();
                            }
                            System.out.println("설문 아이템 받아 온 후 JSONArray의 길이 만큼 viewPager에 count를 넘겨줌 길이 : " + questionJSONArray.length());
                            finish();
                            return;
                        }else
                            viewPager.setAdapter(new SurveyViewPagerAdapter(getSupportFragmentManager(), questionJSONArray.length()+1));
                    }else {
                        if(SurveyActivity.this.isFinishing() == false) {
                            Toast.makeText(getApplicationContext(), "설문 아이템 값이 null", LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                protected void onCancelled(){
                    super.onCancelled();
                    System.out.println("요청 취소!");
                }
            }
            GetDataJson g = new GetDataJson();
            g.execute(url, method);
        }

    public void postAnswerJSON(String url, String method){

        class GetDataJson extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                String method = params[1];
                String postResultData = postParseJSON();
                System.out.println(postParseJSON() + "postParseJSON");

                if (params == null || params.length < 1)
                    return null;

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept","application/json");
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    con.setReadTimeout(100000); //10초동안 서버로부터 반응없으면 에러
                    con.setConnectTimeout(15000); //접속하는 커넥션 타임 15초동안 접속안되면 접속안되는 것으로 간주 (ms)
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    OutputStream os = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    System.out.println(postResultData.getBytes("utf-8") + "postResultDATA");
                    writer.write("data="+postResultData);
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
            protected void onPostExecute(String result){
                System.out.println(result + "postResult");

            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url, method);
    }

    public void getUserJson(String url, String method) {

        class GetDataJson extends AsyncTask<String, Void, String> {

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
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    OutputStream os = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("access_token=" + LoginActivity.jwtToken);
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
                getUserJSON = result;
                System.out.println("popo" + getUserJSON);
                if(getUserJSON == null) {
                    Toast.makeText(getApplicationContext(), "jwt 토큰 인증 실패", Toast.LENGTH_SHORT).show();
                    return;
                }
                parseUserJSON();
                getSurveyItemJSON("http://172.26.2.77:8000/api/response/questions", "POST");
            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url, method);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this); 최상위 액티비티로 이동 후 아래에 있는 모든 activity destroy
                this.finish();
                controlPosition = -1;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        controlPosition=-1;
    }

    public void filteringFinishButton(){
        finishButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.VISIBLE);
    }
    public void filteringNextButton(){
        nextButton.setVisibility(View.GONE);
        finishButton.setVisibility(View.VISIBLE);
    }

}
