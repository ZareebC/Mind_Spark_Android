package com.example.mindspark;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ObjectFragment extends Fragment {


    public static final String ARG_OBJECT = "object";
    public static final String headline = "headline";
    public static final String author_string = "author";
    public static final String url_holder = "url";
    public static final String author_id = "author_id";
    TextView textView;
    TextView articleHeader;
    ViewPager viewPager;
    Button readArticle;
    TextView author;
    ImageView dropDown;
    ImageView profPic;
    String imageURL, authorID;
    FirebaseFirestore db;
    ArrayList<Article> articles;
    Boolean isUp = false;
    HorizontalScrollView subjectScroll;
    OvershootInterpolator interpolator;
    int realPosition;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_object, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        IntroAdapter introAdapter = new IntroAdapter(getChildFragmentManager());
        realPosition = CollectionAdapter.realPosition;
        articles = MainActivity.articles;
        viewPager = view.findViewById(R.id.smallViewPager);
        viewPager.setAdapter(introAdapter);
        textView = view.findViewById(R.id.text1);
        profPic = view.findViewById(R.id.profPic);
        author = view.findViewById(R.id.author);
        author.setText(args.getString(author_string));
        articleHeader = view.findViewById(R.id.articleHeader);
        articleHeader.setText(args.getString(headline));
        textView.setText(Integer.toString(args.getInt(ARG_OBJECT)));
        readArticle = view.findViewById(R.id.readArticle);
        dropDown = view.findViewById(R.id.dropDown);
        subjectScroll = view.findViewById(R.id.subjectScroller);
        interpolator = new OvershootInterpolator();
        imageURL = args.getString(url_holder);
        authorID = args.getString(author_id);
        db = FirebaseFirestore.getInstance();
        setProfPic();



        readArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainArticlePage.class);
                startActivity(intent);

            }
        });
        deterMotion();


    }
    private void moveUp(){
        dropDown.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        subjectScroll.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        dropDown.animate().translationY(-300f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        isUp = !isUp;
    }
    private void moveDown(){
        dropDown.animate().setInterpolator(interpolator).rotation(180f).setDuration(300).start();
        dropDown.animate().translationY(8f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        subjectScroll.animate().translationY(8f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        isUp = !isUp;
    }
    private void deterMotion(){
        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUp){
                    moveDown();
                }
                else {
                    moveUp();
                }
            }
        });

    }
    private void setProfPic(){
        DocumentReference documentReference = db.collection("userData").document(authorID);
        documentReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String profPicRef = documentSnapshot.getString("profilePicImg");
                        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/mind-spark-international.appspot.com/o/profile%2F"+profPicRef+"?alt=media").into(profPic);
                    }
                });
    }
}
