package com.example.mindspark;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class  CollectionAdapter extends FragmentStatePagerAdapter {
    public ArrayList<Article> articles;
    ArrayList<String> titles;
    CountDownLatch done;
    int realPosition;
    public CollectionAdapter(FragmentManager fragment) {
        super(fragment);
        done = new CountDownLatch(1);
        done.countDown();
        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        articles = MainActivity.articles;
        titles = new ArrayList<>();
        Log.d("TagOML", "" + articles.size());

        for(int i = 0; i < articles.size(); i++){
            titles.add(articles.get(i).getTitle());
        }
        Log.d("TagTitle", ""+titles.size());
    }
/*
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        Fragment fragment = new ObjectFragment();
        Bundle args = new Bundle();

        // Our object is just an integer :-P
        //args.putInt(ObjectFragment.ARG_OBJECT, position);
        done.countDown();
        Log.d("TagThingies", realPosition +"");
        if(realPosition == 1){
            Log.d("TagFinal", articles.size()+"");
            args.putString(ObjectFragment.headline, articles.get(realPosition).getTitle());
        }
        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        fragment.setArguments(args);
        //return fragment;
    }

 */

    @Override
    public Fragment getItem(int position) {
        // Return a NEW fragment instance in createFragment(int)

        realPosition = position;
        Fragment fragment = new ObjectFragment();
        Bundle args = new Bundle();
        if (position == 0){
            return new OnBoarding();
        }
        // Our object is just an integer :-P
        args.putInt(ObjectFragment.ARG_OBJECT, position);
        done.countDown();
        Log.d("Tag25",""+articles.size());
        if(position != 0){
            //args.putString(ObjectFragment.headline, articles.get(position-1).getTitle());
        }
        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Tag25",""+articles.size());
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int getCount() {

        //Log.d("TagThing", ""+articles.size());
        return 100;
    }




}