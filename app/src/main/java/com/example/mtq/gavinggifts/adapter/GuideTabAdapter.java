package com.example.mtq.gavinggifts.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by mtq on 2016/8/21.
 */
public class GuideTabAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> stringList;
    public GuideTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public GuideTabAdapter(FragmentManager supportFragmentManager, List<Fragment> fragmentList, List<String> titleList) {
        super(supportFragmentManager);
        this.fragmentList=fragmentList;
        this.stringList=titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position%fragmentList.size());
    }
}
