package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mbytsang on 7/7/16.
 */
public class GuideContact extends AppCompatActivity {

    GuideUser guideUser;
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
        setContentView(R.layout.activity_guidecontactinfo);
        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");
        dataRef = FirebaseDatabase.getInstance().getReference();


        etPhonePrimary = (EditText) findViewById(R.id.etPhonePrimary);
        etPhoneSecondary = (EditText) findViewById(R.id.etPhoneSecondary);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContactAdditional = (EditText) findViewById(R.id.etContactAdditional);



        dataRef.child("Guide").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                GuideUser user = dataSnapshot.getValue(GuideUser.class);
                etPhonePrimary.setText(user.phonePrimary);
                etPhoneSecondary.setText(user.phoneSecondary);
                etEmail.setText(user.email);
                etContactAdditional.setText(user.contactAdditional);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuideContact.this, "Cannot find user data", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        /*
        etPhonePrimary.setText(guideUser.phonePrimary);
        etPhoneSecondary.setText(guideUser.phoneSecondary);
        etEmail.setText(guideUser.email);
        etContactAdditional.setText(guideUser.contactAdditional);
        */

    }

    public void launchPayment(View v) {
        guideUser.phonePrimary = etPhonePrimary.getText().toString();
        guideUser.phoneSecondary = etPhoneSecondary.getText().toString();
        guideUser.email = etEmail.getText().toString();
        guideUser.contactAdditional = etContactAdditional.getText().toString();

        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, GuidePayment.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i); // brings up the second activity
    }
}
