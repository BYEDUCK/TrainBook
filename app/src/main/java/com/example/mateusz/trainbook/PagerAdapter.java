package com.example.mateusz.trainbook;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Mateusz on 2017-08-24.
 */

public class PagerAdapter extends FragmentPagerAdapter {


    private Fragment fragment;

    public PagerAdapter(android.support.v4.app.FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case(0):
                fragment=new ExcerciseListMaterialFragment();
                return fragment;
            case(1):
                fragment=new TrainingListMaterialFragment();
                return fragment;
            case (2):
                fragment=new HistoryFragment();
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch(position)
        {
            case(0):
                return "ćwiczenia";
            case(1):
                return "treningi";
            case(2):
                return "historia";
            default:
                return "";
        }
    }
}
