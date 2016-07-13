package com.example.ekok.fbutourguideapp.Travelers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mbytsang on 7/5/16.
 */
public class NewRequest extends AppCompatActivity{
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    DatabaseReference db;
    RequestFirebase helper;

    private final static String TAG = "Firebase";

    EditText etPlace;
    EditText etStartDate;
    EditText etEndDate;
    EditText etGroupSize;
    EditText etLanguages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelernew);

        db = FirebaseDatabase.getInstance().getReference();
        helper = new RequestFirebase(db);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etPlace = (EditText) findViewById(R.id.etPlace);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        etGroupSize = (EditText) findViewById(R.id.etGroupSize);
        etLanguages = (EditText) findViewById(R.id.etLanguages);

        // SAVE
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // GET DATA
                String place = etPlace.getText().toString();
                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();
                String groupSize = etGroupSize.getText().toString();
                int finalGroupSize = Integer.parseInt(groupSize);
                String languages = etLanguages.getText().toString();

                // SET DATA
                RequestModel request = new RequestModel();
                request.setPlace(place);
                request.setStartDate(startDate);
                request.setEndDate(endDate);
                request.setGroupSize(finalGroupSize);
                request.setLanguages(languages);

                // SAVE
                if (place != null && place.length() > 0
                        && startDate != null && startDate.length() > 0
                        && endDate != null && endDate.length() > 0
                        && groupSize != null && finalGroupSize > 0
                        && languages != null && languages.length() > 0) {
                    if (helper.save(request))
                    {
                        etPlace.setText("");
                        etStartDate.setText("");
                        etEndDate.setText("");
                        etGroupSize.setText("");
                        etLanguages.setText("");
                        finish();
                    }
                }
                else {
                    Toast.makeText(NewRequest.this, "No field should be left empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        // Read from the database
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                RequestModel request = dataSnapshot.getValue(RequestModel.class);
//                String place = request.getPlace().toString();
//                Toast.makeText(getApplicationContext(), place, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }
}
