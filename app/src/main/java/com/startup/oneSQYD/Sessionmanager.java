package com.startup.oneSQYD;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.HashMap;

public class Sessionmanager {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    public static final String personName = "Full Name";

    public static final String personGivenName = "Full Given Name";

    public static final String personEmail = "EmailName";

    public static final String personId = "personId";

//    private static final Uri personPhoto = Uri.parse("personPhoto.com");

    private static final String personFamilyName = "Family Name";

    public Sessionmanager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("ProfileSession", PRIVATE_MODE);
        editor = pref.edit();
    }

    public void CreateUserProfile(GoogleSignInAccount acct){
        if (acct != null) {
            editor.putString(personName,acct.getDisplayName());
            editor.putString(personGivenName,acct.getGivenName());
            editor.putString(personEmail,acct.getEmail());
            editor.putString(personId,acct.getId());
            editor.commit();
//            Uri personPhoto = acct.getPhotoUrl();
        }
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getProfileDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(personName, pref.getString(personName, null));

        // user email id
        user.put(personEmail, pref.getString(personEmail, null));

        user.put(personGivenName, pref.getString(personGivenName, null));

        user.put(personId, pref.getString(personId, null));

        // return user
        return user;
    }

}


