package com.riteshavikal.lue.societyapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riteshavikal.lue.societyapp.Fragment.RequestedTabdesignFragment;
import com.riteshavikal.lue.societyapp.Fragment.SocietyMainNews;
import com.riteshavikal.lue.societyapp.Fragment.NewsTabdesignFragment;

/**
 * Created by Fujitsu on 28/04/2017.
 */

public class tabdesign extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 4 ;
    String strtext;
    Bundle bundle,bundle1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tabdesign,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs123);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager123);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {




        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){

                case 0 :SocietyMainNews dashfab = new SocietyMainNews();
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().commit();
                    Log.d("tab0123", String.valueOf(strtext));
                    return dashfab;

                case 1 :  NewsTabdesignFragment pendfrag = new NewsTabdesignFragment();
                FragmentManager manager12 = getFragmentManager();
                manager12.beginTransaction().commit();
                Log.d("tab0123", String.valueOf(strtext));
                return pendfrag;

                case 2 :  RequestedTabdesignFragment verfeb = new RequestedTabdesignFragment();
                FragmentManager manager1 = getFragmentManager();
                manager1.beginTransaction().commit();
                Log.d("tab0123", String.valueOf(strtext));
                return verfeb;

                case 3 : SocietyMainNews rejectedNews = new SocietyMainNews();
                    FragmentManager manager2 = getFragmentManager();
                    manager2.beginTransaction().commit();
                    Log.d("tab0123", String.valueOf(strtext));
                    return rejectedNews;
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Society News";
                case 1 :
                    return "Your News";
                case 2 :
                    return "Your Request";
                case 3 :
                    return "Discount Voucher";

            }
            return null;
        }
    }

}


