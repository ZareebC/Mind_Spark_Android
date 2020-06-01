package com.example.mindspark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.LocationListener;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mindspark.Interface.FirestoreLoadDone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FirestoreLoadDone {

    TextView textView;
    private FirebaseFirestore db;
    public static ArrayList<Article> articles;
    Article a;
    BottomNavigationView bottomNavigationView;
    CollectionAdapter collectionAdapter;
    FirestoreLoadDone firestoreLoadDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articles= new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        firestoreLoadDone = this;
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Log.d("Tagtes3", "Hi");
        Log.d("Tagsss3", ""+articles.size());
        loadFragment(new HomeFragment());
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeItem:
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.profileIcon:
                    loadFragment(new ProfileFragment());
                    return true;
                case R.id.createIcon:
                    loadFragment(new CreateFragment());
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onFirestoreLoadSuccess(ArrayList<Article> list) {

    }
}

