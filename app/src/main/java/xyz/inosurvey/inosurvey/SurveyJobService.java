package xyz.inosurvey.inosurvey;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class SurveyJobService extends JobService {

    private boolean jobCancelled = false;
    public String TAG = "jobservice";
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0; i< 10; i++){
                    Log.d(TAG, "rund : " + i);
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
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "job cancelled before completion");
        jobCancelled = false;
        return false;
    }
}
