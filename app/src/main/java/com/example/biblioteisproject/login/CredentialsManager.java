package com.example.biblioteisproject.login;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class CredentialsManager {
    private static final String PREFS_NAME = "secret_shared_prefs";
    private static final String EMAIL_KEY = "EMAIL";
    private static final String PASSWORD_HASH_KEY = "PASSWORD_HASH";
    private SharedPreferences sharedPreferences;

    public CredentialsManager(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCredentials(String email, String passwordHash) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_HASH_KEY, passwordHash);
        editor.apply();
    }

    public boolean hasSavedCredentials() {
        return sharedPreferences.contains(EMAIL_KEY) && sharedPreferences.contains(PASSWORD_HASH_KEY);
    }

    public String getSavedEmail() {
        return sharedPreferences.getString(EMAIL_KEY, null);
    }

    public String getSavedPasswordHash() {
        return sharedPreferences.getString(PASSWORD_HASH_KEY, null);
    }

    public void clearSavedCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
