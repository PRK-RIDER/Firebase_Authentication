package com.example.firebase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG1";

    TextView mFullName,mEmail,mPassword,mMobile;
    Button mRegisterButton;
    TextView mLoginButton;
    String userID;          // created for retrieving UID of users.
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName       = findViewById(R.id.fullname);
        mEmail          = findViewById(R.id.email);
        mPassword       = findViewById(R.id.password);
        mMobile         = findViewById(R.id.mobile);
        mRegisterButton = findViewById(R.id.loginBtn);
        mLoginButton    = findViewById(R.id.createText);
        progressBar     = findViewById(R.id.progressBar);

        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email    = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String mobile   = mMobile.getText().toString();
                String fullname = mFullName.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is required");
                    return;
                }

                if(password.length()<6)
                {
                    mPassword.setError("Password must be >=6");
                }

                //Progressbar

                progressBar.setVisibility(View.VISIBLE);

                //Checking USer Already Logged_in or NOt

                if(fAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }

                //Registering the user in Firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this, "user created", Toast.LENGTH_SHORT).show();

                            //Storing user data to firestore database....

                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            Map<String,Object> user = new HashMap<>();                  //creating data to store using HASH MAP
                            user.put("fName",fullname);
                            user.put("email",email);
                            user.put("mobile",mobile);


                            // Inserting data to Firestore cloud database.

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: User Profile is created for"+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG1,"onFailure: User Profile is not created for"+ userID);
                                }
                            });



                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });

    }
}