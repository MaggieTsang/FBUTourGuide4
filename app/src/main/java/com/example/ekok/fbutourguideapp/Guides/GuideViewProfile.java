package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mbytsang on 7/6/16.
 */
public class GuideViewProfile extends AppCompatActivity{

    private DatabaseReference dataRef;

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
        //guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");
        dataRef = FirebaseDatabase.getInstance().getReference();

        tvName = (TextView) findViewById(R.id.tvName);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvLanguages = (TextView) findViewById(R.id.tvLanguages);
        tvFillDescription = (TextView) findViewById(R.id.tvFillDescription);
        tvPhone1 = (TextView) findViewById(R.id.tvPhone1);
        tvPhone2 = (TextView) findViewById(R.id.tvPhone2);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvMethod = (TextView) findViewById(R.id.tvMethod);
        tvCurrency = (TextView) findViewById(R.id.tvCurrency);
        tvHourly = (TextView) findViewById(R.id.tvHourly);

        dataRef.child("Guide").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                GuideUser user = dataSnapshot.getValue(GuideUser.class);
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

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuideViewProfile.this, "Cannot find user data", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });
    }

    public void goBackToBasic(View view) {
        Intent i = new Intent(this, GuideBasic.class);
        startActivity(i);
    }

    public void saveProfile(View view) {
        Intent i = new Intent(this, GuideViewRequests.class);
        startActivity(i);
    }
}
