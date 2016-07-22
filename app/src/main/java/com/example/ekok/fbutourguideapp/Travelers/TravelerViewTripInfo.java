package com.example.ekok.fbutourguideapp.Travelers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.DatabaseReference;

public class TravelerViewTripInfo extends AppCompatActivity {
    private DatabaseReference dataRef;

    TextView tvPlace;
    TextView tvStartDate;
    TextView tvEndDate;
    TextView tvGroupSize;
    TextView tvLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelerviewtrip);
    }

    public void editRequest(View view) {
    }
}
