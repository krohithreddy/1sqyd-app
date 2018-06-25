package com.startup.oneSQYD;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sessionmanager {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    public static final String personFamilyName = "Family Name";

    public static final String personGivenName = "Given Name";

    public static final String personDisplayName = "Display Name";

    public static final String personEmail = "Email";

    public static final String personPhoto = "personPhoto";

    public static final String IS_USER_LOGIN = "false";

    public static final String Token = "token";

    public static final String IS_TOKEN_VALID = "false";

    public static final String ServerId = "ServerId";

    public Sessionmanager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("ProfileSession", PRIVATE_MODE);
        editor = pref.edit();
    }


    public void SetToken(String token){
        System.out.println("SetToken "+token);
        editor.putString(Token,token);
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.commit();
    }



    public void CreateUserProfile(GoogleSignInAccount acct,String ServId){
        if (acct != null) {
            editor.putString(personDisplayName,acct.getDisplayName());
            editor.putString(personGivenName,acct.getGivenName());
            editor.putString(personFamilyName,acct.getFamilyName());
            editor.putString(personEmail,acct.getEmail());
            editor.putString(personPhoto, String.valueOf(acct.getPhotoUrl()));
            editor.putBoolean(IS_USER_LOGIN, true);
            editor.putString(ServerId,ServId);
            editor.commit();
        }
    }

    public void SignIn(String EmailString)  {

        UserClient.SignInJson Json = new UserClient.SignInJson(EmailString);

        UserClient serviceGet =
                ServiceGenerator.createServiceSignIn(UserClient.class);
        Call<UserClient.ResponseSignInJson> call = serviceGet.UserSignIn(Json);
        call.enqueue(new Callback<UserClient.ResponseSignInJson>() {
            @Override
            public void onResponse(Call<UserClient.ResponseSignInJson> call, Response<UserClient.ResponseSignInJson> response) {
                UserClient.ResponseSignInJson responseObject = response.body();
                SetToken(responseObject.getToken());
            }

            @Override
            public void onFailure(Call<UserClient.ResponseSignInJson> call, Throwable t) {

            }
        });



    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getProfileDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(personFamilyName, pref.getString(personFamilyName, null));

        user.put(personEmail, pref.getString(personEmail, null));

        user.put(personGivenName, pref.getString(personGivenName, null));

        user.put(personDisplayName, pref.getString(personDisplayName, null));

        user.put(personPhoto, pref.getString(personPhoto, null));

        user.put(Token,pref.getString(Token,null));

        user.put(ServerId,pref.getString(ServerId,null));

        return user;
    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}


