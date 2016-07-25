package com.example.ekok.fbutourguideapp.Travelers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ekok on 7/5/16.
 */
public class NewRequest extends AppCompatActivity{
    private final static String TAG = "Firebase";
    private DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    RequestModel requestModel;

    EditText etPlace;
    EditText etStartDate;
    EditText etEndDate;
    EditText etGroupSize;
    EditText etLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

                    final DatabaseReference myOtherRef = dataRef.child("requests").child(place).push();

                    myOtherRef.child("traveler_uid").setValue(uid);
                    myOtherRef.child("requestId").setValue(myRef.getKey());
                    myOtherRef.child("displayName").setValue(user.getDisplayName());
                    myOtherRef.child("dates").setValue(startDate + " - " + endDate);

                    // READ DATA
                    myOtherRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                            //

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                            Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                            // A comment has changed, use the key to determine if we are displaying this
                            // comment and if so displayed the changed comment.
                            RequestModel request = dataSnapshot.getValue(RequestModel.class);
                            String requestKey = dataSnapshot.getKey();
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                            // A comment has changed, use the key to determine if we are displaying this
                            // comment and if so remove it.
                            String requestKey = dataSnapshot.getKey();
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                            Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                            // A comment has changed position, use the key to determine if we are
                            // displaying this comment and if so move it.
                            RequestModel movedRequest = dataSnapshot.getValue(RequestModel.class);
                            String requestKey = dataSnapshot.getKey();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "addRequest:onCancelled", databaseError.toException());
                            Toast.makeText(getApplicationContext(), "Failed to load requests.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Send data back to home screen
                    String placeGetter = place;
                    Intent data = new Intent();
                    data.putExtra("place", placeGetter);
                    setResult(RESULT_OK, data);
                    finish();
                }

            });
        }
    }
}