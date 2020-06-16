package com.example.mindspark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static com.example.mindspark.CollectionAdapter.realPosition;

public class CommentsSection extends AppCompatActivity {

    ListView commentListView;
    Button submit;
    TextInputEditText commentText;
    ArrayList<String> comments;
    ArrayList<Article> articles;
    FirebaseFirestore db;
    ImageView refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_section);
        db = FirebaseFirestore.getInstance();
        commentListView = findViewById(R.id.commentsHolder);
        submit = findViewById(R.id.postComment);
        commentText = findViewById(R.id.inputComment);
        refresh = findViewById(R.id.refresh);
        comments = new ArrayList<>();
        articles = MainActivity.articles;
        inflateListView();
        addComment();
        refresh();
    }

    private void addComment(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentString = ""+commentText.getText();
                String id = articles.get(realPosition-1).getId();
                DocumentReference documentReference = db.collection("article").document(id);
                documentReference.update("Comments", FieldValue.arrayUnion(commentString));
            }
        });
    }

    private void inflateListView(){
        comments = articles.get(realPosition-1).getComments();
        CommentAdapter commentAdapter = new CommentAdapter(this, R.layout.comments_list,comments);
        commentListView.setAdapter(commentAdapter);
    }
    private void refresh(){
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());

            }
        });
    }
}
