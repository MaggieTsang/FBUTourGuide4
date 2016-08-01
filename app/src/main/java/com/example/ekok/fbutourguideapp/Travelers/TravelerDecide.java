package com.example.ekok.fbutourguideapp.Travelers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TravelerDecide extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage storage;
    StorageReference storageRef;

    TextView tvName;
    TextView tvPlace;
    TextView tvLang;
    TextView tvDesc;
    TextView tvPhone1;
    TextView tvPhone2;
    TextView tvEmail;
    TextView tvHourly;
    TextView tvMethod;
    TextView tvCurrency;

    String reqGuide;
    String loc;
    String reqBucket;
    String reqID;
    String dates;
    String lang;
    String gSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_travelerdecide);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://fbutourguide.appspot.com/");

        tvName = (TextView) findViewById(R.id.tvGuideName);
        tvPlace = (TextView) findViewById(R.id.tvGuideLocation);
        tvLang = (TextView) findViewById(R.id.tvGuideLanguages);
        tvDesc = (TextView) findViewById(R.id.tvFillDescription);
        tvPhone1 = (TextView) findViewById(R.id.tvPhone1);
        tvPhone2 = (TextView) findViewById(R.id.tvPhone2);
        tvEmail = (TextView) findViewById(R.id.tvGuideEmail);
        tvHourly = (TextView) findViewById(R.id.tvHourly);
        tvMethod = (TextView) findViewById(R.id.tvMethod);
        tvCurrency = (TextView) findViewById(R.id.tvCurrency);

        reqGuide = getIntent().getSerializableExtra("reqGuide").toString();
        loc = getIntent().getSerializableExtra("loc").toString();
        if (getIntent().getSerializableExtra("reqBucket") != null) {
            reqBucket = getIntent().getSerializableExtra("reqBucket").toString();
        }
        reqID = getIntent().getSerializableExtra("reqID").toString();
        dates = getIntent().getSerializableExtra("dates").toString();
        lang = getIntent().getSerializableExtra("languages").toString();
        gSize = getIntent().getSerializableExtra("groupSize").toString();


        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.child("users").child(reqGuide).child("profilePic").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ImageView image = (ImageView) findViewById(R.id.ivProfilePicture);
                image.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("ErrorMessage", "No profilePic in database");
            }
        });

        dataRef.child("users").child(reqGuide).child("Guide").child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvName.setText(dataSnapshot.child("legalName").getValue().toString());
                tvPlace.setText(dataSnapshot.child("location").getValue().toString());
                tvLang.setText(dataSnapshot.child("languages").getValue().toString());
                tvDesc.setText(dataSnapshot.child("description").getValue().toString());
                tvPhone1.setText(dataSnapshot.child("phonePrimary").getValue().toString());
                tvPhone2.setText(dataSnapshot.child("phoneSecondary").getValue().toString());
                tvEmail.setText(dataSnapshot.child("email").getValue().toString());
                tvHourly.setText(dataSnapshot.child("timelyPay").getValue().toString());
                tvMethod.setText(dataSnapshot.child("method").getValue().toString());
                tvCurrency.setText(dataSnapshot.child("currency").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Cancelled.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void TravelerAccept(View view) {
        Toast.makeText(getApplicationContext(), "Accepted!", Toast.LENGTH_SHORT).show();
        dataRef.child("requests").child(loc).child(reqBucket).setValue(null);
        dataRef.child("users").child(reqGuide).child("Guide").child("Accepted").child(loc).child(reqID).setValue(uid);
        dataRef.child("users").child(uid).child("Traveler").child("Accepted").child(reqID).child("location").setValue(loc);
        dataRef.child("users").child(uid).child("Traveler").child("Accepted").child(reqID).child("dates").setValue(dates);
        dataRef.child("users").child(uid).child("Traveler").child("Accepted").child(reqID).child("guideID").setValue(reqGuide);
        dataRef.child("users").child(uid).child("Traveler").child("Accepted").child(reqID).child("groupSize").setValue(gSize);
        dataRef.child("users").child(uid).child("Traveler").child("Accepted").child(reqID).child("languages").setValue(lang);
        dataRef.child("users").child(reqGuide).child("Guide").child("Pending").child(loc).child(reqBucket).setValue(null);
        dataRef.child("users").child(uid).child("Traveler").child("Pending").child(reqBucket).setValue(null);
        dataRef.child("users").child(uid).child("Traveler").child("trips_current").child(reqID).setValue(null);
        finish();
    }

    public void TravelerDecline(View view) {
        Toast.makeText(getApplicationContext(), "Declined!", Toast.LENGTH_SHORT).show();
        dataRef.child("users").child(reqGuide).child("Guide").child("Pending").child(loc).child(reqBucket).setValue(null);
        dataRef.child("users").child(uid).child("Traveler").child("Pending").child(reqBucket).setValue(null);
        dataRef.child("users").child(reqGuide).child("Guide").child("TravelerDeclined").child(loc).child(reqBucket).setValue("rejection hurts bro");
        finish();
    }
}
