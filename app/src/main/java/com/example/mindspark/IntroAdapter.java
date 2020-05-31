package com.example.mindspark;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class IntroAdapter extends FragmentPagerAdapter {

    public IntroAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new DescriptionFragment();
            case 1:
                return new LikesFragment();
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
