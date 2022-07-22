package com.example.firebase_authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Request extends AppCompatActivity {

    TextView mScrapamount,mScraptype;
    Button mConfirmpickup;
    String userID;          // created for retrieving UID of users.
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mScrapamount       = findViewById(R.id.scrapamount);
        mConfirmpickup     = findViewById(R.id.confirmpickup);
        mScraptype         = findViewById(R.id.scraptype);

        mConfirmpickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scrapamount    = mScrapamount.getText().toString().trim();
                String scraptype    = mScraptype.getText().toString().trim();

                if(TextUtils.isEmpty(scrapamount))
                {
                    mScrapamount.setError("Scrap Amount is required");
                    return;
                }

                if(TextUtils.isEmpty(scraptype))
                {
                    mScraptype.setError("Scrap Type is required");
                    return;
                }



            }
        });

    }
}