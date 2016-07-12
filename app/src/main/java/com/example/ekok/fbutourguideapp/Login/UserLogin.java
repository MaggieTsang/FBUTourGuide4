package com.example.ekok.fbutourguideapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekok.fbutourguideapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    LoginButton loginButton;
    CallbackManager callbackManager;

    SignInButton signInButton;
    Button signOutButton;
    TextView statusTextView;
    GoogleApiClient mGoogleAppClient;

    private static final String TAG = "SignInActivity";
    private static final String TAG_2 = "FacebookLogin";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

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
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        };


        //  if you can see this you pulled yahss

        loginButton = (LoginButton) findViewById(R.id.btnLogin);
        loginButton.setReadPermissions("email");

        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG_2, "facebook:onSuccess" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                String name = Profile.getCurrentProfile().getFirstName();
                Toast.makeText(getApplicationContext(), "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UserLogin.this, UserType.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                // App code
                Log.d(TAG_2, "facebook:onCancel");
                Toast.makeText(getApplicationContext(), "Login cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d(TAG_2, "facebook:onError", exception);
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                exception.printStackTrace();

            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleAppClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        statusTextView = (TextView) findViewById(R.id.status_textview);
        signInButton = (SignInButton) findViewById(R.id.btnLoginGoogle);
        signInButton.setOnClickListener(this);

        signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(this);
    }


    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginGoogle:
                signIn();
                break;
            case R.id.signOutButton:
                signOut();
            case R.id.com_facebook_button_xout:
                signOut();
                break;
        }
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleAppClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            //Signed in successfully, show authenticated UI
            GoogleSignInAccount acct = result.getSignInAccount();
            statusTextView.setText("Hello, " + acct.getDisplayName());
        }
        else {

        }
    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
       // showProgressDialog();
        // [END_EXCLUDE]

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

                        // [START_EXCLUDE]
                     //   hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]


    private void signOut() {

        mAuth.signOut();
        LoginManager.getInstance().logOut();

        Auth.GoogleSignInApi.signOut(mGoogleAppClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                statusTextView.setText("Signed out");
            }
        });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}
