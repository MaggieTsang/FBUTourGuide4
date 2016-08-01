package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mbytsang on 7/7/16.
 */
public class GuideContact extends AppCompatActivity {

    private DatabaseReference dataRef;

    EditText etPhonePrimary;
    EditText etPhoneSecondary;
    EditText etEmail;
    EditText etContactAdditional;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if new --> guidenew
        //if registered --> guiderequests
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guidecontactinfo);
        dataRef = FirebaseDatabase.getInstance().getReference();

        etPhonePrimary = (EditText) findViewById(R.id.etPhonePrimary);
        etPhoneSecondary = (EditText) findViewById(R.id.etPhoneSecondary);
        etEmail = (EditText) findViewById(R.id.etTravelerEmail);
        etContactAdditional = (EditText) findViewById(R.id.etContactAdditional);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Guide").child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get user value
                    GuideUser user = dataSnapshot.getValue(GuideUser.class);
                    if (user != null) {
                        etPhonePrimary.setText(user.phonePrimary);
                        etPhoneSecondary.setText(user.phoneSecondary);
                        etEmail.setText(user.email);
                        etContactAdditional.setText(user.contactAdditional);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(GuideContact.this, "Cannot find user data", Toast.LENGTH_SHORT).show();
                    //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                }
            });
        }
    }

    public void launchPayment(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            dataRef.child("users").child(uid).child("Guide").child("Profile").child("phonePrimary").setValue(etPhonePrimary.getText().toString());
            dataRef.child("users").child(uid).child("Guide").child("Profile").child("phoneSecondary").setValue(etPhoneSecondary.getText().toString());
            dataRef.child("users").child(uid).child("Guide").child("Profile").child("email").setValue(etEmail.getText().toString());
            dataRef.child("users").child(uid).child("Guide").child("Profile").child("contactAdditional").setValue(etContactAdditional.getText().toString());

            if (etPhonePrimary.getText().toString().isEmpty()
                    || etEmail.getText().toString().isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Primary phone and email must be filled out to continue.", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(this, GuidePayment.class);
                startActivity(i);
                finish();
            }
        }
    }

    public void exit(View view) {
        Intent i = new Intent(GuideContact.this, GuideViewProfile.class);
        startActivity(i);
        finish();
    }

    public void back(View view) {
        Intent i = new Intent(GuideContact.this, GuideBasic.class);
        startActivity(i);
        finish();
    }
}
