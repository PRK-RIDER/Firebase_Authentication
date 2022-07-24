package com.example.firebase_authentication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.appsearch.StorageInfo;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    TextView fullName,email,mobile;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Button mRaiserequest,change_profile_image,mCancelrequest;
    ImageView profileimage;
    //StorageRefernece storgaereference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobile              = findViewById(R.id.ProfileMobile);
        fullName            = findViewById(R.id.ProfileName);
        email               = findViewById(R.id.ProfileEmail);
        mRaiserequest       = findViewById(R.id.Raisepickup);
        mCancelrequest      = findViewById(R.id.CancelPickup);
        profileimage        = findViewById(R.id.profileimage);
        change_profile_image= findViewById(R.id.changeimage);

        fAuth  = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);

        // Retrieving data from database using documentReference.......
        // addSnapshot Listner data changes from database.

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mobile.setText(documentSnapshot.getString("mobile"));
                fullName.setText(documentSnapshot.getString("fName"));
                email.setText(documentSnapshot.getString("email"));
            }


        });

        mRaiserequest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Request.class));

            }

    });

        change_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Open Gallery

                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Uri imageUri = data.getData();
                profileimage.setImageURI(imageUri);
            }
        }
    }

    public void logout(View view) {
        //Logged out user
        FirebaseAuth.getInstance().signOut();

        // sending user to login page
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }


}