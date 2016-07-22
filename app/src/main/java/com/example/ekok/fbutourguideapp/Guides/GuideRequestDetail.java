package com.example.ekok.fbutourguideapp.Guides;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mbytsang on 7/22/16.
 */
public class GuideRequestDetail extends AppCompatActivity{

    DatabaseReference dataRef;

    TextView tvReqPlace;
    TextView tvReqDate;
    TextView tvReqGroupNum;
    TextView tvReqLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guiderequestdetails);
        dataRef = FirebaseDatabase.getInstance().getReference();

        tvReqPlace = (TextView) findViewById(R.id.tvReqPlace);
        tvReqDate = (TextView) findViewById(R.id.tvReqDate);
        tvReqGroupNum = (TextView) findViewById(R.id.tvReqGroupNum);
        tvReqLanguages = (TextView) findViewById(R.id.tvReqLanguages);

        tvReqPlace.setText("req place");
        tvReqDate.setText("req date");
        tvReqGroupNum.setText("req group size");
        tvReqLanguages.setText("req languages");

    }


}
