package com.example.ekok.fbutourguideapp.Travelers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.FireBase.FirebaseHelper;
import com.example.ekok.fbutourguideapp.Models.TravelerModel;
import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mbytsang on 7/5/16.
 */
public class TravelerNewTrip extends AppCompatActivity{
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    DatabaseReference db;
    FirebaseHelper helper;
    EditText etPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelernew);

        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etPlace = (EditText) findViewById(R.id.etPlace);

        // SAVE
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // GET DATA
                String place = etPlace.getText().toString();

                // SET DATA
                TravelerModel t = new TravelerModel();
                t.setName(place);

                // SAVE
                if (place != null && place.length() > 0) {
                    if (helper.save(t))
                    {
                        etPlace.setText("");
                    }
                }
                else {
                    Toast.makeText(TravelerNewTrip.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void submitRequest(View view) {
//        String etPlace = findViewById(R.id.etPlace).toString();
//
//        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//        editor.putString("etPlace", etPlace);
//        editor.commit();
//        finish();
        String mailID = findViewById(R.id.etPlace).toString();

        //Here define all your sharedpreferences code with key and value
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("etPlace", mailID );
        edit.commit();
        finish();
    }
}
