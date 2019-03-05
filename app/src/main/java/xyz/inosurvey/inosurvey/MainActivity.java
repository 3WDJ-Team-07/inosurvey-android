package xyz.inosurvey.inosurvey;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        textView = findViewById(R.id.textView);

        //바텀 네비게이션 이벤트 리스너
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            //메뉴 아이템 id를 받아와서 case 조건에 맞는 preference 보여주기
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.bottom_survey_list:
                        textView.setText("설문리스트");
                        break;
                    case R.id.bottom_survey_market:
                        textView.setText("마켓");
                        break;
                    case R.id.bottom_survey_profile:
                        textView.setText("프로필");
                        break;
                    case R.id.bottom_survey_settings:
                        textView.setText("세팅");
                        break;
                }
                return false;
            }
        });
    }
}
