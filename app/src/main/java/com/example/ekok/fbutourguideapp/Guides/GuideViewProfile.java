package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

/**
 * Created by mbytsang on 7/6/16.
 */
public class GuideViewProfile extends AppCompatActivity{

    private DatabaseReference dataRef;
    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference profPicData;

    TextView tvName;
    TextView tvLocation;
    TextView tvLanguages;
    TextView tvFillDescription;
    TextView tvPhone1;
    TextView tvPhone2;
    TextView tvEmail;
    TextView tvMethod;
    TextView tvCurrency;
    TextView tvHourly;

    //Traveler's view: can request/message
    //Guide's view: just a preview, cannot request or message thyself
    //Insert profile info via ExpandableListView??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideprofilepreview);
        dataRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://fbutourguide.appspot.com/");

        tvName = (TextView) findViewById(R.id.tvGuideName);
        tvLocation = (TextView) findViewById(R.id.tvGuideLocation);
        tvLanguages = (TextView) findViewById(R.id.tvGuideLanguages);
        tvFillDescription = (TextView) findViewById(R.id.tvFillDescription);
        tvPhone1 = (TextView) findViewById(R.id.tvPhone1);
        tvPhone2 = (TextView) findViewById(R.id.tvPhone2);
        tvEmail = (TextView) findViewById(R.id.tvGuideEmail);
        tvMethod = (TextView) findViewById(R.id.tvMethod);
        tvCurrency = (TextView) findViewById(R.id.tvCurrency);
        tvHourly = (TextView) findViewById(R.id.tvHourly);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            //profPicData = storage.getReferenceFromUrl("gs://fbutourguide.appspot.com/profilePic.jpg");
            //Load profile pic preview
            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.child("users").child(uid).child("profilePic").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Data for "images/island.jpg" is returns, use this as needed
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ImageView image = (ImageView) findViewById(R.id.ivProfilePicture);
                    image.setImageBitmap(bmp);
                    //Toast.makeText(GuideViewProfile.this, "Uploaded profile picture!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    //Toast.makeText(GuideViewProfile.this, "Please upload a profile picture.", Toast.LENGTH_SHORT).show();
                    Log.d("ErrorMessage", "No profilePic in database");
                }
            });

            dataRef.child("users").child(uid).child("Guide").child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get user value
                    GuideUser user = dataSnapshot.getValue(GuideUser.class);
                    if (user != null) {
                        tvName.setText(user.legalName);
                        tvLocation.setText(user.location);
                        tvLanguages.setText(user.languages);
                        tvFillDescription.setText(user.description);
                        tvPhone1.setText(user.phonePrimary);
                        tvPhone2.setText(user.phoneSecondary);
                        tvEmail.setText(user.email);
                        tvMethod.setText(user.method);
                        tvCurrency.setText(user.currency);
                        tvHourly.setText(user.timelyPay);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(GuideViewProfile.this, "Cannot find user data", Toast.LENGTH_SHORT).show();
                    //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit, menu);
        return true;
    }

    public void goBackToBasic(View view) {
        Intent i = new Intent(GuideViewProfile.this, GuideBasic.class);
        startActivity(i);
        finish();
    }

    public void saveProfile(View view) {
        //Intent i = new Intent(this, GuideViewRequests.class);
        //startActivity(i);
        finish();
    }

    public void exit(MenuItem item) {
        finish();
    }
}
