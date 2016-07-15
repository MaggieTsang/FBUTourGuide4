package com.example.ekok.fbutourguideapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.example.ekok.fbutourguideapp.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserLogin extends AppCompatActivity {
    private static final String TAG = "FacebookLogin";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference myRef;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        myRef =  FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btnLogin);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String name = Profile.getCurrentProfile().getFirstName();
                Toast.makeText(getApplicationContext(), "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UserLogin.this, UserType.class);
                startActivity(i);
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
                myRef.child("users").child(firebaseUser.getUid()).setValue(user);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                Toast.makeText(getApplicationContext(), "Login cancelled", Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                // [END_EXCLUDE]
            }
        });

//        callbackManager = CallbackManager.Factory.create();
//
//        loginButton = (LoginButton) findViewById(R.id.btnLogin);
//        loginButton.setReadPermissions("email", "public_profile");
//
//        AppEventsLogger.activateApp(this);
//
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                handleFacebookAccessToken(AccessToken.getCurrentAccessToken());
//                mAuth = FirebaseAuth.getInstance();
//                mAuthListener = new FirebaseAuth.AuthStateListener() {
//                    @Override
//                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                        FirebaseUser user = firebaseAuth.getCurrentUser();
//                        if (user != null) {
//                            // User is signed in
//                            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                        } else {
//                            // User is signed out
//                            Log.d(TAG, "onAuthStateChanged:signed_out");
//                        }
//                        // ...
//                    }
//                };
//
//                String name = Profile.getCurrentProfile().getFirstName();
//                Toast.makeText(getApplicationContext(), "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(UserLogin.this, UserType.class);
//                startActivity(i);
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                Toast.makeText(getApplicationContext(), "Login cancelled", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
//                exception.printStackTrace();
//
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(UserLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
