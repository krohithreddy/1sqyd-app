package com.startup.oneSQYD;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import me.relex.circleindicator.CircleIndicator;


public class LoginScreen extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TAG";
    private static int RC_SIGN_IN = 9001;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    Sessionmanager newsession;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);



        newsession = new Sessionmanager(LoginScreen.this);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        if(newsession.isUserLoggedIn()){
            Intent MainActivity = new Intent(this, MainActivity.class);
            LoginScreen.this.startActivity(MainActivity);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
         account = GetAccountDetails();
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
//                System.out.println("The Display name : "+account.getDisplayName());
                if(account == null){
                    System.out.println("New Login");
                    signIn();

                }
                else{

//                    System.out.print("The email : " + account.get);
//                    System.out.print("photo : "+account.getPhotoUrl());
                    new LoginHandler().execute();

                }
                break;
            // ...
        }
    }

    public GoogleSignInAccount GetAccountDetails(){
        account = GoogleSignIn.getLastSignedInAccount(LoginScreen.this);
        return account;
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        account = null;
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            new SigninHandler().execute();
            // Signed in successfully, show authenticated UI.
            Log.w(TAG, "signInResult=" + account.getGivenName());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    public class SigninHandler extends AsyncTask<String, Void, String> {

            public String ResponseString;
////        protected void onPreExecute () {
////            Dialog = new ProgressDialog(_context);
////            Dialog.setMessage("Please wait...");
////            Dialog.setCancelable(false);
////            Dialog.show();
////        }

        protected String doInBackground (String...arg0){

            try {

                URL url = new URL(LoginScreen.this.getString(R.string.ServerURL) + "user/signup"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                try {
                    postDataParams.put("Email",account.getEmail());
                    postDataParams.put("GivenName",account.getGivenName());
                    postDataParams.put("FamilyName",account.getFamilyName());
                    postDataParams.put("DisplayName",account.getDisplayName());
                    postDataParams.put("Url",account.getPhotoUrl());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setReadTimeout(30000 /* milliseconds */);
                conn.setConnectTimeout(30000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                //TO add in header
                //  conn.setRequestProperty("authorization", "bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Imtlc3N5cm9oaXRocmVkZHlAZ21haWwuY29tIiwidXNlcklkIjoiNWIwMDc3MmVkOTE4MzgwNjRkNDc5MjIyIiwiaWF0IjoxNTI2NzU3Mjg2LCJleHAiOjE1MjY3NjA4ODZ9.wJKS_HZpQgp9HtvJ85MzDsAUp9B9M6BGCo7ZwPT5kjw");

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(String.valueOf(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                System.out.println("Response code upon connection is  : " + responseCode);

                if (responseCode ==  200 || responseCode == 201) {

                    System.out.println("IF END");
//                    ResponseString = conn.g;
                    System.out.println(ResponseString);
                    BufferedReader into = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    System.out.println("IF END1");
                    StringBuffer sb = new StringBuffer("");
                    String line = "";
                    System.out.println("IF END2");
                    while ((line = into.readLine()) != null) {

                        sb.append(line);
                        break;
                    }
                    System.out.println("IF END3");
                    into.close();
                    System.out.println("IF END4");
//                    System.out.println(sb.toString());
//                    JSONObject responsejson = new JSONObject(sb.toString());
//                    System.out.println(responsejson);
                    ResponseString = sb.toString();
                    return String.valueOf(responseCode);


                } else {
                    return new String(String.valueOf(responseCode));
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute (String result){
            super.onPostExecute(result);
//            if (Dialog.isShowing())
//                Dialog.dismiss();

            System.out.println("Result : "+result);
            if(result.equals("201")){
                try {
                    JSONObject JSONResponse = new JSONObject(ResponseString);
                    String ServerId = JSONResponse.getString("ServerId");
                    String token = JSONResponse.getString("token");

                    newsession.CreateUserProfile(account,ServerId);
                    newsession.SetToken(token);
                    Intent PersonalDetailsintent = new Intent(LoginScreen.this, Personaldetails.class);
                    LoginScreen.this.startActivity(PersonalDetailsintent);
    //              Toast.makeText(_context, "Connectin Login successful",
    //                            Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if(result.equals("200")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginScreen.this);
                alertDialogBuilder.setMessage("User account Already Exists! Do you wish to continue with same account?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {
                                    JSONObject JSONResponse = new JSONObject(ResponseString);
                                    String ServerId = JSONResponse.getString("ServerId");
                                    String token = JSONResponse.getString("token");

                                    newsession.CreateUserProfile(account,ServerId);
                                    newsession.SetToken(token);
                                    Intent MainActivityIntent = new Intent(LoginScreen.this, MainActivity.class);
                                    LoginScreen.this.startActivity(MainActivityIntent);
                                    //              Toast.makeText(_context, "Connectin Login successful",
                                    //                            Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Signout!!");
                        signOut();
//                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            // Result = false;
        }

//        }.execute();
////        System.out.println("Result String : "+ResultString[0]);
//        System.out.println("Waiting String : "+Result);
//
//    return ResponseString;
//
//    }


    }


    public class LoginHandler extends AsyncTask<String, Void, String> {

        public String ResponseString;
////        protected void onPreExecute () {
////            Dialog = new ProgressDialog(_context);
////            Dialog.setMessage("Please wait...");
////            Dialog.setCancelable(false);
////            Dialog.show();
////        }

        protected String doInBackground (String...arg0){

            try {

                URL url = new URL(LoginScreen.this.getString(R.string.ServerURL) + "user/login"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                try {
                    postDataParams.put("Email",account.getEmail());
//                    postDataParams.put("GivenName",account.getGivenName());
//                    postDataParams.put("FamilyName",account.getFamilyName());
//                    postDataParams.put("DisplayName",account.getDisplayName());
//                    postDataParams.put("Url",account.getPhotoUrl());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setReadTimeout(30000 /* milliseconds */);
                conn.setConnectTimeout(30000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                //TO add in header
                //  conn.setRequestProperty("authorization", "bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Imtlc3N5cm9oaXRocmVkZHlAZ21haWwuY29tIiwidXNlcklkIjoiNWIwMDc3MmVkOTE4MzgwNjRkNDc5MjIyIiwiaWF0IjoxNTI2NzU3Mjg2LCJleHAiOjE1MjY3NjA4ODZ9.wJKS_HZpQgp9HtvJ85MzDsAUp9B9M6BGCo7ZwPT5kjw");

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(String.valueOf(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                System.out.println("Response code upon connection : " + responseCode);

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));



                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
//                    System.out.println(sb.toString());
                    JSONObject responsejson = new JSONObject(sb.toString());
                    System.out.println(responsejson);
                    ResponseString = sb.toString();
                    return String.valueOf(responseCode);


                } else {
                    return new String(String.valueOf(responseCode));
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute (String result){
            super.onPostExecute(result);
//            if (Dialog.isShowing())
//                Dialog.dismiss();

            System.out.println("Result : "+result);
            if(result.equals("200")){
                try {
                    JSONObject JSONResponse = new JSONObject(ResponseString);
                    String token = JSONResponse.getString("token");

                    newsession.SetToken(token);
                    Intent MainActivityIntent = new Intent(LoginScreen.this, MainActivity.class);
                    LoginScreen.this.startActivity(MainActivityIntent);
                    //              Toast.makeText(_context, "Connectin Login successful",
                    //                            Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // Result = false;
        }

//        }.execute();
////        System.out.println("Result String : "+ResultString[0]);
//        System.out.println("Waiting String : "+Result);
//
//    return ResponseString;
//
//    }


    }


}
