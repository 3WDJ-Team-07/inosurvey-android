package xyz.inosurvey.inosurvey;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    TextView textView;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new SurveyListFragment());
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //textView = findViewById(R.id.textView);

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
                        break;
                    //마켓
                    case R.id.bottom_survey_market:
                        fragment = new MarketFragment();
                        break;
                    //프로필
                    case R.id.bottom_survey_profile:
                        fragment = new ProfileFragment();
                        break;
                    //세팅
                    case R.id.bottom_survey_setting:
                        fragment = new SettingFragment();
                        break;
                }
                return false;
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
}
