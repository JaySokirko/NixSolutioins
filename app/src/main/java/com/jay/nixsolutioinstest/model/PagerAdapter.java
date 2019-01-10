package com.jay.nixsolutioinstest.model;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentsList = new ArrayList<>();
    private List<String> fragmentTitleList = new ArrayList<>();


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }


    @Override
    public int getCount() {
        return fragmentsList.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }


    public void addFragment(Fragment fragment, String title){

        fragmentsList.add(fragment);
        fragmentTitleList.add(title);
    }
}
