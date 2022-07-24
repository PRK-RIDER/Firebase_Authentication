package com.example.firebase_authentication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.FinalizablePhantomReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Request extends AppCompatActivity {

    public static final String TAG_1 = "TAG1";
    public static final String TAG = "TAG";
    TextView mScrapamount,mScraptype;
    Date mscrapdate;
    Button mConfirmpickup;
    String userID,userID1;          // created for retrieving UID of users.
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mScrapamount       = findViewById(R.id.scrapamount);
        mConfirmpickup     = findViewById(R.id.confirmpickup);
        mScraptype         = findViewById(R.id.scraptype);

        fAuth              = FirebaseAuth.getInstance();
        fStore             = FirebaseFirestore.getInstance();


        mConfirmpickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scrapamount  = mScrapamount.getText().toString().trim();
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


               userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("pickuprequest").document(userID);
                Map<String,Object> user = new HashMap<>();

                user.put("Amount",scrapamount);
                user.put("type",scraptype);



                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"onSuccess: Pickup Request Confirmed for"+ userID);
                        Toast.makeText(Request.this, "Pickup Request Confirmed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG_1,"onFailure: Pickup Request Failed for"+ userID);
                        Toast.makeText(Request.this, "Pickup Request Failed", Toast.LENGTH_SHORT).show();
                    }
                });






            }
        });

    }
}