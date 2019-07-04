package com.example.s20141210jinwoojung.capston.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.s20141210jinwoojung.capston.R;


/**
 * Created by Admin on 3/15/2017.
 */

public class VolumeFragment extends Fragment{

    private static ViewPager mPager;
    private TabLayout mTabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_volume, container, false);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        mPager.setAdapter(new TabsAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mPager);

        setHasOptionsMenu(true);

        return view;
    }

    class TabsAdapter extends FragmentPagerAdapter {

        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new TabGeoCone();
                case 1:
                    return new NoticeFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "검사하기";
                case 1:
                    return "지도";
            }
            return "";
        }
    }
}
