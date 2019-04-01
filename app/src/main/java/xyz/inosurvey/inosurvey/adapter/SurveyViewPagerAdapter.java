package xyz.inosurvey.inosurvey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import xyz.inosurvey.inosurvey.SurveyActivity;
import xyz.inosurvey.inosurvey.SurveyCheckboxFragment;
import xyz.inosurvey.inosurvey.SurveyIntroFragment;
import xyz.inosurvey.inosurvey.SurveyRadioFragment;

public class SurveyViewPagerAdapter extends FragmentStatePagerAdapter {

    //private ArrayList<Fragment> fragmentArray;

    //public void addFragment(Fragment fragment){
    //    fragmentArray.add(fragment);
    //}
    ArrayList<Integer> pageNum = new ArrayList<>();
    public static int controlPosition = 0; //페이지 포지션 컨트롤 변수

    public SurveyViewPagerAdapter(FragmentManager fm, ArrayList<Integer> num){
        super(fm);
        this.pageNum = num;
    }


        @Override
        public Fragment getItem (int position) {
            Fragment drawFragment;
            System.out.println(position+ "position");
            position = pageNum.get(controlPosition);
            System.out.println(position + "abcd");
            if(position == 0){
                drawFragment = new SurveyIntroFragment();
                return drawFragment;
            }
            if(position>=1) {
                if (position == 1) {
                    drawFragment = new SurveyRadioFragment();
                    return drawFragment;
                } else if (position == 2) {
                    drawFragment = new SurveyCheckboxFragment();
                    return drawFragment;
                }
            }
            return null;
        /*switch(position){
            case 0:
                return new SurveyIntroFragment();
            case 1:
                return new SurveyRadioFragment();
            case 2:
                return new SurveyCheckboxFragment();
            default :
                return null;
        }*/
        //return null;
    }

        @Override
        public int getCount () {
        return SurveyActivity.setCount;
    }




}
