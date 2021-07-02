package com.example.conflictjd.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Time:2021/7/1 18:09
 * Author:
 * Description:
 */
public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> data;

    public ViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity, List<Fragment> data) {
        super(fragmentActivity);
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
