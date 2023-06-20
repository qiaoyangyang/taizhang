package com.meiling.account.adapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;
public class FragAdapter extends FragmentPagerAdapter {
    //1.创建Fragment数组
    private List<Fragment> mFragments;
    //2.接收从Activity页面传递过来的Fragment数组
    public FragAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        mFragments = fragments;
    }
    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }
}