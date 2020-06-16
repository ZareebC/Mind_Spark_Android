package com.example.mindspark;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    ListView listView;
    ArrayList<String> recentActivty;
    TextView logout;
    ArrayList<String> pastArticles;
    ListView articleListView;
    String urlProf;
    ArrayList<User> users;
    ArrayList<Article> userArticles;
    ArrayList<Article> articles;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    User user;
    ImageView profPic;
    TextView numFollowers;
    TextView numArticles;
    TextView username;
    String permaImg;
    StorageReference storageReference;
    ImageView refresh;
    int realPosition;
    private Uri imageURI;
    private static final int PICK_IMAGE_REQUEST = 1;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realPosition = CollectionAdapter.realPosition;
        listView = view.findViewById(R.id.listView);
        articleListView = view.findViewById(R.id.articleListView);
        username = view.findViewById(R.id.username);
        refresh = view.findViewById(R.id.refresh);
        logout = view.findViewById(R.id.logout);
        profPic = view.findViewById(R.id.profPic);
        fAuth = FirebaseAuth.getInstance();
        numFollowers = view.findViewById(R.id.numFollowers);
        numArticles = view.findViewById(R.id.numArticles);
        storageReference = FirebaseStorage.getInstance().getReference("profile");
        db = FirebaseFirestore.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        userArticles = new ArrayList<>();
        user = MainActivity.u;
        articles = MainActivity.articles;

        numFollowers.setText(user.getFollowers()+"");
        numArticles.setText(user.getPostedArticles().size()+"");
        username.setText(user.getFirstName()+" "+user.getLastName());
        permaImg = user.getProfilePicImg();
        if(permaImg != null && !(permaImg.equals(""))){
            Log.d("CheckProf", permaImg+"");
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/mind-spark-international.appspot.com/o/profile%2F"+permaImg+"?alt=media").resize(200, 200).centerCrop().into(profPic);
        }
        else{
            Picasso.get().load(R.drawable.create).resize(200,200).centerCrop().into(profPic);
        }

        pastArticles = new ArrayList<>();
        recentActivty = new ArrayList<>();
        recentActivty.add("He liked");
        recentActivty.add("she liked");
        recentActivty.add("I liked");
        Log.d("Check", articles.size()+"");

        for(Article a : articles){
            if(a.getAuthorID().equals(fAuth.getCurrentUser().getUid())){
                userArticles.add(a);
                Log.d("Check", "CHeck");
            }
        }


        pastArticles.add("Article 1");
        pastArticles.add("Article 2");
        pastArticles.add("Article 3");
        pastArticles.add("Article 3");
        pastArticles.add("Article 3");
        pastArticles.add("Article 3");
        pastArticles.add("Article 3");

        MyAdapter adapter = new MyAdapter(getActivity(), R.layout.list_view_setup, recentActivty);
        ArticleAdapter articleAdapter= new ArticleAdapter(getActivity(), R.layout.article_recent, userArticles);
        listView.setAdapter(adapter);
        articleListView.setAdapter(articleAdapter);
        setProfPic();

    }
    public class MyAdapter extends ArrayAdapter<String>{
        Context context;
        int resource;
        List<String> list;
        public MyAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.list = objects;
        }
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//            Do not put ANY loops in here! You will blow the entire program up!

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterLayout = layoutInflater.inflate(resource, null);
            ImageView imageView= adapterLayout.findViewById(R.id.aritcleImage);
            TextView textView = adapterLayout.findViewById(R.id.desciption);
            textView.setText(list.get(position) + " " + position);
            notifyDataSetChanged();

            return adapterLayout;
        }
    }
    private void setProfPic(){
        profPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageURI = data.getData();
            Picasso.get().load(imageURI).centerCrop().fit().into(profPic);
            uploadFile();
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile(){
        if(imageURI != null){
            StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageURI));
            fileRef.putFile(imageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                }
                            }, 2000);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            urlProf = taskSnapshot.getMetadata().getReference().getName();
                            DocumentReference documentReference = db.collection("userData").document(fAuth.getCurrentUser().getUid());
                            documentReference.update("profilePicImg", urlProf);

                            Log.d("Test", urlProf);

                        }
                    });

            Log.d("TestOne", fileRef.getName()+"");
        }
        else {
            Toast.makeText(getActivity(), "No File Selected", Toast.LENGTH_LONG).show();

        }
    }
   private void refresh(){
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
   }
}
