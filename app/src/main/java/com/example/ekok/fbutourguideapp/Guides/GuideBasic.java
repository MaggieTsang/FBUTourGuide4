package com.example.ekok.fbutourguideapp.Guides;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by mbytsang on 7/5/16.
 */
public class GuideBasic extends AppCompatActivity{

    ImageView ivProfilePic;
    private DatabaseReference dataRef;
    private FirebaseStorage storage;
    StorageReference storageRef;


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
        dataRef = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://fbutourguide.appspot.com");

        etName = (EditText) findViewById(R.id.etName);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etBasicAdditional = (EditText) findViewById(R.id.etBasicAdditional);
        etLanguages = (EditText) findViewById(R.id.etLanguages);

        //final String userId = getUid();
        // dataRef.child("Guide").child(userId).addListenerForSingleValueEvent(
        dataRef.child("Guide").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                GuideUser user = dataSnapshot.getValue(GuideUser.class);
                etName.setText(user.legalName);
                etLocation.setText(user.location);
                etBasicAdditional.setText(user.description);
                etLanguages.setText(user.languages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GuideBasic.this, "Cannot find user data", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });

        //Go to camera roll to upload profile pic
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

    //Launches Contact info view and saves current info to FireBase
    public void launchContact(View v) {
        dataRef.child("Guide").child("legalName").setValue(etName.getText().toString());
        dataRef.child("Guide").child("location").setValue(etLocation.getText().toString());
        dataRef.child("Guide").child("description").setValue(etBasicAdditional.getText().toString());
        dataRef.child("Guide").child("languages").setValue(etLanguages.getText().toString());
        Intent i = new Intent(this, GuideContact.class);
        startActivity(i);
    }


    //Load a profile picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                // Load the taken image into a preview
                ImageView ivPreview = (ImageView) findViewById(R.id.ivProfilePic);
                ivPreview.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        } else {

            if (resultCode == RESULT_OK){
                Uri targetUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    ivProfilePic.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }


        //storeData();
    }

    /*
    public void storeData(){

        // Get the data from an ImageView as bytes
        ivProfilePic.setDrawingCacheEnabled(true);
        ivProfilePic.buildDrawingCache();
        Bitmap bitmap = ivProfilePic.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(GuideBasic.this, "Failed to upload to database", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(GuideBasic.this, "Uploaded to database!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    */



    public final String APP_TAG = "UseCamera";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }
            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public void removePicture(View view) {
        ivProfilePic.setImageResource(R.drawable.profile);
    }
}
