package com.example.mindspark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.mindspark.CollectionAdapter.realPosition;

public class MainArticlePage extends AppCompatActivity {

    FloatingActionButton mainFab;
    FloatingActionButton likeFab;
    FloatingActionButton shareFab;
    FloatingActionButton commentFab;
    public static final String realPos = "position";
    Float translationY = 100f;
    Boolean isMenuOpen = false;
    TextView headlineMain;
    String headline;
    FirebaseFirestore db;
    ImageView bigImage;
    ArrayList<Article> articles;
    String imageName;
    int realPosition;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_article_page);
        bigImage = findViewById(R.id.app_bar_image);

        initFabMenu();
        db = FirebaseFirestore.getInstance();
        headlineMain = findViewById(R.id.headlineMain);
        articles = MainActivity.articles;
        fillHeadline();
        addLike();
        addComment();
        imageName = articles.get(realPosition-1).getImageURL();
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/mind-spark-international.appspot.com/o/uploads%2F"+imageName+"?alt=media").into(bigImage);

    }

    private void fillHeadline(){
        realPosition = CollectionAdapter.realPosition;
        if (realPosition > -1) {
            headline = articles.get(realPosition-1).getTitle();
            headlineMain.setText(headline);
        }

    }

    private void menuOpen(){
        isMenuOpen = !isMenuOpen;
        mainFab.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();
        likeFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        commentFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        shareFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void menuClose(){
        isMenuOpen = !isMenuOpen;
        mainFab.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        likeFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        commentFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        shareFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void initFabMenu(){
        mainFab = findViewById(R.id.mainFAB);
        likeFab = findViewById(R.id.likeFAB);
        shareFab = findViewById(R.id.shareFAB);
        commentFab = findViewById(R.id.commentFAB);

        likeFab.setAlpha(0f);
        shareFab.setAlpha(0f);
        commentFab.setAlpha(0f);

        likeFab.setTranslationY(translationY);
        shareFab.setTranslationY(translationY);
        commentFab.setTranslationY(translationY);

        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMenuOpen){
                    menuClose();
                }
                else{
                    menuOpen();
                }

            }
        });



        shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello!");

// (Optional) Here we're setting the title of the content
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Send message");

// (Optional) Here we're passing a content URI to an image to be displayed

                sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

// Show the Sharesheet
                startActivity(Intent.createChooser(sendIntent, null));

            }
        });


    }
    private void addLike(){
        realPosition = CollectionAdapter.realPosition;
        likeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = articles.get(realPosition-1).getId();
                Log.d("TagCheck", id);
                DocumentReference documentReference = db.collection("article").document(id);
                int likeNum = articles.get(realPosition-1).getLikes();
                documentReference.update("Likes", likeNum+1);


            }
        });
    }
    private void addComment(){
        Intent intent = new Intent(this, CommentsSection.class);
        commentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                Log.d("PosCom", realPosition + "");
            }
        });
    }
}
