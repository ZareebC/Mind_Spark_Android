package com.example.mindspark;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;


/**
 * A simple {@link Fragment} subclass.
 */
public class LikesFragment extends Fragment {


    TextView numLikes;
    int intLikes;
    ArrayList<Article> articles;
    int position;
    TextView numReads;
    int intReads;
    public LikesFragment() {
        // Required empty public constructor
        articles = MainActivity.articles;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_likes, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        numLikes = view.findViewById(R.id.numLikes);
        numReads = view.findViewById(R.id.numReads);
        position = CollectionAdapter.realPosition;
        if(position > -1) {
            intLikes = articles.get(position ).getLikes();
            intReads = articles.get(position).getNumReads();
            Log.d("Likes", "" + articles.get(position ).getLikes());
            numLikes.setText(""+intLikes);
            numReads.setText(""+intReads);
        }
    }
}
