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

    private void readData(){

        db.collection("article")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Tag", document.getId() + " => " + document.getString("Title"));
                                String author = document.getString("Author");
                                String field = document.getString("Field");
                                String title = document.getString("Title");
                                String postType = document.getString("postType");
                                int estReadTime = document.getLong("estReadTime").intValue();
                                int numReads = document.getLong("numReads").intValue();
                                ArrayList<String> bodyText = (ArrayList<String>) document.get("BodyText");
                                ArrayList<Integer> actualReadTime = (ArrayList<Integer>) document.get("actReadTime");

                                a = new Article(author, field, title, postType, estReadTime, numReads, bodyText, actualReadTime);
                                articles.add(a);
                                Log.d("TagSize", "" + articles.size());
                                Log.d("TagReal", "" + articles.get(0).getAuthor());

                            }

                        }
                    }
                });

    }



    private interface FirestoreCallback{
       public void onCallBack(ArrayList<Article> list);
    }

}
