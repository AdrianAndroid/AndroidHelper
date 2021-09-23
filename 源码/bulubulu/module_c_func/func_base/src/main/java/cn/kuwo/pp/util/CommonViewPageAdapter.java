package cn.kuwo.pp.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommonViewPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<String> fragmentsTitle;

    public CommonViewPageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = new ArrayList<>();
        fragmentsTitle = new ArrayList<>();

        for (int i = 0; i < fragments.size(); i++) {
            this.fragments.add(fragments.get(i));
            this.fragmentsTitle.add(titles.get(i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }
}
