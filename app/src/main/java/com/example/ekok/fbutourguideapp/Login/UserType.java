package com.example.ekok.fbutourguideapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.Guides.GuideBasic;
import com.example.ekok.fbutourguideapp.Guides.GuideUser;
import com.example.ekok.fbutourguideapp.Guides.GuideViewRequests;
import com.example.ekok.fbutourguideapp.R;
import com.example.ekok.fbutourguideapp.Travelers.TravelerViewTrips;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mbytsang on 7/5/16.
 */
public class UserType extends AppCompatActivity{

    private DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertype);
        dataRef = FirebaseDatabase.getInstance().getReference();
    }

    // From user type to either traveler or guide
    public void launchTraveler(View v) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, TravelerViewTrips.class);
        startActivity(i); // brings up the second activity
    }

    public void launchGuide(View v) {
        //If profile name is empty, go to GuideBasic. Else, go to GuideViewRequests

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            dataRef.child("users").child(uid).child("Guide").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get user value
                    GuideUser user = dataSnapshot.getValue(GuideUser.class);
                    if (user == null || user.legalName.isEmpty()){
                        Intent i = new Intent(UserType.this, GuideBasic.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(UserType.this, GuideViewRequests.class);
                        startActivity(i);
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(UserType.this, "Cannot find user data", Toast.LENGTH_SHORT).show();
                    //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                }
            });


        } else {
            Toast.makeText(UserType.this, "User not authenticated.", Toast.LENGTH_SHORT).show();

        }
    }

}
