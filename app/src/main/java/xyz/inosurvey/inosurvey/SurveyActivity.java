package xyz.inosurvey.inosurvey;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import xyz.inosurvey.inosurvey.adapter.SurveyViewPagerAdapter;

public class SurveyActivity extends AppCompatActivity {

    ViewPager viewPager;
    Button previousButton, nextButton, finishButton, startButton;
    public int lastPosition;
    public static int exPo;   //viewpager 컨트롤 변수 ex) exPo == 1 -> 1번쨰 페이지
    //public static int exCount = 4; //viewpager의 총 페이지 갯수
    //public static final int INTRO_PAGE = 0;
    //public static final int RADIO_PAGE = 1;
    //public static final int CHECK_PAGE = 2;
    ArrayList<Integer> pageArray = new ArrayList<>();   //0=소개, 1=라디오 2=체크 3=주관식

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        //setCount = numArray.size();
        //lastPosition = setCount-1;

        pageArray.add(0);
        pageArray.add(1);
        pageArray.add(2);
        pageArray.add(3);

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

        viewPager.setAdapter(new SurveyViewPagerAdapter(getSupportFragmentManager(), pageArray));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("position : " + position);
                if(exPo == 0){
                    startButton.setVisibility(View.VISIBLE);
                    previousButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                }else if(exPo +1 == pageArray.size()){
                    startButton.setVisibility(View.GONE);
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.GONE);
                    finishButton.setVisibility(View.VISIBLE);
                }else if(exPo>=1 && exPo<pageArray.size()){
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    finishButton.setVisibility(View.GONE);
                    startButton.setVisibility(View.GONE);
                }
                /*if(position == 0) {
                    startButton.setVisibility(View.VISIBLE);
                    previousButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                    System.out.println("position 1: " + position);
                }else if(position == lastPosition){
                    startButton.setVisibility(View.GONE);
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.GONE);
                    finishButton.setVisibility(View.VISIBLE);
                    System.out.println("position 2: " + position);
                }else if(1<=position && lastPosition>position){
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    finishButton.setVisibility(View.GONE);
                    startButton.setVisibility(View.GONE);
                    System.out.println("position 3: " + position);
                }
                */
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //System.out.println(SurveyViewPagerAdapter.controlPosition + "   controlPosition");
                int currentPage = viewPager.getCurrentItem();
                exPo--;
                viewPager.setCurrentItem(currentPage-1, true);
                //buttonControl();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                exPo++;
                System.out.println("exPo : " + exPo);
                viewPager.setCurrentItem(currentPage + 1, true);
                //buttonControl();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                exPo++;
                System.out.println("exPo : " + exPo);
                viewPager.setCurrentItem(currentPage + 1, true);
                //buttonControl();
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
                exPo = 0;
                finish();
            }
        });

        builder.show();
    }
}
