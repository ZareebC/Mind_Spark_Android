package com.example.mindspark;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{

    CollectionAdapter collectionAdapter;
    ViewPager viewPager;

    private FirebaseFirestore db;
    Article a;
    public static ArrayList<Article> articles;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        articles = new ArrayList<>();
        //readData();

        ArrayList<String> test = new ArrayList<>();
        ArrayList<Integer> test1 = new ArrayList<>();
        //articles.add(new Article("author", "field", "title", "postType", 1, 1, test, test1));

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        collectionAdapter = new CollectionAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(collectionAdapter);

    }





    private interface FirestoreCallback{
       public void onCallBack(ArrayList<Article> list);
    }

}
