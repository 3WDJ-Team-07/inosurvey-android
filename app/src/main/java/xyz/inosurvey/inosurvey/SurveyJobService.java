package xyz.inosurvey.inosurvey;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SurveyJobService extends JobService {

    private String getSurveyJSONInformation;
    private boolean jobCancelled = false;
    private String TAG = "jobservice";
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "job started");
        getJson("http://192.168.43.172/final/menu.php","POST", params);
        //doBackgroundWork(params);
        return true;
    }

    public void getJson(String url, String method, final JobParameters params) {

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
                    createNotification();
                    jobFinished(params, true);
                }else {
                    getSurveyJSONInformation = result;
                    createNotification();
                    jobFinished(params, true);
                    Log.d(TAG, getSurveyJSONInformation);
                }
            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url, method);
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
            notificationBuilder.setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("제목")
                    .setContentText("세부내용");
            notificationManager.notify(1, notificationBuilder.build());
        }//else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            //notificationChannel = new NotificationChannel("2", "push", )
        }
    }
