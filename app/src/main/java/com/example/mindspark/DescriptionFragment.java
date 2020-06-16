package com.example.mindspark;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment {


    ImageView previewPic;
    ArrayList<Article> articles;
    String imageName;
    int realPosition;
    TextView articleSubheading;
    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realPosition = CollectionAdapter.realPosition;
        previewPic = view.findViewById(R.id.previewPic);
        articleSubheading = view.findViewById(R.id.articleSubheading);
        articles = MainActivity.articles;
        articleSubheading.setText(articles.get(realPosition).getSubheading());
        imageName = articles.get(realPosition).getImageURL();
        Log.d("TestCheck", imageName+"");
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/mind-spark-international.appspot.com/o/uploads%2F"+imageName+"?alt=media").resize(600, 600).into(previewPic);

    }
}
