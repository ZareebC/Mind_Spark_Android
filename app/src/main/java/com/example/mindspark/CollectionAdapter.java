package com.example.mindspark;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class  CollectionAdapter extends FragmentStatePagerAdapter {
    public ArrayList<Article> articles;
    ArrayList<String> titles;
    public static int realPosition;
    Article a;
    private FirebaseFirestore db;

    public CollectionAdapter(FragmentManager fragment) {
        super(fragment);
        db = FirebaseFirestore.getInstance();
        articles = MainActivity.articles;
        titles = new ArrayList<>();
        new Thread().run();
        Log.d("TagOML", "" + articles.size());

        for(int i = 0; i < articles.size(); i++){
            titles.add(articles.get(i).getTitle());
        }
        Log.d("TagTitle", ""+titles.size());
    }

    @Override
    public Fragment getItem(int position) {
        // Return a NEW fragment instance in createFragment(int)

        realPosition = position-1;
        Fragment fragment = new ObjectFragment();
        Bundle args = new Bundle();
        if (position == 0){
            return new OnBoarding();
        }
        // Our object is just an integer :-P
        args.putInt(ObjectFragment.ARG_OBJECT, position);
        Log.d("Tag25",""+articles.size());
        Log.d("Position0", "Real: " + realPosition + " Fake: " + position);
        if(position != 0){
            Log.d("Position1", "Reached");
            args.putInt(MainArticlePage.realPos, realPosition);
            args.putString(ObjectFragment.headline, articles.get(position-1).getTitle());
            args.putString(ObjectFragment.author_string, articles.get(position-1).getAuthor());
            args.putString(ObjectFragment.url_holder, articles.get(position-1).getImageURL());
            args.putString(ObjectFragment.author_id, articles.get(position-1).getAuthorID());
        }

        Log.d("Tag27",""+articles.size());
        Log.d("Position", "Real: " + realPosition + " Fake: " + position);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int getCount() {

        //Log.d("TagThing", ""+articles.size());
        return articles.size()+1;
    }

    public class Thread extends java.lang.Thread{
        public void run(){
            try {
                CollectionAdapter.Thread.sleep(1);
            }
            catch (Exception e) {
            }
        }
    }

}