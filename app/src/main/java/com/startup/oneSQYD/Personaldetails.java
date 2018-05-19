package com.startup.oneSQYD;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Personaldetails extends AppCompatActivity {

    Sessionmanager personaldetailssession;

    RequestHandler object;

    public String response;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldetails);
        personaldetailssession = new Sessionmanager(Personaldetails.this);
        final HashMap<String, String> profile =  personaldetailssession.getProfileDetails();
        System.out.println("Profile session : " + profile.get(Sessionmanager.personName));
        final Button ContinueButton = findViewById(R.id.ContinueButton);
        ContinueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject postDataParams = new JSONObject();
                try {
                    postDataParams.put("email",profile.get(Sessionmanager.personEmail));
                    postDataParams.put("password",profile.get(Sessionmanager.personId));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("params",postDataParams.toString());

                object = new RequestHandler(Personaldetails.this,"POST","user/login",postDataParams);

                String s;
                s = object.SendPostRequest();
                System.out.println("SendPostRequest output : " + object.ResponseString);
                Toast.makeText(Personaldetails.this, object.ResponseString + " : Received data Successful", Toast.LENGTH_LONG).show();

            }
        });
    }
}
