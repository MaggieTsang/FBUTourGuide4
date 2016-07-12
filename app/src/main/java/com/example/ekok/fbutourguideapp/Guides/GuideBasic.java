package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ekok.fbutourguideapp.R;

import java.io.FileNotFoundException;

/**
 * Created by mbytsang on 7/5/16.
 */
public class GuideBasic extends AppCompatActivity{

    GuideUser guideUser;

    ImageView ivProfilePic;

    EditText etName;
    EditText etLocation;
    EditText etBasicAdditional;
    EditText etLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if new --> guidenew
        //if registered --> guiderequests
        setContentView(R.layout.activity_guidebasic);
        guideUser = (GuideUser) getIntent().getSerializableExtra("guideUser");

        etName = (EditText) findViewById(R.id.etName);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etBasicAdditional = (EditText) findViewById(R.id.etBasicAdditional);
        etLanguages = (EditText) findViewById(R.id.etLanguages);
        etName.setText(guideUser.legalName);
        etLocation.setText(guideUser.location);
        etBasicAdditional.setText(guideUser.description);
        etLanguages.setText(guideUser.languages);

        ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);
        Button loadImage = (Button) findViewById(R.id.btnUploadImage);
        loadImage.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }});

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                ivProfilePic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }




    public void launchContact(View v) {
        guideUser.legalName = etName.getText().toString();
        guideUser.location = etLocation.getText().toString();
        guideUser.description = etBasicAdditional.getText().toString();
        guideUser.languages = etLanguages.getText().toString();

        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, GuideContact.class);
        i.putExtra("guideUser", guideUser);
        startActivity(i); // brings up the second activity
    }

}
