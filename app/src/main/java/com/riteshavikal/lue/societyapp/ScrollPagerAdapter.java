package com.riteshavikal.lue.societyapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.riteshavikal.lue.societyapp.IntroFragment.IntroFrag1;
import com.riteshavikal.lue.societyapp.IntroFragment.IntroFragment2;
import com.riteshavikal.lue.societyapp.IntroFragment.IntroFragment3;
import com.riteshavikal.lue.societyapp.IntroFragment.IntroFragment4;

/**
 * Created by Chandan on 7/8/2016.
 */
public class ScrollPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 4;

    public ScrollPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new IntroFrag1();
                break;
            case 1:
                fragment = new IntroFragment2();
                break;
            case 2:
                fragment = new IntroFragment3();
                break;
            case 3:
                fragment = new IntroFragment4();
                break;
        }
        return fragment;
    }

}
