package xyz.inosurvey.inosurvey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import xyz.inosurvey.inosurvey.SurveyActivity;
import xyz.inosurvey.inosurvey.fragment.SurveyFragment;

public class SurveyViewPagerAdapter extends FragmentStatePagerAdapter {

    //private ArrayList<Fragment> fragmentArray;

    //public void addFragment(Fragment fragment){
    //    fragmentArray.add(fragment);
    //}
    ArrayList<Integer> pageNum = new ArrayList<>();

    public SurveyViewPagerAdapter(FragmentManager fm, ArrayList<Integer> num){
        super(fm);
        this.pageNum = num;
    }


        @Override
        public Fragment getItem (int position) {
            SurveyFragment fragment = new SurveyFragment();
            return fragment;
    }

        @Override
        public int getCount () {
        return pageNum.size();
    }
}
