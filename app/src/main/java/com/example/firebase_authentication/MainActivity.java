package com.example.firebase_authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logout(View view) {
        //Logged out user
        FirebaseAuth.getInstance().signOut();

        // sending user to login page
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}