package com.example.mindspark;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment {


    Button create;
    int authorCount, fieldCount, titleCount, postTypeCount, estReadTimeCount, numReadsCount, likesCount;
    ArrayList<String> body;
    ArrayList<Integer> minutes;
    String url;
    String subheading;
    ArrayList<String> comments;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    User user;
    String authorID;
    ImageView testImg;
    ProgressBar progressBar;
    StorageReference storageReference;
    String[] examples = {"1 billion dollars lost by NASA after wasting expended resources on false assumption", "Life of a germ. Is it more important than we believe?", "This is an example header because I could not think of anything else. Hope you are having a good day :)"};

    private static final int PICK_IMAGE_REQUEST = 1;
    Button uploadImage;
    private Uri imageURI;
    private StorageTask mUploadTask;
    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        create = view.findViewById(R.id.submit);
        testImg = view.findViewById(R.id.testImg);
        progressBar = view.findViewById(R.id.progressBar);
        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        authorID = fAuth.getCurrentUser().getUid();
        user = MainActivity.u;
        uploadImage = view.findViewById(R.id.uploadImage);
        authorCount = 0;
        fieldCount =0;
        titleCount = 0;
        postTypeCount = 0;
        estReadTimeCount = 0;
        numReadsCount = 0;
        likesCount = 0;
        body = new ArrayList<>();
        comments = new ArrayList<>();
        minutes = new ArrayList<>();
        submit();
        uploadImageMethod();
    }

    private void submit(){
        Map<String, Object> data = new HashMap<>();
        data.put("Author", user.getFirstName()+ " " + user.getLastName());
        authorCount++;
        data.put("Field", "Field "+fieldCount);
        fieldCount++;
        data.put("Likes",likesCount);
        likesCount++;
        data.put("Title", "Title " + titleCount);
        titleCount++;
        data.put("estReadTime", estReadTimeCount);
        estReadTimeCount++;
        data.put("postType", "Post Type "+postTypeCount);
        postTypeCount++;
        data.put("numReads", numReadsCount);
        numReadsCount++;
        data.put("BodyText", body);
        data.put("actReadTime", minutes);
        data.put("Comments", comments);
        data.put("authorID", authorID);
        int rand = (int)(Math.random())+2;
        subheading = examples[rand];
        data.put("subheading", subheading);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
                new CountDownTimer(3000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {


                    }

                    @Override
                    public void onFinish() {
                        Log.d("TestURl", url+"");
                        data.put("imageURL", url);
                        db.collection("article").add(data);
                    }

                }.start();



            }
        });
    }

    private void uploadImageMethod(){
        uploadImage.setOnClickListener(new View.OnClickListener() {
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
            Picasso.get().load(imageURI).into(testImg);
            testImg.setVisibility(View.VISIBLE);
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
                                    progressBar.setProgress(0);
                                }
                            }, 2000);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            url = taskSnapshot.getMetadata().getReference().getName();

                            Log.d("Test", url);

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);
                        }
                    });


            Log.d("TestOne", fileRef.getName()+"");
        }
        else {
            Toast.makeText(getActivity(), "No File Selected", Toast.LENGTH_LONG).show();

        }
    }
}
