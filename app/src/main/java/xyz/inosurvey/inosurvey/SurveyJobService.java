package xyz.inosurvey.inosurvey;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.LinearGradient;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SurveyJobService extends JobService {

    private JobParameters jobParams;
    private String getSurveyListJSON;
    private boolean jobCancelled = false;
    private String TAG = "jobservice";
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private String getUserJSON = null;
    private int userID;
    private DBHelper helper;

    @Override
    public boolean onStartJob(JobParameters params) {
        jobParams = params;
        Log.d(TAG, "job started");
        postToken("http://172.26.2.61:8000/api/user/check", "POST");
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
                    getSurveyFormJson("http://172.26.2.61:8000/api/response/index", "POST", jobParams);
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
                    System.out.println(getSurveyListJSON + "getgetget");
                    jobFinished(params, true);
                    createNotification();
                    Log.d(TAG, getSurveyListJSON);
                }
            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url, method);
    }

    public void parseUserJSON(){
        try {
            JSONObject userJSONObject = new JSONObject(getUserJSON);
            System.out.println("abcd");
            JSONObject userObject = userJSONObject.getJSONObject("user");
            userID = userObject.getInt("id");
            System.out.println(userID + "useruser");
        }catch(Exception e){
            System.out.println("zmzm");
            e.printStackTrace();
        }
    }

    public void parseSurveyListJSON(){
        try{
            JSONObject SurveyListJSONObject = new JSONObject(getSurveyListJSON);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void insertSurveyList(){
        helper = new DBHelper(this, "survey_list", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        //db.execSQL();

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
            Intent intent = new Intent(this, MainActivity.class);
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
