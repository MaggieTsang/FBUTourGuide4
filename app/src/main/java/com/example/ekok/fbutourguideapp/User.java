package com.example.ekok.fbutourguideapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.List;

/**
 * Created by ekok on 7/15/16.
 */
public class User extends FirebaseUser {
    String Uid;
    String displayName;
    String email;

    public User (String Uid, String displayName, String email) {
        this.Uid = Uid;
        this.displayName = displayName;
        this.email = email;
    }

    @NonNull
    @Override
    public String getUid() {
        return Uid;
    }

    @NonNull
    @Override
    public String getProviderId() {
        return null;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Nullable
    @Override
    public List<String> getProviders() {
        return null;
    }

    @NonNull
    @Override
    public List<? extends UserInfo> getProviderData() {
        return null;
    }

    @NonNull
    @Override
    public FirebaseUser zzN(@NonNull List<? extends UserInfo> list) {
        return null;
    }

    @Override
    public FirebaseUser zzaK(boolean b) {
        return null;
    }

    @NonNull
    @Override
    public FirebaseApp zzOl() {
        return null;
    }

    @Nullable
    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    @Override
    public Uri getPhotoUrl() {
        return null;
    }

    @Nullable
    @Override
    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String zzOm() {
        return null;
    }

    @Override
    public void zzhG(@NonNull String s) {

    }
}
