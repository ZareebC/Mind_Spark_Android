package com.example.mindspark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText firstName, lastName, email, password;
    Button register;
    TextView loginText;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.createAccount);
        loginText = findViewById(R.id.loginAlready);
        progressBar = findViewById(R.id.registerProgress);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString().trim();
                String passString = password.getText().toString().trim();
                String firstNameString = firstName.getText().toString().trim();
                String lastnNameString = lastName.getText().toString().trim();
                if(TextUtils.isEmpty(emailString)){
                    email.setError("Email Required");
                    return;
                }
                if(TextUtils.isEmpty(passString)){
                    email.setError("Password Required");
                    return;
                }
                if(passString.length() < 6){
                    password.setError("Password must be longer than 6 characters");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(emailString, passString)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Account Created", Toast.LENGTH_LONG).show();
                                        userID = firebaseAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = fStore.collection("userData").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("firstName", firstNameString);
                                        user.put("lastName", lastnNameString);
                                        user.put("email", emailString);
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Tag user", "User submitted for " + userID);
                                            }
                                        });
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                    else{
                                        Toast.makeText(Register.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }


            }
        });

    }
}
