package com.startup.oneSQYD;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class SellLandFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_land_form);

        Sessionmanager PersonalSession;
        PersonalSession = new Sessionmanager(this);
        final HashMap<String, String> profile =  PersonalSession.getProfileDetails();

        final EditText Phone_number = (EditText) findViewById(R.id.Phone_number);
        final EditText Expected_value = (EditText) findViewById(R.id.Land_value);
        final EditText Units = (EditText) findViewById(R.id.Units);

        Button LandImageButton = (Button) findViewById(R.id.Land_button);
        Button SurveyImageButton = (Button) findViewById(R.id.Survey_button);



        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Phonenumber = Integer.valueOf(Phone_number.getText().toString());
                int Expectedvalue = Integer.valueOf(Expected_value.getText().toString());
                int NoofUnits = Integer.valueOf(Units.getText().toString());


            }
        });







    }

}
