package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.ekok.fbutourguideapp.R;

/**
 * Created by mbytsang on 7/7/16.
 */
public class GuidePayment extends AppCompatActivity {

    GuideUser guideUser;

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

        etPaymentMethod = (EditText) findViewById(R.id.etPaymentMethod);
        etHourlyPay = (EditText) findViewById(R.id.etHourlyPay);
        etPackageDeals = (EditText) findViewById(R.id.etPackageDeals);
        etCurrencyType = (EditText) findViewById(R.id.etCurrencyType);

        etPaymentMethod.setText(guideUser.method);
        etHourlyPay.setText(guideUser.timelyPay);
        etPackageDeals.setText(guideUser.packageDeals);
        etCurrencyType.setText(guideUser.currency);

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