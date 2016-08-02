package com.example.ekok.fbutourguideapp.Travelers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ekok on 7/5/16.
 */
public class TravelerNewRequest extends AppCompatActivity{
    private final static String TAG = "Firebase";
    private DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TravelerRequestModel requestModel = new TravelerRequestModel();

    EditText etPlace;
    EditText etStartDate;
    EditText etEndDate;
    EditText etGroupSize;
    EditText etLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_travelernew);

        if (user != null) {
            // The user's ID, unique to the Firebase project.
            final String uid = user.getUid();
            final DatabaseReference myRef = dataRef.child("users").child(uid).child("Traveler").child("trips_current").push();

            Button btnSubmit = (Button) findViewById(R.id.btnEditRequest);
            etPlace = (EditText) findViewById(R.id.etPlace);
            etStartDate = (EditText) findViewById(R.id.etStartDate);
            etEndDate = (EditText) findViewById(R.id.etEndDate);
            etGroupSize = (EditText) findViewById(R.id.etGroupSize);
            etLanguages = (EditText) findViewById(R.id.etTravelerLanguages);

            // SAVE
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // GET DATA
                    if (etPlace.getText().toString().isEmpty()
                            || etStartDate.getText().toString().isEmpty()
                            || etEndDate.getText().toString().isEmpty()
                            || etGroupSize.getText().toString().isEmpty()
                            || etLanguages.getText().toString().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "All fields must be filled out!", Toast.LENGTH_SHORT).show();
                    } else if(etGroupSize.getText().toString().equals("0")){
                        Toast.makeText(getApplicationContext(), "Group size must be more than 0 people.", Toast.LENGTH_SHORT).show();
                    } else {
                        final String place = etPlace.getText().toString();
                        final String startDate = etStartDate.getText().toString();
                        final String endDate = etEndDate.getText().toString();
                        final String groupSize = etGroupSize.getText().toString();
                        final int finalGroupSize = Integer.parseInt(groupSize);
                        final String languages = etLanguages.getText().toString();

                        // SET DATA
                        if (place.equalsIgnoreCase(place)) {
                            myRef.child("place").setValue(place);
                        }

                        myRef.child("startDate").setValue(startDate);
                        myRef.child("endDate").setValue(endDate);
                        myRef.child("groupSize").setValue(finalGroupSize);
                        myRef.child("languages").setValue(languages);

                        requestModel.place = place;
                        requestModel.startDate = startDate;
                        requestModel.endDate = endDate;
                        requestModel.groupSize = finalGroupSize;
                        requestModel.languages = languages;

                        final DatabaseReference myOtherRef = dataRef.child("requests").child(place.toLowerCase()).push();

                        myOtherRef.child("traveler_uid").setValue(uid);
                        myOtherRef.child("requestId").setValue(myRef.getKey());
                        myOtherRef.child("displayName").setValue(user.getDisplayName());
                        myOtherRef.child("dates").setValue(startDate + " - " + endDate);
                        myOtherRef.child("groupSize").setValue(finalGroupSize);
                        myOtherRef.child("languages").setValue(languages);

                        finish();
                    }
                }

            });
        }
    }

    public void exit(View view) {
        finish();
    }
}