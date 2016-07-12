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
public class GuidePayment extends AppCompatActivity {

    GuideUser guideUser;
    private DatabaseReference dataRef;

    EditText etPaymentMethod;
    EditText etHourlyPay;
    EditText etPackageDeals;
    EditText etCurrencyType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if new --> guidenew
        //if registered --> guiderequests
        setContentView(R.layout.activity_guidepaymentinfo);
        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");
        dataRef = FirebaseDatabase.getInstance().getReference();


        etPaymentMethod = (EditText) findViewById(R.id.etPaymentMethod);
        etHourlyPay = (EditText) findViewById(R.id.etHourlyPay);
        etPackageDeals = (EditText) findViewById(R.id.etPackageDeals);
        etCurrencyType = (EditText) findViewById(R.id.etCurrencyType);



        dataRef.child("Guide").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                GuideUser user = dataSnapshot.getValue(GuideUser.class);
                etPaymentMethod.setText(user.method);
                etHourlyPay.setText(user.timelyPay);
                etPackageDeals.setText(user.packageDeals);
                etCurrencyType.setText(user.currency);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuidePayment.this, "Cannot find user data", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        /*
        etPaymentMethod.setText(guideUser.method);
        etHourlyPay.setText(guideUser.timelyPay);
        etPackageDeals.setText(guideUser.packageDeals);
        etCurrencyType.setText(guideUser.currency);
        */

    }

    public void launchViewProfile(View v) {
        guideUser.method = etPaymentMethod.getText().toString();
        guideUser.timelyPay = etHourlyPay.getText().toString();
        guideUser.packageDeals = etPackageDeals.getText().toString();
        guideUser.currency = etCurrencyType.getText().toString();

        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, GuideViewProfile.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i); // brings up the second activity
    }
}