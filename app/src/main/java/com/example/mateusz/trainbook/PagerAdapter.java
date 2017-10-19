package com.example.mateusz.trainbook;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by Mateusz on 2017-08-24.
 */

public class PagerAdapter extends FragmentPagerAdapter {


    //private Fragment fragment;
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public PagerAdapter(android.support.v4.app.FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case(0):
                return new ExcerciseListMaterialFragment();
            case(1):
                return new TrainingListMaterialFragment();
            case (2):
                return new HistoryFragment();
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
