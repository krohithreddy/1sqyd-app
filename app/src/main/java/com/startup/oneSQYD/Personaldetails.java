package com.startup.oneSQYD;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class Personaldetails extends AppCompatActivity {

    Sessionmanager personaldetailssession;

    RequestHandler object;
    Context _context;

    public String PhotoUrl;
    Personaldetails pd;




    void postExecuteFunc(String s,Context _context) {
        System.out.println("SendPostRequest output : " );
        System.out.println(s);
        //handle response

        Toast.makeText(_context, s+" : Responded well", Toast.LENGTH_LONG).show();

    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldetails);


        final ImageView ProfileImage = (ImageView) findViewById(R.id.ProfileimageView);
        final EditText GivenName = (EditText) findViewById(R.id.GivenName);
        final EditText FamilyName = (EditText) findViewById(R.id.FamilyName);
        final EditText DisplayName = (EditText) findViewById(R.id.DisplayName);
        final EditText Email = (EditText) findViewById(R.id.Email);



        personaldetailssession = new Sessionmanager(Personaldetails.this);
        final HashMap<String, String> profile =  personaldetailssession.getProfileDetails();

        PhotoUrl = profile.get(Sessionmanager.personPhoto);
        Picasso.get().load(PhotoUrl).into(ProfileImage);


        GivenName.setText(profile.get(Sessionmanager.personGivenName));
        FamilyName.setText(profile.get(Sessionmanager.personFamilyName));
        DisplayName.setText(profile.get(Sessionmanager.personDisplayName));
        Email.setText(profile.get(Sessionmanager.personEmail));



        final Button ContinueButton = findViewById(R.id.ContinueButton);

        ContinueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {





                Intent MainActivty = new Intent(Personaldetails.this, MainActivity.class);
                Personaldetails.this.startActivity(MainActivty);


              // Toast.makeText(Personaldetails.this, " : click finished", Toast.LENGTH_LONG).show();

            }
        });
    }



}
