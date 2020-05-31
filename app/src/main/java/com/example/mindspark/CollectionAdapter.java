package com.example.mindspark;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class  CollectionAdapter extends FragmentStatePagerAdapter {
    public ArrayList<Article> articles;
    ArrayList<String> titles;
    public CollectionAdapter(FragmentManager fragment) {
        super(fragment);
        articles = MainActivity.articles;
        titles = new ArrayList<>();
        Log.d("TagOML", "" + articles.size());

        for(int i = 0; i < articles.size(); i++){
            titles.add(articles.get(i).getTitle());
        }
        Log.d("TagTitle", ""+titles.size());
    }



    @Override
    public Fragment getItem(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new ObjectFragment();
        Bundle args = new Bundle();
        if (position == 0){
            return new OnBoarding();
        }
        // Our object is just an integer :-P
        args.putInt(ObjectFragment.ARG_OBJECT, position);
        Log.d("Tag25",""+articles.size());
        if(position == 1){
            //args.putString(ObjectFragment.headline, articles.get(position-1).getTitle());
        }
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int getCount() {

        return 100;
    }




}