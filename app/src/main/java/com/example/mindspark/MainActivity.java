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
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.util.concurrent.SettableFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private FirebaseFirestore db;
    public static ArrayList<Article> articles;
    Article a;
    BottomNavigationView bottomNavigationView;
    CollectionAdapter collectionAdapter;
    Boolean success = false;
    public static User u;
    FirebaseAuth fAuth;
    public static ArrayList<User> users;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar5);
        db = FirebaseFirestore.getInstance();
        articles = new ArrayList<>();
        users = new ArrayList<>();
        fAuth = FirebaseAuth.getInstance();
        //Users
        db.collection("userData").document(fAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            String firstName = document.getString("firstName");
                            String lastName = document.getString("lastName");
                            int followers = document.getLong("followers").intValue();
                            ArrayList<Article> userArticles = (ArrayList<Article>) document.get("articles");
                            String email = document.getString("email");
                            String profilePicImg = document.getString("profilePicImg");
                            u = new User(firstName, lastName, email, userArticles, followers, profilePicImg);
                        }
                    }
                });

        //Articles
        db.collection("article")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int numCores = Runtime.getRuntime().availableProcessors();
                        ThreadPoolExecutor executor = new ThreadPoolExecutor(numCores * 3, numCores *3,
                                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
                        task.addOnCompleteListener(executor, new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("TagAdd", "Added");
                                        Log.d("Tag", document.getId() + " => " + document.getString("Title"));
                                        String author = document.getString("Author");
                                        String field = document.getString("Field");
                                        String title = document.getString("Title");
                                        String postType = document.getString("postType");
                                        int estReadTime = document.getLong("estReadTime").intValue();
                                        int numReads = document.getLong("numReads").intValue();
                                        ArrayList<String> bodyText = (ArrayList<String>) document.get("BodyText");
                                        ArrayList<Integer> actualReadTime = (ArrayList<Integer>) document.get("actReadTime");
                                        ArrayList<String > comments = (ArrayList<String>) document.get("Comments");
                                        int likes = document.getLong("Likes").intValue();
                                        String id = document.getId();
                                        String authorID = document.getString("authorID");
                                        String imageURL = document.getString("imageURL");
                                        String subheading = document.getString("subheading");


                                        a = new Article(author, field, title, postType, estReadTime, numReads, bodyText, actualReadTime, likes, id, comments, authorID, imageURL, subheading);
                                        articles.add(a);
                                        Log.d("TagSize", "" + articles.size());
                                        Log.d("TagReal", "" + articles.get(0).getAuthor());
                                        Log.d("Wow", ""+Html.fromHtml("<p>asdasdasdasdasd</p>"));

                                    }

                                }
                            }
                        });


                    }
                });
        new CountDownTimer(5000, 1) {

            @Override
            public void onTick(long millisUntilFinished) {

                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                loadFragment(new HomeFragment());
                Log.d("Tagtes3", "Hi");
                Log.d("Tagsss3", ""+articles.size());
            }

        }.start();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




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



    public class Thread extends java.lang.Thread implements Runnable {
        public void run() {
            try {
                CollectionAdapter.Thread.sleep(1);
            }
            catch (Exception e) {
            }


        }
    }
}

