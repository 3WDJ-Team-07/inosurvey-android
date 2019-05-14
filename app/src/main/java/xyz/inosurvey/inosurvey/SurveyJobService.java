package xyz.inosurvey.inosurvey;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Integer.parseInt;

public class SurveyJobService extends JobService {

    private JobParameters jobParams;
    private String getSurveyListJSON;
    private boolean jobCancelled = false;
    private String TAG = "jobservice";
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private String getUserJSON = null;
    private int userID;
    private String userNickName;
    private DBHelper helper;
    //private int userINO;
    public static int userINO;
    SharedPreferences preferences;

    @Override
    public boolean onStartJob(JobParameters params) {
        jobParams = params;
        preferences = getSharedPreferences("jwt", MODE_PRIVATE);
        userID = preferences.getInt("user_id", -1);
        userINO = preferences.getInt("user_ino", -1);
        Log.d(TAG, "job started");
        postToken("http://54.180.121.254/api/user/check", "POST");
        //doBackgroundWork(params);
        return true;
    }

    public void postToken(String url, String method){

        class dataJson extends AsyncTask<String, Void, String> {
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
                    System.out.println(LoginActivity.jwtToken + "zxcvbn");
                    SharedPreferences preferences = getSharedPreferences("jwt", MODE_PRIVATE);
                    String token = preferences.getString("jwt", null);
                    if(token == null){
                        Log.d("TAG", "toekn is null");
                        return null;
                    }
                    writer.write("access_token="+LoginActivity.jwtToken);
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
                getUserJSON = result;
                System.out.println(getUserJSON + "opi");
                if(getUserJSON != null) {
                    parseUserJSON();
                    getUserINO("http://54.180.121.254/api/user/wallet", "POST");
                    getSurveyFormJson("http://54.180.121.254/api/response/index", "POST", jobParams);
                }
            }
        }
        dataJson g = new dataJson();
        g.execute(url, method);
    }

    public void getSurveyFormJson(String url, String method, final JobParameters params) {

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
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    OutputStream os = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("user_id="+userID);
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
                if(result == null){
                    Log.d(TAG, "result is null");
                    jobFinished(params, true);
                }else {
                    getSurveyListJSON = result;
                    Log.d(TAG, "getSurveyListJSON's value = " + getSurveyListJSON);
                    insertSurveyListJSON(getSurveyListJSON);
                    jobFinished(params, true);
                }
            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url, method);
    }

    public void getUserINO(String url, String method){

        class GetINO extends AsyncTask<String, Void, String> {
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
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setDefaultUseCaches(false);
                    OutputStream os = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("id=" + userID);
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
                if(result == null){
                    System.out.println("이노 잔액 결과 null");
                    return;
                }
                if(preferences.getInt("ino", -1) == -1){
                    String getINOJSON = result;
                    System.out.println("이노 잔액 확인 JSON = " + getINOJSON);
                    try {
                        JSONObject inoObject = new JSONObject(getINOJSON);
                        String ino = inoObject.getString("current_ino");
                        System.out.println("이노 잔액 확인 String = " + ino);
                        int userINO = Integer.parseInt(ino);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("user_ino", userINO);
                        editor.commit();
                        System.out.println("userINO = " + preferences.getInt("user_ino", -1));
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        GetINO g = new GetINO();
        g.execute(url,method);
    }

    public void parseUserJSON(){
        try {
            JSONObject userJSONObject = new JSONObject(getUserJSON);
            System.out.println("abcd");
            JSONObject userObject = userJSONObject.getJSONObject("user");
            userID = userObject.getInt("id");
            String userNickName = userObject.getString("nickname");
            String userEmail = userObject.getString("email");
            SharedPreferences preferences = getSharedPreferences("jwt", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("user_id", userID);
            editor.putString("user_nickname", userNickName);
            editor.putString("user_email", userEmail);
            System.out.println("parseUserJSON's situation" + userNickName + userEmail);
            editor.commit();
            System.out.println(userID + "useruser");
        }catch(Exception e){
            System.out.println("zmzm");
            e.printStackTrace();
        }
    }

    public void insertSurveyListJSON(String surveyListJSON){
        int dbInsertResult;
        try{
            int id;
            String title, description, bgColor, createdAt, closedAt;
            int coin, respondentNumber, respondentCount, isCompleted, isSale;

            JSONObject surveyListJSONObject = new JSONObject(surveyListJSON);
            Log.d(TAG, "insertSurveyListJSON's SurveyListJSONObject = " + surveyListJSONObject);
            JSONArray surveyFormArray = surveyListJSONObject.getJSONArray("form");
            if(surveyFormArray.length() == 0){
                Log.d(TAG, "설문 리스트의 정보가 0입니다.");
                return;
            }
            for(int i=0; i<=surveyFormArray.length(); i++) {
                JSONObject surveyFormObject = surveyFormArray.getJSONObject(i);
                id = surveyFormObject.getInt("id");
                Log.d(TAG, "insertSurveyListJSON's get List in id = " +id);
                title = surveyFormObject.getString("title");
                coin = parseInt(surveyFormObject.getString("reward"));
                description = surveyFormObject.getString("description");
                respondentNumber = surveyFormObject.getInt("respondent_number");
                respondentCount = surveyFormObject.getInt("respondent_count");
                isCompleted = surveyFormObject.getInt("is_completed");
                isSale = surveyFormObject.getInt("is_sale");
                createdAt = surveyFormObject.getString("created_at");
                closedAt = surveyFormObject.getString("closed_at");
                bgColor = surveyFormObject.getString("bgcolor");
                dbInsertResult = insertSurveyList(id, title, description, coin, createdAt, closedAt, respondentCount, respondentNumber, isCompleted, isSale, bgColor);
                Log.d(TAG, "dbInsertResult = " + dbInsertResult);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int searchSurveyID(int id){
        helper = new DBHelper(this, "survey_list", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from survey_list where id = " +id;
        Cursor cursor = db.rawQuery(sql, null);
        Integer surveyID = null;
        while(cursor.moveToNext()) {
            surveyID = cursor.getInt(0);
        }
        int result;
        if(surveyID != null){
            result = 0;
        }else
            result = 1;

        return result;
    }

    public int insertSurveyList(int id, String title, String description, int coin, String createdAt, String closedAt, int respondentCount, int respondentNumber, int isCompleted,int isSale, String bgColor){
        try {
            helper = new DBHelper(this, "survey_list", null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();
            int result = searchSurveyID(id);
            if(result == 0){
                Log.d(TAG, "insertSuveyList :해당 " + id + " 는 이미 테이블에 존재합니다");
                return 0;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", id);
            contentValues.put("title", title);
            contentValues.put("description", description);
            contentValues.put("coin", coin);
            contentValues.put("started_at", createdAt);
            contentValues.put("closed_at", closedAt);
            contentValues.put("respondent_count", respondentCount);
            contentValues.put("respondent_number", respondentNumber);
            contentValues.put("is_completed", isCompleted);
            contentValues.put("is_sale", isSale);
            contentValues.put("bg_color", bgColor);
            contentValues.put("is_done", false);
            Log.d(TAG, "insertSurveyList's situation");
            //String sql = "INSERT INTO survey_list(id, title, description, coin, startedAt, closed_at, respondent_count, respondent_number, is_complated, is_sale, color, is_done) values("+ id + "," + title + "," +  description, coin, closedAt, respondentCount, respondentNumber, isComplated, isSale, color, false)"
            db.insert("survey_list",  null, contentValues);
            db.close();
            helper.close();
        }catch(Exception e){
            Log.e(TAG ,"insertSurveyList's error");
            e.printStackTrace();
            return 0;
        }
        createNotification();
        return 1;
    }

    /*private void doBackgroundWork(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0; i< 10; i++){
                    Log.d(TAG, "run : " + i);
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "job finished");
                jobFinished(params, true);
            }
        }).start();
    }*/

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "job cancelled before completion");
        jobCancelled = false;
        return false;
    }


    public void createNotification(){
        notificationManager = (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("1", "push", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, notificationChannel.getId());
            Intent intent = new Intent(this, SplashActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("알림")
                    .setContentText("회원님을 위한 설문이 도착했습니다")
                    .setContentIntent(pendingIntent);
            notificationManager.notify(1, notificationBuilder.build());
        }//else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            //notificationChannel = new NotificationChannel("2", "push", )
        }
    }
