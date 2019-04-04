package xyz.inosurvey.inosurvey;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import xyz.inosurvey.inosurvey.fragment.ProfileFragment;
import xyz.inosurvey.inosurvey.fragment.SettingFragment;
import xyz.inosurvey.inosurvey.fragment.SurveyListFragment;
import xyz.inosurvey.inosurvey.fragment.DonationFragment;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    TextView textView;
    public String TAG = "mainActivity";

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new SurveyListFragment());
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //액션바 제목 지정
        final ActionBar ab = getSupportActionBar();
        ab.setTitle("설문 리스트");
        //textView = findViewById(R.id.textView);

        ComponentName componentName = new ComponentName(this, SurveyJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job Scheduling failed");
        }


        //바텀 네비게이션 이벤트 리스너
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            //메뉴 아이템 id를 받아와서 case 조건에 맞는 fragment 보여주기
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch(menuItem.getItemId()){
                    //설문 리스트
                    case R.id.bottom_survey_list:
                        fragment = new SurveyListFragment();
                        ab.setTitle("설문 리스트");
                        break;
                    //마켓
                    case R.id.bottom_survey_donation:
                        fragment = new DonationFragment();
                        ab.setTitle("기부 리스트");
                        break;
                    //프로필
                    case R.id.bottom_survey_profile:
                        fragment = new ProfileFragment();
                        ab.setTitle("마이 페이지");
                        break;
                    //세팅
                    case R.id.bottom_survey_setting:
                        fragment = new SettingFragment();
                        ab.setTitle("옵션");
                        break;
                }
                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

   /* public void scheduleJob(View v){
        ComponentName componentName = new ComponentName(this, SurveyJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job Scheduling failed");
        }
    }

    public void cancelJob(View v){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelld");
    }*/
}
