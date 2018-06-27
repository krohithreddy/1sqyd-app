package com.startup.oneSQYD;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

public class BuyLandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_land);

        Intent currentIntent = getIntent();
        String selectedBuyCard = (String) currentIntent.getSerializableExtra("BuyCardSelected");

//        Toast.makeText(this, "Land Image passed : "+selectedLandCard.getLandImage()+"Survey Image : "+selectedLandCard.getSurveyImage(), Toast.LENGTH_SHORT).show();


    }
}
