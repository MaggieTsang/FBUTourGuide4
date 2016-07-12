package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mbytsang on 7/6/16.
 */
public class GuideViewProfile extends AppCompatActivity{

    GuideUser guideUser;
    DatabaseReference dataBaseRef;
    GuideFirebase guideFirebase;

    TextView tvName;
    TextView tvLocation;
    TextView tvLanguages;
    TextView tvFillDescription;
    TextView tvPhone1;
    TextView tvPhone2;
    TextView tvEmail;
    TextView tvMethod;
    TextView tvCurrency;
    TextView tvHourly;

    //Traveler's view: can request/message
    //Guide's view: just a preview, cannot request or message themself


    //Insert profile info via ExpandableListView??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideprofilepreview);
        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");
        dataBaseRef = FirebaseDatabase.getInstance().getReference();
        guideFirebase = new GuideFirebase(dataBaseRef);


        tvName = (TextView) findViewById(R.id.tvName);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvLanguages = (TextView) findViewById(R.id.tvLanguages);
        tvFillDescription = (TextView) findViewById(R.id.tvFillDescription);
        tvPhone1 = (TextView) findViewById(R.id.tvPhone1);
        tvPhone2 = (TextView) findViewById(R.id.tvPhone2);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvMethod = (TextView) findViewById(R.id.tvMethod);
        tvCurrency = (TextView) findViewById(R.id.tvCurrency);
        tvHourly = (TextView) findViewById(R.id.tvHourly);

        tvName.setText(guideUser.legalName);
        tvLocation.setText(guideUser.location);
        tvLanguages.setText(guideUser.languages);
        tvFillDescription.setText(guideUser.description);
        tvPhone1.setText(guideUser.phonePrimary);
        tvPhone2.setText(guideUser.phoneSecondary);
        tvEmail.setText(guideUser.email);
        tvMethod.setText(guideUser.method);
        tvCurrency.setText(guideUser.currency);
        tvHourly.setText(guideUser.timelyPay);
    }

    public void goBackToBasic(View view) {
        Intent i = new Intent(this, GuideBasic.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i);
    }

    public void saveProfile(View view) {
        guideFirebase.saveToGuide(guideUser);
        Intent i = new Intent(this, GuideViewRequests.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i);
    }
}
