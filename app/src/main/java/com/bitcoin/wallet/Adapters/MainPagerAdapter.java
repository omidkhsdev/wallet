package com.bitcoin.wallet.Adapters;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    /* Fragments list that is being initialized in constructor */
    private List<Fragment> fragments;
    /* Strings list that contains tab names */
    private List<String> tabsNames;


    public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabsNames) {
        super(fm);
        this.fragments = fragments;
        this.tabsNames = tabsNames;
    }

    @Override
    public Fragment getItem(int position) {
       return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabsNames.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
