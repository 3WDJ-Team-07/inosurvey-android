package xyz.inosurvey.inosurvey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import xyz.inosurvey.inosurvey.SurveyActivity;
import xyz.inosurvey.inosurvey.fragment.SurveyFragment;

public class SurveyViewPagerAdapter extends FragmentPagerAdapter {

    //private ArrayList<Fragment> fragmentArray;

    //public void addFragment(Fragment fragment){
    //    fragmentArray.add(fragment);
    //}

    private int count;

    public SurveyViewPagerAdapter(FragmentManager fm, int getCount){
        super(fm);
        this.count = getCount;
    }


        @Override
        public Fragment getItem (int position) {
            SurveyFragment fragment = new SurveyFragment();
            return fragment;
    }

        @Override
        public int getCount () {
        return count;
    }
}
