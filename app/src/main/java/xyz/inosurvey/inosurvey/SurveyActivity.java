package xyz.inosurvey.inosurvey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import xyz.inosurvey.inosurvey.adapter.SurveyViewPagerAdapter;

public class SurveyActivity extends AppCompatActivity {

    ViewPager viewPager;
    Button previousButton, nextButton, finishButton, startButton;
    public static int setCount;  //페이지 전체 갯수 변수
    public int lastPosition;
    //public static final int INTRO_PAGE = 0;
    //public static final int RADIO_PAGE = 1;
    //public static final int CHECK_PAGE = 2;
    ArrayList<Integer> numArray = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        numArray.add(0);
        numArray.add(1);
        numArray.add(2);
        numArray.add(1);
        numArray.add(2);
        numArray.add(2);
        numArray.add(1);
        numArray.add(1);
        numArray.add(1);
        setCount = numArray.size();
        lastPosition = setCount-1;
        System.out.println( setCount + "++" + lastPosition + "qwer");

        viewPager = findViewById(R.id.viewPager);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        finishButton = findViewById(R.id.finishButton);
        startButton = findViewById(R.id.startButton);


        ActionBar ab = getSupportActionBar();
        ab.setTitle("설문소개");
        ab.setDisplayHomeAsUpEnabled(true);
        Bundle bundlePosition = new Bundle();
        bundlePosition.putInt("position", 1);

        SurveyRadioFragment srf = new SurveyRadioFragment();
        srf.setArguments(bundlePosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    startButton.setVisibility(View.VISIBLE);
                    previousButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                }else if(position == lastPosition){
                    startButton.setVisibility(View.GONE);
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.GONE);
                    finishButton.setVisibility(View.VISIBLE);
                }else if(1<=position && lastPosition>position){
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
        viewPager.setAdapter(new SurveyViewPagerAdapter(getSupportFragmentManager(), numArray));

        previousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SurveyViewPagerAdapter.controlPosition--;
                System.out.println(SurveyViewPagerAdapter.controlPosition + "   controlPosition");
                int currentPage = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentPage-1, true);
                //buttonControl();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SurveyViewPagerAdapter.controlPosition++;
                System.out.println(SurveyViewPagerAdapter.controlPosition + "   controlPosition");
                int currentPage = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentPage + 1, true);
                //buttonControl();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SurveyViewPagerAdapter.controlPosition++;
                System.out.println(SurveyViewPagerAdapter.controlPosition + "   controlPosition");
                int currentPage = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentPage + 1, true);
                //buttonControl();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this); 최상위 액티비티로 이동 후 아래에 있는 모든 activity destroy
                this.finish();
                SurveyViewPagerAdapter.controlPosition = 0;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
